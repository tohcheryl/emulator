import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Gui extends Application {

    private Emulator emulator;

    @Override
    public void init() throws Exception {
        super.init();
        System.out.println("Inside init() method! Perform necessary initializations here.");
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        //Label label = new Label("Hello World");
        //label.setAlignment(Pos.CENTER);
        //Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("gui.fxml"));
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("gui.fxml"));
        Parent root = (Parent) loader.load();
        GuiController guiController = loader.getController();
        Scene scene = new Scene(root, 500, 350);

        primaryStage.setTitle("Climify Emulator");
        primaryStage.setScene(scene);
        primaryStage.show();
        emulator = new Emulator(guiController);
        emulator.receiveData(emulator.getUuid());

        Runnable r = new Runnable() {
            public void run() {
                try {
                    emulator.BackGround();
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        };
        new Thread(r).start();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        System.out.println("Inside stop() method! Destroy resources. Perform Cleanup.");
    }

    public static void main(String[] args) {
        launch(args);



    }
}
