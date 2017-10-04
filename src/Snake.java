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

    public int SetDirection(int dir) {
        if (0 <= dir && dir < 4)
            direction = dir;
        throw new IllegalArgumentException();
    }

    public void tick() {
        Point head = position.add(Point.OFFSET[direction]);
        if (field.getWrap()) {
            head = field.wrapPoint(head);
        }
        else if (!(field.isInside(head))) {
            field.lose();
        }

        Entity entity = field.entityAtPoint(head);
        if (!head.equals(body.get(body.size() - 1)) &&
                entity instanceof Snake) {
            field.lose();
        }
        else if (entity instanceof Apple) {
            field.removeEntity(entity);
        }
        else {
            body.remove(body.size() - 1);
        }
        body.add(0, head);
        field.removeEntity(this);
        position = head;
        field.addEntity(this);
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
