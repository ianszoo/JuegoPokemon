package Pokemon;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.net.URL;
import java.util.Random;

public class PantallaCombate extends JPanel {

    private final PokemonShenanigans mainApp;
    private final CardLayout cardInterno;
    private final JPanel contenedorInterno;

    // Componentes de búsqueda
    private JLabel lblBuscando;
    private Timer timerBuscando;

    // Elementos visuales de la batalla
    private JLabel lblSpriteJugador;
    private JLabel lblSpriteRival;
    private JLabel lblNombreJugador;
    private JLabel lblNombreRival;
    private JLabel lblTipoJugador;
    private JLabel lblTipoRival;
    private JProgressBar barraHpJugador;
    private JProgressBar barraHpRival;
    private JLabel lblHpTextoJugador;
    private JLabel lblHpTextoRival;
    private JTextArea txtHistorialBatalla;

    // Botones de acción
    private JButton btnAtacar;
    private JButton btnCambiar;
    private JButton btnObjetos;
    private JButton btnEquipo;
    private JButton btnHistorial;

    private Usuario rivalActual;
    private ListaHistorial historial;
    private Combate combateActual;

    public PantallaCombate(PokemonShenanigans mainApp) {
        this.mainApp = mainApp;
        setLayout(new BorderLayout());

        cardInterno = new CardLayout();
        contenedorInterno = new JPanel(cardInterno);
        add(contenedorInterno, BorderLayout.CENTER);

        contenedorInterno.add(crearPanelBuscando(), "Buscando");
        contenedorInterno.add(crearPanelBatalla(), "Batalla");
    }

    private JPanel crearPanelBuscando() {
        FondoDegradado fondo = new FondoDegradado(UIUtils.AZUL_OSCURO, new Color(12, 15, 24));
        fondo.setLayout(new GridBagLayout());

        lblBuscando = new JLabel("Buscando rival...");
        lblBuscando.setForeground(UIUtils.AMARILLO_OSCURO);
        lblBuscando.setFont(new Font("SansSerif", Font.BOLD, 28));

        fondo.add(lblBuscando);
        return fondo;
    }

    private void animarBusquedaRival() {
        if (timerBuscando != null && timerBuscando.isRunning()) {
            timerBuscando.stop();
        }

        Random rnd = new Random();
        String[] nombres = PokemonFactory.NOMBRES_DISPONIBLES;
        int[] tick = {0};

        timerBuscando = new Timer(100, e -> {
            tick[0]++;
            lblBuscando.setText("Buscando rival: " + nombres[rnd.nextInt(nombres.length)] + " ...");

            if (tick[0] >= 10) {
                timerBuscando.stop();
                lblBuscando.setText("¡Rival encontrado: " + rivalActual.getUsername() + "!");

                Timer pausa = new Timer(700, ev -> {
                    popularBatalla();
                    cardInterno.show(contenedorInterno, "Batalla");
                });
                pausa.setRepeats(false);
                pausa.start();
            }
        });
        timerBuscando.start();
    }

