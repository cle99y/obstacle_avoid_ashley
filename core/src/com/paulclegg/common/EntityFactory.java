package com.paulclegg.common;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.paulclegg.Config.GameConfig;
import com.paulclegg.assets.AssetDescriptors;
import com.paulclegg.assets.RegionNames;
import com.paulclegg.component.BoundsComponent;
import com.paulclegg.component.CleanupComponent;
import com.paulclegg.component.CollectableComponent;
import com.paulclegg.component.DimensionComponent;
import com.paulclegg.component.MovementComponent;
import com.paulclegg.component.ObjectType;
import com.paulclegg.component.ObstacleComponent;
import com.paulclegg.component.PlayerComponent;
import com.paulclegg.component.PositionComponent;
import com.paulclegg.component.TextureComponent;
import com.paulclegg.component.WorldWrapComponent;

/**
 * Created by cle99 on 20/04/2017.
 */

public class EntityFactory {

    private PooledEngine engine;
    private AssetManager assetManager;
    private TextureAtlas gamePlayAtlas;

    public EntityFactory( PooledEngine engine, AssetManager assetManager ) {
        this.engine = engine;
        this.assetManager = assetManager;
        gamePlayAtlas = assetManager.get( AssetDescriptors.GAME_PLAY );
    }

    private static float getCollectableBoundsRadius( ObjectType type ) {
        float radius;

        if ( type == ObjectType.HEALTH ) {
            radius = GameConfig.HEALTH_TOKEN_BOUNDS_RADIUS;
        } else if ( type == ObjectType.POINTS ) {
            radius = GameConfig.POINTS_TOKEN_BOUNDS_RADIUS;
        } else {
            throw new IllegalArgumentException( "Unknown Object Type: " + type );
        }

        return radius;
    }

    private static DimensionComponent getCollectableDimension( ObjectType type, DimensionComponent dimension ) {
        // pass DimensionComponent class to return two parameters back
        DimensionComponent dim = dimension;
        if ( type == ObjectType.HEALTH ) {
            dim.width = GameConfig.HEALTH_TOKEN_SIZE;
            dim.height = GameConfig.HEALTH_TOKEN_SIZE;
        } else if ( type == ObjectType.POINTS ) {
            dim.width = GameConfig.POINTS_TOKEN_SIZE;
            dim.height = GameConfig.POINTS_TOKEN_SIZE;
        } else {
            throw new IllegalArgumentException( "Unknown Object Type: " + type );
        }

        return dim;
    }

    public void addPlayer() {

        float x = ( GameConfig.WORLD_WIDTH - GameConfig.PLAYER_SIZE ) / 2f;
        float y = 1 - GameConfig.PLAYER_SIZE / 2f;

        PositionComponent position = engine.createComponent( PositionComponent.class );
        position.x = x;
        position.y = y;

        BoundsComponent bounds = engine.createComponent( BoundsComponent.class );
        bounds.bounds.set( x, y, GameConfig.PLAYER_BOUNDS_RADIUS );

        MovementComponent movement = engine.createComponent( MovementComponent.class );

        PlayerComponent player = engine.createComponent( PlayerComponent.class );

        WorldWrapComponent worldWrap = engine.createComponent( WorldWrapComponent.class );

        TextureComponent texture = engine.createComponent( TextureComponent.class );
        texture.region = gamePlayAtlas.findRegion( RegionNames.PLAYER );

        DimensionComponent dimension = engine.createComponent( DimensionComponent.class );
        dimension.width = GameConfig.PLAYER_SIZE;
        dimension.height = GameConfig.PLAYER_SIZE;

        Entity entity = engine.createEntity();

        entity.add( bounds );
        entity.add( movement );
        entity.add( player );
        entity.add( position );
        entity.add( worldWrap );
        entity.add( dimension );
        entity.add( texture );

        engine.addEntity( entity );

    }

    // -- PRIVATE METHODS --

