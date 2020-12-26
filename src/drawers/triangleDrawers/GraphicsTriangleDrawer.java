package drawers.triangleDrawers;

import drawers.GraphicsDrawer;
import drawers.lineDrawers.GraphicsLineDrawer;
import drawers.lineDrawers.LineDrawer;
import drawers.rectDrawers.GraphicsDiamondDrawer;
import drawers.wrapperDrawer.GraphicsWrapperDrawer;
import drawers.wrapperDrawer.WrapperDrawer;
import engine.shapes.*;
import engine.shapes.Rectangle;
import engine.wrappers.RectWrapper;
import screen.ScreenConverter;
import screen.ScreenCoordinates;
import screen.ScreenPoint;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.Arrays;

public class GraphicsTriangleDrawer extends GraphicsDrawer implements TriangleDrawer {
    private LineDrawer lineDrawer;
    private WrapperDrawer wrapperDrawer;

    public GraphicsTriangleDrawer(Graphics2D graphics2D, ScreenConverter screenConverter) {
        super(graphics2D, screenConverter);
        this.lineDrawer = new GraphicsLineDrawer(graphics2D, screenConverter);
        this.wrapperDrawer = new GraphicsWrapperDrawer(graphics2D, screenConverter);
    }

    @Override
    public void draw(Triangle triangle) {
        ScreenConverter screenConverter = getScreenConverter();
        Graphics2D graphics2D = getGraphics2D();

        RectWrapper wrapper = triangle.getWrapper();

        double width = wrapper.getWidth();
        double height = wrapper.getHeight();

        Line left = new Line(wrapper.getBottomLeft().getPosition(), wrapper.getTopMiddle().getPosition());
        Line right = new Line(wrapper.getTopMiddle().getPosition(), wrapper.getBottomRight().getPosition());
        Line bottom = new Line(wrapper.getBottomRight().getPosition(), wrapper.getBottomLeft().getPosition());


        ScreenCoordinates screenCoordinates = new ScreenCoordinates(Arrays.asList(
                screenConverter.r2s(wrapper.getBottomLeft().getPosition()),
                screenConverter.r2s(wrapper.getTopMiddle().getPosition()),
                screenConverter.r2s(wrapper.getBottomRight().getPosition()),
                screenConverter.r2s(wrapper.getBottomLeft().getPosition())
        ));

        Style style = triangle.getStyle();
        graphics2D.setStroke(style.getStroke());

        if (style.isFilled()) {
            graphics2D.setColor(style.getColor());
            graphics2D.fillPolygon(screenCoordinates.getXx(), screenCoordinates.getYy(), screenCoordinates.size());
        }
        graphics2D.setColor(style.getBorderColor());
        graphics2D.drawPolygon(screenCoordinates.getXx(), screenCoordinates.getYy(), screenCoordinates.size());



//
//        lineDrawer.draw(left);
//        lineDrawer.draw(right);
//        lineDrawer.draw(bottom);
//
//        ScreenPoint sl = screenConverter.r2s(wrapper.getTopLeft().getPosition());
//        ScreenPoint sl = screenConverter.r2s(triangle.getLeftTop());
//
//        graphics2D.setColor(Color.WHITE);
//        graphics2D.fillRect(sl.getI(), sl.getJ(), screenConverter.r2sDistanceH(width), screenConverter.r2sDistanceV(height));
//        graphics2D.setColor(Color.BLACK);
//        graphics2D.drawRect(sl.getI(), sl.getJ(), screenConverter.r2sDistanceH(width), screenConverter.r2sDistanceV(height));

    }

    public LineDrawer getLineDrawer() {
        return lineDrawer;
    }

    public WrapperDrawer getWrapperDrawer() {
        return wrapperDrawer;
    }
}
