package com.swedUdolda.vkbot.command;

import com.swedUdolda.vkbot.parse.MessageParser;
import com.swedUdolda.vkbot.vk.VKManager;

public class Commander implements Runnable {

    private String message;
    private int peerId;

    public Commander(String message, int peerId) {
        this.message = message;
        this.peerId = peerId;
    }

    @Override
    public void run() {
        String cmd = message.split(" ")[0].toLowerCase();
        if(CommandManager.getCommandsList().contains(cmd))
            CommandDeterminate.getCommand(CommandManager.getCommands(),cmd).exec(peerId,message);
        else{
            cmd=CommandDeterminate.StringToCommandString(cmd);
            if(cmd == null) cmd=CommandDeterminate.StringToCommandString(message.trim());
            if(cmd != null) CommandDeterminate.getCommand(CommandManager.getCommands(),cmd).exec(peerId,message);
            else new VKManager().sendMessage(MessageParser.ParseMessage(message),peerId);
        }
    }
}
