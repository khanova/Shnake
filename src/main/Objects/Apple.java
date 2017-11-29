package main.Objects;

import main.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.BiFunction;

public abstract class Apple extends Entity implements Edible {
    protected Field field;

    public Apple(Point pos, Field field) {
        position = pos;
        this.field = field;
    }

    private final static List<BiFunction<Point, Field, Apple>> appleTypes =
            new ArrayList<BiFunction<Point, Field, Apple>>(){{
                add(AppleSimple::new);
                add(AppleBig::new);
                add(Balloon::new);
                add(AppleSpeed::new);
            }};

    public static Apple randomApple(Random random, Point pos, Field field) {
        int choice = random.nextInt(appleTypes.size());
        return appleTypes.get(choice).apply(pos, field);
    }

    public void tick(Game game) {

    }
}
