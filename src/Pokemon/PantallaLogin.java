package Pokemon;

import javax.swing.*;
import java.awt.*;

public class PantallaLogin extends JPanel {
    public PantallaLogin(PokemonShenanigans mainApp) {
        setLayout(new GridBagLayout());
        setBackground(UIUtils.BG_OSCURO);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;

        JLabel titulo = new JLabel("INICIAR SESIÓN");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 40));
        titulo.setForeground(UIUtils.PKMN_AMARILLO);

        JTextField txtUsuario = new JTextField(15);
        txtUsuario.setFont(new Font("SansSerif", Font.PLAIN, 24));
        txtUsuario.setHorizontalAlignment(JTextField.CENTER);
        
        JPasswordField txtPass = new JPasswordField(15);
        txtPass.setFont(new Font("SansSerif", Font.PLAIN, 24));
        txtPass.setHorizontalAlignment(JPasswordField.CENTER);

        JLabel lblError = new JLabel(" ");
        lblError.setForeground(UIUtils.ERROR_COLOR);
        lblError.setFont(new Font("SansSerif", Font.BOLD, 14));

        JButton btnLogin = UIUtils.crearBotonEstilizado("Entrar");
        JButton btnVolver = UIUtils.crearBotonEstilizado("Volver");

        btnVolver.addActionListener(e -> {
            lblError.setText(" ");
            mainApp.cambiarPantalla("Inicio");
        });

        btnLogin.addActionListener(e -> {
            try {
                String user = txtUsuario.getText();
                String pass = new String(txtPass.getPassword());
                
                if (user.isEmpty() || pass.isEmpty()) {
                    throw new CampoVacioException("Error: Campos vacíos.");
                }

                Usuario encontrado = mainApp.getBaseDatosUsuarios().buscarUsuario(user);
                if (encontrado == null || !encontrado.getPassword().equals(pass)) {
                    throw new MenuException("Error: Usuario o contraseña incorrectos.");
                }

                lblError.setText(" ");
                txtUsuario.setText("");
                txtPass.setText("");
                mainApp.loginExitoso(encontrado);

            } catch (MenuException ex) {
                lblError.setText(ex.getMessage());
            }
        });

        gbc.gridy = 0; add(titulo, gbc);
        gbc.gridy = 1; add(new JLabel("Usuario:") {{ setForeground(UIUtils.TEXTO_CLARO); }}, gbc);
        gbc.gridy = 2; add(txtUsuario, gbc);
        gbc.gridy = 3; add(new JLabel("Contraseña:") {{ setForeground(UIUtils.TEXTO_CLARO); }}, gbc);
        gbc.gridy = 4; add(txtPass, gbc);
        gbc.gridy = 5; add(lblError, gbc);
        gbc.gridy = 6; add(btnLogin, gbc);
        gbc.gridy = 7; add(btnVolver, gbc);
    }
}