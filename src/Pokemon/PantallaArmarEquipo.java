package Pokemon;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.net.URL;

public class PantallaArmarEquipo extends JPanel {

    private static final int MAX_EQUIPO = 4;

    private final PokemonShenanigans mainApp;
    private final TarjetaRedondeada[] tarjetasSlots = new TarjetaRedondeada[MAX_EQUIPO];
    private final JLabel lblContador;

  
    private Pokemon[] equipoLocal = new Pokemon[MAX_EQUIPO];
    private int cantidadLocal = 0;

    public PantallaArmarEquipo(PokemonShenanigans mainApp) {
        this.mainApp = mainApp;
        setLayout(new BorderLayout());

        FondoDegradado fondo = new FondoDegradado(UIUtils.AZUL_OSCURO, new Color(12, 15, 24));
        fondo.setLayout(new BorderLayout(20, 20));
        fondo.setBorder(new EmptyBorder(20, 30, 20, 30));
        add(fondo, BorderLayout.CENTER);

        
        JPanel panelHeader = new JPanel(new BorderLayout());
        panelHeader.setOpaque(false);
        JLabel titulo = UIUtils.crearTitulo("CONSTRUCTOR DE EQUIPO", 34);
        panelHeader.add(titulo, BorderLayout.CENTER);

        lblContador = new JLabel("Equipo: 0 / " + MAX_EQUIPO, SwingConstants.CENTER);
        lblContador.setForeground(UIUtils.AMARILLO_OSCURO);
        lblContador.setFont(new Font("SansSerif", Font.BOLD, 16));
        panelHeader.add(lblContador, BorderLayout.SOUTH);

        fondo.add(panelHeader, BorderLayout.NORTH);

        JPanel panelCentro = new JPanel(new BorderLayout(15, 15));
        panelCentro.setOpaque(false);

        JPanel panelSeleccion = new JPanel(new GridLayout(4, 5, 14, 14));
        panelSeleccion.setOpaque(false);
        for (String nombre : PokemonFactory.NOMBRES_DISPONIBLES) {
            panelSeleccion.add(crearTarjetaPokemon(nombre));
        }

        JScrollPane scrollSeleccion = new JScrollPane(panelSeleccion);
        scrollSeleccion.setOpaque(false);
        scrollSeleccion.getViewport().setOpaque(false);
        scrollSeleccion.setBorder(BorderFactory.createEmptyBorder());
        scrollSeleccion.getVerticalScrollBar().setUnitIncrement(16);

        panelCentro.add(scrollSeleccion, BorderLayout.CENTER);

      
        JPanel panelSlots = new JPanel(new GridLayout(1, MAX_EQUIPO, 14, 14));
        panelSlots.setOpaque(false);
        panelSlots.setPreferredSize(new Dimension(0, 150));
        for (int i = 0; i < MAX_EQUIPO; i++) {
            panelSlots.add(crearSlotVacio(i));
        }
        panelCentro.add(panelSlots, BorderLayout.SOUTH);

        fondo.add(panelCentro, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panelBotones.setOpaque(false);

        JButton btnGuardar = UIUtils.crearBotonExito("Guardar Equipo");
        btnGuardar.addActionListener(e -> guardarEquipo());

        JButton btnVolver = UIUtils.crearBotonSecundario("Volver al Lobby");
        btnVolver.addActionListener(e -> mainApp.cambiarPantalla("MenuPrincipal"));

        panelBotones.add(btnGuardar);
        panelBotones.add(btnVolver);
        fondo.add(panelBotones, BorderLayout.SOUTH);

    
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                cargarEquipoDesdeUsuario();
            }
        });
    }


    private JComponent crearTarjetaPokemon(String nombreDisplay) {
        TarjetaRedondeada tarjeta = new TarjetaRedondeada(UIUtils.AZUL_MEDIO, UIUtils.AMARILLO_OSCURO, 14);
        tarjeta.setLayout(new BorderLayout(6, 6));
        tarjeta.setBorder(new EmptyBorder(10, 10, 10, 10));
        tarjeta.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        Pokemon muestra = PokemonFactory.crearPokemon(nombreDisplay, 1);

        JLabel lblSprite = new JLabel(cargarIcono(muestra.getRutaImagen(), 80, 80), SwingConstants.CENTER);
        lblSprite.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel lblNombre = new JLabel("<html><div style='text-align:center;'>" + nombreDisplay + "</div></html>", SwingConstants.CENTER);
        lblNombre.setForeground(Color.WHITE);
        lblNombre.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblNombre.setHorizontalAlignment(SwingConstants.CENTER);

        tarjeta.add(lblSprite, BorderLayout.CENTER);
        tarjeta.add(lblNombre, BorderLayout.SOUTH);

        tarjeta.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                agregarAlBuffer(nombreDisplay);
            }
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                tarjeta.setBackground(UIUtils.AZUL_OSCURO);
                tarjeta.repaint();
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                tarjeta.setBackground(UIUtils.AZUL_MEDIO);
                tarjeta.repaint();
            }
        });

        return tarjeta;
    }


    private JComponent crearSlotVacio(int indice) {
        TarjetaRedondeada slot = new TarjetaRedondeada(UIUtils.AZUL_MEDIO, UIUtils.AMARILLO_OSCURO, 16);
        slot.setLayout(new BorderLayout());
        slot.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        pintarSlotVacio(slot);

        tarjetasSlots[indice] = slot;

        slot.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                quitarDelBuffer(indice);
            }
        });

        return slot;
    }

    private void pintarSlotVacio(TarjetaRedondeada slot) {
        slot.removeAll();
        slot.setLayout(new BorderLayout());
        JLabel lbl = new JLabel("Vacío", SwingConstants.CENTER);
        lbl.setForeground(new Color(150, 158, 170));
        lbl.setFont(new Font("SansSerif", Font.ITALIC, 14));
        slot.add(lbl, BorderLayout.CENTER);
        slot.revalidate();
        slot.repaint();
    }

  
    private void cargarEquipoDesdeUsuario() {
        Usuario usuario = mainApp.getUsuarioLogueado();
        equipoLocal = new Pokemon[MAX_EQUIPO];
        cantidadLocal = 0;

        if (usuario != null) {
            Pokemon[] real = usuario.getEquipo().toArray();
            for (int i = 0; i < real.length && i < MAX_EQUIPO; i++) {
                equipoLocal[cantidadLocal++] = real[i];
            }
        }
        refrescarSlots();
    }

  
    private void agregarAlBuffer(String nombreDisplay) {
        if (mainApp.getUsuarioLogueado() == null) return;

        if (cantidadLocal >= MAX_EQUIPO) {
            JOptionPane.showMessageDialog(this,
                    "Tu equipo ya tiene " + MAX_EQUIPO + " Pokémon. Quita uno tocándolo abajo antes de agregar otro.",
                    "Equipo completo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        equipoLocal[cantidadLocal] = PokemonFactory.crearPokemon(nombreDisplay, 20);
        cantidadLocal++;
        refrescarSlots();
    }


    private void quitarDelBuffer(int indice) {
        if (indice >= cantidadLocal) return;

        for (int i = indice; i < cantidadLocal - 1; i++) {
            equipoLocal[i] = equipoLocal[i + 1];
        }
        equipoLocal[cantidadLocal - 1] = null;
        cantidadLocal--;
        refrescarSlots();
    }


    private void guardarEquipo() {
        Usuario usuario = mainApp.getUsuarioLogueado();
        if (usuario == null) return;

        if (cantidadLocal == 0) {
            JOptionPane.showMessageDialog(this,
                    "Elegí al menos un Pokémon antes de guardar.",
                    "Equipo vacío", JOptionPane.WARNING_MESSAGE);
            return;
        }

        usuario.getEquipo().vaciar();
        for (int i = 0; i < cantidadLocal; i++) {
            usuario.getEquipo().add(equipoLocal[i]);
        }

        JOptionPane.showMessageDialog(this,
                "Equipo guardado para " + usuario.getUsername() + ".",
                "Listo", JOptionPane.INFORMATION_MESSAGE);
    }


    private void refrescarSlots() {
        for (int i = 0; i < MAX_EQUIPO; i++) {
            TarjetaRedondeada slot = tarjetasSlots[i];

            if (i < cantidadLocal) {
                Pokemon p = equipoLocal[i];
                slot.removeAll();
                slot.setLayout(new BorderLayout(4, 4));
                slot.setBorder(new EmptyBorder(8, 8, 8, 8));

                JLabel lblIcono = new JLabel(cargarIcono(p.getRutaImagen(), 64, 64), SwingConstants.CENTER);
                JLabel lblNombre = new JLabel("<html><div style='text-align:center;'>" + p.getNombre() + "</div></html>", SwingConstants.CENTER);
                lblNombre.setForeground(Color.WHITE);
                lblNombre.setFont(new Font("SansSerif", Font.BOLD, 13));

                slot.add(lblIcono, BorderLayout.CENTER);
                slot.add(lblNombre, BorderLayout.SOUTH);
            } else {
                pintarSlotVacio(slot);
            }
            slot.revalidate();
            slot.repaint();
        }

        lblContador.setText("Equipo: " + cantidadLocal + " / " + MAX_EQUIPO);
    }

   
    private ImageIcon cargarIcono(String nombreArchivo, int ancho, int alto) {
        URL url = getClass().getResource("/Sprites/" + nombreArchivo);
        if (url != null) {
            ImageIcon icono = new ImageIcon(url);
            Image escalada = icono.getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
            return new ImageIcon(escalada);
        }
        Image placeholder = new java.awt.image.BufferedImage(ancho, alto, java.awt.image.BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = (Graphics2D) placeholder.getGraphics();
        g2.setColor(UIUtils.AMARILLO_OSCURO);
        g2.setStroke(new BasicStroke(2f));
        g2.drawOval(2, 2, ancho - 4, alto - 4);
        g2.dispose();
        return new ImageIcon(placeholder);
    }
}