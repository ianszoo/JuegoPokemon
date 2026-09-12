package Pokemon;

import javax.swing.*;
import java.awt.*;

public class UIUtils {
 
    public static final Color BG_OSCURO     = new Color(20, 24, 36);
    public static final Color BG_CLARO      = new Color(57, 62, 70);

    public static final Color AZUL_OSCURO   = new Color(20, 24, 36);
    public static final Color AZUL_MEDIO    = new Color(35, 45, 68);
    public static final Color AZUL_CLARO    = new Color(52, 66, 98);

    public static final Color PKMN_ROJO       = new Color(213, 20, 20);
    public static final Color ROJO_POKEDEX    = new Color(213, 20, 20);
    public static final Color ROJO_HOVER      = new Color(240, 60, 60);
    public static final Color ROJO_PRESIONADO = new Color(160, 10, 10);

    public static final Color VERDE_OK        = new Color(46, 160, 90);
    public static final Color VERDE_HOVER     = new Color(70, 190, 115);
    public static final Color VERDE_PRESIONADO= new Color(30, 120, 65);

    public static final Color PKMN_AMARILLO = new Color(255, 203, 5);
    public static final Color AMARILLO      = new Color(255, 203, 5);
    public static final Color AMARILLO_OSCURO = new Color(224, 168, 0);

    public static final Color TEXTO_CLARO = new Color(238, 238, 238);
    public static final Color ERROR_COLOR = new Color(255, 110, 110);

 
    public static JButton crearBotonEstilizado(String texto) {
        return new RoundedButton(texto);
    }

    public static JButton crearBotonSecundario(String texto) {
        return new RoundedButton(texto, AZUL_CLARO, AZUL_CLARO.brighter(), AZUL_OSCURO);
    }

    public static JButton crearBotonExito(String texto) {
        return new RoundedButton(texto, VERDE_OK, VERDE_HOVER, VERDE_PRESIONADO);
    }

   
    public static JTextField crearCampoTexto(int columnas) {
        JTextField campo = new JTextField(columnas);
        estilizarCampo(campo);
        return campo;
    }

    public static void estilizarCampo(JTextField campo) {
        campo.setFont(new Font("SansSerif", Font.PLAIN, 20));
        campo.setHorizontalAlignment(JTextField.CENTER);
        campo.setBackground(Color.WHITE);
        campo.setForeground(AZUL_OSCURO);
        campo.setCaretColor(AZUL_OSCURO);
        campo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(AMARILLO, 2, true),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)));
    }

 
    public static JLabel crearTitulo(String texto, int tamano) {
        JLabel lbl = new JLabel(texto, SwingConstants.CENTER);
        lbl.setFont(new Font("SansSerif", Font.BOLD, tamano));
        lbl.setForeground(AMARILLO);
        return lbl;
    }

    public static JLabel crearSubtitulo(String texto) {
        JLabel lbl = new JLabel(texto, SwingConstants.CENTER);
        lbl.setFont(new Font("SansSerif", Font.ITALIC, 15));
        lbl.setForeground(TEXTO_CLARO);
        return lbl;
    }

    public static JLabel crearEtiqueta(String texto) {
        JLabel lbl = new JLabel(texto);
        lbl.setForeground(TEXTO_CLARO);
        lbl.setFont(new Font("SansSerif", Font.BOLD, 15));
        return lbl;
    }

    public static JLabel crearEtiquetaAyuda(String texto) {
        JLabel lbl = new JLabel(texto, SwingConstants.CENTER);
        lbl.setForeground(new Color(180, 188, 200));
        lbl.setFont(new Font("SansSerif", Font.PLAIN, 12));
        return lbl;
    }

    public static JLabel crearEtiquetaError() {
        JLabel lbl = new JLabel(" ", SwingConstants.CENTER);
        lbl.setForeground(ERROR_COLOR);
        lbl.setFont(new Font("SansSerif", Font.BOLD, 13));
        return lbl;
    }

 
    public static JPanel crearBarraDecorativa(int ancho) {
        JPanel barra = new JPanel();
        barra.setPreferredSize(new Dimension(ancho, 4));
        barra.setMaximumSize(new Dimension(ancho, 4));
        barra.setBackground(ROJO_POKEDEX);
        return barra;
    }
}