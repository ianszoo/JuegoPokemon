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
public class ListaHistorial {
    private NodoHistorial head;

    public ListaHistorial(){
        this.head=null;
    }

    public void agregar(String txt){
        NodoHistorial nuevo=new NodoHistorial(txt);
        if (head==null) {
            head=nuevo;
            return;
        }
        NodoHistorial actual=head;
        while (actual.getSiguiente()!=null){
            actual=actual.getSiguiente();
        }
        actual.setSiguiente(nuevo);
    }

    public String obtenerTextoCompleto(){
        if (head==null){
            return "No hay eventos registrados en este combate.";
        }

        StringBuilder sb=new StringBuilder();
        NodoHistorial actual=head;
        while (actual!=null){
            sb.append(actual.getRegistro()).append("\n--------------------------------\n");
            actual=actual.getSiguiente();
        }
        return sb.toString();
    }

    public void limpiar(){
        this.head=null;
    }
}
