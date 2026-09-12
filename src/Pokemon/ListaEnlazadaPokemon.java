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
public class ListaEnlazadaPokemon {
    private NodoPokemon head;
    private int indice;

    public ListaEnlazadaPokemon() {
        this.head = null;
        this.indice = 0;
    }
    
    public void add(Pokemon pokemon) {
        NodoPokemon nuevo=new NodoPokemon(pokemon);
        if (head == null) {
            head=nuevo;
            return;
        }
        NodoPokemon actual=head;
        while (actual.getSiguiente()!=null){
            actual=actual.getSiguiente();
        }
        actual.setSiguiente(nuevo);
    }
    
    public Pokemon buscarPorNombre(String nombre) {
        if (nombre==null){
            return null;
        }
        NodoPokemon actual=head;
        while (actual != null) {
            if (actual.getPokemon().getNombre().equalsIgnoreCase(nombre)) {
                return actual.getPokemon();
            }
            actual = actual.getSiguiente();
        }
        return null;
    }
    
    public boolean eliminarPorNombre(String nombre) {
        if (head==null || nombre==null){
            return false;
        }

        if (head.getPokemon().getNombre().equalsIgnoreCase(nombre)) {
            head=head.getSiguiente();
            ajustarIndiceTrasEliminacion();
            return true;
        }

        NodoPokemon actual=head;
        while (actual.getSiguiente()!=null) {
            if (actual.getSiguiente().getPokemon().getNombre().equalsIgnoreCase(nombre)) {
                actual.setSiguiente(actual.getSiguiente().getSiguiente());
                ajustarIndiceTrasEliminacion();
                return true;
            }
            actual=actual.getSiguiente();
        }
        return false;
    }
    
    public int contarTotal(){
        int contador = 0;
        NodoPokemon actual=head;
        while (actual != null){
            contador++;
            actual=actual.getSiguiente();
        }
        return contador;
    }
    
    public int contarDisponibles(){
        int contador=0;
        NodoPokemon actual=head;
        while (actual != null) {
            if (!actual.getPokemon().estaDerrotado()) {
                contador++;
            }
            actual=actual.getSiguiente();
        }
        return contador;
    }
    
    private void ajustarIndiceTrasEliminacion(){
        if (indice >= contarTotal() && contarTotal() > 0){
            indice= 0;
        }
    }
    
    public boolean modificarPokemon(String nombreOriginal, int nuevoNivel, int nuevoHpMax, int nuevoAtaque){
        Pokemon p=buscarPorNombre(nombreOriginal);
        if (p!=null){
            p.setNivel(nuevoNivel);
            p.setHpMax(nuevoHpMax);
            p.setAtaque(nuevoAtaque);
            return true;
        }
        return false;
    }
    
    public Pokemon obtenerConIndice(int indice){
        if (indice<0){
            return null;
        }
        int contador=0;
        NodoPokemon actual=head;
        while (actual != null){
            if (contador == indice){
                return actual.getPokemon();
            }
            contador++;
            actual=actual.getSiguiente();
        }
        return null;
    }
    
    public Pokemon getPokemonActivo(){
        return obtenerConIndice(indice);
    }
    
    public boolean setPokemonActivoIndice(int nuevoIndice){
        Pokemon candidato=obtenerConIndice(nuevoIndice);
        if (candidato!=null && !candidato.estaDerrotado()){
            this.indice=nuevoIndice;
            return true;
        }
        return false;
    }
    
    public Pokemon encontrarSiguienteDisponible(){
        int total=contarTotal();
        for (int i = 0; i < total; i++){
            Pokemon p = obtenerConIndice(i);
            if (p != null && !p.estaDerrotado()){
                this.indice = i;
                return p;
            }
        }
        return null;
    }
    
    public boolean moverAlPrimerLugar(String nombre){
        if (head==null || head.getPokemon().getNombre().equalsIgnoreCase(nombre)){
            return false;
        }

        NodoPokemon previo=null;
        NodoPokemon actual=head;

        while (actual!=null && !actual.getPokemon().getNombre().equalsIgnoreCase(nombre)){
            previo=actual;
            actual=actual.getSiguiente();
        }

        if (actual == null){
            return false;
        }

        previo.setSiguiente(actual.getSiguiente());
        actual.setSiguiente(head);
        head=actual;
        this.indice=0;
        return true;
    }
    
    public void reiniciarVidaEquipo(){
        NodoPokemon actual=head;
        while (actual!=null){
            actual.getPokemon().setHpActual(actual.getPokemon().getHpMax());
            actual=actual.getSiguiente();
        }
        this.indice=0;
    }
    
    public boolean estaVacia(){
        return head==null;
    }
    public void insertar(Pokemon pokemon) {
        add(pokemon);
    }

    // Convierte la lista enlazada a arreglo para la GUI (sin exponer nodos)
    public Pokemon[] toArray() {
        int total = contarTotal();
        Pokemon[] arr = new Pokemon[total];
        for (int i = 0; i < total; i++) {
            arr[i] = obtenerConIndice(i);
        }
        return arr;
    }

    // Cambia el Pokémon activo por nombre
    public boolean cambiarActivoPorNombre(String nombre) {
        int total = contarTotal();
        for (int i = 0; i < total; i++) {
            Pokemon p = obtenerConIndice(i);
            if (p != null && p.getNombre().equalsIgnoreCase(nombre) && !p.estaDerrotado()) {
                this.indice = i;
                return true;
            }
        }
        return false;
    }
    public void vaciar() {
    this.head = null;
    this.indice = 0;
}

    public boolean tieneVivos() {
        return contarDisponibles() > 0;
    }
}
