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

public class ListaEnlazadaUsuarios {
    private NodoUsuario cabeza;
    private Usuario[] usuariosRivales;
    private Random random;

    public ListaEnlazadaUsuarios() {
        this.cabeza = null;
        this.random = new Random();
        inicializar10Rivales();
    }

    public void registrarUsuario(Usuario nuevoUsuario) throws UsuariaExistenteException {
        if (buscarUsuario(nuevoUsuario.getUsername()) != null) {
            throw new UsuariaExistenteException("Error: El nombre de usuario ya está registrado.");
        }

        // Si el usuario no tiene Pokémon, le asignamos su equipo inicial de 4
        if (nuevoUsuario.getEquipo().estaVacia()) {
            nuevoUsuario.getEquipo().add(PokemonFactory.crearPokemon("Lucario", 20));
            nuevoUsuario.getEquipo().add(PokemonFactory.crearPokemon("Greninja", 20));
            nuevoUsuario.getEquipo().add(PokemonFactory.crearPokemon("Charizard", 20));
            nuevoUsuario.getEquipo().add(PokemonFactory.crearPokemon("Garchomp", 20));
        }

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
    }

    public Usuario buscarUsuario(String username) {
        if (username == null) return null;
        NodoUsuario actual = cabeza;
        while (actual != null) {
            if (actual.getUsuario().getUsername().equalsIgnoreCase(username.trim())) {
                return actual.getUsuario();
            }
            actual = actual.getSiguiente();
        }
        return null;
    }

    // 10 Rivales Predefinidos con 4 Pokémon cada uno
    private void inicializar10Rivales() {
        usuariosRivales = new Usuario[10];
        String[] nombres = {
            "Red", "Blue", "Cynthia", "Steven", "Lance", 
            "Leon", "Ash", "Iris", "Alder", "Diantha"
        };

        String[][] equipos = {
            {"Charizard", "Snorlax", "Dragonite", "Blaziken"},
            {"Blaziken", "Tyranitar", "Excadrill", "Gengar"},
            {"Garchomp", "Milotic", "Lucario", "Gardevoir"},
            {"Metagross", "Corviknight", "Excadrill", "Tyranitar"},
            {"Dragonite", "Hydreigon", "Charizard", "Garchomp"},
            {"Charizard", "Corviknight", "Hydreigon", "Chandelure"},
            {"Lucario", "Greninja", "Gengar", "Dragonite"},
            {"Dragonite", "Garchomp", "Hydreigon", "Excadrill"},
            {"Chandelure", "Excadrill", "Torterra", "Florges"},
            {"Gardevoir", "Florges", "Milotic", "Weavile"}
        };

        for (int i = 0; i < 10; i++) {
            Usuario rival = new Usuario(nombres[i], "1234");
            for (String pNom : equipos[i]) {
                rival.getEquipo().add(PokemonFactory.crearPokemon(pNom, 20));
            }
            usuariosRivales[i] = rival;
        }
    }

    public Usuario obtenerRivalAleatorio() {
        int indice = random.nextInt(usuariosRivales.length);
        Usuario base = usuariosRivales[indice];

        Usuario clon = new Usuario(base.getUsername(), "1234");
        Pokemon[] pokemons = base.getEquipo().toArray();
        for (int i = 0; i < pokemons.length; i++) {
            clon.getEquipo().add(PokemonFactory.crearPokemon(pokemons[i].getNombre(), pokemons[i].getNivel()));
        }
        return clon;
    }
}
