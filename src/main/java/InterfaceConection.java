import java.util.ArrayList;
import java.util.Scanner;

public abstract class InterfaceConection{
	protected String response;
	
	public abstract void speak(String output);
	public abstract void run(Connection c);
	public abstract String getResponse();
}
