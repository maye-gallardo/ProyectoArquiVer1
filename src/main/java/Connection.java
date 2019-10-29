import java.util.ArrayList;
import java.util.List;

public class Connection
{
	   private Mailbox currentMailbox;
	   private MailSystem system;
	   private String currentRecording;
	   private String accumulatedKeys;
	   private Telephone phone;
	   private int state;
	   private UIMessages UImessages;
	   private InterfaceConection interfaceConection;
	   
	   private ArrayList<InterfaceConection> conector = new ArrayList<InterfaceConection>();

	   private static final int DISCONNECTED = 0;
	   private static final int CONNECTED = 1;
	   private static final int RECORDING = 2;
	   private static final int MAILBOX_MENU = 3;
	   private static final int MESSAGE_MENU = 4;
	   private static final int CHANGE_PASSCODE = 5;
	   private static final int CHANGE_GREETING = 6;
	   
   public Connection(MailSystem s, InterfaceConection ic){
      system = s;
      interfaceConection = ic;
      resetConnection(); 
      this.conector = new ArrayList<InterfaceConection>();
   }
   
   /*public Connection(MailSystem s, Telephone p){
	      system = s;
	      phone = p;
	      resetConnection(); 
	   }*/

   public void dial(String key){
      if (state == CONNECTED) 
         connect(key);
      else if (state == RECORDING)
         login(key);
      else if (state == CHANGE_PASSCODE)
         changePasscode(key);
      else if (state == CHANGE_GREETING)
         changeGreeting(key);
      else if (state == MAILBOX_MENU)
         mailboxMenu(key);
      else if (state == MESSAGE_MENU)
         messageMenu(key);
   }

   public void record(String voice){
      if (state == RECORDING || state == CHANGE_GREETING)
         currentRecording += voice;
   }

   public void hangup(){
      if (state == RECORDING)
         currentMailbox.addMessage(new Message(currentRecording));
      resetConnection();
   }

   private void resetConnection(){
      currentRecording = "";
      accumulatedKeys = "";
      state = CONNECTED;
      //phone.speak(UImessages.INITIAL_PROMPT);
     conector.forEach(x -> x.speak(UImessages.INITIAL_PROMPT));
   }

   private void connect(String key){
      if (key.equals("#"))
      {
         currentMailbox = system.findMailbox(accumulatedKeys);
         if (currentMailbox != null)
         {
            state = RECORDING;
           //phone.speak(currentMailbox.getGreeting());
            conector.forEach(x -> x.speak(currentMailbox.getGreeting()));
         }
         else
            //phone.speak(UImessages.INCORRECT_MAILBOX);
         	conector.forEach(x -> x.speak(UImessages.INCORRECT_MAILBOX));
         accumulatedKeys = "";
      }
      else
         accumulatedKeys += key;
   }

   private void login(String key){
      if (key.equals("#"))
      {
         if (currentMailbox.checkPasscode(accumulatedKeys))
         {
            state = MAILBOX_MENU;
            //phone.speak(UImessages.MAILBOX_MENU_TEXT);
            conector.forEach(x -> x.speak(UImessages.MAILBOX_MENU_TEXT));
         }
         else
            //phone.speak(UImessages.INCORRECT_PASSCODE);
         	conector.forEach(x -> x.speak(UImessages.INCORRECT_PASSCODE));
         accumulatedKeys = "";
      }
      else
         accumulatedKeys += key; 
   }

   private void changePasscode(String key){
      if (key.equals("#"))
      {
         currentMailbox.setPasscode(accumulatedKeys);
         state = MAILBOX_MENU;
         //phone.speak(UImessages.MAILBOX_MENU_TEXT);
         conector.forEach(x -> x.speak(UImessages.MAILBOX_MENU_TEXT));
         accumulatedKeys = "";
      }
      else
         accumulatedKeys += key;
   }

   private void changeGreeting(String key){
      if (key.equals("#"))
         currentMailbox.setGreeting(currentRecording);
         currentRecording = "";
         state = MAILBOX_MENU;
         //phone.speak(UImessages.MAILBOX_MENU_TEXT);
         conector.forEach(x -> x.speak(UImessages.MAILBOX_MENU_TEXT));
   }

   private void mailboxMenu(String key){
	  switch(Integer.parseInt(key)) {
		  case 1:{
			  state = MESSAGE_MENU;
		      //phone.speak(UImessages.MESSAGE_MENU_TEXT);
		      conector.forEach(x -> x.speak(UImessages.MESSAGE_MENU_TEXT));
		      break;
		  } 
		  case 2:{
			  state = CHANGE_PASSCODE;
		      //phone.speak(UImessages.NEW_PASSWORD);
		      conector.forEach(x -> x.speak(UImessages.NEW_PASSWORD));
		      break;
		  }
		  case 3:{
			  state = CHANGE_GREETING;
		      //phone.speak(UImessages.RECORD_GREETING);
		      conector.forEach(x -> x.speak(UImessages.NEW_PASSWORD));
		      break;
		  }
	  }
   }

   private void messageMenu(String key){
	  switch(Integer.parseInt(key)) {
	  	case  1:{
	  		String output = getMessage();
	  		//phone.speak(output);
	  		conector.forEach(x -> x.speak(output));
	        break;
	  	}
	  	case 2:{
	  		currentMailbox.saveCurrentMessage();
	        //phone.speak(UImessages.MESSAGE_MENU_TEXT);
	        conector.forEach(x -> x.speak(UImessages.MESSAGE_MENU_TEXT));
	        break;
	  	}
	  	case 3:{
	  		currentMailbox.removeCurrentMessage();
	        //phone.speak(UImessages.MESSAGE_MENU_TEXT);
	        conector.forEach(x -> x.speak(UImessages.MESSAGE_MENU_TEXT));
	        break;
	  	}
	  	case 4:{
	  		state = MAILBOX_MENU;
	        //phone.speak(UImessages.MAILBOX_MENU_TEXT);
	        conector.forEach(x -> x.speak(UImessages.MAILBOX_MENU_TEXT));
	        break;
	  	}
	  }
   }

	private String getMessage() {
		String output = "";
		 Message m = currentMailbox.getCurrentMessage();
		 if (m == null) output += "No messages." + "\n";
		 else output += m.getText() + "\n";
		 output += UImessages.MESSAGE_MENU_TEXT;
		return output;
	}
}







