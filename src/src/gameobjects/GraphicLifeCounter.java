package src.gameobjects;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

public class GraphicLifeCounter extends GameObject {
    private static final float HEART_SIZE = 20;
    private final Counter curLifeCount;
    private int prevLifeCount;
    private final GameObjectCollection gameObjects;
    private final GameObject[] listOfHeartGraphics;


    /**
     * Construct a new GameObject instance.
     * Adds the graphic life counter, the hearts to the screen
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     */
    public GraphicLifeCounter(Vector2 topLeftCorner, Vector2 dimensions,
                              Counter curLifeCount, Renderable renderable,
                              GameObjectCollection gameObjectCollection,
                              int numOfLives) {
        super(topLeftCorner, dimensions, renderable);
        this.curLifeCount = curLifeCount;
        this.prevLifeCount = curLifeCount.value();
        this.gameObjects = gameObjectCollection;
        this.listOfHeartGraphics = new GameObject[numOfLives];

        for (int i = 0; i < numOfLives; i++) {
            listOfHeartGraphics[i] = new GameObject( topLeftCorner.add(new Vector2( (HEART_SIZE * 1.5F * i), 0)),
                    new Vector2(HEART_SIZE, HEART_SIZE), renderable);
            gameObjects.addGameObject(listOfHeartGraphics[i], Layer.BACKGROUND);
        }
    }

    /**
     * If the life counter is updated (in BrickerGameManager), then removes one heart from the
     * graphics
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if (curLifeCount.value() < prevLifeCount) {
            gameObjects.removeGameObject(listOfHeartGraphics[curLifeCount.value()], Layer.BACKGROUND);
            prevLifeCount = curLifeCount.value();
        }
    }

}
