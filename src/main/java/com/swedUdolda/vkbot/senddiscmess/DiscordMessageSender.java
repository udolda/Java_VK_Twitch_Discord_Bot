package com.swedUdolda.vkbot.senddiscmess;

import com.swedUdolda.vkbot.vk.VKManager;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.security.auth.login.LoginException;

public class DiscordMessageSender implements Runnable{

    JSONObject vkObject;

    public DiscordMessageSender(JSONObject vkObject) {
        this.vkObject = vkObject;
    }

    public static void exec(String message) throws LoginException, InterruptedException {

        //бот дискорд
        JDABuilder builder = new JDABuilder(AccountType.BOT);
        String token = System.getenv("discordBotToken");
        System.out.println(token);
        builder.setToken(token);
        System.out.println("Перехожу к отправке сообщения в текстовый канал");
        JDA jda = builder.build();
        jda.awaitReady();
        jda.getTextChannelsByName("testingbot", true).get(0).sendMessage(message).queue();
        //бот дискорд
    }

    @Override
    public void run() {
        String text = vkObject.getString("text");

        new VKManager().sendMessage("Пришла новая запись\n" + text,98604072);
        try {
            JSONArray jsonArray = vkObject.getJSONArray("attachments");
            for (Object obj : jsonArray) {
                JSONObject jsonObject = (JSONObject) obj;
                int id = jsonObject.getJSONObject("photo").getInt("id");
                int ownerId = jsonObject.getJSONObject("photo").getInt("owner_id");
                try {
                    new VKManager().sendImage("", id, ownerId, 98604072);
                } catch (ClientException | ApiException e) {
                    e.printStackTrace();
                    new VKManager().sendMessage("Не удалось отправить картинку", 98604072);
                }
            }
        }
        catch(JSONException e){
            System.out.println("Нет картинок");
        }

        try {
            exec(text);
        } catch (LoginException | InterruptedException e) {
            System.out.println("Ощибка отправки сообщения в дискорд");
            e.printStackTrace();
    }
    }
}
