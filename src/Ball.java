import processing.core.*;

public class Ball {
    private PApplet canvas;
    private float ballX;
    private float ballY;
    private float ballSize = 30;
    private Paddle paddle;
    private float xSpeed = 6;
    private float ySpeed = -6;

    public Ball(PApplet c, Paddle paddle) {
        canvas = c;
        this.paddle = paddle;
        ballX = 400;
        ballY = 400;
    }

    public boolean ballTouchingPaddle() {
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

    public boolean ballTouchingBrick(Brick brick) {
        if (brick.isAlive() != true) {
            return false;
        }
        float closestXOnBrick = PApplet.constrain(ballX, brick.getBrickX(), brick.getBrickX() + brick.getBrickWidth());
        float closestYOnBrick = PApplet.constrain(ballY, brick.getBrickY(), brick.getBrickY() + brick.getBrickHeight());

        return PApplet.dist(ballX, ballY, closestXOnBrick, closestYOnBrick) < ballSize / 2;
    }

    public void bounceOffBrick(Brick brick) {
        if (ballX >= brick.getBrickX() && ballX <= brick.getBrickX() + brick.getBrickWidth()) {
            ySpeed *= -1.02;
        } else {
            xSpeed *= -1.02;
            ballX += xSpeed;
            ballY += ySpeed;
        }
    }

    public void display() {
        canvas.circle(ballX, ballY, ballSize);
    }

    public void update() {
        ballX += xSpeed;
        ballY += ySpeed;
        // ballX = canvas.mouseX;
        // ballY = canvas.mouseY;

        if (ballX < ballSize / 2 || ballX > canvas.width - ballSize / 2) {
            xSpeed *= -1;
        } else if (ballY <= ballSize / 2 && ySpeed < 0) {
            ySpeed *= -1;
            ballY = ballSize / 2;
        }

    }

    public void bounce() {
        if (ySpeed > 0) {
            ySpeed *= -1;
            ballY = paddle.getPaddleY() - ballSize / 2;
        }
    }

    public void reset() {
        xSpeed = 6;
        ySpeed = -6;
        ballX = 400;
        ballY = 400;
    }

    public boolean isDead() {
        return ballY > canvas.height + ballSize;
    }

    public void ballGoToPaddle() {
        ballX = paddle.getPaddleX();
        ballY = paddle.getPaddleY();
    }

}
