package engine.wrappers;

import engine.ReferencePoint;
import engine.SupportPoint;
import math.Vector2;
import math.affineTransformation.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static engine.SupportPoint.*;

public class RectWrapper implements IWrapper {
    private ReferencePoint topLeft;
    private ReferencePoint bottomRight;
    private AffineTransform affineTransform;
    private Map<SupportPoint, ReferencePoint> points;

    public RectWrapper(Vector2 topLeft, Vector2 bottomRight) {
        double maxX = Math.max(topLeft.getX(), bottomRight.getX());
        double maxY = Math.max(topLeft.getY(), bottomRight.getY());
        double minX = Math.min(topLeft.getX(), bottomRight.getX());
        double minY = Math.min(topLeft.getX(), bottomRight.getX());
        points = new HashMap<>();

        Scale scale = new Scale();
        Rotate rotate = new Rotate();
        Transfer transfer = new Transfer();
        affineTransform = new AffineTransform(scale, rotate, transfer);
        this.topLeft = new ReferencePoint(new Vector2(minX, maxY), TOP_LEFT);
        this.bottomRight = new ReferencePoint(new Vector2(maxX, minY), BOTTOM_RIGHT);
        recount();
    }

    public RectWrapper() {
        this.topLeft = new ReferencePoint(new Vector2(-1,1), TOP_LEFT);
        this.bottomRight = new ReferencePoint(new Vector2(1, -1), BOTTOM_RIGHT);
        points = new HashMap<>();

        Scale scale = new Scale();
        Rotate rotate = new Rotate();
        Transfer transfer = new Transfer();
        affineTransform = new AffineTransform(scale, rotate, transfer);
        recount();
    }

    public RectWrapper transform() {
        RectWrapper rectWrapper = new RectWrapper();
        for (ReferencePoint rp : getPoints()) {
            rectWrapper.setPoint(new ReferencePoint(affineTransform.transform(rp.getPosition()), rp.getType()));
        }
        return rectWrapper;
    }


    @Override
    public void setPoint(ReferencePoint point) {
        switch (point.getType()) {
            case TOP_LEFT:
                setTopLeft(point);
                break;
            case TOP_MIDDLE:
                setTopMiddle(point);
                break;
            case TOP_RIGHT:
                setTopRight(point);
                break;
            case LEFT_MIDDLE:
                setLeftMiddle(point);
                break;
            case RIGHT_MIDDLE:
                setRightMiddle(point);
                break;
            case BOTTOM_LEFT:
                setBottomLeft(point);
                break;
            case BOTTOM_MIDDLE:
                setBottomMiddle(point);
                break;
            case BOTTOM_RIGHT:
                setBottomRight(point);
                break;
        }

    }

    public void recount() {
        double maxX = Math.max(maxX(topLeft), maxX(bottomRight));
        double maxY = Math.max(maxY(topLeft), maxY(bottomRight));
        double minX = Math.min(minX(topLeft), minX(bottomRight));
        double minY = Math.max(minY(topLeft), minY(bottomRight));

        ReferencePoint topMiddle, topRight;
        ReferencePoint leftMiddle, rightMiddle;
        ReferencePoint bottomLeft, bottomMiddle;

//        this.topLeft = new ReferencePoint(new Vector2(minX, maxY), TOP_LEFT);
//        this.bottomRight = new ReferencePoint(new Vector2(maxX, minY), BOTTOM_RIGHT);

        topMiddle = new ReferencePoint(new Vector2(topLeft.getX() + getWidth() / 2, topLeft.getY()), TOP_MIDDLE);
        topRight = new ReferencePoint(new Vector2(topLeft.getX() + getWidth(), topLeft.getY()), TOP_RIGHT);

        leftMiddle = new ReferencePoint(new Vector2(topLeft.getX(), topLeft.getY() - getHeight() / 2), LEFT_MIDDLE);
        rightMiddle = new ReferencePoint(new Vector2(bottomRight.getX(), bottomRight.getY() + getHeight() / 2), RIGHT_MIDDLE);

        bottomLeft = new ReferencePoint(new Vector2(bottomRight.getX() - getWidth(), bottomRight.getY()), BOTTOM_LEFT);
        bottomMiddle = new ReferencePoint(new Vector2(bottomRight.getX() - getWidth() / 2, bottomRight.getY()), BOTTOM_MIDDLE);

        points.put(TOP_LEFT, this.topLeft);
        points.put(TOP_MIDDLE, topMiddle);
        points.put(TOP_RIGHT, topRight);
        points.put(LEFT_MIDDLE, leftMiddle);
        points.put(RIGHT_MIDDLE, rightMiddle);
        points.put(BOTTOM_LEFT, bottomLeft);
        points.put(BOTTOM_MIDDLE, bottomMiddle);
        points.put(BOTTOM_RIGHT, this.bottomRight);
    }

    private double maxX(ReferencePoint point) {
        double max = point.getX();
        for (ReferencePoint rp : getPoints()) {
            if (rp.getX() > max) {
                max = rp.getX();
            }
        }
        return max;
    }


    private double maxY(ReferencePoint point) {
        double max = point.getY();
        for (ReferencePoint rp : getPoints()) {
            if (rp.getY() > max) {
                max = rp.getY();
            }
        }
        return max;
    }


    private double minX(ReferencePoint point) {
        double min = point.getX();
        for (ReferencePoint rp : getPoints()) {
            if (rp.getX() < min) {
                min = rp.getX();
            }
        }
        return min;
    }


