package ee;

import com.sun.xml.internal.ws.api.config.management.policy.ManagementAssertion;
import org.codehaus.jackson.map.ObjectMapper;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Set;


public class GuiRoot {
    public Evopid evopid;
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
    private JProgressBar progressBar;
    private JCheckBox limitOvershootCheckbox;
    private JTextField limitOvershootField;

    public GuiRoot(Evopid evopid) {
        this.evopid = evopid;
        startButton.addActionListener(new StartBtnClick(this));
        saveButton.addActionListener(new SaveBtnClick(this));
        loadButton.addActionListener(new LoadBtnClick(this));
        progressBar.setMinimum(0);
        progressBar.setValue(0);
        JTextAreaOutputStream out = new JTextAreaOutputStream (logArea);
        System.setOut (new PrintStream(out, true));
    }

    public void getSettingsFromGui() {
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
        SettingsContainer.get().setLimitOvershootValue(Double.parseDouble(limitOvershootField.getText()));
        SettingsContainer.get().setLimitOvershootEnabled(limitOvershootCheckbox.isSelected());

        SettingsContainer.get().setAstring(matrixA.getText());
        SettingsContainer.get().setBstring(matrixB.getText());
        SettingsContainer.get().setCstring(matrixC.getText());
        SettingsContainer.get().setDstring(matrixD.getText());
        progressBar.setMaximum(SettingsContainer.get().getPopulations()*SettingsContainer.get().getSpecimens());
    }

    public void incrementProgressBar() {
        int value = progressBar.getValue();
        progressBar.setValue(++value);
    }

    public void setGuiFromSettings() {
        SettingsContainer sc = SettingsContainer.get();
        samplesField.setText(sc.getSamples()+"");
        timeField.setText(sc.getTime()+"");
        populationsField.setText(sc.getPopulations()+"");
        specimensField.setText(sc.getSpecimens()+"");
        risingTimeLowField.setText(sc.getRisingTimeLow()+"");
        risingTimeHighField.setText(sc.getRisingTimeHigh()+"");
        settlingMarginField.setText(sc.getSettlingMargin()+"");
        overshootField.setText(sc.getOvershoot()+"");
        risingTime.setText(sc.getRisingTime()+"");
        settlingTimeField.setText(sc.getSettlingTime()+"");
        limitOvershootField.setText(sc.getLimitOvershootValue()+"");
        limitOvershootCheckbox.setSelected(sc.isLimitOvershootEnabled());
        matrixA.setText(sc.getAstring());
        matrixB.setText(sc.getBstring());
        matrixC.setText(sc.getCstring());
        matrixD.setText(sc.getDstring());
        progressBar.setMaximum(SettingsContainer.get().getPopulations()*SettingsContainer.get().getSpecimens());
    }

    public class StartBtnClick implements ActionListener {
        GuiRoot root;
        public StartBtnClick(GuiRoot root) {this.root=root;}

        public void actionPerformed(ActionEvent event) {
            root.getSettingsFromGui();
            root.progressBar.setValue(0);
            Thread t = new Thread(root.evopid);
            t.start();
        }
    }

    public class SaveBtnClick implements ActionListener {
        GuiRoot root;
        public SaveBtnClick(GuiRoot root) {this.root=root;}

        public void actionPerformed(ActionEvent event) {
            root.getSettingsFromGui();
            root.saveSettingsToJSON();
        }
    }

    public class LoadBtnClick implements ActionListener {
        GuiRoot root;
        public LoadBtnClick(GuiRoot root) {this.root=root;}

        public void actionPerformed(ActionEvent event) {
            root.getSettingsFromJSON();
        }
    }

    public void getSettingsFromJSON() {
        JFileChooser c = new JFileChooser();
        c.setSelectedFile(new File("evopid_settings.json"));
        c.setFileFilter(new FileNameExtensionFilter("JSON file","json"));
        if (c.showOpenDialog(rootPanel) == JFileChooser.APPROVE_OPTION) {
            try {
                File file = c.getSelectedFile();
                ObjectMapper mapper = new ObjectMapper();
                SettingsContainer.set(mapper.readValue(file, SettingsContainer.class));
                setGuiFromSettings();
                System.out.println("File " + file.getPath() + " successfully loaded");
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Unable to load file " + c.getSelectedFile().getPath());
            }
        }
    }

    public void saveSettingsToJSON() {
        JFileChooser c = new JFileChooser();
        c.setSelectedFile(new File("evopid_settings.json"));
        c.setFileFilter(new FileNameExtensionFilter("JSON file","json"));
        String filename = "";
        String directory = "";
        // Demonstrate "Save" dialog:
        int rVal = c.showSaveDialog(rootPanel);
        if (rVal == JFileChooser.APPROVE_OPTION) {
            filename  = c.getSelectedFile().getName();
            directory = c.getCurrentDirectory().toString();

            try {
                ObjectMapper mapper = new ObjectMapper();
                mapper.writeValue(new FileOutputStream(directory + File.separator + filename), SettingsContainer.get());
                System.out.println("Settings file saved to " + directory + File.separator + filename);
            } catch (IOException e) {
                System.out.println("Unable to save file");
            }
        }

    }

    public class JTextAreaOutputStream extends OutputStream {
        private final JTextArea destination;

        public JTextAreaOutputStream(JTextArea destination) {
            if (destination == null)
                throw new IllegalArgumentException("Destination is null");

            this.destination = destination;
        }

        @Override
        public void write(byte[] buffer, int offset, int length) throws IOException {
            final String text = new String(buffer, offset, length);
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    destination.append(text);
                }
            });
        }

        @Override
        public void write(int b) throws IOException {
            write(new byte[]{(byte) b}, 0, 1);
        }
    }

}
