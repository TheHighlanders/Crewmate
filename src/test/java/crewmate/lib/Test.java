package crewmate.lib;

public class Test {
    @Test
    public void method(){
        Assert.assertTrue("Connected: ", Library.connected());
    }
}
