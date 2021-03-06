package main;

import javax.swing.*;
import java.awt.*;

public class TextureManager {
    private Image head, body, apple, appleBig, grass, applePoison, wall, balloon, rainbow;

    public TextureManager() {
        ImageIcon ii = new ImageIcon("src/images/head.png");
        head = ii.getImage();
        ii = new ImageIcon("src/images/body.png");
        body = ii.getImage();
        ii = new ImageIcon("src/images/apple.png");
        apple = ii.getImage();
        ii = new ImageIcon("src/images/appleBig.png");
        appleBig = ii.getImage();
        ii = new ImageIcon("src/images/field.png");
        grass = ii.getImage();
        ii = new ImageIcon("src/images/applePoison.png");
        applePoison = ii.getImage();
        ii = new ImageIcon("src/images/wall.png");
        wall = ii.getImage();
        ii = new ImageIcon("src/images/balloons.png");
        balloon = ii.getImage();
        ii = new ImageIcon("src/images/rainbow.png");
        rainbow = ii.getImage();
    }

    public Image getHead() {
        return head;
    }

    public Image getBody() {
        return body;
    }

    public Image getApple() {
        return apple;
    }

    public Image getAppleBig() {
        return appleBig;
    }

    public Image getGrass() {
        return grass;
    }

    public Image getApplePoison() { return applePoison; }

    public Image getWall() {return wall;}

    public Image getBalloon() {return balloon;}

    public Image getRainbow() {
        return rainbow;
    }
}
