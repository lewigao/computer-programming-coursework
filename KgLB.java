/*
CSCI 185 Fall 2023
GUI Basics Lab
Lewi Gao
12.7.23
*/
import java.awt.GridLayout;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JFrame;
import javax.swing.JButton;

public class KGLB extends JFrame{
    public static void main(String args []) {
        //creates a new frame
        KGLB window = new KGLB();
        
        window.setTitle("Measurement Unit Converter");
        window.setSize(400, 100);
        window.setLocationRelativeTo(null);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
        
    }
    
    public KGLB() {
        setLayout(new GridLayout(3, 1, 5, 1));//3 rows, 1 column
        add(new JLabel("Kilograms: "));
        add(new JTextField(10));
        add(new JLabel("Pounds: "));
        add(new JTextField(10));
        add(new JButton("Convert"));
        add(new JButton("Clear"));
    }
}
