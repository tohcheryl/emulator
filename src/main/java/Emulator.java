

import javafx.application.Platform;
import okhttp3.*;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.io.*;
import java.net.URLEncoder;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.json.JSONObject;


public class Emulator {

    public static GuiController guiController;
    public static String uuid;
    public static String values;

    public Emulator(GuiController guiControl) {
        guiController = guiControl;
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
        while (true) {
            TimeUnit.SECONDS.sleep(30);
            guiController.handleSetButtonAction();
        }
    }

    public static void setData(String SensorID,String parameter,String inputData) {
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
            //System.out.println("Connecting to broker: " + broker);
            sampleClient.connect(connOpts);
            //System.out.println("Connected");



            if("temp".equals(parameter)){
                content = SensorID+ ",building=\"303b\"" + " Temperature=" + inputData + ",batterylvl=12" + "uuid=" + uuid;
            }
            if("CO2".equals(parameter)){
                content = SensorID + ",building=\"Building 324\"" + " CO2=" + inputData + ",batterylvl=12"+ "uuid=" + uuid;

            }
            if("Window".equals(parameter)){
                content = SensorID + ",building=\"324\"" + " Window=" + inputData + ",batterylvl=12"+ "uuid=" + uuid;

            }

            //System.out.println("Publishing message: " + content);
            MqttMessage message = new MqttMessage(content.getBytes());
            message.setQos(qos);
            sampleClient.publish(topic, message);
            System.out.println("Messages published: " + parameter);
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


                    System.out.println("Emulator is setting the temperature");
                    System.out.println("temperature: " + values);
                    //guiController.setTemp(Integer.parseInt(values));


                    System.out.println("Emulator is setting CO2");
                    System.out.println("carbon dioxide: " + values);
                    //guiController.setCarbonDioxide(Integer.parseInt(values));


                    //guiController.setWindow(values);
                    System.out.println("window: " + values);

                    Platform.runLater(new Runnable() {
                        public void run() {
                            guiController.setTemp(values);
                            //guiController.setCarbonDioxide(Integer.parseInt(values));
                            //guiController.setWindow(values);
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

    public static void registerDevice(String label, String type, String deviceUuid, String rpiUuid) {
        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        String queryString = formQueryStringForDevice(label, type, deviceUuid, rpiUuid);
        RequestBody body = RequestBody.create(mediaType, queryString);
        Request request = new Request.Builder()
                .url("http://se2-webapp04.compute.dtu.dk/api/api-post-actuators.php")
                .post(body)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .addHeader("cache-control", "no-cache")
                .build();

        try {
            Response response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String urlEncode(String string) {
        String encodedString = "";
        try {
            encodedString = URLEncoder.encode(string, "UTF-8");
        } catch (UnsupportedEncodingException uee) {
            uee.printStackTrace();
        }
        return encodedString;
    }

    public static String formQueryStringForDevice(String label, String type, String deviceUuid, String rpiUuid) {
        String encodedLabel = urlEncode(label);
        String requestBodyString = "label=" + encodedLabel;
        requestBodyString = requestBodyString + "&zwave_class_generic=" + type;
        requestBodyString = requestBodyString + "&thingUID=" + deviceUuid;
        requestBodyString = requestBodyString + "&serial=" + rpiUuid + "&undefined=";
        return requestBodyString;
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
