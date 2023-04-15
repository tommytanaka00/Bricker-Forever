package src.brick_strategies;

import danogl.GameObject;
import danogl.gui.ImageReader;
import danogl.gui.WindowController;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.gameobjects.PaddleChangePowerup;

import java.util.Random;

public class ChangePaddleWidthStrategy extends RemoveBrickStrategyDecorator {
    private static final int POWER_UP_LENGTH = 43;
    private static final int POWER_UP_HEIGHT = 30;
    private static final Random RANDOM = new Random();
    private static final boolean BIGGER_PADDLE = true;
    private static final boolean SMALLER_PADDLE = false;
    private static final float POWER_UP_FALLING_SPEED = 100;
    private final ImageReader imageReader;
    private final WindowController windowController;

    /**
     * The strategy that drops a powerup that widens or reduces
     * the paddle
     */
    public ChangePaddleWidthStrategy(CollisionStrategy toBeDecorated, ImageReader imageReader, WindowController windowController) {
        super(toBeDecorated);
        this.imageReader = imageReader;
        this.windowController = windowController;
    }

    /**
     * Drops the powerup icon when the brick breaks
     */
    @Override
    public void onCollision(GameObject thisObject, GameObject other, Counter curAmountOfBricks){
        super.onCollision(thisObject, other, curAmountOfBricks);
        Renderable powerUpRender = null;
        boolean powerUpType = RANDOM.nextBoolean();
        if (powerUpType == BIGGER_PADDLE)
        {
            powerUpRender = imageReader.readImage("assets/buffWiden.png", true); //todo maybe false?
        }
        else if (powerUpType == SMALLER_PADDLE)
        {
            powerUpRender = imageReader.readImage("assets/buffNarrow.png", true);
        }

        GameObject powerupIcon = new PaddleChangePowerup(thisObject.getCenter(),
                new Vector2(POWER_UP_LENGTH, POWER_UP_HEIGHT),
                powerUpRender,
                powerUpType,
                getGameObjectCollection(), windowController.getWindowDimensions());

        powerupIcon.setVelocity(new Vector2(Vector2.DOWN.mult(POWER_UP_FALLING_SPEED)));
        getGameObjectCollection().addGameObject(powerupIcon);//,  Layer.BACKGROUND);
    }
}
