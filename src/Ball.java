import processing.core.*;

public class Ball {
    private PApplet canvas;
    private float ballX;
    private float ballY;
    private float ballSize = 30;
    private Paddle paddle;
    private float xSpeed = 6;
    private float ySpeed = -6;
    private int bricksDestroyedCount = 0;

    public Ball(PApplet c, Paddle paddle) {
        canvas = c;
        this.paddle = paddle;
        ballX = 400;
        ballY = 400;
    }
    
    public boolean ballTouchingPaddle() {
        float closestXOnPaddle = canvas.constrain(ballX, paddle.getPaddleX(), paddle.getPaddleX() + paddle.getPaddleWidth());
        float closestYOnPaddle = canvas.constrain(ballY, paddle.getPaddleY(), paddle.getPaddleY() + paddle.getPaddleHeight());

        if (canvas.dist(closestXOnPaddle, closestYOnPaddle, ballX, ballY) < ballSize / 2) {
            return true;
        } else {
            return false;
        }
    }
    public boolean ballTouchingBrick(Brick brick) {
        if(brick.isAlive() != true) {
            return false;
        }
        float closestXOnBrick = canvas.constrain(ballX, brick.getBrickX(), brick.getBrickX() + brick.getBrickWidth());
        float closestYOnBrick = canvas.constrain(ballY, brick.getBrickY(), brick.getBrickY() + brick.getBrickHeight());

        return canvas.dist(ballX, ballY, closestXOnBrick, closestYOnBrick) < ballSize / 2;
    }

    public void bounceOffBrick() {
        ySpeed*= -1.02;
        xSpeed*= 1.02;
    }

    public void display() {
        canvas.circle(ballX,ballY,ballSize);
    }

    public void update() {
        ballX += xSpeed;
        ballY += ySpeed;

        if (ballX < ballSize/2 || ballX > canvas.width - ballSize/2) {
            xSpeed *= -1;
        } else if (ballY <= ballSize/2 && ySpeed < 0) {
            ySpeed *= -1;
            ballY = ballSize/2;
        }
    } 

    public void bounce() {
        if (ySpeed > 0) {
            ySpeed *= -1;
            ballY = paddle.getPaddleY() - ballSize/2;
        }
    }

    public void bricksDestroyedCount() {
        bricksDestroyedCount++;
    }

}
