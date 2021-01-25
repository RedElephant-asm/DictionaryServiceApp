package DictionaryServiceApp;

public interface Interruptible {

    void closeThread();

    default void handledWait(Runnable thread){
        synchronized(thread){
            try {
                thread.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    default void handledNotify(){
        synchronized (this){
            this.notify();
        }
    }
}
