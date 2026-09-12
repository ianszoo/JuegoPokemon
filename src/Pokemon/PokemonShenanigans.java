package Pokemon;

import javax.swing.*;
import java.awt.*;

public class PokemonShenanigans extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainContainer;
    private ListaUsuarios baseDatosUsuarios;
    private Usuario usuarioLogueado;

    public PokemonShenanigans() {
        setTitle("Pokémon Shenanigans - Battle GUI");
        setSize(1000, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        baseDatosUsuarios = new ListaUsuarios();
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
    }

    public void cambiarPantalla(String nombrePantalla) {
        cardLayout.show(mainContainer, nombrePantalla);
    }

    public ListaUsuarios getBaseDatosUsuarios() {
        return baseDatosUsuarios;
    }

    public void loginExitoso(Usuario u) {
        this.usuarioLogueado = u;
        cambiarPantalla("MenuPrincipal");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new PokemonShenanigans().setVisible(true);
        });
    }
}