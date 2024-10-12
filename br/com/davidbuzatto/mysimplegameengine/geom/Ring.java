package br.com.davidbuzatto.mysimplegameengine.geom;

import br.com.davidbuzatto.mysimplegameengine.core.Drawable;
import br.com.davidbuzatto.mysimplegameengine.core.Engine;

import java.awt.Color;

/**
 * Classe para representação de um anel em duas dimensões.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class Ring implements Drawable {
    
    public double x;
    public double y;
    public double innerRadius;
    public double outerRadius;
    public double startAngle;
    public double endAngle;
    public int segments;

    public Ring( double x, double y, double innerRadius, double outerRadius, double startAngle, double endAngle, int segments ) {
        this.x = x;
        this.y = y;
        this.innerRadius = innerRadius;
        this.outerRadius = outerRadius;
        this.startAngle = startAngle;
        this.endAngle = endAngle;
        this.segments = segments;
    }

    public Ring( double x, double y, double innerRadius, double outerRadius, double startAngle, double endAngle ) {
        this( x, y, innerRadius, outerRadius, startAngle, endAngle, 30 );
    }

    @Override
    public void draw( Engine engine, Color color ) {
        engine.drawRing( this, color );
    }

    @Override
    public void fill( Engine engine, Color color ) {
        engine.fillRing( this, color );
    }

    @Override
    public String toString() {
        return String.format( "Ring[%.2f, %.2f, %.2f, %.2f, %.2f, %.2f, %d]", x, y, innerRadius, outerRadius, startAngle, endAngle, segments );
    }

}
