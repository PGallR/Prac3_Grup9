package excepcions;

public class MaquinaNoValidException extends Exception {
    
    public MaquinaNoValidException(String missatge) {
        super(missatge);
    }
}
