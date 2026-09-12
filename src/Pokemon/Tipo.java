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
public enum Tipo {
    NORMAL, FUEGO, AGUA, PLANTA, ELECTRICO, HIELO, 
    LUCHA, VENENO, TIERRA, VOLADOR, PSIQUICO, BICHO, 
    ROCA, FANTASMA, DRAGON, ACERO, SINIESTRO, HADA, NINGUNO;
    
    public static double getMultiplicador(Tipo ataque, Tipo defensa) {
        if (ataque == null || defensa == null || ataque == NINGUNO || defensa == NINGUNO) {
            return 1.0;
        }
        switch (ataque) {
            case FUEGO:
                if (defensa == PLANTA || defensa == HIELO || defensa == BICHO || defensa == ACERO) return 2.0;
                if (defensa == FUEGO || defensa == AGUA || defensa == ROCA || defensa == DRAGON) return 0.5;
                break;
            case AGUA:
                if (defensa == FUEGO || defensa == TIERRA || defensa == ROCA) return 2.0;
                if (defensa == AGUA || defensa == PLANTA || defensa == DRAGON) return 0.5;
                break;
            case PLANTA:
                if (defensa == AGUA || defensa == TIERRA || defensa == ROCA) return 2.0;
                if (defensa == FUEGO || defensa == PLANTA || defensa == VENENO || defensa == VOLADOR || defensa == BICHO || defensa == DRAGON || defensa == ACERO) return 0.5;
                break;
            case ELECTRICO:
                if (defensa == AGUA || defensa == VOLADOR) return 2.0;
                if (defensa == PLANTA || defensa == ELECTRICO || defensa == DRAGON) return 0.5;
                if (defensa == TIERRA) return 0.0;
                break;
            case HIELO:
                if (defensa == PLANTA || defensa == TIERRA || defensa == VOLADOR || defensa == DRAGON) return 2.0;
                if (defensa == FUEGO || defensa == AGUA || defensa == HIELO || defensa == ACERO) return 0.5;
                break;
            case LUCHA:
                if (defensa == NORMAL || defensa == HIELO || defensa == ROCA || defensa == SINIESTRO || defensa == ACERO) return 2.0;
                if (defensa == VENENO || defensa == VOLADOR || defensa == PSIQUICO || defensa == BICHO || defensa == HADA) return 0.5;
                if (defensa == FANTASMA) return 0.0;
                break;
            case VENENO:
                if (defensa == PLANTA || defensa == HADA) return 2.0;
                if (defensa == VENENO || defensa == TIERRA || defensa == ROCA || defensa == FANTASMA) return 0.5;
                if (defensa == ACERO) return 0.0;
                break;
            case TIERRA:
                if (defensa == FUEGO || defensa == ELECTRICO || defensa == VENENO || defensa == ROCA || defensa == ACERO) return 2.0;
                if (defensa == PLANTA || defensa == BICHO) return 0.5;
                if (defensa == VOLADOR) return 0.0;
                break;
            case VOLADOR:
                if (defensa == PLANTA || defensa == LUCHA || defensa == BICHO) return 2.0;
                if (defensa == ELECTRICO || defensa == ROCA || defensa == ACERO) return 0.5;
                break;
            case PSIQUICO:
                if (defensa == LUCHA || defensa == VENENO) return 2.0;
                if (defensa == PSIQUICO || defensa == ACERO) return 0.5;
                if (defensa == SINIESTRO) return 0.0;
                break;
            case BICHO:
                if (defensa == PLANTA || defensa == PSIQUICO || defensa == SINIESTRO) return 2.0;
                if (defensa == FUEGO || defensa == LUCHA || defensa == VENENO || defensa == VOLADOR || defensa == FANTASMA || defensa == ACERO || defensa == HADA) return 0.5;
                break;
            case ROCA:
                if (defensa == FUEGO || defensa == HIELO || defensa == VOLADOR || defensa == BICHO) return 2.0;
                if (defensa == LUCHA || defensa == TIERRA || defensa == ACERO) return 0.5;
                break;
            case FANTASMA:
                if (defensa == PSIQUICO || defensa == FANTASMA) return 2.0;
                if (defensa == SINIESTRO) return 0.5;
                if (defensa == NORMAL) return 0.0;
                break;
            case DRAGON:
                if (defensa == DRAGON) return 2.0;
                if (defensa == ACERO) return 0.5;
                if (defensa == HADA) return 0.0;
                break;
            case SINIESTRO:
                if (defensa == PSIQUICO || defensa == FANTASMA) return 2.0;
                if (defensa == LUCHA || defensa == SINIESTRO || defensa == HADA) return 0.5;
                break;
            case ACERO:
                if (defensa == HIELO || defensa == ROCA || defensa == HADA) return 2.0;
                if (defensa == FUEGO || defensa == AGUA || defensa == ELECTRICO || defensa == ACERO) return 0.5;
                break;
            case HADA:
                if (defensa == LUCHA || defensa == DRAGON || defensa == SINIESTRO) return 2.0;
                if (defensa == FUEGO || defensa == VENENO || defensa == ACERO) return 0.5;
                break;
            case NORMAL:
                if (defensa == ROCA || defensa == ACERO) return 0.5;
                if (defensa == FANTASMA) return 0.0;
                break;
        }
        return 1.0;
    }
}
