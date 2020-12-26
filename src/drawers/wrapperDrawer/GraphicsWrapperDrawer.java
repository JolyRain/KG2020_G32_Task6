package drawers.wrapperDrawer;

import drawers.GraphicsDrawer;
import engine.shapes.Line;
import engine.wrappers.IWrapper;
import engine.ReferencePoint;
import engine.wrappers.LineWrapper;
import engine.wrappers.RectWrapper;
import org.w3c.dom.css.Rect;
import screen.ScreenConverter;
import screen.ScreenPoint;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class GraphicsWrapperDrawer extends GraphicsDrawer implements WrapperDrawer {

    public GraphicsWrapperDrawer(Graphics2D graphics2D, ScreenConverter screenConverter) {
        super(graphics2D, screenConverter);
    }

    public GraphicsWrapperDrawer() {
    }

    @Override
    public void draw(IWrapper wrapper) {
        ScreenConverter sc = getScreenConverter();
        Graphics2D graphics2D = getGraphics2D();




        drawLines(wrapper);
        for (ReferencePoint point : wrapper.getPoints()) {
            drawReferencePoint(point);
            if (first != null && second != null)
                drawDottedLine(first, second);
        }

    }
    private final Color color =new Color(200, 0, 40);

    private void drawReferencePoint(ReferencePoint point) {
        ScreenConverter sc = getScreenConverter();
        Graphics2D graphics2D = getGraphics2D();
        graphics2D.setColor(color);
        int rH = sc.r2sDistanceH(point.getRadius());
        int rV = sc.r2sDistanceV(point.getRadius());
        int r = rH + rV;
        ScreenPoint center = sc.r2s(point.getPosition());
        graphics2D.fillOval(center.getI() - rH, center.getJ() - rV, r, r);
    }

    private ScreenPoint first, second;

    private void drawDottedLine(ScreenPoint p1, ScreenPoint p2) {
        Graphics2D g2d = getGraphics2D();

        int x1 = p1.getI();
        int y1 = p1.getJ();
        int x2 = p2.getI();
        int y2 = p2.getJ();
        Stroke buf = g2d.getStroke();
        g2d.setColor(color);
        //set the stroke of the copy, not the original
        Stroke dashed = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{20}, 0);
        g2d.setStroke(dashed);
        g2d.drawLine(x1, y1, x2, y2);
        //gets rid of the copy
        g2d.setStroke(buf);
    }

    private void drawDottedLine(Line line) {
        ScreenConverter sc = getScreenConverter();
        drawDottedLine(sc.r2s(line.getStart()), sc.r2s(line.getEnd()));
    }

    private void drawLines(IWrapper wrapper) {
        ScreenConverter sc = getScreenConverter();
        if (wrapper instanceof LineWrapper) {
            LineWrapper lineWrapper = (LineWrapper) wrapper;
            drawDottedLine(sc.r2s(lineWrapper.getStart().getPosition()), sc.r2s(lineWrapper.getEnd().getPosition()));
        } else if (wrapper instanceof RectWrapper) {
            RectWrapper rectWrapper = (RectWrapper) wrapper;
            Line top = new Line(rectWrapper.getTopLeft().getPosition(), rectWrapper.getTopRight().getPosition());
            Line right = new Line(rectWrapper.getTopRight().getPosition(), rectWrapper.getBottomRight().getPosition());
            Line bottom = new Line(rectWrapper.getBottomRight().getPosition(), rectWrapper.getBottomLeft().getPosition());
            Line left = new Line(rectWrapper.getBottomLeft().getPosition(), rectWrapper.getTopLeft().getPosition());

            drawDottedLine(top);
            drawDottedLine(right);
            drawDottedLine(bottom);
            drawDottedLine(left);

        }
    }


}
