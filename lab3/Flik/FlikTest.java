import static org.junit.Assert.*;
import org.junit.Test;


public class FlikTest {

    @Test
    public void TestisSameNumber(){
        int A = 5;
        int B = 5;
        assertTrue(Flik.isSameNumber(A, B));
    }
}
