import processing.core.*;

import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.*;

public class App extends PApplet {
    ArrayList<Brick> bricks = new ArrayList<>();
    // ArrayList<Ball> balls = new ArrayList<>();
    Paddle paddle = new Paddle(this);
    Ball ball = new Ball(this, paddle);
    private int score = 0;
    private int highscore = 0;
    private int totalBricks;
    private int scene = 1;

    public static void main(String[] args) {
        PApplet.main("App");
    }

    public void setup() {
        if (scene == 1) {
            readHighScore();
            totalBricks = 5 * 9;
            bricks = Brick.createBricks(this, 5, 9, 70, 33, 6, 55, 50);
        }

    }

    public void readHighScore() {
        try (Scanner scanner = new Scanner(Paths.get("highscore.txt"))) {

            while (scanner.hasNextLine()) {
                String row = scanner.nextLine();
                highscore = Integer.valueOf(row);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void settings() {
        size(800, 600);

    }

    public void draw() {
        if (scene == 1) {
            background(200);

            paddle.update();
            paddle.display();

            ball.update();
            ball.display();

            for (Brick b : bricks) {
                if (ball.ballTouchingBrick(b) == true) {
                    b.destroy();
                    ball.bounceOffBrick(b);
                    totalBricks--;
                    score++;
                    break;
                }
                b.display();
            }

            for (int i = bricks.size() - 1; i >= 0; i--) {
                Brick b = bricks.get(i);

                if (ball.ballTouchingBrick(b)) {
                    ball.bounceOffBrick(b);
                    bricks.remove(i);
                    totalBricks--;
                    score++;
                    break;
                }
                b.display();
            }

            if (totalBricks == 0) {
                regenerateBricks();
            }

            if (ball.ballTouchingPaddle() == true) {
                ball.bounce();
            }

            if (score > highscore) {
                highscore = score;
                saveHighScore();
            }

            fill(0);
            PFont font;
            font = createFont("font1.ttf", 128);
            textFont(font);
            textAlign(LEFT);
            textSize(16);
            text("Score: " + score, 20, 560);
            text("High Score: " + highscore, 20, 580);
        }

        if (ball.isDead() == true) {
            scene = 2;
        }

        if (scene == 2) {
            background(0);
            fill(255);
            PFont font;
            font = createFont("font.ttf", 128);
            textFont(font);
            textAlign(CENTER);
            textSize(90);
            fill(225, 0, 0);
            text("Game Over", width / 2, height / 2 - 20);

            textSize(35);
            fill(255);
            text("Score: " + score, width / 2, height / 2 + 31);
            text("Press R to Restart", width / 2, height / 2 + 80);
        }

    }

    public void saveHighScore() {
        try {
            PrintWriter writer = new PrintWriter("highscore.txt");
            writer.println(highscore);
            writer.close();
        } catch (Exception e) {
            System.out.println("Error saving high score");
        }
    }

    public void regenerateBricks() {
        totalBricks = 5 * 9;
        bricks = Brick.createBricks(this, 5, 9, 70, 33, 6, 55, 50);
        ball.ballGoToPaddle();
    }

    public void restartGame() {
        score = 0;
        bricks = Brick.createBricks(this, 5, 9, 70, 33, 6, 55, 50);
        ball.reset();
        scene = 1;
    }

    public void keyPressed() {
        if (scene == 2 && key == 'r') {

            restartGame();
            ball.reset();
        }
    }

}
