package main.Objects;

import main.Snake;

public interface Edible {
    void eatEffect(Snake snake);
    Integer getSpriteId();
}
