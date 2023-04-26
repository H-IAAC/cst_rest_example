package org.example.ana.codelets;

import br.unicamp.cst.core.entities.Memory;
import br.unicamp.cst.io.rest.HttpCodelet;
import java.io.IOException;

public class WikiAnswering extends HttpCodelet {
    String getURI;
    Memory reactivity;

    public WikiAnswering(String apiURI){
        this.getURI = apiURI + "/whatis/";
    }

    @Override
    public void accessMemoryObjects() {
        if(reactivity == null){
            reactivity = this.getInput("reactivityMemory");
        }
    }

    @Override
    public void calculateActivation() {
        this.activation = 1 - reactivity.getEvaluation();
    }

    @Override
    public void proc() {
        if(reactivity.getEvaluation() == 0.0){
            String request = (String) reactivity.getI();
            String response = " API GET request failed!";
            if(request != null){
                try{
                    response = this.sendGET(this.getURI +request);
                }catch (IOException e){e.printStackTrace();}

                reactivity.setI(null);
                System.out.println(response);
            }

        }


    }
}
