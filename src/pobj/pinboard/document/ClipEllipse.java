package pobj.pinboard.document;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class ClipEllipse extends AbstractClip {

    public ClipEllipse(double left, double top, double right, double bottom, Color color) {
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
        this.color = color;
    }

    @Override
    public void draw(GraphicsContext ctx) {
        ctx.setFill(color);
        ctx.fillOval(left, top, right - left, bottom - top);
    }

    @Override
    public Clip copy() {
        return new ClipEllipse(left, top, right, bottom, color);
    }
    
}
