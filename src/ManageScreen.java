import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class ManageScreen extends JPanel {
    private JComboBox<String> classPComboBox;
    private JComboBox<String> genderComboBox;
    private JComboBox<String> embarkedComboBox;
    private JTextField passengerName;
    private JTextField cabin;
    private JTextField passengerNumMin;
    private JTextField passengerNumMax;
    private JTextField sibSp;
    private JTextField  parCh;
    private JTextField ticketCostMin;
    private JTextField ticketCostMax;
    private JTextField ticketNumber;
    private JButton filter;
    private JButton statistics;


    public ManageScreen(int x, int y, int width, int height) {
        File file = new File(Constants.PATH_TO_DATA_FILE);
        if (file.exists()) {
            this.setLayout(null);
            this.setBounds(x, y + Constants.MARGIN_FROM_TOP, width, height);
            this.setFocusable(true);
            this.requestFocus(true);

            createJTextField();
            createJLabel();
            createJComboBox();
            createJButton();
        }
    }


    private void createJButton() {
        AdditionsToScreen additionsToScreen = new AdditionsToScreen();
        this.filter = new JButton("Filter");
        this.filter.setBounds(this.ticketCostMin.getX(),this.ticketCostMin.getY()+Constants.SPACE*3,Constants.WIDTH_BUTTON,Constants.HEIGHT_BUTTON);
        this.add(this.filter);
        this.filter.setBackground(Color.white);
        this.filter.addActionListener(e -> {
            JLabel dialogMassage=new JLabel();
            JOptionPane.showMessageDialog(dialogMassage,additionsToScreen.filter(this.classPComboBox.getItemAt(this.classPComboBox.getSelectedIndex()),this.genderComboBox.getItemAt(this.genderComboBox.getSelectedIndex())
                    ,this.embarkedComboBox.getItemAt(this.embarkedComboBox.getSelectedIndex()),this.passengerName.getText(),this.ticketNumber.getText(),this.cabin.getText(),
                    this.passengerNumMin.getText(),this.passengerNumMax.getText(),this.sibSp.getText(),this.ticketCostMin.getText()
                    ,this.ticketCostMax.getText(),this.parCh.getText()));


        });
        this.statistics = new JButton("Statistics");
        this.statistics.setBounds(this.ticketCostMin.getX(),this.parCh.getY()+Constants.SPACE*6,Constants.WIDTH_BUTTON,Constants.HEIGHT_BUTTON);
        this.add(this.statistics);
        this.statistics.setBackground(Color.white);
        this.statistics.addActionListener(e -> {
            additionsToScreen.statistics();
            this.statistics.setText("Open File");
            this.statistics.addActionListener(e1 -> {
                try {
                    Desktop.getDesktop().open(new File("statistic.txt"));
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });
        });
    }

    private void createJComboBox(){
        JLabel classPLabel = new JLabel("Passenger Class: ");
        classPLabel.setForeground(Color.black);
        classPLabel.setBounds(this.getX() + Constants.MARGIN_FROM_LEFT, this.getY(), Constants.LABEL_WIDTH+10, Constants.LABEL_HEIGHT);
        this.add(classPLabel);

        this.classPComboBox = new JComboBox<>(Constants.PASSENGER_CLASS_OPTIONS);
        this.classPComboBox.setBounds(classPLabel.getX() + classPLabel.getWidth() + 1, classPLabel.getY(), Constants.COMBO_BOX_WIDTH, Constants.COMBO_BOX_HEIGHT);
        this.add(this.classPComboBox);

        this.genderComboBox = new JComboBox<>(Constants.GENDER);
        this.genderComboBox.setBounds(this.classPComboBox.getX(),this.classPComboBox.getY()+this.classPComboBox.getHeight()+10,Constants.COMBO_BOX_WIDTH, Constants.COMBO_BOX_HEIGHT);
        this.add(this.genderComboBox);

        JLabel genderLabel = new JLabel("Gender Class: ");
        genderLabel.setForeground(Color.black);
        genderLabel.setBounds(this.getX() + Constants.MARGIN_FROM_LEFT, this.genderComboBox.getY(), Constants.LABEL_WIDTH+10, Constants.LABEL_HEIGHT);
        this.add(genderLabel);

        this.embarkedComboBox  =new JComboBox<>(Constants.EMBARKED);
        this.embarkedComboBox.setBounds(this.genderComboBox.getX(),this.genderComboBox.getY()+this.genderComboBox.getHeight()+10,Constants.COMBO_BOX_WIDTH,Constants.COMBO_BOX_HEIGHT);
        this.add(this.embarkedComboBox);

        JLabel embarkedLabel = new JLabel("Embarked Class: ");
        embarkedLabel.setForeground(Color.black);
        embarkedLabel.setBounds(this.getX() + Constants.MARGIN_FROM_LEFT, this.embarkedComboBox.getY(), Constants.LABEL_WIDTH+10, Constants.LABEL_HEIGHT);
        this.add(embarkedLabel);
    }

    private void createJTextField(){
        this.passengerName = new JTextField();
        this.passengerName.setBounds(Constants.X_LABEL,Constants.Y_LABEL,Constants.LABEL_WIDTH,Constants.LABEL_HEIGHT);
        this.add(this.passengerName);
        this.passengerName.addKeyListener(new KeyInputs(this.passengerName,Constants.ONLY_LETTER));

        this.ticketNumber = new JTextField();
        this.ticketNumber.setBounds(this.passengerName.getX()+this.passengerName.getWidth()+Constants.SPACE, this.passengerName.getY(), Constants.LABEL_WIDTH, Constants.LABEL_HEIGHT);
        this.add(this.ticketNumber);
        this.ticketNumber.addKeyListener(new KeyInputs(this.ticketNumber,Constants.ONLY_DIGIT));

        this.cabin = new JTextField();
        this.cabin.setBounds(this.ticketNumber.getX()+this.ticketNumber.getWidth()+Constants.SPACE,this.ticketNumber.getY(),Constants.LABEL_WIDTH,Constants.LABEL_HEIGHT);
        this.add(this.cabin);
        this.cabin.addKeyListener(new KeyInputs(this.cabin,Constants.ONLY_DIGIT));

        this.passengerNumMin = new JTextField();
        this.passengerNumMin.setBounds(this.passengerName.getX(),this.ticketNumber.getY()+Constants.J_TEXT_BOX_HEIGHT+Constants.SPACE,Constants.LABEL_WIDTH,Constants.LABEL_HEIGHT);
        this.add(this.passengerNumMin);
        this.passengerNumMin.addKeyListener(new KeyInputs(this.passengerNumMin ,Constants.DIGITS));

        this.passengerNumMax = new JTextField();
        this.passengerNumMax.setBounds( this.passengerNumMin.getX()+ this.passengerNumMin.getWidth()+Constants.SPACE, this.passengerNumMin.getY(),Constants.LABEL_WIDTH,Constants.LABEL_HEIGHT);
        this.add(this.passengerNumMax);
        this.passengerNumMax.addKeyListener(new KeyInputs(this.passengerNumMax,Constants.DIGITS));

        this.sibSp = new JTextField();
        this.sibSp.setBounds(this.passengerNumMax.getX()+ this.passengerNumMax.getWidth()+Constants.SPACE, this.passengerNumMax.getY(),Constants.LABEL_WIDTH,Constants.LABEL_HEIGHT);
        this.add(this.sibSp);
        this.sibSp.addKeyListener(new KeyInputs(this.sibSp,Constants.ONLY_DIGIT));

        this.ticketCostMin = new JTextField();
        this.ticketCostMin.setBounds( this.passengerName.getX(), this.passengerNumMin.getY()+Constants.J_TEXT_BOX_HEIGHT+Constants.SPACE,Constants.LABEL_WIDTH,Constants.LABEL_HEIGHT);
        this.add(this.ticketCostMin);
        this.ticketCostMin.addKeyListener(new KeyInputs(this.ticketCostMin,Constants.DIGITS));

        this.ticketCostMax = new JTextField();
        this.ticketCostMax.setBounds(this.ticketCostMin.getX() + this.ticketCostMin.getWidth()+Constants.SPACE, this.ticketCostMin.getY(),Constants.LABEL_WIDTH,Constants.LABEL_HEIGHT);
        this.add(this.ticketCostMax);
        this.ticketCostMax.addKeyListener(new KeyInputs(this.ticketCostMax,Constants.DIGITS));

        this.parCh = new JTextField();
        this.parCh.setBounds(this.ticketCostMax.getX()+ this.ticketCostMax.getWidth()+Constants.SPACE, this.ticketCostMax.getY(),Constants.LABEL_WIDTH,Constants.LABEL_HEIGHT);
        this.add(this.parCh);
        this.parCh.addKeyListener(new KeyInputs(this.parCh,Constants.ONLY_DIGIT));

    }

    private void createJLabel(){
        JLabel passengerNameLabel = new JLabel("Passenger Name");
        passengerNameLabel.setBounds(this.passengerName.getX(),this.passengerName.getY()-Constants.Y_LABEL_SPACE, this.passengerName.getWidth(),this.passengerName.getHeight());
        passengerNameLabel.setForeground(Color.black);
        this.add(passengerNameLabel);

        JLabel ticketLabel = new JLabel("Ticket Number");
        ticketLabel.setBounds(this.ticketNumber.getX(),this.ticketNumber.getY()-Constants.Y_LABEL_SPACE,this.ticketNumber.getWidth(),this.ticketNumber.getHeight());
        ticketLabel.setForeground(Color.black);
        this.add(ticketLabel);

        JLabel cabinLabel = new JLabel("Cabin");
        cabinLabel.setBounds(this.cabin.getX(),this.cabin.getY()-Constants.Y_LABEL_SPACE,this.cabin.getWidth(),this.ticketNumber.getHeight());
        cabinLabel.setForeground(Color.black);
        this.add(cabinLabel);

        JLabel sibSpLabel = new JLabel("Sibling");
        sibSpLabel.setBounds(this.sibSp.getX(),this.sibSp.getY()-Constants.Y_LABEL_SPACE, this.sibSp.getWidth(),this.sibSp.getHeight());
        sibSpLabel.setForeground(Color.black);
        this.add(sibSpLabel);

        JLabel parChLabel = new JLabel("parCh");
        parChLabel.setBounds(this.parCh.getX(),this.parCh.getY()-Constants.Y_LABEL_SPACE,this.parCh.getWidth(),this.parCh.getHeight());
        parChLabel.setForeground(Color.black);
        this.add(parChLabel);

        JLabel passengerNumMinLabel = new JLabel("PN Min");
        passengerNumMinLabel.setBounds(this.passengerNumMin.getX(),this.passengerNumMin.getY()-Constants.Y_LABEL_SPACE,this.passengerNumMin.getWidth(),this.passengerNumMin.getHeight());
        passengerNumMinLabel.setForeground(Color.black);
        this.add(passengerNumMinLabel);
        passengerNumMinLabel.setToolTipText("Passenger Number Minimum");

        JLabel passengerNumMaxLabel= new JLabel("PN Max");
        passengerNumMaxLabel.setBounds(this.passengerNumMax.getX(),this.passengerNumMax.getY()-Constants.Y_LABEL_SPACE, this.passengerNumMax.getWidth(), this.passengerNumMax.getHeight());
        passengerNumMaxLabel.setForeground(Color.black);
        this.add(passengerNumMaxLabel);
        passengerNumMaxLabel.setToolTipText("Passenger Number Maximum");


        JLabel ticketCostMinLabel= new JLabel("Ticket Cost Min");
        ticketCostMinLabel.setBounds(this.ticketCostMin.getX(),this.ticketCostMin.getY()-Constants.Y_LABEL_SPACE,this.ticketCostMin.getWidth(),this.ticketCostMin.getHeight());
        ticketCostMinLabel.setForeground(Color.black);
        this.add(ticketCostMinLabel);

        JLabel ticketCostMaxLabel = new JLabel("Ticket Cost Max");
        ticketCostMaxLabel.setBounds(this.ticketCostMax.getX(), this.ticketCostMax.getY()-Constants.Y_LABEL_SPACE, this.ticketCostMax.getWidth(),this.ticketCostMax.getHeight());
        ticketCostMaxLabel.setForeground(Color.black);
        this.add(ticketCostMaxLabel);
    }


}
