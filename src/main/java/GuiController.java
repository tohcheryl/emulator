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
    private TextField tempField;

    @FXML
    private TextField carbonDioxideField;

    @FXML
    private TextField windowField;


    //3. row
    @FXML
    private Label currentTemp;

    @FXML
    private Label currentCarbonDioxide;

    @FXML
    private Label currentWindow;



    @FXML
    private Button setTempButton;

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

    public void setTemp(int temp) {
        currentTemp.setText((Integer.toString(temp)));
    }

    public void setCarbonDioxide(int carbonDioxideLevel) {
        currentCarbonDioxide.setText(Integer.toString(carbonDioxideLevel));
    }

    public void setWindow(String status) {
        currentWindow.setText(status);
    }


}
