package crewmate.lib;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class Test {
    @Test
    public void method(){
        Assert.assertTrue("Connected: ", Library.connected());
    }
}
