package pobj.pinboard.editor;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import pobj.pinboard.document.Board;
import pobj.pinboard.editor.tools.*;

import java.io.File;

public class EditorWindow implements EditorInterface {

    private Stage stage;
    private Board board = new Board();
    private Tool currentTool = new ToolSelection();
    private Selection selection = new Selection();

    public EditorWindow(Stage stage) {
        this.stage = stage;
        this.stage.setTitle("Pinboard Editor");

        VBox vbox = new VBox();

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
        closeFileItem.setOnAction(actionEvent -> stage.close());

        fileMenu.getItems().addAll(newFileItem, new SeparatorMenuItem(), closeFileItem);

        // Edit Menu
        Menu editMenu = new Menu("Edit");
        // Tools Menu
        Menu toolsMenu = new Menu("Tools");
        menuBar.getMenus().addAll(fileMenu, toolsMenu);

        // Tool Bar
        ToolBar toolbar = new ToolBar();
        Button boxButton = new Button("Box");

        Button elipsesButton = new Button("Elipses");

        Button imgButton = new Button("Img...");

        Button selectButton = new Button("Select");

        toolbar.getItems().addAll(boxButton, elipsesButton, imgButton, selectButton);

        // Canvas
        Canvas canvas = new Canvas(800, 600);

        Separator separator = new Separator();

        // Status Bar
        Label statusBar = new Label("");
        statusBar.setTranslateX(5);

        // Add All to Vbox
        vbox.getChildren().addAll(menuBar, toolbar, canvas, separator, statusBar);

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

    @Override
    public CommandStack getUndoStack() {
        return null;
    }
}
