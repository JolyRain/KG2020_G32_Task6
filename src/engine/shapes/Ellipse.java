package engine.shapes;

import engine.ReferencePoint;
import engine.wrappers.IWrapper;
import engine.wrappers.RectWrapper;
import math.Vector2;

public class Ellipse implements IShape {
    private RectWrapper wrapper;
    private Vector2 center;
    private double width, height;
    private Style style = new Style();

    public Ellipse(Vector2 center, double width, double height) {
        this.center = center;
        this.width = width;
        this.height = height;
        this.wrapper = new RectWrapper(
                new Vector2(center.getX() - width / 2, center.getY() - height / 2),
                new Vector2(center.getX() + width / 2, center.getY() + height / 2));
    }

    public Vector2 getCenter() {
        return center;
    }

    public void setCenter(Vector2 center) {
        this.center = center;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    @Override
    public boolean isInnerPoint(Vector2 point) {
        for (ReferencePoint referencePoint : wrapper.getPoints()) {
            if (referencePoint.isInnerPoint(point)) return true;
        }
        double x = point.getX(), y = point.getY();
        double a = width / 2, b = height / 2;
        double cx = center.getX(), cy = center.getY();
        return (((x - cx) * (x - cx)) / (a * a) + (((y - cy) * (y - cy)) / (b * b))) <= 1;
    }

    @Override
    public IWrapper getWrapper() {
        return wrapper;
    }

    @Override
    public void setReferencePoint(ReferencePoint referencePoint) {
        wrapper.setPoint(referencePoint);
            recount();
    }

    @Override
    public Style getStyle() {
        return style;
    }

    @Override
    public void setStyle(Style style) {
        this.style = style;
    }

    @Override
    public void setWrapper(IWrapper wrapper) {
        this.wrapper = (RectWrapper) wrapper;
        recount();
    }

    private void recount() {
        setCenter(wrapper.getCenter());
        setWidth(wrapper.getWidth());
        setHeight(wrapper.getHeight());
    }

    @Override
    public void transfer(Vector2 delta) {
//        wrapper.transfer(delta);
        recount();
    }


    public void setWrapper(RectWrapper wrapper) {
        this.wrapper = wrapper;
    }
}
