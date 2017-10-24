package main;

public class Apple extends Entity {
    protected Field field;

    public Apple(Point pos, Field field) {
        position = pos;
        this.field = field;
    }

    public void eatEffect(Snake snake) {
        snake.setGrowth(snake.getGrowth() + 1);
        field.setPoints(field.getPoints() + 1);
    }

    public void tick() {

    }

    @Override
    public Sprite createSprite() {
        return new AppleSprite(this);
    }
}
