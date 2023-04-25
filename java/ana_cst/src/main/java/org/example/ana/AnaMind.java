package org.example.ana;

import br.unicamp.cst.core.entities.MemoryObject;
import br.unicamp.cst.core.entities.Mind;
import org.example.ana.codelets.GermanTranslator;
import org.example.ana.codelets.QASensory;
import org.example.ana.codelets.Reactivity;
import org.example.ana.codelets.WikiAnswering;

import java.util.List;
import java.util.Map;

public class AnaMind extends Mind {
    String apiURL;

    public AnaMind(String apiURI, String pathToJson){
        this.apiURL = apiURI;
        this.setupMind(pathToJson);
    }

    private void setupMind(String pathToJson){
        //declaring memories
        MemoryObject sensoryMemory = createMemoryObject("sensoryMemory");
        MemoryObject reactivityMemory = createMemoryObject("reactivityMemory");

        //declaring, mounting and inserting Codelets
        QASensory qaSensory = new QASensory(pathToJson);
        qaSensory.addOutput(sensoryMemory);
        this.insertCodelet(qaSensory);

        Reactivity reactivity = new Reactivity();
        reactivity.addInput(sensoryMemory);
        reactivity.addOutput(reactivityMemory);
        this.insertCodelet(reactivity);

        WikiAnswering wikiAnswering = new WikiAnswering(this.apiURL);
        wikiAnswering.addInput(reactivityMemory);
        this.insertCodelet(wikiAnswering);

        GermanTranslator germanTranslator = new GermanTranslator(this.apiURL);
        germanTranslator.addInput(reactivityMemory);
        this.insertCodelet(germanTranslator);

    }
}
