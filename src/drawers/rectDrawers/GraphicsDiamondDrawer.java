package drawers.rectDrawers;

import drawers.lineDrawers.LineDrawer;
import engine.shapes.Line;
import engine.shapes.Rectangle;
import engine.shapes.Style;
import engine.wrappers.RectWrapper;
import screen.ScreenConverter;
import screen.ScreenCoordinates;

import java.awt.*;
import java.util.Arrays;

public class GraphicsDiamondDrawer extends GraphicsRectDrawer {
    public GraphicsDiamondDrawer(Graphics2D graphics2D, ScreenConverter screenConverter) {
        super(graphics2D, screenConverter);
    }

    @Override
    public void draw(Rectangle rectangle) {
        ScreenConverter screenConverter = getScreenConverter();
        Graphics2D graphics2D = getGraphics2D();
        LineDrawer lineDrawer = getLineDrawer();

        RectWrapper wrapper = rectangle.getWrapper();

        double width = wrapper.getWidth();
        double height = wrapper.getHeight();

        Line leftTop = new Line(wrapper.getLeftMiddle().getPosition(), wrapper.getTopMiddle().getPosition());
        Line rightTop = new Line(wrapper.getTopMiddle().getPosition(), wrapper.getRightMiddle().getPosition());
        Line rightBottom = new Line(wrapper.getRightMiddle().getPosition(), wrapper.getBottomMiddle().getPosition());
        Line leftBottom = new Line(wrapper.getBottomMiddle().getPosition(), wrapper.getLeftMiddle().getPosition());
        ScreenCoordinates screenCoordinates = new ScreenCoordinates(Arrays.asList(
                screenConverter.r2s(wrapper.getLeftMiddle().getPosition()),
                screenConverter.r2s(wrapper.getTopMiddle().getPosition()),
                screenConverter.r2s(wrapper.getRightMiddle().getPosition()),
                screenConverter.r2s(wrapper.getBottomMiddle().getPosition()),
                screenConverter.r2s(wrapper.getLeftMiddle().getPosition())));

        Style style = rectangle.getStyle();
        graphics2D.setStroke(style.getStroke());

        if (style.isFilled()) {
            graphics2D.setColor(style.getColor());
            graphics2D.fillPolygon(screenCoordinates.getXx(), screenCoordinates.getYy(), screenCoordinates.size());
        }
        graphics2D.setColor(style.getBorderColor());
        graphics2D.drawPolygon(screenCoordinates.getXx(), screenCoordinates.getYy(), screenCoordinates.size());

//        lineDrawer.draw(leftTop);
//        lineDrawer.draw(rightTop);
//        lineDrawer.draw(rightBottom);
//        lineDrawer.draw(leftBottom);
    }
}
