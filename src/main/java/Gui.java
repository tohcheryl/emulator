import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author Kristian Krarup S144122 & Cheryl Toh S181611
 */

public class Gui extends Application {

    private static final GuiController GUI_CONTROLLER = new GuiController();

    @Override
    public void init() throws Exception {
        super.init();
        System.out.println("Inside init() method! Perform necessary initializations here.");
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("gui.fxml"));
        loader.setController(GUI_CONTROLLER);
        Parent root = (Parent) loader.load();
        Scene scene = new Scene(root, 500, 350);

        primaryStage.setTitle("Climify Emulator");
        primaryStage.setScene(scene);
        primaryStage.show();
        GUI_CONTROLLER.setGui(this);
        GUI_CONTROLLER.start();

        Runnable r = new Runnable() {
            public void run() {
                try {
                    GUI_CONTROLLER.getEmulator().background();
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
