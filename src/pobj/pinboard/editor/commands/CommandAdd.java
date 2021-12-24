package pobj.pinboard.editor.commands;

import java.util.ArrayList;
import java.util.List;

import pobj.pinboard.document.Clip;
import pobj.pinboard.editor.EditorInterface;

public class CommandAdd implements Command {
    private EditorInterface editor;
    private List<Clip> clipList = new ArrayList<>();

    public CommandAdd(EditorInterface editor, Clip clip) {
        this.editor = editor;
        clipList.add(clip);
    }

    public CommandAdd(EditorInterface editor, List<Clip> clipList) {
        this.editor = editor;
        this.clipList = clipList;
    }

    @Override
    public void execute() {
        for (Clip clip : clipList) {
            editor.getBoard().addClip(clip);
        }
    }

    @Override
    public void undo() {
        for (Clip clip : clipList) {
            editor.getBoard().removeClip(clip);
        }
    }
}