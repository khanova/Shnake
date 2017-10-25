package main;

import java.security.AlgorithmConstraints;
import java.util.ArrayList;
import java.util.List;

public class Snake extends Entity {
    private List<Point> body;

    private int direction;
    private int lastDirection;
    private int growth;

    private Field field;

    public Snake(Point pos, int dir, Field field) {
        position = pos;
        body = new ArrayList<>();
        body.add(pos);
        direction = dir;
        lastDirection = dir;
        growth = 0;
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

    public int getGrowth() {
        return growth;
    }

    public void setGrowth(int value) {
        growth = value;
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

        if (entity instanceof Snake &&
                !(head.equals(body.get(body.size() - 1)) && getGrowth() <= 0)) {
            field.lose();
        }
        else if (entity instanceof Apple) {
            ((Apple)entity).eatEffect(this);
            field.removeEntity(entity);
            spawnApple = true;
        }

        if (getGrowth() <= 0) {
            for (int i = 0; i < 1 - getGrowth() && body.size() > 0; i++) {
                body.remove(body.size() - 1);
            }
            setGrowth(0);
        }
        else {
            setGrowth(getGrowth() - 1);
        }

        body.add(0, head);
        field.removeEntity(this);
        position = head;
        field.addEntity(this);

        if (spawnApple) {
            field.spawnRandomApple();
        }
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
