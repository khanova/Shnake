package main;

import main.Objects.Apple;
import main.Objects.Wall;
import main.Sprites.AutoSnakeSprite;
import main.Sprites.Sprite;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class AutoSnake extends Snake{
    private List<Point> body;
    private int direction;
    private int prevDirection;
    private Random rand;

    public AutoSnake(Point pos, int dir) {
        super(pos, dir);
        position = pos;
        body = new ArrayList<>();
        body.add(pos);
        direction = dir;
        prevDirection = dir;
        rand = new Random();
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

        direction = findDirection(game);

        boolean spawnApple = false;

        Point newPosition = position.add(Point.OFFSET[direction]);

        if (!(game.getField().isInside(newPosition))) {
            game.getPowerUp().outOfField(game);
        }
        newPosition = game.getField().wrapPoint(newPosition);
        Entity eaten = game.getField().entityAtPoint(newPosition);

        if (eaten instanceof AutoSnake) {
            boolean canEatTail = game.getGrowthAutoSnake() == 0;
            int lim = canEatTail ? body.size() - 1 : body.size();
            for (int i = 0; i < lim; ++i) {
                if (newPosition.equals(body.get(i))) {
                    game.getPowerUp().eatYourself(game);
                    break;
                }
            }
        }

        if (eaten instanceof Snake) {
            game.getPowerUp().eatYourself(game);
        }

        if (eaten instanceof Apple) {
            spawnApple = true;
            game.getPowerUp().eatAppleAuto(game, (Apple) eaten);
        }
        if (eaten instanceof Wall) {
            ((Wall) eaten).eatEffectAuto(game);
        }

        game.getPowerUp().growAutoSnake(game, newPosition);
        if (spawnApple)
            game.spawnRandomApple();

        prevDirection = direction;

    }

    private int findDirection(Game game)
    {
        Point pos = position;
        for (int i = 0; i < 4; i++)
        {
            if (game.getField().entityAtPoint(pos.add(Point.OFFSET[i])) instanceof Snake)
                return i;
            pos = position;
        }

        for (int i = 0; i < 4; i++)
        {
            if (game.getField().entityAtPoint(pos.add(Point.OFFSET[i])) instanceof Apple)
                return i;
            pos = position;
        }
        if (!(game.getField().entityAtPoint(pos.add(Point.OFFSET[prevDirection])) instanceof Wall
                || game.getField().entityAtPoint(pos.add(Point.OFFSET[prevDirection])) instanceof AutoSnake
                || !(game.getField().isInside(pos.add(Point.OFFSET[prevDirection])))))
            return prevDirection;
        ArrayList<Integer> dirs = new ArrayList<>();

        for (int i = 0; i < 4; i++)
        {
            if (!(game.getField().entityAtPoint(pos.add(Point.OFFSET[i])) instanceof Wall
                    || game.getField().entityAtPoint(pos.add(Point.OFFSET[i])) instanceof AutoSnake
                    || !(game.getField().isInside(pos.add(Point.OFFSET[i])))
                    || prevDirection == (i + 2) % 4))
                dirs.add(i);
            pos = position;
        }
        int ind = rand.nextInt(dirs.size());
        return dirs.get(ind);
    }

    @Override
    public Sprite createSprite() {
        return new AutoSnakeSprite(this);
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
