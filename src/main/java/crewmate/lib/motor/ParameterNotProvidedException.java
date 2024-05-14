package crewmate.lib.motor;
public class ParameterNotProvidedException extends Exception{
    public ParameterNotProvidedException(String errorMessage){
        super(errorMessage);
    }
}