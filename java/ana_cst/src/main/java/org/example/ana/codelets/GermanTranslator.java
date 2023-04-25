package org.example.ana.codelets;

import br.unicamp.cst.core.entities.Memory;
import br.unicamp.cst.io.rest.HttpCodelet;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class GermanTranslator extends HttpCodelet {
    HashMap<String, String> params = new HashMap<>();
    String postURI;
    Memory reactivity;

    public GermanTranslator(String apiURI){
        this.postURI = apiURI + "/translate/";
    }

    @Override
    public void accessMemoryObjects() {
        if(reactivity == null){
            reactivity = this.getInput("reactivityMemory");
        }
        params.put("en_text", "");
    }

    @Override
    public void calculateActivation() {
        this.activation = reactivity.getEvaluation();
    }

    @Override
    public void proc() {
        if(reactivity.getEvaluation() == 1.0){
            List request = (List) reactivity.getI();
            String response = " API request failed!";
            if(request != null){
                String json = new Gson().toJson(request );
                params.replace("en_text", json);
                String paramsString = prepareParams(params);
                System.out.println(paramsString);
                try{
                    response = this.sendPOST(this.postURI, paramsString);
                }catch (IOException e){e.printStackTrace();}
            }
            reactivity.setI(null);
            System.out.println(response);
        }
    }
}
