package com.swedUdolda.vkbot.vk;

import com.swedUdolda.vkbot.json.JSONHandler;
import com.swedUdolda.vkbot.notification.Event;
import com.swedUdolda.vkbot.notification.EventDeterminate;
import com.swedUdolda.vkbot.notification.EventManager;
import com.swedUdolda.vkbot.notification.events.Unknown;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.PrintWriter;

public class CallbackVK extends javax.servlet.http.HttpServlet {

    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/plain");
        PrintWriter printWriter = response.getWriter();
        StringBuffer stringBuffer = new StringBuffer();
        String line = null;
        try {
            BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null) {
                stringBuffer.append(line);
            }
            reader.close();

        } catch (Exception e) {
            System.out.println("Произошла ошибка в получении данных");
            return;
        }
        System.out.println("Получен запрос:\n"+ stringBuffer.toString());

        JSONHandler jsonHandler;
        Event event;
        try {
           jsonHandler  = new JSONHandler(stringBuffer.toString());
           event = EventDeterminate.getEvent(EventManager.getEvents(),jsonHandler.getType());
        }catch (JSONException e) {
            System.out.println("Полученная информация не является json форматом, или не найден параметр get_type");
            return;
        }

        String responseMessage;
        if(event != null)
           responseMessage  = event.exec(jsonHandler);
        else
            responseMessage = new Unknown("unknown").exec(jsonHandler);
        printWriter.println(responseMessage);
        System.out.println("Отправлен ответ: " + responseMessage);
    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {

    }
}