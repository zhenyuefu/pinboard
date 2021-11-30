package pobj.pinboard.document;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ClipImage implements Clip {

    private double left, top, right, bottom;
    private File filename;
    private Image image;

    public ClipImage(double left, double top, File filename) {
        this.left = left;
        this.top = top;
        this.filename = filename;
        try {
            image = new Image(new FileInputStream(filename.getAbsolutePath()));
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        }
        right = left + image.getWidth();
        bottom = top + image.getHeight();
    }

    @Override public void draw(GraphicsContext ctx) {
        ctx.drawImage(image, left, top);
    }

    @Override public double getTop() {
        return top;
    }

    @Override public double getLeft() {
        return left;
    }

    @Override public double getBottom() {
        return bottom;
    }

    @Override public double getRight() {
        return right;
    }

    @Override public void setGeometry(double left, double top, double right, double bottom) {
        this.left = left;
        this.top = top;
        this.right = left + image.getWidth();
        this.bottom = top + image.getHeight();
    }

    @Override public void move(double x, double y) {
        this.left += x;
        this.top += y;
        this.right = left + image.getWidth();
        this.bottom = top + image.getHeight();
    }

    @Override public boolean isSelected(double x, double y) {
        return (left < x && right > x && top < y && bottom > y);
    }

    @Override public Color getColor() {
        throw new UnsupportedOperationException();
    }

    @Override public void setColor(Color c) {
        throw new UnsupportedOperationException();
    }

    @Override public Clip copy() {
        return new ClipImage(left, top, filename);
    }
}
