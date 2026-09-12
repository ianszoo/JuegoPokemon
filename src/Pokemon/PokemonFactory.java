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
public class PokemonFactory {

    public static Pokemon crearPokemon(String nombre, int nivel) {
        switch (nombre.toLowerCase().trim()) {
            case "charizard":
                return new Pokemon("Charizard", nivel, Tipo.FUEGO, Tipo.VOLADOR, 78, 84, 78, "Llamarada", Tipo.FUEGO, 110, "charizard.png");
            case "garchomp":
                return new Pokemon("Garchomp", nivel, Tipo.DRAGON, Tipo.TIERRA, 108, 130, 95, "Garra Dragón", Tipo.DRAGON, 80, "garchomp.png");
            case "lucario":
                return new Pokemon("Lucario", nivel, Tipo.LUCHA, Tipo.ACERO, 70, 110, 70, "Aura Esférica", Tipo.LUCHA, 80, "lucario.png");
            case "greninja":
                return new Pokemon("Greninja", nivel, Tipo.AGUA, Tipo.SINIESTRO, 72, 95, 67, "Hidrobomba", Tipo.AGUA, 110, "greninja.png");
            case "gardevoir":
                return new Pokemon("Gardevoir", nivel, Tipo.PSIQUICO, Tipo.HADA, 68, 65, 65, "Fuerza Lunar", Tipo.HADA, 95, "gardevoir.png");
            case "tyranitar":
                return new Pokemon("Tyranitar", nivel, Tipo.ROCA, Tipo.SINIESTRO, 100, 134, 110, "Triturar", Tipo.SINIESTRO, 80, "tyranitar.png");
            case "snorlax":
                return new Pokemon("Snorlax", nivel, Tipo.NORMAL, Tipo.NINGUNO, 160, 110, 65, "Golpe Cuerpo", Tipo.NORMAL, 85, "snorlax.png");
            case "gengar":
                return new Pokemon("Gengar", nivel, Tipo.FANTASMA, Tipo.VENENO, 60, 65, 60, "Bola Sombra", Tipo.FANTASMA, 80, "gengar.png");
            case "dragonite":
                return new Pokemon("Dragonite", nivel, Tipo.DRAGON, Tipo.VOLADOR, 91, 134, 95, "Enfado", Tipo.DRAGON, 120, "dragonite.png");
            case "metagross":
                return new Pokemon("Metagross", nivel, Tipo.ACERO, Tipo.PSIQUICO, 80, 135, 130, "Puño Meteoro", Tipo.ACERO, 90, "metagross.png");
            case "blaziken":
                return new Pokemon("Blaziken", nivel, Tipo.FUEGO, Tipo.LUCHA, 80, 120, 70, "Envite Ígneo", Tipo.FUEGO, 120, "blaziken.png");
            case "milotic":
                return new Pokemon("Milotic", nivel, Tipo.AGUA, Tipo.NINGUNO, 95, 60, 79, "Surf", Tipo.AGUA, 90, "milotic.png");
            case "raichu de alola":
                return new Pokemon("Raichu de Alola", nivel, Tipo.ELECTRICO, Tipo.PSIQUICO, 60, 85, 50, "Rayo", Tipo.ELECTRICO, 90, "raichuAlola.png");
            case "torterra":
                return new Pokemon("Torterra", nivel, Tipo.PLANTA, Tipo.TIERRA, 95, 109, 105, "Terremoto", Tipo.TIERRA, 100, "torterra.png");
            case "weavile":
                return new Pokemon("Weavile", nivel, Tipo.SINIESTRO, Tipo.HIELO, 70, 120, 65, "Chuzos", Tipo.HIELO, 85, "weavile.png");
            case "corviknight":
                return new Pokemon("Corviknight", nivel, Tipo.ACERO, Tipo.VOLADOR, 98, 87, 105, "Pájaro Osado", Tipo.VOLADOR, 120, "corviKnight.png");
            case "chandelure":
                return new Pokemon("Chandelure", nivel, Tipo.FANTASMA, Tipo.FUEGO, 60, 55, 90, "Lanzallamas", Tipo.FUEGO, 90, "chandelure.png");
            case "excadrill":
                return new Pokemon("Excadrill", nivel, Tipo.TIERRA, Tipo.ACERO, 110, 135, 60, "Terremoto", Tipo.TIERRA, 100, "excadrill.png");
            case "florges":
                return new Pokemon("Florges", nivel, Tipo.HADA, Tipo.NINGUNO, 78, 65, 68, "Fuerza Lunar", Tipo.HADA, 95, "florges.png");
            case "hydreigon":
                return new Pokemon("Hydreigon", nivel, Tipo.SINIESTRO, Tipo.DRAGON, 92, 105, 90, "Pulso Dragón", Tipo.DRAGON, 85, "hydreigon.png");
            default:
                return new Pokemon("Pikachu", nivel, Tipo.ELECTRICO, Tipo.NINGUNO, 35, 55, 40, "Impactrueno", Tipo.ELECTRICO, 40, "pikachu.png");
        }
    }

    public static final String[] NOMBRES_DISPONIBLES = {
        "Charizard", "Garchomp", "Lucario", "Greninja", "Gardevoir",
        "Tyranitar", "Snorlax", "Gengar", "Dragonite", "Metagross",
        "Blaziken", "Milotic", "Raichu de Alola", "Torterra", "Weavile",
        "Corviknight", "Chandelure", "Excadrill", "Florges", "Hydreigon"
    };
}
