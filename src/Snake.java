import java.util.ArrayList;

public class Snake extends Entity {
    private ArrayList<Point> body;

    private int direction;

    private Field field;

    public Snake(Point pos, int dir, Field field) {
        position = pos;
        body = new ArrayList<>();
        body.add(pos);
        direction = dir;
        this.field = field;
    }

    @Override
    public ArrayList<Point> allPositions() {
        ArrayList<Point> result = new ArrayList<>();
        result.addAll(body);
        return result;
    }

    public boolean SetDirection(int dir) {
        if (!(0 <= dir && dir < 4))
            throw new IllegalArgumentException();
        if ((direction + 2) % 4 == dir)
            return false;
        direction = dir;
        return true;
    }

    public void tick() {
        Point head = position.add(Point.OFFSET[direction]);
        System.out.println(head.x);
        System.out.println(head.y);
        if (field.getWrap()) {
            head = field.wrapPoint(head);
        }
        else if (!(field.isInside(head))) {
            field.lose();
        }

        Entity entity = field.entityAtPoint(head);
        boolean spawnApple = false;

        if (!head.equals(body.get(body.size() - 1)) &&
                entity instanceof Snake) {
            field.lose();
        }
        else if (entity instanceof Apple) {
            field.removeEntity(entity);
            spawnApple = true;
        }
        else {
            body.remove(body.size() - 1);
        }

        body.add(0, head);
        field.removeEntity(this);
        position = head;
        field.addEntity(this);

        if (spawnApple)
            field.spawnRandomApple();
    }

    public boolean isOccupied(Point pos) {
        for (Point bodyPos: body) {
            if (pos.equals(bodyPos))
                return true;
        }
        return false;
    }

    public boolean isIntersecting() {
        for (int i = 1; i < body.size(); ++i) {
            if (body.get(i).equals(position)) {
                return true;
            }
        }
        return false;
    }
}
