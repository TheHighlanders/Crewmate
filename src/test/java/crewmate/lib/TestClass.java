package crewmate.lib;

import org.junit.jupiter.api.*;

public class TestClass {
    @Test
    public void method(){
        Assertions.assertTrue(Library.connected(), "Library Is Connected");
    }
}
