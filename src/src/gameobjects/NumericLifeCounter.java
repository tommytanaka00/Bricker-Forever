package src.gameobjects;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Counter;
import danogl.util.Vector2;

import java.awt.*;

public class NumericLifeCounter extends GameObject {
    private final Counter curLifeCount;
    private final TextRenderable textRenderable;
    private int prevLifeCount;

    /**
     * Construct a new GameObject instance.
     * Adds the numeric life counter to the screen
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     */
    public NumericLifeCounter(Counter curLifeCount,
                              Vector2 topLeftCorner,
                              Vector2 dimensions,
                              GameObjectCollection gameObjectCollection) {
        super(topLeftCorner, dimensions, null);
        this.curLifeCount = curLifeCount;
        this.prevLifeCount = curLifeCount.value();
        this.textRenderable = new TextRenderable("Amount of lives left: " + this.curLifeCount.value());
        this.textRenderable.setColor(Color.WHITE);
        GameObject livesLeftText = new GameObject(topLeftCorner, dimensions, this.textRenderable);

        gameObjectCollection.addGameObject(livesLeftText, Layer.BACKGROUND);
    }

    /**
     * If the life counter is updated (in BrickerGameManager), then subtracts one life from
     * the amount of lives text
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if (curLifeCount.value() < prevLifeCount)
            this.textRenderable.setString("Amount of lives left: " + this.curLifeCount.value());
            prevLifeCount = curLifeCount.value();
    }

}
