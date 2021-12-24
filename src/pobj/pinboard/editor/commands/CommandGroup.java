package pobj.pinboard.editor.commands;

import java.util.List;

import pobj.pinboard.document.Clip;
import pobj.pinboard.document.ClipGroup;
import pobj.pinboard.editor.EditorInterface;

public class CommandGroup implements Command {
    private EditorInterface editor;
    private List<Clip> clipList;
    private ClipGroup group;

    public CommandGroup(EditorInterface editor, List<Clip> toGroup) {
        this.editor = editor;
        this.clipList = toGroup;
        group = new ClipGroup();
        for (Clip clip : toGroup) {
            group.addClip(clip);
        }
    }

    @Override
    public void execute() {
        for (Clip clip : clipList) {
            editor.getBoard().removeClip(clip);
        }
        editor.getBoard().addClip(group);
    }

    @Override
    public void undo() {
        editor.getBoard().removeClip(group);
        for (Clip clip : clipList) {
            editor.getBoard().addClip(clip);
        }
    }
}
