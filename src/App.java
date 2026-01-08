import processing.core.*;
import java.util.*;

public class App extends PApplet {
    ArrayList<Brick> bricks = new ArrayList<>();
    // ArrayList<Ball> balls = new ArrayList<>();
    Paddle paddle = new Paddle(this);
    Ball ball = new Ball(this, paddle);
    

    public static void main(String[] args) {
        PApplet.main("App");
    }

    public void setup() {
        bricks = Brick.createBricks(this, 5, 9, 70, 30, 6, 55, 50);
    }

    public void settings() {
        size(800, 600);

    }

    public void draw() {
        background(200);

        paddle.update();
        paddle.display();

        for (Brick b : bricks) {
            if (ball.ballTouchingBrick(b) == true) {
                b.destroy();
                ball.bounceOffBrick();
                ball.bricksDestroyedCount();
                break;
            }
            b.display();
        }

        ball.update();
        ball.display();

        if (ball.ballTouchingPaddle() == true) {
            ball.bounce();
        }

        
    }
}
