package main;

public class Wall extends Apple {
    public Wall(Point pos, Field field) {
        super(pos, field);
    }

    @Override
    public void eatEffect(Game game) {
        game.lose();
    }

    @Override
    public Sprite createSprite() {
        return new AppleSprite(this, (textureManager -> textureManager.getWall()));
    }
}
