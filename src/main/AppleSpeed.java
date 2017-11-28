package main;

public class AppleSpeed extends Apple {
    public AppleSpeed(Point pos, Field field) {
        super(pos, field);
    }

    public void eatEffect(Game game) {
        game.setSpeed(2);
    }

    @Override
    public Sprite createSprite() {
        return new AppleSprite(this, (textureManager -> textureManager.getRainbow()));
    }
}
