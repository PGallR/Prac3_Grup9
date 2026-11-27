package llistes;

import excepcions.*;
import extras.*;
import dades.*;
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


    /**
     * Constructor Llista Treball
     */
    public LlistaTreball() {
        this.llista = new Treball[CAPACITAT_INICIAL];
        this.numElements = 0;
    }


    /**
     * Metode que retorna el numero d'elements a la llista
     * 
     * @return int; num elements a la llista
     */
    public int getNumElements() {
        return numElements;
    }


    /**
     * Metode que permet obtenir el Treball demanat
     * 
     * @param index posicio de la dada que es vol consultar
     * @return retorna les dades Treball, sino null
     * @throws TreballNoValidException
     */
    public Treball getTreball(int index) throws TreballNoValidException {

        if (index >= 0 && index < numElements) {
            return llista[index];
        } else {
            throw new TreballNoValidException("No hi ha cap treball");
        }
    }


    /**
     * Metode que amplia la capacitat de la llista
     */
    private void ampliarCapacitat() {
        Treball[] nou = new Treball[llista.length * 2];
        for (int i = 0; i < numElements; i++) {
            nou[i] = llista[i];
        }
        llista = nou;
    }


    /**
     * Metode que permet afegir un treball a la llista
     * 
     * @param nouTreball treball a afegir
     * @throws TreballNoValidException
     */
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

    
    /**
     * Metode que cerca un treball per el seu codi identificador
     * 
     * @param codi codi del treball a buscar
     * @return l'objecte Treball trobat, sino null
     */
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


    /**
     * Metode que elimina un treball de la llista que estigui en estat 'penndent'
     * 
     * @param codiTreball codi del treball que es vol cancel·lar
     * @throws OperacioTreballNoPermesaException 
     */
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


    /**
     * Metode que compta quants treballs te un usuari en estat 'pendent' o 'en curs'
     * 
     * @param aliesUsuari alies del usuari
     * @return int; nombre total de treballs pendents/en curs
     */
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


    /**
     * Metode que retorna el treball que esta en estat 'en curs' en una maquina concreta
     * 
     * @param codiMaquina codi de la maquina
     * @return el treball en curs, sino null si la maquina es lliure
     */
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


    /**
     * Metode que retorna el primer treball 'pendent' d'una maquina segons l'ordre de sol·licitud
     * 
     * @param codiMaquina codi de la maquina
     * @return el treball pendent, o null si no n'hi ha
     */
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


    /**
     * Metode que inicia el seguent treball pendent d'una maquina
     * 
     * @param codiMaquina codi de la maquina
     * @return el treball que passa en estat 'en curs'
     * @throws OperacioTreballNoPermesaException
     */
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


    /**
     * Metode que finalitza el treball que esta en curs de la maquina
     * 
     * @param codiMaquina codi de la maquina
     * @param dataFi dara i hora en que s'acaba el treball
     * @return treball finalitzat
     * @throws OperacioTreballNoPermesaException
     */
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


    /**
     * Metode que calcula el temps total d'espera d'una maquina sumant les hores
     * del treball en curs i tots els treballs pendents
     * @param codiMaquina codi de la mquina
     * @return total d'hores acumulades 
     */
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


    /**
     * Metode que retorna una subllista amb els treballs pendents d'una maquina ordenats per data de sol·licitud
     * 
     * @param codiMaquina codi de la maquina
     * @return nova llistaTreball amb els treballs pendents 
     */
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


    /**
     * Metode que retorna una subllista amb els treballs pendents o en curs d'un usuari
     * 
     * @param aliesUsuari alies de l'usuari
     * @return nova llistaTreballs amb els treballs actius del usuari
     */
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

    /**
     * Metode que retorna tots els treballs en estat 'pendent' ordenats per sol·licitud
     * 
     * @return nova llistaTreballs amb tots els treballs pendents
     */
    public LlistaTreball obtenirTotsPendents() {
        LlistaTreball pendents = new LlistaTreball();
        for (int i = 0; i < numElements; i++) {
            if (llista[i].getEstat().equals(Treball.ESTAT_PENDENT)) {
                pendents.inserirDirecte(llista[i]);
            }
        }
        return pendents;
    }


    /**
     * Metode que retorna els treballs acabats d'un usuari en un mes i any concrets
     * 
     * @param aliesUsuari alies del usuari
     * @param mes mes del treball (1-12)
     * @param any any del treball
     * @return nova llistaTreballs amb els treballs acabats en aquell periode de temps 
     */
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


    /**
     * Metode que retorna els treballs acabats entre dues dates per calcular la recaptacio
     * 
     * @param inici data i hora inicial del rang
     * @param fi data i hora final del rang
     * @return nova llistaTreballs amb els treballs acabats dins del periode marcat
     */
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


    /**
     * Metode que calcula les hores d'us d'una maquina concreta a partir dels treballs acabats entre dos dates
     * 
     * @param codiMaquina codi numeric de la maquina
     * @param inici data i hora inicial del rang
     * @param fi data i hora final del rang
     * @return total hores d'us
     */
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


    /**
     * Metode que calcula les hores totals utilitzades per a un usuari en treballs acabats
     * 
     * @param aliesUsuari alies del usuari
     * @return total d'hores acumulades
     */
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


    /**
     * Metode intern per omplir subllistes de resultats
     * 
     * @param t treball a inserir de la subllista
     */
    private void inserirDirecte(Treball t) {
        if (numElements == llista.length) {
            ampliarCapacitat();
        }
        llista[numElements] = t;
        numElements++;
    }


    /**
     * Metode que guarda tota la llisat de treballs en un fitxer binari serialitzat
     * 
     * @param rutaFitxer cami del fitxer de sortida
     * @throws IOException
     */
    public void guardarSerialitzat(String rutaFitxer) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(rutaFitxer))) {
            oos.writeObject(this);
        }
    }


    /**
     * Metode que carrega la llista de treballs des d'un fitxer binari serialitzat
     * 
     * @param rutaFitxer cami del fitxer d'entrada
     * @return instancia de llistaTreballs recuperada del fitxer 
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static LlistaTreball carregarSerialitzat(String rutaFitxer)
            throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(rutaFitxer))) {
            return (LlistaTreball) ois.readObject();
        }
    }


}
