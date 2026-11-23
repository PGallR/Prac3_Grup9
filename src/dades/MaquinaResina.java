package dades;




public class MaquinaResina extends Maquina{
   private int capacitat;
   private int resolucio;
   private static final double PREU_HORA = 2.5;
   private static final double PREU_GRAM = 0.15;
   private static final double MAX_HORES = 12;


   //quantitat de resina no pot superar la capacitat del tanc


   //CONSTRUCTOR
   public MaquinaResina(int codi, String model, int laboratori, int cap, int res){
       super(codi, model, laboratori);
      
       if(cap<=0||res<=0){
           throw new IllegalArgumentException("Tots els paràmetres han de ser positius\n");
       }
      
       capacitat=cap;
       resolucio=res;
   }


   public MaquinaResina(){
       super();
       capacitat=0;
       resolucio=0;
   }


   //GETTERS
   public double getCapacitat(){
       return this.capacitat;
   }
   public double getResolucio(){
       return this.resolucio;
   }


   //SETTERS => falta control d'errors
   public void setCapacitat(int cap){
       capacitat=cap;
   }
   public void setResolucio(int res){
       resolucio=res;
   }




   //CREA COPIA
   public MaquinaResina creaCopiaMaquinaResina(){
       return new MaquinaResina(this.getCodi(), this.getModel(), this.getLaboratori(), this.capacitat, this.resolucio);
   }


   //TO STRING
   public String toString(){
       return (super.toString()+"\n\tCapacitat: "+capacitat+"\n\tResolucio: "+resolucio);
   }


   //SON IGUALS
   //comparem codi? Atributs??


   //COST
   @Override
   public double calcularCostMaquina(double hores, double material){
       if(hores>MAX_HORES){
           throw new IllegalArgumentException("màxim d'hores = 12\n");
       }
       if(material>capacitat){
           throw new IllegalArgumentException("capacitat màxima:"+capacitat+"\n");
       }
       return hores*PREU_HORA+material*PREU_GRAM;
   }


}

