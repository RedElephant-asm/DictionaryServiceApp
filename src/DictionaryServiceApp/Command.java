package DictionaryServiceApp;

import DictionaryServiceApp.throwable.IncorrectCountOfParametersException;
import org.SimpleDictionaryService.*;
import java.util.Arrays;

public enum Command{

    SET_DICTIONARY           (4){

        @Override
        public void execute(DictionaryService context, String[] parameters) {
            context.setDictionary(new Dictionary(
                    parameters[0],
                    Language.getLanguageByName(parameters[1]),
                    Language.getLanguageByName(parameters[2]),
                    Encoding.getEncodingByName(parameters[3])
            ));
        }
    },

    ADD_RECORD          (2){

        @Override
        public void execute(DictionaryService context, String[] parameters) {
            context.addRecord(context.createRecord(parameters[0], parameters[1]));
        }
    },

    DELETE_RECORD       (1){

        @Override
        public void execute(DictionaryService context, String[] parameters) {
            context.deleteRecord(parameters[0]);
        }
    },

    READ_ALL            (0){

        @Override
        public void execute(DictionaryService context, String[] parameters) {
            context.readAll().forEach(System.out::print);
        }
    },

    UPDATE_RECORD       (2){

        @Override
        public void execute(DictionaryService context, String[] parameters) {
            context.updateRecord(parameters[0], context.createRecord(parameters[0], parameters[1]));
        }
    },

    QUIT                (0){

        @Override
        public void execute(DictionaryService context, String[] parameters) {}
    },

    UNKNOWN_COMMAND     (0){

        @Override
        public void execute(DictionaryService context, String[] parameters) {}
    };

    private int parametersCount;

    Command(int parametersCount){
        this.parametersCount = parametersCount;
    }

    public abstract void execute(DictionaryService context, String[] parameters);

    public static String[] getArgumentsFromCommandBody(String commandBody){
        return commandBody.replaceAll("\\s{2,}", " ").split(" ");
    }

    public static Command getCommandFromString(String commandBody){
        String commandName = getArgumentsFromCommandBody(commandBody)[0];
        if(Arrays.stream(Command.values()).anyMatch(currentCommand -> currentCommand.getConsoleEquivalent().equals(commandName))){
            return Arrays.stream(Command.values()).filter(currentCommand -> currentCommand.getConsoleEquivalent().equals(commandName)).findFirst().get();
        }else return UNKNOWN_COMMAND;
    }

    public boolean isNumberOfParametersCorrect(int receivedCount){
        if (parametersCount != receivedCount){
            try {
                throw new IncorrectCountOfParametersException(getConsoleEquivalent(), parametersCount, receivedCount);
            } catch (IncorrectCountOfParametersException e) {
                System.out.println(e.getMessage());
                return false;
            }
        }
        return true;
    }

    public int getParametersCount() {
        return parametersCount;
    }

    public String getConsoleEquivalent() {
        return this.name().toLowerCase();
    }
}
