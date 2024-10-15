package br.com.davidbuzatto.mysimplegameengine.tests;

import java.awt.Color;

import br.com.davidbuzatto.mysimplegameengine.core.Engine;
import br.com.davidbuzatto.mysimplegameengine.geom.Vector2;
import br.com.davidbuzatto.mysimplegameengine.utils.Utils;

/**
 * Um exemplo de simulação da bolinha.
 * 
 * @author Prof. Dr. David Buzatto
 * @copyright Copyright (c) 2024
 */
public class BallTest extends Engine {

    private static final double GRAVIDADE = 50;
    private double xOffset;
    private double yOffset;

    private class Bolinha {

        Vector2 pos;
        Vector2 posAnt;
        Vector2 vel;
        Color cor;
        double raio;
        double atrito;
        double elasticidade;
        boolean arrastando;

        void desenhar( Engine engine ) {
            engine.fillCircle( pos, raio, cor );
        }

        void atualizar( double delta ) {

            if ( isMouseButtonPressed( MOUSE_BUTTON_LEFT ) ) {
                if ( Utils.checkCollisionPointCircle( getMousePositionPoint(), pos, raio ) ) {
                    arrastando = true;
                    xOffset = pos.x - getMouseX();
                    yOffset = pos.y - getMouseY();
                }
            } else if ( isMouseButtonReleased( MOUSE_BUTTON_LEFT ) ) {
                arrastando = false;
            }
    
            if ( !arrastando ) {
    
                pos.x += vel.x * delta;
                pos.y += vel.y * delta;
    
                if ( pos.x - raio <= 0 ) {
                    pos.x = raio;
                    vel.x = -vel.x * elasticidade;
                } else if ( pos.x + raio >= getScreenWidth() ) {
                    pos.x = getScreenWidth() - raio;
                    vel.x = -vel.x * elasticidade;
                }
    
                if ( pos.y - raio <= 0 ) {
                    pos.y = raio;
                    vel.y = -vel.y * elasticidade;
                } else if ( pos.y + raio >= getScreenHeight() ) {
                    pos.y = getScreenHeight() - raio;
                    vel.y = -vel.y * elasticidade;
                }
    
                vel.x = vel.x * atrito;
                vel.y = vel.y * atrito + GRAVIDADE;
    
            } else {
                pos.x = getMouseX() + xOffset;
                pos.y = getMouseY() + yOffset;
                vel.x = ( pos.x - posAnt.x ) / delta;
                vel.y = ( pos.y - posAnt.y ) / delta;
                posAnt.x = pos.x;
                posAnt.y = pos.y;
            }

        }

    }

    Bolinha bolinha;

    public BallTest() {
        super( 800, 600, "Ball Simulation", 60, true );
    }

    @Override
    public void create() {
        
        bolinha = new Bolinha();
        bolinha.pos = new Vector2( getScreenWidth() / 2, getScreenHeight() / 2 );
        bolinha.posAnt = new Vector2();
        bolinha.vel = new Vector2( 200, 200 );
        bolinha.raio = 50;
        bolinha.atrito = 0.99;
        bolinha.elasticidade = 0.9;
        bolinha.cor = BLUE;

    }

    @Override
    public void update() {
        double delta = getFrameTime();
        bolinha.atualizar( delta );
    }

    @Override
    public void draw() {
        bolinha.desenhar( this );
    }

    public static void main( String[] args ) {
        new BallTest();
    }

}