package dades;

import excepcions.*;                                /*FALTA TRACTAMENT D'ERRORS!!!! */
import extras.*;

public class LlistaMaquines {
    
    private static final int MIDA_STANDARD = 50;
    
    private Maquina[] llista;   // llista de dades de màquines
    private int num_elem;       // punter al final de la llista


    /**
     * Constructor de llista de maquines
     */
    public LlistaMaquines(){
        llista = new Maquina[MIDA_STANDARD];
        num_elem = 0;   
    }
    
    /**
     * Constructor de llista de maquines
     * @param mida indica la mida de la llista
     */
    public LlistaMaquines(int mida){
        llista = new Maquina[mida];
        num_elem = 0;   
    }

    /**
     * Retorna el nombre d'elements dins la llista
     * @return nombre d'elements enregistrats a la llista
     */
    public int getNumElem(){
        return num_elem;
    }

   /**
     * Permet obtenir l'Nessim element de la llista de maquines
     * @param index rep posicio de les dades que es vol consultar
     * @return retorna les dades, null si es troba fora de rang
     */
    public Maquina getMaquina(int index){
        if(index>=0 && index<num_elem){
            return llista[index];
        }
        else{
            return null;                                //POSSIBLE EXCEPTION (CONTROL D'ERRORS PENDENT)
        }
    }

    /**
     * Afegeix una impressora de filament a la llista
     * @param nova impressora 3D de filament a afegir
     */
    public void afegirImpressoraFilament(ImpressoraFilament nova) {
    
        /*CONTROLAR SI == NULL????*/
    if (llistaPlena()) {
        ampliarCapacitat();
    }

    if(trobarCodi(nova.getCodi()==-1)){             //no esta previament a la llista / EXEPTION???
        int i=0;

        while((i<num_elem)&&(llista[i].getCodi().compareTo(nova.getCodi())<0)){         //ordenar llista segons el codi
            i++;
        }
    }

    for (int j = numElements; j > pos; j--) {       //desplaçar elements
        elements[j] = elements[j - 1];
    }

    elem[i] = nova.copia();
    num_elem++;
    }

    /**
     * Afegeix una impressora de resina a la llista
     * @param nova impressora 3D de resina a afegir
     */
    public void afegirImpressoraResina(ImpressoraResina nova) {
    
        /*CONTROLAR SI == NULL????*/
    if (llistaPlena()) {
        ampliarCapacitat();
    }

    if(trobarCodi(nova.getCodi()==-1)){             //no esta previament a la llista / EXEPTION???
        int i=0;

        while((i<num_elem)&&(llista[i].getCodi().compareTo(nova.getCodi())<0)){         //ordenar llista segons el codi
            i++;
        }
    }

    for (int j = numElements; j > pos; j--) {       //desplaçar elements
        elements[j] = elements[j - 1];
    }

    elem[i] = nova.copia();
    num_elem++;
    }

    /**
     * Afegeix una talladora laser a la llista
     * @param nova impressora laser a afegir
     */
    public void afegirImpressoraLaser(ImpressoraLaser nova) {
    
        /*CONTROLAR SI == NULL????*/
    if (llistaPlena()) {
        ampliarCapacitat();
    }

    if(trobarCodi(nova.getCodi()==-1)){             //no esta previament a la llista / EXEPTION???
        int i=0;

        while((i<num_elem)&&(llista[i].getCodi().compareTo(nova.getCodi())<0)){         //ordenar llista segons el codi
            i++;
        }
    }

    for (int j = num_elem; j > i; j--) {       //desplaçar elements
        llista[j] = llista[j - 1];
    }

    llista[i] = nova.copia();
    num_elem++;
    }

    /**
     * Escriu les dades de un tipus de maquina especific
     * @param tipus: 0=tot, 1=filament, 2=resina i 3=làser
     * @return llista de dades en format text segons el tipus triat
     */
    public String mostrarDadesMaquines(int tipus){
        String aux = "LLISTA DE MAQUINES, amb elements: " + num_elem + " i mida maxima: "+llista.length+"\n";
        
        switch (tipus){
            case 0:	//Mostrar el conjunt de dades de la llista
                for (int i=0; i<num_elem; i++){
                    aux = aux + "\nIndex: "+i+" "+llista[i];
                }

            case 1:	//Eliminar el conjunt de dades d'un municipi
                for (int i=0; i<num_elem; i++){
                    if(llista[i] instanceof ImpressoraFilament){
                        aux = aux + "\nIndex: "+i+" "+llista[i];
                    }     
                }

            case 2:	//Mostrar la informació del municipi amb major frac orgànica per habitant
                for (int i=0; i<num_elem; i++){
                    if(llista[i] instanceof ImpressoraResina){
                        aux = aux + "\nIndex: "+i+" "+llista[i];
                    }     
                }

            case 3:	//Mostrar els registres d'un municipi, tots els anys
                for (int i=0; i<num_elem; i++){
                    if(llista[i] instanceof ImpressoraLaser){
                        aux = aux + "\nIndex: "+i+" "+llista[i];
                    }     
                }
        }
        
        return aux;
    }

    /**
     * Mostra les dades de una maquina segons el seu codi
     * @param codi de la màquina que es vol tractar
     * @return dades de la maquina, null si no existeix                     EXEPTION????
     */
    public String mostrarMaquina(int codi){
        String aux = null;

        int index = trobarCodi(codi);
        if(index != -1){                                                        /*POSSIBLE TRACTAMENT D'ERRORS */
            aux = "MAQUINA: " +llista[i]+"\n";
        }
              
        return aux;
    }

    /*public Data horesUs(Data d1, Data d2){
        Data hores;
        
        FALTA CODI DE MÈTODE: Mostrar les hores d’ús entre dues dates 
              
        return hores;
    } */


    /*------------------------------------- PRIVATES ----------------------------------------------- */

    /**
     * Comprova si la mida de la llista és igual al nombre d'elements
     * @return si la llista està plena
     */
    private boolean llistaPlena(){
        return (llista.length==num_elem);
    }

    /**
     * Comprova si hi ha una màquina a la llista amb el codi
     * @param codi de la maquina a tractar
     * @return index de la posicio de la maquina
     */
    private int trobarCodi(int codi){
        int i = 0;
        boolean trobat = false;
        String aux = null;

        while(i<num_elem && !trobat){
            if(llista[i].getCodi() == codi){
                trobat = true;
            }
            else{i++;}
        }
        if (!trobat){i=-1;}                 /*POSSIBLE TRACTAMENT D'ERRORS */
        
        return i;
    }

    /**
     * Amplia la capacitat d'una llista de màquines al doble
     */
    private boolean ampliarCapacitat(){
        LlistaMaquines[] nova = new LlistaMaquines[llista.length * 2];      //augmenta la capacitat al doble
        
        for (int i = 0; i < num_elem; i++) {
            nou[i] = llista[i];
        }
        
        llista = nova;
    }

}