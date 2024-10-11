package br.com.davidbuzatto.mysimplegameengine.geom;

import br.com.davidbuzatto.mysimplegameengine.core.Drawable;
import br.com.davidbuzatto.mysimplegameengine.core.Engine;

import java.awt.Color;

/**
 * Classe para representação de uma curva Bézier quadrática.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class QuadCurve implements Drawable {
    
    public double x1;
    public double y1;
    public double cx;
    public double cy;
    public double x2;
    public double y2;

    public QuadCurve( double x1, double y1, double cx, double cy, double x2, double y2 ) {
        this.x1 = x1;
        this.y1 = y1;
        this.cx = cx;
        this.cy = cy;
        this.x2 = x2;
        this.y2 = y2;
    }

    @Override
    public void draw( Engine engine, Color color ) {
        engine.drawSplineSegmentBezierQuadratic( this, color );
    }

    @Override
    public void fill( Engine engine, Color color ) {
        engine.drawSplineSegmentBezierQuadratic( this, color );
    }

    @Override
    public String toString() {
        return String.format( "QuadCurve2D[%.2f, %.2f, %.2f, %.2f, %.2f, %.2f]", x1, y1, cx, cy, x2, x2 );
    }

}
