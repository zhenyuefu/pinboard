package pobj.pinboard.editor.tools;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import pobj.pinboard.document.Board;
import pobj.pinboard.document.ClipEllipse;
import pobj.pinboard.document.ClipRect;
import pobj.pinboard.editor.EditorInterface;

public class ToolEllipse implements Tool{
    private double left,right,top,bottom;
    private double x,y;

    @Override
    public void press(EditorInterface i, MouseEvent e) {
        x=e.getX();
        y=e.getY();
        left = e.getX();
        top = e.getY();
        right = e.getX();
        bottom = e.getY();
    }

    @Override
    public void drag(EditorInterface i, MouseEvent e) {
        if (e.getX()>x) {
            right = e.getX();
            left = x;
        }else {
            right = x;
            left = e.getX();
        }
        if (e.getY()>y) {
            top = y;
            bottom = e.getY();
        }else {
            bottom = y;
            top = e.getY();
        }
    }

    @Override
    public void release(EditorInterface i, MouseEvent e) {
        Board board = i.getBoard();
        board.getContents().add(new ClipEllipse(left,top,right,bottom, Color.LAWNGREEN));
    }

    @Override
    public void drawFeedback(EditorInterface i, GraphicsContext gc) {
        Board board = i.getBoard();
        board.draw(gc);
        gc.setStroke(Color.LAWNGREEN);
        gc.strokeOval(left,top,right-left,bottom-top);
    }

    @Override
    public String getName(EditorInterface editor) {
        return "Filled ellipse tool";
    }
}
