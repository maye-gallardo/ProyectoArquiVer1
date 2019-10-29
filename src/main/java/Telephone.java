import java.util.Scanner;

public class Telephone
{
	String response;
	private Scanner scanner;
	
	public String getResponse() {
		return response;
	}
	
   public Telephone(Scanner aScanner){
      scanner = aScanner;
   }

   public void speak(String output){
	  response = output;
      System.out.println(output);
   }

   public void run(Connection c){
      boolean more = true;
      while (more)
      {
         String input = scanner.nextLine();
         if (input == null) return;
         if (input.equalsIgnoreCase("H"))
            c.hangup();
         else if (input.equalsIgnoreCase("Q"))
            more = false;
         else if (input.length() == 1
            && "1234567890#".indexOf(input) >= 0)
            c.dial(input);
         else
            c.record(input);
      }
   }
}
