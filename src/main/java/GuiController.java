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
    private Label CurrentTemp;

    @FXML
    private Button setTempButton;

    @FXML
    protected void handleSetTempButtonAction(ActionEvent event) {
        int tempToSet = Integer.parseInt(tempField.getText());
        handleCurrentTemp(tempToSet);
        Emulator.setData(tempToSet);
        return;
    }
    public void handleCurrentTemp(int temp){
        CurrentTemp.setText((Integer.toString(temp)));
    }



}
