import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MailSystemTester
{
	private static final int MAILBOX_COUNT = 20;
	
   public static void main(String[] args){
      MailSystem system = new MailSystem(MAILBOX_COUNT);
      Scanner console = new Scanner(System.in);
      Telephone p = new Telephone(console);
      InterfaceConection interfaceConection = new Consola();
      Connection c = new Connection(system, interfaceConection);
      interfaceConection.run(c);
      //p.run(c);
     
   }
}
