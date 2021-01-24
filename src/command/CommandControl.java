package command;

import java.util.Stack;

public class CommandControl {
    private final Stack<CommandInterFace> undoList = new Stack<>();
    private final Stack<CommandInterFace> redoList = new Stack<>();

    public void addToUndo(CommandInterFace command){
        undoList.push(command);
    }

    public Stack<CommandInterFace> getUndoList() {
        return undoList;
    }

    public Stack<CommandInterFace> getRedoList() {
        return redoList;
    }
}
