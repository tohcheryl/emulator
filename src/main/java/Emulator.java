import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.io.IOException;

public class Emulator {

    public static void main(String[] args) {
        registerPi("000000012a34");
        //setData(7);
    }

    public static void setData(int inputData) {
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
            System.out.println("Publishing message: " + content);
            MqttMessage message = new MqttMessage(content.getBytes());
            message.setQos(qos);
            sampleClient.publish(topic, message);
            System.out.println("Message published");
            //sampleClient.disconnect();
            //System.out.println("Disconnected");
            //System.exit(0);
        } catch (MqttException me) {
            printErrorMessages(me);
        }
    }

    public static void registerPi(String uuid) {
        HttpClient httpClient = HttpClientBuilder.create().build();
        String json = "{\"UUID\":\"" + uuid + "\"}";
        System.out.println(json);
        try {
            String api = "http://se2-webapp04.compute.dtu.dk/api/api-post-rpi.php";
            HttpPost request = new HttpPost(api);
            StringEntity entity = new StringEntity(json);
            request.setEntity(entity);
            request.setHeader("Content-Type", "application/json");
            HttpResponse response = httpClient.execute(request);
            System.out.println(response.getStatusLine().getStatusCode());
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
