package game.controller;

import game.scene.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class MenuController {

    @FXML
    private ImageView local_btn;

    @FXML
    private ImageView ai_btn;

    @FXML
    private ImageView net_btn;

    @FXML
    private ImageView escape_btn;

    @FXML
    void localGame(MouseEvent event) {
        SceneManager.getInstance().switch_scene(SceneManager.SceneType.GameScene);
    }

    @FXML
    void localEntered(MouseEvent event) {
        local_btn.setOpacity(0.8);
    }

    @FXML
    void localExited(MouseEvent event) {
        local_btn.setOpacity(1.0);
    }

    @FXML
    void aiGame(MouseEvent event) {
    }

    @FXML
    void aiEntered(MouseEvent event) {
        ai_btn.setOpacity(0.8);
    }

    @FXML
    void aiExited(MouseEvent event) {
        ai_btn.setOpacity(1.0);
    }

    @FXML
    void netGame(MouseEvent event) {

    }

    @FXML
    void netEntered(MouseEvent event) {
        net_btn.setOpacity(0.8);
    }

    @FXML
    void netExited(MouseEvent event) {
        net_btn.setOpacity(1.0);
    }

    @FXML
    void escGame(MouseEvent event) {

    }

    @FXML
    void escEntered(MouseEvent event) {
        escape_btn.setOpacity(0.8);
    }

    @FXML
    void escExited(MouseEvent event) {
        escape_btn.setOpacity(1.0);
    }
}