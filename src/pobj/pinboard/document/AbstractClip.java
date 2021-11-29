package pobj.pinboard.document;

import javafx.scene.paint.Color;

public abstract class AbstractClip implements Clip {

    protected double left, top, right, bottom;
    protected Color color;

    @Override
    public double getTop() {
        return top;
    }

    @Override
    public double getLeft() {
        return left;
    }

    @Override
    public double getBottom() {
        return bottom;
    }

    @Override
    public double getRight() {
        return right;
    }

    @Override
    public void setGeometry(double left, double top, double right, double bottom) {
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
    }

    @Override
    public void move(double x, double y) {
        this.left += x;
        this.top += y;
        this.right += x;
        this.bottom += y;
    }

    @Override
    public boolean isSelected(double x, double y) {
        double cx = (left + right) / 2;
        double cy = (top + bottom) / 2;
        double rx = (right - left) / 2;
        double ry = (bottom - top) / 2;
        return (((x - cx) / rx) * ((x - cx) / rx) + ((y - cy) / ry) * ((y - cy) / ry) <= 1);
    }

    @Override
    public void setColor(Color c) {
        this.color = c;
    }

    @Override
    public Color getColor() {
        return color;
    }

}
