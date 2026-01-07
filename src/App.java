import processing.core.*;
import java.util.*;

public class App extends PApplet {
    ArrayList<Brick> bricks = new ArrayList<>();
    // ArrayList<Ball> balls = new ArrayList<>();
    Ball ball = new Ball(this);
    Paddle paddle = new Paddle(this);

    public static void main(String[] args) {
        PApplet.main("App");
    }

    public void setup() {
        bricks = Brick.createBricks(this, 4, 10, 70, 30, 5, 20, 50);
        // balls.add(new Ball(this, width / 2, height / 2));
    }

    public void settings() {
        size(800, 600);

    }

    public void draw() {
        background(200);
        paddle.update();
        paddle.display();
        ball.display();
        for (Brick b : bricks) {
            b.display();
        }
    }
}
