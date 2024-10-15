package br.com.davidbuzatto.mysimplegameengine.tests;

import br.com.davidbuzatto.mysimplegameengine.core.Engine;

/**
 * Testes de entrada.
 * 
 * @author Prof. Dr. David Buzatto
 * @copyright Copyright (c) 2024
 */
public class InputTests extends Engine {

    private double value;

    public InputTests() {
        super( 800, 600, "Input tests", 60, true );
    }

    @Override
    public void create() {
    }

    @Override
    public void update() {
        
        if ( isMouseButtonPressed( MOUSE_BUTTON_LEFT ) ) {
            System.out.println( "esquerda" );
        }

        if ( isMouseButtonPressed( MOUSE_BUTTON_MIDDLE ) ) {
            System.out.println( "meio" );
        }

        if ( isMouseButtonPressed( MOUSE_BUTTON_RIGHT ) ) {
            System.out.println( "direita" );
        }

        if ( isKeyPressed( KEY_K ) ) {
            System.out.println( "aaa" );
        }

    }

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

        value += getMouseWheelMove();
        drawRectangle( 0, value, 100, 100, BLACK);

        /*if ( isKeyDown( KeyEvent.VK_LEFT ) ) {
            System.out.println( "press" );
        }*/

    }

    public static void main( String[] args ) {
        new InputTests();
    }

}