package game.scene;

import game.Map;
import game.Point;
import game.resources.ResID;
import game.resources.ResourcesManager;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

import static game.Util.window_height;
import static game.Util.window_width;

public class GameScene extends Scene
{


    boolean piece_selected = false;
    public char current_piece = '#';
    public Point pos_current_piece = new Point(-1,-1);
    public static final int distance = 68;;
    public static final int baseX = -6;;
    public static final int baseY = -2;
    public PlayerTurn turn = PlayerTurn.Red;
    private Canvas canvas;
    private GraphicsContext painter;
    private Pane game_pane;
    private Map map = new Map();




    public enum PlayerTurn
    {
        Red,
        Black
    }
    public GameScene()
    {
        //初始化屏幕
        game_pane = new Pane();
        canvas = new Canvas(window_width, window_height);
        scene = new javafx.scene.Scene(game_pane);
        painter = canvas.getGraphicsContext2D();
        game_pane.getChildren().add(canvas);

        //游戏界面事件监听
        scene.setOnKeyPressed(event -> {
            switch (event.getCode())
            {
                case ESCAPE:
                    SceneManager.getInstance().switch_scene(SceneManager.SceneType.MenuScene);
                    break;
            }
        });

        scene.setOnMouseClicked(event -> {
            //获取当前点击位置，并且更新当前棋子以及棋子位置
            select_piece(event);
        });

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
        painter.clearRect(0,0,window_width,window_height);
        render_background();
        render_map(map.map_now);
        render_selected_frame();
    }

    @Override
    public void on_exit()
    {

    }

    public void render_background()
    {
        painter.drawImage(ResourcesManager.getInstance().get_image(ResID.Img_Background),-6,0);
        painter.drawImage(ResourcesManager.getInstance().get_image(ResID.Img_toolbar),-6,698);
    }

    public void render_map(char[][] map)
    {
        for(int i = 0; i < 10; i++){
            for(int j = 0; j < 9; j++){
                switch (map[i][j]){
                    case 'E':
                        painter.drawImage(ResourcesManager.getInstance().get_image(ResID.bE),baseX+distance*j,baseY+distance*i);
                        break;
                    case 'e':
                        painter.drawImage(ResourcesManager.getInstance().get_image(ResID.rE),baseX+distance*j,baseY+distance*i);
                        break;
                    case 'D':
                        painter.drawImage(ResourcesManager.getInstance().get_image(ResID.bD),baseX+distance*j,baseY+distance*i);
                        break;
                    case 'd':
                        painter.drawImage(ResourcesManager.getInstance().get_image(ResID.rD),baseX+distance*j,baseY+distance*i);
                        break;
                    case 'C':
                        painter.drawImage(ResourcesManager.getInstance().get_image(ResID.bC),baseX+distance*j,baseY+distance*i);
                        break;
                    case 'c':
                        painter.drawImage(ResourcesManager.getInstance().get_image(ResID.rC),baseX+distance*j,baseY+distance*i);
                        break;
                    case 'B':
                        painter.drawImage(ResourcesManager.getInstance().get_image(ResID.bB),baseX+distance*j,baseY+distance*i);
                        break;
                    case 'b':
                        painter.drawImage(ResourcesManager.getInstance().get_image(ResID.rB),baseX+distance*j,baseY+distance*i);
                        break;
                    case 'A':
                        painter.drawImage(ResourcesManager.getInstance().get_image(ResID.bA),baseX+distance*j,baseY+distance*i);
                        break;
                    case 'a':
                        painter.drawImage(ResourcesManager.getInstance().get_image(ResID.rA),baseX+distance*j,baseY+distance*i);
                        break;
                    case 'G':
                        painter.drawImage(ResourcesManager.getInstance().get_image(ResID.bG),baseX+distance*j,baseY+distance*i);
                        break;
                    case 'g':
                        painter.drawImage(ResourcesManager.getInstance().get_image(ResID.rG),baseX+distance*j,baseY+distance*i);
                        break;
                    case 'F':
                        painter.drawImage(ResourcesManager.getInstance().get_image(ResID.bF),baseX+distance*j,baseY+distance*i);
                        break;
                    case 'f':
                        painter.drawImage(ResourcesManager.getInstance().get_image(ResID.rF),baseX+distance*j,baseY+distance*i);
                        break;
                    case '#':
                        break;
                    default:
                }
            }
        }

    }

