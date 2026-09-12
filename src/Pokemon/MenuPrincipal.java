package Pokemon;

import javax.swing.*;
import java.awt.*;

public class MenuPrincipal extends JPanel {
    public MenuPrincipal(PokemonShenanigans mainApp) {
        setLayout(new GridBagLayout());
        setBackground(UIUtils.BG_OSCURO);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.gridx = 0;

        JLabel titulo = new JLabel("LOBBY DEL ENTRENADOR");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 45));
        titulo.setForeground(UIUtils.PKMN_AMARILLO);
        titulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 40, 0));

        JButton btnEquipo = UIUtils.crearBotonEstilizado("Armar Equipo");
        JButton btnBatalla = UIUtils.crearBotonEstilizado("Jugar Batalla");
        JButton btnCerrarSesion = UIUtils.crearBotonEstilizado("Cerrar Sesión");

        // Batalla deshabilitada por ahora, como pediste
        btnBatalla.setEnabled(false);
        btnBatalla.setText("Batalla (Próximamente)");
        btnBatalla.setBackground(Color.GRAY);

        btnEquipo.addActionListener(e -> mainApp.cambiarPantalla("ArmarEquipo"));
        btnCerrarSesion.addActionListener(e -> mainApp.cambiarPantalla("Inicio"));

        gbc.gridy = 0; add(titulo, gbc);
        gbc.gridy = 1; add(btnEquipo, gbc);
        gbc.gridy = 2; add(btnBatalla, gbc);
        gbc.gridy = 3; add(btnCerrarSesion, gbc);
    }
}