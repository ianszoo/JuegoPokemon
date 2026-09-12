package Pokemon;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;

public class PantallaCombate extends JPanel {

    private final PokemonShenanigans mainApp;
    private final CardLayout cardInterno;
    private final JPanel contenedorInterno;

    private JLabel lblBuscando;
    private Timer timerBuscando;

    // Nombres de ENTRENADORES para la animación de búsqueda (no Pokémon)
    private static final String[] NOMBRES_ENTRENADORES = {
        "Red", "Blue", "Cynthia", "Steven", "Lance",
        "Leon", "Ash", "Iris", "Alder", "Diantha"
    };

    private JLayeredPane campoLayered;
    private FondoImagen panelFondoCampo;
    private JButton btnHuir;
    private JLabel lblSpriteJugador;
    private JLabel lblSpriteRival;
    private JPanel panelInfoJugador;
    private JPanel panelInfoRival;
    private JLabel lblNombreJugador;
    private JLabel lblNombreRival;
    private JLabel lblTipoJugador;
    private JLabel lblTipoRival;
    private JProgressBar barraHpJugador;
    private JProgressBar barraHpRival;
    private JLabel lblHpTextoJugador;
    private JLabel lblHpTextoRival;
    private JTextArea txtHistorialBatalla;

    // --- Overlay embebido (reemplaza los JOptionPane) ---
    private JPanel overlay;
    private JLabel lblOverlayTitulo;
    private JPanel panelOverlayContenido;

    private JButton btnAtacar;
    private JButton btnCambiar;
    private JButton btnObjetos;
    private JButton btnEquipo;
    private JButton btnHistorial;

    private Usuario rivalActual;
    private ListaHistorial historial;
    private Combate combateActual;

    // Cola de mensajes para animar los turnos
    private final List<String> colaMensajes = new ArrayList<>();
    private Timer timerMensajes;
    private Runnable alTerminarAnimacion;

    public PantallaCombate(PokemonShenanigans mainApp) {
        this.mainApp = mainApp;
        setLayout(new BorderLayout());

        cardInterno = new CardLayout();
        contenedorInterno = new JPanel(cardInterno);
        add(contenedorInterno, BorderLayout.CENTER);

        contenedorInterno.add(crearPanelBuscando(), "Buscando");
        contenedorInterno.add(crearPanelBatalla(), "Batalla");
    }

    // ============================================================
    //  PANTALLA DE BÚSQUEDA
    // ============================================================

    private JPanel crearPanelBuscando() {
        FondoDegradado fondo = new FondoDegradado(UIUtils.AZUL_OSCURO, new Color(12, 15, 24));
        fondo.setLayout(new GridBagLayout());

        lblBuscando = new JLabel("Buscando rival...");
        lblBuscando.setForeground(UIUtils.AMARILLO_OSCURO);
        lblBuscando.setFont(new Font("SansSerif", Font.BOLD, 32));

        fondo.add(lblBuscando);
        return fondo;
    }

    private void animarBusquedaRival() {
        if (timerBuscando != null && timerBuscando.isRunning()) {
            timerBuscando.stop();
        }

        Random rnd = new Random();
        int[] tick = {0};

        timerBuscando = new Timer(180, e -> {
            tick[0]++;
            String nombreFalso = NOMBRES_ENTRENADORES[rnd.nextInt(NOMBRES_ENTRENADORES.length)];
            lblBuscando.setText("Buscando entrenador: " + nombreFalso + " ...");

            if (tick[0] >= 8) {
                timerBuscando.stop();
                lblBuscando.setText("¡Rival encontrado: " + rivalActual.getUsername() + "!");

                Timer pausa = new Timer(700, ev -> {
                    popularBatalla();
                    cardInterno.show(contenedorInterno, "Batalla");
                    recolocarComponentesResponsivo();
                });
                pausa.setRepeats(false);
                pausa.start();
            }
        });
        timerBuscando.start();
    }

    // ============================================================
    //  PANTALLA DE BATALLA
    // ============================================================

