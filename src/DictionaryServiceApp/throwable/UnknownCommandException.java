package DictionaryServiceApp.throwable;

public class UnknownCommandException extends Exception {
    public UnknownCommandException(String commandName){
        super(String.format("Unknown command \"%s\".", commandName));
    }
}
