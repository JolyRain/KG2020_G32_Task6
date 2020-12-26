package engine.shapes;

import engine.ReferencePoint;
import engine.wrappers.IWrapper;
import math.Vector2;

public interface IShape {
    boolean isInnerPoint(Vector2 point);

    IWrapper getWrapper();

   void setReferencePoint(ReferencePoint referencePoint);
Style getStyle();
void setStyle(Style style);
   void setWrapper(IWrapper wrapper);
   void transfer(Vector2 delta);
}
