package src.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.GameObjectCollection;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

public class MockPaddle extends Paddle {
    public static boolean isInstantiated = false;

    private final GameObjectCollection gameObjects;
    private final int numCollisionsToDisappear;
    private int collisionAmount;

    /**
     * Construct a new Paddle instance.
     *
     * @param topLeftCorner       Position of the object, in window coordinates (pixels).
     *                            Note that (0,0) is the top-left corner of the window.
     * @param dimensions          Width and height in window coordinates.
     * @param renderable          The renderable representing the object. Can be null, in which case
     * @param inputListener       Listens for the input of the user
     * @param minDistanceFromEdge The minimum distance from the edge that the paddle comes to contact
     *                            with
     */
    public MockPaddle(Vector2 topLeftCorner,
                      Vector2 dimensions,
                      Renderable renderable,
                      UserInputListener inputListener,
                      Vector2 windowDimensions,
                      GameObjectCollection gameObjects,
                      int minDistanceFromEdge,
                      int numCollisionsToDisappear) {
        super(topLeftCorner, dimensions, renderable, inputListener, windowDimensions, minDistanceFromEdge);
        this.gameObjects = gameObjects;
        this.numCollisionsToDisappear = numCollisionsToDisappear;
        this.collisionAmount = 0;
    }


    /**
     * After other objects collide with the mock paddle for more than
     * numCollisionsToDisappear, then the mockpaddle disappears and is removed
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        collisionAmount++;

        if (collisionAmount >= numCollisionsToDisappear) {
            gameObjects.removeGameObject(this);
            isInstantiated = false;
        }
    }
}
