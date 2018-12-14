

import javafx.application.Platform;
import okhttp3.*;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.io.*;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.json.JSONObject;


public class Emulator {

    public static GuiController GUI_CONTROLLER;
    public static String uuid;
    public static String values;

    public Emulator(GuiController guiControl) {
        GUI_CONTROLLER = guiControl;
        uuid = generateUuid();
    }

    /*
    public static void main(String[] args) {
        String regID = "phew";
        registerPi(regID);
        receiveData(regID);
        //setData(0, "98");
        //setData(7);
    }
    */

    public void BackGround() throws InterruptedException {
        while(true){
            TimeUnit.SECONDS.sleep(30);
            GUI_CONTROLLER.handleSetButtonAction();

        }

    }

    public static void setData(int parameter, String inputData) {
        String topic = "TempData";
        String content = "Sensor2,building=\"101\"" + " Temperature=" + inputData + ",batterylvl=12" + "uuid=" + uuid;
        int qos = 2;
        String broker = "tcp://se2-webapp04.compute.dtu.dk:1883";
        String clientId = "98";
        MemoryPersistence persistence = new MemoryPersistence();

        try {
            MqttClient sampleClient = new MqttClient(broker, clientId, persistence);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            System.out.println("Connecting to broker: " + broker);
            sampleClient.connect(connOpts);
            System.out.println("Connected");

            if (parameter == 0) {
                content = "Sensor2,building=\"101\"" + " Temperature=" + inputData + ",batterylvl=12";
                //content = content + ",uuid=phew";
            }

            if (parameter == 1) {
                content = "Sensor2,building=\"101\"" + " CO2=" + inputData + ",batterylvl=12";
            }

            if (parameter == 2) {
                content = "Sensor2,building=\"101\"" + " Window=" + inputData + ",batterylvl=12";
            }
            System.out.println("Publishing message: " + content);
            MqttMessage message = new MqttMessage(content.getBytes());
            message.setQos(qos);
            sampleClient.publish(topic, message);
            System.out.println("Message published");
            sampleClient.disconnect();

            //sampleClient.disconnect();
            //System.out.println("Disconnected");
            //System.exit(0);
        } catch (MqttException me) {
            printErrorMessages(me);
        }
    }

    public String getUuid() {
        return this.uuid;
    }

    public static void receiveData(String uuid) {
        String topic = uuid + "/Commands";
        int qos = 2;
        String broker = "tcp://se2-webapp04.compute.dtu.dk:1883";
        String clientId = "24";
        MemoryPersistence persistence = new MemoryPersistence();

        try {
            MqttClient client = new MqttClient(broker, clientId, persistence);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            client.setCallback(new MqttCallback() {
                public void connectionLost(Throwable cause) {
                }

                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    System.out.println("Message received: " + message.toString());
                    String m = message.toString();
                    JSONObject messageData = new JSONObject(m);

                    String device = (String) messageData.get("device");
                    values = (String) messageData.get("values");

                    if (device.equals("11")) {
                        System.out.println("Emulator is setting the temperature");
                        System.out.println("temperature: " + values);
                        //GUI_CONTROLLER.setTemp(Integer.parseInt(values));
                    }
                    if (device.equals("11")) {
                        System.out.println("Emulator is setting CO2");
                        System.out.println("carbon dioxide: " + values);
                        //GUI_CONTROLLER.setCarbonDioxide(Integer.parseInt(values));
                    }
                    if (device.equals("11")) {
                        //GUI_CONTROLLER.setWindow(values);
                        System.out.println("window: " + values);
                    }

                    Platform.runLater(new Runnable() {
                        public void run() {
                            GUI_CONTROLLER.setTemp(values);
                            //GUI_CONTROLLER.setCarbonDioxide(Integer.parseInt(values));
                            //GUI_CONTROLLER.setWindow(values);
                        }
                    });
                }

                public void deliveryComplete(IMqttDeliveryToken token) {
                }
            });

            System.out.println("Connecting to broker: " + broker);
            client.connect(connOpts);
            client.subscribe(topic, qos);

        } catch (MqttException me) {
            printErrorMessages(me);
        }
    }

    public static String generateUuid() {
        String uniqueId = UUID.randomUUID().toString();
        String uuid = uniqueId.substring(0, Math.min(uniqueId.length(), 30));
        System.out.println(uuid);
        return uuid;
    }

    public static void registerPi(String uuid) {
        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        String requestBodyString = "UUID=" + uuid + "&undefined=";
        RequestBody body = RequestBody.create(mediaType, requestBodyString);
        Request request = new Request.Builder()
                .url("http://se2-webapp04.compute.dtu.dk/api/api-post-rpi.php")
                .post(body)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .addHeader("cache-control", "no-cache")
                .build();
        try {
            Response response = client.newCall(request).execute();
            System.out.println(response.toString());
        } catch (IOException io) {
            io.printStackTrace();
        }
    }


    public static void printErrorMessages(MqttException me) {
        System.out.println("reason " + me.getReasonCode());
        System.out.println("msg " + me.getMessage());
        System.out.println("loc " + me.getLocalizedMessage());
        System.out.println("cause " + me.getCause());
        System.out.println("excep " + me);
        me.printStackTrace();
    }
}
