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


    //3. row
    @FXML
    public Label currentTemp;

    @FXML
    public Label currentCarbonDioxide;

    @FXML
    public Label currentWindow;

    public Label currenttempID;
    @FXML
    public Label currentcarbonDioxideID;
    @FXML
    public Label currentwindowID;


    //4. row

    @FXML
    public Button setTempButton;

    @FXML
    protected void handleSetButtonAction(ActionEvent event) {
        try {
            tempToSet = tempField.getText();
            setTemp(tempToSet);
            Emulator.setData(0, tempToSet);
        } catch (Exception e) {
            System.out.println("Exception occurred");
        }
        try {
            carbonDioxideToSet = carbonDioxideField.getText();
            setCarbonDioxide(carbonDioxideToSet);
            Emulator.setData(1, carbonDioxideToSet);
        } catch (Exception e) {
            System.out.println("Exception occurred");
        }
        try {
            windowStatusToSet = windowField.getText();
            setWindow(windowStatusToSet);
            Emulator.setData(2, windowStatusToSet);
        } catch (Exception e) {
            System.out.println("Exception occurred");
        }
        return;
    }
    public void handleRegButtonAction(ActionEvent actionEvent) {
        try {
            IDtemp = tempId.getText();
            System.out.println(IDtemp + "::" + tempId);
            setcurrenttempID(IDtemp);
        }catch (Exception e) {
            System.out.println("Exception occurred");
        }
        try {
            IDco2 = carbonDioxideId.getText();
            setcurrentcarbonDioxideID(IDco2);
        }catch (Exception e) {
            System.out.println("Exception occurred");
        }
        try {
            IDwindow = windowId.getText();
            setcurrentwindowID(IDwindow);
        }catch (Exception e) {
            System.out.println("Exception occurred");
        }
        return;
    }


    public void setTemp(String temp) {
        try {
            System.out.println("setting temperature");
            System.out.println("temp: " + temp);
            tempToSet = temp;
            currentTemp.setText(temp);
            System.out.println("here");
        }catch (Exception e) {
            System.out.println(e);
        }

    }

    public void setCarbonDioxide(String carbonDioxideLevel) {
        currentCarbonDioxide.setText(carbonDioxideLevel);
    }

    public void setWindow(String status) {
        currentWindow.setText(status);
    }


    public void setcurrenttempID(String status) {
        currenttempID.setText(status);
    }
    public void setcurrentcarbonDioxideID(String status) {
        currentcarbonDioxideID.setText(status);
    }
    public void setcurrentwindowID(String status) {
        currentwindowID.setText(status);
    }




}