    public void render_selected_frame()
    {
        if (current_piece == '#')
            return;
        painter.drawImage(ResourcesManager.getInstance().get_image(ResID.selected_frame),
                baseX+12+distance* pos_current_piece.y,baseY+8+distance* pos_current_piece.x);
    }

    public void select_piece(MouseEvent e)
    {
        double x = e.getX();
        double y = e.getY();
        int X = (int)((x-10)/distance);
        int Y = (int)((y-8)/distance);
        //点到了 char数组索引为 (Y,X)的棋子
        char temp = map.map_now[Y][X];


        if(temp == '#')
            piece_selected = false;
        //如果点到了非本回合棋子，则不修改
        if((turn == PlayerTurn.Red &&Character.isUpperCase(temp)) || (turn == PlayerTurn.Black && Character.isLowerCase(temp)) )
            return;

        pos_current_piece.setPoint(Y,X);
        current_piece = temp;
        piece_selected = true;
    }

    public void change_turn()
    {
        if(turn == PlayerTurn.Red)
            turn = PlayerTurn.Black;
        else
            turn = PlayerTurn.Red;
    }


    //获取当前棋子的索引，默认为空
    public Point get_piece_idx(char current_piece)
    {
        if (current_piece == '#')
            return null;

        return pos_current_piece;

    }

