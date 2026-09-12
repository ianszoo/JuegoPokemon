package Pokemon;

import javax.swing.*;
import java.awt.*;

public class PantallaInicio extends JPanel {
    public PantallaInicio(PokemonShenanigans mainApp) {
        setLayout(new GridBagLayout());
        setBackground(UIUtils.BG_OSCURO);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.gridx = 0;

        JLabel titulo = new JLabel("POKÉMON SHENANIGANS");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 54));
        titulo.setForeground(UIUtils.PKMN_AMARILLO);
        titulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 40, 0));

        JButton btnLogin = UIUtils.crearBotonEstilizado("Iniciar Sesión");
        JButton btnCrear = UIUtils.crearBotonEstilizado("Crear Usuario");
        JButton btnSalir = UIUtils.crearBotonEstilizado("Salir");

        btnLogin.addActionListener(e -> mainApp.cambiarPantalla("Login"));
        btnCrear.addActionListener(e -> mainApp.cambiarPantalla("Registro"));
        btnSalir.addActionListener(e -> System.exit(0));

        gbc.gridy = 0; add(titulo, gbc);
        gbc.gridy = 1; add(btnLogin, gbc);
        gbc.gridy = 2; add(btnCrear, gbc);
        gbc.gridy = 3; add(btnSalir, gbc);
    }
}