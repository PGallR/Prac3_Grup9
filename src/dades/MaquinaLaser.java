package dades;




public class MaquinaLaser extends Maquina{
   private double potencia;
   private double amplada;
   private double profunditat;
   private static final double PREU_HORA = 15;
   private static final double MAX_HORES = 2;
   
   //area de treball?


   //CONSTRUCTOR
   public MaquinaLaser(int codi, String model, int laboratori, double pot, double amp, double prof){
       super(codi, model, laboratori);
      
       if(pot<=0||amp<=0||prof<=0){
           throw new IllegalArgumentException("Tots els paràmetres han de ser positius\n");
       }
      
       amplada=amp;
       potencia=pot;
       profunditat=prof;
   }


   public MaquinaLaser(){
       super();
       potencia=0;
       amplada=0;


   }


   //GETTERS
   public double getAmplada(){
       return this.amplada;
   }
   public double getProfunditat(){
       return this.profunditat;
   }
   public double getPotenciat(){
       return this.potencia;
   }


   //SETTERS
   public void setAmplada(double amp){
       this.amplada=amp;
   }
   public void setPotencia(double pot){
       this.potencia=pot;
   }
   public void setProfunditat(double prof){
       this.profunditat=prof;
   }


   //CREA COPIA
   public MaquinaLaser creaCopiaMaquinaLaser(){
       return new MaquinaLaser(this.getCodi(), this.getModel(), this.getLaboratori(), this.potencia, this.amplada, this.profunditat);
   }


   //TO STRING
   public String toString(){
       return (super.toString()+"\n\tPotencia: "+potencia+"n\tAmplada: "+amplada+"\n\tProfunditat: "+profunditat);
   }


   //SON IGUALS
   //comparem codi? Atributs??


   //COST
   @Override
   public double calcularCostMaquina(double hores, double material){
       if(hores>MAX_HORES){
           throw new IllegalArgumentException("màxim d'hores = 2\n");
       }
       return hores*PREU_HORA;
   }
}



