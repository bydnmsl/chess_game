package game.resources;

import javafx.scene.image.Image;

import java.util.concurrent.ConcurrentHashMap;

public class ResourcesManager
{


    private ResourcesManager()
    {
        load_resources();
    }

    public void load_resources()
    {
        img_pool.put(ResID.Img_Background, new Image("img/chessboard.png"));
        img_pool.put(ResID.Img_toolbar, new Image("img/toolbar.jpg"));
        img_pool.put(ResID.bE, new Image("img/qizi/E.png"));
        img_pool.put(ResID.bD, new Image("img/qizi/D.png"));
        img_pool.put(ResID.bC, new Image("img/qizi/C.png"));
        img_pool.put(ResID.bB, new Image("img/qizi/B.png"));
        img_pool.put(ResID.bA, new Image("img/qizi/A.png"));
        img_pool.put(ResID.bG, new Image("img/qizi/G.png"));
        img_pool.put(ResID.bF, new Image("img/qizi/F.png"));
        img_pool.put(ResID.rE, new Image("img/qizi/ee.png"));
        img_pool.put(ResID.rD, new Image("img/qizi/dd.png"));
        img_pool.put(ResID.rC, new Image("img/qizi/cc.png"));
        img_pool.put(ResID.rB, new Image("img/qizi/bb.png"));
        img_pool.put(ResID.rA, new Image("img/qizi/aa.png"));
        img_pool.put(ResID.rG, new Image("img/qizi/gg.png"));
        img_pool.put(ResID.rF, new Image("img/qizi/ff.png"));
        img_pool.put(ResID.selected_frame, new Image("img/selected.png"));
        img_pool.put(ResID.reachable_frame, new Image("img/reachable.png"));
    }

    public Image get_image(ResID id)
    {
        return img_pool.get(id);
    }

    public static ResourcesManager getInstance()
    {
        return instance;
    }

    private static final ResourcesManager instance = new ResourcesManager();
    private ConcurrentHashMap<ResID, Image> img_pool = new ConcurrentHashMap<>();

}
