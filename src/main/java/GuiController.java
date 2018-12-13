import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class GuiController {
    int tempToSet;
    int carbonDioxideToSet;
    String windowStatusToSet;
    @FXML
    private TextField idField;

    //2. row
    @FXML
    public static  TextField tempField;

    @FXML
    public static  TextField carbonDioxideField;

    @FXML
    public static  TextField windowField;


    //3. row
    @FXML
    public static Label currentTemp;

    @FXML
    public static  Label currentCarbonDioxide;

    @FXML
    public static  Label currentWindow;



    @FXML
    public static  Button setTempButton;

    @FXML
    protected void handleSetButtonAction(ActionEvent event) {
        try {
            tempToSet = Integer.parseInt(tempField.getText());
            setTemp(tempToSet);
            Emulator.setData(0, Integer.toString(tempToSet));
        }catch (Exception e) {
            System.out.println("Exception occurred");
        }
        try {
            carbonDioxideToSet = Integer.parseInt(carbonDioxideField.getText());
            setCarbonDioxide(carbonDioxideToSet);
            Emulator.setData(1, Integer.toString(carbonDioxideToSet));
        }catch (Exception e) {
            System.out.println("Exception occurred");
        }
        try {
            windowStatusToSet = windowField.getText();
            setWindow(windowStatusToSet);
            Emulator.setData(2, windowStatusToSet);
        }catch (Exception e) {
            System.out.println("Exception occurred");
            }
        return;
    }

    public static void setTemp(int temp) {
        currentTemp.setText((Integer.toString(temp)));
    }

    public static void setCarbonDioxide(int carbonDioxideLevel) {
        currentCarbonDioxide.setText(Integer.toString(carbonDioxideLevel));
    }

    public static void setWindow(String status) {
        currentWindow.setText(status);
    }


}
