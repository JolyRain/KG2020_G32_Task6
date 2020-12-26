package math.affineTransformation;

import math.Vector2;

public class Rotate implements IAffine {

    private double angle;
    private double offsetX;
    private double offsetY;

    public Rotate(double angle) {
        this.angle = angle;
    }

    public Rotate() {
    }

    @Override
    public Vector2 transform(Vector2 point) {
        Vector2 buf = new Vector2(point.getX() - offsetX, point.getY() - offsetY);
        double x = (buf.getX() * Math.cos(angle) + buf.getY() * Math.sin(angle));
        double y = (buf.getX() * -Math.sin(angle) + buf.getY() * Math.cos(angle));
        return new Vector2(x + offsetX, y + offsetY);
    }

    public double getOffsetX() {
        return offsetX;
    }

    public void setOffsetX(double offsetX) {
        this.offsetX = offsetX;
    }

    public double getOffsetY() {
        return offsetY;
    }

    public void setOffsetY(double offsetY) {
        this.offsetY = offsetY;
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }
}
