package Pokemon;

import javax.swing.*;
import java.awt.*;

public class PantallaRegistro extends JPanel {
    public PantallaRegistro(PokemonShenanigans mainApp) {
        setLayout(new GridBagLayout());
        setBackground(UIUtils.BG_OSCURO);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;

        JLabel titulo = new JLabel("CREAR USUARIO");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 40));
        titulo.setForeground(UIUtils.PKMN_AMARILLO);

        JTextField txtUsuario = new JTextField(15);
        txtUsuario.setFont(new Font("SansSerif", Font.PLAIN, 24));
        txtUsuario.setHorizontalAlignment(JTextField.CENTER);
        
        JPasswordField txtPass = new JPasswordField(15);
        txtPass.setFont(new Font("SansSerif", Font.PLAIN, 24));
        txtPass.setHorizontalAlignment(JPasswordField.CENTER);

        JLabel lblUsuario = new JLabel("Usuario Único:");
        lblUsuario.setForeground(UIUtils.TEXTO_CLARO);
        JLabel lblPass = new JLabel("Contraseña (5 chars, 1 mayúscula, 1 número):");
        lblPass.setForeground(UIUtils.TEXTO_CLARO);

        // Etiqueta para errores (Sin Pop-ups)
        JLabel lblError = new JLabel(" ");
        lblError.setForeground(UIUtils.ERROR_COLOR);
        lblError.setFont(new Font("SansSerif", Font.BOLD, 14));

        JButton btnRegistrar = UIUtils.crearBotonEstilizado("Registrar y Entrar");
        JButton btnVolver = UIUtils.crearBotonEstilizado("Volver");

        btnVolver.addActionListener(e -> {
            lblError.setText(" ");
            mainApp.cambiarPantalla("Inicio");
        });

        btnRegistrar.addActionListener(e -> {
            try {
                String user = txtUsuario.getText();
                String pass = new String(txtPass.getPassword());

                if (user.isEmpty() || pass.isEmpty()) {
                    throw new CampoVacioException("Error: Ningún campo puede estar vacío.");
                }
                
                if (pass.length() != 5) {
                    throw new PasswordInvalidaException("Error: La contraseña debe tener exactamente 5 caracteres.");
                }
                
                boolean tieneMayuscula = false;
                boolean tieneNumero = false;
                for (char c : pass.toCharArray()) {
                    if (Character.isUpperCase(c)) tieneMayuscula = true;
                    if (Character.isDigit(c)) tieneNumero = true;
                }
                
                if (!tieneMayuscula || !tieneNumero) {
                    throw new PasswordInvalidaException("Error: La contraseña necesita 1 mayúscula y 1 número.");
                }

                Usuario nuevoUser = new Usuario(user, pass);
                mainApp.getBaseDatosUsuarios().registrarUsuario(nuevoUser);
                
                // Auto Login
                lblError.setText(" ");
                txtUsuario.setText("");
                txtPass.setText("");
                mainApp.loginExitoso(nuevoUser);

            } catch (MenuException ex) {
                lblError.setText(ex.getMessage());
            }
        });

        gbc.gridy = 0; add(titulo, gbc);
        gbc.gridy = 1; add(lblUsuario, gbc);
        gbc.gridy = 2; add(txtUsuario, gbc);
        gbc.gridy = 3; add(lblPass, gbc);
        gbc.gridy = 4; add(txtPass, gbc);
        gbc.gridy = 5; add(lblError, gbc);
        gbc.gridy = 6; add(btnRegistrar, gbc);
        gbc.gridy = 7; add(btnVolver, gbc);
    }
}