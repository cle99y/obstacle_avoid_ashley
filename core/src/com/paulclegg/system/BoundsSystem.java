package com.paulclegg.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.paulclegg.common.Mappers;
import com.paulclegg.component.BoundsComponent;
import com.paulclegg.component.DimensionComponent;
import com.paulclegg.component.MovementComponent;
import com.paulclegg.component.PositionComponent;

/**
 * Created by cle99 on 21/04/2017.
 */

public class BoundsSystem extends IteratingSystem {

    private static final Family FAMILY = Family.all(
            PositionComponent.class,
            MovementComponent.class,
            DimensionComponent.class
    ).get();

    public BoundsSystem() {
        super( FAMILY );
    }

    @Override
    protected void processEntity( Entity entity, float deltaTime ) {
        BoundsComponent bounds = Mappers.BOUNDS.get( entity );
        PositionComponent position = Mappers.POSITION.get( entity );
        DimensionComponent dimension = Mappers.DIMENSION.get( entity );

        bounds.bounds.x = position.x + dimension.width / 2f;
        bounds.bounds.y = position.y + dimension.height / 2f;
    }
}
