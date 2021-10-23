package com.criiscz.evaart_reto.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class EncryptUtils {
    public static String decrypt(String code) throws IOException {
        String url = "https://test.evalartapp.com/extapiquest/code_decrypt/" + code;
        return webServiceReader(url).replace("\"","");
    }

    private static String webServiceReader(String url) throws IOException {

        URL urlObj = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) urlObj.openConnection();
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;

        StringBuilder response = new StringBuilder();
        while((inputLine = reader.readLine()) != null){
            response.append(inputLine);
        }
        reader.close();
        return response.toString();
    }
}
