package org.example;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.example.ana.AnaMind;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        String apiURI = "http://127.0.0.1:5000";
        String pathToJson = "/home/wander/Projects/cst_pack/cst_rest_example/java/ana_cst/src/main/resources/tasks.json";


        AnaMind anaMind = new AnaMind(apiURI, pathToJson);
        // start
        anaMind.start();

        try{
            Thread.sleep(10000); // 10 secs
        }catch (Exception e){ e.printStackTrace();}

        //stop
        anaMind.shutDown();
    }
}