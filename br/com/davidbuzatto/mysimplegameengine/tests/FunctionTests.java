package br.com.davidbuzatto.mysimplegameengine.tests;

import java.awt.Color;

import br.com.davidbuzatto.mysimplegameengine.core.Engine;
import br.com.davidbuzatto.mysimplegameengine.utils.Utils;

/**
 * Classe básica de exemplo de utilização da engine.
 * 
 * @author Prof. Dr. David Buzatto
 * @copyright Copyright (c) 2024
 */
public class FunctionTests extends Engine {

    // declaração de variáveis

    public FunctionTests() {
        super( 800, 600, "Test Window", 60, true );
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

        for ( int i = 0; i <= 360; i++ ) {
            drawRectangle( 100 + i, 100 + i, 2, 2, Utils.colorFromHSV( i, 1, 1 ) );
        }

        Color c = LIME;
        fillRectangle( 0, 0, 100, 100, c );
        fillRectangle( 0, 100, 100, 100, Utils.colorAlpha( c, 0.5 ) );
        fillRectangle( 0, 200, 100, 100, Utils.colorTint( c, WHITE ) );
        fillRectangle( 0, 300, 100, 100, Utils.colorBrightness( c, -0.5 ) );
        fillRectangle( 0, 400, 100, 100, Utils.colorContrast( c, -0.5 ) );

    }

    public static void main( String[] args ) {
        new FunctionTests();
    }

}