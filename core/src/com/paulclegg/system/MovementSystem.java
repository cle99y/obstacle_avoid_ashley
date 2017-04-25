package com.paulclegg.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.paulclegg.common.Mappers;
import com.paulclegg.component.MovementComponent;
import com.paulclegg.component.PositionComponent;

/**
 * Created by cle99 on 20/04/2017.
 */

public class MovementSystem extends IteratingSystem {

    private static final Family FAMILY = Family.all(
            PositionComponent.class,
            MovementComponent.class
    ).get();

    public MovementSystem() {
        super( FAMILY );
    }

    @Override
    protected void processEntity( Entity entity, float deltaTime ) {
        PositionComponent position = Mappers.POSITION.get( entity );
        MovementComponent movement = Mappers.MOVEMENT.get( entity );

        position.x += movement.xSpeed;
        position.y += movement.ySpeed;


    }
}
