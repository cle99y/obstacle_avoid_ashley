package com.paulclegg.system.collision;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.utils.Logger;
import com.paulclegg.Util.ViewportUtils;
import com.paulclegg.common.Mappers;
import com.paulclegg.component.BoundsComponent;
import com.paulclegg.component.CollectableComponent;
import com.paulclegg.component.ObjectType;
import com.paulclegg.component.ObstacleComponent;
import com.paulclegg.component.PlayerComponent;

/**
 * Created by cle99 on 22/04/2017.
 */

public class CollisionSystem extends EntitySystem {

    private static final Logger log = new Logger( ViewportUtils.class.getName(), Logger.DEBUG );

    private static final Family PLAYER_FAMILY = Family.all(
            PlayerComponent.class,
            BoundsComponent.class
    ).get();

    private static final Family OBSTACLE_FAMILY = Family.all(
            ObstacleComponent.class,
            BoundsComponent.class
    ).get();

    private static final Family COLLECTABLE_FAMILY = Family.all(
            CollectableComponent.class,
            BoundsComponent.class
    ).get();

    private final CollisionListener listener;

    public CollisionSystem( CollisionListener listener ) {
        this.listener = listener;
    }

    @Override
    public void update( float deltaTime ) {
        ImmutableArray<Entity> players = getEngine().getEntitiesFor( PLAYER_FAMILY );
        ImmutableArray<Entity> obstacles = getEngine().getEntitiesFor( OBSTACLE_FAMILY );
        ImmutableArray<Entity> collectables = getEngine().getEntitiesFor( COLLECTABLE_FAMILY );

        for ( Entity playerEntity : players ) {

            // token collisions
            for ( Entity tokenEntity : collectables ) {
                CollectableComponent token = Mappers.COLLECTABLE.get( tokenEntity );

                if ( token.hit ) {
                    continue;
                }

                if ( checkCollision( playerEntity, tokenEntity ) ) {
                    token.hit = true;
                    //log.debug( "collision detected" );
                    listener.hitObject( token.type.getType() );
                }
            }

            //obstacle collisions
            for ( Entity obstacleEntity : obstacles ) {
                ObstacleComponent obstacle = Mappers.OBSTACLE.get( obstacleEntity );


                if ( obstacle.hit ) {
                    continue;
                }

                if ( checkCollision( playerEntity, obstacleEntity ) ) {
                    obstacle.hit = true;
                    //log.debug( "collision detected" );
                    listener.hitObject( ObjectType.OBSTACLE );
                }
            }
        }
    }

    private boolean checkCollision( Entity player, Entity obstacle ) {
        BoundsComponent playerBounds = Mappers.BOUNDS.get( player );
        BoundsComponent obstacleBounds = Mappers.BOUNDS.get( obstacle );

        return Intersector.overlaps( playerBounds.bounds, obstacleBounds.bounds );
    }
}
