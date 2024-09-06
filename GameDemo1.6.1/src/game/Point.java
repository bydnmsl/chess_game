package game;

public class Point
{
    public Point(){};
    public Point(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    public void setPoint(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    public static boolean is_adjacent(Point p1, Point p2)
    {
        return ((Math.abs(p1.x - p2.x) == 1 && p1.y == p2.y) || (Math.abs(p1.y - p2.y) == 1 && p1.x == p2.x));
    }

    public static boolean is_valid(Point p)
    {
        if(p.x < 0 || p.x > 9 || p.y < 0 || p.y > 8)
            return false;
        return true;
    }

    public int x;
    public int y;
}
