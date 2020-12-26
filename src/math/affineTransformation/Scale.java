package math.affineTransformation;

import math.Vector2;

import java.util.ArrayList;
import java.util.List;

public class Scale implements IAffine {

    private double xScale = 1;
    private double yScale = 1;

    public Scale(double xScale, double yScale) {
        this.xScale = xScale;
        this.yScale = yScale;
    }

    public Scale() {
    }

    public Scale(double scale) {
        xScale = scale;
        yScale = scale;
    }

    public Scale(Vector2 scale) {
        xScale = scale.getX();
        yScale = scale.getY();
    }


    @Override
    public Vector2 transform(Vector2 point) {
        double x = point.getX() * xScale;
        double y = point.getY() * yScale;
        return new Vector2(x,y);
    }

    public void setScale(double scale) {
        setXScale(scale);
        setYScale(scale);
    }

    public double getXScale() {
        return xScale;
    }

    public void setXScale(double xScale) {
        this.xScale = xScale;
    }

    public double getYScale() {
        return yScale;
    }

    public void setYScale(double yScale) {
        this.yScale = yScale;
    }
}
