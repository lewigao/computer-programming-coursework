/* 
Program name: Paper, Scissor and Rock Version 0.1
Initially created by Dr. Wenjia Li
Finished by Lewi Gao
11.21.23
*/

import javax.swing.JOptionPane;

public class FirstRPSGame { 
   public static void main(String args[]){ 
      //Variable declaration
      int yourChoice, computerChoice; //initializes player and compuer choices
      String yourInput; //players choice

      //Welcome screen and show the basic rule to the user
      JOptionPane.showMessageDialog(null, "Welcome to Paper, Scissor and Rock");
      JOptionPane.showMessageDialog(null, "Let me remind you the rule first:\nScissor cuts paper, paper covers rock, and rock breaks scissors.");
      JOptionPane.showMessageDialog(null, "\n0: Rock\n1: Paper\n2: Scissor\n");
     
     yourChoice = -1;
     
     try{
     //get your choice
        yourInput = JOptionPane.showInputDialog("Now tell me your choice!");
        yourChoice = Integer.parseInt(yourInput);
     
        if (yourChoice < 0 || yourChoice > 2) {
            JOptionPane.showMessageDialog(null, "Only the ~numbers~ 0, 1, and 2 work.");
        }
     }
        catch (NumberFormatException nf){
        JOptionPane.showMessageDialog(null, "Try answering with a number.");
        }
        catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Weird.");
     }
    

      //Get computer choice
      computerChoice = (int)(Math.random()*10); //generate a random number
      computerChoice %= 3; //Why we need to modulus 3 here? Think about it.
        
      //Compare your Choice with computer’s choice and output the result
      //Case I: Both you and computer pick the same choice, then it’s a tie!
      if (yourChoice == computerChoice){
          JOptionPane.showMessageDialog(null,"It’s a tie!"); 
      }

      //Case II: You WIN :)
      else if ((yourChoice == 0 && computerChoice == 2) || (yourChoice == 1 && computerChoice == 0) || (yourChoice == 2 && computerChoice == 1)){
          JOptionPane.showMessageDialog(null,"You win!"); 
      }

      //Case III: You lose :(
      else if ((computerChoice == 0 && yourChoice == 2) || (computerChoice == 1 && yourChoice == 0) || (computerChoice == 2 && yourChoice == 1)){
          JOptionPane.showMessageDialog(null,"You lose."); 
      }
   }
}