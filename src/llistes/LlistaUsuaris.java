package llistes;

import dades.Empresa;
import dades.Estudiant;
import dades.Investigador;
import dades.Usuari;
import excepcions.*;

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
     * Permet obtenir l'Nessim element de la llista de usuaris
     * @param index rep posicio de les dades que es vol consultar
     * @return retorna les dades
     */
    public Usuari getUsuari(int index) throws UsuariNoValidException{
        if(index>=0 && index<num_elem){
            return llista[index];
        }
        else{
            throw new UsuariNoValidException("No hi ha cap usuari que correspon.\n"); 
        }
    }

    /**
     * Afegeix una usuari nou a la llista
     * @param nou usuari
     */
    public void afegirUsuari(Usuari nova) throws UsuariNoValidException{
    int i=0;

    if (nova == null){
        throw new UsuariNoValidException("No hi ha cap usuari que correspon.\n"); 
    }else{
        if (llistaPlena()) {
            ampliarCapacitat();
        }

        if((trobarUsuari(nova.getAlias()))==-1){
            i=0;

            while((i<num_elem)&&(llista[i].getAlias().compareTo(nova.getAlias())<0)){         //ordenar llista segons el codi
                i++;
            }
        }

        for (int j = num_elem; j > i; j--) {       //desplaçar elements
            llista[j] = llista[j - 1];
        }

        llista[i] = nova; //.copia();
        num_elem++;
        }
    
    }

    
    /**
     * Escriu les dades de un tipus d'usuari especific
     * @param tipus: 0=tot, 1=estudiants, 2=investigador i 3=empresa
     * @return llista de dades en format text segons el tipus triat
     */
    public String mostrarDadesUsuaris(int tipus){
        String aux = "LLISTA D'USUARIS, amb elements: " + num_elem + " i mida maxima: "+llista.length+"\n";
        
        switch (tipus){
            case 0:	
                for (int i=0; i<num_elem; i++){
                    aux = aux + "\nIndex: "+i+" "+llista[i];
                }
                break;

            case 1:
                for (int i=0; i<num_elem; i++){
                    if(llista[i] instanceof Estudiant){
                        aux = aux + "\nIndex: "+i+" "+llista[i];
                    }     
                }
                break;

            case 2:
                for (int i=0; i<num_elem; i++){
                    if(llista[i] instanceof Investigador){
                        aux = aux + "\nIndex: "+i+" "+llista[i];
                    }     
                }
                break;

            case 3:
                for (int i=0; i<num_elem; i++){
                    if(llista[i] instanceof Empresa){
                        aux = aux + "\nIndex: "+i+" "+llista[i];
                    }     
                }
                break;
        }
        
        return aux;
    }

    /**
     * Mostra les dades d'un usuari segons el seu alias
     * @param alias de l'usuari que es vol tractar
     * @return dades de l'usuari
     */
    public String mostrarUsuari(String alias) throws UsuariNoValidException{
        String aux = null;

        int index = trobarUsuari(alias);
        if(index != -1){                     
            aux = "USUARI: " +llista[index]+"\n";
        }else{
            throw new UsuariNoValidException("No s'ha pogut trobar l'usuari.\n");
        }
              
        return aux;
    }

    /**
     * Mostra el usuari d'un tipus que més hores ha fet servir les màquines
     * @param alias de l'usuari que es vol tractar
     * @return dades de l'usuari
     */
    /*public Usuari mesHores(int tipus){
        Usuari max = null;
        
        FORMA PART DEL MAIN??
              
        return max;
    }*/


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

        while(i<num_elem && !trobat){
            if(llista[i].getAlias().equalsIgnoreCase(alias)){
                trobat = true;
            }
            else{i++;}
        }
        if (!trobat){i=-1;}                 
        
        return i;
    }

    /**
     * Amplia la capacitat d'una llista d'usuaris al doble
     */
    private void ampliarCapacitat(){
        Usuari[] nova = new Usuari[llista.length * 2];      //augmenta la capacitat al doble
        
        for (int i = 0; i < num_elem; i++) {
            nova[i] = llista[i];
        }
        
        llista = nova;
    }

}