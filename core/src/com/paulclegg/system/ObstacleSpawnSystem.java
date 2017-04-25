package com.paulclegg.system;

import com.badlogic.ashley.systems.IntervalSystem;
import com.badlogic.gdx.math.MathUtils;
import com.paulclegg.Config.GameConfig;
import com.paulclegg.common.EntityFactory;
import com.paulclegg.component.ObjectType;

/**
 * Created by cle99 on 22/04/2017.
 */

public class ObstacleSpawnSystem extends IntervalSystem {

    private static final int OBTACLES_BEFORE_HEALTH_TOKEN = 20;
    private static final int OBSTACLES_BEFORE_POINTS_TOKEN = 12;
    private static int obstacleCount;
    private EntityFactory factory;


    public ObstacleSpawnSystem( EntityFactory factory ) {
        super( GameConfig.OBSTACLE_SPAWN_TIME );
        this.factory = factory;
    }

    @Override
    protected void updateInterval() {
        obstacleCount++;
        float min = 0;
        float max = GameConfig.WORLD_WIDTH - GameConfig.OBSTACLE_SIZE;

        float obstacleX = randomX( min, max );
        float obstacleY = GameConfig.WORLD_HEIGHT + 2 * GameConfig.PLAYER_SIZE;

        factory.addObstacle( obstacleX, obstacleY );

        // generate a health token every 20 obstacles (5s)
        if ( obstacleCount % OBTACLES_BEFORE_HEALTH_TOKEN == 0 ) {
            factory.addCollectable(
                    ObjectType.HEALTH,
                    randomX( min, max ),
                    // position to avoid overlap - simple method
                    obstacleY + GameConfig.OBSTACLE_SIZE * 1.25f
            );
        }

        // generate a points token every 12 obstacles (3s)
        if ( obstacleCount % OBSTACLES_BEFORE_POINTS_TOKEN == 0 ) {
            factory.addCollectable(
                    ObjectType.POINTS,
                    randomX( min, max ),
                    obstacleY - GameConfig.OBSTACLE_SIZE * 1.25f
            );
        }

    }

    private float randomX( float min, float max ) {
        return MathUtils.random( min, max );

    }
}
