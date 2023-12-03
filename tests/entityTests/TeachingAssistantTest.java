import org.junit.Test;
import static org.junit.Assert.*;

public class TeachingAssistantTest {

    @Test
    public void testTeachingAssistantConstruction() {
        TeachingAssistant ta = new TeachingAssistant("user456", "Jane Doe", "janedoe@example.com", "password456");

        assertEquals("user456", ta.getUID());
        assertEquals("Jane Doe", ta.getName());
        assertEquals("janedoe@example.com", ta.getEmail());
        assertEquals("password456", ta.getPassword());
        assertEquals("ta", ta.getType());
    }
}}