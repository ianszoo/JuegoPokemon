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
    private NodoUsuario cabeza;           // Cabeza de la lista enlazada de usuarios
    private Usuario[] usuariosRivales;    // Catálogo de los 10 rivales predefinidos
    private Random random;

    public GestorUsuarios() {
        this.cabeza = null;
        this.random = new Random();
        inicializar10Rivales();
    }

    // 1. CREAR UN USUARIO (Registro con 4 Pokémon)
    public boolean crearUsuario(String username, String password) {
        if (username == null || username.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            return false;
        }
        
        if (buscarUsuario(username) != null) {
            return false; 
        }

        Usuario nuevoUsuario = new Usuario(username.trim(), password);
        // Equipo inicial de 4 Pokémon
        nuevoUsuario.getEquipo().insertar(PokemonFactory.crearPokemon("Lucario", 20));
        nuevoUsuario.getEquipo().insertar(PokemonFactory.crearPokemon("Greninja", 20));
        nuevoUsuario.getEquipo().insertar(PokemonFactory.crearPokemon("Charizard", 20));
        nuevoUsuario.getEquipo().insertar(PokemonFactory.crearPokemon("Garchomp", 20));

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

    // 2. LOG IN (Autenticación)
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

    // 3. LOS 10 RIVALES CON 4 POKÉMON CADA UNO
    private void inicializar10Rivales() {
        usuariosRivales = new Usuario[10];

        String[] nombres = {
            "Red", "Blue", "Cynthia", "Steven", "Lance", 
            "Leon", "Ash", "Iris", "Alder", "Diantha"
        };

        // Cada rival con exactamente 4 Pokémon de los 20 permitidos
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

    // OBTENER UN RIVAL ALEATORIO CON 4 POKÉMON
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
