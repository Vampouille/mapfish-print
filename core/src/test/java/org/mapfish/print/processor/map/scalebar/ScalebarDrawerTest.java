package org.mapfish.print.processor.map.scalebar;

import static org.junit.Assert.*;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.mapfish.print.AbstractMapfishSpringTest;
import org.mapfish.print.attribute.ScalebarAttribute;
import org.mapfish.print.attribute.ScalebarAttribute.ScalebarAttributeValues;
import org.mapfish.print.map.DistanceUnit;
import org.mapfish.print.test.util.ImageSimilarity;

public class ScalebarDrawerTest {

    @Test
    public void testCreate() {
        assertNotNull(ScalebarDrawer.create(Type.LINE, null, new ScaleBarRenderSettings()));
        assertNotNull(ScalebarDrawer.create(Type.BAR, null, new ScaleBarRenderSettings()));
        assertNotNull(ScalebarDrawer.create(Type.BAR_SUB, null, new ScaleBarRenderSettings()));
    }

    @Test
    public void testDrawLine() throws Exception {
        final BufferedImage bufferedImage = new BufferedImage(180, 40, BufferedImage.TYPE_4BYTE_ABGR);
        final Graphics2D graphics2d = bufferedImage.createGraphics();

        ScaleBarRenderSettings settings = getSettings(graphics2d, 1);
        ScalebarDrawer drawer = ScalebarDrawer.create(Type.LINE, graphics2d, settings);
        drawer.draw();

//        ImageSimilarity.writeUncompressedImage(bufferedImage, "/tmp/expected-scalebar-line.tiff");
        new ImageSimilarity(bufferedImage, 4).assertSimilarity(getFile("expected-scalebar-line.tiff"), 5);
    }

    @Test
    public void testDrawLineWithSubIntervals() throws Exception {
        final BufferedImage bufferedImage = new BufferedImage(180, 40, BufferedImage.TYPE_4BYTE_ABGR);
        final Graphics2D graphics2d = bufferedImage.createGraphics();

        ScaleBarRenderSettings settings = getSettings(graphics2d, 2);
        ScalebarDrawer drawer = ScalebarDrawer.create(Type.LINE, graphics2d, settings);
        drawer.draw();

//        ImageSimilarity.writeUncompressedImage(bufferedImage, "/tmp/expected-scalebar-line-subs.tiff");
        new ImageSimilarity(bufferedImage, 4).assertSimilarity(getFile("expected-scalebar-line-subs.tiff"), 5);
    }

    @Test
    public void testDrawBar() throws Exception {
        final BufferedImage bufferedImage = new BufferedImage(180, 40, BufferedImage.TYPE_4BYTE_ABGR);
        final Graphics2D graphics2d = bufferedImage.createGraphics();

        ScaleBarRenderSettings settings = getSettings(graphics2d, 1);
        ScalebarDrawer drawer = ScalebarDrawer.create(Type.BAR, graphics2d, settings);
        drawer.draw();

//        ImageSimilarity.writeUncompressedImage(bufferedImage, "/tmp/expected-scalebar-bar.tiff");
        new ImageSimilarity(bufferedImage, 4).assertSimilarity(getFile("expected-scalebar-bar.tiff"), 5);
    }

    @Test
    public void testDrawBarWithSubIntervals() throws Exception {
        final BufferedImage bufferedImage = new BufferedImage(180, 40, BufferedImage.TYPE_4BYTE_ABGR);
        final Graphics2D graphics2d = bufferedImage.createGraphics();

        ScaleBarRenderSettings settings = getSettings(graphics2d, 2);
        ScalebarDrawer drawer = ScalebarDrawer.create(Type.BAR, graphics2d, settings);
        drawer.draw();

//        ImageSimilarity.writeUncompressedImage(bufferedImage, "/tmp/expected-scalebar-bar-subs.tiff");
        new ImageSimilarity(bufferedImage, 4).assertSimilarity(getFile("expected-scalebar-bar-subs.tiff"), 5);
    }

    @Test
    public void testDrawBarWithBackground() throws Exception {
        final BufferedImage bufferedImage = new BufferedImage(180, 40, BufferedImage.TYPE_4BYTE_ABGR);
        final Graphics2D graphics2d = bufferedImage.createGraphics();

        ScaleBarRenderSettings settings = getSettings(graphics2d, 1);
        settings.getParams().backgroundColor = "rgb(214, 214, 214)";
        ScalebarDrawer drawer = ScalebarDrawer.create(Type.BAR, graphics2d, settings);
        drawer.draw();

//        ImageSimilarity.writeUncompressedImage(bufferedImage, "/tmp/expected-scalebar-bar-bg.tiff");
        new ImageSimilarity(bufferedImage, 4).assertSimilarity(getFile("expected-scalebar-bar-bg.tiff"), 25);
    }

