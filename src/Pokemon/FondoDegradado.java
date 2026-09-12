package Pokemon;

import javax.swing.*;
import java.awt.*;


public class FondoDegradado extends JPanel {
    private final Color colorSuperior;
    private final Color colorInferior;

    public FondoDegradado(Color colorSuperior, Color colorInferior) {
        this.colorSuperior = colorSuperior;
        this.colorInferior = colorInferior;
        setOpaque(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();

        GradientPaint gp = new GradientPaint(0, 0, colorSuperior, w, h, colorInferior);
        g2.setPaint(gp);
        g2.fillRect(0, 0, w, h);

        
        g2.setColor(new Color(255, 255, 255, 10));
        g2.fillOval(-120, -140, 320, 320);
        g2.fillOval(w - 220, h - 260, 380, 380);

        g2.setColor(new Color(255, 255, 255, 6));
        g2.fillOval(w - 480, -80, 240, 240);

        g2.dispose();
    }
}
