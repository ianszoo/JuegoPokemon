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
import java.util.Random;

public class GestorUsuarios {
    private NodoUsuario cabeza;           
    private Usuario[] usuariosRivales;    
    private Random random;

    public GestorUsuarios() {
        this.cabeza = null;
        this.random = new Random();
        inicializar10Rivales();
    }

   public boolean crearUsuario(String username, String password) {
    if (username == null || username.trim().isEmpty() || password == null || password.trim().isEmpty()) {
        return false;
    }
    
    if (buscarUsuario(username) != null) {
        return false; 
    }

    Usuario nuevoUsuario = new Usuario(username.trim(), password);
  

    NodoUsuario nuevoNodo = new NodoUsuario(nuevoUsuario);
    if (cabeza == null) {
        cabeza = nuevoNodo;
    } else {
        NodoUsuario actual = cabeza;
        while (actual.getSiguiente() != null) {
            actual = actual.getSiguiente();
        }
        actual.setSiguiente(nuevoNodo);
    }
    return true;
}

    public Usuario login(String username, String password) {
        NodoUsuario actual = cabeza;
        while (actual != null) {
            Usuario u = actual.getUsuario();
            if (u.getUsername().equalsIgnoreCase(username.trim()) && u.getPassword().equals(password)) {
                return u;
            }
            actual = actual.getSiguiente();
        }
        return null;
    }

    public Usuario buscarUsuario(String username) {
        NodoUsuario actual = cabeza;
        while (actual != null) {
            if (actual.getUsuario().getUsername().equalsIgnoreCase(username.trim())) {
                return actual.getUsuario();
            }
            actual = actual.getSiguiente();
        }
        return null;
    }

 
    private void inicializar10Rivales() {
        usuariosRivales = new Usuario[10];

        String[] nombres = {
            "Red", "Blue", "Cynthia", "Steven", "Lance", 
            "Leon", "Ash", "Iris", "Alder", "Diantha"
        };

        
        String[][] equipos = {
            {"Charizard", "Snorlax", "Dragonite", "Blaziken"},      // Red
            {"Blaziken", "Tyranitar", "Excadrill", "Gengar"},        // Blue
            {"Garchomp", "Milotic", "Lucario", "Gardevoir"},         // Cynthia
            {"Metagross", "Corviknight", "Excadrill", "Tyranitar"},  // Steven
            {"Dragonite", "Hydreigon", "Charizard", "Garchomp"},     // Lance
            {"Charizard", "Corviknight", "Hydreigon", "Chandelure"}, // Leon
            {"Lucario", "Greninja", "Gengar", "Dragonite"},          // Ash
            {"Dragonite", "Garchomp", "Hydreigon", "Excadrill"},     // Iris
            {"Chandelure", "Excadrill", "Torterra", "Florges"},      // Alder
            {"Gardevoir", "Florges", "Milotic", "Weavile"}           // Diantha
        };

        for (int i = 0; i < 10; i++) {
            Usuario rival = new Usuario(nombres[i], "1234");
            for (String pNom : equipos[i]) {
                rival.getEquipo().insertar(PokemonFactory.crearPokemon(pNom, 20));
            }
            usuariosRivales[i] = rival;
        }
    }

 
    public Usuario obtenerRivalAleatorio() {
        int indice = random.nextInt(usuariosRivales.length);
        Usuario rivalOriginal = usuariosRivales[indice];

        Usuario rivalClon = new Usuario(rivalOriginal.getUsername(), "1234");
        Pokemon[] pokemons = rivalOriginal.getEquipo().toArray();
        for (int i = 0; i < pokemons.length; i++) {
            rivalClon.getEquipo().insertar(PokemonFactory.crearPokemon(pokemons[i].getNombre(), pokemons[i].getNivel()));
        }
        return rivalClon;
    }
}
