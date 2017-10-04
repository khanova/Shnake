public class Point {
    static final Point[] OFFSET = {
            new Point(1, 0),
            new Point(0, 1),
            new Point(-1, 0),
            new Point(0, -1)
    };

    public int x;
    public int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Point))
            return false;
        Point point = (Point)other;
        return x == point.x && y == point.y;
    }

    public Point add (Point other) {
        return new Point(x + other.x, y + other.y);
    }

    @Override
    public int hashCode() {
        return (x << 15) | y;
    }
}
