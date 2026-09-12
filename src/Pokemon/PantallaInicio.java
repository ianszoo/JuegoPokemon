package Pokemon;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class PantallaInicio extends JPanel {
    public PantallaInicio(PokemonShenanigans mainApp) {
        setLayout(new BorderLayout());

        FondoDegradado fondo = new FondoDegradado(UIUtils.AZUL_OSCURO, new Color(12, 15, 24));
        fondo.setLayout(new GridBagLayout());
        add(fondo, BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 15, 12, 15);
        gbc.gridx = 0;

        JLabel lblLogo = crearLogo(120, 120);
        JLabel titulo = UIUtils.crearTitulo("POKÉMON SHENANIGANS", 46);
        JPanel barra = UIUtils.crearBarraDecorativa(360);
        JLabel subtitulo = UIUtils.crearSubtitulo("Combate por la gloria");

        JButton btnLogin = UIUtils.crearBotonEstilizado("Iniciar Sesión");
        JButton btnCrear = UIUtils.crearBotonExito("Crear Usuario");
        JButton btnSalir = UIUtils.crearBotonSecundario("Salir");

        btnLogin.addActionListener(e -> mainApp.cambiarPantalla("Login"));
        btnCrear.addActionListener(e -> mainApp.cambiarPantalla("Registro"));
        btnSalir.addActionListener(e -> System.exit(0));

        int fila = 0;
        gbc.insets = new Insets(0, 15, 6, 15);
        if (lblLogo != null) {
            gbc.gridy = fila++; fondo.add(lblLogo, gbc);
        }
        gbc.insets = new Insets(6, 15, 12, 15);
        gbc.gridy = fila++; fondo.add(titulo, gbc);
        gbc.insets = new Insets(0, 15, 40, 15);
        gbc.gridy = fila++; fondo.add(barra, gbc);
        gbc.insets = new Insets(12, 15, 12, 15);
        gbc.gridy = fila++; fondo.add(subtitulo, gbc);
        gbc.gridy = fila++; fondo.add(btnLogin, gbc);
        gbc.gridy = fila++; fondo.add(btnCrear, gbc);
        gbc.gridy = fila++; fondo.add(btnSalir, gbc);
    }

    private JLabel crearLogo(int ancho, int alto) {
        URL url = getClass().getResource("/imagenes/Logo.png");
        if (url == null) return null;
        Image escalada = new ImageIcon(url).getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
        JLabel lbl = new JLabel(new ImageIcon(escalada));
        lbl.setHorizontalAlignment(SwingConstants.CENTER);
        return lbl;
    }
}