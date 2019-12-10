package com.swedUdolda.vkbot.command;

import com.swedUdolda.vkbot.command.commands.Help;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class CommandManager {
    private static List<String> commandsList = new ArrayList<>();
    static {
        commandsList.add("help");
    }

    public static List<String> getCommandsList() { return commandsList; }

    public static HashSet<Command> getCommands(){
        HashSet<Command> commands = new HashSet<>();
        commands.add(new Help("help"));
        return commands;
    }
}
