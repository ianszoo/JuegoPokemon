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
public class NodoObjeto {
    private Objeto objeto;
    private NodoObjeto siguiente;

    public NodoObjeto(Objeto objeto){
        this.objeto = objeto;
        this.siguiente = null;
    }

    public Objeto getObjeto(){
        return objeto;
    }

    public void setObjeto(Objeto objeto){
        this.objeto = objeto;
    }

    public NodoObjeto getSiguiente(){
        return siguiente;
    }

    public void setSiguiente(NodoObjeto siguiente){
        this.siguiente = siguiente;
    }
}
