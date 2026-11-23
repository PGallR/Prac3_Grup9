package dades;

import java.io.Serializable;

import javax.management.openmbean.OpenDataException;

import excepcions.*;
import extras.Data;

public class Treball {
    
    public static final String MATERIAL_FILAMENT = "filament";
    public static final String MATERIAL_RESINA = "resina";
    public static final String MATERIAL_CAP = "cap";

    public static final String ESTAT_PENDENT = "pendent";
    public static final String ESTAT_EN_CURS = "en curs";
    public static final String ESTAT_ACABAT = "acabat";

    private String codi;
    private String material;
    private String estat;
    private Data dataSolicitud;
    private Data dataFinalitzacio;
    private double duradaH;
    private double quantitatMaterial;
    //private Maquina maquina;
    //private Usuari usuari;

    public Treball(String codi, /*Maquina maquina, Usuari usuari,*/ String material, 
                    Data dataSolicitud, double duradaH, double quantitatMaterial) {
        
        if (codi == null || codi.trim().isEmpty()) {
            throw new TreballNoValidException("El treball ha de tenir una màquina i un usuari assignats.");
        }

        if (dataSolicitud == null) {
            throw new TreballNoValidException("La data de sol·licitud no pot ser nul·la.");
        }

        if (duradaH <= 0) {
            throw new TreballNoValidException("La durada del treball ha de ser superior a 0 hores.");
        }

        if (quantitatMaterial < 0) {
            throw new TreballNoValidException("La quantitat de material no pot ser negativa.");
        }

        this.codi = codi;
        this.maquina = maquina;
        this.usuari = usuari;
        this.material = material;
        this.dataSolicitud = dataSolicitud;
        this.duradaH = duradaH;
        this.quantitatMaterial = quantitatMaterial;
        this.estat = ESTAT_PENDENT;
        this.dataFinalitzacio = null;

    }

    public void iniciarTreball() throws OperacioTreballNoPermesaException {
        if (!this.estat.equals(ESTAT_PENDENT)) {
            throw new OperacioTreballNoPermesaException("Nomes es pot iniciar un treball que estigui pendent. Estat actual: " + this.estat);
        }
        this.estat = ESTAT_EN_CURS;
    }

    public void finalitzarTreball(Data dataFi) throws OperacioTreballNoPermesaException {
        if (!this.estat.equals(ESTAT_EN_CURS)) {
            throw new OperacioTreballNoPermesaException("Nomes es pot finalitzar un treball que estigui en curs. Estat actual: " + this.estat);
        }
        if (dataFi == null || dataFi.esDataInferior(this.dataSolicitud)) {
            throw new OperacioTreballNoPermesaException("La data de finalitzacio no pot s er anterior a la data de sol·licitud");
        }

        this.estat = ESTAT_ACABAT;
        this.dataFinalitzacio = dataFi.copia();
    }

    //public double calcularImportFinal() {
        
    //}


    public int calcularDiesEspera(Data dataActual) {
        int diesEspera = 0;
        if (dataActual == null) {
            try {
                int dies = this.dataSolicitud.numDiesAData(dataActual);
                if (dies > 0) {
                    diesEspera = dies;
                }
            } catch (ValorInexistent e) {
            diesEspera = 0;
            }
        }
        return diesEspera;
    }
     
    // Getters

    public String getCodi() {
        return codi;
    }

    //public Maquina getMaquina() {
    //    return maquina;
    //}

    //public Usuari getUsuari() {
    //    return usuari;
    //}

    public String getMaterial() {
        return material;
    }

    public Data getDataSolicitud() {
        return dataSolicitud.copia();
    }

    public double getDuradaHores() {
        return duradaH;
    }

    public double getQuantitatMaterial() {
        return quantitatMaterial;
    }

    public String getEstat() {
        return estat;
    }

    public Data getDataFinalitzacio() {
        Data copiaData = null;
        if (dataFinalitzacio != null) {
            copiaData = dataFinalitzacio.copia();
        }
        return copiaData;
    }

    @Override 
    public String toString() {
        String infoFi = "";
        if (dataFinalitzacio != null) {
            infoFi = " Finalitzat: " + dataFinalitzacio.toString();
        }

        return "Treball [" + codi + "]  - Maquina: " + maquina.getCodi() + 
                " | Usuari: " + usuari + 
                " | Material: " + material + " (" + quantitatMaterial + ") " +
                " | Durada: " + duradaH + "h " + 
                " | Sol·licitat: " + dataSolicitud.toString() + 
                " | Estat: " + estat.toUpperCase() + infoFi;
    }
} 


