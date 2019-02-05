import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class KeywordCipher {

    String alphabet = "abcdefghijklmnopqrstuvwxyz";

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

    private KeywordCipher() {
        window = new JFrame("Keyword Cipher");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.createComponents();
        window.setSize(300, 400);
        Dimension minimum = new Dimension(300, 200);
        window.setMinimumSize(minimum);
        window.setVisible(true);

    }

    @SuppressWarnings("Duplicates") // much of the window building is duplicate from caesarcipher

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
        titleLabel = new JLabel("Keyword Cipher");
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
        System.out.println(optionsPanel.getLayout());

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


        JTextField keywordEntry = new JTextField("Keyword");
        keywordEntry.setColumns(10);


        ActionListener chooseMode = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                encryptMode = e.getActionCommand().equals("Encrypt"); // true if encrypt button pressed
            }
        };
        encryptButton.addActionListener(chooseMode);
        decryptButton.addActionListener(chooseMode);
        goButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String keyword = keywordEntry.getText();
                String inputText = inputTextArea.getText();

                boolean encrypt = true;
                if (decryptButton.isSelected()) {
                    encrypt = false;
                }
                String output = shiftText(inputText, keyword, encrypt);
                outputTextArea.setText(output);
            }
        });

        cipherModeButtonGroup.add(encryptButton);
        cipherModeButtonGroup.add(decryptButton);

        optionsPanel.add(encryptButton);
        optionsPanel.add(decryptButton);
        optionsPanel.add(keywordEntry);

        // spinner


        body.add(contentBox);
    }

    @SuppressWarnings("Duplicates") // trim word and alphabet are duped-ish
    private String getEncryptedAlphabet(String word) {
        word = word.toLowerCase();
        String trimmedWord = "";
        for (int i = 0; i < word.length(); i++) {

            if (trimmedWord.contains(word.substring(i, i + 1))) {
                continue;
            } else {
                trimmedWord += word.charAt(i);
            }
        }
        if (trimmedWord.length() > 26) {
            trimmedWord = trimmedWord.substring(0, 26);
        }

        String encryptedAlphabetString = trimmedWord;
        for (int i = 0; i < alphabet.length(); i++) {
            if (encryptedAlphabetString.contains(alphabet.substring(i, i + 1))) {
                continue;
            } else {
                encryptedAlphabetString += alphabet.charAt(i);
            }
        }
        // some shuffufling
        String shuffled = "";
        int middle = encryptedAlphabetString.length() / 2;
        String first = encryptedAlphabetString.substring(0, middle);
        String last = encryptedAlphabetString.substring(middle);
        encryptedAlphabetString = last + first;

        return encryptedAlphabetString;
    }

    private String shiftText(String input, String word, boolean encryptMode) {
        StringBuilder output = new StringBuilder();
        String encryptedAlphabet = getEncryptedAlphabet(word);
        input = input.toLowerCase();
        String sourceAlphabet;
        String outAlphabet;
        if (encryptMode) {
            sourceAlphabet = alphabet;
            outAlphabet = encryptedAlphabet;
        } else {
            sourceAlphabet = encryptedAlphabet;
            outAlphabet = alphabet;
        }

        char inChar;
        int index;
        char outChar;
        for (int i = 0; i < input.length(); i++) {
            inChar = input.charAt(i);
            String x = "" + inChar;
            if (!sourceAlphabet.contains(x)) {
                output.append(x);
                continue;
            }
            index = sourceAlphabet.indexOf(inChar);
            outChar = outAlphabet.charAt(index);
            output.append(outChar);
        }

        return output.toString();
    }

    public static void main(String[] args) {
        final KeywordCipher program = new KeywordCipher();

    }
}
