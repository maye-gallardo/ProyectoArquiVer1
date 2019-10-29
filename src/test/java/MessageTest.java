import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class MessageTest {

	@Test
	void testGetText() {
		Message message = new Message("Texto aqui");
		assertEquals("Texto aqui", message.getText());
	}

}
