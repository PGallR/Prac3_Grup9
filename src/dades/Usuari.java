package dades;
public abstract class Usuari {

 /**
    Classe que representa un usuari del taller.
    Guarda la informació comuna de tots els tipus d'usuaris.
 */

    // Atributs comuns
    private String alias; 
    private String nom;
    private String correu;

    //ideas excepciones: comprobar que usuario no haya sup max trabajos pendientes
    //alias duplicado?
    //?????
    //FALTA mirar la lista para ver qué
    //copia?
    //iguales?
    //?????s

    /**
     * Constructor de la classe Usuari.
     *
     * @param alias àlies identificador de l'usuari
     * @param nom nom de l'usuari
     * @param correu correu electrònic de l'usuari
     */
    public Usuari(String alias, String nom, String correu) {
        this.alias = alias;
        this.nom = nom;
        this.correu = correu;
    }

    //GETTERS
    public String getAlias() {
        return alias;
    }


    public String getNom() {
        return nom;
    }


    public String getCorreu() {
        return correu;
    }


    /**
     * Calcula l'import final que ha de pagar l'usuari.
     *
     * @param costUs cost d'ús de la màquina
     * @return import final
     */
    public abstract double calcularImport(double costUs);


    /**
     * Retorna el nombre màxim de treballs pendents
     * que pot tenir l'usuari.
     *
     * @return nombre màxim de treballs pendents
     */
    public abstract int getMaxTreballsPendents();


    //TO STRING
    @Override
    public String toString() {
        return "Àlies: " + alias + ", nom: " + nom + ", correu: " + correu;
    }
}
