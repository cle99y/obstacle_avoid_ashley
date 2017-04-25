package com.paulclegg.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.paulclegg.Config.GameConfig;
import com.paulclegg.common.EntityFactory;
import com.paulclegg.common.Mappers;
import com.paulclegg.component.CleanupComponent;
import com.paulclegg.component.PositionComponent;

/**
 * Created by cle99 on 22/04/2017.
 */

public class CleanupSystem extends IteratingSystem {

    private static final Family FAMILY = Family.all(
            PositionComponent.class,
            CleanupComponent.class
    ).get();
    private EntityFactory factory;

    public CleanupSystem( EntityFactory factory ) {
        super( FAMILY );
        this.factory = factory;
    }

    @Override
    protected void processEntity( Entity entity, float deltaTime ) {
        PositionComponent position = Mappers.POSITION.get( entity );

        if ( position.y < -GameConfig.OBSTACLE_SIZE * 2 ) {
            factory.removeObstacle( entity );
        }
    }
}
