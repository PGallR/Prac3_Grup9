package dades;

import excepcions.*;
import extra.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class LListaTreball implements Serializable {
    private static final int CAPACITAT_INICIAL = 50;

    private Treball[] llista;
    private int numElements;


    public LListaTreball() {
        this.llista = new Treball[CAPACITAT_INICIAL];
        this.numElements = 0;
    }


    public int getNumElements() {
        return numElements;
    }

    public Treball obtenirT (int index) {
        Treball resultat = null;
        if (index >= 0 && index < numElements) {
            resultat = llista[index];
        }
        return resultat;
    }


    private void ampliarCapacitat() {
        Treball[] nou = new Treball[llista.length * 2];
        for (int i = 0; i < numElements; i++) {
            nou[i] = llista[i];
        }
        llista = nou;
    }

    
}
