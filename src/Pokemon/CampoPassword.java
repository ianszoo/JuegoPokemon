package Pokemon;

import javax.swing.*;
import java.awt.*;


public class CampoPassword extends JPanel {
    private final JPasswordField campo;
    private final JButton btnToggle;
    private boolean visible = false;

    public CampoPassword(int columnas) {
        setOpaque(false);
        setLayout(new BorderLayout(8, 0));

        campo = new JPasswordField(columnas);
        campo.setEchoChar('•');
        campo.setFont(new Font("SansSerif", Font.PLAIN, 20));
        campo.setHorizontalAlignment(JTextField.CENTER);
        campo.setBackground(Color.WHITE);
        campo.setForeground(UIUtils.AZUL_OSCURO);
        campo.setCaretColor(UIUtils.AZUL_OSCURO);
        campo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UIUtils.AMARILLO, 2, true),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)));

        btnToggle = new JButton("Mostrar");
        btnToggle.setFont(new Font("SansSerif", Font.BOLD, 11));
        btnToggle.setFocusPainted(false);
        btnToggle.setFocusable(false);
        btnToggle.setBackground(UIUtils.AZUL_CLARO);
        btnToggle.setForeground(UIUtils.AMARILLO);
        btnToggle.setBorder(BorderFactory.createLineBorder(UIUtils.AMARILLO, 1, true));
        btnToggle.setMargin(new Insets(2, 6, 2, 6));
        btnToggle.setPreferredSize(new Dimension(78, 40));
        btnToggle.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnToggle.addActionListener(e -> alternarVisibilidad());

        add(campo, BorderLayout.CENTER);
        add(btnToggle, BorderLayout.EAST);
    }

    private void alternarVisibilidad() {
        visible = !visible;
        campo.setEchoChar(visible ? (char) 0 : '•');
        btnToggle.setText(visible ? "Ocultar" : "Mostrar");
    }

    public String getPassword() {
        return new String(campo.getPassword());
    }

    public void limpiar() {
        campo.setText("");
        visible = false;
        campo.setEchoChar('•');
        btnToggle.setText("Mostrar");
    }

    public JPasswordField getCampo() {
        return campo;
    }
}