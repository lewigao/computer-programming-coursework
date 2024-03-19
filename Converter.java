//CSCI 185 Fall 2023
//Hwan Lee, Lewi Gao, and Richard Martinez-Mejia
//M7 Lab: Develop More Advanced Java GUI
//Wenjia Li
//December 5, 2023

//Importing the Needed Tools
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

//Defining the Converter class and setting up main method
public class Converter {
    public static void main(String[] args) {

        //The Converter will be whatever is in the Constructor being made
        Converter kgLb = new Converter();

    }

    //Defining a Constructor
    public Converter(){
        //Defining the Base of Converter
        JPanel panel = new JPanel();
        JFrame frame = new JFrame("Measurement Unit Converter");

        //Setting up Converter's location, size, way to exit, and layout
        //Whichever components  are added to the panel will also be added to the frame
        frame.setLocation(550,300);
        frame.setSize(275, 225);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        panel.setLayout(new FlowLayout(FlowLayout.CENTER,10,10));

        //creating the components to be used
        JLabel l1 = new JLabel("  Pounds(lbs):   ");
        JTextField tf1 = new JTextField(15);
        JLabel l2 = new JLabel("Kilograms(kgs):");
        JTextField tf2 = new JTextField(15);
        JButton b1 = new JButton("Convert");
        JButton b2 = new JButton("Clear");

        //adding components to panel
        panel.add(l1);
        panel.add(tf1);
        panel.add(l2);
        panel.add(tf2);
        panel.add(b1);
        panel.add(b2);

        //setting all components to be visible
        frame.setVisible(true);

        //Adding Convert Button Functionality
        b1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String input0 = tf1.getText();
                String input1 = tf2.getText();

            //Exception Handling
                try {

                    //For when the user for some reason decides to fill in both text fields.
                    if (input0.length() > 0 && input1.length() > 0) {
                        throw new IllegalArgumentException("ERROR: You've filled both fields.\nPlease clear both fields and use only one field for conversion.");

                        //For when the user does not even bother adding anything in the text fields.
                    } else if (input0.isEmpty() && input1.isEmpty()) {
                        throw new NullPointerException("ERROR: There is no input.\nPlease enter a value in either the Pounds or Kilograms field.");


                        //For when someone inputs a negative value for pounds.
                    } else if (input1.isEmpty()) {
                        double lb = Double.parseDouble(input0);
                        if (lb < 0) {
                            throw new IllegalArgumentException("ERROR: Negative values are not allowed in this converter.\nPlease enter a non-negative value for Pounds.");
                        }
                        double kg = poundToKilo(lb);
                        tf2.setText(String.valueOf(kg));

                        //For when someone inputs a negative value for kilos.
                    } else if (input0.isEmpty()) {
                        double kg = Double.parseDouble(input1);
                        if (kg < 0) {
                            throw new IllegalArgumentException("ERROR: Negative values are not allowed in this converter.\nPlease enter a non-negative value for Kilograms.");
                        }
                        double lb = kiloToPound(kg);
                        tf1.setText(String.valueOf(lb));


                        //If no exceptions are triggered, we can go forth with the conversion!

                        //Converting pounds to kilograms. The Text Field for pounds should be filled, while the field for kilograms should be empty
                    } else if (input1.isEmpty()) {
                        double lb = Double.parseDouble(input0);
                        double kg = poundToKilo(lb);

                        //After inputting a number into the pounds text field, the result should appear on the kilograms text field
                        tf2.setText(String.valueOf(kg));

                        //Converting kilograms to pounds. The Text Field for kilograms should be filled, while the field for pounds should be empty
                    } else if (input0.isEmpty()) {
                        double kg = Double.parseDouble(input1);
                        double lb = kiloToPound(kg);

                        //After inputting a number into the kilograms text field, the result should appear on the pounds text field
                        tf1.setText(String.valueOf(lb));

                    }

                } catch (NumberFormatException ex) {
                    //For when the user inputs something that is invalid, like a letter or a special symbol.
                    JOptionPane.showMessageDialog(frame, "ERROR: You've entered an invalid input.\nPlease enter valid numeric values.");
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(frame, ex.getMessage());
                }
                catch (NullPointerException ex) {
                    JOptionPane.showMessageDialog(frame, ex.getMessage());
                }
            }
        });

        //Adding Clear Button Functionality
        b2.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                tf1.setText("");
                tf2.setText("");
            }
        });
    }

    //The math behind the conversion
    private double poundToKilo(double pounds) {
        return pounds * 0.45359237;
    }

    private double kiloToPound(double kilograms) {
        return kilograms * 2.2046226218;
    }
}