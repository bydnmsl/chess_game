package game.scene;

import javafx.stage.Stage;



public class SceneManager
{
    public static SceneManager getInstance()
    {
        return instance;
    }

    private SceneManager()
    {

    }

    public enum SceneType
    {
        MenuScene,
        GameScene
    }

    public void on_update()
    {

    }

    //将当前场景的内容设置到舞台上
    public void on_render(Stage stage)
    {
        current_scene.on_render();
        if(stage.getScene() != current_scene.scene)
        {
            stage.setScene(current_scene.scene);
        }
    }

    //切换场景
    public void switch_scene(SceneType type)
    {
        //切换场景
        switch (type)
        {
            case MenuScene:
                current_scene = menu_scene;
                current_scene_type = SceneType.MenuScene;
                break;
            case GameScene:
                current_scene = game_scene;
                current_scene_type = SceneType.GameScene;
                break;
        }
        //最后执行场景的进入函数
        current_scene.on_enter();
    }



    public SceneType current_scene_type = SceneType.MenuScene;
    private static final SceneManager instance = new SceneManager();


    private Scene menu_scene = new MenuScene();
    private Scene game_scene = new GameScene();

    private Scene current_scene = menu_scene;
}