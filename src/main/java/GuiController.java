import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class GuiController {
    String tempToSet;
    String carbonDioxideToSet;
    String windowStatusToSet;

    String tempDeviceId;
    String carbonDioxideDeviceId;
    String windowDeviceId;

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
            Emulator.setData(tempId.getText(),"temp", tempToSet);
        } catch (Exception e) {
            System.out.println(e);
        }
        try {
            carbonDioxideToSet = carbonDioxideField.getText();
            setCarbonDioxide(carbonDioxideToSet);
            Emulator.setData(carbonDioxideId.getText(),"CO2", carbonDioxideToSet);
        } catch (Exception e) {
            System.out.println(e);
        }
        try {
            windowStatusToSet = windowField.getText();
            setWindow(windowStatusToSet);
            Emulator.setData(windowId.getText(),"Window", windowStatusToSet);
        } catch (Exception e) {
            System.out.println(e);
        }
        return;
    }

    @FXML
    public void handleRegButtonAction() {
        tempDeviceId = tempId.getText();
        //setTempID(tempDeviceId);
        Emulator.registerDevice("Thermostat"+tempDeviceId , "Thermostat", tempDeviceId, Emulator.uuid);

        carbonDioxideDeviceId = carbonDioxideId.getText();
        Emulator.registerDevice("CO2" + carbonDioxideDeviceId, "CO2", carbonDioxideDeviceId, Emulator.uuid);
        //Emula

        windowDeviceId = windowId.getText();
        Emulator.registerDevice("Window" + windowDeviceId, "Window", windowDeviceId, Emulator.uuid);
    }


    public void setTemp(String status) {
        try {
            System.out.println("setting temperature");
            tempField.setText(status);
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public void setCarbonDioxide(String status) {
        try {
            System.out.println("setting carbonDioxideLevel");
            carbonDioxideField.setText(status);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void setWindow(String status) {
        try {
            System.out.println("setting window status");
            windowField.setText(status);
        } catch (Exception e) {
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
