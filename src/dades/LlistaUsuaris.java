package dades;

import excepcions.*;                                /*FALTA TRACTAMENT D'ERRORS!!!! */

public class LlistaUsuaris {
    
    private static final int MIDA_STANDARD = 50;
    
    private Usuari[] llista;   // llista de dades de màquines
    private int num_elem;       // punter al final de la llista


    /**
     * Constructor de llista d'usuaris
     */
    public LlistaUsuaris(){
        llista = new Usuari[MIDA_STANDARD];
        num_elem = 0;   
    }
    
    /**
     * Constructor de llista d'usuaris'
     * @param mida indica la mida de la llista
     */
    public LlistaUsuaris(int mida){
        llista = new Usuari[mida];
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
    public Usuari getUsuari(int index){
        if(index>=0 && index<num_elem){
            return llista[index];
        }
        else{
            return null;                                //POSSIBLE EXCEPTION (CONTROL D'ERRORS PENDENT)
        }
    }

    /**
     * Afegeix una usuari nou a la llista
     * @param nou usuari
     */
    public void afegirUsuari(Usuari nou) {
    
        /*CONTROLAR SI == NULL????*/
    if (llistaPlena()) {
        ampliarCapacitat();
    }

    if(trobarUsuari(nova.getAlias()==-1)){             //no esta previament a la llista / EXEPTION???
        int i=0;

        while((i<num_elem)&&(llista[i].getAlias().compareTo(nova.getAlias())<0)){         //ordenar llista segons el codi
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
     * Escriu les dades de un tipus d'usuari especific
     * @param tipus: 0=tot, 1=estudiants, 2=investigador i 3=empresa
     * @return llista de dades en format text segons el tipus triat
     */
    public String mostrarDadesUsuaris(int tipus){
        String aux = "LLISTA D'USUARIS, amb elements: " + num_elem + " i mida maxima: "+llista.length+"\n";
        
        switch (tipus){
            case 0:	//Mostrar el conjunt de dades de la llista
                for (int i=0; i<num_elem; i++){
                    aux = aux + "\nIndex: "+i+" "+llista[i];
                }

            case 1:	//Eliminar el conjunt de dades d'un municipi
                for (int i=0; i<num_elem; i++){
                    if(llista[i] instanceof Estudiant){
                        aux = aux + "\nIndex: "+i+" "+llista[i];
                    }     
                }

            case 2:	//Mostrar la informació del municipi amb major frac orgànica per habitant
                for (int i=0; i<num_elem; i++){
                    if(llista[i] instanceof Investigador){
                        aux = aux + "\nIndex: "+i+" "+llista[i];
                    }     
                }

            case 3:	//Mostrar els registres d'un municipi, tots els anys
                for (int i=0; i<num_elem; i++){
                    if(llista[i] instanceof ImpressoraEmpresa){
                        aux = aux + "\nIndex: "+i+" "+llista[i];
                    }     
                }
        }
        
        return aux;
    }

    /**
     * Mostra les dades d'un usuari segons el seu alias
     * @param alias de l'usuari que es vol tractar
     * @return dades de l'usuari, null si no existeix                     EXEPTION????
     */
    public String mostrarUsuari(String alias){
        String aux = null;

        int index = trobarUsuari(alias);
        if(index != -1){                                                        /*POSSIBLE TRACTAMENT D'ERRORS */
            aux = "USUARI: " +llista[i]+"\n";
        }
              
        return aux;
    }

    /*public Usuari mesHores(int tipus){
        Usuari max;
        
        FALTA CODI DE MÈTODE: Calcular usuari d’un cert tipus que ha fet servir més hores de màquina 
              
        return max;
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
     * Comprova si hi ha un usuari a la llista amb el alias
     * @param alias de l'usuari a tractar
     * @return index de la posicio de l'usuari
     */
    private int trobarUsuari(String alias){
        int i = 0;
        boolean trobat = false;
        String aux = null;

        while(i<num_elem && !trobat){
            if(llista[i].getAlias().equalsIgnoreCase(alias)){
                trobat = true;
            }
            else{i++;}
        }
        if (!trobat){i=-1;}                 /*POSSIBLE TRACTAMENT D'ERRORS */
        
        return i;
    }

    /**
     * Amplia la capacitat d'una llista d'usuaris al doble
     */
    private boolean ampliarCapacitat(){
        LlistaUsuaris[] nova = new LlistaUsuaris[llista.length * 2];      //augmenta la capacitat al doble
        
        for (int i = 0; i < num_elem; i++) {
            nou[i] = llista[i];
        }
        
        llista = nova;
    }

}