package main;

import java.util.ArrayList;
import java.util.List;

public class Snake extends Entity {
    private List<Point> body;

    private int direction;
    private int lastDirection;

    private Field field;

    public Snake(Point pos, int dir, Field field) {
        position = pos;
        body = new ArrayList<>();
        body.add(pos);
        direction = dir;
        lastDirection = dir;
        this.field = field;
    }

    @Override
    public List<Point> allPositions() {
        List<Point> result = new ArrayList<>();
        result.addAll(body);
        return result;
    }

    public boolean setDirection(int dir) {
        if (!(0 <= dir && dir < 4))
            throw new IllegalArgumentException();
        if ((lastDirection + 2) % 4 == dir)
            return false;
        direction = dir;
        return true;
    }

    public int getDirection() {
        return direction;
    }

    public int getLastDirection() {
        return lastDirection;
    }

    public List<Point> getBody() {
        List<Point> result = new ArrayList<>();
        for (int i = 1; i < body.size(); ++i) {
            result.add(body.get(i));
        }
        return result;
    }

    public void tick() {
        lastDirection = direction;

        Point head = position.add(Point.OFFSET[direction]);
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
            field.setPoints(field.getPoints() + 1);
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
        for (Point point: getBody()) {
            if (point.equals(position)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Sprite createSprite() {
        return new SnakeSprite(this);
    }
}
