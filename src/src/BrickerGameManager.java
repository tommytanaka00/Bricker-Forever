package src;


import src.brick_strategies.BrickStrategyFactory;
import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.components.CoordinateSpace;
import danogl.gui.*;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import danogl.util.Counter;
import src.brick_strategies.CollisionStrategy;
import src.gameobjects.*;

import java.util.Random;

public class BrickerGameManager extends GameManager{

    //General
    public static final int BORDER_WIDTH = 2;
    private static final int EPSILON = 4;
    private static final float OBJECT_HEIGHT_LENGTH = 15;

    //For the Window
    private static final float X_DIMENSION_LENGTH = 700;
    private static final float Y_DIMENSION_LENGTH = 500;
    private Vector2 windowDimensions;
    private WindowController windowController;

    //For the Ball
    private static final float BALL_SPEED = 200;
    private static final float BALL_RADIUS = 20;
    private GameObject ball;

    //For the Paddle
    private static final float PADDLE_STARTING_LENGTH = 100;
    private static final int MIN_DISTANCE_FROM_SCREEN_EDGE = 5;

    //For the Bricks
    private static final int BRICK_AMOUNT_IN_ROW = 8;
    private static final int ROW_AMOUNT = 5;

    //For the life counter
    private static final int AMOUNT_OF_LIVES = 4;
    private GraphicLifeCounter heartGraphics;
    private NumericLifeCounter heartText;
    private Counter curAmountOfBricks;
    private Counter curLifeCount;

    //-------------Public methods-----------------
    public BrickerGameManager(String windowTitle, Vector2 windowDimensions){
        super(windowTitle, windowDimensions);
    }

    @Override
    public void initializeGame(ImageReader imageReader,
                               SoundReader soundReader,
                               UserInputListener inputListener,
                               WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener, windowController);


        this.curAmountOfBricks = new Counter(0);
        this.curLifeCount = new Counter(AMOUNT_OF_LIVES);
        this.windowController = windowController;
        this.windowDimensions = windowController.getWindowDimensions();

        createBackGround(imageReader, windowDimensions);

        createWallToBounceAwayFrom(windowDimensions);

        createLifeCounter(imageReader);

        createBall(imageReader, soundReader);

        createPaddle(imageReader, inputListener, windowDimensions);