    private JPanel crearPanelBatalla() {
        JPanel contenedor = new JPanel(new BorderLayout());

        FondoImagen fondo = new FondoImagen("/imagenes/background.png");
        fondo.setLayout(null);

        JButton btnHuir = UIUtils.crearBotonSecundario("Huir");
        btnHuir.setBounds(15, 12, 90, 32);
        btnHuir.addActionListener(e -> {
            int resp = JOptionPane.showConfirmDialog(this, "¿Deseas huir del combate?", "Huir", JOptionPane.YES_NO_OPTION);
            if (resp == JOptionPane.YES_OPTION) {
                mainApp.cambiarPantalla("MenuPrincipal");
            }
        });
        fondo.add(btnHuir);

        // Panel Rival
        lblSpriteRival = new JLabel();
        lblSpriteRival.setBounds(680, 20, 220, 220);
        fondo.add(lblSpriteRival);

        JPanel panelInfoRival = crearPanelInfo();
        lblNombreRival = new JLabel();
        lblTipoRival = new JLabel();
        lblHpTextoRival = new JLabel();
        barraHpRival = new JProgressBar();
        configurarPanelInfo(panelInfoRival, lblNombreRival, lblTipoRival, lblHpTextoRival, barraHpRival);
        panelInfoRival.setBounds(40, 45, 310, 95);
        fondo.add(panelInfoRival);

        // Panel Jugador
        lblSpriteJugador = new JLabel();
        lblSpriteJugador.setBounds(60, 260, 260, 260);
        fondo.add(lblSpriteJugador);

        JPanel panelInfoJugador = crearPanelInfo();
        lblNombreJugador = new JLabel();
        lblTipoJugador = new JLabel();
        lblHpTextoJugador = new JLabel();
        barraHpJugador = new JProgressBar();
        configurarPanelInfo(panelInfoJugador, lblNombreJugador, lblTipoJugador, lblHpTextoJugador, barraHpJugador);
        panelInfoJugador.setBounds(640, 340, 310, 95);
        fondo.add(panelInfoJugador);

        contenedor.add(fondo, BorderLayout.CENTER);

        // Historial y Menú inferior
        JPanel panelInferior = new JPanel(new BorderLayout());
        panelInferior.setBackground(new Color(12, 15, 24));

        txtHistorialBatalla = new JTextArea(4, 50);
        txtHistorialBatalla.setEditable(false);
        txtHistorialBatalla.setFont(new Font("Monospaced", Font.BOLD, 12));
        txtHistorialBatalla.setBackground(new Color(20, 24, 36));
        txtHistorialBatalla.setForeground(new Color(240, 240, 240));
        txtHistorialBatalla.setBorder(new EmptyBorder(6, 12, 6, 12));
        JScrollPane scrollLog = new JScrollPane(txtHistorialBatalla);
        scrollLog.setBorder(BorderFactory.createLineBorder(UIUtils.AZUL_MEDIO, 1));
        panelInferior.add(scrollLog, BorderLayout.CENTER);

        panelInferior.add(crearPanelMenu(), BorderLayout.SOUTH);
        contenedor.add(panelInferior, BorderLayout.SOUTH);

        return contenedor;
    }

    private JPanel crearPanelInfo() {
        TarjetaRedondeada panel = new TarjetaRedondeada(UIUtils.AZUL_MEDIO, UIUtils.AMARILLO_OSCURO, 14);
        panel.setLayout(new GridLayout(4, 1, 2, 2));
        panel.setBorder(new EmptyBorder(8, 12, 8, 12));
        return panel;
    }

    private void configurarPanelInfo(JPanel panel, JLabel lblNombre, JLabel lblTipo, JLabel lblHpTexto, JProgressBar barra) {
        lblNombre.setForeground(Color.WHITE);
        lblNombre.setFont(new Font("SansSerif", Font.BOLD, 15));

        lblTipo.setForeground(UIUtils.AMARILLO);
        lblTipo.setFont(new Font("SansSerif", Font.ITALIC, 12));

        barra.setStringPainted(false);
        barra.setForeground(new Color(76, 217, 100));
        barra.setBackground(new Color(40, 44, 56));
        barra.setPreferredSize(new Dimension(100, 12));
        barra.setBorderPainted(false);

        lblHpTexto.setForeground(new Color(210, 215, 225));
        lblHpTexto.setFont(new Font("SansSerif", Font.PLAIN, 12));

        panel.add(lblNombre);
        panel.add(lblTipo);
        panel.add(barra);
        panel.add(lblHpTexto);
    }

