package pobj.pinboard.editor.commands;

import java.util.List;

import pobj.pinboard.document.Clip;
import pobj.pinboard.document.ClipGroup;
import pobj.pinboard.editor.EditorInterface;

public class CommandUngroup implements Command {

    private EditorInterface editor;
    private ClipGroup group;
    private List<Clip> clipList;

    public CommandUngroup(EditorInterface editor, ClipGroup group) {
        this.editor = editor;
        this.group = group;
        this.clipList = group.getClips();
    }

    @Override
    public void execute() {
        editor.getBoard().removeClip(group);
        for (Clip clip : clipList) {
            editor.getBoard().addClip(clip);
        }
    }

    @Override
    public void undo() {
        for (Clip clip : clipList) {
            editor.getBoard().removeClip(clip);
        }
        editor.getBoard().addClip(group);
    }

}
