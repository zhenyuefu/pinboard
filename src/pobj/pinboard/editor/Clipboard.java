package pobj.pinboard.editor;

import java.util.ArrayList;
import java.util.List;
import pobj.pinboard.document.Clip;

public class Clipboard {

    private static Clipboard clipboard = new Clipboard();
    private List<Clip> listClips = new ArrayList<>();
    private List<ClipboardListener> listeners = new ArrayList<>();

    private Clipboard() {

    }

    public static Clipboard getInstance() {
        return clipboard;
    }

    public void copyToClipboard(List<Clip> clips) {
        clear();
        for (Clip clip : clips) {
            listClips.add(clip.copy());
        }
        changed();
    }

    public List<Clip> copyFromClipboard() {
        List<Clip> list = new ArrayList<>();
        for (Clip clip : listClips) {
            list.add(clip.copy());
        }
        changed();
        return list;
    }

    public void clear() {
        listClips.clear();
        changed();
    }

    public boolean isEmpty() {
        return listClips.isEmpty();
    }

    public void changed() {
        for (var c : listeners) {
            c.clipboardChanged();
        }
    }

    public void addListener(ClipboardListener listener) {
        listeners.add(listener);
    }

    public void removeListener(ClipboardListener listener) {
        listeners.remove(listener);
    }

}