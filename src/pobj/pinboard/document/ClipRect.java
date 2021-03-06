package pobj.pinboard.document;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class ClipRect implements Clip {

    private double left, top, right, bottom;
    private Color color;

    public ClipRect(double left, double top, double right, double bottom, Color color) {
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
        this.color = color;
    }

    @Override
    public void draw(GraphicsContext ctx) {
        ctx.setFill(color);
        ctx.fillRect(left, top, right-left, bottom-top);
    }

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
        return (left < x && right > x && top < y && bottom > y);
    }

    @Override
    public void setColor(Color c) {
        this.color = c;
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public Clip copy() {
        return new ClipRect(left, top, right, bottom, color);
    }
}
