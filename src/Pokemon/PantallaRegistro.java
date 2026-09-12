package Pokemon;

import javax.swing.*;
import java.awt.*;


public class PantallaRegistro extends JPanel {

    private static final int LONGITUD_MINIMA = 5;

    public PantallaRegistro(PokemonShenanigans mainApp) {
        setLayout(new BorderLayout());

        FondoDegradado fondo = new FondoDegradado(UIUtils.AZUL_OSCURO, new Color(12, 15, 24));
        fondo.setLayout(new GridBagLayout());
        add(fondo, BorderLayout.CENTER);

        TarjetaRedondeada tarjeta = new TarjetaRedondeada(UIUtils.AZUL_MEDIO, UIUtils.AMARILLO, 28);
        tarjeta.setLayout(new GridBagLayout());
        tarjeta.setPreferredSize(new Dimension(500, 680));
        fondo.add(tarjeta, new GridBagConstraints());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(6, 40, 6, 40);

        JLabel titulo = UIUtils.crearTitulo("CREAR ENTRENADOR", 30);
        JLabel subtitulo = UIUtils.crearSubtitulo("Regístrate y comienza tu aventura");

        JLabel lblUsuario = UIUtils.crearEtiqueta("USUARIO ÚNICO");
        JTextField txtUsuario = UIUtils.crearCampoTexto(15);

        JLabel lblPass = UIUtils.crearEtiqueta("CONTRASEÑA");
        CampoPassword campoPass = new CampoPassword(15);
        JLabel ayudaPass = UIUtils.crearEtiquetaAyuda(
                "Mínimo " + LONGITUD_MINIMA + " caracteres, con al menos 1 mayúscula y 1 número");

        JLabel lblConfirmar = UIUtils.crearEtiqueta("CONFIRMAR CONTRASEÑA");
        CampoPassword campoConfirmar = new CampoPassword(15);

        JLabel lblError = UIUtils.crearEtiquetaError();

        JButton btnRegistrar = UIUtils.crearBotonExito("Registrar y Entrar");
        JButton btnVolver = UIUtils.crearBotonSecundario("Volver");

        btnVolver.addActionListener(e -> {
            lblError.setText(" ");
            txtUsuario.setText("");
            campoPass.limpiar();
            campoConfirmar.limpiar();
            mainApp.cambiarPantalla("Inicio");
        });

        btnRegistrar.addActionListener(e -> {
            try {
                String user = txtUsuario.getText().trim();
                String pass = campoPass.getPassword();
                String confirmacion = campoConfirmar.getPassword();

                if (user.isEmpty() || pass.isEmpty() || confirmacion.isEmpty()) {
                    throw new CampoVacioException("Error: Ningún campo puede estar vacío.");
                }

                if (pass.length() < LONGITUD_MINIMA) {
                    throw new PasswordInvalidaException(
                            "Error: La contraseña debe tener mínimo " + LONGITUD_MINIMA + " caracteres.");
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

                if (!pass.equals(confirmacion)) {
                    throw new PasswordInvalidaException("Error: Las contraseñas no coinciden.");
                }

                Usuario nuevoUser = new Usuario(user, pass);
                mainApp.getBaseDatosUsuarios().registrarUsuario(nuevoUser);

                lblError.setText(" ");
                txtUsuario.setText("");
                campoPass.limpiar();
                campoConfirmar.limpiar();
                mainApp.loginExitoso(nuevoUser);

            } catch (MenuException ex) {
                lblError.setText(ex.getMessage());
            }
        });

        JPanel panelBotones = new JPanel(new GridLayout(2, 1, 0, 12));
        panelBotones.setOpaque(false);
        panelBotones.add(btnRegistrar);
        panelBotones.add(btnVolver);

        int fila = 0;
        gbc.insets = new Insets(18, 40, 2, 40);
        gbc.gridy = fila++; tarjeta.add(titulo, gbc);
        gbc.insets = new Insets(2, 40, 18, 40);
        gbc.gridy = fila++; tarjeta.add(subtitulo, gbc);

        gbc.insets = new Insets(6, 40, 2, 40);
        gbc.gridy = fila++; tarjeta.add(lblUsuario, gbc);
        gbc.insets = new Insets(0, 40, 12, 40);
        gbc.gridy = fila++; tarjeta.add(txtUsuario, gbc);

        gbc.insets = new Insets(6, 40, 2, 40);
        gbc.gridy = fila++; tarjeta.add(lblPass, gbc);
        gbc.insets = new Insets(0, 40, 2, 40);
        gbc.gridy = fila++; tarjeta.add(campoPass, gbc);
        gbc.insets = new Insets(0, 40, 12, 40);
        gbc.gridy = fila++; tarjeta.add(ayudaPass, gbc);

        gbc.insets = new Insets(6, 40, 2, 40);
        gbc.gridy = fila++; tarjeta.add(lblConfirmar, gbc);
        gbc.insets = new Insets(0, 40, 14, 40);
        gbc.gridy = fila++; tarjeta.add(campoConfirmar, gbc);

        gbc.insets = new Insets(0, 40, 10, 40);
        gbc.gridy = fila++; tarjeta.add(lblError, gbc);

        gbc.insets = new Insets(10, 40, 25, 40);
        gbc.gridy = fila++; tarjeta.add(panelBotones, gbc);
    }
}