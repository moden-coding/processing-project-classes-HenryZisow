import processing.core.*;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.*;

public class App extends PApplet {
    private ArrayList<Brick> bricks = new ArrayList<>(); //Creates an arraylist for the bricks
    private ArrayList<Ball> balls = new ArrayList<>(); //Creates an arraylist for the balls
    private ArrayList<Powerup> powerups = new ArrayList<>(); //Creates an arraylist for the powerups
    private Paddle paddle = new Paddle(this); //Adds a paddle to the app class
    private int score = 0; //Initializes the game score
    private int highscore = 0; //Starts the highscore at 0
    private int totalBricks; //Sums up the number of bricks in its arraylist
    private int scene = 3; //starts the game at the title screen
    private boolean gotHighScore; //boolean to check if the score was greater than the highscore
    private PImage tripleBallImg;  //Used chatGPT to help make the powerup an image

    public static void main(String[] args) {
        PApplet.main("App");
    }

    public void setup() {
        readHighScore(); //finds the highscore from the file and sets it

        tripleBallImg = loadImage("tripleball.png"); //loads the powerup image

        balls.clear(); //removew all balls from the arraylist
        balls.add(new Ball(this, paddle)); //adds one new ball

        totalBricks = 5 * 9; //initiates a 5x9 brick formation
        bricks = Brick.createBricks(this, 5, 9, 70, 33, 6, 55, 50); //creates the bricks

    }

    public void readHighScore() { //reads the highscore value from the highscore.txt file
        try (Scanner scanner = new Scanner(Paths.get("highscore.txt"))) {

            while (scanner.hasNextLine()) {
                String row = scanner.nextLine();
                highscore = Integer.valueOf(row);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void settings() { //creates the 800x600 pixel screen
        size(800, 600);

    }

    public void draw() {

        background(200); //creates a gray background
        if (scene == 1) {
            background(200);

            paddle.update(); //updates the paddles position
            paddle.display(); //shows the paddle

            for (int i = balls.size() - 1; i >= 0; i--) { //Goes through the ball arraylist and repeatedly updates and displays the ball using the methods
                Ball b = balls.get(i);
                b.update();
                b.display();

                if (b.isDead()) { //removes a ball if the isDead method is called
                    balls.remove(i);
                }
            }

            for (int i = bricks.size() - 1; i >= 0; i--) { //loops through the bricks and checks if they got hit
                Brick brick = bricks.get(i);
                boolean brickHit = false;

                for (int b = balls.size() - 1; b >= 0; b--) {
                    Ball ball = balls.get(b);

                    if (ball.ballTouchingBrick(brick)) { //checks if the ball is touching any of the bricks, and if it is updates the variables accordingly
                        ball.bounceOffBrick(brick);
                        bricks.remove(i);
                        totalBricks--;
                        score++;
                        brickHit = true;
                        if (random(1) < 0.1) { //randomly generates a float 0 to 1, and if it is less than 0.1 it spawns a powerup in
                            powerups.add(new Powerup(this, 30, 25,
                                    brick.getBrickX() + brick.getBrickWidth() / 2 - 15,
                                    brick.getBrickY() + brick.getBrickHeight() / 2 - 15,
                                    paddle, tripleBallImg));
                        }
                        break;
                    }
                }

                if (brickHit == false) { //displays bricks if they haven't been hit, but stops displaying them the moment they have been hit
                    brick.display();
                }
            }

            for (int i = powerups.size() - 1; i >= 0; i--) { //loops through the powerups arraylist and updates everything aaccordingly
                Powerup p = powerups.get(i);
                p.update();
                p.display();

                if (p.hitsPaddle()) {
                    tripleBall();
                    powerups.remove(i);
                } else if (p.isOffScreen() == true || p.isActive() == false) {
                    powerups.remove(i);
                }
            }

            if (totalBricks == 0) { //if there are no bricks left, regenerates all the bricks to how they were before the game so the player can continue playing
                regenerateBricks();
            }

            fill(0); //displays the score and highscore on the screen
            PFont font;
            font = createFont("font1.ttf", 128); //used chatGPT to add a cool font
            textFont(font);
            textAlign(LEFT);
            textSize(16);
            text("Score: " + score, 20, 560);
            text("High Score: " + highscore, 20, 580);
            fill(0);
            textSize(16);
        }
        for (int i = powerups.size() - 1; i >= 0; i--) {
            Powerup p = powerups.get(i);
            p.update();
            p.display();

            if (p.hitsPaddle()) {
                tripleBall();
                powerups.remove(i);
            } else if (p.isOffScreen()) {
                powerups.remove(i);
            }
        }

        if (scene == 1 && balls.isEmpty()) {
            if (score > highscore) {
                gotHighScore = true;
                highscore = score;
                saveHighScore();
            }
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

            if (gotHighScore == true) {
                textSize(35);
                fill(0, 255, 0);
                text("You Got the High Score!", width / 2, height / 2 + 130);
            }
        }

        if (scene == 3) { // Used ChatGPT to make formatting this screen easier
            background(40);
            fill(255, 126, 0);
            PFont font;
            font = createFont("font1.ttf", 128);
            textFont(font);
            textAlign(CENTER);

            textSize(75);
            text("BREAKOUT", width / 2, height / 2 - 60);

            textSize(30);
            fill(255);
            text("Move paddle: Mouse", width / 2, height / 2 + 45);

            fill(0, 200, 30);
            text("Press SPACE to start", width / 2, height / 2 + 130);
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

        if (balls.isEmpty() == false) {
            Ball oldBall = balls.get(0);

            float xS = oldBall.getBallXSpeed();
            float yS = oldBall.getBallYSpeed();

            balls.clear();
            Ball newBall = new Ball(this, paddle, xS, yS);
            newBall.ballGoToPaddle();
            balls.add(newBall);
        }
        powerups.clear();
    }

    public void restartGame() {
        score = 0;
        totalBricks = 5 * 9;
        bricks = Brick.createBricks(this, 5, 9, 70, 33, 6, 55, 50);
        balls.clear();
        balls.add(new Ball(this, paddle));
        scene = 1;
    }

    public void keyPressed() {
        if (scene == 2 && key == 'r') {
            restartGame();
            gotHighScore = false;
        }

        if (scene == 3 && key == ' ') {
            gotHighScore = false;
            restartGame();
        }
    }

    public void tripleBall() {
        ArrayList<Ball> newBalls = new ArrayList<>();

        for (Ball b : balls) {
            for (int i = 0; i < 2; i++) {
                Ball ball = new Ball(this, paddle);
                ball.setPosition(b.getBallX(), b.getBallY());
                ball.setVelocity(ball.getBallXSpeed(), ball.getBallYSpeed());
                newBalls.add(ball); // Used chatGPT to help add the balls to the arraylist at the same time
            }
        }
        balls.addAll(newBalls); // Used chatGPT to help add the balls to the arraylist at the same time
    }

}
