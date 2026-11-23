package dades;

/**
 * Classe que representa un usuari de tipus Estudiant.
 */
public class Estudiant extends Usuari {

    private String ensenyament;
    private int anyInici;


    public Estudiant(String alias, String nom, String correu,
                     String ensenyament, int anyInici) {

        super(alias, nom, correu);

        this.ensenyament = ensenyament;
        this.anyInici = anyInici;
    }


    public String getEnsenyament() {
        return ensenyament;
    }


    public int getAnyInici() {
        return anyInici;
    }


    /**
     * Aplica un descompte del 50% sobre el cost d'ús.
     */
    @Override
    public double calcularImport(double costUs) {

        double importFinal;

        importFinal = costUs * 0.50;

        return importFinal;
    }


    /**
     * Un estudiant pot tenir com a màxim 2 treballs pendents.
     */
    @Override
    public int getMaxTreballsPendents() {
        return 2;
    }


    @Override
    public String toString() {
        return super.toString() + ", ensenyament: " + ensenyament + ", any d'inici: " + anyInici;
    }
}
