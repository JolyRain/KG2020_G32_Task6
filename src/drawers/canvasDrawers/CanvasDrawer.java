package drawers.canvasDrawers;

import drawers.GraphicsDrawer;
import drawers.lineDrawers.GraphicsLineDrawer;
import drawers.lineDrawers.LineDrawer;
import drawers.shapeDrawers.GraphicsShapeDrawer;
import drawers.shapeDrawers.ShapeDrawer;
import drawers.wrapperDrawer.GraphicsWrapperDrawer;
import drawers.wrapperDrawer.WrapperDrawer;
import engine.Canvas;
import engine.shapes.IShape;
import engine.shapes.Line;
import math.Vector2;
import screen.ScreenConverter;

import java.awt.*;


public class CanvasDrawer extends GraphicsDrawer implements ICanvasDrawer {
    private ShapeDrawer shapeDrawer;
    private WrapperDrawer wrapperDrawer;

    public CanvasDrawer(Graphics2D graphics2D, ScreenConverter screenConverter) {
        super(graphics2D, screenConverter);
        shapeDrawer = new GraphicsShapeDrawer(graphics2D, screenConverter);
        wrapperDrawer = new GraphicsWrapperDrawer(graphics2D, screenConverter);

    }

    @Override
    public void draw(Canvas canvas) {
        drawAxis(new GraphicsLineDrawer(getGraphics2D(), getScreenConverter()));
        for (IShape shape : canvas.getShapes()) {
            shapeDrawer.draw(shape);
            if (canvas.getSelectShapesMap().get(shape))
                wrapperDrawer.draw(shape.getWrapper());
        }
    }

    private void drawAxis(LineDrawer lineDrawer) {
        ScreenConverter screenConverter = getScreenConverter();
        Line xAxis = new Line(
                new Vector2(-10, 0),
                new Vector2(10, 0));
        Line yAxis = new Line(
                new Vector2(0, 10),
                new Vector2(0, -10));
        lineDrawer.draw(xAxis);
        lineDrawer.draw(yAxis);
    }
}