    private JPanel crearPanelMenu() {
        JPanel panelMenu = new JPanel(new GridLayout(1, 5, 10, 10));
        panelMenu.setBackground(new Color(12, 15, 24));
        panelMenu.setBorder(new EmptyBorder(10, 15, 12, 15));

        btnAtacar = UIUtils.crearBotonEstilizado("ATACAR");
        btnCambiar = UIUtils.crearBotonEstilizado("CAMBIAR");
        btnObjetos = UIUtils.crearBotonEstilizado("OBJETOS");
        btnEquipo = UIUtils.crearBotonEstilizado("MI EQUIPO");
        btnHistorial = UIUtils.crearBotonEstilizado("HISTORIAL");

        // AQUÍ ESTÁN TODOS LOS ACTION LISTENERS CONECTADOS Y FUNCIONANDO:
        btnAtacar.addActionListener(e -> ejecutarAtaque());
        btnCambiar.addActionListener(e -> mostrarDialogoCambio());
        btnObjetos.addActionListener(e -> mostrarDialogoObjetos());
        btnEquipo.addActionListener(e -> mostrarDialogoEquipo());
        btnHistorial.addActionListener(e -> mostrarDialogoHistorialCompleto());

        panelMenu.add(btnAtacar);
        panelMenu.add(btnCambiar);
        panelMenu.add(btnObjetos);
        panelMenu.add(btnEquipo);
        panelMenu.add(btnHistorial);

        return panelMenu;
    }

    private void ejecutarAtaque() {
        if (combateActual == null || combateActual.isFinalizado()) return;

        combateActual.atacar();
        popularBatalla();
        verificarFinCombate();
    }

