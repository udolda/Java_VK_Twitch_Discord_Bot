package com.swedUdolda.vkbot.senddiscmess;

import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.security.auth.login.LoginException;

public class DiscordMessageSender{
    public static void exec() throws LoginException, ClientException, ApiException {
        /*
        TransportClient transportClient = HttpTransportClient.getInstance();
        VkApiClient vk = new VkApiClient(transportClient);

        GroupAuthResponse authResponse = vk.oAuth()
                .groupAuthorizationCodeFlow(APP_ID, CLIENT_SECRET, REDIRECT_URI, code)
                .execute();

        GroupActor actor = new GroupActor(groupId, authResponse.getAccessTokens().get(groupId));
        //старый вк код
        */

        //бот дискорд
        JDABuilder builder = new JDABuilder(AccountType.BOT);
        String token = System.getenv("discordBotToken");
        System.out.println(token);
        builder.setToken(token);
        System.out.println("Перехожу к отправке сообщения в текстовый канал");
        builder.build().getTextChannels().get(0).sendMessage("что-то").queue();
        //бот дискорд
    }
}
