package dades;

import excepcions.*;
import extras.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class LlistaTreball implements Serializable {
    private static final int CAPACITAT_INICIAL = 50;

    private Treball[] llista;
    private int numElements;


    public LlistaTreball() {
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


    public void afegirTreball(Treball nouTreball) throws TreballNoValidException {
        if (nouTreball == null) {
            throw new TreballNoValidException("No es pot afegir un treball nul.");
        }
        if (cercarCodi(nouTreball.getCodi()) != null) {
            throw new TreballNoValidException("Ja existeix un treball amb el codi: " + nouTreball.getCodi());
        }

        Usuari u = nouTreball.getUsuari();
        int pendentsActuals = comptarTreballsPendentsUsuari(u.getAlias());
        if (pendentsActuals >= u.getMaxTreballsPendents()) {
            throw new TreballNoValidException("L'usuari " + u.getAlias()
                    + " ja té el màxim permès de treballs pendents (" + u.getMaxTreballsPendents() + ").");
        }

        if (numElements == llista.length) {
            ampliarCapacitat();
        }

        int pos = numElements - 1;
        while (pos >= 0 && nouTreball.getDataSolicitud().esDataInferior(llista[pos].getDataSolicitud())) {
            llista[pos + 1] = llista[pos];
            pos--;
        }
        llista[pos + 1] = nouTreball;
        numElements++;
    }

    
    public Treball cercarCodi(String codi) {
        Treball trobat = null;
        if (codi != null) {
            int i = 0;
            while (i < numElements && trobat == null) {
                if (llista[i].getCodi().equalsIgnoreCase(codi)) {
                    trobat = llista[i];
                }
                i++;
            }
        }
        return trobat;
    }

    public void cancelarTreballPendent(String codiTreball) throws OperacioTreballNoPermesaException {
        int index = -1;
        int i = 0;
        while (i < numElements && index == -1) {
            if (llista[i].getCodi().equalsIgnoreCase(codiTreball)) {
                index = i;
            }
            i++;
        }

        if (index == -1) {
            throw new OperacioTreballNoPermesaException("No s'ha trobat cap treball amb codi: " + codiTreball);
        }
        if (!llista[index].getEstat().equals(Treball.ESTAT_PENDENT)) {
            throw new OperacioTreballNoPermesaException(
                    "Només es poden cancel·lar treballs pendents. Aquest treball està: " + llista[index].getEstat());
        }

        for (int j = index; j < numElements - 1; j++) {
            llista[j] = llista[j + 1];
        }
        llista[numElements - 1] = null;
        numElements--;
    }


    public int comptarTreballsPendentsUsuari(String aliesUsuari) {
        int comptador = 0;
        for (int i = 0; i < numElements; i++) {
            Treball t = llista[i];
            if (t.getUsuari().getAlias().equalsIgnoreCase(aliesUsuari)
                    && (t.getEstat().equals(Treball.ESTAT_PENDENT) || t.getEstat().equals(Treball.ESTAT_EN_CURS))) {
                comptador++;
            }
        }
        return comptador;
    }


    public Treball obtenirTreballEnCurs(int codiMaquina) {
        Treball enCurs = null;
        int i = 0;
        while (i < numElements && enCurs == null) {
            Treball t = llista[i];
            if (t.getMaquina().getCodi() == codiMaquina 
                    && t.getEstat().equals(Treball.ESTAT_EN_CURS)) {
                enCurs = t;
            }
            i++;
        }
        return enCurs;
    }


    public Treball obtenirSeguentTreballPendent(int codiMaquina) {
        Treball seguent = null;
        int i = 0;
        while (i < numElements && seguent == null) {
            Treball t = llista[i];
            if (t.getMaquina().getCodi() == codiMaquina
                    && t.getEstat().equals(Treball.ESTAT_PENDENT)) {
                seguent = t;
            }
            i++;
        }
        return seguent;
    }

    public Treball iniciarSeguentTreballMaquina(int codiMaquina) throws OperacioTreballNoPermesaException {
        if (obtenirTreballEnCurs(codiMaquina) != null) {
            throw new OperacioTreballNoPermesaException(
                    "La màquina " + codiMaquina + " ja té un treball en curs.");
        }
        Treball seguent = obtenirSeguentTreballPendent(codiMaquina);
        if (seguent == null) {
            throw new OperacioTreballNoPermesaException(
                    "La màquina " + codiMaquina + " no té cap treball pendent a la cua.");
        }
        seguent.iniciarTreball();
        return seguent;
    }

    public Treball finalitzarTreballActualMaquina(int codiMaquina, Data dataFi)
            throws OperacioTreballNoPermesaException {
        Treball enCurs = obtenirTreballEnCurs(codiMaquina);
        if (enCurs == null) {
            throw new OperacioTreballNoPermesaException(
                    "La màquina " + codiMaquina + " no té cap treball en curs per finalitzar.");
        }
        enCurs.finalitzarTreball(dataFi);
        return enCurs;
    }


    public double calcularTempsEsperaMaquina(int codiMaquina) {
        double horesTotals = 0.0;
        for (int i = 0; i < numElements; i++) {
            Treball t = llista[i];
            if (t.getMaquina().getCodi() == codiMaquina
                    && (t.getEstat().equals(Treball.ESTAT_EN_CURS) || t.getEstat().equals(Treball.ESTAT_PENDENT))) {
                horesTotals += t.getDuradaHores();
            }
        }
        return horesTotals;
    }


    public LlistaTreball obtenirPendentsMaquina(int codiMaquina) {
        LlistaTreball resultat = new LlistaTreball();
        for (int i = 0; i < numElements; i++) {
            Treball t = llista[i];
            if (t.getMaquina().getCodi() == codiMaquina
                    && t.getEstat().equals(Treball.ESTAT_PENDENT)) {
                resultat.inserirDirecte(t);
            }
        }
        return resultat;
    }


    public LlistaTreball obtenirPendentsUsuari(String aliesUsuari) {
        LlistaTreball resultat = new LlistaTreball();
        for (int i = 0; i < numElements; i++) {
            Treball t = llista[i];
            if (t.getUsuari().getAlias().equalsIgnoreCase(aliesUsuari)
                    && (t.getEstat().equals(Treball.ESTAT_PENDENT) || t.getEstat().equals(Treball.ESTAT_EN_CURS))) {
                resultat.inserirDirecte(t);
            }
        }
        return resultat;
    }

    public LlistaTreball obtenirTotsPendents() {
        LlistaTreball pendents = new LlistaTreball();
        for (int i = 0; i < numElements; i++) {
            if (llista[i].getEstat().equals(Treball.ESTAT_PENDENT)) {
                pendents.inserirDirecte(llista[i]);
            }
        }
        return pendents;
    }


    public LlistaTreball obtenirTreballsAcabatsUsuariMes(String aliesUsuari, int mes, int any) {
        LlistaTreball factura = new LlistaTreball();
        for (int i = 0; i < numElements; i++) {
            Treball t = llista[i];
            Data dFi = t.getDataFinalitzacio();
            if (t.getEstat().equals(Treball.ESTAT_ACABAT)
                    && t.getUsuari().getAlias().equalsIgnoreCase(aliesUsuari)
                    && dFi != null
                    && dFi.getMes() == mes
                    && dFi.getAny() == any) {
                factura.inserirDirecte(t);
            }
        }
        return factura;
    }


    public LlistaTreball obtenirTreballsAcabatsEntreDates(Data inici, Data fi) {
        LlistaTreball acabats = new LlistaTreball();
        for (int i = 0; i < numElements; i++) {
            Treball t = llista[i];
            Data dFi = t.getDataFinalitzacio();
            if (t.getEstat().equals(Treball.ESTAT_ACABAT)
                    && dFi != null
                    && !dFi.esDataInferior(inici)
                    && !fi.esDataInferior(dFi)) {
                acabats.inserirDirecte(t);
            }
        }
        return acabats;
    }


    public double calcularHoresUsMaquinaEntreDates(int codiMaquina, Data inici, Data fi) {
        double hores = 0.0;
        for (int i = 0; i < numElements; i++) {
            Treball t = llista[i];
            Data dFi = t.getDataFinalitzacio();
            if (t.getEstat().equals(Treball.ESTAT_ACABAT)
                    && t.getMaquina().getCodi() == codiMaquina
                    && dFi != null
                    && !dFi.esDataInferior(inici)
                    && !fi.esDataInferior(dFi)) {
                hores += t.getDuradaHores();
            }
        }
        return hores;
    }


    public double calcularHoresTotalsUsuari(String aliesUsuari) {
        double hores = 0.0;
        for (int i = 0; i < numElements; i++) {
            Treball t = llista[i];
            if (t.getEstat().equals(Treball.ESTAT_ACABAT)
                    && t.getUsuari().getAlias().equalsIgnoreCase(aliesUsuari)) {
                hores += t.getDuradaHores();
            }
        }
        return hores;
    }


    private void inserirDirecte(Treball t) {
        if (numElements == llista.length) {
            ampliarCapacitat();
        }
        llista[numElements] = t;
        numElements++;
    }


    public void guardarSerialitzat(String rutaFitxer) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(rutaFitxer))) {
            oos.writeObject(this);
        }
    }

    public static LlistaTreball carregarSerialitzat(String rutaFitxer)
            throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(rutaFitxer))) {
            return (LlistaTreball) ois.readObject();
        }
    }


}
