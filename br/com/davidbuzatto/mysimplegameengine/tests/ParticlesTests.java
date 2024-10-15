package br.com.davidbuzatto.mysimplegameengine.tests;

import java.awt.Color;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import br.com.davidbuzatto.mysimplegameengine.core.Engine;
import br.com.davidbuzatto.mysimplegameengine.geom.Point;
import br.com.davidbuzatto.mysimplegameengine.geom.Vector2;
import br.com.davidbuzatto.mysimplegameengine.utils.Utils;

/**
 * Testes de partículas.
 * 
 * @author Prof. Dr. David Buzatto
 * @copyright Copyright (c) 2024
 */
public class ParticlesTests extends Engine {

    private static final double GRAVITY = 50;

    private class Particula {

        Vector2 pos;
        Vector2 vel;
        Color cor;
        double raio;
        double atrito;
        double elasticidade;

        void desenhar( Engine engine ) {
            engine.fillCircle( pos, raio, cor );
        }

        void atualizar( Engine engine, double delta ) {

            pos.x += vel.x * delta;
            pos.y += vel.y * delta;

            if ( pos.x - raio <= 0 ) {
                pos.x = raio;
                vel.x = -vel.x * elasticidade;
            } else if ( pos.x + raio >= engine.getScreenWidth() ) {
                pos.x = engine.getScreenWidth() - raio;
                vel.x = -vel.x * elasticidade;
            }

            /*if ( pos.y - raio <= 0 ) {
                pos.y = raio;
                vel.y = -vel.y * elasticidade / raio;
            } else if ( pos.y + raio >= engine.getScreenHeight() ) {
                pos.y = engine.getScreenHeight() - raio;
                vel.y = -vel.y * elasticidade / raio;
            }*/

            vel.x = vel.x * atrito;
            vel.y = vel.y * atrito + GRAVITY;

        }

    }

    List<Particula> particulas;

    public ParticlesTests() {
        super( 800, 450, "Particle Tests", 60, true );
    }

    @Override
    public void create() {
        particulas = new CopyOnWriteArrayList<>();
    }

    @Override
    public void update() {        

        double delta = getFrameTime();

        Point mousePos = getMousePositionPoint();

        if ( isMouseButtonDown( MOUSE_BUTTON_LEFT ) ) {
            for ( int i = 0; i < 20; i++ ) {
                Particula p = new Particula();
                p.pos = new Vector2( mousePos.x, mousePos.y );
                p.vel = new Vector2( Utils.getRandomValue( -200, 200 ), Utils.getRandomValue( -200, 200 ) );
                p.raio = Utils.getRandomValue( 2, 6 );
                p.atrito = 0.99;
                p.elasticidade = 0.8;
                p.cor = Utils.colorFromHSV( Utils.getRandomValue( 0, 30 ), 1, 1 );
                particulas.add( p );
            }
        }

        for ( Particula p : particulas ) {
            p.atualizar( this, delta );
        }
    }

    @Override
    public void draw() {

        clearBackground( BLACK );
        drawFPS( 10, 20 );
        drawText( "Particles: " + particulas.size(), 10, 40, 20, WHITE );

        for ( Particula p : particulas ) {
            p.desenhar( this );
        }
        
    }

    public static void main( String[] args ) {
        new ParticlesTests();
    }

}