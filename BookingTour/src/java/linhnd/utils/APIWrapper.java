/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package linhnd.utils;


import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import linhnd.dtos.UsersDTO;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 *
 * @author PC
 */
public class APIWrapper {

    private static String appID = "500393610668939";
    private static String appSecret = "b87c4ff769f997f9ad78a6c23d1ad008";
    private static String redirecUrl = "http://localhost:8084/BookingTour/login-facebook";
    private String accessToken;
    

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public static String getDiaLogLink() {
        String testUrl = "https://www.facebook.com/dialog/oauth?client_id=" + appID + "&redirect_uri=" + redirecUrl + "&scope=user_link%2Cemail%2Cpublic_profile";
        return testUrl;
    }

    public String getAccessToken(String code) {
        String accessTokenLink = "https://graph.facebook.com/oauth/access_token?"
                + "client_id=%s"
                + "&client_secret=%s"
                + "&redirect_uri=%s"
                + "&code=%s";
        accessTokenLink = String.format(accessTokenLink, appID, appSecret, redirecUrl, code);
        String result = NetUtils.GetResult(accessTokenLink);
        String token = result.substring(result.indexOf(":") + 2, result.indexOf(",") - 1);
        return token;
    }

    public UsersDTO getUserInfor() throws MalformedURLException, IOException {
        String infoUrl = "https://graph.facebook.com/v7.0/me?fields=id%2Cname%2Clink%2Cemail&access_token=";
        infoUrl = infoUrl + this.accessToken;

        URL url = new URL(infoUrl);
        JSONTokener tokener = new JSONTokener(url.openStream());
        JSONObject obj = new JSONObject(tokener);
        String facebooId = obj.getString("id");
        String name = obj.getString("name");
        String facebookLink = obj.getString("link");
        UsersDTO user = new UsersDTO();
        user.setFacebookID(facebooId);
        user.setFacebookLink(facebookLink);
        user.setName(name);
        user.setUserID(facebooId);
        return user;
    }
}
