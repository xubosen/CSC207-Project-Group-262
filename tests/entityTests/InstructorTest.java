import org.junit.Test;
import static org.junit.Assert.*;

public class InstructorTest {

    @Test
    public void testInstructorConstruction() {
        Instructor instructor = new Instructor("user123", "John Doe", "johndoe@example.com", "password123");

        assertEquals("user123", instructor.getUID());
        assertEquals("John Doe", instructor.getName());
        assertEquals("johndoe@example.com", instructor.getEmail());
        assertEquals("password123", instructor.getPassword());
        assertEquals("instructor", instructor.getType());
    }
}