package pobj.pinboard.editor.tools;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import pobj.pinboard.document.Board;
import pobj.pinboard.document.ClipRect;
import pobj.pinboard.editor.EditorInterface;

public class ToolRect implements Tool{
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
        board.getContents().add(new ClipRect(left,top,right,bottom,Color.BLUE));
    }

    @Override
    public void drawFeedback(EditorInterface i, GraphicsContext gc) {
        Board board = i.getBoard();
        board.draw(gc);
        gc.setStroke(Color.BLUE);
        gc.strokeRect(left,top,right-left,bottom-top);
    }

    @Override
    public String getName(EditorInterface editor) {
        return "Filled rectangle tool";
    }
}
