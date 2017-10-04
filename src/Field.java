import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.HashMap;

public class Field implements ITickable {
    private HashMap<Point, Entity> entities;
    private int width;
    private int height;
    private boolean wrap;

    private int points;
    private boolean lost;

    public Field(int width, int height, boolean wrap) {
        this.width = width;
        this.height = height;
        this.wrap = wrap;
        entities = new HashMap<>();
        points = 0;
        lost = false;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean getWrap() {
        return wrap;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        if (points < 0)
            throw new IllegalArgumentException();
        this.points = points;
    }

    public boolean getLost() {
        return lost;
    }

    public void lose() {
        lost = true;
    }

    public boolean isInside(Point point) {
        return 0 <= point.x && point.x < width &&
                0 <= point.y && point.y < height;
    }

    public Point wrapPoint(Point point) {
        Point result = new Point(point.x % width, point.y % height);
        if (result.x < 0)
            result.x += width;
        if (result.y < 0)
            result.y += height;
        return result;
    }

    public void tick() {
        ArrayList<Entity> allEntities = new ArrayList<>();
        allEntities.addAll(entities.values());
        for (Entity entity: allEntities) {
            entity.tick();
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
        if (entityAtPoint(entity.position) != null)
            return false;
        entities.put(entity.position, entity);
        return true;
    }

    public ArrayList<ArrayList<Entity>> toRectangle() {
        ArrayList<ArrayList<Entity>> result = new ArrayList<>();
        for (int x = 0; x < width; ++x) {
            ArrayList<Entity> line = new ArrayList<>();
            for (int y = 0; y < height; ++y) {
                line.add(entityAtPoint(new Point(x, y)));
            }
            result.add(line);
        }
        return result;
    }
}
