/*!
 * resizable-label
 * https://github.com/tfoxy/resizable-label
 *
 * Copyright 2015 Tomás Fox
 * Released under the MIT license
 */

package tf.resizablelabel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.Icon;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Graphics;

/**
 * Based on: http://stackoverflow.com/questions/9814616
 */
public class ResizableLabel extends JLabel {
    public static final float LARGE_FONT_SIZE = 2000f;

    private static final Logger LOGGER =
            LoggerFactory.getLogger(ResizableLabel.class);


    private boolean painting = false;


    public ResizableLabel() {
        super();
    }

    public ResizableLabel(String text) {
        super(text);
    }

    public ResizableLabel(Icon image, int horizontalAlignment) {
        super(image, horizontalAlignment);
    }

    public ResizableLabel(Icon image) {
        super(image);
    }

    public ResizableLabel(String text, Icon icon, int horizontalAlignment) {
        super(text, icon, horizontalAlignment);
    }

    public ResizableLabel(String text, int horizontalAlignment) {
        super(text, horizontalAlignment);
    }

    void adaptLabelFont() {
        if (getText().isEmpty())
            return;

        Font font = getFont();
        Font auxFont = font.deriveFont(font.getStyle(), LARGE_FONT_SIZE);

        // stringWidth returns an int. So it is more precise to use a font with a large size
        double stringWidth = getFontMetrics(auxFont).stringWidth(getText());
        double parentWidth = getParent().getWidth();

        // Find out how much the font can grow in width.
        LOGGER.debug("parentWidth {}, stringWidth {}", parentWidth, stringWidth);
        double widthRatio = parentWidth / stringWidth;

        LOGGER.debug("fontSize {}, widthRatio {}", auxFont.getSize2D(), widthRatio);
        float newFontSize = (float) Math.floor(auxFont.getSize2D() * widthRatio);
        int parentHeight = getParent().getHeight();

        // Pick a new font size so it will not be larger than the height of parent.
        float fontSizeToUse = Math.min(newFontSize, parentHeight);

        Font newFont = font.deriveFont(font.getStyle(), fontSizeToUse);

        // Sometimes with the new font, the width exceeds the parent width.
        // Fix it by decrementing the font size
        if (getFontMetrics(newFont).stringWidth(getText()) > parentWidth) {
            fontSizeToUse--;
            newFont = font.deriveFont(font.getStyle(), fontSizeToUse);
        }

        // Set the label's font size to the newly determined size.
        if (font.getSize2D() != fontSizeToUse) {
            LOGGER.debug("font size changed from {} to {}", font.getSize2D(), fontSizeToUse);
            setFont(newFont);
        }
    }

    @Override
    public void repaint() {
        LOGGER.debug("repaint {}", !painting);
        if (!painting) {
            super.repaint();
        }
    }

    @Override
    public void paint(Graphics g) {
        LOGGER.debug("painting {}", painting);
        if (!painting) {
            painting = true;
            LOGGER.debug("paint");
            adaptLabelFont();
            super.paint(g);
            painting = false;
        }
    }
}
