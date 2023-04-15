package src.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

public class Ball extends GameObject {
    private final Sound collisionSound;
    private int collisionCount;

    /**
     * Construct a new Ball
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     * @param collisionSound The collision sound when the ball collides with an object
     */
    public Ball(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                    Sound collisionSound) {
        super(topLeftCorner, dimensions, renderable);
        this.collisionSound = collisionSound;
        this.collisionCount = 0;
    }

    /**
     * On collision, bounces the ball back
     * @param other Other game object that the ball collided with
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        if (other instanceof PaddleChangePowerup) //todo: bad code
        {
            return;
        }
        Vector2 newVelocity = getVelocity().flipped(collision.getNormal());
        setVelocity(newVelocity);
        collisionCount++;
        collisionSound.play();

    }

    /**
     * Returns the amount of times the ball collided with a game object
     */
    public int getCollisionCount(){
        return collisionCount;
    }
}
