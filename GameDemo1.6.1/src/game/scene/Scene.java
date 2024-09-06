package game.scene;

public abstract class Scene
{
    public Scene(){};
    abstract public void on_enter();
    abstract public void on_update();
    abstract public void on_render();
    abstract public void on_exit();

    javafx.scene.Scene scene;

}
