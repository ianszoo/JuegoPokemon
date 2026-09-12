package Pokemon;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class FondoImagen extends JPanel {
    private Image imagen;

    public FondoImagen(String rutaRecurso) {
        URL url = getClass().getResource(rutaRecurso);
        if (url != null) {
            imagen = new ImageIcon(url).getImage();
        }
        setOpaque(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int w = getWidth();
        int h = getHeight();
        if (imagen != null) {
            g.drawImage(imagen, 0, 0, w, h, this);
        } else {
            g.setColor(UIUtils.AZUL_OSCURO);
            g.fillRect(0, 0, w, h);
        }
    }
}