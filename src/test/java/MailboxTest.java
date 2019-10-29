import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MailboxTest {
	String gretting;
	String passcode;
	String concept;
	MessageQueue newMessages;
	MessageQueue keptMessages;
	Message message;
	Mailbox mailbox;
	
	@BeforeEach
	public void setup() {
		gretting = "abc";
		passcode = "123";
		newMessages = new MessageQueue(); 
		message = new Message(concept);
		keptMessages = new MessageQueue();
		mailbox = new Mailbox(passcode, gretting); 
	}

	@Test
	void  greetingShouldBeAGreeting() {
		assertEquals("abc", mailbox.getGreeting());
	}
	
	@Test
	void greetingShouldBeANewGreeting() {
		String newGreeting = "a1b2c3";
		mailbox.setGreeting(newGreeting);
		assertEquals("a1b2c3", mailbox.getGreeting());
	}
	
	@Test
	void passcodeShouldBeAPasscode() {
		assertEquals("123", mailbox.getPasscode());
	}
	
	@Test
	void passcodeShouldBeaNewPasscode() {
		String newPasscode = "246";
		mailbox.setPasscode(newPasscode); 
		assertEquals("246", mailbox.getPasscode());
	}
	
	@Test
	void correctPasscodeShouldBeTrue() {
		assertEquals(true, mailbox.checkPasscode(passcode));
	}
	
	@Test
	void ifAddAMessageInEmptyQueueSizeShouldBeOne() {
		mailbox.addMessage(message);
		assertEquals(1, mailbox.getMessageQueue().size());
	}
	
	@Test
	void emptyNewMessagesAndKeptMessagesShouldBeNull() {
		assertEquals(null, mailbox.getCurrentMessage());
	}
	
	@Test
	void ifSaveCurrentMessageKeptMessageSizeShouldBeOne() {
		mailbox.saveCurrentMessage();
		assertEquals(0, mailbox.getKeptMessage().size());
	}
}
