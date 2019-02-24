package ru.stqa.pft.mantis.appmanager;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class HttpSession {

    private ApplicationManager app;
    private CloseableHttpClient httpClient;

    public HttpSession(ApplicationManager app) {

        this.app = app;
        httpClient = HttpClients.custom().setRedirectStrategy(new LaxRedirectStrategy()).build();

    }

    public boolean login(String username, String password) throws IOException {
        //сформировал пустой объект запроса
        HttpPost post = new HttpPost(app.getProperty("web.baseUrl") + "/login.php" );

        // сформировал параметры запроса
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("username", username));
        params.add(new BasicNameValuePair("password", password));
        params.add(new BasicNameValuePair("secure_session", "on"));
        params.add(new BasicNameValuePair("return", "my_view_page.php"));

        // добавил параметры в запрос
        post.setEntity(new UrlEncodedFormEntity(params));

        // выполняю запрос
        CloseableHttpResponse response = httpClient.execute(post);

        // получил html-текст полученной страницы
        String body = geTextFrom(response);

        // проверяю, что на странице есть текст с именем пользователя
        return body.contains(String.format("<span class=\"user-info\">%s</span>", username));


    }

    // Получает HTML-текст страницы
    private String geTextFrom(CloseableHttpResponse response) throws IOException {
        try{
            return EntityUtils.toString(response.getEntity());
        }finally {
            response.close();
        }
    }

    public boolean isLoggedInAs(String username) throws IOException {

        // формирую пустой запрос
        HttpGet get = new HttpGet(app.getProperty("web.baseUrl") + "/my_view_page.php");

        // исполняю запрос
        CloseableHttpResponse response = httpClient.execute(get);

        // получил текст страницы
        String body = geTextFrom(response);

        // проверяю, что на странице есть текст с именем пользователя
        return body.contains(String.format("<span class=\"user-info\">%s</span>", username));

    }

}
