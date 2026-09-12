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
public class NodoPokemon {
    private Pokemon pokemon;
    private NodoPokemon siguiente;

    public NodoPokemon(Pokemon pokemon) {
        this.pokemon = pokemon;
        this.siguiente=null;
        
    }
    
    public Pokemon getPokemon(){
        return pokemon;
    }
    
    public void setPokemon(Pokemon pokemon){
        this.pokemon = pokemon;
    }

    public NodoPokemon getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(NodoPokemon siguiente) {
        this.siguiente = siguiente;
    }
    
    
}
