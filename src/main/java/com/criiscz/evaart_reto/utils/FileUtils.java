package com.criiscz.evaart_reto.utils;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;

public class FileUtils {
    public static String readFile(String filename) throws IOException, URISyntaxException {
        FileReader fileReader = new FileReader(getFileFromPath(filename));
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        StringBuilder content = new StringBuilder();
        boolean flag = false;
        while(!flag){
            String line = bufferedReader.readLine();
            if(line == null) flag = true;
            else{
                content.append(line);
                content.append('\n');
            }
        }
        return content.toString();
    }

    private static File getFileFromPath(String path) throws URISyntaxException {
        ClassLoader classLoader = FileUtils.class.getClassLoader();
        URL resource = classLoader.getResource(path);

        if (resource == null) {
            throw new IllegalArgumentException("File not found! " + path);
        } else {
            return new File(resource.toURI());
        }
    }

    public static void main(String[] args) {
        try {
            System.out.println(Arrays.toString(FileUtils.readFile("input.txt").replaceFirst("<","").split("<")[1].replace(">","").split("\n")));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