    public ArrayList<Point> get_possible_move_pos(char current_piece)
    {
        ArrayList<Point> possible_move_pos = new ArrayList<>();
        switch (current_piece)
        {
            //红方帅的移动
            case 'a':   //帅
            {
                //遍历九宫格
                for (int i = 7;i <= 9;i++)
                {
                    for(int j = 3;j <= 5;j++)
                    {
                        //如果不是红方棋子(小写),则继续判断
                        if (!Character.isLowerCase(map.map_now[i][j]))
                        {
                            Point pos = new Point(i,j);
                            //如果相邻
                            boolean is_adjacent = Point.is_adjacent(pos,get_piece_idx(current_piece));
                            if(is_adjacent)
                            {
                                possible_move_pos.add(pos);
                            }
                        }
                    }
                }
                break;
            }
            case 'b':   //士
            {
                //遍历九宫格
                for(int i = 7;i <= 9;i++)
                {
                    for(int j = 7;j <= 9;j++)
                    {
                        //如果不是红方棋子(小写),则继续判断
                        if (!Character.isLowerCase(map.map_now[i][j]))
                        {
                            Point pos = new Point(i,j);
                            boolean is_diagonal = Math.abs(pos.x - get_piece_idx(current_piece).x) == 1 && Math.abs(pos.y - get_piece_idx(current_piece).y) == 1;
                            if(is_diagonal)
                            {
                                possible_move_pos.add(pos);
                            }
                        }
                    }
                }
                break;
            }
            case 'c':    //相
            {
                Point pos_piece = get_piece_idx(current_piece);

                Point pos_left_up = new Point(pos_piece.x - 1,pos_piece.y - 1);
                Point pos_right_up = new Point(pos_piece.x - 1,pos_piece.y + 1);
                Point pos_left_down = new Point(pos_piece.x + 1,pos_piece.y - 1);
                Point pos_right_down = new Point(pos_piece.x + 1,pos_piece.y + 1);

                Point pos_left_up_target = new Point(pos_piece.x - 2,pos_piece.y - 2);
                Point pos_left_down_target = new Point(pos_piece.x + 2,pos_piece.y - 2);
                Point pos_right_up_target = new Point(pos_piece.x - 2,pos_piece.y + 2);
                Point pos_right_down_target = new Point(pos_piece.x + 2,pos_piece.y + 2);


                //如果左上方在界内
                if(Point.is_valid(pos_left_up))
                {
                    //左上方没有棋子
                    if(map.map_now[pos_left_up.x][pos_left_up.y] == '#')
                    {
                        //如果目标位置没出界，并且没有红色棋子，并且在己方场地内
                        if(Point.is_valid(pos_left_up_target) && !Character.isLowerCase(map.map_now[pos_left_up_target.x][pos_left_up_target.y]) && pos_left_up_target.x >= 5)
                            possible_move_pos.add(pos_left_up_target);
                    }
                }

                if (Point.is_valid(pos_right_up))
                {
                    if(map.map_now[pos_right_up.x][pos_right_up.y] == '#')
                    {
                        if(Point.is_valid(pos_right_up_target) && !Character.isLowerCase(map.map_now[pos_right_up_target.x][pos_right_up_target.y]) && pos_right_up_target.x >= 5)
                            possible_move_pos.add(pos_right_up_target);
                    }
                }

                if (Point.is_valid(pos_left_down))
                {
                    if(map.map_now[pos_left_down.x][pos_left_down.y] == '#')
                    {
                        if(Point.is_valid(pos_left_down_target)
                                && !Character.isLowerCase(map.map_now[pos_left_down_target.x][pos_left_down_target.y]) && pos_left_down_target.x >= 5)
                            possible_move_pos.add(pos_left_down_target);
                    }
                }

                if (Point.is_valid(pos_right_down))
                {
                    if(map.map_now[pos_right_down.x][pos_right_down.y] == '#')
                    {
                        if(Point.is_valid(pos_right_down_target) && !Character.isLowerCase(map.map_now[pos_right_down_target.x][pos_right_down_target.y]) && pos_right_down_target.x >= 5)
                            possible_move_pos.add(pos_right_down_target);
                    }
                }

                break;
            }
            case 'd':
            {
                //point x是行，y是列
                Point pos_piece = get_piece_idx(current_piece);

                Point pos_left = new Point(pos_piece.x,pos_piece.y - 1);
                Point pos_right = new Point(pos_piece.x,pos_piece.y + 1);
                Point pos_up = new Point(pos_piece.x - 1,pos_piece.y);
                Point pos_down = new Point(pos_piece.x + 1,pos_piece.y);

                Point pos_left_up_target = new Point(pos_piece.x - 1,pos_piece.y - 2);
                Point pos_left_down_target = new Point(pos_piece.x + 1,pos_piece.y - 2);
                Point pos_top_left_target = new Point(pos_piece.x - 2,pos_piece.y - 1);
                Point pos_top_right_target = new Point(pos_piece.x - 2,pos_piece.y + 1);
                Point pos_right_up_target = new Point(pos_piece.x - 1,pos_piece.y + 2);
                Point pos_right_down_target = new Point(pos_piece.x + 1,pos_piece.y + 2);
                Point pos_bottom_left_target = new Point(pos_piece.x + 2,pos_piece.y - 1);
                Point pos_bottom_right_target = new Point(pos_piece.x + 2,pos_piece.y + 1);


                //如果左边不出界
                if(Point.is_valid(pos_left))
                {
                    //如果左边没有棋子
                    if (map.map_now[pos_left.x][pos_left.y] == '#')
                    {
                        //如果左上角没出界，并且不是红方棋子，则可以为目标位置
                        if (Point.is_valid(pos_left_up_target) && !Character.isLowerCase(map.map_now[pos_left_up_target.x][pos_left_up_target.y]))
                            possible_move_pos.add(pos_left_up_target);
                        if (Point.is_valid(pos_left_down_target) && !Character.isLowerCase(map.map_now[pos_left_down_target.x][pos_left_down_target.y]))
                            possible_move_pos.add(pos_left_down_target);
                    }
                }

                if(Point.is_valid(pos_up))
                {
                    if(map.map_now[pos_up.x][pos_up.y] == '#')
                    {
                        if(Point.is_valid(pos_top_left_target) && !Character.isLowerCase(map.map_now[pos_top_left_target.x][pos_top_left_target.y]))
                            possible_move_pos.add(pos_top_left_target);
                        if(Point.is_valid(pos_top_right_target) && !Character.isLowerCase(map.map_now[pos_top_right_target.x][pos_top_right_target.y]))
                            possible_move_pos.add(pos_top_right_target);
                    }
                }

                if(Point.is_valid(pos_right))
                {
                    if(map.map_now[pos_right.x][pos_right.y] == '#')
                    {
                        if(Point.is_valid(pos_right_up_target) && !Character.isLowerCase(map.map_now[pos_right_up_target.x][pos_right_up_target.y]))
                            possible_move_pos.add(pos_right_up_target);
                        if(Point.is_valid(pos_right_down_target) && !Character.isLowerCase(map.map_now[pos_right_down_target.x][pos_right_down_target.y]))
                            possible_move_pos.add(pos_right_down_target);
                    }
                }


                if(Point.is_valid(pos_down))
                {
                    if(map.map_now[pos_down.x][pos_down.y] == '#')
                    {
                        if(Point.is_valid(pos_bottom_left_target) && !Character.isLowerCase(map.map_now[pos_bottom_left_target.x][pos_bottom_left_target.y]))
                            possible_move_pos.add(pos_bottom_left_target);
                        if(Point.is_valid(pos_bottom_right_target) && !Character.isLowerCase(map.map_now[pos_bottom_right_target.x][pos_bottom_right_target.y]))
                            possible_move_pos.add(pos_bottom_right_target);
                    }
                }
                break;
            }

            case 'e':
            {
                Point pos_piece = get_piece_idx(current_piece);
                int pos_x = pos_piece.x;
                int pox_y = pos_piece.y;
                //向右找
                while(Point.is_valid(new Point(pos_x,pox_y + 1)))
                {
                    pox_y++;
                    //如果是空位置
                    if(map.map_now[pos_x][pox_y] == '#')
                    {
                        possible_move_pos.add(new Point(pos_x,pox_y));
                    }
                    //如果是红方棋子
                    else if(Character.isLowerCase(map.map_now[pos_x][pox_y]))
                    {
                        break;
                    }
                    //否则就是黑方棋子
                    else
                    {
                        possible_move_pos.add(new Point(pos_x,pox_y));
                        break;
                    }
                }
                //重置坐标
                pos_x = pos_piece.x;
                pox_y = pos_piece.y;
                //向上找
                while (Point.is_valid(new Point(pos_x - 1,pox_y)))
                {
                    pos_x--;
                    if(map.map_now[pos_x][pox_y] == '#')
                    {
                        possible_move_pos.add(new Point(pos_x,pox_y));
                    }
                    else if(Character.isLowerCase(map.map_now[pos_x][pox_y]))
                    {
                        break;
                    }
                    else
                    {
                        possible_move_pos.add(new Point(pos_x,pox_y));
                       break;
                    }
                }
                //重置坐标
                pos_x = pos_piece.x;
                pox_y = pos_piece.y;
                //向左找
                while (Point.is_valid(new Point(pos_x,pox_y - 1)))
                {
                    pox_y--;
                    if(map.map_now[pos_x][pox_y] == '#')
                    {
                        possible_move_pos.add(new Point(pos_x,pox_y));
                    }
                    else if(Character.isLowerCase(map.map_now[pos_x][pox_y]))
                    {
                        break;
                    }
                    else
                    {
                        possible_move_pos.add(new Point(pos_x,pox_y));
                       break;
                    }
                }
                //重置坐标
                pos_x = pos_piece.x;
                pox_y = pos_piece.y;
                //向下找
                while (Point.is_valid(new Point(pos_x + 1,pox_y)))
                {
                    pos_x++;
                    if(map.map_now[pos_x][pox_y] == '#')
                    {
                        possible_move_pos.add(new Point(pos_x,pox_y));
                    }
                    else if(Character.isLowerCase(map.map_now[pos_x][pox_y]))
                    {
                        break;
                    }
                    else
                    {
                        possible_move_pos.add(new Point(pos_x,pox_y));
                      break;
                    }
                }
                break;

            }

            case 'f':
            {
                Point pos_piece = get_piece_idx(current_piece);
                //横向遍历
                int flag = 0;  //炮越过的棋子数标识
                int pos_x = pos_piece.x;
                int pox_y = pos_piece.y;
                //向左遍历
                while(Point.is_valid(new Point(pos_x,pox_y - 1)))
                {
                    pox_y--;
                    //左边有空的格子且还没有棋子隔着
                    if(map.map_now[pos_x][pox_y] == '#')
                    {
                        if(flag == 0)
                            possible_move_pos.add(new Point(pos_x,pox_y));
                    }
                    //否则就是遇到棋子了
                    else
                    {
                        if(flag == 1 && !Character.isLowerCase(map.map_now[pos_x][pox_y]))
                        {
                            possible_move_pos.add(new Point(pos_x,pox_y));
                            break; //结束遍历
                        }
                        flag++;
                    }
                }

                //重置
                pos_x = pos_piece.x;
                pox_y = pos_piece.y;
                flag = 0;
                //向右遍历
                while(Point.is_valid(new Point(pos_x,pox_y + 1)))
                {
                    pox_y++;
                    if(map.map_now[pos_x][pox_y] == '#')
                    {
                        if(flag == 0)
                            possible_move_pos.add(new Point(pos_x,pox_y));
                    }
                    else
                    {
                        if(flag == 1 && !Character.isLowerCase(map.map_now[pos_x][pox_y]))
                        {
                            possible_move_pos.add(new Point(pos_x,pox_y));
                            break;
                        }
                        flag++;
                    }
                }

                //重置
                pos_x = pos_piece.x;
                pox_y = pos_piece.y;
                flag = 0;
                //向上遍历
                while(Point.is_valid(new Point(pos_x - 1,pox_y)))
                {
                    pos_x--;
                    if(map.map_now[pos_x][pox_y] == '#')
                    {
                        if(flag == 0)
                            possible_move_pos.add(new Point(pos_x,pox_y));
                    }
                    else
                    {
                        if(flag == 1 && !Character.isLowerCase(map.map_now[pos_x][pox_y]))
                        {
                            possible_move_pos.add(new Point(pos_x,pox_y));
                            break;
                        }
                        flag++;
                    }
                }

                //重置
                pos_x = pos_piece.x;
                pox_y = pos_piece.y;
                flag = 0;
                //向下遍历
                while(Point.is_valid(new Point(pos_x + 1,pox_y)))
                {
                    pos_x++;
                    if(map.map_now[pos_x][pox_y] == '#')
                    {
                        if(flag == 0)
                            possible_move_pos.add(new Point(pos_x,pox_y));
                    }
                    else
                    {
                        if(flag == 1 && !Character.isLowerCase(map.map_now[pos_x][pox_y]))
                        {
                            possible_move_pos.add(new Point(pos_x,pox_y));
                            break;
                        }
                        flag++;
                    }
                }
                break;
            }

            case 'g':
            {
                Point pos_piece = get_piece_idx(current_piece);

                //没过河，只检查前面能走吗
                if(pos_piece.x >= 5)
                {
                    //!lowercase只要不是小写就是false
                    if(!Character.isLowerCase(map.map_now[pos_piece.x - 1][pos_piece.y]))
                    {
                        possible_move_pos.add(new Point(pos_piece.x - 1,pos_piece.y));
                    }
                }
                else
                {
                    Point pos_left = new Point(pos_piece.x,pos_piece.y - 1);
                    Point pos_right = new Point(pos_piece.x,pos_piece.y + 1);
                    Point pos_forward = new Point(pos_piece.x - 1,pos_piece.y);

                    if(Point.is_valid(pos_left))
                    {
                        if (!Character.isLowerCase(map.map_now[pos_left.x][pos_left.y]))
                            possible_move_pos.add(pos_left);
                    }
                    if(Point.is_valid(pos_right))
                    {
                        if (!Character.isLowerCase(map.map_now[pos_right.x][pos_right.y]))
                            possible_move_pos.add(pos_right);
                    }
                    if(Point.is_valid(pos_forward))
                    {
                        if (!Character.isLowerCase(map.map_now[pos_forward.x][pos_forward.y]))
                            possible_move_pos.add(pos_forward);
                    }
                }

                break;
            }

            case 'A':   //将
            {
                //遍历九宫格
                for (int i = 0;i <= 2;i++)
                {
                    for(int j = 3;j <= 5;j++)
                    {
                        //如果不是黑方棋子(大写),则继续判断
                        if (!Character.isUpperCase(map.map_now[i][j]))
                        {
                            Point pos = new Point(i,j);
                            //如果相邻
                            boolean is_adjacent = Point.is_adjacent(pos,get_piece_idx(current_piece));
                            if(is_adjacent)
                            {
                                possible_move_pos.add(pos);
                            }
                        }
                    }
                }
                break;
            }

            case 'B':   //士
            {
                //遍历九宫格
                for(int i = 0;i <= 2;i++)
                {
                    for(int j = 7;j <= 9;j++)
                    {
                        //如果不是红方棋子(小写),则继续判断
                        if (!Character.isUpperCase(map.map_now[i][j]))
                        {
                            Point pos = new Point(i,j);
                            boolean is_diagonal = Math.abs(pos.x - get_piece_idx(current_piece).x) == 1 && Math.abs(pos.y - get_piece_idx(current_piece).y) == 1;
                            if(is_diagonal)
                            {
                                possible_move_pos.add(pos);
                            }
                        }
                    }
                }
                break;
            }

            case 'C':    //相
            {
                Point pos_piece = get_piece_idx(current_piece);

                Point pos_left_up = new Point(pos_piece.x - 1,pos_piece.y - 1);
                Point pos_right_up = new Point(pos_piece.x - 1,pos_piece.y + 1);
                Point pos_left_down = new Point(pos_piece.x + 1,pos_piece.y - 1);
                Point pos_right_down = new Point(pos_piece.x + 1,pos_piece.y + 1);

                Point pos_left_up_target = new Point(pos_piece.x - 2,pos_piece.y - 2);
                Point pos_left_down_target = new Point(pos_piece.x + 2,pos_piece.y - 2);
                Point pos_right_up_target = new Point(pos_piece.x - 2,pos_piece.y + 2);
                Point pos_right_down_target = new Point(pos_piece.x + 2,pos_piece.y + 2);


                //如果左上方在界内
                if(Point.is_valid(pos_left_up))
                {
                    //左上方没有棋子
                    if(map.map_now[pos_left_up.x][pos_left_up.y] == '#')
                    {
                        //如果目标位置没出界，并且没有黑色棋子，并且在己方场地内
                        if(Point.is_valid(pos_left_up_target) && !Character.isUpperCase(map.map_now[pos_left_up_target.x][pos_left_up_target.y]) && pos_left_up_target.x <= 4)
                            possible_move_pos.add(pos_left_up_target);
                    }
                }

                if (Point.is_valid(pos_right_up))
                {
                    if(map.map_now[pos_right_up.x][pos_right_up.y] == '#')
                    {
                        if(Point.is_valid(pos_right_up_target) && !Character.isUpperCase(map.map_now[pos_right_up_target.x][pos_right_up_target.y]) && pos_right_up_target.x <= 4)
                            possible_move_pos.add(pos_right_up_target);
                    }
                }

                if (Point.is_valid(pos_left_down))
                {
                    if(map.map_now[pos_left_down.x][pos_left_down.y] == '#')
                    {
                        if(Point.is_valid(pos_left_down_target)
                                && !Character.isLowerCase(map.map_now[pos_left_down_target.x][pos_left_down_target.y]) && pos_left_down_target.x <= 4)
                            possible_move_pos.add(pos_left_down_target);
                    }
                }

                if (Point.is_valid(pos_right_down))
                {
                    if(map.map_now[pos_right_down.x][pos_right_down.y] == '#')
                    {
                        if(Point.is_valid(pos_right_down_target) && !Character.isUpperCase(map.map_now[pos_right_down_target.x][pos_right_down_target.y]) && pos_right_down_target.x <= 4)
                            possible_move_pos.add(pos_right_down_target);
                    }
                }
                break;
            }

            case 'D':
            {
                //point x是行，y是列
                Point pos_piece = get_piece_idx(current_piece);

                Point pos_left = new Point(pos_piece.x,pos_piece.y - 1);
                Point pos_right = new Point(pos_piece.x,pos_piece.y + 1);
                Point pos_up = new Point(pos_piece.x - 1,pos_piece.y);
                Point pos_down = new Point(pos_piece.x + 1,pos_piece.y);

                Point pos_left_up_target = new Point(pos_piece.x - 1,pos_piece.y - 2);
                Point pos_left_down_target = new Point(pos_piece.x + 1,pos_piece.y - 2);
                Point pos_top_left_target = new Point(pos_piece.x - 2,pos_piece.y - 1);
                Point pos_top_right_target = new Point(pos_piece.x - 2,pos_piece.y + 1);
                Point pos_right_up_target = new Point(pos_piece.x - 1,pos_piece.y + 2);
                Point pos_right_down_target = new Point(pos_piece.x + 1,pos_piece.y + 2);
                Point pos_bottom_left_target = new Point(pos_piece.x + 2,pos_piece.y - 1);
                Point pos_bottom_right_target = new Point(pos_piece.x + 2,pos_piece.y + 1);


                //如果左边不出界
                if(Point.is_valid(pos_left))
                {
                    //如果左边没有棋子
                    if (map.map_now[pos_left.x][pos_left.y] == '#')
                    {
                        //如果左上角没出界，并且不是红方棋子，则可以为目标位置
                        if (Point.is_valid(pos_left_up_target) && !Character.isUpperCase(map.map_now[pos_left_up_target.x][pos_left_up_target.y]))
                            possible_move_pos.add(pos_left_up_target);
                        if (Point.is_valid(pos_left_down_target) && !Character.isUpperCase(map.map_now[pos_left_down_target.x][pos_left_down_target.y]))
                            possible_move_pos.add(pos_left_down_target);
                    }
                }

                if(Point.is_valid(pos_up))
                {
                    if(map.map_now[pos_up.x][pos_up.y] == '#')
                    {
                        if(Point.is_valid(pos_top_left_target) && !Character.isUpperCase(map.map_now[pos_top_left_target.x][pos_top_left_target.y]))
                            possible_move_pos.add(pos_top_left_target);
                        if(Point.is_valid(pos_top_right_target) && !Character.isUpperCase(map.map_now[pos_top_right_target.x][pos_top_right_target.y]))
                            possible_move_pos.add(pos_top_right_target);
                    }
                }

                if(Point.is_valid(pos_right))
                {
                    if(map.map_now[pos_right.x][pos_right.y] == '#')
                    {
                        if(Point.is_valid(pos_right_up_target) && !Character.isUpperCase(map.map_now[pos_right_up_target.x][pos_right_up_target.y]))
                            possible_move_pos.add(pos_right_up_target);
                        if(Point.is_valid(pos_right_down_target) && !Character.isUpperCase(map.map_now[pos_right_down_target.x][pos_right_down_target.y]))
                            possible_move_pos.add(pos_right_down_target);
                    }
                }


                if(Point.is_valid(pos_down))
                {
                    if(map.map_now[pos_down.x][pos_down.y] == '#')
                    {
                        if(Point.is_valid(pos_bottom_left_target) && !Character.isUpperCase(map.map_now[pos_bottom_left_target.x][pos_bottom_left_target.y]))
                            possible_move_pos.add(pos_bottom_left_target);
                        if(Point.is_valid(pos_bottom_right_target) && !Character.isUpperCase(map.map_now[pos_bottom_right_target.x][pos_bottom_right_target.y]))
                            possible_move_pos.add(pos_bottom_right_target);
                    }
                }
                break;
            }

            case 'E':
            {
                Point pos_piece = get_piece_idx(current_piece);
                int pos_x = pos_piece.x;
                int pox_y = pos_piece.y;
                //向右找
                while(Point.is_valid(new Point(pos_x,pox_y + 1)))
                {
                    pox_y++;
                    //如果是空位置
                    if(map.map_now[pos_x][pox_y] == '#')
                    {
                        possible_move_pos.add(new Point(pos_x,pox_y));
                    }
                    //如果是黑方棋子
                    else if(Character.isUpperCase(map.map_now[pos_x][pox_y]))
                    {
                        break;
                    }
                    //否则就是红方棋子
                    else
                    {
                        possible_move_pos.add(new Point(pos_x,pox_y));
                        break;
                    }
                }
                //重置坐标
                pos_x = pos_piece.x;
                pox_y = pos_piece.y;
                //向上找
                while (Point.is_valid(new Point(pos_x - 1,pox_y)))
                {
                    pos_x--;
                    if(map.map_now[pos_x][pox_y] == '#')
                    {
                        possible_move_pos.add(new Point(pos_x,pox_y));
                    }
                    else if(Character.isUpperCase(map.map_now[pos_x][pox_y]))
                    {
                        break;
                    }
                    else
                    {
                        possible_move_pos.add(new Point(pos_x,pox_y));
                        break;
                    }
                }
                //重置坐标
                pos_x = pos_piece.x;
                pox_y = pos_piece.y;
                //向左找
                while (Point.is_valid(new Point(pos_x,pox_y - 1)))
                {
                    pox_y--;
                    if(map.map_now[pos_x][pox_y] == '#')
                    {
                        possible_move_pos.add(new Point(pos_x,pox_y));
                    }
                    else if(Character.isUpperCase(map.map_now[pos_x][pox_y]))
                    {
                        break;
                    }
                    else
                    {
                        possible_move_pos.add(new Point(pos_x,pox_y));
                        break;
                    }
                }
                //重置坐标
                pos_x = pos_piece.x;
                pox_y = pos_piece.y;
                //向下找
                while (Point.is_valid(new Point(pos_x + 1,pox_y)))
                {
                    pos_x++;
                    if(map.map_now[pos_x][pox_y] == '#')
                    {
                        possible_move_pos.add(new Point(pos_x,pox_y));
                    }
                    else if(Character.isUpperCase(map.map_now[pos_x][pox_y]))
                    {
                        break;
                    }
                    else
                    {
                        possible_move_pos.add(new Point(pos_x,pox_y));
                        break;
                    }
                }
                break;
            }

            case 'F':
            {
                Point pos_piece = get_piece_idx(current_piece);
                //横向遍历
                int flag = 0;  //炮越过的棋子数标识
                int pos_x = pos_piece.x;
                int pox_y = pos_piece.y;
                //向左遍历
                while(Point.is_valid(new Point(pos_x,pox_y - 1)))
                {
                    pox_y--;
                    //左边有空的格子且还没有棋子隔着
                    if(map.map_now[pos_x][pox_y] == '#')
                    {
                        if(flag == 0)
                            possible_move_pos.add(new Point(pos_x,pox_y));
                    }
                    //否则就是遇到棋子了
                    else
                    {
                        if(flag == 1 && !Character.isUpperCase(map.map_now[pos_x][pox_y]))
                        {
                            possible_move_pos.add(new Point(pos_x,pox_y));
                            break; //结束遍历
                        }
                        flag++;
                    }
                }

                //重置
                pos_x = pos_piece.x;
                pox_y = pos_piece.y;
                flag = 0;
                //向右遍历
                while(Point.is_valid(new Point(pos_x,pox_y + 1)))
                {
                    pox_y++;
                    if(map.map_now[pos_x][pox_y] == '#')
                    {
                        if(flag == 0)
                            possible_move_pos.add(new Point(pos_x,pox_y));
                    }
                    else
                    {
                        if(flag == 1 && !Character.isUpperCase(map.map_now[pos_x][pox_y]))
                        {
                            possible_move_pos.add(new Point(pos_x,pox_y));
                            break;
                        }
                        flag++;
                    }
                }

                //重置
                pos_x = pos_piece.x;
                pox_y = pos_piece.y;
                flag = 0;
                //向上遍历
                while(Point.is_valid(new Point(pos_x - 1,pox_y)))
                {
                    pos_x--;
                    if(map.map_now[pos_x][pox_y] == '#')
                    {
                        if(flag == 0)
                            possible_move_pos.add(new Point(pos_x,pox_y));
                    }
                    else
                    {
                        if(flag == 1 && !Character.isUpperCase(map.map_now[pos_x][pox_y]))
                        {
                            possible_move_pos.add(new Point(pos_x,pox_y));
                            break;
                        }
                        flag++;
                    }
                }

                //重置
                pos_x = pos_piece.x;
                pox_y = pos_piece.y;
                flag = 0;
                //向下遍历
                while(Point.is_valid(new Point(pos_x + 1,pox_y)))
                {
                    pos_x++;
                    if(map.map_now[pos_x][pox_y] == '#')
                    {
                        if(flag == 0)
                            possible_move_pos.add(new Point(pos_x,pox_y));
                    }
                    else
                    {
                        if(flag == 1 && !Character.isUpperCase(map.map_now[pos_x][pox_y]))
                        {
                            possible_move_pos.add(new Point(pos_x,pox_y));
                            break;
                        }
                        flag++;
                    }
                }
                break;
            }

            case 'G':
            {
                Point pos_piece = get_piece_idx(current_piece);

                //没过河，只检查前面能走吗
                if(pos_piece.x <= 4)
                {
                    //!lowercase只要不是小写就是false
                    if(!Character.isLowerCase(map.map_now[pos_piece.x - 1][pos_piece.y]))
                    {
                        possible_move_pos.add(new Point(pos_piece.x - 1,pos_piece.y));
                    }
                }
                else
                {
                    Point pos_left = new Point(pos_piece.x,pos_piece.y - 1);
                    Point pos_right = new Point(pos_piece.x,pos_piece.y + 1);
                    Point pos_forward = new Point(pos_piece.x + 1,pos_piece.y);

                    if(Point.is_valid(pos_left))
                    {
                        if (!Character.isUpperCase(map.map_now[pos_left.x][pos_left.y]))
                            possible_move_pos.add(pos_left);
                    }
                    if(Point.is_valid(pos_right))
                    {
                        if (!Character.isUpperCase(map.map_now[pos_right.x][pos_right.y]))
                            possible_move_pos.add(pos_right);
                    }
                    if(Point.is_valid(pos_forward))
                    {
                        if (!Character.isUpperCase(map.map_now[pos_forward.x][pos_forward.y]))
                            possible_move_pos.add(pos_forward);
                    }
                }

                break;
            }

            case '#':

        }

        //返回位置可能性 （可能为空
        return possible_move_pos;
    }

    public void paint_moveable(ArrayList<Point> arr){

    }

}
