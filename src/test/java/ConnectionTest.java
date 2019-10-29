import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ConnectionTest {
	MailSystem system;
	Telephone phone;
	Mailbox mailbox;
	Connection connection;
	Message message;
	

	@BeforeEach
	public void setup() {
		system = mock(MailSystem.class);
	    phone = mock(Telephone.class);
	    mailbox = mock(Mailbox.class);
	    connection = new Connection(system, phone);
	    message = mock(Message.class);
	}
	
	@Test
	public void connectionShouldStartInitialPrompt() {
	    verify(phone).speak("Enter mailbox number followed by #");
	}

	@Test
	public void dialExistingMailbox() {    
	    
	    when(system.findMailbox("1")).thenReturn(mailbox);
	    when(mailbox.getGreeting()).thenReturn("Hola Mundo:");
	    connection.dial("1");
	    connection.dial("#");
	    verify(phone).speak("Hola Mundo:");
	}
	
	@Test
	public void dialNotExistMailbox() {
	    
	    when(system.findMailbox("34")).thenReturn(null);
	    connection.dial("3");
	    connection.dial("4");
	    connection.dial("#");
	    verify(phone).speak("Incorrect mailbox number. Try again!");
	}

	@Test
	public void loginWithIncorrectPasscode() {
		when(system.findMailbox("1")).thenReturn(mailbox);
		when(mailbox.checkPasscode("3")).thenReturn(false);
		connection.dial("1");
		connection.dial("#");
		connection.dial("3");
		connection.dial("#");
		verify(phone).speak("Incorrect passcode. Try again!");
		
	}
	
	@Test
	public void loginWhitCorrectPasscode() {
		when(system.findMailbox("1")).thenReturn(mailbox);
		when(mailbox.checkPasscode("1")).thenReturn(true);
		connection.dial("1");
		connection.dial("#");
		connection.dial("1");
		connection.dial("#");
		verify(phone).speak("Enter 1 to listen to your messages\n" 
							+ "Enter 2 to change your passcode\n"
							+ "Enter 3 to change your greeting");
	}
	
	@Test
	void setGreetingShouldShowNewGreeting() {
		when(system.findMailbox("1")).thenReturn(mailbox);
		when(mailbox.checkPasscode("1")).thenReturn(true);
		connection.dial("1");
		connection.dial("#");
		connection.dial("1");
		connection.dial("#");
		connection.dial("3"); 
		verify(phone).speak("Record your greeting, then press the # key");
	}
	
	@Test
	void setPasswordShouldShowNewPassword() {
		when(system.findMailbox("1")).thenReturn(mailbox);
		when(mailbox.checkPasscode("1")).thenReturn(true);
		connection.dial("1");
		connection.dial("#");
		connection.dial("1");
		connection.dial("#");
		connection.dial("2"); 
		verify(phone).speak("Enter mailbox number followed by #");
	}
	
	@Test
	void changePassCodeShouldShowTheMainMenu() {
		String newPasscode = "123";
		when(system.findMailbox("1")).thenReturn(mailbox);
		when(mailbox.checkPasscode("1")).thenReturn(true);
		doNothing().when(mailbox).setPasscode(newPasscode);
		connection.dial("1");
		connection.dial("#");
		connection.dial("1");
		connection.dial("#"); 
		connection.dial("2"); 
		connection.dial("1");
		connection.dial("2");
		connection.dial("3");
		connection.dial("#");
		verify(mailbox).setPasscode("123");
		verify(phone, times(2)).speak("Enter 1 to listen to your messages\n" 
							+ "Enter 2 to change your passcode\n"
							+ "Enter 3 to change your greeting");
	}
	
	@Test
	void changePassCodeShouldChangeThePassCode() {
		Mailbox mailboxtest = new Mailbox("1","Hola mundo");
		when(system.findMailbox("1")).thenReturn(mailboxtest);
		when(mailbox.checkPasscode("1")).thenReturn(true);
		assertEquals("1", mailboxtest.getPasscode());
		connection.dial("1");
		connection.dial("#");
		connection.dial("1");
		connection.dial("#");
		connection.dial("2"); 
		connection.dial("1");
		connection.dial("2");
		connection.dial("3");
		connection.dial("#");
		assertEquals("123", mailboxtest.getPasscode());
		verify(phone, times(2)).speak("Enter 1 to listen to your messages\n" 
							+ "Enter 2 to change your passcode\n"
							+ "Enter 3 to change your greeting");
	}
	
	@Test
	void changeGreeting() {
		String newGreeting = "Hola test";
		when(system.findMailbox("1")).thenReturn(mailbox);
		when(mailbox.checkPasscode("1")).thenReturn(true);
		doNothing().when(mailbox).setGreeting(newGreeting);
		connection.dial("1");
		connection.dial("#");
		connection.dial("1");
		connection.dial("#");
		connection.dial("3"); 
		connection.dial("Hola test");
		connection.dial("#");
		verify(phone, times(2)).speak("Enter 1 to listen to your messages\n" 
							+ "Enter 2 to change your passcode\n"
							+ "Enter 3 to change your greeting");
	}
	
	@Test
	void selectListenMessageShouldShowListenOptions() {
		when(system.findMailbox("1")).thenReturn(mailbox);
		when(mailbox.checkPasscode("1")).thenReturn(true);
		connection.dial("1");
		connection.dial("#");
		connection.dial("1");
		connection.dial("#");
		connection.dial("1"); 
		verify(phone).speak("Enter 1 to listen to the current message\n" 
				+ "Enter 2 to save the current message\n"
				+ "Enter 3 to delete the current message\n"
				+ "Enter 4 to return to the main menu");
	}
	
	@Test
	void selectListenCurrentMessageShouldShowActualMessages() {
		when(system.findMailbox("1")).thenReturn(mailbox);
		when(mailbox.checkPasscode("1")).thenReturn(true);
		when(mailbox.getCurrentMessage()).thenReturn(null);
		connection.dial("1");
		connection.dial("#");
		connection.dial("1");
		connection.dial("#");
		connection.dial("1"); 
		connection.dial("1");
		verify(phone).speak("No messages.\n"
				+ "Enter 1 to listen to the current message\n" 
				+ "Enter 2 to save the current message\n"
				+ "Enter 3 to delete the current message\n"
				+ "Enter 4 to return to the main menu");
	}
	
	@Test
	void selectSaveCurrentMessagesShouldBeShowMessagesMenu() {
		when(system.findMailbox("1")).thenReturn(mailbox);
		when(mailbox.checkPasscode("1")).thenReturn(true);
		connection.dial("1");
		connection.dial("#");
		connection.dial("1");
		connection.dial("#");
		connection.dial("1"); 
		connection.dial("2");
		verify(phone, times(2)).speak("Enter 1 to listen to the current message\n" 
				+ "Enter 2 to save the current message\n"
				+ "Enter 3 to delete the current message\n"
				+ "Enter 4 to return to the main menu");
	}
	
	@Test
	void selectDeleteCurrentMessageShouldBeShowMessagesMenu() {
		when(system.findMailbox("1")).thenReturn(mailbox);
		when(mailbox.checkPasscode("1")).thenReturn(true);
		connection.dial("1");
		connection.dial("#");
		connection.dial("1");
		connection.dial("#");
		connection.dial("1"); 
		connection.dial("3");
		verify(phone, times(2)).speak("Enter 1 to listen to the current message\n" 
				+ "Enter 2 to save the current message\n"
				+ "Enter 3 to delete the current message\n"
				+ "Enter 4 to return to the main menu");
	}
	
	@Test
	void selectReturnToTheMainMenuShouldBeShowMainMenu() {
		when(system.findMailbox("1")).thenReturn(mailbox);
		when(mailbox.checkPasscode("1")).thenReturn(true);
		connection.dial("1");
		connection.dial("#");
		connection.dial("1");
		connection.dial("#");
		connection.dial("1"); 
		connection.dial("4");
		verify(phone, times(2)).speak("Enter 1 to listen to your messages\n" 
				+ "Enter 2 to change your passcode\n"
				+ "Enter 3 to change your greeting");
	}
}
