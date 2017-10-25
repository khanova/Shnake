package main;

public class AppleBig extends Apple {
    public AppleBig(Point pos, Field field) {
        super(pos, field);
        ticks = 0;
    }

    private static final int MAX_TICK = 5;
    private int ticks;

    public void eatEffect(Snake snake) {
        snake.setGrowth(snake.getGrowth() + 2);
        field.setPoints(field.getPoints() + 2);
    }

    public void tick() {
        ticks++;
        if (ticks == MAX_TICK) {
            field.removeEntity(this);
            field.addEntity(new ApplePoison(position, field));
        }
    }

    @Override
    public Sprite createSprite() {
        return new AppleBigSprite(this);
    }
}
