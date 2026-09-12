package Pokemon;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

public class RoundedButton extends JButton {
    private Color colorNormal;
    private Color colorHover;
    private Color colorPresionado;
    private boolean hover = false;
    private boolean presionado = false;
    private final int radio = 20;

    public RoundedButton(String texto) {
        this(texto, UIUtils.ROJO_POKEDEX, UIUtils.ROJO_HOVER, UIUtils.ROJO_PRESIONADO);
    }

    public RoundedButton(String texto, Color colorNormal, Color colorHover, Color colorPresionado) {
        super(texto);
        this.colorNormal = colorNormal;
        this.colorHover = colorHover;
        this.colorPresionado = colorPresionado;

        setFont(new Font("SansSerif", Font.BOLD, 16));
        setForeground(Color.WHITE);
        setFocusPainted(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setOpaque(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setPreferredSize(new Dimension(220, 48));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                hover = true;
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                hover = false;
                presionado = false;
                repaint();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                presionado = true;
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                presionado = false;
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Color base;
        if (!isEnabled()) {
            base = new Color(100, 100, 100);
        } else if (presionado) {
            base = colorPresionado;
        } else if (hover) {
            base = colorHover;
        } else {
            base = colorNormal;
        }

        int w = getWidth();
        int h = getHeight();

        g2.setColor(new Color(0, 0, 0, 70));
        g2.fill(new RoundRectangle2D.Double(2, 4, w - 4, h - 4, radio, radio));

        GradientPaint gp = new GradientPaint(0, 0, base.brighter(), 0, h, base.darker());
        g2.setPaint(gp);
        g2.fill(new RoundRectangle2D.Double(0, 0, w - 4, h - 4, radio, radio));

        g2.setStroke(new BasicStroke(2.0f));
        g2.setColor(new Color(255, 255, 255, isEnabled() ? 200 : 80));
        g2.draw(new RoundRectangle2D.Double(1, 1, w - 6, h - 6, radio, radio));

        FontMetrics fm = g2.getFontMetrics(getFont());
        int x = (w - 4 - fm.stringWidth(getText())) / 2;
        int y = (h - 4 - fm.getHeight()) / 2 + fm.getAscent();

        g2.setColor(getForeground());
        g2.setFont(getFont());
        g2.drawString(getText(), x, y);

        g2.dispose();
    }
}