    public void addObstacle( float x, float y ) {
        BoundsComponent bounds = engine.createComponent( BoundsComponent.class );
        bounds.bounds.set( x, y, GameConfig.OBSTACLE_BOUNDS_RADIUS );

        MovementComponent movement = engine.createComponent( MovementComponent.class );
        movement.ySpeed = -GameManager.INSTANCE.getDifficultyLevel().getTargetObjectSpeed();

        PositionComponent position = engine.createComponent( PositionComponent.class );
        position.x = x;
        position.y = y;

        CleanupComponent cleanup = engine.createComponent( CleanupComponent.class );

        ObstacleComponent obstacle = engine.createComponent( ObstacleComponent.class );

        DimensionComponent dimension = engine.createComponent( DimensionComponent.class );
        dimension.width = GameConfig.OBSTACLE_SIZE;
        dimension.height = GameConfig.OBSTACLE_SIZE;

        TextureComponent texture = engine.createComponent( TextureComponent.class );
        texture.region = gamePlayAtlas.findRegion( RegionNames.OBSTACLE );

        Entity entity = engine.createEntity();
        entity.add( bounds );
        entity.add( movement );
        entity.add( position );
        entity.add( cleanup );
        entity.add( obstacle );
        entity.add( dimension );
        entity.add( texture );
        engine.addEntity( entity );

    }

    public void addCollectable( ObjectType type, float x, float y ) {

        CollectableComponent collectable = engine.createComponent( CollectableComponent.class );
        collectable.type = type;

        BoundsComponent bounds = engine.createComponent( BoundsComponent.class );
        bounds.bounds.set( x, y, getCollectableBoundsRadius( collectable.type ) );


        MovementComponent movement = engine.createComponent( MovementComponent.class );
        movement.ySpeed = -GameManager.INSTANCE.getDifficultyLevel().getTargetObjectSpeed();

        PositionComponent position = engine.createComponent( PositionComponent.class );
        position.x = x;
        position.y = y;

        CleanupComponent cleanup = engine.createComponent( CleanupComponent.class );

        DimensionComponent dimension = engine.createComponent( DimensionComponent.class );

        dimension.width = getCollectableDimension(
                collectable.type,
                dimension
        ).width;

        dimension.height = getCollectableDimension(
                collectable.type,
                dimension
        ).height;


        TextureComponent texture = engine.createComponent( TextureComponent.class );
        texture.region = getTextureRegion( collectable.type );

        Entity entity = engine.createEntity();
        entity.add( bounds );
        entity.add( movement );
        entity.add( position );
        entity.add( cleanup );
        entity.add( collectable );
        entity.add( dimension );
        entity.add( texture );
        engine.addEntity( entity );

    }

    private TextureRegion getTextureRegion( ObjectType type ) {
        TextureRegion texture;

        if ( type == ObjectType.HEALTH ) {
            texture = gamePlayAtlas.findRegion( RegionNames.HEALTH_TOKEN );
        } else if ( type == ObjectType.POINTS ) {
            texture = gamePlayAtlas.findRegion( RegionNames.POINTS_TOKEN );
        } else {
            throw new IllegalArgumentException( "Unknown Object Type: " + type );
        }

        return texture;
    }


    // -- PUBLIC METHODS --

    public void addBackground() {
        TextureComponent texture = engine.createComponent( TextureComponent.class );
        texture.region = gamePlayAtlas.findRegion( RegionNames.BACKGROUND );

        DimensionComponent dimension = engine.createComponent( DimensionComponent.class );
        dimension.width = GameConfig.WORLD_WIDTH;
        dimension.height = GameConfig.WORLD_HEIGHT;

        PositionComponent position = engine.createComponent( PositionComponent.class );
        position.x = 0;
        position.y = 0;

        Entity entity = engine.createEntity();

        entity.add( texture );
        entity.add( position );
        entity.add( dimension );

        engine.addEntity( entity );

    }

    public void removeObstacle( Entity entity ) {
        engine.removeEntity( entity );
    }

}
