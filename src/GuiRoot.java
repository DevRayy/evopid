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
    private JTextArea textArea1;
    private JTextArea textArea2;
    private JTextArea textArea3;
    private JTextArea textArea4;
    private JTextArea logArea;
    private JButton loadButton;
    private JButton saveButton;

    public GuiRoot() {
    }

    private class StartButtonClicked implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Test t = new Test();
        }
    }
}
