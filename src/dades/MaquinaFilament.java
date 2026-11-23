package dades;




public class MaquinaFilament extends Maquina{
   private double amplada;
   private double alcada;
   private double profunditat;
   private double diametre;
   private static final double PREU_HORA = 1.5;
   private static final double PREU_GRAM = 0.05;
   private static final double MAX_HORES = 24;
  
   //volum màxim d'impressió


   //CONSTRUCTOR
   public MaquinaFilament(int codi, String model, int laboratori, double amp, double alc, double prof, double diam){
       super(codi, model, laboratori);


       if(amp<=0||alc<=0||prof<=0||diam<=0){
           throw new IllegalArgumentException("Tots els paràmetres han de ser positius\n");//POSAR EL TRY/CATCH
       }


       amplada=amp;
       alcada=alc;
       profunditat=prof;
       diametre=diam;
   }


   public MaquinaFilament(){
       super();
       amplada=0;
       alcada=0;
       profunditat=0;
       diametre=0;
   }


   //GETTERS
   public double getAmplada(){
       return this.amplada;
   }
   public double getAlcada(){
       return this.alcada;
   }
   public double getProfunditat(){
       return this.profunditat;
   }
   public double getDiametre(){
       return this.diametre;
   }


   //SETTERS
   public void setAmplada(double a){
       this.amplada=a;
   }
   public void setAlcada(double alc){
       this.alcada=alc;
   }
   public void setProfunditat(double prof){
       this.profunditat=prof;
   }
   public void setDiametre(double diam){
       this.diametre=diam;
   }


   //CREA COPIA
   public MaquinaFilament creaCopiaMaquinaFilament(){
       return new MaquinaFilament(this.getCodi(), this.getModel(), this.getLaboratori(), this.amplada, this.alcada, this.profunditat, this.diametre);
   }


   //TO STRING
   public String toString(){
       return (super.toString()+"\n\tAmplada: "+amplada+"\n\tAlçada: "+alcada+"\n\tProfunditat: "+profunditat+"\n\tDiametre: "+diametre);
   }


   //SON IGUALS
   //comparem codi? Atributs??


   //CALCUL COST
   @Override
   public double calcularCostMaquina(double hores, double material){
       if(hores>MAX_HORES){
           throw new IllegalArgumentException("màxim d'hores = 24\n");
       }
       return hores*PREU_HORA+material*PREU_GRAM;
   }
}


