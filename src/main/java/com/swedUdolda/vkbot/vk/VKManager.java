package com.swedUdolda.vkbot.vk;

import com.swedUdolda.vkbot.request.Post;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class VKManager {


    public static VKCore vkCore;

    public VKManager() {
        try {
            vkCore = new VKCore();
        } catch (ApiException | ClientException e) {
            System.out.println("Ошибка в инициализации VKCore");
        }
    }

    public void sendMessage(String msg, int peerId){
        if (msg == null){
            System.out.println("Не удалось, отправить сообщение, т.к. msg = null");
            return;
        }
        try {
            vkCore.getVk().messages().send(vkCore.getActor()).peerId(peerId).message(msg).execute();
            System.out.println("Сообщение отправлено:\npeerId = " + peerId + "\nтекст = " + msg);
        } catch (ApiException | ClientException e) {
            System.out.println("Ошибка отправки сообщения");
        }
    }

    public void sendImage(String msg, String url, int peerId) throws ClientException, ApiException, IOException {
        String attachmentId = Post.SendImagePostVK(vkCore.getVk(),vkCore.getActor(),url);
        vkCore.getVk().messages().send(vkCore.getActor()).peerId(peerId).attachment(attachmentId).message(msg).execute();
    }

    public void sendImage(String msg, int id, int ownerId, int peerId) throws ClientException, ApiException {
        String attachmentId = "photo" + ownerId + "_" + id;
        vkCore.getVk().messages().send(vkCore.getActor()).peerId(peerId).attachment(attachmentId).message(msg).execute();
    }

    public void sendImageList(String msg, int [] id, int [] ownerId, int peerId) throws ClientException, ApiException{
        List<String> attachmentIdList = new ArrayList<>();
        for(int i = 0; i < id.length; i++){
            attachmentIdList.add("photo" + ownerId[i] + "_" + id[i]);
        }
        System.out.println(attachmentIdList);
        vkCore.getVk().messages().send(vkCore.getActor()).peerId(peerId).attachment(attachmentIdList).message(msg).execute();
    }
}
