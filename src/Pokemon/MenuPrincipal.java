package Pokemon;

import javax.swing.*;
import java.awt.*;

public class MenuPrincipal extends JPanel {
    public MenuPrincipal(PokemonShenanigans mainApp) {
        setLayout(new BorderLayout());

        FondoDegradado fondo = new FondoDegradado(UIUtils.AZUL_OSCURO, new Color(12, 15, 24));
        fondo.setLayout(new GridBagLayout());
        add(fondo, BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.gridx = 0;

        JLabel titulo = UIUtils.crearTitulo("LOBBY DEL ENTRENADOR", 40);
        JPanel barra = UIUtils.crearBarraDecorativa(340);

        JButton btnEquipo = UIUtils.crearBotonEstilizado("Armar Equipo");
        JButton btnBatalla = UIUtils.crearBotonEstilizado("Jugar Batalla");
        JButton btnCerrarSesion = UIUtils.crearBotonSecundario("Cerrar Sesión");

      
btnBatalla.setPreferredSize(new Dimension(320, 54));
btnBatalla.addActionListener(e -> mainApp.iniciarCombate());

btnEquipo.addActionListener(e -> mainApp.cambiarPantalla("ArmarEquipo"));
btnCerrarSesion.addActionListener(e -> mainApp.cambiarPantalla("Inicio"));
        gbc.gridy = 0; fondo.add(titulo, gbc);
        gbc.insets = new Insets(0, 15, 40, 15);
        gbc.gridy = 1; fondo.add(barra, gbc);
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.gridy = 2; fondo.add(btnEquipo, gbc);
        gbc.gridy = 3; fondo.add(btnBatalla, gbc);
        gbc.gridy = 4; fondo.add(btnCerrarSesion, gbc);
    }
}