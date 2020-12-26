package engine.shapes;

import engine.ReferencePoint;
import engine.wrappers.IWrapper;
import engine.wrappers.RectWrapper;
import math.Vector2;

//public abstract class Shape implements IShape {
//    private RectWrapper wrapper;
//
//    public Shape(RectWrapper wrapper) {
//        this.wrapper = wrapper;
//    }
//
//    public Shape() {
//    }
//
//    public Shape(Vector2 topLeft, Vector2 bottomRight) {
//        double maxX = Math.max(topLeft.getX(), bottomRight.getX());
//        double maxY = Math.max(topLeft.getY(), bottomRight.getY());
//        double minX = Math.min(topLeft.getX(), bottomRight.getX());
//        double minY = Math.min(topLeft.getX(), bottomRight.getX());
//
//        Vector2 leftTop = new Vector2(minX, maxY);
//        Vector2 rightBottom = new Vector2(maxX, minY);
//
//        this.wrapper = new RectWrapper(leftTop, rightBottom);
//        wrapper.recount();
//    }
//
//
//
//    @Override
//    public boolean isInnerPoint(Vector2 point) {
//        for (ReferencePoint referencePoint : wrapper.getPoints()) {
//            if (referencePoint.isInnerPoint(point)) return true;
//        }
//        return false;
//    }
//
//
//    public RectWrapper getWrapper() {
//        return wrapper;
//    }
//
//
//    public void setWrapper(RectWrapper wrapper) {
//        this.wrapper = wrapper;
//        recount();
//    }
//
//    abstract void recount();
//
//    @Override
//    public void setReferencePoint(ReferencePoint referencePoint) {
//        wrapper.setPoint(referencePoint);
//        recount();
//    }

//}
