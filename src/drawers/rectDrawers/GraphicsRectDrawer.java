package drawers.rectDrawers;

import drawers.GraphicsDrawer;
import drawers.lineDrawers.GraphicsLineDrawer;
import drawers.lineDrawers.LineDrawer;
import drawers.wrapperDrawer.GraphicsWrapperDrawer;
import drawers.wrapperDrawer.WrapperDrawer;
import engine.shapes.Diamond;
import engine.shapes.Line;
import engine.shapes.Rectangle;
import engine.shapes.Style;
import engine.wrappers.RectWrapper;
import math.Vector2;
import screen.ScreenConverter;
import screen.ScreenPoint;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.Map;

public class GraphicsRectDrawer extends GraphicsDrawer implements RectDrawer {
    private LineDrawer lineDrawer;
    private WrapperDrawer wrapperDrawer;

    public GraphicsRectDrawer(Graphics2D graphics2D, ScreenConverter screenConverter) {
        super(graphics2D, screenConverter);
        this.lineDrawer = new GraphicsLineDrawer(graphics2D, screenConverter);
        this.wrapperDrawer = new GraphicsWrapperDrawer(graphics2D, screenConverter);
    }

    @Override
    public void draw(Rectangle rectangle) {
        if (rectangle instanceof Diamond) {
            new GraphicsDiamondDrawer(getGraphics2D(), getScreenConverter()).draw(rectangle);
            return;
        }
        ScreenConverter screenConverter = getScreenConverter();
        Graphics2D graphics2D = getGraphics2D();

        AffineTransform transform = new AffineTransform();
//        transform.scale(2, 2);

        Style style = rectangle.getStyle();


        graphics2D.setStroke(style.getStroke());


        RectWrapper wrapper = rectangle.getWrapper();
        double width = wrapper.getWidth();
        double height = wrapper.getHeight();

        ScreenPoint c = screenConverter.r2s(wrapper.getCenter());



        Line top = new Line(wrapper.getTopLeft().getPosition(), wrapper.getTopRight().getPosition());
        Line right = new Line(wrapper.getTopRight().getPosition(), wrapper.getBottomRight().getPosition());
        Line bottom = new Line(wrapper.getBottomRight().getPosition(), wrapper.getBottomLeft().getPosition());
        Line left = new Line(wrapper.getBottomLeft().getPosition(), wrapper.getTopLeft().getPosition());

//        lineDrawer.draw(top);
//        lineDrawer.draw(right);
//        lineDrawer.draw(bottom);
//        lineDrawer.draw(left);
        ScreenPoint sl = screenConverter.r2s(wrapper.getTopLeft().getPosition());

//        ScreenPoint sl = screenConverter.r2s(rectangle.getLeftTop());
//
        if (style.isFilled()) {
            graphics2D.setColor(style.getColor());
            graphics2D.fillRect(sl.getI(), sl.getJ(), screenConverter.r2sDistanceH(width), screenConverter.r2sDistanceV(height));
        }
        graphics2D.setColor(style.getBorderColor());
        graphics2D.drawRect(sl.getI(), sl.getJ(), screenConverter.r2sDistanceH(width), screenConverter.r2sDistanceV(height));

    }

    public LineDrawer getLineDrawer() {
        return lineDrawer;
    }

    public WrapperDrawer getWrapperDrawer() {
        return wrapperDrawer;
    }
}
