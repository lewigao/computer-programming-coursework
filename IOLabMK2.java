/*
CSCI 185 Fall 2023
Text I/O Lab
Lewi Gao
12.12.23
*/

import java.io.*;
import javax.swing.JOptionPane;

public class IOLabMK2{
    public static void main(String args[]) throws IOException{
        String[] list = {"Rob Ehrmentraut", "Nicki Minaj", "James Charles", "Steve Jobs", "Bill Gates"};
        String file = "testStrings.txt";
        
        //creating new string array to hold data read from file
        String[] readList = new String[list.length];
        
        try {
            writeToFile(list, file);
            readList = readFromFile(file);
            
            printList(readList);
            JOptionPane.showMessageDialog(null, listToString(readList));
            
        }
        catch (IOException ioe) {
            JOptionPane.showMessageDialog(null, "There is an input/output exception");
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Unexpected error, please make sure everything is correct");
        }
    }
    //writes each name to file, the file is NOT readable by us
    private static void writeToFile(String[] list, String file) throws IOException{
        DataOutputStream output = new DataOutputStream(new FileOutputStream(file));
        
        for (int i = 0; i < list.length; i++) {
            output.writeUTF(list[i] + "\n");//we use UTF8 for names
        }
    }
    //reads each name from file
    private static String[] readFromFile(String file) throws IOException{
        DataInputStream input = new DataInputStream(new FileInputStream(file));
        String[] list = new String[5];
        int i = 0;
        
        while (input.available() != 0) {
            list[i] = input.readUTF();//we use UTF8 for names
            i++;
        }
        return list;
    }
    
    //printing the names to console
    private static void printList(String[] list) {
        for (int i = 0; i < list.length; i++) {
            System.out.print(list[i]);
        }
        System.out.println();
    }
    //turns the names into one continuous string
    private static String listToString(String[] list) {
        String out = "Names in the file: \n";
        for (int i = 0; i < list.length; i++) {
            out += list[i];
        }
        return out;
    }
}
