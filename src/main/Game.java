package main;

import main.Objects.Apple;
import main.Objects.Wall;
import main.PowerUps.PowerUp;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.BiFunction;

public class Game {
    private Field field;
    private int ready;
    private ArrayList<Snake> snakes;
    private int tickCount;

    private Lock fieldLock;
    private Lock readyCounterLock;
    private Lock clientsCounterLock;
    private int clientsCount;

    public Game(Field field) {
        this.field = field;
        clientsCount = 0;
        tickCount = 0;
        ready = 0;
        snakes = new ArrayList<>();
        fieldLock = new ReentrantLock();
        readyCounterLock = new ReentrantLock();
        clientsCounterLock = new ReentrantLock();
    }

    public void tick() {
        if (getReady() == 0)
            return;
        System.out.println("tick...");
        System.out.flush();
        resetReady();
        ++tickCount;
        field.tick(this);
    }

    public Field getField() { return field; }

    public Wall spawnWall(Point position) { return field.spawnWall(position); }

    public void spawnRandomApple() {
        field.spawnRandomApple();
    }

    public void addEntity(Entity entity) { field.addEntity(entity); }

    public void removeEntity(Entity entity) { field.removeEntity(entity); }

    public Snake spawnRandomSnake(int id) {
        fieldLock.lock();
        try {
            snakes.add(field.spawnRandomSnake(id));
            return snakes.get(snakes.size() - 1);
        }
        finally {
            fieldLock.unlock();
        }
    }

    public int getTickCount() {
        return tickCount;
    }

    public int getReady() {
        try {
            readyCounterLock.lock();
            return ready;
        }
        finally {
            readyCounterLock.unlock();
        }
    }

    public void incReady() {
        ready += 1;
    }

    public void resetReady() {
        ready = 0;
    }

    public List<List<Integer>> getFieldWithMainSnake(Snake snake) {
        fieldLock.lock();
        try {
            field.setMainSnake(snake);
            return field.toSpritesId();
        }
        finally {
            fieldLock.unlock();
        }
    }

    public int getClientsCount() {
        clientsCounterLock.lock();
        try {
            return clientsCount;
        }
        finally {
            clientsCounterLock.unlock();
        }
    }

    public void incClientsCount() {
        clientsCounterLock.lock();
        try {
            this.clientsCount++;
        }
        finally {
            clientsCounterLock.unlock();
        }
    }

    public void decClientsCount() {
        clientsCounterLock.lock();
        try {
            this.clientsCount--;
        }
        finally {
            clientsCounterLock.unlock();
        }
    }
}
