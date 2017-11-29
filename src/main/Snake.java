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
    private int direction;
    private int prevDirection;

    public Snake(Point pos, int dir, Field field) {
        position = pos;
        body = new ArrayList<>();
        body.add(pos);
        direction = dir;
        prevDirection = dir;
    }

    public Point getHead() { return position; }

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

        Point newPosition = position.add(Point.OFFSET[direction]);
        boolean spawnApple = false;
        if (!(game.getField().isInside(newPosition))) {
            game.getPowerUp().outOfField(game);
        }
        newPosition = game.getField().wrapPoint(newPosition);
        Entity eaten = game.getField().entityAtPoint(newPosition);

        if (eaten instanceof Snake) {
            boolean canEatTail = game.getGrowth() == 0;
            int lim = canEatTail ? body.size() - 1 : body.size();
            for (int i = 0; i < lim; ++i) {
                if (newPosition.equals(body.get(i))) {
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

        game.getPowerUp().grow(game, newPosition);
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

    public void grow(int growth, Point newPosition) {
        if (growth <= 0) {
            for (int i = 0; i < 1 - growth && body.size() > 0; i++) {
                body.remove(body.size() - 1);
            }
        }
        body.add(0, newPosition);
        position = newPosition;
    }

    public boolean setDirection(int dir) {
        if (!(0 <= dir && dir < 4))
            throw new IllegalArgumentException();
        if ((prevDirection + 2) % 4 == dir)
            return false;
        direction = dir;
        return true;
    }

    public int getDirection() {
        return direction;
    }
}
