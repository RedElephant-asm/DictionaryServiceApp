package DictionaryServiceApp;

import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

public class ConsoleListener implements Runnable, Interruptible {

    private AtomicBoolean isActive;
    private String currentCommand = "";
    private Application application;

    public ConsoleListener(Application application){
        this.application = application;
    }

    @Override
    public void run() {
        isActive = new AtomicBoolean(true);
        Scanner consoleScanner = new Scanner(System.in);
        while(isActive.get()){
            if(consoleScanner.hasNext()){
                currentCommand = consoleScanner.nextLine();
                if (!currentCommand.isEmpty()) {
                    handledNotify();
                    handledWait(application);
                }
            }
        }
    }

    @Override
    public void closeThread() {
        isActive.getAndSet(false);
    }

    public String getCurrentCommand() {
        return currentCommand;
    }
}
