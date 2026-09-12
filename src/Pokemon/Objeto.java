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
public class Objeto {
    private String nombre;
    private String descripcion;
    private int cantidad;
    private int poderCuracion;
    private boolean esRevivir;

    public Objeto(String nombre, String descripcion, int cantidad, int poderCuracion, boolean esRevivir) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.cantidad = cantidad;
        this.poderCuracion = poderCuracion;
        this.esRevivir = esRevivir;
    }

    public String getNombre() { 
        return nombre; 
    }
    public String getDescripcion() {
        return descripcion; 
    }
    public int getCantidad() {
        return cantidad; 
    }
    public int getPoderCuracion() {
        return poderCuracion; 
    }
    public boolean esRevivir() {
        return esRevivir; 
    }

    public boolean usar(Pokemon p) {
        if (cantidad <= 0) return false;
        if (esRevivir) {
            if (p.estaDerrotado()) {
                p.revivir(50);
                cantidad--;
                return true;
            }
            return false;
        } else {
            if (!p.estaDerrotado() && p.getHpActual() < p.getHpMax()) {
                p.curar(poderCuracion);
                cantidad--;
                return true;
            }
            return false;
        }
    }
}
