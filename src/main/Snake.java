package main;

import main.Objects.Apple;
import main.Objects.Edible;
import main.Objects.Wall;
import main.Sprites.SnakeSprite;
import main.Sprites.Sprite;

import java.util.ArrayList;
import java.util.List;


public class Snake extends Entity {
    private List<Point> body;
    private Point head;
    private int direction;
    private int prevDirection;

    public Snake(Point pos, int dir, Field field) {
        position = pos;
        body = new ArrayList<>();
        body.add(pos);
        direction = dir;
        prevDirection = dir;
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
        if (!game.isEvalTick())
            return;

        head = position.add(Point.OFFSET[direction]);
        boolean spawnApple = false;
        if (!(game.getField().isInside(head))) {
            game.getPowerUp().outOfField(game);
        }
        head = game.getField().wrapPoint(head);
        Entity eaten = game.getField().entityAtPoint(head);

        if (eaten instanceof Snake) {
            boolean canEatTail = game.getGrowth() == 0;
            int lim = canEatTail ? body.size() - 1 : body.size();
            for (int i = 0; i < lim; ++i) {
                if (head.equals(body.get(i))) {
                    game.getPowerUp().eatYourself(game);
                    break;
                }
            }
        }
        if (eaten instanceof Apple) {
            spawnApple = true;
            game.getPowerUp().eatApple(game, (Apple) eaten);
        }
        if (eaten instanceof Wall) {
            ((Wall) eaten).eatEffect(game);
        }

        game.getPowerUp().grow(game);
        if (spawnApple)
            game.spawnRandomApple();

        prevDirection = direction;
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
        body.add(0, head);
        position = head;
    }

    public void setDirection(int direction) {
        if ((direction + 2) % 4 != prevDirection) {
            this.direction = direction;
        }
    }

    public int getDirection() {
        return direction;
    }
}
