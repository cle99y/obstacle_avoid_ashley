package com.paulclegg.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.paulclegg.common.Mappers;
import com.paulclegg.component.DimensionComponent;
import com.paulclegg.component.PositionComponent;
import com.paulclegg.component.WorldWrapComponent;

/**
 * Created by cle99 on 22/04/2017.
 */

public class WorldWrapSystem extends IteratingSystem {

    private static final Family FAMILY = Family.all(
            PositionComponent.class,
            WorldWrapComponent.class,
            DimensionComponent.class
    ).get();

    private Viewport viewport;

    public WorldWrapSystem( Viewport viewport ) {
        super( FAMILY );
        this.viewport = viewport;
    }

    @Override
    protected void processEntity( Entity entity, float deltaTime ) {
        PositionComponent position = Mappers.POSITION.get( entity );
        DimensionComponent dimension = Mappers.DIMENSION.get( entity );

        position.x = MathUtils.clamp(
                position.x,
                0f,
                viewport.getWorldWidth() - dimension.width
        );

        position.y = MathUtils.clamp(
                position.y,
                0f,
                viewport.getWorldHeight() - dimension.height
        );
    }
}
