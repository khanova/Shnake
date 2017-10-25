package main;

public class Wall extends Apple {
    public Wall(Point pos, Field field) {
        super(pos, field);
    }

    @Override
    public void eatEffect(Snake snake) {
        field.lose();
    }

    @Override
    public Sprite createSprite() {
        return new WallSprite(this);
    }
}
