package pobj.pinboard.editor;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pobj.pinboard.document.Board;

public class EditorWindow {

    private Stage stage;
    private Board board;

    public EditorWindow(Stage stage) {
        this.stage = stage;
        this.stage.setTitle("Pinboard Editor");

        VBox vbox = new VBox();

        MenuBar menuBar = new MenuBar();
        vbox.getChildren().add(menuBar);

        Menu fileMenu = new Menu("File");
        Menu editMenu = new Menu("Edit");
        Menu toolsMenu = new Menu("Tools");
        menuBar.getMenus().addAll(fileMenu, editMenu, toolsMenu);

        ToolBar toolbar = new ToolBar();

        Scene scene = new Scene(vbox);
        this.stage.setScene(scene);
        this.stage.show();

    }

}
