import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CaesarCipher {

    boolean encryptMode = true;

    JFrame window;
    JPanel body; // contains everything

    JPanel titleBox; // contains titles
    JPanel title;
    JLabel titleLabel;

    JPanel contentBox; // contains useful parts
    JPanel inputTextBox;
    JLabel inputLabel;
    JTextArea inputTextArea;

    JPanel optionsPanel;
    JSpinner shiftSpinner;

    private CaesarCipher() {
        window = new JFrame("Caesar Cipher");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.createComponents();
        window.setSize(300, 400);
        Dimension minimum = new Dimension(200, 200);
        window.setMinimumSize(minimum);
        window.setVisible(true);
    }

    private void createComponents() {
        // main window
        body = new JPanel();
        body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));
        window.add(body);

        // titles box
        titleBox = new JPanel();
        BoxLayout titleBoxLayout = new BoxLayout(titleBox, BoxLayout.Y_AXIS);
        titleBox.setLayout(titleBoxLayout);

        // individual titles
        //title = new JLabel("Caesar Cipher");
        title = new JPanel(new FlowLayout());
        titleLabel = new JLabel("Caesar Cipher");
        //titleLabel.setHorizontalAlignment(SwingConstants.LEFT);
        titleLabel.setAlignmentX(FlowLayout.LEFT);
        title.add(titleLabel);

        //subTitle = new JLabel("Yeah idk");

        titleBox.add(title); // adds title to box
        body.add(titleBox); // adds to body

        // content
        contentBox = new JPanel(); // box to contain all encrypt, options and decrypt boxes
        BoxLayout contentBoxLayout = new BoxLayout(contentBox, BoxLayout.Y_AXIS); // basic column
        contentBox.setLayout(contentBoxLayout);

        // top box
        inputTextBox = new JPanel(); // contains label and text area
        BorderLayout inputBoxLayout = new BorderLayout();
        inputTextBox.setLayout(inputBoxLayout);

        inputLabel = new JLabel("Input: ");
        inputTextArea = new JTextArea();

        inputTextBox.add(inputLabel, BorderLayout.NORTH);
        inputTextBox.add(inputTextArea, BorderLayout.CENTER);
        contentBox.add(inputTextBox);

        // options panel
        JPanel middlePanel = new JPanel();
        BoxLayout middlePanelLayout = new BoxLayout(middlePanel, BoxLayout.Y_AXIS);
        middlePanel.setLayout(middlePanelLayout);

        optionsPanel = new JPanel();

        JButton goButton = new JButton("GO!");

        goButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        middlePanel.add(optionsPanel);
        middlePanel.add(goButton);

        contentBox.add(middlePanel);


        // bottom box
        JPanel outputTextBox = new JPanel(); // panel + layout
        BorderLayout outputBoxLayout = new BorderLayout();
        outputTextBox.setLayout(outputBoxLayout);

        JLabel outputLabel = new JLabel("Output: ");
        JTextArea outputTextArea = new JTextArea();
        outputTextArea.setEditable(false); // so it doesnt get edited, purely output

        outputTextBox.add(outputLabel, BorderLayout.NORTH);
        outputTextBox.add(outputTextArea, BorderLayout.CENTER);
        contentBox.add(outputTextBox);

        // options panel content
        // encrypt/decrypt
        ButtonGroup cipherModeButtonGroup = new ButtonGroup();

        JRadioButton encryptButton = new JRadioButton("Encrypt");
        encryptButton.setActionCommand("Encrypt");

        encryptButton.setSelected(true);

        JRadioButton decryptButton = new JRadioButton("Decrypt");
        decryptButton.setActionCommand("Decrypt");

        ActionListener chooseMode = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                encryptMode = e.getActionCommand().equals("Encrypt"); // true if encrypt button pressed
            }
        };
        encryptButton.addActionListener(chooseMode);
        decryptButton.addActionListener(chooseMode);
        goButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int shift = (int) shiftSpinner.getValue();
                String inputText = inputTextArea.getText();
                if (decryptButton.isSelected()) {
                    shift = -shift;
                }
                String output = shiftText(inputText, shift);
                outputTextArea.setText(output);
            }
        });

        cipherModeButtonGroup.add(encryptButton);
        cipherModeButtonGroup.add(decryptButton);

        optionsPanel.add(encryptButton);
        optionsPanel.add(decryptButton);

        // spinner
        SpinnerNumberModel cipherShiftModel = new CipherSpinner(1, 1, 26, 1);

        shiftSpinner = new JSpinner(cipherShiftModel);
        optionsPanel.add(shiftSpinner);


        body.add(contentBox);
    }

    private String shiftText(String input, int shift) {
        input = input.toLowerCase();
        //String output = "";
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            char character = input.charAt(i);
            if(character == ' ') {
                output.append(' ');
                continue;
            }
            int ascii = (int) character;
            ascii += shift;
            if (ascii < 97) {
                ascii += 26;
            }
            if (ascii > 122) {
                ascii -= 26;
            }
            output.append((char) ascii);
        }

        return output.toString();
    }

    public static void main(String[] args) {
        final CaesarCipher program = new CaesarCipher();

    }
}

class CipherSpinner extends SpinnerNumberModel {

    public CipherSpinner(int value, int minimum, int maximum, int stepSize) {
        super(value, minimum, maximum, stepSize);
    }

    @Override
    public Object getPreviousValue() {
        Object l = super.getPreviousValue();
        if (l == null) {
            this.setValue(this.getMaximum());
            l = this.getValue();
        }
        return l;
    }

    @Override
    public Object getNextValue() {
        Object l = super.getNextValue();
        if (l == null) {
            this.setValue(this.getMinimum());
            l = this.getValue();
        }
        return l;
    }
}