package pobj.pinboard.editor;

public interface Command {

    public void execute();

    public void undo();
}