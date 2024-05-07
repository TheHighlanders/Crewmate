package crewmate.lib;

import org.junit.jupiter.api.Assertions;

public class Test {
    @Test
    public void method(){
        Assertions.assertTrue(Library.connected(), "Library Is Connected");
    }
}
