package pobj.pinboard.editor;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import pobj.pinboard.document.Board;
import pobj.pinboard.document.Clip;
import pobj.pinboard.editor.tools.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class EditorWindow implements EditorInterface, ClipboardListener {

    private Stage stage;
    private Board board = new Board();
    private MenuItem copyItem, pasteItem, deleteItem;

    private Color currentColor = Color.DARKCYAN;
    private Rectangle currentRect;
    private Tool currentTool = new ToolSelection();
    private Selection selection = new Selection();

    public EditorWindow(Stage stage) {
        this.stage = stage;
        Clipboard.getInstance().addListener(this);
        this.stage.setTitle("Pinboard Editor");

        VBox vbox = new VBox();

        // Canvas
        Canvas canvas = new Canvas(800, 600);

        Separator separator = new Separator();
        // Menu Bar
        MenuBar menuBar = new MenuBar();
        menuBar.setUseSystemMenuBar(true);

        // File Menu
        Menu fileMenu = new Menu("_File");
        fileMenu.setMnemonicParsing(true);
        MenuItem newFileItem = new MenuItem("_New");
        newFileItem.setMnemonicParsing(true);
        newFileItem.setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCombination.SHORTCUT_DOWN));
        newFileItem.setOnAction(actionEvent -> new EditorWindow(new Stage()));
        MenuItem closeFileItem = new MenuItem("Close");
        closeFileItem
            .setAccelerator(new KeyCodeCombination(KeyCode.W, KeyCombination.SHORTCUT_DOWN, KeyCombination.SHIFT_DOWN));
        closeFileItem.setOnAction(actionEvent -> {
            Clipboard.getInstance().removeListener(this);
            stage.close();
        });

        fileMenu.getItems().addAll(newFileItem, new SeparatorMenuItem(), closeFileItem);

        // Edit Menu
        Menu editMenu = new Menu("_Edit");
        editMenu.setMnemonicParsing(true);
        copyItem = new MenuItem("Copy");
        copyItem.setAccelerator(new KeyCodeCombination(KeyCode.C, KeyCombination.SHORTCUT_DOWN));
        copyItem.setOnAction(actionEvent -> {
            if (selection.getContents().isEmpty()) {
                return;
            }
            Clipboard.getInstance().copyToClipboard(selection.getContents());
        });

        pasteItem = new MenuItem("Paste");
        pasteItem.setDisable(true);
        pasteItem.setAccelerator(new KeyCodeCombination(KeyCode.V, KeyCombination.SHORTCUT_DOWN));
        pasteItem.setOnAction(actionEvent -> {
            if (Clipboard.getInstance().isEmpty()) {
                return;
            }
            board.addClip(Clipboard.getInstance().copyFromClipboard());
            board.draw(canvas.getGraphicsContext2D());
        });

        deleteItem = new MenuItem("Delete");
        deleteItem.setAccelerator(new KeyCodeCombination(KeyCode.DELETE));
        deleteItem.setOnAction(actionEvent -> {
            if (selection.getContents().isEmpty()) {
                return;
            }
            board.removeClip(selection.getContents());
            board.draw(canvas.getGraphicsContext2D());
        });
        editMenu.getItems().addAll(copyItem, pasteItem, deleteItem);
        // Tools Menu
        Menu toolsMenu = new Menu("Tools");
        menuBar.getMenus().addAll(fileMenu, editMenu, toolsMenu);

        // Tool Bar
        ToolBar toolbar = new ToolBar();
        Button boxButton = new Button("Box");

        Button elipsesButton = new Button("Elipses");

        Button imgButton = new Button("Img...");

        Button selectButton = new Button("Select");

        toolbar.getItems().addAll(boxButton, elipsesButton, imgButton, selectButton);

        // Color Bar
        ToolBar colorbar = new ToolBar();
        List<Rectangle> listRect = new ArrayList<>();
        listRect.add(new Rectangle(15, 15, Color.ALICEBLUE));
        listRect.add(new Rectangle(15, 15, Color.AQUA));
        listRect.add(new Rectangle(15, 15, Color.BLUEVIOLET));
        listRect.add(new Rectangle(15, 15, Color.GOLDENROD));
        listRect.add(new Rectangle(15, 15, Color.GREEN));
        listRect.add(new Rectangle(15, 15, Color.GREENYELLOW));
        listRect.add(new Rectangle(15, 15, Color.LIGHTPINK));
        listRect.add(new Rectangle(15, 15, Color.LIGHTCYAN));
        listRect.add(new Rectangle(15, 15, Color.SKYBLUE));
        listRect.add(new Rectangle(15, 15, Color.CHOCOLATE));
        listRect.add(new Rectangle(15, 15, Color.RED));
        listRect.add(new Rectangle(15, 15, Color.SILVER));
        listRect.add(new Rectangle(15, 15, Color.YELLOW));
        currentRect = listRect.get(0);
        for (Rectangle rect : listRect) {
            rect.setOnMouseClicked(actionEvent -> {
                currentColor = (Color)rect.getFill();
                for (Clip clip : selection.getContents()) {
                    clip.setColor(currentColor);
                }
                board.draw(canvas.getGraphicsContext2D());
                currentRect.setStroke(Color.TRANSPARENT);
                rect.setStroke(Color.BLACK);
                currentRect = rect;
            });
        }

        colorbar.getItems().addAll(listRect);

        // Status Bar
        Label statusBar = new Label("");
        statusBar.setTranslateX(5);

        // Add All to Vbox
        vbox.getChildren().addAll(menuBar, toolbar, colorbar, canvas, separator, statusBar);

        // Button Action
        boxButton.setOnAction(actionEvent -> {
            setCurrentTool(new ToolRect());
            statusBar.setText(currentTool.getName(this));
        });

        elipsesButton.setOnAction(actionEvent -> {
            setCurrentTool(new ToolEllipse());
            statusBar.setText(currentTool.getName(this));
        });

        imgButton.setOnAction(actionEvent -> {
            FileChooser fc = new FileChooser();
            fc.setTitle("Open Image");
            fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("All Images", "*.*"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"), new FileChooser.ExtensionFilter("GIF", "*.gif"),
                new FileChooser.ExtensionFilter("BMP", "*.bmp"), new FileChooser.ExtensionFilter("PNG", "*.png"));
            File file = fc.showOpenDialog(stage);
            setCurrentTool(new ToolImage(file));
        });

        selectButton.setOnAction(actionEvent -> {
            setCurrentTool(new ToolSelection());
            statusBar.setText(currentTool.getName(this));
        });

        canvas.setOnMousePressed(event -> {
            currentTool.press(this, event);
            currentTool.drawFeedback(this, canvas.getGraphicsContext2D());
        });

        canvas.setOnMouseDragged(event -> {
            currentTool.drag(this, event);
            currentTool.drawFeedback(this, canvas.getGraphicsContext2D());
        });

        canvas.setOnMouseReleased(event -> {
            currentTool.release(this, event);
            board.draw(canvas.getGraphicsContext2D());
            currentTool.drawFeedback(this, canvas.getGraphicsContext2D());
        });

        Scene scene = new Scene(vbox);
        this.stage.setScene(scene);
        this.stage.show();

    }

    public void setCurrentTool(Tool currentTool) {
        this.currentTool = currentTool;

    }

    @Override
    public Board getBoard() {
        return board;
    }

    @Override
    public Selection getSelection() {
        return selection;
    }

    public Color getCurrentColor() {
        return currentColor;
    }

    @Override
    public CommandStack getUndoStack() {
        return null;
    }

    @Override
    public void clipboardChanged() {
        Clipboard clipboard = Clipboard.getInstance();
        if (clipboard.isEmpty())
            pasteItem.setDisable(true);
        else
            pasteItem.setDisable(false);
    }
}
