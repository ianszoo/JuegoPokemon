package Pokemon;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class PantallaArmarEquipo extends JPanel {
    public PantallaArmarEquipo(PokemonShenanigans mainApp) {
        setLayout(new BorderLayout());

        FondoDegradado fondo = new FondoDegradado(UIUtils.AZUL_OSCURO, new Color(12, 15, 24));
        fondo.setLayout(new BorderLayout(20, 20));
        fondo.setBorder(new EmptyBorder(20, 30, 20, 30));
        add(fondo, BorderLayout.CENTER);

        JLabel titulo = UIUtils.crearTitulo("CONSTRUCTOR DE EQUIPO", 34);
        fondo.add(titulo, BorderLayout.NORTH);

        JPanel panelSlots = new JPanel(new GridLayout(6, 1, 8, 8));
        panelSlots.setOpaque(false);

        for (int i = 1; i <= 6; i++) {
            TarjetaRedondeada slot = new TarjetaRedondeada(UIUtils.AZUL_MEDIO, UIUtils.AMARILLO_OSCURO, 16);
            slot.setLayout(new BorderLayout());

            JLabel lblVacio = new JLabel("[ Slot " + i + " Vacío - Esperando conexión con DB de Pokémon ]", SwingConstants.CENTER);
            lblVacio.setForeground(new Color(180, 188, 200));
            lblVacio.setFont(new Font("SansSerif", Font.ITALIC, 16));
            lblVacio.setBorder(new EmptyBorder(10, 10, 10, 10));

            slot.add(lblVacio, BorderLayout.CENTER);
            panelSlots.add(slot);
        }
        fondo.add(panelSlots, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panelBotones.setOpaque(false);

        JButton btnConectar = UIUtils.crearBotonExito("Elegir Pokémon");
        btnConectar.setEnabled(false);

        JButton btnVolver = UIUtils.crearBotonSecundario("Volver al Lobby");
        btnVolver.addActionListener(e -> mainApp.cambiarPantalla("MenuPrincipal"));

        panelBotones.add(btnConectar);
        panelBotones.add(btnVolver);
        fondo.add(panelBotones, BorderLayout.SOUTH);
    }
}