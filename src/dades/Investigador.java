package dades;
/**
 * Classe que representa un usuari investigador.
 */
public class Investigador extends Usuari {

    private String departament;
    private String codiProjecte;


    public Investigador(String alias, String nom, String correu,
                        String departament, String codiProjecte) {

        super(alias, nom, correu);

        this.departament = departament;
        this.codiProjecte = codiProjecte;
    }


    public String getDepartament() {
        return departament;
    }


    public String getCodiProjecte() {
        return codiProjecte;
    }


    /**
     * L'investigador paga el cost d'ús sense descompte.
     */
    @Override
    public double calcularImport(double costUs) {

        double importFinal;
        importFinal = costUs;

        return importFinal;
    }


    /**
     * Un investigador pot tenir com a màxim 5 treballs pendents.
     */
    @Override
    public int getMaxTreballsPendents() {
        return 5;
    }


    @Override
    public String toString() {
        return super.toString()
                + ", departament: " + departament
                + ", projecte: " + codiProjecte;
    }
}