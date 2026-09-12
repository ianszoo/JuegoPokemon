package Pokemon;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class PantallaArmarEquipo extends JPanel {
    public PantallaArmarEquipo(PokemonShenanigans mainApp) {
        setLayout(new BorderLayout(20, 20));
        setBackground(UIUtils.BG_OSCURO);
        setBorder(new EmptyBorder(20, 30, 20, 30));

        JLabel titulo = new JLabel("CONSTRUCTOR DE EQUIPO", SwingConstants.CENTER);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 36));
        titulo.setForeground(UIUtils.PKMN_AMARILLO);
        add(titulo, BorderLayout.NORTH);

        // Cascarón vacío de 6 slots
        JPanel panelSlots = new JPanel(new GridLayout(6, 1, 5, 5));
        panelSlots.setBackground(UIUtils.BG_CLARO);
        panelSlots.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));

        for (int i = 1; i <= 6; i++) {
            JPanel slot = new JPanel(new BorderLayout());
            slot.setBackground(UIUtils.BG_OSCURO);
            slot.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.DARK_GRAY));
            
            JLabel lblVacio = new JLabel("[ Slot " + i + " Vacío - Esperando conexión con DB de Pokémon ]", SwingConstants.CENTER);
            lblVacio.setForeground(Color.GRAY);
            lblVacio.setFont(new Font("SansSerif", Font.ITALIC, 18));
            
            slot.add(lblVacio, BorderLayout.CENTER);
            panelSlots.add(slot);
        }
        add(panelSlots, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panelBotones.setBackground(UIUtils.BG_OSCURO);

        JButton btnConectar = UIUtils.crearBotonEstilizado("Elegir Pokémon");
        btnConectar.setEnabled(false); // Listo para cuando se implementen los 20 pokemones
        
        JButton btnVolver = UIUtils.crearBotonEstilizado("Volver al Lobby");
        btnVolver.addActionListener(e -> mainApp.cambiarPantalla("MenuPrincipal"));

        panelBotones.add(btnConectar);
        panelBotones.add(btnVolver);
        add(panelBotones, BorderLayout.SOUTH);
    }
}