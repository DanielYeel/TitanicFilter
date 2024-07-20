import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyInputs implements KeyListener {
    private JTextField jTextField;
    private int type;
    private JLabel dialogMassage;

    public KeyInputs(JTextField jTextField, int type) {
        this.jTextField = jTextField;
        this.type = type;
        this.dialogMassage = new JLabel();
        this.dialogMassage.setBounds(Constants.WINDOW_WIDTH / 2 - 100, Constants.WINDOW_HEIGHT / 2 - 100, 200, 200);
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {
        if (this.type == Constants.ONLY_LETTER) {
            textLettersValidation();
        } else {
            textDigitsValidation();
        }

    }

    public void textLettersValidation() {
        if (!this.jTextField.getText().matches("[\\D]{1,20}")) {
            if (!this.jTextField.getText().equals("")) {
                showMessage("Only letters, and 20 letters tops!");
            }
        }
    }

    public void textDigitsValidation() {
        if (this.type == Constants.ONLY_DIGIT) {
            if (!this.jTextField.getText().matches("[\\d]")) {
                if (!this.jTextField.getText().equals("")) {
                    showMessage("Only one digit!");
                }
            }
        } else {
            if (!this.jTextField.getText().matches("[\\d]{1,10}")) {
                if (!this.jTextField.getText().equals("")) {
                    showMessage("Only digits, and 10 digits tops");
                }
            }
        }
    }

    private void showMessage(String textMessage){
        this.jTextField.setText("");
        JOptionPane.showMessageDialog(this.dialogMassage,textMessage);
    }

}

