import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class GuiController {
    String tempToSet;
    String carbonDioxideToSet;
    String windowStatusToSet;

    String IDtemp;
    String IDco2;
    String IDwindow;

    //1. row
    @FXML
    public TextField tempId;
    @FXML
    public TextField carbonDioxideId;
    @FXML
    public TextField windowId;

    //2. row
    @FXML
    public TextField tempField;
    @FXML
    public TextField carbonDioxideField;
    @FXML
    public TextField windowField;


    @FXML
    public void handleSetButtonAction() {
        try {
            tempToSet = tempField.getText();
            setTemp(tempToSet);
            Emulator.setData("temp",tempToSet);
        } catch (Exception e) {
            System.out.println(e);
        }
        try {
            carbonDioxideToSet = carbonDioxideField.getText();
            setCarbonDioxide(carbonDioxideToSet);
            Emulator.setData("CO2",carbonDioxideToSet);
        } catch (Exception e) {
            System.out.println(e);
        }
        try {
            windowStatusToSet = windowField.getText();
            setWindow(windowStatusToSet);
            Emulator.setData("Window",windowStatusToSet);
        } catch (Exception e) {
            System.out.println(e);
        }
        return;
    }

    @FXML
    public void handleRegButtonAction() {
        try {
            IDtemp = tempId.getText();
            System.out.println(IDtemp + "::" + tempId);
            setTempID(IDtemp);
        }catch (Exception e) {
            System.out.println(e);
        }
        try {
            IDco2 = carbonDioxideId.getText();
            setCarbonDioxideID(IDco2);
        }catch (Exception e) {
            System.out.println(e);
        }
        try {
            IDwindow = windowId.getText();
            setWindowID(IDwindow);
        }catch (Exception e) {
            System.out.println(e);
        }
        return;
    }


    public void setTemp(String status) {
        try {
            System.out.println("setting temperature");
            tempField.setText(status);
        }catch (Exception e) {
            System.out.println(e);
        }

    }

    public void setCarbonDioxide(String status) {
        try {
            System.out.println("setting carbonDioxideLevel");
            carbonDioxideField.setText(status);
        }catch (Exception e) {
            System.out.println(e);
        }
    }

    public void setWindow(String status) {
        try {
            System.out.println("setting window status");
            windowField.setText(status);
        }catch (Exception e) {
            System.out.println(e);
        }


    }


    public void setTempID(String status) {
        tempId.setText(status);
    }
    public void setCarbonDioxideID(String status) {
        carbonDioxideId.setText(status);
    }
    public void setWindowID(String status) {
        windowId.setText(status);
    }




}
