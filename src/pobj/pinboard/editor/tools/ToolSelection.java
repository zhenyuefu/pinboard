package pobj.pinboard.editor.tools;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import pobj.pinboard.document.Clip;
import pobj.pinboard.editor.EditorInterface;

public class ToolSelection implements Tool {
    private double initX, initY;
    private double lastX, lastY;

    @Override
    public void press(EditorInterface i, MouseEvent e) {
        if (e.isShiftDown()) {
            i.getSelection().toogleSelect(i.getBoard(), e.getX(), e.getY());
        } else {
            i.getSelection().select(i.getBoard(), e.getX(), e.getY());
        }
        initX = e.getX();
        initY = e.getY();
        lastX = e.getX();
        lastY = e.getY();
    }

    @Override
    public void drag(EditorInterface i, MouseEvent e) {
        lastX = e.getX();
        lastY = e.getY();
    }

    @Override
    public void release(EditorInterface i, MouseEvent e) {
        for (Clip clip : i.getSelection().getContents()) {
            clip.move(lastX - initX, lastY - initY);
        }
        initX = 0;
        initY = 0;
        lastX = 0;
        lastY = 0;
    }

    @Override
    public void drawFeedback(EditorInterface i, GraphicsContext gc) {
        i.getBoard().draw(gc);
        i.getSelection().drawFeedback(gc);
        for (Clip clip : i.getSelection().getContents()) {
            gc.setStroke(Color.BLUEVIOLET);
            double x = lastX - initX;
            double y = lastY - initY;
            gc.strokeRect(clip.getLeft() + x, clip.getTop() + y, clip.getRight() - clip.getLeft(),
                clip.getBottom() - clip.getTop());
        }
    }

    @Override
    public String getName(EditorInterface editor) {
        return "Select Tool";
    }
}
