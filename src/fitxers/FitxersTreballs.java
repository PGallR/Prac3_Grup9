package fitxers;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import dades.Treball;

public class FitxersTreballs {
    
    private static final String nom_fitxer = "treballs.ser";        // Nom del fitxer serialitzat de informació de treballs
    
    /**
     * Mostra les dades de una maquina segons el seu codi
     * @param llista de treballs que es vol guardar al fitxer (treballs.ser)
     */
    public static void guardarTreballs (Treball[] llista) throws IOException{
        ObjectOutputStream outputFile;
        
        if(llista == null){
            /*GESTIO D'ERRORS */
        }
        else{ 
            try {
                outputFile = new ObjectOutputStream(new FileOutputStream(nom_fitxer));
                
                for (int i=0; i<llista.length; i++) {
                    outputFile.writeObject(llista[i]);
                }
                outputFile.close();
            }
            catch (IOException e) {
                System.out.println("Error en l'arxiu de sortida.");
            }
        }
    }

    /**
     * Mostra les dades de una maquina segons el seu codi
     * @param llista de treballs que es vol guardar al fitxer (treballs.ser)
     */
    public static void llegirTreballs (Treball[] llista) throws IOException{
        ObjectInputStream inputFile;
        
        if(llista == null){
            /*GESTIO D'ERRORS */
        }
        else{
            try {
                inputFile = new ObjectInputStream(new FileInputStream(nom_fitxer));
                
                for (int i=0; i<llista.length; i++) {
                    llista[i]=(Treball)inputFile.readObject();
                }
                inputFile.close();
            }
            catch (IOException e) {
                System.out.println("Error en l'arxiu d'entrada.");
            }
            catch (ClassNotFoundException e) {
                System.out.println("Error: no es troba la classe Treball."+e);
            }
            catch (ClassCastException e){
                System.out.println("Error: el format de l'arxiu no és correcte."+e);
            }
            /*GESTIÓ DE FINAL DE FITXER PENDENT */
        }
    }

}
