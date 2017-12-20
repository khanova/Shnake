package main;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private static ExecutorService threadPool = Executors.newFixedThreadPool(2);

    /**
     * @param args
     */
    public static void main(String[] args) {
        Game game = new Game(new Field(7, 7, true));
        game.spawnWall(new Point(1, 3));
        game.spawnWall(new Point(3, 1));
        game.spawnWall(new Point(5, 3));
        game.spawnWall(new Point(3, 5));

        game.spawnRandomApple();

        int id = 0;
        try (ServerSocket server = new ServerSocket(4004)) {
            System.out.println("Server socket created.");

            while (!server.isClosed()) {

                Socket client = server.accept();

                threadPool.execute(new ThreadClientSnake(client, id++, game));
                System.out.print("Connection accepted.");
            }

            // закрытие пула нитей после завершения работы всех нитей
            threadPool.shutdown();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}