    @Test
    public void testDrawBarSub() throws Exception {
        final BufferedImage bufferedImage = new BufferedImage(180, 40, BufferedImage.TYPE_4BYTE_ABGR);
        final Graphics2D graphics2d = bufferedImage.createGraphics();

        ScaleBarRenderSettings settings = getSettings(graphics2d, 1);
        ScalebarDrawer drawer = ScalebarDrawer.create(Type.BAR_SUB, graphics2d, settings);
        drawer.draw();

//        ImageSimilarity.writeUncompressedImage(bufferedImage, "/tmp/expected-scalebar-barsub.tiff");
        new ImageSimilarity(bufferedImage, 4).assertSimilarity(getFile("expected-scalebar-barsub.tiff"), 5);
    }

    @Test
    public void testDrawBarSubWithSubIntervals() throws Exception {
        final BufferedImage bufferedImage = new BufferedImage(180, 40, BufferedImage.TYPE_4BYTE_ABGR);
        final Graphics2D graphics2d = bufferedImage.createGraphics();

        ScaleBarRenderSettings settings = getSettings(graphics2d, 2);
        ScalebarDrawer drawer = ScalebarDrawer.create(Type.BAR_SUB, graphics2d, settings);
        drawer.draw();

//        ImageSimilarity.writeUncompressedImage(bufferedImage, "/tmp/expected-scalebar-barsub-subs.tiff");
        new ImageSimilarity(bufferedImage, 4).assertSimilarity(getFile("expected-scalebar-barsub-subs.tiff"), 5);
    }

    @Test
    public void testDrawBarHorizontalTextAbove() throws Exception {
        final BufferedImage bufferedImage = new BufferedImage(180, 40, BufferedImage.TYPE_4BYTE_ABGR);
        final Graphics2D graphics2d = bufferedImage.createGraphics();

        ScaleBarRenderSettings settings = getSettings(graphics2d, 1);
        settings.getParams().backgroundColor = "rgb(214, 214, 214)";
        settings.getParams().orientation = Orientation.HORIZONTAL_LABELS_ABOVE.getLabel();
        ScalebarDrawer drawer = ScalebarDrawer.create(Type.BAR, graphics2d, settings);
        drawer.draw();

//        ImageSimilarity.writeUncompressedImage(bufferedImage, "/tmp/expected-scalebar-bar-text-above.tiff");
        new ImageSimilarity(bufferedImage, 4).assertSimilarity(getFile("expected-scalebar-bar-text-above.tiff"), 18);
    }

    @Test
    public void testDrawBarVerticalTextLeft() throws Exception {
        final BufferedImage bufferedImage = new BufferedImage(80, 180, BufferedImage.TYPE_4BYTE_ABGR);
        final Graphics2D graphics2d = bufferedImage.createGraphics();

        ScaleBarRenderSettings settings = getSettings(graphics2d, 1);
        settings.getParams().getSize().width = 80;
        settings.getParams().getSize().height = 180;
        settings.setMaxSize(new Dimension(80, 180));
        settings.getParams().backgroundColor = "rgb(214, 214, 214)";
        settings.getParams().orientation = Orientation.VERTICAL_LABELS_LEFT.getLabel();
        settings.setSize(ScalebarGraphic.getSize(settings.getParams(), settings, settings.getMaxLabelSize()));
        ScalebarDrawer drawer = ScalebarDrawer.create(Type.BAR, graphics2d, settings);
        drawer.draw();

//        ImageSimilarity.writeUncompressedImage(bufferedImage, "/tmp/expected-scalebar-bar-vertical-text-left.tiff");
        new ImageSimilarity(bufferedImage, 4).assertSimilarity(getFile("expected-scalebar-bar-vertical-text-left.tiff"), 44);
    }

    @Test
    public void testDrawBarVerticalTextRight() throws Exception {
        final BufferedImage bufferedImage = new BufferedImage(80, 180, BufferedImage.TYPE_4BYTE_ABGR);
        final Graphics2D graphics2d = bufferedImage.createGraphics();

        ScaleBarRenderSettings settings = getSettings(graphics2d, 1);
        settings.getParams().getSize().width = 80;
        settings.getParams().getSize().height = 180;
        settings.setMaxSize(new Dimension(80, 180));
        settings.getParams().backgroundColor = "rgb(214, 214, 214)";
        settings.getParams().orientation = Orientation.VERTICAL_LABELS_RIGHT.getLabel();
        settings.setSize(ScalebarGraphic.getSize(settings.getParams(), settings, settings.getMaxLabelSize()));
        ScalebarDrawer drawer = ScalebarDrawer.create(Type.BAR, graphics2d, settings);
        drawer.draw();

//        ImageSimilarity.writeUncompressedImage(bufferedImage, "/tmp/expected-scalebar-bar-vertical-text-right.tiff");
        new ImageSimilarity(bufferedImage, 4).assertSimilarity(getFile("expected-scalebar-bar-vertical-text-right.tiff"), 44);
    }

