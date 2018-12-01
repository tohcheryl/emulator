import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class GuiController {
    @FXML
    private TextField idField;

    @FXML
    private TextField tempField;

    @FXML
    private Button setTempButton;

    @FXML
    protected void handleSetTempButtonAction(ActionEvent event) {
        int tempToSet = Integer.parseInt(tempField.getText());
        Emulator.setData(tempToSet);
        return;
    }


}
