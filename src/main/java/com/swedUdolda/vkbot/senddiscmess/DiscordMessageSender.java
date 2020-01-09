package com.swedUdolda.vkbot.senddiscmess;

import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import javax.security.auth.login.LoginException;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class DiscordMessageSender implements Runnable{

    JSONObject vkObject;

    public DiscordMessageSender(JSONObject vkObject) {
        this.vkObject = vkObject;
    }

    public static void exec(String message, List<String> urlImages, int idFrom, int id) throws LoginException, InterruptedException {

        //логинимся за бота на сервере
        JDABuilder builder = new JDABuilder(AccountType.BOT);
        String token = System.getenv("discordBotToken");
        builder.setToken(token);
        JDA jda = builder.build();
        jda.awaitReady();

        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle("Новая запись на стене", System.getenv("communityURL")+"?w=wall"+idFrom+"_"+id);
        embedBuilder.setDescription(message);
        embedBuilder.setColor(Color.BLUE);
        if(!urlImages.isEmpty()) {
            embedBuilder.setImage(urlImages.get(0));
        }
        jda.getTextChannelsByName(System.getenv("channelForBotName"), true).get(0).sendMessage(embedBuilder.build()).queue();
        if(urlImages.size() > 1){
            for(int i = 1; i < urlImages.size(); i++){
                EmbedBuilder embedBuilderImage = new EmbedBuilder();
                embedBuilderImage.setColor(Color.BLUE);
                embedBuilderImage.setImage(urlImages.get(i));
                jda.getTextChannelsByName(System.getenv("channelForBotName"), true).get(0).sendMessage(embedBuilderImage.build()).queue();
            }
        }
        System.out.println("Текстовое сообщение отправлено. Text: " + message);
        for (String imgURL:urlImages) {
            System.out.println("Изображение отправлено. URL: " + imgURL);
        }
    }

    @Override
    public void run() {
        String text = vkObject.getString("text");
        int id, idFrom;
        id = vkObject.getInt("id");
        idFrom = vkObject.getInt("from_id");
        List<String> urlImages = new ArrayList<>();
        try {
            JSONArray jsonArray = vkObject.getJSONArray("attachments");
            System.out.println("Изображений получено: "+jsonArray.length());
            for (Object obj : jsonArray) {
                JSONObject jsonObject = (JSONObject) obj;
                JSONObject jsonObjectPhoto = jsonObject.getJSONObject("photo");
                JSONArray jsonArraySizes = jsonObjectPhoto.getJSONArray("sizes");
                JSONObject jsonObjectSizesLast =  (JSONObject) jsonArraySizes.get(jsonArraySizes.length() - 1);
                urlImages.add(jsonObjectSizesLast.getString("url"));
            }
        }
        catch(JSONException e){
            System.out.println("Не удалось получить фото");
            e.printStackTrace();
        }

        try {
            exec(text, urlImages, idFrom, id);
        } catch (LoginException | InterruptedException e) {
            System.out.println("Ощибка отправки сообщения в дискорд");
            e.printStackTrace();
    }
    }
}
