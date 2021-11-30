package pobj.pinboard.editor.tools;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import pobj.pinboard.editor.EditorInterface;

public class ToolMouse implements Tool {
    @Override
    public void press(EditorInterface i, MouseEvent e) {

    }

    @Override
    public void drag(EditorInterface i, MouseEvent e) {

    }

    @Override
    public void release(EditorInterface i, MouseEvent e) {

    }

    @Override
    public void drawFeedback(EditorInterface i, GraphicsContext gc) {

    }

    @Override
    public String getName(EditorInterface editor) {
        return "";
    }
}
