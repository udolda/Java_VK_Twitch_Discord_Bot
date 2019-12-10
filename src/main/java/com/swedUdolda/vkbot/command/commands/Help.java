package com.swedUdolda.vkbot.command.commands;

import com.swedUdolda.vkbot.command.Command;
import com.swedUdolda.vkbot.vk.VKManager;

public class Help extends Command {
    public Help(String name) {
        super(name);
    }

    @Override
    public void exec(int peerId, String message) {
        new VKManager().sendMessage("помощь", peerId);
    }
}
