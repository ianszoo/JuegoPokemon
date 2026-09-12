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
    private Tipo tipoPrimario;
    private Tipo tipoSecundario;
    private int hpMax;
    private int hpActual;
    private int ataqueBase;
    private int defensaBase;
    private String nombreAtaque;
    private Tipo tipoAtaque;
    private int potenciaAtaque;
    private boolean activo;
    private String rutaImagen;

    public Pokemon(String nombre, int nivel, Tipo tipo1, Tipo tipo2, int psBase, int atk, int def, String nombreAtaque, Tipo tipoAtaque, int potAtaque, String rutaImagen) {
        this.nombre = nombre;
        this.nivel = nivel;
        this.tipoPrimario = tipo1;
        this.tipoSecundario = tipo2;
        this.hpMax = (int) Math.floor((2 * psBase * nivel) / 100.0) + nivel + 10;
        this.hpActual = this.hpMax;
        this.ataqueBase = atk;
        this.defensaBase = def;
        this.nombreAtaque = nombreAtaque;
        this.tipoAtaque = tipoAtaque;
        this.potenciaAtaque = potAtaque;
        this.activo = false;
        this.rutaImagen = rutaImagen;
    }

    public Pokemon(Pokemon p) {
        this.nombre = p.nombre;
        this.nivel = p.nivel;
        this.tipoPrimario = p.tipoPrimario;
        this.tipoSecundario = p.tipoSecundario;
        this.hpMax = p.hpMax;
        this.hpActual = p.hpMax;
        this.ataqueBase = p.ataqueBase;
        this.defensaBase = p.defensaBase;
        this.nombreAtaque = p.nombreAtaque;
        this.tipoAtaque = p.tipoAtaque;
        this.potenciaAtaque = p.potenciaAtaque;
        this.activo = false;
        this.rutaImagen = p.rutaImagen;
    }

    public boolean estaDerrotado() {
        return hpActual <= 0;
    }

    public void recibirDanio(int danio) {
        this.hpActual -= danio;
        if (this.hpActual < 0) this.hpActual = 0;
    }

    public void curar(int cantidad) {
        this.hpActual += cantidad;
        if (this.hpActual > this.hpMax) this.hpActual = this.hpMax;
    }

    public void revivir(int porcentaje) {
        if (estaDerrotado()) {
            this.hpActual = (int) (this.hpMax * (porcentaje / 100.0));
            if (this.hpActual <= 0) this.hpActual = 1;
        }
    }

    public String getNombre() {
        return nombre; 
    }
    public int getNivel() {
        return nivel; 
    }
    public void setNivel(int nivel) {
        this.nivel = nivel; 
    }
    public Tipo getTipoPrimario() {
        return tipoPrimario; 
    }
    public Tipo getTipoSecundario() {
        return tipoSecundario; 
    }
    public int getHpMax() {
        return hpMax; 
    }
    public int getHpActual() {
        return hpActual; 
    }
    public int getAtaqueBase() {
        return ataqueBase; 
    }
    public int getDefensaBase() {
        return defensaBase; 
    }
    public String getNombreAtaque() {
        return nombreAtaque; 
    }
    public Tipo getTipoAtaque() {
        return tipoAtaque; 
    }
    public int getPotenciaAtaque() {
        return potenciaAtaque; 
    }
    public boolean isActivo() {
        return activo; 
    }
    public void setActivo(boolean activo) {
        this.activo = activo; 
    }
    public String getRutaImagen() {
        return rutaImagen; 
    }

    public String getTiposString() {
        if (tipoSecundario == null || tipoSecundario == Tipo.NINGUNO) {
            return tipoPrimario.toString();
        }
        return tipoPrimario.toString() + " / " + tipoSecundario.toString();
    }
    public void setHpActual(int hpActual) {
        this.hpActual = Math.max(0, Math.min(hpActual, this.hpMax));
    }

    public void setHpMax(int hpMax) {
        this.hpMax = hpMax;
    }

    public void setAtaque(int ataque) {
        this.ataqueBase = ataque;
    }
}
