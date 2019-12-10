package com.swedUdolda.vkbot.request;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.photos.Photo;
import com.vk.api.sdk.objects.photos.PhotoUpload;
import com.vk.api.sdk.objects.photos.responses.WallUploadResponse;
import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONObject;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;


public class Post {

    public static String sendRequestJSON(String url, JSONObject json) throws IOException {
        HttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);

        httpPost.setEntity(new StringEntity(json.toString(), StandardCharsets.UTF_8));

        HttpResponse httpResponse = httpClient.execute(httpPost);
        HttpEntity entity = httpResponse.getEntity();

        if (entity != null) {
            InputStream inputStream = entity.getContent();
            Scanner scan = new Scanner(inputStream);
            return scan.nextLine();
        }
        return null;
    }

    public static String SendImagePostVK(VkApiClient vk, GroupActor actor, String urlImage) throws IOException, ClientException, ApiException {
        //Получаем картинку из интернета
        MultipartEntityBuilder builder = getMultipartEntityBuilder(getBinaryCodePage(urlImage));
        //Получаем адрес куда требуется отправить картинку
        PhotoUpload serverResponse = vk.photos().getMessagesUploadServer(actor).execute();
        //Отправляем картинку и получаем ответ от сервера
        String response = SendImagePost(serverResponse.getUploadUrl(),builder);
        //Обработка запроса
        JSONObject json = new JSONObject(response);
        System.out.println("Ответ сервера: " + json.toString());
        List<Photo> photoList = vk.photos().saveMessagesPhoto(actor,json.getString("photo"))
                .server(json.getInt("server"))
                .hash(json.getString("hash")).execute();
        Photo photo = photoList.get(0);
        return "photo" + photo.getOwnerId() + "_" + photo.getId();
    }

    public static String SendImagePostVK(VkApiClient vk, GroupActor actor, File file) throws ClientException, ApiException, IOException {
        //Получаем картинку из файла
        MultipartEntityBuilder builder = getMultipartEntityBuilder(file);
        //Получаем адрес куда требуется отправить картинку
        PhotoUpload serverResponse = vk.photos().getMessagesUploadServer(actor).execute();
        //Отправляем картинку и получаем ответ от сервера
        String response = SendImagePost(serverResponse.getUploadUrl(),builder);
        //Обработка запроса
        JSONObject json = new JSONObject(response);

        List<Photo> photoList = vk.photos().saveMessagesPhoto(actor,json.getString("photo"))
                .server(json.getInt("server"))
                .hash(json.getString("hash")).execute();
        Photo photo = photoList.get(0);
        return "photo" + photo.getOwnerId() + "_" + photo.getId();
    }

    private static String SendImagePost(String urlUpload, MultipartEntityBuilder multipartEntityBuilder) throws IOException {
        HttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(urlUpload);

        httpPost.setEntity(multipartEntityBuilder.build());

        HttpResponse httpResponse = httpClient.execute(httpPost);
        HttpEntity entity = httpResponse.getEntity();

        String response = null;
        if (entity != null) {
            InputStream inputStream = entity.getContent();
            Scanner scan = new Scanner(inputStream);
            response = scan.nextLine();
            return response;
        }
        return null;
    }

    public static byte [] getBinaryCodePage(String url) throws IOException {
        URL urlT = new URL(url);
        URLConnection urlConnection = urlT.openConnection();
        urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64;rv:52.0) Gecko/20100101 Firefox/52.0");
        InputStream in = new BufferedInputStream(urlConnection.getInputStream());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        int n = 0;
        while (-1!=(n=in.read(buf)))
        {
            out.write(buf, 0, n);
        }
        out.close();
        in.close();
        byte[] data = out.toByteArray();
        return data;
    }

    public static byte [] getBinaryCodePage(String url, String pathFile) throws IOException {
        byte [] data = getBinaryCodePage(url);
        FileOutputStream fos = new FileOutputStream(pathFile);
        fos.write(data);
        fos.close();
        return data;
    }

    private static MultipartEntityBuilder getMultipartEntityBuilder(byte [] data){
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();

        builder.setContentType(ContentType.MULTIPART_FORM_DATA);
        builder.setCharset(Charset.forName("UTF-8"));
        builder.setBoundary(UUID.randomUUID().toString());
        ContentBody cd = new InputStreamBody(new ByteArrayInputStream(data), "image.jpg");
        builder.addPart("file", cd);
        return builder;
    }

    private static MultipartEntityBuilder getMultipartEntityBuilder(File file){
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();

        builder.setContentType(ContentType.MULTIPART_FORM_DATA);
        builder.setCharset(Charset.forName("UTF-8"));
        builder.setBoundary(UUID.randomUUID().toString());
        builder.addBinaryBody("file", file);
        return builder;
    }
}
