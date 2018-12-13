import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class GuiController {
    int tempToSet;
    int carbonDioxideToSet;
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
    public Label currenttempID;
    @FXML
    public Label currentcarbonDioxideID;
    @FXML
    public Label currentwindowID;

    //3. row
    @FXML
    public TextField tempField;

    @FXML
    public TextField carbonDioxideField;

    @FXML
    public  TextField windowField;


    //4. row
    @FXML
    public Label currentTemp;

    @FXML
    public  Label currentCarbonDioxide;

    @FXML
    public  Label currentWindow;

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


    public void setTemp(int temp) {
        currentTemp.setText((Integer.toString(temp)));
    }

    public void setCarbonDioxide(int carbonDioxideLevel) {
        currentCarbonDioxide.setText(Integer.toString(carbonDioxideLevel));
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
