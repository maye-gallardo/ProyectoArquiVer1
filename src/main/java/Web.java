import java.util.Scanner;

public class Web extends InterfaceConection{

	@Override
	public void speak(String output) {
		response = output;
	     System.out.println(output);
		
	}

	@Override
	public void run(Connection c) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getResponse() {
		// TODO Auto-generated method stub
		return null;
	}

	
	
}