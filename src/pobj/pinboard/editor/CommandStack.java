package pobj.pinboard.editor;

import java.util.Stack;

import pobj.pinboard.editor.commands.Command;

public class CommandStack {

    private final Stack<Command> undo;
    private final Stack<Command> redo;

    public CommandStack() {
        undo = new Stack<>();
        redo = new Stack<>();
    }

    public void addCommand(Command cmd) {
        undo.push(cmd);
        redo.clear();
    }

    public void redo() {
        if (!redo.empty()) {
            Command cmd = redo.pop();
            cmd.execute();
            undo.push(cmd);
        }
    }

    public void undo() {
        if (!undo.empty()) {
            Command cmd = undo.pop();
            cmd.undo();
            redo.push(cmd);
        }
    }

    public boolean isUndoEmpty() {
        return undo.empty();
    }

    public boolean isRedoEmpty() {
        return redo.empty();
    }
}
