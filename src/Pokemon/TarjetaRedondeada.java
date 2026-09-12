package Pokemon;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;


public class TarjetaRedondeada extends JPanel {
    private final Color colorFondo;
    private final Color colorBorde;
    private final int radio;

    public TarjetaRedondeada(Color colorFondo, Color colorBorde, int radio) {
        this.colorFondo = colorFondo;
        this.colorBorde = colorBorde;
        this.radio = radio;
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();

        // Sombra
        g2.setColor(new Color(0, 0, 0, 90));
        g2.fill(new RoundRectangle2D.Double(6, 8, w - 12, h - 12, radio, radio));

        // Cuerpo
        g2.setColor(colorFondo);
        g2.fill(new RoundRectangle2D.Double(0, 0, w - 12, h - 14, radio, radio));

        // Borde
        g2.setStroke(new BasicStroke(3.5f));
        g2.setColor(colorBorde);
        g2.draw(new RoundRectangle2D.Double(1.75, 1.75, w - 15.5, h - 17.5, radio, radio));

        g2.dispose();
        super.paintComponent(g);
    }
}