    private JPanel crearPanelBatalla() {
        JPanel contenedor = new JPanel(new BorderLayout());

        panelFondoCampo = new FondoImagen("/imagenes/background.png");
        panelFondoCampo.setLayout(null);

        btnHuir = UIUtils.crearBotonSecundario("Huir");
        btnHuir.addActionListener(e -> mostrarOverlayConfirmacion(
                "¿Huir del combate?",
                "Perderás el progreso de esta batalla.",
                () -> {
                    ocultarOverlay();
                    mainApp.cambiarPantalla("MenuPrincipal");
                }
        ));
        panelFondoCampo.add(btnHuir);

        lblSpriteRival = new JLabel();
        panelFondoCampo.add(lblSpriteRival);

        panelInfoRival = crearPanelInfo();
        lblNombreRival = new JLabel();
        lblTipoRival = new JLabel();
        lblHpTextoRival = new JLabel();
        barraHpRival = new JProgressBar();
        configurarPanelInfo(panelInfoRival, lblNombreRival, lblTipoRival, lblHpTextoRival, barraHpRival);
        panelFondoCampo.add(panelInfoRival);

        lblSpriteJugador = new JLabel();
        panelFondoCampo.add(lblSpriteJugador);

        panelInfoJugador = crearPanelInfo();
        lblNombreJugador = new JLabel();
        lblTipoJugador = new JLabel();
        lblHpTextoJugador = new JLabel();
        barraHpJugador = new JProgressBar();
        configurarPanelInfo(panelInfoJugador, lblNombreJugador, lblTipoJugador, lblHpTextoJugador, barraHpJugador);
        panelFondoCampo.add(panelInfoJugador);

        // --- Overlay embebido (para reemplazar los JOptionPane) ---
        overlay = crearOverlay();

        campoLayered = new JLayeredPane();
        campoLayered.setLayout(null);
        campoLayered.add(panelFondoCampo, JLayeredPane.DEFAULT_LAYER);
        campoLayered.add(overlay, JLayeredPane.PALETTE_LAYER);
        overlay.setVisible(false);

        campoLayered.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int w = campoLayered.getWidth();
                int h = campoLayered.getHeight();
                panelFondoCampo.setBounds(0, 0, w, h);
                overlay.setBounds(0, 0, w, h);
                recolocarComponentesResponsivo();
            }
        });

        contenedor.add(campoLayered, BorderLayout.CENTER);

        JPanel panelInferior = new JPanel(new BorderLayout());
        panelInferior.setBackground(new Color(12, 15, 24));

        txtHistorialBatalla = new JTextArea(8, 50);
        txtHistorialBatalla.setEditable(false);
        txtHistorialBatalla.setLineWrap(true);
        txtHistorialBatalla.setWrapStyleWord(true);
        txtHistorialBatalla.setFont(new Font("Monospaced", Font.BOLD, 15));
        txtHistorialBatalla.setBackground(new Color(20, 24, 36));
        txtHistorialBatalla.setForeground(new Color(240, 240, 240));
        txtHistorialBatalla.setBorder(new EmptyBorder(10, 16, 10, 16));
        JScrollPane scrollLog = new JScrollPane(txtHistorialBatalla);
        scrollLog.setBorder(BorderFactory.createLineBorder(UIUtils.AZUL_MEDIO, 1));
        scrollLog.setPreferredSize(new Dimension(100, 190));
        panelInferior.add(scrollLog, BorderLayout.CENTER);

        panelInferior.add(crearPanelMenu(), BorderLayout.SOUTH);
        contenedor.add(panelInferior, BorderLayout.SOUTH);

        return contenedor;
    }

    private void recolocarComponentesResponsivo() {
        int w = panelFondoCampo.getWidth();
        int h = panelFondoCampo.getHeight();

        if (w <= 0 || h <= 0) return;

        btnHuir.setBounds(25, 20, 110, 36);

        int infoW = 340;
        int infoH = 95;
        int infoRivalX = (int) (w * 0.04);
        int infoRivalY = (int) (h * 0.10);
        panelInfoRival.setBounds(infoRivalX, infoRivalY, infoW, infoH);

        int spriteRivalSize = Math.max(220, (int) (h * 0.42));
        int spriteRivalX = (int) (w * 0.66) - (spriteRivalSize / 2);
        int spriteRivalY = (int) (h * 0.28) - (spriteRivalSize / 2);
        lblSpriteRival.setBounds(spriteRivalX, spriteRivalY, spriteRivalSize, spriteRivalSize);

        int spriteJugadorSize = Math.max(260, (int) (h * 0.52));
        int spriteJugadorX = (int) (w * 0.22) - (spriteJugadorSize / 2);
        int spriteJugadorY = h - spriteJugadorSize - (int) (h * 0.05);
        lblSpriteJugador.setBounds(spriteJugadorX, spriteJugadorY, spriteJugadorSize, spriteJugadorSize);

        int infoJugadorX = (int) (w * 0.62);
        int infoJugadorY = h - infoH - (int) (h * 0.14);
        panelInfoJugador.setBounds(infoJugadorX, infoJugadorY, infoW, infoH);

        panelFondoCampo.revalidate();
        panelFondoCampo.repaint();
    }

    private JPanel crearPanelInfo() {
        TarjetaRedondeada panel = new TarjetaRedondeada(UIUtils.AZUL_MEDIO, UIUtils.AMARILLO_OSCURO, 14);
        panel.setLayout(new GridLayout(4, 1, 2, 2));
        panel.setBorder(new EmptyBorder(8, 12, 8, 12));
        return panel;
    }

    private void configurarPanelInfo(JPanel panel, JLabel lblNombre, JLabel lblTipo, JLabel lblHpTexto, JProgressBar barra) {
        lblNombre.setForeground(Color.WHITE);
        lblNombre.setFont(new Font("SansSerif", Font.BOLD, 16));

        lblTipo.setForeground(UIUtils.AMARILLO);
        lblTipo.setFont(new Font("SansSerif", Font.ITALIC, 12));

        barra.setStringPainted(false);
        barra.setForeground(new Color(76, 217, 100));
        barra.setBackground(new Color(40, 44, 56));
        barra.setPreferredSize(new Dimension(100, 14));
        barra.setBorderPainted(false);

        lblHpTexto.setForeground(new Color(210, 215, 225));
        lblHpTexto.setFont(new Font("SansSerif", Font.BOLD, 13));

        panel.add(lblNombre);
        panel.add(lblTipo);
        panel.add(barra);
        panel.add(lblHpTexto);
    }

    private JPanel crearPanelMenu() {
        JPanel panelMenu = new JPanel(new GridLayout(1, 5, 12, 10));
        panelMenu.setBackground(new Color(12, 15, 24));
        panelMenu.setBorder(new EmptyBorder(12, 20, 16, 20));

        btnAtacar = UIUtils.crearBotonEstilizado("ATACAR");
        btnCambiar = UIUtils.crearBotonEstilizado("CAMBIAR");
        btnObjetos = UIUtils.crearBotonEstilizado("OBJETOS");
        btnEquipo = UIUtils.crearBotonEstilizado("MI EQUIPO");
        btnHistorial = UIUtils.crearBotonEstilizado("HISTORIAL");

        btnAtacar.addActionListener(e -> ejecutarAtaque());
        btnCambiar.addActionListener(e -> mostrarOverlayCambio());
        btnObjetos.addActionListener(e -> mostrarOverlayObjetos());
        btnEquipo.addActionListener(e -> mostrarOverlayEquipo());
        btnHistorial.addActionListener(e -> mostrarOverlayHistorialCompleto());

        panelMenu.add(btnAtacar);
        panelMenu.add(btnCambiar);
        panelMenu.add(btnObjetos);
        panelMenu.add(btnEquipo);
        panelMenu.add(btnHistorial);

        return panelMenu;
    }

    private void setBotonesHabilitados(boolean habilitado) {
        btnAtacar.setEnabled(habilitado);
        btnCambiar.setEnabled(habilitado);
        btnObjetos.setEnabled(habilitado);
        btnHuir.setEnabled(habilitado);
        // "Mi equipo" e "Historial" se pueden consultar siempre
    }

    // ============================================================
    //  ACCIONES DE COMBATE (con animación de turnos)
    // ============================================================

    private void ejecutarAtaque() {
        if (combateActual == null || combateActual.isFinalizado()) return;

        String textoAntes = historial.obtenerTextoCompleto();
        combateActual.atacar();
        animarNuevosMensajes(textoAntes, () -> {
            popularBatalla();
            verificarFinCombate();
        });
    }

    private void animarNuevosMensajes(String textoAntes, Runnable alTerminar) {
        String textoCompleto = historial.obtenerTextoCompleto();
        String nuevo = textoCompleto.length() > textoAntes.length()
                ? textoCompleto.substring(textoAntes.length())
                : "";

        colaMensajes.clear();
        for (String linea : nuevo.split("\n")) {
            String limpia = linea.replace("--------------------------------", "").trim();
            if (!limpia.isEmpty()) {
                colaMensajes.add(limpia);
            }
        }

        this.alTerminarAnimacion = alTerminar;

        if (colaMensajes.isEmpty()) {
            if (alTerminar != null) alTerminar.run();
            return;
        }

        setBotonesHabilitados(false);
        if (timerMensajes != null && timerMensajes.isRunning()) {
            timerMensajes.stop();
        }

        timerMensajes = new Timer(900, null);
        timerMensajes.addActionListener(e -> {
            if (colaMensajes.isEmpty()) {
                timerMensajes.stop();
                setBotonesHabilitados(true);
                if (alTerminarAnimacion != null) alTerminarAnimacion.run();
                return;
            }
            String siguiente = colaMensajes.remove(0);
            txtHistorialBatalla.append(siguiente + "\n");
            txtHistorialBatalla.setCaretPosition(txtHistorialBatalla.getDocument().getLength());
        });
        timerMensajes.setInitialDelay(0);
        timerMensajes.start();
    }

    // ============================================================
    //  OVERLAY EMBEBIDO (reemplaza JOptionPane)
    // ============================================================

    private JPanel crearOverlay() {
        JPanel fondoOscuro = new JPanel(new GridBagLayout());
        fondoOscuro.setOpaque(true);
        fondoOscuro.setBackground(new Color(0, 0, 0, 160));

        TarjetaRedondeada tarjeta = new TarjetaRedondeada(UIUtils.AZUL_MEDIO, UIUtils.AMARILLO_OSCURO, 18);
        tarjeta.setLayout(new BorderLayout(0, 14));
        tarjeta.setBorder(new EmptyBorder(20, 24, 20, 24));
        tarjeta.setPreferredSize(new Dimension(560, 380));

        JPanel encabezado = new JPanel(new BorderLayout());
        encabezado.setOpaque(false);
        lblOverlayTitulo = new JLabel("Título", SwingConstants.CENTER);
        lblOverlayTitulo.setForeground(UIUtils.AMARILLO);
        lblOverlayTitulo.setFont(new Font("SansSerif", Font.BOLD, 22));
        encabezado.add(lblOverlayTitulo, BorderLayout.CENTER);

        JButton btnCerrar = new JButton("✕");
        btnCerrar.setFocusPainted(false);
        btnCerrar.setBorderPainted(false);
        btnCerrar.setContentAreaFilled(false);
        btnCerrar.setForeground(UIUtils.TEXTO_CLARO);
        btnCerrar.setFont(new Font("SansSerif", Font.BOLD, 16));
        btnCerrar.addActionListener(e -> ocultarOverlay());
        encabezado.add(btnCerrar, BorderLayout.EAST);

        tarjeta.add(encabezado, BorderLayout.NORTH);

        panelOverlayContenido = new JPanel();
        panelOverlayContenido.setOpaque(false);
        JScrollPane scroll = new JScrollPane(panelOverlayContenido);
        scroll.setBorder(null);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        tarjeta.add(scroll, BorderLayout.CENTER);

        fondoOscuro.add(tarjeta);
        return fondoOscuro;
    }

    private void mostrarOverlay(String titulo) {
        lblOverlayTitulo.setText(titulo);
        panelOverlayContenido.removeAll();
        panelOverlayContenido.setLayout(new GridLayout(0, 1, 0, 10));
        overlay.setVisible(true);
        overlay.revalidate();
        overlay.repaint();
    }

    private void ocultarOverlay() {
        overlay.setVisible(false);
    }

    /** Muestra un mensaje simple con un botón de "Aceptar". */
    private void mostrarOverlayMensaje(String titulo, String mensaje) {
        mostrarOverlayMensaje(titulo, mensaje, null);
    }

    private void mostrarOverlayMensaje(String titulo, String mensaje, Runnable alAceptar) {
        mostrarOverlay(titulo);
        panelOverlayContenido.setLayout(new BorderLayout(0, 16));

        JTextArea txt = new JTextArea(mensaje);
        txt.setEditable(false);
        txt.setLineWrap(true);
        txt.setWrapStyleWord(true);
        txt.setOpaque(false);
        txt.setForeground(UIUtils.TEXTO_CLARO);
        txt.setFont(new Font("SansSerif", Font.PLAIN, 16));
        panelOverlayContenido.add(txt, BorderLayout.CENTER);

        JButton btnOk = UIUtils.crearBotonEstilizado("Aceptar");
        btnOk.addActionListener(e -> {
            ocultarOverlay();
            if (alAceptar != null) alAceptar.run();
        });
        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBoton.setOpaque(false);
        panelBoton.add(btnOk);
        panelOverlayContenido.add(panelBoton, BorderLayout.SOUTH);

        panelOverlayContenido.revalidate();
        panelOverlayContenido.repaint();
    }

    /** Muestra un mensaje de confirmación con Sí / No. */
    private void mostrarOverlayConfirmacion(String titulo, String mensaje, Runnable siAccion) {
        mostrarOverlay(titulo);
        panelOverlayContenido.setLayout(new BorderLayout(0, 16));

        JLabel lbl = new JLabel("<html><div style='text-align:center;'>" + mensaje + "</div></html>", SwingConstants.CENTER);
        lbl.setForeground(UIUtils.TEXTO_CLARO);
        lbl.setFont(new Font("SansSerif", Font.PLAIN, 16));
        panelOverlayContenido.add(lbl, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 16, 0));
        panelBotones.setOpaque(false);
        JButton btnSi = UIUtils.crearBotonExito("Sí");
        JButton btnNo = UIUtils.crearBotonSecundario("No");
        btnSi.addActionListener(e -> siAccion.run());
        btnNo.addActionListener(e -> ocultarOverlay());
        panelBotones.add(btnSi);
        panelBotones.add(btnNo);
        panelOverlayContenido.add(panelBotones, BorderLayout.SOUTH);

        panelOverlayContenido.revalidate();
        panelOverlayContenido.repaint();
    }

    /** Muestra una lista de opciones, cada una como un botón. */
    private void mostrarOverlayOpciones(String titulo, String[] opciones, Consumer<Integer> alSeleccionar) {
        mostrarOverlay(titulo);
        panelOverlayContenido.setLayout(new GridLayout(0, 1, 0, 10));

        for (int i = 0; i < opciones.length; i++) {
            final int idx = i;
            JButton btn = UIUtils.crearBotonEstilizado(opciones[i]);
            btn.addActionListener(e -> {
                ocultarOverlay();
                alSeleccionar.accept(idx);
            });
            panelOverlayContenido.add(btn);
        }

        panelOverlayContenido.revalidate();
        panelOverlayContenido.repaint();
    }

    // ============================================================
    //  DIÁLOGOS CONVERTIDOS A OVERLAY
    // ============================================================

    private void mostrarOverlayCambio() {
        if (combateActual == null || combateActual.isFinalizado()) return;

        Pokemon[] equipo = combateActual.getJugador().getEquipo().toArray();
        Pokemon activo = combateActual.getJugador().getEquipo().getPokemonActivo();

        String[] opciones = new String[equipo.length];
        for (int i = 0; i < equipo.length; i++) {
            Pokemon p = equipo[i];
            opciones[i] = p.getNombre() + " (HP: " + p.getHpActual() + "/" + p.getHpMax() + ")"
                    + (p == activo ? " [ACTIVO]" : (p.estaDerrotado() ? " [DERROTADO]" : ""));
        }

        mostrarOverlayOpciones("Cambiar Pokémon", opciones, seleccion -> {
            Pokemon elegido = equipo[seleccion];
            if (elegido == activo) {
                mostrarOverlayMensaje("Aviso", "Ese Pokémon ya está combatiendo.");
                return;
            }
            if (elegido.estaDerrotado()) {
                mostrarOverlayMensaje("Inválido", "No puedes seleccionar un Pokémon derrotado.");
                return;
            }

            String textoAntes = historial.obtenerTextoCompleto();
            boolean cambio = combateActual.cambiarPokemon(elegido.getNombre());
            if (cambio) {
                animarNuevosMensajes(textoAntes, () -> {
                    popularBatalla();
                    verificarFinCombate();
                });
            }
        });
    }

    private void mostrarOverlayObjetos() {
        if (combateActual == null || combateActual.isFinalizado()) return;

        Objeto[] inventario = combateActual.getJugador().getInventario().toArray();
        if (inventario.length == 0) {
            mostrarOverlayMensaje("Mochila de Objetos", "No tienes objetos disponibles.");
            return;
        }

        String[] opciones = new String[inventario.length];
        for (int i = 0; i < inventario.length; i++) {
            Objeto obj = inventario[i];
            opciones[i] = obj.getNombre() + " (x" + obj.getCantidad() + ") - " + obj.getDescripcion();
        }

        mostrarOverlayOpciones("Mochila de Objetos", opciones, seleccion -> {
            Objeto obj = inventario[seleccion];
            if (obj.getCantidad() <= 0) {
                mostrarOverlayMensaje("Agotado", "No quedan unidades de " + obj.getNombre() + ".");
                return;
            }

            if (obj.esRevivir()) {
                Pokemon[] equipo = combateActual.getJugador().getEquipo().toArray();
                String[] pOpciones = new String[equipo.length];
                for (int i = 0; i < equipo.length; i++) {
                    pOpciones[i] = equipo[i].getNombre() + (equipo[i].estaDerrotado() ? " [DERROTADO]" : " [VIVO]");
                }

                mostrarOverlayOpciones("Selecciona a quién revivir", pOpciones, selPkmn -> {
                    Pokemon objetivo = equipo[selPkmn];
                    if (!objetivo.estaDerrotado()) {
                        mostrarOverlayMensaje("Aviso", objetivo.getNombre() + " ya tiene salud.");
                        return;
                    }
                    String textoAntesRevivir = historial.obtenerTextoCompleto();
                    obj.usar(objetivo);
                    historial.agregar(combateActual.getJugador().getUsername() + " utilizó " + obj.getNombre() + " en " + objetivo.getNombre() + ".");
                    combateActual.contraataqueRival();
                    animarNuevosMensajes(textoAntesRevivir, () -> {
                        popularBatalla();
                        verificarFinCombate();
                    });
                });
            } else {
                String textoAntes = historial.obtenerTextoCompleto();
                boolean exito = combateActual.usarObjeto(obj.getNombre());
                if (!exito) {
                    mostrarOverlayMensaje("Sin efecto", "El Pokémon activo ya tiene la salud al máximo.");
                } else {
                    animarNuevosMensajes(textoAntes, () -> {
                        popularBatalla();
                        verificarFinCombate();
                    });
                }
            }
        });
    }

    private void mostrarOverlayEquipo() {
        if (combateActual == null) return;

        Pokemon[] equipo = combateActual.getJugador().getEquipo().toArray();
        StringBuilder sb = new StringBuilder();

        for (Pokemon p : equipo) {
            sb.append(p.getNombre()).append("  -  Nv. ").append(p.getNivel()).append("\n")
              .append("HP: ").append(p.getHpActual()).append("/").append(p.getHpMax())
              .append(p.estaDerrotado() ? " [DERROTADO]" : (p == combateActual.getJugador().getEquipo().getPokemonActivo() ? " [ACTIVO]" : " [DISPONIBLE]"))
              .append("\nTipos: ").append(p.getTiposString())
              .append("\n------------------------------------\n");
        }
        sb.append("Pokémon disponibles: ").append(combateActual.getJugador().getEquipo().contarDisponibles());

        mostrarOverlayMensaje("Mi Equipo", sb.toString());
    }

    private void mostrarOverlayHistorialCompleto() {
        if (historial == null) return;
        mostrarOverlayMensaje("Historial Completo de Batalla", historial.obtenerTextoCompleto());
    }

    private void verificarFinCombate() {
        if (combateActual.isFinalizado()) {
            boolean ganoJugador = combateActual.getGanador() == combateActual.getJugador();
            String titulo = ganoJugador ? "¡VICTORIA!" : "¡DERROTA!";
            String mensaje = ganoJugador
                    ? "¡Has vencido a " + combateActual.getRival().getUsername() + "!"
                    : "Todos tus Pokémon han caído ante " + combateActual.getRival().getUsername() + ".";

            mostrarOverlayMensaje(titulo, mensaje, () -> mainApp.cambiarPantalla("MenuPrincipal"));
            return;
        }

        Pokemon activo = combateActual.getJugador().getEquipo().getPokemonActivo();
        if (activo != null && activo.estaDerrotado() && combateActual.getJugador().getEquipo().tieneVivos()) {
            mostrarOverlayMensaje("Cambio Forzoso", "¡Tu Pokémon activo cayó! Elige a otro compañero.", this::mostrarOverlayCambio);
        }
    }

    // ============================================================
    //  CICLO DE VIDA DEL COMBATE
    // ============================================================

    public void iniciarNuevoCombate() {
        Usuario jugador = mainApp.getUsuarioLogueado();
        if (jugador == null) return;

        if (jugador.getEquipo().estaVacia()) {
            mostrarOverlayMensaje("Equipo Vacío", "Debes tener al menos 1 Pokémon en tu equipo para combatir.",
                    () -> mainApp.cambiarPantalla("ArmarEquipo"));
            return;
        }

        jugador.getEquipo().reiniciarVidaEquipo();
        rivalActual = mainApp.getBaseDatosUsuarios().obtenerRivalAleatorio();
        historial = new ListaHistorial();
        combateActual = new Combate(jugador, rivalActual, historial);

        txtHistorialBatalla.setText("");
        // Limpia los sprites para evitar que se vea el pokémon del combate anterior
        lblSpriteJugador.setIcon(null);
        lblSpriteRival.setIcon(null);

        cardInterno.show(contenedorInterno, "Buscando");
        animarBusquedaRival();
    }

    private void popularBatalla() {
        Pokemon pJugador = combateActual.getJugador().getEquipo().getPokemonActivo();
        Pokemon pRival = combateActual.getRival().getEquipo().getPokemonActivo();

        int spriteSize = Math.max(240, (int) (panelFondoCampo.getHeight() * 0.45));

        if (pJugador != null) {
            actualizarSprite(lblSpriteJugador, pJugador.getRutaImagen().replace(".png", "Back.png"), spriteSize + 30, spriteSize + 30);
            lblNombreJugador.setText(pJugador.getNombre() + " (Nv. " + pJugador.getNivel() + ")");
            lblTipoJugador.setText("Tipo: " + pJugador.getTiposString());
            actualizarBarra(barraHpJugador, lblHpTextoJugador, pJugador);
        }

        if (pRival != null) {
            actualizarSprite(lblSpriteRival, pRival.getRutaImagen(), spriteSize, spriteSize);
            lblNombreRival.setText(pRival.getNombre() + " (Nv. " + pRival.getNivel() + ")");
            lblTipoRival.setText("Tipo: " + pRival.getTiposString());
            actualizarBarra(barraHpRival, lblHpTextoRival, pRival);
        }

        txtHistorialBatalla.setCaretPosition(txtHistorialBatalla.getDocument().getLength());
    }

    /**
     * Reemplaza el ícono del sprite de forma segura: primero lo limpia y
     * fuerza un repintado antes de asignar el nuevo, evitando que quede
     * "pegada" la imagen anterior al cambiar de Pokémon.
     */
    private void actualizarSprite(JLabel label, String nombreArchivo, int ancho, int alto) {
        label.setIcon(null);
        ImageIcon icono = cargarSprite(nombreArchivo, ancho, alto);
        label.setIcon(icono);
        label.revalidate();
        label.repaint();
    }

    private void actualizarBarra(JProgressBar barra, JLabel lblTexto, Pokemon p) {
        barra.setMaximum(p.getHpMax());
        barra.setValue(p.getHpActual());

        double pct = (double) p.getHpActual() / p.getHpMax();
        if (pct > 0.5) barra.setForeground(new Color(76, 217, 100));
        else if (pct > 0.2) barra.setForeground(new Color(255, 204, 0));
        else barra.setForeground(new Color(255, 59, 48));

        lblTexto.setText("HP: " + p.getHpActual() + " / " + p.getHpMax());
    }

    private ImageIcon cargarSprite(String nombreArchivo, int ancho, int alto) {
        URL url = getClass().getResource("/Sprites/" + nombreArchivo);
        if (url != null) {
            ImageIcon original = new ImageIcon(url);
            // Espera a que la imagen termine de cargar antes de escalarla,
            // para evitar sprites a medio dibujar cuando se cambia rápido.
            new ImageIcon(original.getImage()).getImage();
            Image escalada = original.getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
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