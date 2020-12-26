package math.affineTransformation;

import math.Vector2;

public class AffineTransform implements IAffine {

    private Scale scale;
    private Rotate rotate;
    private Transfer transfer;

    public AffineTransform(Scale scale, Rotate rotate, Transfer transfer) {
        this.scale = scale;
        this.rotate = rotate;
        this.transfer = transfer;
    }
    public void setAngle(double angle) {
        rotate.setAngle(angle);
    }

    public Scale getScale() {
        return scale;
    }

    public void setScale(Scale scale) {
        this.scale = scale;
    }

    public Rotate getRotate() {
        return rotate;
    }

    public void setRotate(Rotate rotate) {
        this.rotate = rotate;
    }

    public Transfer getTransfer() {
        return transfer;
    }

    public void setTransfer(Transfer transfer) {
        this.transfer = transfer;
    }

    @Override
    public Vector2 transform(Vector2 point) {
        return transfer.transform(rotate.transform(scale.transform(point)));
    }
}
