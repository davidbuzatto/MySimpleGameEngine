package br.com.davidbuzatto.mysimplegameengine.geom;

import br.com.davidbuzatto.mysimplegameengine.core.Drawable;
import br.com.davidbuzatto.mysimplegameengine.core.Engine;

import java.awt.Color;

/**
 * Classe para representação de um caminho em duas dimensões.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class Path implements Drawable {
    
    public java.awt.geom.Path2D.Double path = new java.awt.geom.Path2D.Double();

    public void moveTo( double x, double y ) {
        path.moveTo( x, y );
    }

    public void lineTo( double x, double y ) {
        path.lineTo( x, y );
    }

    public void quadTo( double cx, double cy, double x, double y ) {
        path.quadTo( cx, cy, x, y );
    }

    public void cubicTo( double c1x, double c1y, double c2x, double c2y, double x, double y ) {
        path.curveTo( c1x, c1y, c2x, c2y, x, y );
    }

    public void closePath() {
        path.closePath();
    }

    @Override
    public void draw( Engine engine, Color color ) {
        engine.drawPath( this, color );
    }

    @Override
    public void fill( Engine engine, Color color ) {
        engine.fillPath( this, color );
    }

    @Override
    public String toString() {
        return path.toString();
    }

}
