package org.example.ana.codelets;

import br.unicamp.cst.core.entities.Codelet;
import br.unicamp.cst.core.entities.Memory;

import java.util.HashMap;

public class Reactivity extends Codelet {
    Memory sensoryMemory;
    Memory reactivityMemory;

    @Override
    public void accessMemoryObjects() {
        if(sensoryMemory == null){
             sensoryMemory = this.getInput("sensoryMemory");
        }
        if( reactivityMemory == null){
            reactivityMemory = this.getOutput("reactivityMemory");
        }
    }

    @Override
    public void proc() {
        Object info = sensoryMemory.getI();
        if(info != null){
            HashMap<String, Object> command = (HashMap<String, Object>) info;
            if(command.containsKey("question")){
                reactivityMemory.setEvaluation(0.0);
                reactivityMemory.setI(command.get("question"));
            }
            else if(command.containsKey("translate")){
                reactivityMemory.setEvaluation(1.0);
                reactivityMemory.setI(command.get("translate"));
            }
            else{
                reactivityMemory.setI(null);
            }
        }
    }

    @Override
    public void calculateActivation() {}
}
