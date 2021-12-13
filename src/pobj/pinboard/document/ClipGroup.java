package pobj.pinboard.document;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class ClipGroup implements Composite {

    private List<Clip> clips;

    public ClipGroup() {
        this.clips = new ArrayList<>();
    }

    @Override
    public void draw(GraphicsContext ctx) {
        clips.forEach(clip -> clip.draw(ctx));

    }

    @Override
    public double getTop() {
        return clips.stream().mapToDouble(Clip::getTop).min().getAsDouble();
    }

    @Override
    public double getLeft() {
        return clips.stream().mapToDouble(Clip::getLeft).min().getAsDouble();
    }

    @Override
    public double getBottom() {
        return clips.stream().mapToDouble(Clip::getBottom).max().getAsDouble();
    }

    @Override
    public double getRight() {
        return clips.stream().mapToDouble(Clip::getRight).max().getAsDouble();
    }

    @Override
    public void setGeometry(double left, double top, double right, double bottom) {
        // TODO Auto-generated method stub

    }

    @Override
    public void move(double x, double y) {
        clips.forEach(clip -> clip.move(x, y));
    }

    @Override
    public boolean isSelected(double x, double y) {
        for (Clip clip : clips) {
            if (clip.isSelected(x, y)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Color getColor() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setColor(Color c) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Clip copy() {
        ClipGroup cg = new ClipGroup();
        for (Clip clip : clips) {
            cg.addClip(clip.copy());
        }
        return cg;
    }

    @Override
    public List<Clip> getClips() {
        return clips;
    }

    @Override
    public void addClip(Clip toAdd) {
        clips.add(toAdd);
    }

    @Override
    public void removeClip(Clip toRemove) {
        clips.remove(toRemove);
    }

}