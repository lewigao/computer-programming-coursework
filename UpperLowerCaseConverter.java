/*
CSCI 185 Fall 2023
GUI Basics Lab
Lewi Gao
12.5.23
*/
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JFrame;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UpperLowerCaseConverter extends JFrame implements ActionListener{
    private JTextField input;
    private JTextField output;
    private String inputText;
    
    public static void main(String args[]) {
        UpperLowerCaseConverter frame = new UpperLowerCaseConverter();
        
        frame.setTitle("Upper Lower Case Converter");
        frame.setSize(800, 100);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }
    public UpperLowerCaseConverter() {
        //sets the layout
        setLayout(new GridLayout(2, 2, 5, 5));
        input = new JTextField(10);
        output = new JTextField(10);
        
        //adds components
        add(new JLabel("Enter a string: "));
        add(input);
        add(new JLabel("Output: "));
        add(output);
        
        //makes it so output can't be edited by user
        output.setEditable(false);
        output.setText("^ enter only strings of letters, then press enter ^");
        
        input.addActionListener(this);
        input.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                String outputText;
                
                inputText = input.getText();
                //convert user text to upper/lower case
                Convert c = new Convert(inputText);
                outputText = c.getConversion();
                output.setText(outputText);
            }
        });
    }
    public void actionPerformed(ActionEvent event){
        //gets text from the user
        String outputText;
        
        inputText = input.getText();
        //convert user text to upper/lower case
        Convert c = new Convert(inputText);
        outputText = c.getConversion();
        output.setText(outputText);
    }
}
class Convert{
    //private data fields
    private String input;
    private String output;
    //constructors
    public Convert(String s){
        input = s;
    }
    public String getConversion(){
        char[] storage = new char[input.length()];
        //crawling through the string and converting each character
        for (int i = 0; i < input.length(); i++) {
            if (Character.isUpperCase(input.charAt(i))) {
                storage[i] = Character.toLowerCase(input.charAt(i));
            }
            else if (Character.isLowerCase(input.charAt(i))){
                storage[i] = Character.toUpperCase(input.charAt(i));
            }
            else if (Character.isWhitespace(input.charAt(i))){
                storage[i] = ' ';//keeps spaces in the output
            }
        }
        //turns the array of chars into one string
        output = new String(storage);
        return output;
    }
    //getters
    public String getOutput(){
        return output;
    }
}