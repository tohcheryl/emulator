import javafx.application.Platform;
import okhttp3.*;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.io.*;
import java.net.URLEncoder;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONObject;

/**
 * @author Kristian Krarup S144122 & Cheryl Toh S181611
 */

public class Emulator {

    private GuiController guiController;
    private String uuid;
    private String values;

    private static Logger logger = Logger.getLogger(OkHttpClient.class.getName());

    public Emulator(GuiController guiControl) {
        logger.setLevel(Level.FINE);
        guiController = guiControl;
        uuid = generateUuid();
    }

    public void background() throws InterruptedException {
        while (true) {
            TimeUnit.SECONDS.sleep(30);
            guiController.handleSetButtonAction();
        }
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void setData(String SensorID, String parameter, String inputData) {
        String topic = "TempData";

        String content = "Sensor2,building=\"101\"" + " Temperature=" + inputData + ",batterylvl=12" + "uuid=" + uuid;
        int qos = 2;
        String broker = "tcp://se2-webapp04.compute.dtu.dk:1883";
        String clientId = "98";
        MemoryPersistence persistence = new MemoryPersistence();

        try {
            MqttClient client = new MqttClient(broker, clientId, persistence);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            //System.out.println("Connecting to broker: " + broker);
            client.connect(connOpts);
            //System.out.println("Connected");


            if ("temp".equals(parameter)) {
                content = SensorID + ",building=\"303b\"" + " Temperature=" + inputData + ",batterylvl=12" + "uuid=" + uuid;
            }
            if ("CO2".equals(parameter)) {
                content = SensorID + ",building=\"Building 324\"" + " CO2=" + inputData + ",batterylvl=12" + "uuid=" + uuid;

            }
            if ("Window".equals(parameter)) {
                content = SensorID + ",building=\"324\"" + " Window=" + inputData + ",batterylvl=12" + "uuid=" + uuid;

            }

            //System.out.println("Publishing message: " + content);
            MqttMessage message = new MqttMessage(content.getBytes());
            message.setQos(qos);
            client.publish(topic, message);
            System.out.println("Messages published: " + parameter);
            client.disconnect();

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

    public void receiveData() {
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

    public String generateUuid() {
        String uniqueId = UUID.randomUUID().toString();
        uniqueId = uniqueId.substring(0, Math.min(uniqueId.length(), 30));
        System.out.println(uniqueId);
        return uniqueId;
    }

    public void registerPi() {
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
            response.body().close();
        } catch (IOException io) {
            io.printStackTrace();
        }
    }

    public void registerDevice(String label, String type, String deviceUuid) {
        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        String queryString = formQueryStringForDevice(label, type, deviceUuid);
        RequestBody body = RequestBody.create(mediaType, queryString);
        Request request = new Request.Builder()
                .url("http://se2-webapp04.compute.dtu.dk/api/api-post-actuators.php")
                .post(body)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .addHeader("cache-control", "no-cache")
                .build();

        try {
            Response response = client.newCall(request).execute();
            response.body().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String urlEncode(String string) {
        String encodedString = "";
        try {
            encodedString = URLEncoder.encode(string, "UTF-8");
        } catch (UnsupportedEncodingException uee) {
            uee.printStackTrace();
        }
        return encodedString;
    }

    public String formQueryStringForDevice(String label, String type, String deviceUuid) {
        String encodedLabel = urlEncode(label);
        String requestBodyString = "label=" + encodedLabel;
        requestBodyString = requestBodyString + "&zwave_class_generic=" + type;
        requestBodyString = requestBodyString + "&thingUID=" + deviceUuid;
        requestBodyString = requestBodyString + "&serial=" + uuid + "&undefined=";
        return requestBodyString;
    }


    public void printErrorMessages(MqttException me) {
        System.out.println("reason " + me.getReasonCode());
        System.out.println("msg " + me.getMessage());
        System.out.println("loc " + me.getLocalizedMessage());
        System.out.println("cause " + me.getCause());
        System.out.println("excep " + me);
        me.printStackTrace();
    }
}
