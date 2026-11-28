package dades;


public abstract class Maquina {
  
   // les característiques pròpies de la màquina.
   // Cada tipus de màquina ha de saber calcular el cost d’ús d’un treball a partir de
   // - les hores
   // - la quantitat de material.


   //EXCEPCIONS


   //1. TRY/CATCH DE ILLEGAL ARGUMENT EXCEPTION
   //2. EXCEPCIO DE LIMIT DE VOLUM PER MAQUINES DE FILAMENT
   //3. I D'AREA PER MAQUINES DE LASER


   private int codi;   //codi id
   private String model;   //model de la maquina
   private int laboratori; //laboratori


   public Maquina(int codi, String model, int laboratori){
       //preguntar quins paràmetres controlar
       //nombres negatius?
       //s'ha de comprovar en una enum que siguin vàlids el model i el laboratori?
       //quins models i laboratoris s'inclouen?
       this.codi = codi;
       this.model = model;
       this.laboratori = laboratori;
   }


   public Maquina(){
       codi = -1;
       model = "no especificat";
       laboratori = -1;
   }


   /*GETTERS */
   public int getCodi(){
       return codi;
   }


   public String getModel(){
       return model;
   }


   public int getLaboratori(){
       return laboratori;
   }
  
   /*SETTERS */
   public void setCodi(int codi){
       this.codi=codi;
   }


   public void setModel(String model){
       this.model = model;
   }


   public void setLaboratori(int laboratori){
       this.laboratori=laboratori;
   }


   //TO STRING
   @Override
   public String toString() {
       return "Màquina:\n\tCodi=>"+this.codi+"\n\tModel=>"+this.model+"\n\tLaboratori=>"+laboratori;
   }


   //COPIA
   //public Maquina creaCopia(){
     //  return new Maquina (this.codi, this.model, this.laboratori);
   //}

   //CALCUL COST
   public abstract double calcularCostMaquina(double temps, double material);




}

