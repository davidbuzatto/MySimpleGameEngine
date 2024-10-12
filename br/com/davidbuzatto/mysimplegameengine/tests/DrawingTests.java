package br.com.davidbuzatto.mysimplegameengine.tests;

import br.com.davidbuzatto.mysimplegameengine.core.Engine;

/**
 * Testes de desenho.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class DrawingTests extends Engine {

    public DrawingTests() {
        super( 800, 600, "Test Window", true, 60 );
    }

    /**
     * Processa a entrada inicial fornecida pelo usuário e cria
     * e/ou inicializa os objetos/contextos/variáveis do jogo ou simulação.
     */
    @Override
    public void create() {
    }

    /**
     * Atualiza os objetos/contextos/variáveis do jogo ou simulação.
     */
    @Override
    public void update() {
    }

    /**
     * Desenha o estado dos objetos/contextos/variáveis do jogo ou simulação.
     */
    @Override
    public void draw() {

        drawFps( 10, 30 );

        drawPixel( 50, 50, BLACK );
        drawLine( 60, 60, 100, 100, BLACK );

        fillRectangle( 50, 120, 50, 100, BLUE );
        drawRectangle( 50, 120, 50, 100, BLACK );

        fillRectangle( 50, 120, 50, 100, BLUE );
        drawRectangle( 50, 120, 50, 100, BLACK );

        fillRectanglePro( 50, 240, 50, 100, 50, 240, -15, BLUE );
        drawRectanglePro( 50, 240, 50, 100, 50, 240, -15, BLACK );

        fillRoundRectangle( 50, 370, 80, 60, 20, BLUE );
        drawRoundRectangle( 50, 370, 80, 60, 20, BLACK );

        fillRectangleGradientH( 50, 450, 100, 50, BLUE, GREEN );
        fillRectangleGradientV( 50, 520, 100, 50, BLUE, GREEN );

        fillCircle( 250, 70, 30, MAROON );
        drawCircle( 250, 70, 30, BLACK );

        fillEllipse( 250, 160, 60, 30, MAROON );
        drawEllipse( 250, 160, 60, 30, BLACK );

        fillCircleSector( 250, 240, 30, 0, 130, MAROON );
        drawCircleSector( 250, 240, 30, 0, 130, BLACK );

        fillEllipseSector( 250, 300, 60, 30, 0, 130, MAROON );
        drawEllipseSector( 250, 300, 60, 30, 0, 130, BLACK );

        fillArc( 250, 350, 60, 30, 0, 130, MAROON );
        drawArc( 250, 350, 60, 30, 0, 130, BLACK );

        fillRing( 250, 400, 10, 30, 0, 130, 50, MAROON );
        drawRing( 250, 400, 10, 30, 0, 130, 50, BLACK );

        fillTriangle( 400, 50, 440, 100, 360, 100, ORANGE );
        drawTriangle( 400, 50, 440, 100, 360, 100, BLACK );

        fillPolygon( 400, 160, 5, 35, 0, ORANGE );
        drawPolygon( 400, 160, 5, 35, 0, BLACK );

        fillQuadCurve( 400, 220, 450, 270, 400, 320, ORANGE );
        drawQuadCurve( 400, 220, 450, 270, 400, 320, BLACK );

        fillCubicCurve( 400, 340, 350, 380, 450, 420, 400, 460, ORANGE );
        drawCubicCurve( 400, 340, 350, 380, 450, 420, 400, 460, BLACK );

        for ( int i = 0; i < 100000; i++ ) {
            drawLine( 0, 0, 10, 10, BLACK );
            //System.out.println( getFrameTime() );
        }


    }

    public static void main( String[] args ) {
        new DrawingTests();
    }

}