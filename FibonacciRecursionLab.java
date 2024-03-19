/*
CSCI 185 Fall 2023
Recursion: Fibonacci Numbers
Lewi Gao
12.19.23
*/

import javax.swing.JOptionPane;

public class FibonacciRecursionLab
{
    public static void main(String args[]){
        String userIn = JOptionPane.showInputDialog(null, "Pick a number for Fibonacci Sequence.");
        int userNum = Integer.parseInt(userIn);
        
        JOptionPane.showMessageDialog(null, (FibonacciSeq(userNum)));
    }
    
    //starting step, ONLY works off user input
    public static int FibonacciSeq(int count) {
        int start = 1;  //starting number
        int fibNum = 1; //this is the value of the last number
        int fibSum = 0; //we return this value
        
        //0-2 are our base cases, these are simple and shouldn't be computed
        if (count == 0) {
            return 0;
        }
        //includes 2 because it's not worth it to run/doesn't even trigger the (other)else
        else if (count == 1 || count == 2) {
            return 1;
        }
        //returning the current count (- 3 for 3 base cases), sum and previous value
        else { 
            return FibonacciSeq(count - 3, 2, 1);
        }
    }
    //actually calculates after the first step, user has no control here
    public static int FibonacciSeq(int count, int fibSum, int fibNum) {
        if (count == 0) {
            return fibSum;
        }
        else {
            int newSum; //need int for new sum
            newSum = fibNum + fibSum;
            fibNum = fibSum;
                        //otherwise returning fibSum returns a 2
            return FibonacciSeq(count - 1, newSum, fibNum);
        }
    }
}
