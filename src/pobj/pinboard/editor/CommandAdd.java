package pobj.pinboard.editor;

import java.util.ArrayList;
import java.util.List;

import pobj.pinboard.document.Clip;

public class CommandAdd implements Command {
    private EditorInterface _editor;
    private List<Clip> _clipList = new ArrayList<>();

    public CommandAdd(EditorInterface editor, Clip clip) {
        _editor = editor;
        _clipList.add(clip);
    }

    public CommandAdd(EditorInterface editor, List<Clip> clipList) {
        _editor = editor;
        _clipList = clipList;

    }

    @Override
    public void execute() {
        for (Clip clip : _clipList) {
            _editor.getBoard().addClip(clip);
        }
    }

    @Override
    public void undo() {
        for (Clip clip : _clipList) {
            _editor.getBoard().removeClip(clip);
        }
    }
}