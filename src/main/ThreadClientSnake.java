package main;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ThreadClientSnake implements Runnable {
    Socket client;
    int id;
    Snake snake;
    Game game;
    public ThreadClientSnake(Socket client, int id, Game game) {
        this.client = client;
        this.id = id;
        snake = game.spawnRandomSnake(id);
        this.game = game;
    }

    @Override
    public void run() {
        try {
            game.incClientsCount();
            ObjectOutputStream outputStream = new ObjectOutputStream(client.getOutputStream());
            ObjectInputStream inputStream = new ObjectInputStream(client.getInputStream());
            while (!client.isClosed()) {
                System.out.println("Waiting...");
                String command = (String) inputStream.readObject();
                System.out.println("Client with id " + id + " write: " + command);
                if (command.equals("I alive?")) {
                    outputStream.writeBoolean(snake.getAlive());
                    outputStream.flush();
                } else if (command.equals("Direction")) {
                    int dir = inputStream.readInt();
                    snake.setDirection(dir);
                }
                else if (command.equals("Field")) {
                    game.incReady();
                    while (game.getReady() != game.getClientsCount()) {

                    }
                    outputStream.writeObject(game.getFieldWithMainSnake(snake));
                    game.tick();
                    outputStream.flush();
                }

            }
        }
        catch (IOException e) {

        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        game.decClientsCount();
        game.removeEntity(snake);
    }
}
