package engine.shapes;

import engine.ReferencePoint;
import engine.wrappers.IWrapper;
import engine.wrappers.LineWrapper;
import math.Vector2;

public class Line implements IShape {
    private LineWrapper wrapper;
    private Vector2 start;
    private Vector2 end;
    private Style style = new Style();
    public Line(LineWrapper wrapper, Vector2 start, Vector2 end) {
        this.wrapper = wrapper;
        this.start = start;
        this.end = end;
    }

    public Line(Vector2 start, Vector2 end) {
        this.start = start;
        this.end = end;
        this.wrapper = new LineWrapper(this.start, this.end);
    }

    public LineWrapper getWrapper() {
        return wrapper;
    }

    public void setWrapper(LineWrapper wrapper) {
        this.wrapper = wrapper;
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
        setWrapper((LineWrapper) wrapper);
        recount();
    }

    private void recount() {
        setStart(wrapper.getStart().getPosition());
        setEnd(wrapper.getEnd().getPosition());

    }

    @Override
    public void transfer(Vector2 delta) {
//        wrapper.transfer(delta);
        recount();
    }

    public Vector2 getStart() {
        return start;
    }

    public void setStart(Vector2 start) {
        this.start = start;
    }

    public Vector2 getEnd() {
        return end;
    }

    public void setEnd(Vector2 end) {
        this.end = end;
    }

    @Override
    public boolean isInnerPoint(Vector2 point) {
        for (ReferencePoint referencePoint : wrapper.getPoints()) {
            if (referencePoint.isInnerPoint(point)) return true;
        }
//        return false;
        double x = point.getX(), y = point.getY();
        return false;

    }
}
