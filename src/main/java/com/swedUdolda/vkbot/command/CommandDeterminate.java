package com.swedUdolda.vkbot.command;

import java.util.Collection;
import com.swedUdolda.vkbot.string.ChangeString;

public class CommandDeterminate {
    public static Command getCommand(Collection<Command> commands, String nameCommand) {
        for (Command command : commands) {
            if (command.getName().equals(nameCommand))
                return command;
        }
        return null;
    }
    public static String StringToCommandString(String str){
        str = ChangeString.RemoveSign(str);
        switch(str){
            case "помощь":
            case "что ты умеешь":
                return "help";
            default:
                return null;
        }
    }
}
