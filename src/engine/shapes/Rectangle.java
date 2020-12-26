package engine.shapes;

import engine.ReferencePoint;
import engine.wrappers.IWrapper;
import engine.wrappers.RectWrapper;
import math.Vector2;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Rectangle implements IShape {
    private RectWrapper wrapper;
    private Vector2 leftTop;
    private Vector2 rightBottom;
    private Style style = new Style();

    public Style getStyle() {
        return style;
    }

    public void setStyle(Style style) {
        this.style = style;
    }

    public Rectangle(Vector2 topLeft, Vector2 bottomRight) {
        double maxX = Math.max(topLeft.getX(), bottomRight.getX());
        double maxY = Math.max(topLeft.getY(), bottomRight.getY());
        double minX = Math.min(topLeft.getX(), bottomRight.getX());
        double minY = Math.min(topLeft.getX(), bottomRight.getX());

        this.leftTop = new Vector2(minX, maxY);
        this.rightBottom = new Vector2(maxX, minY);

        this.wrapper = new RectWrapper(leftTop, rightBottom);
    }

    public RectWrapper getWrapper() {
        return wrapper;
    }

    @Override
    public void setWrapper(IWrapper wrapper) {
        this.wrapper = ((RectWrapper) wrapper);
        recount();
    }

    public void setWrapper(RectWrapper wrapper) {
        this.wrapper = wrapper;
    }

    @Override
    public void setReferencePoint(ReferencePoint referencePoint) {
        wrapper.setPoint(referencePoint);
        recount();
    }

    private void recount() {
        setLeftTop(wrapper.getTopLeft().getPosition());
        setRightBottom(wrapper.getBottomRight().getPosition());
    }

    @Override
    public void transfer(Vector2 delta) {

        recount();
    }

    public Vector2 getLeftTop() {
        return leftTop;
    }

    public void setLeftTop(Vector2 leftTop) {
        this.leftTop = leftTop;
    }

    public Vector2 getRightBottom() {
        return rightBottom;
    }

    public void setRightBottom(Vector2 rightBottom) {
        this.rightBottom = rightBottom;
    }

    public double getWidth() {
        return rightBottom.getX() - leftTop.getX();
    }

    public double getHeight() {
        return leftTop.getY() - rightBottom.getY();
    }

    public Vector2 getCenter() {
        return new Vector2(leftTop.getX() + getWidth() * 0.5, leftTop.getY() + getHeight() * 0.5);
    }

    public List<Line> getLines() {
        Line top = new Line(leftTop, new Vector2(leftTop.getX() + getWidth(), leftTop.getY()));
        Line right = new Line(new Vector2(leftTop.getX() + getWidth(), leftTop.getY()), rightBottom);
        Line bottom = new Line(rightBottom, new Vector2(leftTop.getX(), leftTop.getY() - getHeight()));
        Line left = new Line(new Vector2(leftTop.getX(), leftTop.getY() - getHeight()), leftTop);
        return new LinkedList<>(Arrays.asList(top, right, bottom, left));
    }

    @Override
    public boolean isInnerPoint(Vector2 point) {
        for (ReferencePoint referencePoint : wrapper.getPoints()) {
            if (referencePoint.isInnerPoint(point)) return true;
        }
//        return false;
        double x = point.getX(), y = point.getY();
        return leftTop.getX() <= x && leftTop.getY() >= y && rightBottom.getX() >= x && rightBottom.getY() <= y;
    }
}
