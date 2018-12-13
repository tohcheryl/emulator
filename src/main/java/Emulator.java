import okhttp3.*;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.io.*;
import java.util.UUID;

public class Emulator {

    public static void main(String[] args) {
        String uniqueID = UUID.randomUUID().toString();
        System.out.println(uniqueID);
        String regID = "phew";
        registerPi(uniqueID);
        //setData(7);
    }

    public static void setData(int parameter, String inputData) {
        String topic = "TempData";
        String content = "Sensor2,building=\"101\"" + " Temperature=" + inputData + ",batterylvl=12";
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
            }

            if (parameter == 1) {
                content = "Sensor2,building=\"101\"" + "CarbonDioxide=" + inputData + ",batterylvl=12";
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

    private static String convertStreamToString(InputStream is) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        is.close();
        return sb.toString();
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
