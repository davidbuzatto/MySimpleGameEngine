package br.com.davidbuzatto.mysimplegameengine.tests;

import br.com.davidbuzatto.mysimplegameengine.core.Engine;

/**
 * Testes das flags para criação da janela.
 * 
 * @author Prof. Dr. David Buzatto
 * @copyright Copyright (c) 2024
 */
public class WindowFlagsTests extends Engine {

    public WindowFlagsTests() {

        // cria a janela do jogo ou simulação
        super( 
            800,
            450,
            "Título da Janela",
            60,
            true,
            false,
            false,
            false,
            false
        );

    }

    @Override
    public void create() {
    }

    @Override
    public void update() {
    }

    @Override
    public void draw() {
        drawLine( 0, 0, getScreenWidth(), getScreenHeight(), BLACK );
        drawText( getTime() + "", 20, 20, 20, BLACK );
        drawText( getFrameTime() + "", 20, 40, 20, BLACK );
        drawText( getFPS() + "", 20, 60, 20, BLACK );
        drawFPS( 10, 100 );
    }

    public static void main( String[] args ) {
        new WindowFlagsTests();
    }

}