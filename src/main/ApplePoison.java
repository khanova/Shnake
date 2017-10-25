package main;

public class ApplePoison extends Apple {
    public ApplePoison(Point pos, Field field) {
        super(pos, field);
    }

    public void eatEffect(Snake snake) {
        snake.setGrowth(snake.getGrowth() - 1);
        field.setPoints(field.getPoints() - 2);
    }

    public void tick() {
    }

    @Override
    public Sprite createSprite() {
        return new ApplePoisonSprite(this);
    }
}
