/*
CSCI 185 Fall 2023
Exception class lab
Lewi Gao
11.30.23
*/

import javax.swing.JOptionPane;

public class ExceptionsLab{
    public static void main(String args[]) {
        BankAcc acc1;
        String name, initBal, dep, wit;
        double balance, deposit, withdraw;
        
        name = JOptionPane.showInputDialog("What's your name?");
        initBal = JOptionPane.showInputDialog("Initial deposit amount: ");
        
        //JOptionPane.showMessageDialog(null, name);
        
        try{
            balance = Integer.parseInt(initBal);
            acc1 = new BankAcc(name, balance);
            
            if (balance == 0){
                throw new ZeroBalanceException("Can not start off with balance of 0.");
            }
            else if (name.length() == 0 && initBal.length() == 0){
                throw new Exception();
            }
            else if (name.length() == 0){
                throw new NoNameException("Please input a name.");
            }
            else if (balance < 0){
                throw new NegativeBalanceException("Can not start off with balance that's less than 0.");
            }
            
            //shows balance
            JOptionPane.showMessageDialog(null, acc1.toString());
            
            dep = JOptionPane.showInputDialog("Deposit an amount: ");
            deposit = Integer.parseInt(dep);
            //trying to deposit
            acc1.deposit(deposit);
            
            
            wit = JOptionPane.showInputDialog("Withdraw an amount: ");
            withdraw = Integer.parseInt(wit);
            //trying to withdraw
            acc1.withdraw(withdraw);
            
            //shows final balance
            JOptionPane.showMessageDialog(null, acc1.toString());
        }
        catch (ZeroBalanceException zb){
            JOptionPane.showMessageDialog(null, zb.getMessage());
        }
        catch (NegativeBalanceException nb) {
            JOptionPane.showMessageDialog(null, nb.getMessage());
        }
        catch (NoNameException nn){
            JOptionPane.showMessageDialog(null, nn.getMessage());
        }
        catch (NegativeDecrementException nd){
            JOptionPane.showMessageDialog(null, nd.getMessage());
        }
        catch (NegativeIncrementException ni){
            JOptionPane.showMessageDialog(null, ni.getMessage());
        }
        catch (NoChangeException nc) {
            JOptionPane.showMessageDialog(null, nc.getMessage());
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Please try again and enter ALL fields.");
        }
    }
}

class BankAcc{
    //private data fields
    private double balance;
    private String name;
    
    //fully loaded constructor
    public BankAcc(String name, double initial){
        this.name = name;
        balance = initial;
    }
    
    //depositing funds
    public void deposit(double d) throws NegativeIncrementException, NegativeBalanceException, NoChangeException{
        if (d < 0){
            throw new NegativeIncrementException();
        }
        else if(d == 0) {
            throw new NoChangeException();
        }
        
        balance += d;
    }
    
    //withdrawing funds
    public void withdraw(double w) throws NegativeDecrementException, NegativeBalanceException, NoChangeException{
        if (w < 0) {
            throw new NegativeDecrementException();
        }
        else if ((balance - w) < 0){
            throw new NegativeBalanceException();
        }
        else if(w == 0) {
            throw new NoChangeException();
        }
        
        balance -= w;
    }
    
    //setters
    public void setBal(double b){
        balance = b;
    }
    
    public void setName(String n){
        name = n;
    }
    
    //getters
    public double getBal(){
        return balance;
    }
    
    public String getName(){
        return name;
    }
    
    //toString
    public String toString(){
        String output = "Name: " + getName();
        output += "\nBalance: " + getBal();
        
        return output;
    }
}

//exceptions
    //balance < 0 
class NegativeBalanceException extends Exception{
    public NegativeBalanceException(String message){
        super(message);
    }
    
    public NegativeBalanceException(){
        super("Balance would be negative.");
    }
}

    //withdrawing negative amount
class NegativeIncrementException extends Exception{
    public NegativeIncrementException(String message){
        super(message);
    }
    
    public NegativeIncrementException(){
        super("Depositing negative amounts is not allowed.");
    }
}

    //depositing negative amount
class NegativeDecrementException extends Exception{
    public NegativeDecrementException(String message){
        super(message);
    }
    
    public NegativeDecrementException(){
        super("Withdrawing negative amounts is not allowed.");
    }
}

    //balance is 0
class ZeroBalanceException extends Exception{
    public ZeroBalanceException(String message){
        super(message);
    }
    
    public ZeroBalanceException(){
        super("Balance of zero is not good.");
    }
}

    //no name
class NoNameException extends Exception{
    public NoNameException(String message){
        super(message);
    }
    
    public NoNameException(){
        super("Please provide your name.");
    }
}

    //no deposit or withdraw amount
class NoChangeException extends Exception{
    public NoChangeException(String message){
        super(message);
    }
    
    public NoChangeException(){
        super("Please provide a number.");
    }
}