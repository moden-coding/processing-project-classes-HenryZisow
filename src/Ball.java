import processing.core.*;
import java.util.*;
import java.io.PrintWriter;
import java.nio.file.Paths;

public class Ball {
    private PApplet canvas;
    private float ballX; // initializes the ball x value
    private float ballY; // initializes the ball y value
    private float ballSize = 30; // initializes the ball size
    private Paddle paddle; // initializes the paddle for this class
    private float xSpeed = 6; // initializes the ball x speed
    private float ySpeed = -6; // initializes the ball y speed

    public Ball(PApplet c, Paddle paddle) { // ball constructor
        canvas = c;
        this.paddle = paddle;
        ballX = 400;
        ballY = 400;
    }

    public Ball(PApplet c, Paddle paddle, float sX, float sY) { // ball constructor which adds the speed variables for
                                                                // the ball so when the bricks are regenerated the speed
                                                                // can stay the same
        this(c, paddle);
        xSpeed = sX;
        ySpeed = sY;

    }

    public boolean ballTouchingPaddle() { //method to check if the ball touches the paddle
        float closestXOnPaddle = PApplet.constrain(ballX, paddle.getPaddleX(),
                paddle.getPaddleX() + paddle.getPaddleWidth());
        float closestYOnPaddle = PApplet.constrain(ballY, paddle.getPaddleY(),
                paddle.getPaddleY() + paddle.getPaddleHeight());

        if (PApplet.dist(closestXOnPaddle, closestYOnPaddle, ballX, ballY) < ballSize / 2) {
            return true;
        } else {
            return false;
        }
    }

    public boolean ballTouchingBrick(Brick brick) { //method to check if the ball is touching the bricks
        if (brick.isAlive() != true) {
            return false;
        }
        float closestXOnBrick = PApplet.constrain(ballX, brick.getBrickX(), brick.getBrickX() + brick.getBrickWidth());
        float closestYOnBrick = PApplet.constrain(ballY, brick.getBrickY(), brick.getBrickY() + brick.getBrickHeight());

        return PApplet.dist(ballX, ballY, closestXOnBrick, closestYOnBrick) < ballSize / 2;
    }

    public void bounceOffBrick(Brick brick) { //method which makes it so that when the ball touches the brick it "bounces" off (speed gets multiplied by -1)
        if (ballX >= brick.getBrickX() && ballX <= brick.getBrickX() + brick.getBrickWidth()) {
            ySpeed *= -1.02;
            xSpeed *= 1.02;
        } else {
            xSpeed *= -1.02;
            ySpeed *= 1.02;
            ballX += xSpeed;
            ballY += ySpeed;
        }
    }

    public void display() { //displays the ball on the screen
        canvas.circle(ballX, ballY, ballSize);
    }

    public void update() { //method that updates the ball speed and makes the ball bounce off the walls
        ballX += xSpeed;
        ballY += ySpeed;

        if (ballX < ballSize / 2 || ballX > canvas.width - ballSize / 2) {
            xSpeed *= -1;
        }

        if (ballY <= ballSize / 2 && ySpeed < 0) {
            ySpeed *= -1;
            ballY = ballSize / 2;
        }

        if (ballTouchingPaddle() && ySpeed > 0) {
            bounce();
        }
    }

    public void bounce() { //method which allows the ball to bounce off of things
        if (ySpeed > 0) {
            ySpeed *= -1;
            ballY = paddle.getPaddleY() - ballSize / 2;
        }
    }

    public boolean isDead() { //method to check is the ball is dead or alive
        return ballY > canvas.height + ballSize;
    }

    public void ballGoToPaddle() { //makes the ball go to the paddle
        ballX = paddle.getPaddleX();
        ballY = paddle.getPaddleY();
    }

    public float getBallX() { //returns the balls x value
        return ballX;
    }

    public float getBallY() { //returns the balls y value
        return ballY;
    }

    public void setPosition(float x, float y) { //sets the ball position
        ballX = x;
        ballY = y;
    }

    public void setVelocity(float xspeed, float yspeed) { //sets the ball velocity
        xSpeed = xspeed;
        ySpeed = yspeed;
    }

    public float getBallXSpeed() { //returns the balls x speed
        return xSpeed;
    }

    public float getBallYSpeed() { //returns the balls y speed
        return ySpeed;
    }

}
