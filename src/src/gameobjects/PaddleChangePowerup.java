package src.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.GameObjectCollection;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

public class PaddleChangePowerup extends GameObject {
    private static final boolean BIGGER_PADDLE = true;
    private static final boolean SMALLER_PADDLE = false;
    private final boolean powerUpType;
    private final GameObjectCollection gameObjects;
    private static final float PADDLE_WIDENING_LENGTH = 30;
    private static final float PADDLE_NARROWING_LENGTH = -30;
    private final Vector2 windowDimentions;

    /**
     * Construct a new GameObject instance.
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param powerUpType   true for BIGGER_PADDLE, false for SMALLER_PADDLE
     */
    public PaddleChangePowerup(Vector2 topLeftCorner, Vector2 dimensions,
                               Renderable renderable, boolean powerUpType,
                               GameObjectCollection gameObjectCollection, Vector2 windowDimentions) {
        super(topLeftCorner, dimensions, renderable);
        this.powerUpType = powerUpType;
        this.gameObjects = gameObjectCollection;
        this.windowDimentions = windowDimentions;
    }

    /**
     * When the powerupIcon collides with the paddle, the powerup is activated
     * and the icon is deleted.
     */
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        if (other instanceof Paddle){
            if (powerUpType == BIGGER_PADDLE) {
                if ((other.getDimensions().add(new Vector2(PADDLE_WIDENING_LENGTH, 0))).x() < windowDimentions.x())
                    other.setDimensions(other.getDimensions().add(new Vector2(PADDLE_WIDENING_LENGTH, 0)));
            }
            else if (powerUpType == SMALLER_PADDLE) {
                if ((other.getDimensions().add(new Vector2(PADDLE_NARROWING_LENGTH, 0))).x() > 0)
                    other.setDimensions(other.getDimensions().add(new Vector2(PADDLE_NARROWING_LENGTH, 0)));
            }
            gameObjects.removeGameObject(this);
        }
    }


}
