package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.BiFunction;

public class Apple extends Entity {
    public Apple(Point pos, Field field) {
        position = pos;
        this.field = field;
    }

    private final static List<BiFunction<Point, Field, Apple>> appleTypes =
            new ArrayList<BiFunction<Point, Field, Apple>>(){{
                add(Apple::new);
                add(AppleBig::new);
            }};

    public static Apple randomApple(Random random, Point pos, Field field) {
        int choice = random.nextInt(appleTypes.size());
        return appleTypes.get(choice).apply(pos, field);
    }

    protected Field field;

    public void eatEffect(Game game) {
        game.addGrowth(1);
        game.addPoints(1);
    }

    public void tick(Game game) {

    }

    @Override
    public Sprite createSprite() {
        return new AppleSprite(this);
    }
}
