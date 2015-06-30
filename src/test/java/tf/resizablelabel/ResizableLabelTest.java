package tf.resizablelabel;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.swing.*;

public class ResizableLabelTest {
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
}
