package pobj.pinboard.editor.tools;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import pobj.pinboard.document.Board;
import pobj.pinboard.document.ClipImage;
import pobj.pinboard.editor.EditorInterface;

import java.io.File;

public class ToolImage implements Tool {
    private double x, y;
    private File file;
    private Image img;

    public ToolImage(File file) {
        this.file = file;
        img = new Image(file.toURI().toString());
    }

    @Override public void press(EditorInterface i, MouseEvent e) {
        x = e.getX() - img.getWidth() / 2;
        y = e.getY() - img.getHeight() / 2;
    }

    @Override public void drag(EditorInterface i, MouseEvent e) {
        x = e.getX() - img.getWidth() / 2;
        y = e.getY() - img.getHeight() / 2;
    }

    @Override public void release(EditorInterface i, MouseEvent e) {
        Board board = i.getBoard();
        board.getContents().add(new ClipImage(x, y, file));
    }

    @Override public void drawFeedback(EditorInterface i, GraphicsContext gc) {
        Board board = i.getBoard();
        board.draw(gc);
        gc.setGlobalAlpha(0.3);
        gc.drawImage(img, x, y);
        gc.setGlobalAlpha(1);
    }

    @Override public String getName(EditorInterface editor) {
        return "Image Tool";
    }
}
