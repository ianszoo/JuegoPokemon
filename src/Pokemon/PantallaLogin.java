package Pokemon;

import javax.swing.*;
import java.awt.*;


public class PantallaLogin extends JPanel {
    public PantallaLogin(PokemonShenanigans mainApp) {
        setLayout(new BorderLayout());

        FondoDegradado fondo = new FondoDegradado(UIUtils.AZUL_OSCURO, new Color(12, 15, 24));
        fondo.setLayout(new GridBagLayout());
        add(fondo, BorderLayout.CENTER);

        TarjetaRedondeada tarjeta = new TarjetaRedondeada(UIUtils.AZUL_MEDIO, UIUtils.AMARILLO, 28);
        tarjeta.setLayout(new GridBagLayout());
        tarjeta.setPreferredSize(new Dimension(480, 520));
        fondo.add(tarjeta, new GridBagConstraints());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(6, 40, 6, 40);

        JLabel titulo = UIUtils.crearTitulo("INICIAR SESIÓN", 30);
        JLabel subtitulo = UIUtils.crearSubtitulo("¡Bienvenido de nuevo, entrenador!");

        JLabel lblUsuario = UIUtils.crearEtiqueta("USUARIO");
        JTextField txtUsuario = UIUtils.crearCampoTexto(15);

        JLabel lblPass = UIUtils.crearEtiqueta("CONTRASEÑA");
        CampoPassword campoPass = new CampoPassword(15);

        JLabel lblError = UIUtils.crearEtiquetaError();

        JButton btnLogin = UIUtils.crearBotonExito("Entrar");
        JButton btnVolver = UIUtils.crearBotonSecundario("Volver");

        btnVolver.addActionListener(e -> {
            lblError.setText(" ");
            txtUsuario.setText("");
            campoPass.limpiar();
            mainApp.cambiarPantalla("Inicio");
        });

        btnLogin.addActionListener(e -> {
            try {
                String user = txtUsuario.getText();
                String pass = campoPass.getPassword();

                if (user.isEmpty() || pass.isEmpty()) {
                    throw new CampoVacioException("Error: Campos vacíos.");
                }

                Usuario encontrado = mainApp.getBaseDatosUsuarios().buscarUsuario(user);
                if (encontrado == null || !encontrado.getPassword().equals(pass)) {
                    throw new MenuException("Error: Usuario o contraseña incorrectos.");
                }

                lblError.setText(" ");
                txtUsuario.setText("");
                campoPass.limpiar();
                mainApp.loginExitoso(encontrado);

            } catch (MenuException ex) {
                lblError.setText(ex.getMessage());
            }
        });

        JPanel panelBotones = new JPanel(new GridLayout(2, 1, 0, 12));
        panelBotones.setOpaque(false);
        panelBotones.add(btnLogin);
        panelBotones.add(btnVolver);

        int fila = 0;
        gbc.insets = new Insets(22, 40, 2, 40);
        gbc.gridy = fila++; tarjeta.add(titulo, gbc);
        gbc.insets = new Insets(2, 40, 22, 40);
        gbc.gridy = fila++; tarjeta.add(subtitulo, gbc);

        gbc.insets = new Insets(6, 40, 2, 40);
        gbc.gridy = fila++; tarjeta.add(lblUsuario, gbc);
        gbc.insets = new Insets(0, 40, 16, 40);
        gbc.gridy = fila++; tarjeta.add(txtUsuario, gbc);

        gbc.insets = new Insets(6, 40, 2, 40);
        gbc.gridy = fila++; tarjeta.add(lblPass, gbc);
        gbc.insets = new Insets(0, 40, 16, 40);
        gbc.gridy = fila++; tarjeta.add(campoPass, gbc);

        gbc.insets = new Insets(0, 40, 10, 40);
        gbc.gridy = fila++; tarjeta.add(lblError, gbc);

        gbc.insets = new Insets(10, 40, 25, 40);
        gbc.gridy = fila++; tarjeta.add(panelBotones, gbc);
    }
}