package DictionaryServiceApp.throwable;

public class NoDiarySelectedException extends Exception {
    public NoDiarySelectedException(){
        super("It is required to select a dictionary before work. Use command : \nset_dictionary [filepath] [word language] [key language] [file encoding]");
    }

}
