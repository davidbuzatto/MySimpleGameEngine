package br.com.davidbuzatto.mysimplegameengine.tests;

import br.com.davidbuzatto.mysimplegameengine.event.*;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import br.com.davidbuzatto.mysimplegameengine.core.Engine;
import br.com.davidbuzatto.mysimplegameengine.core.GameAction;
import br.com.davidbuzatto.mysimplegameengine.core.InputManager;

/**
 * Classe básica de exemplo de utilização da engine.
 * 
 * @author Prof. Dr. David Buzatto
 * @copyright Copyright (c) 2024
 */
public class TestNew extends Engine {

    // declaração de variáveis
    private GameAction actionTest;

    public TestNew() {

        // cria a janela do jogo ou simulação
        super( 
            800,                  // 800 pixels de largura
            600,                  // 600 pixels de largura
            "Título da Janela",   // título da janela
            60,                   // 60 quadros por segundo
            true,                 // ativa a suavização (antialiasing)
            false,                // redimensionável (resizable)
            false,                // tela cheia (fullscreen na resolução atual)
            false,                // sem decoração (undecorated)
            false                 // sempre acima (always on top)
        );

    }

    /**
     * Processa a entrada inicial fornecida pelo usuário e cria
     * e/ou inicializa os objetos/contextos/variáveis do jogo ou simulação.
     */
    @Override
    public void create() {
        /*actionTest = new GameAction( "teste" );
        //inputManager.mapToKey( actionTest, KeyEvent.VK_RIGHT  );
        inputManager.mapToMouse( actionTest, InputManager.MOUSE_BUTTON_1 );*/
    }

    /**
     * Atualiza os objetos/contextos/variáveis do jogo ou simulação.
     */
    @Override
    public void update() {
        
    }

    private double v;
    /**
     * Desenha o estado dos objetos/contextos/variáveis do jogo ou simulação.
     */
    @Override
    public void draw() {
        /*if ( actionTest.isPressed() ) {
            drawLine( 0, 0, inputManager.getMouseX(), inputManager.getMouseY(), BLACK );
        }*/
        /*if ( isMouseButtonPressed( MouseEvent.BUTTON1 ) ) {
            System.out.println( "aaa" );
        }
        if ( isMouseButtonReleased( MouseEvent.BUTTON1 ) ) {
            System.out.println( "ccc" );
        }

        if ( isMouseButtonDown( MouseEvent.BUTTON1 ) ) {
            System.out.println( "bbb" );
        }

        if ( isMouseButtonUp( MouseEvent.BUTTON1 ) ) {
            System.out.println( "ddd" );
        }*/
        v += getMouseWheelMove();
        drawRectangle( 0, v, 100, 100, BLACK);

    }

    public static void main( String[] args ) {
        new TestNew();
    }

}