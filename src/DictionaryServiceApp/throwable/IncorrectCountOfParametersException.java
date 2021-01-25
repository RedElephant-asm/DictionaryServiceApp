package DictionaryServiceApp.throwable;

public class IncorrectCountOfParametersException extends Exception{
    public IncorrectCountOfParametersException(String commandName, int expectedCount, int receivedCount){
        super(String.format("%d parameters expected when using the command \"%s\", %d received.", expectedCount, commandName, receivedCount));
    }
}
