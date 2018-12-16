import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class GuiController {

    private Emulator emulator;
    private Gui gui;

    public GuiController() {
    }

    public GuiController(Emulator emulator, Gui gui) {
        this.emulator = emulator;
        this.gui = gui;
    }

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
            emulator.setData(tempId.getText(),"temp", tempToSet);
        } catch (Exception e) {
            System.out.println(e);
        }
        try {
            carbonDioxideToSet = carbonDioxideField.getText();
            setCarbonDioxide(carbonDioxideToSet);
            emulator.setData(carbonDioxideId.getText(),"CO2", carbonDioxideToSet);
        } catch (Exception e) {
            System.out.println(e);
        }
        try {
            windowStatusToSet = windowField.getText();
            setWindow(windowStatusToSet);
            emulator.setData(windowId.getText(),"Window", windowStatusToSet);
        } catch (Exception e) {
            System.out.println(e);
        }
        return;
    }

    @FXML
    public void handleRegButtonAction() {
        tempDeviceId = tempId.getText();
        //setTempID(tempDeviceId);
        emulator.registerDevice("Thermostat"+tempDeviceId , "Thermostat", tempDeviceId);

        carbonDioxideDeviceId = carbonDioxideId.getText();
        emulator.registerDevice("CO2" + carbonDioxideDeviceId, "CO2", carbonDioxideDeviceId);

        windowDeviceId = windowId.getText();
        emulator.registerDevice("Window" + windowDeviceId, "Window", windowDeviceId);
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

    private void setEmulator() {
        if (this.emulator == null) {
            this.emulator = new Emulator(this);
        }
    }

    public Emulator getEmulator() {
        return this.emulator;
    }

    public void setGui(Gui gui) {
        this.gui = gui;
    }

    private void startEmulator() {
        emulator.registerPi();
        emulator.receiveData();
    }

    public void start() {
        setEmulator();
        startEmulator();
    }
}