        BrickStrategyFactory brickStrategyFactory = new BrickStrategyFactory(gameObjects(),
                this,
                            imageReader,
                            soundReader,
                            inputListener,
                            windowController,
                            windowDimensions);
        createBricks(imageReader, brickStrategyFactory, windowDimensions);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        heartGraphics.update(deltaTime);
        heartText.update(deltaTime);
        checkIfBallFellThrough();
        checkIfGameEnded();
    }


    //-------------Private methods-----------------
    private void checkIfBallFellThrough() {
        double ballHeight = ball.getCenter().y();
        if (ballHeight > windowDimensions.y()) {
            curLifeCount.decrement();
            createBallFromBeginning();
        }
    }

    private void checkIfGameEnded() {
        String prompt = "";
        if (curAmountOfBricks.value() <= 0) {
            prompt = "You won!";
        }
        if (curLifeCount.value() <= 0) {
            //lose
            prompt = "You lost!";
            curLifeCount.increaseBy(AMOUNT_OF_LIVES);
        }
        if (!prompt.isEmpty()) {
            prompt += " Play again?";
            if (windowController.openYesNoDialog(prompt))
                windowController.resetGame();
            else
                windowController.closeWindow();
        }
    }

    private void createBackGround(ImageReader imageReader, Vector2 windowDimensions){
        GameObject background = new GameObject(
                Vector2.ZERO,
                windowDimensions,
                imageReader.readImage("assets/DARK_BG2_small.jpeg", true));

        gameObjects().addGameObject(background, Layer.BACKGROUND);
        background.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);

    }

    private void createBall(ImageReader imageReader, SoundReader soundReader){

        Renderable ballImage = imageReader.readImage("assets/ball.png", true);
        Sound collisionSound = soundReader.readSound("assets/blop_cut_silenced.wav");
        //Creating ball
        GameObject ball = new Ball(Vector2.ZERO, new Vector2(BALL_RADIUS,  BALL_RADIUS), ballImage, collisionSound);
        this.ball = ball;
        createBallFromBeginning();
        gameObjects().addGameObject(ball, Layer.DEFAULT);
    }

    private void createBallFromBeginning(){
        float ballVelocityX = BALL_SPEED;
        float ballVelocityY = BALL_SPEED;
        Random random  = new Random();
        if(random.nextBoolean())
            ballVelocityX *= -1;
        if(random.nextBoolean())
            ballVelocityY *= -1;

        ball.setVelocity(new Vector2(ballVelocityX, ballVelocityY));
        ball.setCenter(windowDimensions.mult(0.5F));
    }

    private void createPaddle(ImageReader imageReader, UserInputListener inputListener, Vector2 windowDimensions) {
        //Create user paddle
        Renderable paddleImage = imageReader.readImage("assets/paddle.png", true);
        GameObject userPaddle = new Paddle(Vector2.ZERO,
                new Vector2(PADDLE_STARTING_LENGTH, OBJECT_HEIGHT_LENGTH),
                paddleImage,
                inputListener,
                windowDimensions,
                MIN_DISTANCE_FROM_SCREEN_EDGE
        );
        userPaddle.setCenter(new Vector2(windowDimensions.x()/2, (int) windowDimensions.y() - 30));
        gameObjects().addGameObject(userPaddle, Layer.DEFAULT);
    }


    private void createBricks(ImageReader imageReader, BrickStrategyFactory brickStrategyFactory, Vector2 windowDimensions) {

        Renderable brickImage = imageReader.readImage("assets/brick.png", false);
        float brickLength = (windowDimensions.x() - 2 * (BORDER_WIDTH + EPSILON)) / BRICK_AMOUNT_IN_ROW;
        for (int row = 0; row < ROW_AMOUNT; row++) {
            for (int brickAmount = 0; brickAmount < BRICK_AMOUNT_IN_ROW; brickAmount++) {
                CollisionStrategy collisionStrategy = brickStrategyFactory.getStrategy();

                GameObject brick = new Brick(Vector2.ZERO, new Vector2(brickLength, OBJECT_HEIGHT_LENGTH),
                        brickImage, collisionStrategy, curAmountOfBricks);

                brick.setTopLeftCorner(new Vector2((BORDER_WIDTH + EPSILON) + (brickLength * brickAmount), (BORDER_WIDTH + EPSILON) + (OBJECT_HEIGHT_LENGTH * row)));
                gameObjects().addGameObject(brick, Layer.STATIC_OBJECTS);
                curAmountOfBricks.increment();
            }
        }
    }

    private void createWallToBounceAwayFrom(Vector2 windowDimensions) {

        GameObject leftWall = new GameObject(Vector2.ZERO,
                new Vector2(BORDER_WIDTH, windowDimensions.y()),
                null);

        GameObject topWall = new GameObject(Vector2.ZERO,
                new Vector2(windowDimensions.x(), BORDER_WIDTH),
                null);

        GameObject rightWall = new GameObject(new Vector2(windowDimensions.x(), 0),
                new Vector2(BORDER_WIDTH, windowDimensions.y()),
                null);

        gameObjects().addGameObject(leftWall, Layer.DEFAULT);
        gameObjects().addGameObject(topWall, Layer.DEFAULT);
        gameObjects().addGameObject(rightWall, Layer.DEFAULT);
    }

    private void createLifeCounter(ImageReader imageReader){
        Renderable heartImage = imageReader.readImage("assets/heart.png", true);
        this.heartGraphics = new GraphicLifeCounter(new Vector2(BORDER_WIDTH + EPSILON, windowDimensions.y() - 30),
                Vector2.ZERO, this.curLifeCount,
                heartImage, gameObjects(), AMOUNT_OF_LIVES);
        this.heartText = new NumericLifeCounter(curLifeCount, new Vector2(BORDER_WIDTH + EPSILON, windowDimensions.y() - 50),
                new Vector2(OBJECT_HEIGHT_LENGTH, OBJECT_HEIGHT_LENGTH), gameObjects());
    }




    public static void main(String[] args) {
        new BrickerGameManager("Bouncing Ball",
                new Vector2(X_DIMENSION_LENGTH, Y_DIMENSION_LENGTH)).run();
    }
}