    private void mostrarDialogoCambio() {
        if (combateActual == null || combateActual.isFinalizado()) return;

        Pokemon[] equipo = combateActual.getJugador().getEquipo().toArray();
        Pokemon activo = combateActual.getJugador().getEquipo().getPokemonActivo();

        String[] opciones = new String[equipo.length];
        for (int i = 0; i < equipo.length; i++) {
            Pokemon p = equipo[i];
            opciones[i] = p.getNombre() + " (HP: " + p.getHpActual() + "/" + p.getHpMax() + ")"
                    + (p == activo ? " [ACTIVO]" : (p.estaDerrotado() ? " [DERROTADO]" : ""));
        }

        int seleccion = JOptionPane.showOptionDialog(
                this,
                "Selecciona el Pokémon a enviar al combate:",
                "Cambiar Pokémon",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                opciones,
                opciones[0]
        );

        if (seleccion >= 0 && seleccion < equipo.length) {
            Pokemon elegido = equipo[seleccion];
            if (elegido == activo) {
                JOptionPane.showMessageDialog(this, "Ese Pokémon ya está combatiendo.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (elegido.estaDerrotado()) {
                JOptionPane.showMessageDialog(this, "No puedes seleccionar un Pokémon derrotado.", "Inválido", JOptionPane.ERROR_MESSAGE);
                return;
            }

            boolean cambio = combateActual.cambiarPokemon(elegido.getNombre());
            if (cambio) {
                popularBatalla();
                verificarFinCombate();
            }
        }
    }

    private void mostrarDialogoObjetos() {
        if (combateActual == null || combateActual.isFinalizado()) return;

        Objeto[] inventario = combateActual.getJugador().getInventario().toArray();
        String[] opciones = new String[inventario.length];
        for (int i = 0; i < inventario.length; i++) {
            Objeto obj = inventario[i];
            opciones[i] = obj.getNombre() + " (x" + obj.getCantidad() + ") - " + obj.getDescripcion();
        }

        int seleccion = JOptionPane.showOptionDialog(
                this,
                "Selecciona un objeto para utilizar:",
                "Mochila de Objetos",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                opciones,
                opciones[0]
        );

        if (seleccion >= 0 && seleccion < inventario.length) {
            Objeto obj = inventario[seleccion];
            if (obj.getCantidad() <= 0) {
                JOptionPane.showMessageDialog(this, "No quedan unidades de " + obj.getNombre() + ".", "Agotado", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (obj.esRevivir()) {
                Pokemon[] equipo = combateActual.getJugador().getEquipo().toArray();
                String[] pOpciones = new String[equipo.length];
                for (int i = 0; i < equipo.length; i++) {
                    pOpciones[i] = equipo[i].getNombre() + (equipo[i].estaDerrotado() ? " [DERROTADO]" : " [VIVO]");
                }

                int selPkmn = JOptionPane.showOptionDialog(
                        this, "Selecciona a quién revivir:", "Revivir",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, pOpciones, pOpciones[0]);

                if (selPkmn >= 0 && selPkmn < equipo.length) {
                    Pokemon objetivo = equipo[selPkmn];
                    if (!objetivo.estaDerrotado()) {
                        JOptionPane.showMessageDialog(this, objetivo.getNombre() + " ya tiene salud.", "Aviso", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    obj.usar(objetivo);
                    historial.agregar(combateActual.getJugador().getUsername() + " utilizó " + obj.getNombre() + " en " + objetivo.getNombre() + ".");
                    combateActual.contraataqueRival();
                    popularBatalla();
                    verificarFinCombate();
                }
            } else {
                boolean exito = combateActual.usarObjeto(obj.getNombre());
                if (!exito) {
                    JOptionPane.showMessageDialog(this, "El Pokémon activo ya tiene la salud al máximo.", "Sin efecto", JOptionPane.WARNING_MESSAGE);
                } else {
                    popularBatalla();
                    verificarFinCombate();
                }
            }
        }
    }

    private void mostrarDialogoEquipo() {
        if (combateActual == null) return;

        Pokemon[] equipo = combateActual.getJugador().getEquipo().toArray();
        StringBuilder sb = new StringBuilder();
        sb.append("=== ESTADO DEL EQUIPO ===\n\n");

        for (int i = 0; i < equipo.length; i++) {
            Pokemon p = equipo[i];
            sb.append(p.getNombre()).append("  -  Nv. ").append(p.getNivel()).append("\n")
              .append("HP: ").append(p.getHpActual()).append("/").append(p.getHpMax())
              .append(p.estaDerrotado() ? " [DERROTADO]" : (p == combateActual.getJugador().getEquipo().getPokemonActivo() ? " [ACTIVO]" : " [DISPONIBLE]"))
              .append("\nTipos: ").append(p.getTiposString())
              .append("\n------------------------------------\n");
        }
        sb.append("Pokémon disponibles: ").append(combateActual.getJugador().getEquipo().contarDisponibles());

        JTextArea txt = new JTextArea(sb.toString());
        txt.setEditable(false);
        txt.setFont(new Font("Monospaced", Font.PLAIN, 13));
        txt.setBackground(new Color(25, 30, 42));
        txt.setForeground(Color.WHITE);

        JOptionPane.showMessageDialog(this, new JScrollPane(txt), "Mi Equipo", JOptionPane.PLAIN_MESSAGE);
    }

    private void mostrarDialogoHistorialCompleto() {
        if (historial == null) return;

        JTextArea txt = new JTextArea(historial.obtenerTextoCompleto());
        txt.setEditable(false);
        txt.setFont(new Font("Monospaced", Font.PLAIN, 12));
        txt.setBackground(new Color(25, 30, 42));
        txt.setForeground(Color.WHITE);

        JScrollPane scroll = new JScrollPane(txt);
        scroll.setPreferredSize(new Dimension(420, 320));
        JOptionPane.showMessageDialog(this, scroll, "Historial Completo de Batalla", JOptionPane.PLAIN_MESSAGE);
    }

    private void verificarFinCombate() {
        if (combateActual.isFinalizado()) {
            boolean ganoJugador = combateActual.getGanador() == combateActual.getJugador();
            String titulo = ganoJugador ? "¡VICTORIA!" : "¡DERROTA!";
            String mensaje = ganoJugador
                    ? "¡Has vencido a " + combateActual.getRival().getUsername() + "!"
                    : "Todos tus Pokémon han caído ante " + combateActual.getRival().getUsername() + ".";

            JOptionPane.showMessageDialog(this, mensaje, titulo,
                    ganoJugador ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE);

            mainApp.cambiarPantalla("MenuPrincipal");
            return;
        }

        Pokemon activo = combateActual.getJugador().getEquipo().getPokemonActivo();
        if (activo != null && activo.estaDerrotado() && combateActual.getJugador().getEquipo().tieneVivos()) {
            JOptionPane.showMessageDialog(this, "¡Tu Pokémon activo cayó! Elige a otro compañero.", "Cambio Forzoso", JOptionPane.WARNING_MESSAGE);
            mostrarDialogoCambio();
        }
    }

    public void iniciarNuevoCombate() {
        Usuario jugador = mainApp.getUsuarioLogueado();
        if (jugador == null) return;

        if (jugador.getEquipo().estaVacia()) {
            JOptionPane.showMessageDialog(this, "Debes tener al menos 1 Pokémon en tu equipo para combatir.", "Equipo Vacío", JOptionPane.WARNING_MESSAGE);
            mainApp.cambiarPantalla("ArmarEquipo");
            return;
        }

        jugador.getEquipo().reiniciarVidaEquipo();
        rivalActual = mainApp.getBaseDatosUsuarios().obtenerRivalAleatorio();
        historial = new ListaHistorial();
        combateActual = new Combate(jugador, rivalActual, historial);

        cardInterno.show(contenedorInterno, "Buscando");
        animarBusquedaRival();
    }

    private void popularBatalla() {
        Pokemon pJugador = combateActual.getJugador().getEquipo().getPokemonActivo();
        Pokemon pRival = combateActual.getRival().getEquipo().getPokemonActivo();

        if (pJugador != null) {
            lblSpriteJugador.setIcon(cargarSprite(pJugador.getRutaImagen().replace(".png", "Back.png"), 230, 230));
            lblNombreJugador.setText(pJugador.getNombre() + " (Nv. " + pJugador.getNivel() + ")");
            lblTipoJugador.setText("Tipo: " + pJugador.getTiposString());
            actualizarBarra(barraHpJugador, lblHpTextoJugador, pJugador);
        }

        if (pRival != null) {
            lblSpriteRival.setIcon(cargarSprite(pRival.getRutaImagen(), 200, 200));
            lblNombreRival.setText(pRival.getNombre() + " (Nv. " + pRival.getNivel() + ")");
            lblTipoRival.setText("Tipo: " + pRival.getTiposString());
            actualizarBarra(barraHpRival, lblHpTextoRival, pRival);
        }

        txtHistorialBatalla.setText(historial.obtenerTextoCompleto());
        txtHistorialBatalla.setCaretPosition(txtHistorialBatalla.getDocument().getLength());
    }

    private void actualizarBarra(JProgressBar barra, JLabel lblTexto, Pokemon p) {
        barra.setMaximum(p.getHpMax());
        barra.setValue(p.getHpActual());

        double pct = (double) p.getHpActual() / p.getHpMax();
        if (pct > 0.5) barra.setForeground(new Color(76, 217, 100));
        else if (pct > 0.2) barra.setForeground(new Color(255, 204, 0));
        else barra.setForeground(new Color(255, 59, 48));

        lblTexto.setText("❤️ " + p.getHpActual() + " / " + p.getHpMax() + " HP");
    }

    private ImageIcon cargarSprite(String nombreArchivo, int ancho, int alto) {
        URL url = getClass().getResource("/Sprites/" + nombreArchivo);
        if (url != null) {
            Image escalada = new ImageIcon(url).getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
            return new ImageIcon(escalada);
        }
        Image placeholder = new java.awt.image.BufferedImage(ancho, alto, java.awt.image.BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = (Graphics2D) placeholder.getGraphics();
        g2.setColor(UIUtils.AMARILLO_OSCURO);
        g2.drawOval(4, 4, ancho - 8, alto - 8);
        g2.dispose();
        return new ImageIcon(placeholder);
    }
}