package com.paulclegg.common;

import com.badlogic.ashley.core.ComponentMapper;
import com.paulclegg.component.BoundsComponent;
import com.paulclegg.component.CollectableComponent;
import com.paulclegg.component.DimensionComponent;
import com.paulclegg.component.MovementComponent;
import com.paulclegg.component.ObstacleComponent;
import com.paulclegg.component.PositionComponent;
import com.paulclegg.component.TextureComponent;

/**
 * Created by cle99 on 20/04/2017.
 */

public class Mappers {

    public static final ComponentMapper<BoundsComponent> BOUNDS =
            ComponentMapper.getFor( BoundsComponent.class );

    public static final ComponentMapper<MovementComponent> MOVEMENT =
            ComponentMapper.getFor( MovementComponent.class );

    public static final ComponentMapper<PositionComponent> POSITION =
            ComponentMapper.getFor( PositionComponent.class );

    public static final ComponentMapper<ObstacleComponent> OBSTACLE =
            ComponentMapper.getFor( ObstacleComponent.class );

    public static final ComponentMapper<CollectableComponent> COLLECTABLE =
            ComponentMapper.getFor( CollectableComponent.class );

    public static final ComponentMapper<DimensionComponent> DIMENSION =
            ComponentMapper.getFor( DimensionComponent.class );

    public static final ComponentMapper<TextureComponent> TEXTURE =
            ComponentMapper.getFor( TextureComponent.class );


    private Mappers() {
    }
}
