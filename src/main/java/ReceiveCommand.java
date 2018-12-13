/*
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class ReceiveCommand {

    public static void main(String... args) {
        receiveData("abcd1234");
    }

    public static void receiveData(String uuid) {
        String topic = uuid + "/#";
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
                }

                public void deliveryComplete(IMqttDeliveryToken token) {
                }
            });

            System.out.println("Connecting to broker: " + broker);
            client.connect(connOpts);
            client.subscribe(topic, qos);


        } catch (MqttException me) {
            Emulator.printErrorMessages(me);
        }
    }
}
*/
