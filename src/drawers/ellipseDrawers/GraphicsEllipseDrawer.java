package drawers.ellipseDrawers;

import drawers.GraphicsDrawer;
import engine.shapes.Ellipse;
import engine.shapes.Line;
import engine.shapes.Style;
import engine.wrappers.RectWrapper;
import screen.ScreenConverter;
import screen.ScreenPoint;

import java.awt.*;
import java.awt.geom.AffineTransform;

import static java.lang.Math.PI;

public class GraphicsEllipseDrawer extends GraphicsDrawer implements EllipseDrawer {
    public GraphicsEllipseDrawer(Graphics2D graphics2D, ScreenConverter screenConverter) {
        super(graphics2D, screenConverter);
    }

    public GraphicsEllipseDrawer() {
    }

    @Override
    public void draw(Ellipse ellipse) {
        ScreenConverter screenConverter = getScreenConverter();
        Graphics2D graphics2D = getGraphics2D();
        RectWrapper wrapper = (RectWrapper) ellipse.getWrapper();

        graphics2D.setColor(Color.BLACK);
        ScreenPoint c = screenConverter.r2s(wrapper.getCenter());
        Style style = ellipse.getStyle();
        graphics2D.setStroke(style.getStroke());

        ScreenPoint topLeft = screenConverter.r2s(wrapper.getTopLeft().getPosition());
        int width = screenConverter.r2sDistanceH(wrapper.getWidth());
        int height = screenConverter.r2sDistanceV(wrapper.getHeight());

        if (style.isFilled()) {
            graphics2D.setColor(style.getColor());
            graphics2D.fillOval(topLeft.getI(), topLeft.getJ(), width, height);
        }
        graphics2D.setColor(style.getBorderColor());
        graphics2D.drawOval(topLeft.getI(), topLeft.getJ(), width, height);
    }
}
