package dades;

import extra.Data;
import java.io.Serializable;

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


}
