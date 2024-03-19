import java.io.*;

public class InputOutputTest
{
    public static void main(String args[]) {
        try {
            File test = new File("test.txt");
            try (BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(test, true))){
                output.write(105);
            }
            try (BufferedInputStream input = new BufferedInputStream(new FileInputStream(test))){
                int data = input.read();
                System.out.println(data);
            }
        }
        catch (FileNotFoundException fnf) {
            System.out.println("File does not exist");
        }
        catch (Exception e) {
            System.out.println("Error.");
        }
    }
}