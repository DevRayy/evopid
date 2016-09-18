import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class GuiRoot {
    private JButton startButton;
    public JPanel rootPanel;
    private JPanel settingsPanel;
    private JTextField samplesField;
    private JTextField timeField;
    private JTextField populationsField;
    private JTextField specimensField;
    private JTextField risingTimeLowField;
    private JTextField risingTimeHighField;
    private JTextField settlingMarginField;
    private JTextField overshootField;
    private JTextField risingTime;
    private JTextField settlingTimeField;
    private JPanel matriciesPanel;
    private JTextArea matrixA;
    private JTextArea matrixB;
    private JTextArea matrixC;
    private JTextArea matrixD;
    private JTextArea logArea;
    private JButton loadButton;
    private JButton saveButton;

    public GuiRoot() {
        startButton.addActionListener(new StartBtnClick(this));
    }

    public void loadSettings() {
        SettingsContainer.get().setSamples(Integer.parseInt(samplesField.getText()));
        SettingsContainer.get().setTime(Double.parseDouble(timeField.getText()));
        SettingsContainer.get().setPopulations(Integer.parseInt(populationsField.getText()));
        SettingsContainer.get().setSpecimens(Integer.parseInt(specimensField.getText()));
        SettingsContainer.get().setRisingTimeLow(Double.parseDouble(risingTimeLowField.getText()));
        SettingsContainer.get().setRisingTimeHigh(Double.parseDouble(risingTimeHighField.getText()));
        SettingsContainer.get().setSettlingMargin(Double.parseDouble(settlingMarginField.getText()));
        SettingsContainer.get().setOvershoot(Double.parseDouble(overshootField.getText()));
        SettingsContainer.get().setRisingTime(Double.parseDouble(risingTime.getText()));
        SettingsContainer.get().setSettlingTime(Double.parseDouble(settlingTimeField.getText()));
        SettingsContainer.get().setA(matrixA.getText());
        SettingsContainer.get().setB(matrixB.getText());
        SettingsContainer.get().setC(matrixC.getText());
        SettingsContainer.get().setD(matrixD.getText());
    }

    public class StartBtnClick implements ActionListener {
        GuiRoot root;
        public StartBtnClick(GuiRoot root) {this.root=root;}

        @Override
        public void actionPerformed(ActionEvent event) {
            root.loadSettings();
            //FIXME: should start when calling some method,
            //       not in constructor
            new Test();
        }
    }
}