    @Test
    public void testDrawBarTopRight() throws Exception {
        final BufferedImage bufferedImage = new BufferedImage(180, 40, BufferedImage.TYPE_4BYTE_ABGR);
        final Graphics2D graphics2d = bufferedImage.createGraphics();

        ScaleBarRenderSettings settings = getSettings(graphics2d, 1);
        settings.getParams().backgroundColor = "rgb(214, 214, 214)";
        settings.getParams().align = HorizontalAlign.RIGHT.getLabel();
        settings.getParams().verticalAlign = VerticalAlign.TOP.getLabel();
        ScalebarDrawer drawer = ScalebarDrawer.create(Type.BAR, graphics2d, settings);
        drawer.draw();

//        ImageSimilarity.writeUncompressedImage(bufferedImage, "/tmp/expected-scalebar-top-right.tiff");
        new ImageSimilarity(bufferedImage, 4).assertSimilarity(getFile("expected-scalebar-top-right.tiff"), 25);
    }

    @Test
    public void testDrawBarMiddleCenter() throws Exception {
        final BufferedImage bufferedImage = new BufferedImage(180, 40, BufferedImage.TYPE_4BYTE_ABGR);
        final Graphics2D graphics2d = bufferedImage.createGraphics();

        ScaleBarRenderSettings settings = getSettings(graphics2d, 1);
        settings.getParams().backgroundColor = "rgb(214, 214, 214)";
        settings.getParams().align = HorizontalAlign.CENTER.getLabel();
        settings.getParams().verticalAlign = VerticalAlign.MIDDLE.getLabel();
        ScalebarDrawer drawer = ScalebarDrawer.create(Type.BAR, graphics2d, settings);
        drawer.draw();

//        ImageSimilarity.writeUncompressedImage(bufferedImage, "/tmp/expected-scalebar-middle-center.tiff");
        new ImageSimilarity(bufferedImage, 4).assertSimilarity(getFile("expected-scalebar-middle-center.tiff"), 40);
    }

    private ScaleBarRenderSettings getSettings(final Graphics2D graphics2d, int numSubIntervals) {
        ScalebarAttribute attribute = new ScalebarAttribute();
        attribute.setWidth(180);
        attribute.setHeight(40);
        ScalebarAttributeValues params = attribute.createValue(null);
        params.labelDistance = 4;
        params.barSize = 10;
        ScaleBarRenderSettings settings = new ScaleBarRenderSettings();
        settings.setParams(params);

        setLabels(settings, graphics2d, params, 40, 50);
        settings.setScaleUnit(DistanceUnit.M);
        settings.setIntervalLengthInPixels(40);
        settings.setIntervalLengthInWorldUnits(0);
        settings.setIntervalUnit(DistanceUnit.M);
        settings.setBarSize(10);
        settings.setLabelDistance(4);
        settings.setLineWidth(2);
        settings.setNumSubIntervals(numSubIntervals);
        settings.setPadding(4);
        settings.setDpiRatio(1.0);

        final Dimension maxLabelSize = ScalebarGraphic.getMaxLabelSize(settings);
        settings.setMaxSize(new Dimension(180, 40));
        settings.setSize(ScalebarGraphic.getSize(params, settings, maxLabelSize));
        settings.setMaxLabelSize(maxLabelSize);
        return settings;
    }

    private void setLabels(ScaleBarRenderSettings settings, Graphics2D graphics2d, ScalebarAttributeValues params,
            int intervalWidthInPixels, int intervalWidthInWorldUnits) {
        final Font font = new Font(params.font, Font.PLAIN, params.fontSize);
        final FontRenderContext frc = new FontRenderContext(null, true, true);

        final List<Label> labels = new ArrayList<Label>(params.intervals + 1);
        for (int i = 0; i <= params.intervals; i++) {
            String labelText = ScalebarGraphic.createLabelText(DistanceUnit.M, intervalWidthInWorldUnits * i, DistanceUnit.M);
            if (i == params.intervals) {
                labelText += DistanceUnit.M;
            }
            TextLayout labelLayout = new TextLayout(labelText, font, frc);
            labels.add(new Label(intervalWidthInPixels * i, labelLayout, params.getOrientation()));
        }
        settings.setLabels(labels);

        settings.setLeftLabelMargin(labels.get(0).getWidth() / 2.0f);
        settings.setRightLabelMargin(labels.get(labels.size() - 1).getWidth() / 2.0f);
        settings.setTopLabelMargin(labels.get(0).getHeight() / 2.0f);
        settings.setBottomLabelMargin(labels.get(labels.size() - 1).getHeight() / 2.0f);
    }

    private File getFile(String fileName) {
        return AbstractMapfishSpringTest.getFile(getClass(), fileName);
    }
}
