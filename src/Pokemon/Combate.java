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
public class Combate {
    private Usuario jugador;
    private Usuario rival;
    private ListaHistorial historial;
    private int turno;
    private boolean finalizado;
    private Usuario ganador;

    public Combate(Usuario jugador, Usuario rival, ListaHistorial historial) {
        this.jugador = jugador;
        this.rival = rival;
        this.historial = historial;
        this.turno = 1;
        this.finalizado = false;
        this.ganador = null;
        this.historial.agregar("¡Inicia la batalla contra " + rival.getUsername() + "!");
    }

    public void atacar() {
        if (finalizado) return;

        Pokemon pJugador = jugador.getEquipo().getPokemonActivo();
        Pokemon pRival = rival.getEquipo().getPokemonActivo();

        if (pJugador == null || pJugador.estaDerrotado()) return;

        historial.agregar("--- Turno " + turno + " ---");

        int danioJugador = calcularDanio(pJugador, pRival);
        pRival.recibirDanio(danioJugador);

        historial.agregar(pJugador.getNombre() + " utilizó " + pJugador.getNombreAtaque() + ".");
        historial.agregar(pRival.getNombre() + " rival recibió " + danioJugador + " puntos de daño.");
        historial.agregar(pRival.getNombre() + " rival: " + pRival.getHpActual() + "/" + pRival.getHpMax() + " HP");

        if (pRival.estaDerrotado()) {
            historial.agregar("¡" + pRival.getNombre() + " rival fue derrotado!");
            if (!rival.getEquipo().tieneVivos()) {
                finalizado = true;
                ganador = jugador;
                historial.agregar("¡VICTORIA! Has vencido a " + rival.getUsername() + ".");
                return;
            } else {
                Pokemon sig = rival.getEquipo().encontrarSiguienteDisponible();
                historial.agregar(rival.getUsername() + " envió a " + sig.getNombre() + ".");
            }
        } else {
            contraataqueRival();
        }

        turno++;
    }

    public void contraataqueRival() {
        Pokemon pJugador = jugador.getEquipo().getPokemonActivo();
        Pokemon pRival = rival.getEquipo().getPokemonActivo();

        if (pRival == null || pRival.estaDerrotado()) return;

        int danioRival = calcularDanio(pRival, pJugador);
        pJugador.recibirDanio(danioRival);

        historial.agregar(pRival.getNombre() + " rival utilizó " + pRival.getNombreAtaque() + ".");
        historial.agregar(pJugador.getNombre() + " recibió " + danioRival + " puntos de daño.");
        historial.agregar(pJugador.getNombre() + ": " + pJugador.getHpActual() + "/" + pJugador.getHpMax() + " HP");

        if (pJugador.estaDerrotado()) {
            historial.agregar("¡Tu " + pJugador.getNombre() + " fue derrotado!");
            if (!jugador.getEquipo().tieneVivos()) {
                finalizado = true;
                ganador = rival;
                historial.agregar("¡DERROTA! Todos tus Pokémon han caído.");
            }
        }
    }

    public int calcularDanio(Pokemon atacante, Pokemon defensor) {
        double mult1 = Tipo.getMultiplicador(atacante.getTipoAtaque(), defensor.getTipoPrimario());
        double mult2 = Tipo.getMultiplicador(atacante.getTipoAtaque(), defensor.getTipoSecundario());
        double efectividad = mult1 * mult2;

        if (efectividad > 1.0) historial.agregar("¡Es súper efectivo!");
        else if (efectividad < 1.0 && efectividad > 0.0) historial.agregar("No es muy efectivo...");
        else if (efectividad == 0.0) historial.agregar("No tuvo ningún efecto.");

        double base = ((((2 * atacante.getNivel() / 5.0) + 2) 
                * atacante.getPotenciaAtaque() 
                * (atacante.getAtaqueBase() / (double) defensor.getDefensaBase())) / 50.0) + 2;

        return Math.max((int) Math.round(base * efectividad), 1);
    }

    public boolean cambiarPokemon(String nombre) {
        boolean cambio = jugador.getEquipo().cambiarActivoPorNombre(nombre);
        if (cambio) {
            historial.agregar(jugador.getUsername() + " envió a " + nombre + ".");
            if (!rival.getEquipo().getPokemonActivo().estaDerrotado()) {
                contraataqueRival();
                turno++;
            }
        }
        return cambio;
    }

    public boolean usarObjeto(String nombreObjeto) {
        Objeto obj = jugador.getInventario().buscarPorNombre(nombreObjeto);
        if (obj != null) {
            Pokemon activo = jugador.getEquipo().getPokemonActivo();
            boolean usado = obj.usar(activo);
            if (usado) {
                historial.agregar(jugador.getUsername() + " utilizó " + obj.getNombre() + ".");
                if (!rival.getEquipo().getPokemonActivo().estaDerrotado()) {
                    contraataqueRival();
                    turno++;
                }
                return true;
            }
        }
        return false;
    }

    public void reiniciar(Usuario nuevoRival) {
        this.rival = nuevoRival;
        this.turno = 1;
        this.finalizado = false;
        this.ganador = null;
        this.historial.limpiar();
        this.jugador.getEquipo().reiniciarVidaEquipo();
        this.historial.agregar("¡Nueva batalla iniciada contra " + rival.getUsername() + "!");
    }

    public Usuario getJugador() {
        return jugador; 
    }
    public Usuario getRival() {
        return rival; 
    }
    public ListaHistorial getHistorial() {
        return historial; 
    }
    public boolean isFinalizado() {
        return finalizado; 
    }
    public Usuario getGanador() {
        return ganador; 
    }
}
