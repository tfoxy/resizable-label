package tf.resizablelabel;

import javax.swing.JLabel;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class ResizableLabel extends JLabel {
    public static final int MIN_FONT_SIZE = 3;
    public static final int MAX_FONT_SIZE = 240;
    private Graphics g;

    public ResizableLabel() {
        super();
        init();
    }

    public ResizableLabel(String text) {
        super(text);
        init();
    }

    protected void init() {
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                adaptLabelFont();
            }

            @Override
            public void componentResized(ComponentEvent e) {
                adaptLabelFont();
            }

            @Override
            public void componentMoved(ComponentEvent e) {
                adaptLabelFont();
            }
        });

        addAncestorListener(new AncestorListener() {
            @Override
            public void ancestorAdded(AncestorEvent event) {
                adaptLabelFont();
            }

            @Override
            public void ancestorRemoved(AncestorEvent event) {
                // noop
            }

            @Override
            public void ancestorMoved(AncestorEvent event) {
                adaptLabelFont();
            }
        });
    }

    protected void adaptLabelFont() {
        if (g == null || getText().isEmpty()) {
            return;
        }
        Rectangle r = getBounds();
        Font f = getFont();
        Rectangle r1 = new Rectangle();
        Rectangle r2 = new Rectangle();
        int fontSize = f.getSize();

        setSizeBasedOnTextSize(r1, fontSize);
        setSizeBasedOnTextSize(r2, fontSize + 1);
        if (r.contains(r1) && !r.contains(r2)) {
            return;
        }

        fontSize = MIN_FONT_SIZE;
        while (fontSize < MAX_FONT_SIZE) {
            setSizeBasedOnTextSize(r1, fontSize);
            setSizeBasedOnTextSize(r2, fontSize + 1);
            if (r.contains(r1) && !r.contains(r2)) {
                break;
            }
            fontSize++;
        }

        setFont(f.deriveFont(f.getStyle(), fontSize));
        repaint();
    }

    private void setSizeBasedOnTextSize(Rectangle r, int fontSize) {
        Font f = getFont();
        r.setSize(getTextSize(f.deriveFont(f.getStyle(), fontSize)));
    }

    private Dimension getTextSize(Font f) {
        Dimension size = new Dimension();
        g.setFont(f);
        FontMetrics fm = g.getFontMetrics(f);
        size.width = fm.stringWidth(getText());
        size.height = fm.getHeight();

        return size;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.g = g;
    }

    @Override
    public void setText(String text) {
        super.setText(text);
        adaptLabelFont();
    }
}