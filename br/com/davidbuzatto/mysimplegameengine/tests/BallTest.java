package br.com.davidbuzatto.mysimplegameengine.tests;

import java.awt.Color;
import java.awt.event.MouseEvent;

import br.com.davidbuzatto.mysimplegameengine.core.Engine;
import br.com.davidbuzatto.mysimplegameengine.event.MouseEventType;
import br.com.davidbuzatto.mysimplegameengine.geom.Point;
import br.com.davidbuzatto.mysimplegameengine.geom.Vector2;
import br.com.davidbuzatto.mysimplegameengine.utils.Utils;

/**
 * Classe básica de exemplo de utilização da engine.
 * 
 * @author Prof. Dr. David Buzatto
 * @copyright Copyright (c) 2024
 */
public class BallTest extends Engine {

    private static final double GRAVIDADE = 50;
    private Point mousePos;
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
    }

    Bolinha bolinha;

    public BallTest() {
        super( 800, 600, "Título da Janela", 60, true );
    }

    /**
     * Processa a entrada inicial fornecida pelo usuário e cria
     * e/ou inicializa os objetos/contextos/variáveis do jogo ou simulação.
     */
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
    public void handleMouseEvents(MouseEvent e, MouseEventType met) {
        
        if ( met == MouseEventType.MOVED || met == MouseEventType.DRAGGED ) {
            mousePos = Utils.mouseEventPositionToPoint( e );
        }

        if ( met == MouseEventType.PRESSED ) {
            if ( Utils.checkCollisionPointCircle( mousePos, bolinha.pos, bolinha.raio ) ) {
                bolinha.arrastando = true;
                xOffset = bolinha.pos.x - mousePos.x;
                yOffset = bolinha.pos.y - mousePos.y;
            }
        } else if ( met == MouseEventType.RELEASED ) {
            bolinha.arrastando = false;
        }

    }

    /**
     * Atualiza os objetos/contextos/variáveis do jogo ou simulação.
     */
    @Override
    public void update() {
        
        double delta = getFrameTime();

        if ( !bolinha.arrastando ) {

            bolinha.pos.x += bolinha.vel.x * delta;
            bolinha.pos.y += bolinha.vel.y * delta;

            if ( bolinha.pos.x - bolinha.raio <= 0 ) {
                bolinha.pos.x = bolinha.raio;
                bolinha.vel.x = -bolinha.vel.x * bolinha.elasticidade;
            } else if ( bolinha.pos.x + bolinha.raio >= getScreenWidth() ) {
                bolinha.pos.x = getScreenWidth() - bolinha.raio;
                bolinha.vel.x = -bolinha.vel.x * bolinha.elasticidade;
            }

            if ( bolinha.pos.y - bolinha.raio <= 0 ) {
                bolinha.pos.y = bolinha.raio;
                bolinha.vel.y = -bolinha.vel.y * bolinha.elasticidade;
            } else if ( bolinha.pos.y + bolinha.raio >= getScreenHeight() ) {
                bolinha.pos.y = getScreenHeight() - bolinha.raio;
                bolinha.vel.y = -bolinha.vel.y * bolinha.elasticidade;
            }

            bolinha.vel.x = bolinha.vel.x * bolinha.atrito;
            bolinha.vel.y = bolinha.vel.y * bolinha.atrito + GRAVIDADE;

        } else {
            bolinha.pos.x = mousePos.x + xOffset;
            bolinha.pos.y = mousePos.y + yOffset;
            bolinha.vel.x = ( bolinha.pos.x - bolinha.posAnt.x ) / delta;
            bolinha.vel.y = ( bolinha.pos.y - bolinha.posAnt.y ) / delta;
            bolinha.posAnt.x = bolinha.pos.x;
            bolinha.posAnt.y = bolinha.pos.y;
        }

    }

    /**
     * Desenha o estado dos objetos/contextos/variáveis do jogo ou simulação.
     */
    @Override
    public void draw() {
        bolinha.desenhar( this );
    }

    public static void main( String[] args ) {
        new BallTest();
    }

}