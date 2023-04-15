package src.brick_strategies;

import danogl.GameObject;
import danogl.gui.ImageReader;
import danogl.gui.Sound;
import danogl.gui.SoundReader;
import danogl.gui.rendering.ImageRenderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.gameobjects.Ball;
import src.gameobjects.Puck;


public class PuckStrategy extends RemoveBrickStrategyDecorator{
    private static final float PUCK_SPEED = 200;
    private static final float PUCK_RADIUS = 20;

    private final ImageRenderable mockBallImage;
    private final Sound collisionSound;

    /**
     * The strategy that adds three additional balls
     */
    public PuckStrategy(CollisionStrategy toBeDecorated,
                        ImageReader imageReader,
                        SoundReader soundReader) {
        super(toBeDecorated);

        this.mockBallImage = imageReader.readImage("assets/mockBall.png", true);
        this.collisionSound = soundReader.readSound("assets/blop_cut_silenced.wav");
    }

    /**
     * When the brick breaks, three new mock balls jump out of the brick
     */
    @Override
    public void onCollision(GameObject thisObject, GameObject other, Counter curAmountOfBricks) {
        //Removes the brick
        super.onCollision(thisObject, other, curAmountOfBricks);

        Ball newBall = new Puck(thisObject.getTopLeftCorner(), new Vector2(PUCK_RADIUS, PUCK_RADIUS),
                        mockBallImage, collisionSound);
        Ball newBall2 = new Puck(thisObject.getCenter(), new Vector2(PUCK_RADIUS, PUCK_RADIUS),
                mockBallImage, collisionSound);
        Ball newBall3 = new Puck(thisObject.getCenter(), new Vector2(PUCK_RADIUS, PUCK_RADIUS),
                mockBallImage, collisionSound);
        float ballVelocityX = PUCK_SPEED;
        float ballVelocityY = PUCK_SPEED;

        getGameObjectCollection().addGameObject(newBall);
        getGameObjectCollection().addGameObject(newBall2);
        getGameObjectCollection().addGameObject(newBall3);

        newBall.setVelocity(new Vector2(ballVelocityX, ballVelocityY));
        newBall2.setVelocity(new Vector2(ballVelocityX * -1, ballVelocityY));
        newBall3.setVelocity(new Vector2(ballVelocityX, ballVelocityY));
    }
}
