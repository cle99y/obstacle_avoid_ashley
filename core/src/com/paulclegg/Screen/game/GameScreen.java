package com.paulclegg.Screen.game;

import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.paulclegg.Config.GameConfig;
import com.paulclegg.ObstacleGame;
import com.paulclegg.Screen.menu.MenuScreen;
import com.paulclegg.Util.GdxUtils;
import com.paulclegg.Util.ViewportUtils;
import com.paulclegg.assets.AssetDescriptors;
import com.paulclegg.common.EntityFactory;
import com.paulclegg.common.GameManager;
import com.paulclegg.component.ObjectType;
import com.paulclegg.system.BoundsSystem;
import com.paulclegg.system.CleanupSystem;
import com.paulclegg.system.HudRenderSystem;
import com.paulclegg.system.MovementSystem;
import com.paulclegg.system.ObstacleSpawnSystem;
import com.paulclegg.system.PlayerSystem;
import com.paulclegg.system.RenderSystem;
import com.paulclegg.system.ScoreSystem;
import com.paulclegg.system.WorldWrapSystem;
import com.paulclegg.system.collision.CollisionListener;
import com.paulclegg.system.collision.CollisionSystem;
import com.paulclegg.system.debug.DebugCameraSystem;
import com.paulclegg.system.debug.DebugRenderSystem;
import com.paulclegg.system.debug.GridRenderSystem;

/**
 * Created by cle99 on 05/04/2017.
 */


public class GameScreen implements Screen {

    // CONSTANTS

    private static final Logger log = new Logger( ViewportUtils.class.getName(), Logger.DEBUG );
    private static final boolean DEBUG = false;

    // attributes
    private final ObstacleGame game;
    private OrthographicCamera camera;
    private AssetManager assetManager;

    private Viewport viewport;
    private Viewport hudViewport;
    private ShapeRenderer renderer;
    private PooledEngine engine;
    private EntityFactory factory;
    private boolean reset;


    public GameScreen( ObstacleGame game ) {
        this.game = game;
        assetManager = game.getAssetManager();
    }

    // CONSTRUCTOR


    @Override
    public void show() {
        camera = new OrthographicCamera();
        viewport = new FitViewport( GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEIGHT, camera );
        hudViewport = new FitViewport( GameConfig.HUD_WIDTH, GameConfig.HUD_HEIGHT );

        renderer = new ShapeRenderer();
        BitmapFont font = assetManager.get( AssetDescriptors.UIFONT );
        engine = new PooledEngine();
        factory = new EntityFactory( engine, assetManager );

        CollisionListener listener = new CollisionListener() {
            @Override
            public void hitObject( ObjectType type ) {
                log.debug( "collision with type: " + type );
                if ( type == ObjectType.OBSTACLE ) {
                    GameManager.INSTANCE.loseLife();

                    if ( GameManager.INSTANCE.isGameOver() ) {
                        GameManager.INSTANCE.updateHighScore();
                    } else {
                        engine.removeAllEntities();
                        reset = true;
                    }

                } else if ( type == ObjectType.HEALTH ) {
                    GameManager.INSTANCE.addLife();

                } else if ( type == ObjectType.POINTS ) {
                    GameManager.INSTANCE.updateScore( 1000 );
                }
            }
        };

        engine.addSystem( new PlayerSystem() );
        engine.addSystem( new MovementSystem() );
        engine.addSystem( new WorldWrapSystem( viewport ) );
        engine.addSystem( new BoundsSystem() );
        engine.addSystem( new ObstacleSpawnSystem( factory ) );
        engine.addSystem( new CleanupSystem( factory ) );
        engine.addSystem( new CollisionSystem( listener ) );
        engine.addSystem( new RenderSystem( viewport, game.getSpriteBatch() ) );

        if ( DEBUG ) {
            engine.addSystem( new GridRenderSystem( viewport, renderer ) );
            engine.addSystem( new DebugRenderSystem( viewport, renderer ) );
            engine.addSystem( new DebugCameraSystem( camera,
                    GameConfig.WORLD_CENTER_X,
                    GameConfig.WORLD_CENTER_Y )
            );
        }

        engine.addSystem( new HudRenderSystem( hudViewport, game.getSpriteBatch(), font ) );

        engine.addSystem( new ScoreSystem() );

        addEntities();
    }

    @Override
    public void render( float delta ) {
        GdxUtils.clearScreen();
        engine.update( delta );

        if ( GameManager.INSTANCE.isGameOver() ) {
            GameManager.INSTANCE.reset();
            game.setScreen( new MenuScreen( game ) );
        }

        if ( reset ) {
            reset = false;
            addEntities();
        }
    }

    @Override
    public void resize( int width, int height ) {
        viewport.update( width, height, true );
        hudViewport.update( width, height, true );
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        renderer.dispose();
    }

    private void addEntities() {
        factory.addBackground();
        factory.addPlayer();

    }
}
