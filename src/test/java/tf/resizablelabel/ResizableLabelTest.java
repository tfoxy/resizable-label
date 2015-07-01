package tf.resizablelabel;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ResizableLabelTest {
    private static final Logger LOGGER =
            LoggerFactory.getLogger(ResizableLabelTest.class);

    private static void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e.getMessage(), e.getCause());
        }
    }

    private ResizableLabel label;

    @Before
    public void initialize() {
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
    public void showFrame() {
        label.setText("FooBar");

        JPanel jPanel = new JPanel();
        jPanel.setSize(200, 100);

        LOGGER.info("Add to panel");
        jPanel.add(label);

        JFrame frame = new JFrame();
        frame.setSize(300, 150);

        LOGGER.info("Add to frame");
        frame.add(label);

        LOGGER.info("Visible frame");
        frame.setVisible(true);

        LOGGER.info("Sleep");
        sleep(100000);
    }
}
