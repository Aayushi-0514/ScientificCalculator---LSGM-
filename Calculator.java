import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class Calculator extends JFrame {
    private final Font BIGGER_FONT = new Font("TimesNewRoman", Font.PLAIN, 20);
    private final JTextField textfield;
    private boolean number = true;
    private String equalOp = "=";
    private final CalculatorOp op = new CalculatorOp();

    public Calculator() {
        textfield = new JTextField("", 12);
        textfield.setHorizontalAlignment(JTextField.RIGHT);
        textfield.setFont(BIGGER_FONT);
        ActionListener numberListener = new NumberListener();
        String buttonOrder = "1234567890 ";
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 4, 4, 4));
        for (int i = 0; i < buttonOrder.length(); i++) {
            String key = buttonOrder.substring(i, i + 1);
            if (key.equals(" ")) {
                buttonPanel.add(new JLabel(""));
            } else {
                JButton button = new JButton(key);
                button.addActionListener(numberListener);
                button.setFont(BIGGER_FONT);
                buttonPanel.add(button);
            }
        }
        ActionListener operatorListener = new OperatorListener();
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 4, 4, 4));
        String[] opOrder = { "+", "-", "*", "/", "=", "C", "sin", "cos", "log" };
        for (String opOrder1 : opOrder) {
            JButton button = new JButton(opOrder1);
            button.addActionListener(operatorListener);
            button.setFont(BIGGER_FONT);
            panel.add(button);
        }
        JPanel pan = new JPanel();
        pan.setLayout(new BorderLayout(4, 4));
        pan.add(textfield, BorderLayout.NORTH);
        pan.add(buttonPanel, BorderLayout.CENTER);
        pan.add(panel, BorderLayout.EAST);
        this.setContentPane(pan);
        this.pack();
        this.setTitle("Calculator");
        this.setResizable(false);
    }

    private void action() {
        number = true;
        textfield.setText("");
        equalOp = "=";
        op.setTotal("");
    }

    public JTextField getTextfield() {
        return textfield;
    }

    class OperatorListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String displayText = textfield.getText();
            switch (e.getActionCommand()) {
                case "sin" -> textfield.setText("" + Math.sin(Double.parseDouble(displayText)));
                case "cos" -> textfield.setText("" + Math.cos(Double.parseDouble(displayText)));
                case "log" -> textfield.setText("" + Math.log(Double.parseDouble(displayText)));
                case "C" -> textfield.setText("");
                default -> {
                    if (number) {
                        
                        action();
                        textfield.setText("");
                        
                    } else {
                        number = true;
                        switch (equalOp) {
                            case "=" -> op.setTotal(displayText);
                            case "+" -> op.add(displayText);
                            case "-" -> op.subtract(displayText);
                            case "*" -> op.multiply(displayText);
                            case "/" -> op.divide(displayText);
                            default -> {
                            }
                        }
                        
                        textfield.setText("" + op.getTotalString());
                        equalOp = e.getActionCommand();
                    }
                }
            }
        }
    }

    class NumberListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            String digit = event.getActionCommand();
            if (number) {
                textfield.setText(digit);
                number = false;
            } else {
                textfield.setText(textfield.getText() + digit);
            }
        }
    }

    public class CalculatorOp {
        private int total;

        public CalculatorOp() {
            total = 0;
        }

        public String getTotalString() {
            return "" + total;
        }

        public void setTotal(String n) {
            total = convertToNumber(n);
        }

        public void add(String n) {
            total += convertToNumber(n);
        }

        public void subtract(String n) {
            total -= convertToNumber(n);
        }

        public void multiply(String n) {
            total *= convertToNumber(n);
        }

        public void divide(String n) {
            total /= convertToNumber(n);
        }

        private int convertToNumber(String n) {
            return Integer.parseInt(n);
        }
    }
}

class SwingCalculator {
    public static void main(String[] args) {
        JFrame frame = new Calculator();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
}
}