    private double minY(ReferencePoint point) {
        double min = point.getY();
        for (ReferencePoint rp : getPoints()) {
            if (rp.getY() > min) {
                min = rp.getY();
            }
        }
        return min;
    }

    @Override
    public AffineTransform getAffineTransform() {
        return affineTransform;
    }

    public List<ReferencePoint> getPoints() {
        List<ReferencePoint> points = new LinkedList<>();
        this.points.forEach((supportPoint, point) -> points.add(point));
        return points;
    }


    public double getWidth() {
        return bottomRight.getX() - topLeft.getX();
    }

    public double getHeight() {
        return topLeft.getY() - bottomRight.getY();
    }

    public ReferencePoint getTopLeft() {
        return topLeft;
    }

    public void setTopLeft(ReferencePoint topLeft) {
        this.topLeft = topLeft;
        points.put(TOP_LEFT, topLeft);
        recount();
    }

    public ReferencePoint getTopMiddle() {
        return points.get(TOP_MIDDLE);
    }

    public void setTopMiddle(ReferencePoint topMiddle) {
        Vector2 oldPosition = getTopMiddle().getPosition();
        Vector2 delta = topMiddle.getPosition().minus(oldPosition);
        Vector2 newPosition = new Vector2(topLeft.getX(), oldPosition.getY() + delta.getY());
        points.put(TOP_MIDDLE, new ReferencePoint(new Vector2(oldPosition.getX(), topMiddle.getY()), TOP_MIDDLE));
        setTopLeft(new ReferencePoint(newPosition, TOP_LEFT));
    }

    public ReferencePoint getTopRight() {
        return points.get(TOP_RIGHT);
    }

    public void setTopRight(ReferencePoint topRight) {
        Vector2 oldPosition = getTopRight().getPosition();
        Vector2 delta = topRight.getPosition().minus(oldPosition);
        points.put(TOP_RIGHT, topRight);
        setTopLeft(new ReferencePoint(new Vector2(topLeft.getX(), topLeft.getY() + delta.getY()), TOP_LEFT));
        setBottomRight(new ReferencePoint(new Vector2(bottomRight.getX() + delta.getX(), bottomRight.getY()), BOTTOM_RIGHT));
    }

    public ReferencePoint getLeftMiddle() {
        return points.get(LEFT_MIDDLE);
    }

    public void setLeftMiddle(ReferencePoint leftMiddle) {
        Vector2 oldPosition = getLeftMiddle().getPosition();
        Vector2 delta = leftMiddle.getPosition().minus(oldPosition);
        Vector2 newPosition = new Vector2(oldPosition.getX() + delta.getX(), topLeft.getY());
        points.put(LEFT_MIDDLE, new ReferencePoint(new Vector2(leftMiddle.getX(), oldPosition.getY()), LEFT_MIDDLE));
        setTopLeft(new ReferencePoint(newPosition, TOP_LEFT));
    }

    public ReferencePoint getRightMiddle() {
        return points.get(RIGHT_MIDDLE);
    }

    public void setRightMiddle(ReferencePoint rightMiddle) {
        Vector2 oldPosition = getRightMiddle().getPosition();
        Vector2 delta = rightMiddle.getPosition().minus(oldPosition);
        Vector2 newPosition = new Vector2(oldPosition.getX() + delta.getX(), bottomRight.getY());
        points.put(RIGHT_MIDDLE, new ReferencePoint(new Vector2(rightMiddle.getX(), oldPosition.getY()), RIGHT_MIDDLE));
        setBottomRight(new ReferencePoint(newPosition, BOTTOM_RIGHT));
    }

    public ReferencePoint getBottomLeft() {
        return points.get(BOTTOM_LEFT);
    }

    public void setBottomLeft(ReferencePoint bottomLeft) {
        Vector2 oldPosition = getBottomLeft().getPosition();
        Vector2 delta = bottomLeft.getPosition().minus(oldPosition);
        setTopLeft(new ReferencePoint(new Vector2(topLeft.getX() + delta.getX(), topLeft.getY()), TOP_LEFT));
        setBottomRight(new ReferencePoint(new Vector2(bottomRight.getX(), bottomRight.getY() + delta.getY()), BOTTOM_RIGHT));
        points.put(BOTTOM_LEFT, bottomLeft);
    }

    public ReferencePoint getBottomMiddle() {
        return points.get(BOTTOM_MIDDLE);
    }

    public void setBottomMiddle(ReferencePoint bottomMiddle) {
        Vector2 oldPosition = getBottomMiddle().getPosition();
        Vector2 delta = bottomMiddle.getPosition().minus(oldPosition);
        Vector2 newPosition = new Vector2(bottomRight.getX(), oldPosition.getY() + delta.getY());
        points.put(BOTTOM_MIDDLE, new ReferencePoint(new Vector2(oldPosition.getX(), bottomMiddle.getY()), BOTTOM_MIDDLE));
        setBottomRight(new ReferencePoint(newPosition, BOTTOM_RIGHT));
    }

    public ReferencePoint getBottomRight() {
        return bottomRight;
    }

    public void setBottomRight(ReferencePoint bottomRight) {
        this.bottomRight = bottomRight;
        points.put(BOTTOM_RIGHT, bottomRight);
        recount();
    }

    public Vector2 getCenter() {
        return new Vector2(topLeft.getX() + getWidth() / 2, topLeft.getY() - getHeight() / 2);
    }
}
