/*!
 * resizable-label
 * https://github.com/tfoxy/resizable-label
 *
 * Copyright 2015 Tomás Fox
 * Released under the MIT license
 */

package tf.resizablelabel;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Font;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.junit.Assert.assertThat;

public class ResizableLabelTest {
    private ResizableLabel label;

    @Before
    public void setUp() {
        label = new ResizableLabel();
    }

    @Test
    public void hasDefaultFontSizeAsJLabelWhenCreated() {
        JLabel jLabel = new JLabel();
        int jLabelSize = jLabel.getFont().getSize();
        int size = label.getFont().getSize();

        Assert.assertEquals(jLabelSize, size);
    }

    @Test
    public void increasesFontSizeWhenParentIsBigger() {
        label.setText("FooBar");

        float size = label.getFont().getSize2D();

        JPanel jPanel = new JPanel();
        jPanel.setSize(200, 100);

        jPanel.add(label);

        label.adaptLabelFont();

        assertThat(label.getFont().getSize2D(), greaterThan(size));
    }

    @Test
    public void changesFontSizeToFillTheContainer() {
        label.setText("FooBar");

        JPanel panel = new JPanel();
        panel.setSize(300, 150);

        panel.add(label);

        label.adaptLabelFont();

        Assert.assertEquals(75, label.getFont().getSize2D(), 0);
    }

    @Test
    public void doesNotExceedsContainerWidth() {
        label.setText("FooBar");

        JPanel panel = new JPanel();

        panel.add(label);

        for (int panelWidth = 200; panelWidth <= 500; ++panelWidth) {
            panel.setSize(panelWidth, 150);

            label.adaptLabelFont();

            Font font = label.getFont();
            String text = label.getText();
            int labelWidth = label.getFontMetrics(font).stringWidth(text);

            assertThat(panel.getWidth(), greaterThanOrEqualTo(labelWidth));
        }
    }
}
