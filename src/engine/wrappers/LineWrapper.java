package engine.wrappers;

import engine.ReferencePoint;
import engine.SupportPoint;
import math.Vector2;
import math.affineTransformation.*;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static engine.SupportPoint.END;
import static engine.SupportPoint.START;

public class LineWrapper implements IWrapper {
    private ReferencePoint start;
    private ReferencePoint end;
    private AffineTransform affineTransform;
    private Map<SupportPoint, ReferencePoint> points;

    public LineWrapper(ReferencePoint start, ReferencePoint end) {
        this.start = start;
        this.end = end;
        Scale scale = new Scale();
        Rotate rotate = new Rotate();
        Transfer transfer = new Transfer();
        affineTransform = new AffineTransform(scale, rotate, transfer);
    }

    public LineWrapper(Vector2 start, Vector2 end) {
        this.start = new ReferencePoint(start, START);
        this.end = new ReferencePoint(end, END);
        Scale scale = new Scale();
        Rotate rotate = new Rotate();
        Transfer transfer = new Transfer();
        affineTransform = new AffineTransform(scale, rotate, transfer);
    }

    public LineWrapper transform() {
        ReferencePoint start = new ReferencePoint(affineTransform.transform(this.start.getPosition()), this.start.getType());
        ReferencePoint end = new ReferencePoint(affineTransform.transform(this.end.getPosition()), this.end.getType());
        return new LineWrapper(start, end);
    }

    @Override
    public AffineTransform getAffineTransform() {
        return affineTransform;
    }

    @Override
    public List<ReferencePoint> getPoints() {
        return new LinkedList<>(Arrays.asList(start, end));
    }

    @Override
    public void setPoint(ReferencePoint point) {
        switch (point.getType()) {
            case START:
                setStart(point);
                break;
            case END:
                setEnd(point);
                break;
        }
    }


    @Override
    public Vector2 getCenter() {
        return new Vector2(
                (start.getX() + end.getX()) / 2,
                (start.getY() + end.getY()) / 2);
    }

    public double length() {
        return start.getPosition().minus(end.getPosition()).length();
    }

    public ReferencePoint getStart() {
        return start;
    }

    public void setStart(ReferencePoint start) {
        this.start = start;
    }

    public ReferencePoint getEnd() {
        return end;
    }

    public void setEnd(ReferencePoint end) {
        this.end = end;
    }

    public void setAffineTransform(AffineTransform affineTransform) {
        this.affineTransform = affineTransform;
    }
}
