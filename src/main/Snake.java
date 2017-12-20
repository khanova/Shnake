package main;

import main.Objects.Apple;
import main.Objects.Wall;
import main.PowerUps.PowerUp;

import java.util.ArrayList;
import java.util.List;


public class Snake extends Entity {
    private List<Point> body;
    private int direction;
    private int prevDirection;
    private PowerUp powerUp;
    private boolean alive;
    private int growth;
    private Field field;
    private int speed;

    public Snake(Point pos, int dir, Field field) {
        powerUp = new PowerUp();
        position = pos;
        body = new ArrayList<>();
        body.add(pos);
        direction = dir;
        prevDirection = dir;
        this.field = field;
        alive = true;
        growth = 0;
        speed = 2;
    }

    public Point getHead() { return position; }

    @Override
    public List<Point> allPositions() {
        List<Point> result = new ArrayList<>();
        result.addAll(body);
        return result;
    }

    public Field getField() {
        return field;
    }

    public int getGrowth() {
        return growth;
    }

    public void setGrowth(int value) {
        growth = value;
    }

    public void addGrowth(int i) {
        growth += i;
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
        if (!alive)
            return;
        powerUp.tick(this);
        if (!isEvalTick(game.getTickCount()))
            return;

        Point newPosition = position.add(Point.OFFSET[direction]);
        boolean spawnApple = false;
        if (!(field.isInside(newPosition))) {
            powerUp.outOfField(this);
        }
        newPosition = field.wrapPoint(newPosition);
        Entity eaten = field.entityAtPoint(newPosition);

        if (eaten instanceof Snake) {
            boolean canEatTail = getGrowth() == 0;
            int lim = canEatTail ? body.size() - 1 : body.size();
            for (int i = 0; i < lim; ++i) {
                if (newPosition.equals(body.get(i))) {
                    powerUp.eatSnake(this);
                    break;
                }
            }
        }
        if (eaten instanceof Apple) {
            spawnApple = true;
            powerUp.eatApple(this, (Apple) eaten);
        }
        if (eaten instanceof Wall) {
            ((Wall) eaten).eatEffect(this);
        }

        powerUp.grow(this, newPosition);
        if (spawnApple)
            field.spawnRandomApple();

        prevDirection = direction;
    }


    public void eatApple(Apple apple) {
        field.removeEntity(apple);
        apple.eatEffect(this);
    }


    public void setPowerUp(PowerUp powerUp) {
        this.powerUp.finish(this);
        this.powerUp = powerUp;
        powerUp.start(this);
    }

    public void eatWall(){
        powerUp.eatWall(this);
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

    public void outOfField() {
        if (!field.getWrap()) {
            lose();
        }
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
        dir = powerUp.getDirection(dir);
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
    public void lose() {
        field.removeEntity(this);
        alive = false;
    }

    public boolean getAlive() {
        return alive;
    }

    public boolean isEvalTick(int tickCount) {
        return tickCount % speed == 0;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getSpeed() {
        return speed;
    }
}
