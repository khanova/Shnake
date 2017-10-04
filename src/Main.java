import com.sun.deploy.util.SyncAccess;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Main {
    public static void printField(Field field) {
        ArrayList<ArrayList<Entity>> rectangle = field.toRectangle();
        for (ArrayList<Entity> line: rectangle) {
            for (Entity entity: line) {
                if (entity == null) {
                    System.out.print('.');
                }
                else if (entity instanceof Snake) {
                    System.out.print('#');
                }
                else if (entity instanceof Apple) {
                    System.out.print('O');
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Field field = new Field(5, 5, true);
        field.addEntity(new Snake(new Point(1, 2), 0, field));
        field.addEntity(new Apple(new Point(0, 2), field));
        field.addEntity(new Apple(new Point(2, 2), field));
        field.addEntity(new Apple(new Point(3, 2), field));
        field.addEntity(new Apple(new Point(4, 2), field));
        printField(field);

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                field.tick();
                if (field.getLost())
                    timer.cancel();

                printField(field);
            }
        }, 1000, 1000);
    }
}
