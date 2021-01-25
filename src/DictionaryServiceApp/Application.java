package DictionaryServiceApp;

import DictionaryServiceApp.throwable.NoDiarySelectedException;
import DictionaryServiceApp.throwable.UnknownCommandException;
import org.SimpleDictionaryService.Dictionary;
import org.SimpleDictionaryService.DictionaryService;
import org.SimpleDictionaryService.Encoding;
import org.SimpleDictionaryService.Language;
import org.SimpleDictionaryService.throwable.UnknownLanguageException;
import org.SimpleDictionaryService.throwable.WrongEncodingException;

import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

public class Application implements Runnable, Interruptible {

    private DictionaryService dictionaryService;
    private AtomicBoolean isActive = new AtomicBoolean(true);
    private ConsoleListener consoleListener;
    private boolean isDictionarySelected;

    public Application(){
        consoleListener = new ConsoleListener(this);
        dictionaryService = new DictionaryService();
    }

    @Override
    public void run() {
        Thread consoleListenerThread = new Thread(consoleListener);
        consoleListenerThread.start();
        String currentCommand = "";
        System.out.println("[Dictionary service app]");
        while (isActive.get()){
            handledWait(consoleListener);
            currentCommand = consoleListener.getCurrentCommand();
            handleCommand(currentCommand);
            handledNotify();
        }
        System.out.println("Bye...");
    }

    @Override
    public void closeThread() {
        isActive.getAndSet(false);
    }

    public boolean isDictionarySelected(){
        try{
            if(!dictionaryService.isDictionarySelected()){
                throw new NoDiarySelectedException();
            }
        }catch (NoDiarySelectedException exception){
            System.out.println(exception.getMessage());
            return false;
        }
        return true;
    }

    public void handleCommand(String commandString){
        String[] commandParameters = Command.getArgumentsFromCommandBody(commandString);
        Command currentCommand = Command.getCommandFromString(commandString);

        switch (currentCommand){

            case SET_DICTIONARY:
                if(!currentCommand.isNumberOfParametersCorrect(commandParameters.length - 1)){
                    break;
                }else if (!Encoding.isEncodingExist(commandParameters[4])){
                    try {
                        throw new WrongEncodingException(commandParameters[4]);
                    }catch (WrongEncodingException exception){
                        System.out.println(exception.getMessage());
                    }
                    break;
                }else if (!Language.isLanguageExist(commandParameters[2])){
                    try {
                        throw new UnknownLanguageException(commandParameters[2]);
                    }catch (UnknownLanguageException exception){
                        System.out.println(exception.getMessage());
                    }
                    break;
                }else if (!Language.isLanguageExist(commandParameters[3])){
                    try {
                        throw new UnknownLanguageException(commandParameters[3]);
                    }catch (UnknownLanguageException exception){
                        System.out.println(exception.getMessage());
                    }
                    break;
                }
                currentCommand.execute(dictionaryService, Arrays.copyOfRange(commandParameters, 1, commandParameters.length));
                break;

            case UNKNOWN_COMMAND:
                try {
                    throw new UnknownCommandException(commandParameters[0]);
                } catch (UnknownCommandException e) {
                    System.out.println(e.getMessage());
                }
                break;

            case QUIT:
                consoleListener.closeThread();
                this.closeThread();
                break;

            default:
                if(!isDictionarySelected() || !currentCommand.isNumberOfParametersCorrect(commandParameters.length - 1)){
                    break;
                }
                currentCommand.execute(dictionaryService, Arrays.copyOfRange(commandParameters, 1, commandParameters.length));
                break;
        }
    }
}
