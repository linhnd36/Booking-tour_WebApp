/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package linhnd.utils;

import java.io.IOException;
import org.apache.http.client.fluent.Request;

/**
 *
 * @author PC
 */
public class NetUtils {

    public static String GetResult(String url) {
        try {
            return Request.Get(url).setHeader("Accept-Charset", "UTF-8").execute()
                    .returnContent().asString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
