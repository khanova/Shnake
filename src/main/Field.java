package main;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.function.BiFunction;

public class Field implements ITickable {
    private HashMap<Point, Entity> entities;
    private int width;
    private int height;

    private Random random;
    private boolean wrap;

    public Field(int width, int height, boolean wrap) {
        this.width = width;
        this.height = height;
        this.wrap = wrap;
        entities = new HashMap<>();
        random = new Random();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean isInside(Point point) {
        return 0 <= point.getX() && point.getX() < width &&
                0 <= point.getY() && point.getY() < height;
    }

    public Point wrapPoint(Point point) {
        int x = point.getX() % width;
        int y = point.getY() % height;
        if (x < 0)
            x += width;
        if (y < 0)
            y += height;
        return new Point(x, y);
    }

    public void tick(Game game) {
        List<Entity> allEntities = new ArrayList<>();
        allEntities.addAll(entities.values());
        for (Entity entity: allEntities) {
            entity.tick(game);
        }
    }

    public Entity entityAtPoint(Point position) {
        if (entities.containsKey(position))
            return entities.get(position);
        for (Entity entity: entities.values())
            if (entity.allPositions().contains(position))
                return entity;
        return null;
    }

    public boolean hasEntityAtPoint(Point position) {
        return entityAtPoint(position) != null;
    }

    public boolean removeEntity(Point position) {
        Entity entity = entityAtPoint(position);
        if (entity == null)
            return false;
        entities.remove(entity.getPosition());
        return true;
    }

    public boolean removeEntity(Entity entity) {
        if (entities.containsValue(entity)) {
            entities.remove(entity.position);
            return true;
        }
        return false;
    }

    public boolean addEntity(Entity entity) {
        if (hasEntityAtPoint(entity.position))
            return false;
        entities.put(entity.position, entity);
        return true;
    }

    public List<List<Entity>> toRectangle() {
        List<List<Entity>> result = new ArrayList<>();
        for (int x = 0; x < width; ++x) {
            List<Entity> line = new ArrayList<>();
            for (int y = 0; y < height; ++y) {
                line.add(entityAtPoint(new Point(x, y)));
            }
            result.add(line);
        }
        return result;
    }

    public Snake spawnSnake(Point position, int direction) {
        if (!isInside(position) || hasEntityAtPoint(position))
            throw new IllegalArgumentException();
        Snake snake = new Snake(position, direction, this);
        addEntity(snake);
        return snake;
    }

    public Apple spawnApple(Point position, BiFunction<Point, Field, Apple> init) {
        if (!isInside(position) || hasEntityAtPoint(position))
            throw new IllegalArgumentException();
        Apple apple = init.apply(position, this);
        addEntity(apple);
        return apple;
    }

    public Apple spawnRandomApple() {
        List<Point> choices = new ArrayList<>();
        List<List<Entity>> rectangle = toRectangle();
        for (int i = 0; i < rectangle.size(); ++i) {
            List<Entity> row = rectangle.get(i);
            for (int j = 0; j < row.size(); ++j) {
                if (row.get(j) == null) {
                    choices.add(new Point(i, j));
                }
            }
        }
        if (choices.isEmpty())
            return null;
        int index = random.nextInt(choices.size());
        Apple apple = Apple.randomApple(random, choices.get(index), this);
        addEntity(apple);
        return apple;
    }

    public List<Entity> getAllEntities() {
        List<Entity> result = new ArrayList<>();
        result.addAll(entities.values());
        return result;
    }

    public List<Entity> getAllSnakes() {
        List<Entity> result = new ArrayList<>();
        for (Entity entity: entities.values()) {
            if (entity instanceof Snake) {
                result.add(entity);
            }
        }
        return result;
    }

    public List<Entity> getAllApples() {
        List<Entity> result = new ArrayList<>();
        for (Entity entity: entities.values()) {
            if (entity instanceof Apple) {
                result.add(entity);
            }
        }
        return result;
    }

    public boolean getWrap() {
        return wrap;
    }
}
