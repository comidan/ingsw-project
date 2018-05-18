package it.polimi.ingsw.sagrada.network.utilities;


public class CommandParser {
    private KeyParser keyParser;

    public CommandParser() {
        keyParser = new KeyParser();
    }


    public ActionEnum parse(String message) {

        String stringType = keyParser.accessNextLevel(message);
        String stringAction = keyParser.getKey(stringType);

        switch (stringAction) {
            case "login":
                return ActionEnum.LOGIN;
            case "choice":
                return ActionEnum.CHOICE;
            case "settings":
                return ActionEnum.SETTINGS;

            default:
                return null;
        }

    }


}
