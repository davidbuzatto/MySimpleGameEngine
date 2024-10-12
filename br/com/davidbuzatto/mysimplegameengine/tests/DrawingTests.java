package br.com.davidbuzatto.mysimplegameengine.tests;

import br.com.davidbuzatto.mysimplegameengine.core.Engine;
import br.com.davidbuzatto.mysimplegameengine.geom.*;

/**
 * Testes de desenho.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class DrawingTests extends Engine {

    public DrawingTests() {
        super( 800, 600, "Test Window", true, 60 );
    }

    @Override
    public void create() {
    }

    @Override
    public void update() {
    }

    @Override
    public void draw() {

        drawFps( 10, 30 );
        //rotate( 90, getScreenWidth() / 2, getScreenHeight() / 2 );
        //translate( 100, 100 );
        //scale( 2, 2 );

        drawUsingPrimitives();
        drawUsingObjects();

    }

    @SuppressWarnings( "unused" )
    private void drawUsingPrimitives() {

        drawPixel( 50, 50, BLACK );
        drawLine( 60, 60, 100, 100, BLACK );

        fillRectangle( 50, 120, 50, 100, BLUE );
        drawRectangle( 50, 120, 50, 100, BLACK );

        fillRectangle( 50, 120, 50, 100, BLUE );
        drawRectangle( 50, 120, 50, 100, BLACK );

        fillRectanglePro( 50, 240, 50, 100, 50, 240, 15, BLUE );
        drawRectanglePro( 50, 240, 50, 100, 50, 240, 15, BLACK );

        fillRoundRectangle( 50, 370, 80, 60, 20, BLUE );
        drawRoundRectangle( 50, 370, 80, 60, 20, BLACK );

        fillRectangleGradientH( 50, 450, 100, 50, BLUE, GREEN );
        fillRectangleGradientV( 50, 520, 100, 50, BLUE, GREEN );

        fillCircle( 250, 70, 30, MAROON );
        drawCircle( 250, 70, 30, BLACK );

        fillEllipse( 250, 160, 60, 30, MAROON );
        drawEllipse( 250, 160, 60, 30, BLACK );

        fillCircleSector( 250, 220, 30, 0, 130, MAROON );
        drawCircleSector( 250, 220, 30, 0, 130, BLACK );

        fillEllipseSector( 250, 280, 60, 30, 0, 130, MAROON );
        drawEllipseSector( 250, 280, 60, 30, 0, 130, BLACK );

        fillArc( 250, 350, 60, 30, 0, 130, MAROON );
        drawArc( 250, 350, 60, 30, 0, 130, BLACK );

        fillRing( 250, 400, 10, 30, 0, 130, 50, MAROON );
        drawRing( 250, 400, 10, 30, 0, 130, 50, BLACK );

        fillTriangle( 400, 50, 440, 100, 360, 100, ORANGE );
        drawTriangle( 400, 50, 440, 100, 360, 100, BLACK );

        fillPolygon( 400, 160, 5, 35, 0, ORANGE );
        drawPolygon( 400, 160, 5, 35, 0, BLACK );

        fillQuadCurve( 400, 220, 450, 270, 400, 320, ORANGE );
        drawQuadCurve( 400, 220, 450, 270, 400, 320, BLACK );

        fillCubicCurve( 400, 340, 350, 380, 450, 420, 400, 460, ORANGE );
        drawCubicCurve( 400, 340, 350, 380, 450, 420, 400, 460, BLACK );
        
    }

    @SuppressWarnings( "unused" )
    private void drawUsingObjects() {

        Point point = new Point( 50, 50 );
        point.draw( this, BLACK );

        Vector vector = new Vector( 30, 30 );
        vector.draw( this, BLACK );

        Line line = new Line( 60, 60, 100, 100 );
        line.draw( this, BLACK );

        Rectangle rectangle = new Rectangle( 50, 120, 50, 100 );
        rectangle.fill( this, BLUE );
        rectangle.draw( this, BLACK );

        RoundRectangle roundRectangle = new RoundRectangle( 50, 370, 80, 60, 20 );
        roundRectangle.fill( this, BLUE );
        roundRectangle.draw( this, BLACK );

        Circle circle = new Circle( 250, 70, 30 );
        circle.fill( this, MAROON );
        circle.draw( this, BLACK );

        Ellipse ellipse = new Ellipse( 250, 160, 60, 30 );
        ellipse.fill( this, MAROON );
        ellipse.draw( this, BLACK );

        CircleSector circleSector = new CircleSector( 250, 220, 30, 0, 130 );
        circleSector.fill( this, MAROON );
        circleSector.draw( this, BLACK );

        EllipseSector ellipseSector = new EllipseSector( 250, 280, 60, 30, 0, 130 );
        ellipseSector.fill( this, MAROON );
        ellipseSector.draw( this, BLACK );

        Arc arc = new Arc( 250, 350, 60, 30, 0, 130 );
        arc.fill( this, MAROON );
        arc.draw( this, BLACK );

        Ring ring = new Ring( 250, 400, 10, 30, 0, 130, 50 );
        ring.fill( this, MAROON );
        ring.draw( this, BLACK );

        Triangle triangle = new Triangle( 400, 50, 440, 100, 360, 100 );
        triangle.fill( this, ORANGE );
        triangle.draw( this, BLACK );

        Polygon polygon = new Polygon( 400, 160, 5, 35, 0 );
        polygon.fill( this, ORANGE );
        polygon.draw( this, BLACK );


        QuadCurve quadCurve = new QuadCurve( 400, 220, 450, 270, 400, 320 );
        quadCurve.fill( this, ORANGE );
        quadCurve.draw( this, BLACK );

        CubicCurve cubicCurve = new CubicCurve( 400, 340, 350, 380, 450, 420, 400, 460 );
        cubicCurve.fill( this, ORANGE );
        cubicCurve.draw( this, BLACK );

    }

    public static void main( String[] args ) {
        new DrawingTests();
    }

}