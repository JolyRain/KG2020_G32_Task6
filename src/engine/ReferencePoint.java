package engine;

import math.Vector2;

public class ReferencePoint {
    private double radius = 0.2;
    private Vector2 position;
    private SupportPoint type;

    public ReferencePoint(Vector2 position, SupportPoint type) {
        this.position = position;
        this.type = type;
    }

    public SupportPoint getType() {
        return type;
    }

//    public ReferencePoint(Vector2 position) {
//        this.position = position;
//    }

    public void setType(SupportPoint type) {
        this.type = type;
    }

    public boolean isInnerPoint(Vector2 point) {
        double x = point.getX(), y = point.getY();
        double a = position.getX(), b = position.getY();
        return (x - a) * (x - a) + (y - b) * (y - b) <= radius * radius;
    }

    public double getX() {
        return position.getX();
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public double getY() {
        return position.getY();
    }

    @Override
    public String toString() {
        return "ReferencePoint{" +
                "position=" + position +
                '}';
    }
}
