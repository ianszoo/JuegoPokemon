package Pokemon;

import javax.swing.*;
import java.awt.*;

public class PantallaInicio extends JPanel {
    public PantallaInicio(PokemonShenanigans mainApp) {
        setLayout(new BorderLayout());

        FondoDegradado fondo = new FondoDegradado(UIUtils.AZUL_OSCURO, new Color(12, 15, 24));
        fondo.setLayout(new GridBagLayout());
        add(fondo, BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 15, 12, 15);
        gbc.gridx = 0;

        JLabel titulo = UIUtils.crearTitulo("POKÉMON SHENANIGANS", 46);
        JPanel barra = UIUtils.crearBarraDecorativa(360);
        JLabel subtitulo = UIUtils.crearSubtitulo("Combate por la gloria");

        JButton btnLogin = UIUtils.crearBotonEstilizado("Iniciar Sesión");
        JButton btnCrear = UIUtils.crearBotonExito("Crear Usuario");
        JButton btnSalir = UIUtils.crearBotonSecundario("Salir");

        btnLogin.addActionListener(e -> mainApp.cambiarPantalla("Login"));
        btnCrear.addActionListener(e -> mainApp.cambiarPantalla("Registro"));
        btnSalir.addActionListener(e -> System.exit(0));

        gbc.gridy = 0; fondo.add(titulo, gbc);
        gbc.insets = new Insets(0, 15, 40, 15);
        gbc.gridy = 1; fondo.add(barra, gbc);
        gbc.gridy = 2; fondo.add(subtitulo, gbc);
        gbc.insets = new Insets(12, 15, 12, 15);
        gbc.gridy = 3; fondo.add(btnLogin, gbc);
        gbc.gridy = 4; fondo.add(btnCrear, gbc);
        gbc.gridy = 5; fondo.add(btnSalir, gbc);
    }
}