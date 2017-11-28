package main;

import java.util.ArrayList;
import java.util.List;

public class Snake extends Entity {
    private List<Point> body;
    private int direction;


    public Snake(Point pos, int dir, Field field) {
        position = pos;
        body = new ArrayList<>();
        body.add(pos);
        direction = dir;
    }

    @Override
    public List<Point> allPositions() {
        List<Point> result = new ArrayList<>();
        result.addAll(body);
        return result;
    }


    public int length()
    {
        return allPositions().size();
    }


    public List<Point> getBody() {
        List<Point> result = new ArrayList<>();
        for (int i = 1; i < body.size(); ++i) {
            result.add(body.get(i));
        }
        return result;
    }


    public void tick(Game game) {
        Point head = position.add(Point.OFFSET[direction]);
        if (!(game.field.isInside(head))) {
            game.powerUp.outOfField(game);
        }
        Entity entity = game.field.entityAtPoint(head);

        if (entity instanceof Snake) {
            game.powerUp.crash(game);
        }
        else if (entity instanceof Apple) {
            game.powerUp.eatApple(game, (Apple) entity);
        }
        game.powerUp.grow(game);
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

    public void grow(int growth) {
        if (growth <= 0) {
            for (int i = 0; i < 1 - growth && body.size() > 0; i++) {
                body.remove(body.size() - 1);
            }
        }
        Point head = position.add(Point.OFFSET[direction]);

        body.add(0, head);
        position = head;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getDirection() {
        return direction;
    }
}
