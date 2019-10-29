import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MessageQueueTest { 
	MessageQueue queue;
	Message message;
	
	@BeforeEach
	public void setup() {
		queue = new MessageQueue();
		message = new Message("Hola");
	}

	@Test
	public void sizeOfAnEmptyQueueShouldBeZero() {		
		assertEquals(0, queue.size()); 
	}
	
	@Test
	public void sizeOfQueueWithOneMessageShouldBeOne() {
		queue.add(message);
		assertEquals(1,queue.size());
	}
	
	@Test
	public void whenRemoveAMessageSizeQueueShouldBeZero() {
		queue.add(message);
		queue.remove();
		assertEquals(0,queue.size());
	}
	
	@Test
	public void peekOfEmptyQueueShouldBeNull() {
		assertEquals(null, queue.peek());
	}
	
	@Test
	public void peekOfQueueShouldBeElement() {
		queue.add(message);
		assertEquals(message, queue.peek());
	}
}
