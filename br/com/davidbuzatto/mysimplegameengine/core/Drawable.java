package br.com.davidbuzatto.mysimplegameengine.core;

import java.awt.Color;

/**
 * Interface para elementos desenháveis.
 * 
 * @author Prof. Dr. David Buzatto
 */
public interface Drawable {
    
    /**
     * Desenha o elemento corrente usando a engine.
     * 
     * @param engine engine utilizada.
     * @param color cor do desenho.
     */
    void draw( Engine engine, Color color );

    /**
     * Pinta o elemento corrente usando a engine.
     * 
     * @param engine engine utilizada.
     * @param color cor do desenho.
     */
    void fill( Engine engine, Color color );

}
