package pobj.pinboard.editor;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import pobj.pinboard.document.Board;
import pobj.pinboard.document.Clip;

public class Selection {

    private List<Clip> clipsSelection;

    public Selection() {
        this.clipsSelection = new ArrayList<Clip>();
    }

    public void select(Board board, double x, double y) {
        clear();
        List<Clip> clips = board.getContents();
        // Make sure that the top-clip is selected first.
        for (int i = clips.size() - 1; i >= 0; i--) {
            Clip clip = clips.get(i);
            if (clip.isSelected(x, y)) {
                clipsSelection.add(clip);
                return;
            }
        }
    }

    public void toogleSelect(Board board, double x, double y) {
        List<Clip> clips = board.getContents();
        for (Clip clip : clips) {
            if (clip.isSelected(x, y)) {
                if (clipsSelection.contains(clip)) {
                    clipsSelection.remove(clip);
                } else {
                    clipsSelection.add(clip);
                }
                return;
            }
        }
    }

    public void clear() {
        clipsSelection.clear();
    }

    public List<Clip> getContents() {
        return clipsSelection;
    }

    public void drawFeedback(GraphicsContext gc) {
        for (Clip clip : clipsSelection) {
            double left = clip.getLeft();
            double top = clip.getTop();
            double right = clip.getRight();
            double bottom = clip.getBottom();
            gc.setStroke(Color.BLACK);
            gc.strokeRect(left, top, right - left, bottom - top);
        }

    }
}
