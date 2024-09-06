package game.scene;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class MenuScene extends Scene
{

    //menu_scene 自己维护的布局
    public Pane menu_pane;

    MenuScene()
    {
        //初始化pane
        try
        {
            menu_pane = FXMLLoader.load(getClass().getResource("/fxml/menu.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        scene = new javafx.scene.Scene(menu_pane);


    }

    @Override
    public void on_enter()
    {

    }

    @Override
    public void on_update()
    {

    }

    @Override
    public void on_render()
    {

    }

    @Override
    public void on_exit()
    {

    }


}
