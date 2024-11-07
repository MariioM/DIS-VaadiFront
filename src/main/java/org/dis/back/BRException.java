package org.dis.back;
//Excepción genérica para el ejercicio.
//Como extiende a Exception, hereda todos los métodos necesarios
//para funcionar correctamente en try-catch o throw

public class BRException extends Exception{
    public BRException(String causa) {
        super (causa);
    }
}
