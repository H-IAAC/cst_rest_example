package org.example.ana.codelets;

import br.unicamp.cst.core.entities.Codelet;
import br.unicamp.cst.core.entities.Memory;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class QASensory extends Codelet {
    Memory sensoryMemory;
    List<HashMap<String, Object>> tasks;


    public QASensory(String pathToJson){
        this.tasks = getTasks(pathToJson);
        this.timeStep = 2000;

    }

    @Override
    public void accessMemoryObjects() {
        if(sensoryMemory == null){
            sensoryMemory = this.getOutput("sensoryMemory");
        }
    }

    @Override
    public void calculateActivation() {

    }

    @Override
    public void proc() {
        if(tasks.size() != 0){
            HashMap<String, Object> task = tasks.get(0);
            sensoryMemory.setI(task);
            tasks.remove(0);
        }
        else{sensoryMemory.setI(null);}
    }

    List<HashMap<String, Object>> getTasks(String pathToJson){
        //tasks
        try {
            // create Gson instance
            Gson gson = new Gson();

            // create a reader
            Reader reader = Files.newBufferedReader(Paths.get(pathToJson));

            // convert JSON array to list of users
            //List<HashMap<String, String>> tasks = new Gson().fromJson(reader, new TypeToken<List<HashMap<String, String>>>() {}.getType());
            HashMap<String, List<HashMap<String, Object>>> hashtasks = new Gson().fromJson(reader, new TypeToken<HashMap<String, List<HashMap<String, Object>>>>() {}.getType());

            tasks = hashtasks.get("tasks");
            // print users
            //users.forEach(System.out::println);

            // close reader
            reader.close();
            //System.out.println(tasks.size());
            return tasks;

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
