package com.paulclegg.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

/**
 * Created by cle99 on 20/04/2017.
 */

public class MovementComponent implements Component, Pool.Poolable {

    public float xSpeed;
    public float ySpeed; // = 0

    @Override
    public void reset() {
        xSpeed = 0;
        ySpeed = 0;
    }
}
