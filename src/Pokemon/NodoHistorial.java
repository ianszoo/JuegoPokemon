/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pokemon;

/**
 *
 * @author Ian Suazo Palao
 */
public class NodoHistorial {
    private String registro;
    private NodoHistorial siguiente;

    public NodoHistorial(String registro){
        this.registro=registro;
        this.siguiente=null;
    }

    public String getRegistro(){
        return registro;
    }

    public NodoHistorial getSiguiente(){
        return siguiente;
    }

    public void setSiguiente(NodoHistorial siguiente){
        this.siguiente=siguiente;
    }
}
