package br.com.davidbuzatto.mysimplegameengine.tests;

import java.awt.Cursor;

import br.com.davidbuzatto.mysimplegameengine.core.Engine;

/**
 * Classe básica de exemplo de utilização da engine.
 * 
 * @author Prof. Dr. David Buzatto
 * @copyright Copyright (c) 2024
 */
public class WindowFlagsTest extends Engine {

    // declaração de variáveis

    public WindowFlagsTest() {

        // cria a janela do jogo ou simulação
        super( 
            800,
            600,
            "Título da Janela",
            60,
            true,
            false,
            false,
            false,
            false
        );

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
        drawLine( 0, 0, getScreenWidth(), getScreenHeight(), BLACK );
        drawText( getTime() + "", 20, 20, 20, BLACK );
        drawText( getFrameTime() + "", 20, 40, 20, BLACK );
        drawText( getFps() + "", 20, 60, 20, BLACK );
        drawFps( 10, 100 );
    }

    public static void main( String[] args ) {
        new WindowFlagsTest();
    }

}