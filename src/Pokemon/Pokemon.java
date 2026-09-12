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
public class Pokemon {
    private String nombre;
    private int nivel;
    private Tipo tipo;
    private int hpMax;
    private int hpActual;
    private int ataque;
    private String nombre_atk;

    public Pokemon(String nombre, int nivel, Tipo tipo, int hpMax, int ataque, String nombre_atk) {
        this.nombre = nombre;
        this.nivel = nivel;
        this.tipo = tipo;
        this.hpMax = hpMax;
        this.hpActual=hpMax;
        this.ataque = ataque;
        this.nombre_atk = nombre_atk;
    }
    
    public Pokemon(String nombre, int nivel, Tipo tipo, int hpMax, int hpActual, int ataque, String nombreAtaque) {
        this.nombre = nombre;
        this.nivel = nivel;
        this.tipo = tipo;
        this.hpMax = hpMax;
        this.hpActual=Math.min(hpMax,Math.max(0,hpActual));
        this.ataque = ataque;
        this.nombre_atk = nombreAtaque;
    }
    
    public boolean estaDerrotado(){
        return this.hpActual<=0;
    }
    
    public void recibirDanio(int danio){
        if (danio <= 0){
            return;
        }
        this.hpActual=Math.max(0,this.hpActual-danio);
    }
    
    public void curar(int cantidad){
        if (estaDerrotado() || cantidad<=0){
            return;
        }
        this.hpActual=Math.min(this.hpMax,this.hpActual+cantidad);
    }
    
    public boolean revivir(int percentHp) {
        if (!estaDerrotado()){
            return false;
        }
        this.hpActual=(this.hpMax*Math.max(1, Math.min(100,percentHp))) / 100;
        return true;
    }
    
    public Pokemon clonar(){
        return new Pokemon(this.nombre,this.nivel,this.tipo,this.hpMax,this.hpActual,this.ataque,this.nombre_atk);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public int getHpMax() {
        return hpMax;
    }

    public void setHpMax(int hpMax) {
        this.hpMax = hpMax;
    }

    public int getHpActual() {
        return hpActual;
    }

    public void setHpActual(int hpActual) {
        this.hpActual = hpActual;
    }

    public int getAtaque() {
        return ataque;
    }

    public void setAtaque(int ataque) {
        this.ataque = ataque;
    }

    public String getNombre_atk() {
        return nombre_atk;
    }

    public void setNombre_atk(String nombre_atk) {
        this.nombre_atk = nombre_atk;
    }
    
    public String toString(){
        return nombre+" (Nv."+nivel+" | Tipo: "+tipo+" | HP: "+hpActual+"/"+hpMax+")";
    }
}
