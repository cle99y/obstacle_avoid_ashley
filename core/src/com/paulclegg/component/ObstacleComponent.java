package com.paulclegg.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

/**
 * Created by cle99 on 24/04/2017.
 * used for avoidance obstacles
 * and collectable tokens for health and points
 */

public class ObstacleComponent implements Component, Pool.Poolable {
    public final ObjectType type = ObjectType.OBSTACLE;
    public boolean hit;

    @Override
    public void reset() {
        hit = false;
    }

}
