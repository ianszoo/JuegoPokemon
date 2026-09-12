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
public class ListaEnlazadaObjetos {
    private NodoObjeto head;

    public ListaEnlazadaObjetos() {
        this.head=null;
    }

    public void insertar(Objeto obj) {
        NodoObjeto nuevo = new NodoObjeto(obj);
        if (head==null) {
            head=nuevo;
            return;
        }
        NodoObjeto actual=head;
        while (actual.getSiguiente()!=null) {
            actual=actual.getSiguiente();
        }
        actual.setSiguiente(nuevo);
    }

    public int contar() {
        int contador=0;
        NodoObjeto actual=head;
        while (actual != null) {
            contador++;
            actual=actual.getSiguiente();
        }
        return contador;
    }

    public Objeto obtenerPorIndice(int indice) {
        if (indice<0){
            return null;
        }
        int contador=0;
        NodoObjeto actual=head;
        while (actual != null){
            if (contador == indice){
                return actual.getObjeto();
            }
            contador++;
            actual=actual.getSiguiente();
        }
        return null;
    }

    public Objeto buscarPorNombre(String nombre){
        if (nombre==null){
            return null;
        }
        NodoObjeto actual=head;
        while (actual != null){
            if (actual.getObjeto().getNombre().equalsIgnoreCase(nombre)) {
                return actual.getObjeto();
            }
            actual=actual.getSiguiente();
        }
        return null;
    }
    public void agregar(Objeto obj) {
        insertar(obj);
    }

    public Objeto[] toArray() {
        int total = contar();
        Objeto[] arr = new Objeto[total];
        for (int i = 0; i < total; i++) {
            arr[i] = obtenerPorIndice(i);
        }
        return arr;
    }
}
