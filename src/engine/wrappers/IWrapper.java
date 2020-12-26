package engine.wrappers;

import engine.ReferencePoint;
import math.Vector2;
import math.affineTransformation.AffineTransform;
import math.affineTransformation.IAffine;

import java.util.List;

public interface IWrapper {

    AffineTransform getAffineTransform();

    List<ReferencePoint> getPoints();

    void setPoint(ReferencePoint point);

    IWrapper transform();

    Vector2 getCenter();

}
