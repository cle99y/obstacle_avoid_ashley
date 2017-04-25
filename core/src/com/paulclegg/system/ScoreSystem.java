package com.paulclegg.system;

import com.badlogic.ashley.systems.IntervalSystem;
import com.badlogic.gdx.math.MathUtils;
import com.paulclegg.Config.GameConfig;
import com.paulclegg.common.GameManager;

/**
 * Created by cle99 on 23/04/2017.
 */

public class ScoreSystem extends IntervalSystem {

    public ScoreSystem() {
        super( GameConfig.MAX_SCORE_TIME );
    }

    @Override
    protected void updateInterval() {
        GameManager.INSTANCE.updateScore( MathUtils.random( 1, 5 ) );
    }
}
