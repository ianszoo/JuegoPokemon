package Pokemon;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class PokemonShenanigans extends JFrame {

    private CardLayout cardLayout;
    private JPanel mainContainer;
    private ListaEnlazadaUsuarios baseDatosUsuarios;
    private Usuario usuarioLogueado;
    private PantallaCombate pantallaCombate;

    public PokemonShenanigans() {
        setTitle("Pokémon Shenanigans - Battle GUI");
        setSize(1000, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        URL iconoURL = getClass().getResource("/imagenes/Logo.png");
        if (iconoURL != null) {
            setIconImage(new ImageIcon(iconoURL).getImage());
        }

        baseDatosUsuarios = new ListaEnlazadaUsuarios();
        cardLayout = new CardLayout();
        mainContainer = new JPanel(cardLayout);

        inicializarPantallas();
        add(mainContainer);
    }

    private void inicializarPantallas() {
        mainContainer.add(new PantallaInicio(this), "Inicio");
        mainContainer.add(new PantallaRegistro(this), "Registro");
        mainContainer.add(new PantallaLogin(this), "Login");
        mainContainer.add(new MenuPrincipal(this), "MenuPrincipal");
        mainContainer.add(new PantallaArmarEquipo(this), "ArmarEquipo");

        pantallaCombate = new PantallaCombate(this);
        mainContainer.add(pantallaCombate, "Combate");
    }

    public void cambiarPantalla(String nombrePantalla) {
        cardLayout.show(mainContainer, nombrePantalla);
    }

    public ListaEnlazadaUsuarios getBaseDatosUsuarios() {
        return baseDatosUsuarios;
    }

    public void loginExitoso(Usuario u) {
        this.usuarioLogueado = u;
        cambiarPantalla("MenuPrincipal");
    }

    public Usuario getUsuarioLogueado() {
        return usuarioLogueado;
    }

    public void iniciarCombate() {
    cambiarPantalla("Combate");
    pantallaCombate.iniciarNuevoCombate();
}

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new PokemonShenanigans().setVisible(true);
        });
    }
}
