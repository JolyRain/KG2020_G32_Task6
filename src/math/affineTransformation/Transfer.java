package math.affineTransformation;

import math.Vector2;

public class Transfer implements IAffine {

    private Vector2 vector = new Vector2(0,0);

    public Transfer(Vector2 vector) {
        this.vector = vector;
    }

    public Transfer(double x, double y) {
        this.vector = new Vector2(x, y);
    }

    public Transfer() {
    }

    @Override
    public Vector2 transform(Vector2 point) {
        return new Vector2(point.getX() + vector.getX(), point.getY() + vector.getY());
    }

    public Vector2 getVector() {
        return vector;
    }

    public void setVector(Vector2 vector) {
        this.vector = vector;
    }
}
