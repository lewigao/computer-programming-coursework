/*
CSCI 185 Fall 2023
Text I/O Lab
Lewi Gao
12.12.23
*/

import java.io.*;
import java.util.Random;
import javax.swing.JOptionPane;

public class IOLab
{
    public static void main(String args[]) throws IOException{
        int[] list = randomGen(10);
        String file = "test.dat";
        
        try {
            writeToFile(file, list);//creates an array of ten random #'s, then writes to file
            int[] readList = readFromFile(file);//read array from a file first
            
            printIntList(readList); //prints the array to console
            JOptionPane.showMessageDialog(null, listToString(readList));
        }
        catch (IOException ioe) {
            JOptionPane.showMessageDialog(null, "There is an input/output exception");
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Just re-do it 💀");
        }
    }
    
    //generates a random amound of numbers based on # you give it
    private static int[] randomGen(int amt){
        Random rand = new Random();
        int[] random = new int[amt];
        
        for (int i = 0; i < amt; i++) {
            random[i] = rand.nextInt(201) - 100;
        }
        
        return random;
    }
    
    private static void writeToFile(String file, int[] list) throws IOException{
        //uses printwriter to write to the file
        try (DataOutputStream output = new DataOutputStream(new FileOutputStream(file))) {
            //loops through the list, recording one int per line
            for (int i = 0; i < list.length; i++) {
                output.writeInt(list[i]);
            }
        }
    }
    
    private static int[] readFromFile(String file) throws IOException{
        //uses bufferedreader to read from the file
        try (DataInputStream input = new DataInputStream(new FileInputStream(file))) {
            int[] list = new int[10];
            String line;
            int i = 0;
            
            //loops through the file while inputs are valid
            while (input.available() != 0) {
                //adds the numbers from file
                list[i] = input.readInt();
                i++;
            }
            return list;
        }
    }
    
    //used for quickly checking the arrays
    private static void printIntList(int[] list) {
        for (int i = 0; i < list.length; i++) {
            System.out.print(list[i] + " ");
        }
        System.out.println("");
    }
    //turns numbers in a list to a string
    private static String listToString(int[] list) {
        String out = "Numbers in the file: ";
        for (int i = 0; i < list.length; i++) {
            out += list[i] + " ";
        }
        return out;
    }
}
