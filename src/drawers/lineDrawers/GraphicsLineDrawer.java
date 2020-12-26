package drawers.lineDrawers;

import drawers.GraphicsDrawer;
import engine.shapes.Line;
import engine.shapes.Style;
import engine.wrappers.LineWrapper;
import engine.wrappers.RectWrapper;
import screen.ScreenConverter;
import screen.ScreenPoint;

import java.awt.*;

public class GraphicsLineDrawer extends GraphicsDrawer implements LineDrawer {

    public GraphicsLineDrawer(Graphics2D graphics2D, ScreenConverter screenConverter) {
        super(graphics2D, screenConverter);
    }

    public GraphicsLineDrawer() {
    }

    @Override
    public void draw(Line line) {
        ScreenConverter screenConverter = getScreenConverter();
        Graphics2D graphics2D = getGraphics2D();


        LineWrapper wrapper = line.getWrapper();
//        wrapper.transform();

        Style style = line.getStyle();
        graphics2D.setStroke(style.getStroke());
        ScreenPoint start = screenConverter.r2s(wrapper.getStart().getPosition());
        ScreenPoint end = screenConverter.r2s(wrapper.getEnd().getPosition());
        graphics2D.setColor(style.getBorderColor());
        graphics2D.drawLine(start.getI(), start.getJ(), end.getI(), end.getJ());

    }
}
