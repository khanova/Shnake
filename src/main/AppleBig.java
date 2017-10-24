package main;

public class AppleBig extends Apple {
    public AppleBig(Point pos, Field field) {
        super(pos, field);
    }

    public void eatEffect(Snake snake) {
        snake.setGrowth(snake.getGrowth() + 2);
        field.setPoints(field.getPoints() + 2);
    }

    public void tick() {

    }

    @Override
    public Sprite createSprite() {
        return new AppleBigSprite(this);
    }
}
