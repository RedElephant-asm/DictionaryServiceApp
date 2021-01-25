package DictionaryServiceApp;

import org.SimpleDictionaryService.DictionaryService;

public class Program {
    public static Object object;
    public static void main(String[] args) {
        Thread applicationThread = new Thread(new Application());
        applicationThread.start();
        try {
            applicationThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
