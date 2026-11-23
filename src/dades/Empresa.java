package dades;
/**
 * Classe que representa una empresa externa.
 */
public class Empresa extends Usuari {

    private String cif;
    private String personaContacte;


    public Empresa(String alias, String nom, String correu,
                   String cif, String personaContacte) {

        super(alias, nom, correu);

        this.cif = cif;
        this.personaContacte = personaContacte;
    }


    public String getCif() {
        return cif;
    }


    public String getPersonaContacte() {
        return personaContacte;
    }


    /**
     * Aplica un recàrrec del 30% sobre el cost d'ús.
     */
    @Override
    public double calcularImport(double costUs) {

        double importFinal;

        importFinal = costUs * 1.30;

        return importFinal;
    }


    /**
     * Una empresa pot tenir com a màxim 3 treballs pendents.
     */
    @Override
    public int getMaxTreballsPendents() {
        return 3;
    }


    @Override
    public String toString() {
        return super.toString()
                + ", CIF: " + cif
                + ", persona de contacte: " + personaContacte;
    }
}