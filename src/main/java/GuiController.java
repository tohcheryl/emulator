import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class GuiController {
    @FXML
    private TextField idField;

    @FXML
    private TextField tempField;

    @FXML
    private TextField carbonDioxideField;

    @FXML
    private TextField windowField;

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
        int tempToSet = Integer.parseInt(tempField.getText());
        int carbonDioxideToSet = Integer.parseInt(carbonDioxideField.getText());
        String windowStatusToSet = windowField.getText();

        setTemp(tempToSet);
        setCarbonDioxide(carbonDioxideToSet);
        setWindow(windowStatusToSet);

        Emulator.setData(tempToSet);
        return;
    }

    private void setTemp(int temp) {
        currentTemp.setText((Integer.toString(temp)));
    }

    private void setCarbonDioxide(int carbonDioxideLevel) {
        currentCarbonDioxide.setText(Integer.toString(carbonDioxideLevel));
    }

    private void setWindow(String status) {
        currentWindow.setText(status);
    }


}
