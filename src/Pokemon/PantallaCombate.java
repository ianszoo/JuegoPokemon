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

 
    private JLabel lblBuscando;
    private Timer timerBuscando;


    private JLabel lblSpriteJugador;
    private JLabel lblSpriteRival;
    private JLabel lblNombreJugador;
    private JLabel lblNombreRival;
    private JProgressBar barraHpJugador;
    private JProgressBar barraHpRival;
    private JLabel lblHpTextoJugador;
    private JLabel lblHpTextoRival;

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
        lblBuscando.setFont(new Font("SansSerif", Font.BOLD, 30));

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

        timerBuscando = new Timer(120, e -> {
            tick[0]++;
            lblBuscando.setText("Buscando rival: " + nombres[rnd.nextInt(nombres.length)] + " ...");

            if (tick[0] >= 12) {
                timerBuscando.stop();
                lblBuscando.setText("¡Rival encontrado: " + rivalActual.getUsername() + "!");

                Timer pausa = new Timer(900, ev -> {
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
        btnHuir.setBounds(15, 12, 100, 32);
        btnHuir.addActionListener(e -> mainApp.cambiarPantalla("MenuPrincipal"));
        fondo.add(btnHuir);


        lblSpriteRival = new JLabel();
        lblSpriteRival.setBounds(680, 30, 220, 220);
        fondo.add(lblSpriteRival);

        JPanel panelInfoRival = crearPanelInfo();
        lblNombreRival = new JLabel();
        lblHpTextoRival = new JLabel();
        barraHpRival = new JProgressBar();
        configurarPanelInfo(panelInfoRival, lblNombreRival, lblHpTextoRival, barraHpRival);
        panelInfoRival.setBounds(50, 55, 290, 82);
        fondo.add(panelInfoRival);

        lblSpriteJugador = new JLabel();
        lblSpriteJugador.setBounds(60, 300, 260, 260);
        fondo.add(lblSpriteJugador);

        JPanel panelInfoJugador = crearPanelInfo();
        lblNombreJugador = new JLabel();
        lblHpTextoJugador = new JLabel();
        barraHpJugador = new JProgressBar();
        configurarPanelInfo(panelInfoJugador, lblNombreJugador, lblHpTextoJugador, barraHpJugador);
        panelInfoJugador.setBounds(650, 400, 290, 82);
        fondo.add(panelInfoJugador);

        contenedor.add(fondo, BorderLayout.CENTER);
        contenedor.add(crearPanelMenu(), BorderLayout.SOUTH);
        return contenedor;
    }

    private JPanel crearPanelInfo() {
        TarjetaRedondeada panel = new TarjetaRedondeada(UIUtils.AZUL_MEDIO, UIUtils.AMARILLO_OSCURO, 14);
        panel.setLayout(new BorderLayout(4, 2));
        panel.setBorder(new EmptyBorder(8, 14, 10, 14));
        return panel;
    }

    private void configurarPanelInfo(JPanel panel, JLabel lblNombre, JLabel lblHpTexto, JProgressBar barra) {
        lblNombre.setForeground(Color.WHITE);
        lblNombre.setFont(new Font("SansSerif", Font.BOLD, 16));

        barra.setStringPainted(false);
        barra.setForeground(new Color(76, 217, 100));
        barra.setBackground(new Color(40, 44, 56));
        barra.setPreferredSize(new Dimension(100, 14));
        barra.setBorderPainted(false);

        lblHpTexto.setForeground(new Color(210, 215, 225));
        lblHpTexto.setFont(new Font("SansSerif", Font.PLAIN, 12));

        panel.add(lblNombre, BorderLayout.NORTH);
        panel.add(barra, BorderLayout.CENTER);
        panel.add(lblHpTexto, BorderLayout.SOUTH);
    }


    private JPanel crearPanelMenu() {
        JPanel panelMenu = new JPanel(new GridLayout(1, 5, 10, 10));
        panelMenu.setBackground(new Color(12, 15, 24));
        panelMenu.setBorder(new EmptyBorder(12, 20, 16, 20));

        JButton btnAtacar = UIUtils.crearBotonEstilizado("Atacar");
        JButton btnCambiar = UIUtils.crearBotonEstilizado("Cambiar Pokémon");
        JButton btnObjetos = UIUtils.crearBotonEstilizado("Objetos");
        JButton btnEquipo = UIUtils.crearBotonEstilizado("Mi Equipo");
        JButton btnHistorial = UIUtils.crearBotonEstilizado("Historial");

        // Siguiente paso: conectar cada botón con la lógica de Combate.java
        panelMenu.add(btnAtacar);
        panelMenu.add(btnCambiar);
        panelMenu.add(btnObjetos);
        panelMenu.add(btnEquipo);
        panelMenu.add(btnHistorial);

        return panelMenu;
    }


    public void iniciarNuevoCombate() {
        Usuario jugador = mainApp.getUsuarioLogueado();
        if (jugador == null) return;

        if (jugador.getEquipo().estaVacia()) {
            JOptionPane.showMessageDialog(this,
                    "Primero arma tu equipo en 'Armar Equipo'.",
                    "Equipo vacío", JOptionPane.WARNING_MESSAGE);
            mainApp.cambiarPantalla("MenuPrincipal");
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

        lblSpriteJugador.setIcon(cargarSprite(rutaBack(pJugador.getRutaImagen()), 220, 220));
        lblSpriteRival.setIcon(cargarSprite(pRival.getRutaImagen(), 190, 190));

        lblNombreJugador.setText(pJugador.getNombre() + "   Nv. " + pJugador.getNivel());
        lblNombreRival.setText(pRival.getNombre() + "   Nv. " + pRival.getNivel());

        actualizarBarra(barraHpJugador, lblHpTextoJugador, pJugador);
        actualizarBarra(barraHpRival, lblHpTextoRival, pRival);
    }

    private void actualizarBarra(JProgressBar barra, JLabel lblTexto, Pokemon p) {
        barra.setMaximum(p.getHpMax());
        barra.setValue(p.getHpActual());
        lblTexto.setText(p.getHpActual() + " / " + p.getHpMax() + " HP");
    }

    private String rutaBack(String rutaFrontal) {
        return rutaFrontal.replace(".png", "Back.png");
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
        g2.setStroke(new BasicStroke(3f));
        g2.drawOval(4, 4, ancho - 8, alto - 8);
        g2.dispose();
        return new ImageIcon(placeholder);
    }
}