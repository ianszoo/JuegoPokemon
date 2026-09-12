package Pokemon;

import javax.swing.*;
import java.awt.*;

public class UIUtils {
    public static final Color BG_OSCURO = new Color(34, 40, 49);
    public static final Color BG_CLARO = new Color(57, 62, 70);
    public static final Color PKMN_ROJO = new Color(238, 21, 21);
    public static final Color PKMN_AMARILLO = new Color(255, 203, 5);
    public static final Color TEXTO_CLARO = new Color(238, 238, 238);
    public static final Color ERROR_COLOR = new Color(255, 85, 85);

    public static JButton crearBotonEstilizado(String texto) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("SansSerif", Font.BOLD, 20));
        btn.setBackground(PKMN_ROJO);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setPreferredSize(new Dimension(250, 50));
        btn.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (btn.isEnabled()) btn.setBackground(new Color(255, 60, 60));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                if (btn.isEnabled()) btn.setBackground(PKMN_ROJO);
            }
        });
        return btn;
    }
}