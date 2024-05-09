package crewmate.lib;

import org.junit.jupiter.api.*;

public class TestClass {
    @Test
    public void LibraryUnitTests(){
        Assertions.assertTrue(Library.connected(), "Library Is Not Connected");
    }
}
