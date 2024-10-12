package br.com.davidbuzatto.mysimplegameengine.geom;

import br.com.davidbuzatto.mysimplegameengine.core.Drawable;
import br.com.davidbuzatto.mysimplegameengine.core.Engine;

import java.awt.Color;

/**
 * Classe para representação de um setor circular.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class CircleSector implements Drawable {
    
    public double x;
    public double y;
    public double radius;
    public double startAngle;
    public double endAngle;

    public CircleSector( double x, double y, double radius, double startAngle, double endAngle ) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.startAngle = startAngle;
        this.endAngle = endAngle;
    }

    @Override
    public void draw( Engine engine, Color color ) {
        engine.drawCircleSector( this, color );
    }

    @Override
    public void fill( Engine engine, Color color ) {
        engine.fillCircleSector( this, color );
    }

    @Override
    public String toString() {
        return String.format( "CircleSector[%.2f, %.2f, %.2f, %.2f, %.2f]", x, y, radius, startAngle, endAngle );
    }

}
