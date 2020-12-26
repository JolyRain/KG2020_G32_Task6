package drawers.shapeDrawers;

import drawers.GraphicsDrawer;
import drawers.ellipseDrawers.EllipseDrawer;
import drawers.ellipseDrawers.GraphicsEllipseDrawer;
import drawers.lineDrawers.GraphicsLineDrawer;
import drawers.lineDrawers.LineDrawer;
import drawers.rectDrawers.GraphicsRectDrawer;
import drawers.rectDrawers.RectDrawer;
import drawers.triangleDrawers.GraphicsTriangleDrawer;
import drawers.triangleDrawers.TriangleDrawer;
import engine.shapes.*;
import engine.shapes.Rectangle;
import screen.ScreenConverter;

import java.awt.*;

public class GraphicsShapeDrawer extends GraphicsDrawer implements ShapeDrawer {
    private LineDrawer lineDrawer;
    private RectDrawer rectDrawer;
    private EllipseDrawer ellipseDrawer;
    private TriangleDrawer triangleDrawer;

    public GraphicsShapeDrawer(Graphics2D graphics2D, ScreenConverter screenConverter) {
        super(graphics2D, screenConverter);
        lineDrawer = new GraphicsLineDrawer(graphics2D, screenConverter);
        rectDrawer = new GraphicsRectDrawer(graphics2D, screenConverter);
        ellipseDrawer = new GraphicsEllipseDrawer(graphics2D, screenConverter);
        triangleDrawer = new GraphicsTriangleDrawer(graphics2D, screenConverter);

    }

    public GraphicsShapeDrawer() {
    }

    @Override
    public void draw(IShape shape) {
        shape.setWrapper(shape.getWrapper().transform());
        if (shape instanceof Line) lineDrawer.draw((Line) shape);
        if (shape instanceof Rectangle) rectDrawer.draw((Rectangle) shape);
        if (shape instanceof Ellipse) ellipseDrawer.draw((Ellipse) shape);
        if (shape instanceof Triangle) triangleDrawer.draw((Triangle) shape);
    }
}
