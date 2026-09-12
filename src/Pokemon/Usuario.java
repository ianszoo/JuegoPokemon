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
public class Usuario {
    private String username;
    private String password;
    private ListaEnlazadaPokemon equipo;       // Lista enlazada de Pokémon
    private ListaEnlazadaObjetos inventario;   // Lista enlazada de Objetos

    public Usuario(String username, String password) {
        this.username = username;
        this.password = password;
        this.equipo = new ListaEnlazadaPokemon();
        this.inventario = new ListaEnlazadaObjetos();
        inicializarInventario();
    }

    private void inicializarInventario() {
        inventario.agregar(new Objeto("Poción", "Recupera 20 HP", 3, 20, false));
        inventario.agregar(new Objeto("Superpoción", "Recupera 50 HP", 2, 50, false));
        inventario.agregar(new Objeto("Revivir", "Revive con 50% HP", 1, 0, true));
    }

    // Getters y Setters
    public String getUsername() {
        return username; 
    }
    public void setUsername(String username) {
        this.username = username; 
    }

    public String getPassword() { 
        return password; 
    }
    public void setPassword(String password) { 
        this.password = password; 
    }

    public ListaEnlazadaPokemon getEquipo() { 
        return equipo; 
    }
    public ListaEnlazadaObjetos getInventario() { 
        return inventario; 
    }
}
