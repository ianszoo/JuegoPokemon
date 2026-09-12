/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pokemon;

/**
 *
 * @author David Suazo Palao
 */
public class NodoUsuario {
    private Usuario usuario;
    private NodoUsuario siguiente;

    public NodoUsuario(Usuario usuario) {
        this.usuario = usuario;
        this.siguiente = null;
    }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public NodoUsuario getSiguiente() { return siguiente; }
    public void setSiguiente(NodoUsuario siguiente) { this.siguiente = siguiente; }
}
