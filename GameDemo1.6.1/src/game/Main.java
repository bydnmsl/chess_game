package game;

import game.scene.SceneManager;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

import static game.Util.window_height;
import static game.Util.window_width;


public class Main extends Application
{
    public static void main(String[] args)
    {
        launch(args);
    }


    @Override
    public void start(Stage stage) throws Exception
    {
        stage.setWidth(window_width);
        stage.setHeight(window_height);

        //游戏主循环
        AnimationTimer main_loop = new AnimationTimer()
        {
            @Override
            public void handle(long l)
            {
                SceneManager.getInstance().on_update();
                Platform.runLater(()->
                {
                    SceneManager.getInstance().on_render(stage);
                });

            }
        };
        main_loop.start();

        stage.show();

    }




}


