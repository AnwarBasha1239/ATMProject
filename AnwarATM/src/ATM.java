import java.awt.*;

import java.awt.event.*;

import javax.swing.*;
import javax.swing.text.*;
import javax.swing.event.*;

public class ATM {
public void init() {
add(new ATMPanel());
}

private void add(ATMPanel atmPanel) {
	// TODO Auto-generated method stub
	
}

static class ATMPanel extends Panel {
// setting up a pin that is needed for the atm
private static String PIN= "5803";
int accountmoney = 5000000;
//constants that provide state information for the atm
private static final int WAIT_FOR_CARD = 1;
private static final int WAIT_FOR_PIN = 2;
private static final int MENU_DISPLAYED = 3;
private static final int TRANSFER_MONEY = 4;
private static final int DISBURSE_MONEY = 5;
private static final int CHECK_BALANCE = 6;
private static final int SAVING_ACC = 7;
private TextArea mScreen= new TextArea("", 6, 45, TextArea.SCROLLBARS_NONE);
// some buttons
Button mEnter= new Button("Enter");
Button mClear= new Button("Clear");
Button mCancel= new Button("Cancel");
JButton Receipt = new JButton("Receipts Slot");
Button Quit = new Button("Quit");
JRadioButton Cash = new JRadioButton("Yes");
JRadioButton Cash1 = new JRadioButton("No");

private JLabel something = new JLabel("Yeah");
private String mPin= "";
private String Transfer= "";
private int Transfer1;
private int state = WAIT_FOR_CARD; // the current state of the ATM

public ATMPanel() {
setLayout(new BorderLayout());
mScreen.setEditable(false);
ActionListener keyPadListener = new ActionListener(){
public void actionPerformed(ActionEvent e){
key(Integer.parseInt(((Button) e.getSource()).getLabel()));
}
};

// create keypad with 10 buttons

JPanel keypad= new JPanel();
for(int i=1; i<10; i++) {
Button btn= new Button(String.valueOf(i));
btn.addActionListener(keyPadListener);
keypad.add(btn);
}

keypad.add(new Label());
Button btn= new Button("0");
btn.addActionListener(keyPadListener);
keypad.add(btn);



// what to do when the enter button is clicked
mEnter.addActionListener(new ActionListener() {
public void actionPerformed(ActionEvent e) {
// call the method enter() when enter is pressed.
enter();
}
});

// see enter() - same idea
mClear.addActionListener(new ActionListener() {
public void actionPerformed(ActionEvent e) {
clear();
}
});
// same idea as enter()
mCancel.addActionListener(new ActionListener() {
public void actionPerformed(ActionEvent e){
cancel();
}
});
// same idea as enter()
Receipt.addActionListener(new ActionListener() {
public void actionPerformed(ActionEvent e){
receipt();
}
});
// same idea as enter()
Quit.addActionListener(new ActionListener() {
public void actionPerformed(ActionEvent e) {
quit();
}
});
// same idea as enter()
Cash.addActionListener(new ActionListener() {
public void actionPerformed(ActionEvent e) {
cash();
}
});

// add enter, clear, receeipt, quit, and cancel buttons to panel

Panel controls= new Panel();
controls.add(mEnter);
controls.add(mClear);
controls.add(mCancel);
controls.add(Receipt);
controls.add(Quit);
controls.add(Cash);
controls.add(Cash1);

add("North", mScreen);
add("Center", keypad);
add("South", controls);

mScreen.setText("Enter your card and press a number key.");
}



private void key(int key) {
switch (state) {
case WAIT_FOR_CARD: // waiting for card
clear();
mScreen.setText(
"Card accepted.\n" +
"Welcome to ICICI Bank.\n" +
"Please enter your pin...");
state = WAIT_FOR_PIN;
break;
case WAIT_FOR_PIN: // waiting for pin

// accept number as part of pin

if (mPin.length() == 0)
mScreen.setText("");
mPin += String.valueOf(key);
mScreen.setText(mScreen.getText() +"*");
break;
case MENU_DISPLAYED:

// treat number as menu selection

switch(key){
case 1:
TransMoney();
break;
case 2:
DispenseMoney();
break;
case 3:
CheckBalance();
break;
case 4:
DispenseMoney();
break;
default:
InvalidOption();
}
break;
case TRANSFER_MONEY:
// treat number as part of amount to transfer

if(Transfer.length() == 0)
mScreen.setText("");
Transfer += String.valueOf(key);
mScreen.setText(mScreen.getText() + key );

break;
case DISBURSE_MONEY:
// treat number as part of amount to disburse
if(Transfer.length() == 0)
mScreen.setText("");
Transfer += String.valueOf(key);
mScreen.setText(mScreen.getText() + key );

break;
case CHECK_BALANCE:
break;
}
}




private void enter(){
if (state == WAIT_FOR_CARD)
return;

if (state == WAIT_FOR_PIN){
// check pin

if(mPin.equals(PIN)){
// pin correct, display menu
menu();
}
else {
// pin incorrect, display message
clear();
mScreen.setText("Invalid pin, try again (it's " +PIN +")");
}
}

if (state == TRANSFER_MONEY) {
// perform transfer

Transfer1 = Integer.parseInt(Transfer);
accountmoney += Transfer1;

Transfer = "";
Transfer1 = 0;
menu();
}

if (state == DISBURSE_MONEY) {
// perform disburse

Transfer1 = Integer.parseInt(Transfer);
accountmoney -= Transfer1;

Transfer = "";
Transfer1 = 0;
menu();
}
}

// called (by action listener) whenever clear button is pressed

private void clear() {
if (state == WAIT_FOR_CARD)
return;

if (state == WAIT_FOR_PIN) {
mScreen.setText("");
mPin= "";
}
}

// called (by action listener) whenever cancel button is pressed
private void cancel() {
menu();
}

private void menu() {
// display menu
state = MENU_DISPLAYED;
clear();
mScreen.setText(
"1. Transfer money\n" +
"2. WITHDRAWAL money\n" +
"3. Check balance\n"+
"4. SAVING");
}




// called (by action listener) whenever receipt button is pressed

private void receipt() {
// display popuup
JOptionPane.showMessageDialog(null, "successfully withdrawal.", "Inane warning",
JOptionPane.WARNING_MESSAGE);

}

// called (by action listener) whenever cash button is pressed
private void cash(){
JOptionPane.showMessageDialog(null, "Transfer your many.", "Inane warning",
JOptionPane.WARNING_MESSAGE);
}

private void quit() {
// quit application
System.exit(0);
}

private void TransMoney() {
clear();
mScreen.setText("How much money would you like to transfer? ...");
state = TRANSFER_MONEY;
}

private void DispenseMoney() {
clear();
mScreen.setText("How much money would you like to take out? ...");

if (Transfer1 > accountmoney) {
mScreen.setText("You can't have more money than you already have");
}

state = DISBURSE_MONEY;
}

private void CheckBalance() {
state = CHECK_BALANCE;
clear();
mScreen.setText("This is the amount of money u have in your account.\n" +
" ---> " + accountmoney);
}

private void InvalidOption() {
clear();
mScreen.setText("You have choose the incorrect option. Try again");
}
}

// the main method that creates a frame, and adds your panel to it so that you can see it.
public static void main(String[] argv) {
Frame frame= new Frame("Simple ATM Example");
frame.add(new ATMPanel());
frame.setSize(500,400);
frame.setResizable(true);
frame.setVisible(true);
frame.addWindowListener(new WindowAdapter() {
public void windowClosing(WindowEvent e) {
System.exit(0);
}
});
}
}