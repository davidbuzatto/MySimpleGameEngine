package br.com.davidbuzatto.mysimplegameengine.utils;

import br.com.davidbuzatto.mysimplegameengine.geom.CubicCurve;
import br.com.davidbuzatto.mysimplegameengine.geom.Line;
import br.com.davidbuzatto.mysimplegameengine.geom.Point;
import br.com.davidbuzatto.mysimplegameengine.geom.QuadCurve;
import br.com.davidbuzatto.mysimplegameengine.geom.Vector2D;

/**
 * Classe com métodos estáticos utilitários para splines.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class SplineUtils {

    /**
     * Obtém um ponto dentro de uma spline linear.
     * 
     * @param p1x coordenada x do ponto inicial.
     * @param p1y coordenada y do ponto inicial.
     * @param p2x coordenada x do ponto final.
     * @param p2y coordenada y do ponto final.
     * @param t Um valor de 0 a 1 que representa a posição, em porcentagem, do ponto desejado.
     * @return O ponto dentro da spline.
     */
    public static Point getSplinePointLinear( double p1x, double p1y, double p2x, double p2y, double t ) {

        double x = p1x * ( 1.0f - t ) + p2x * t;
        double y = p1y * ( 1.0f - t ) + p2y * t;

        return new Point( x, y );
        
    }

    /**
     * Obtém um ponto dentro de uma spline linear.
     * 
     * @param p1 ponto final.
     * @param p2 ponto final.
     * @param t Um valor de 0 a 1 que representa a posição, em porcentagem, do ponto desejado.
     * @return O ponto dentro da spline.
     */
    public static Point getSplinePointLinear( Vector2D p1, Vector2D p2, double t ) {
        return getSplinePointLinear( p1.x, p1.y, p2.x, p2.y, t )        ;
    }

    /**
     * Obtém um ponto dentro de uma spline linear.
     * 
     * @param p1 ponto final.
     * @param p2 ponto final.
     * @param t Um valor de 0 a 1 que representa a posição, em porcentagem, do ponto desejado.
     * @return O ponto dentro da spline.
     */
    public static Point getSplinePointLinear( Point p1, Point p2, double t ) {
        return getSplinePointLinear( p1.x, p1.y, p2.x, p2.y, t )        ;
    }

    /**
     * Obtém um ponto dentro de uma spline linear.
     * 
     * @param line uma linha.
     * @param t Um valor de 0 a 1 que representa a posição, em porcentagem, do ponto desejado.
     * @return O ponto dentro da spline.
     */
    public static Point getSplinePointLinear( Line line, double t ) {
        return getSplinePointLinear( line.x1, line.y1, line.x2, line.y2, t );
    }

    /**
     * Obtém um ponto dentro de uma spline quadrática (curva Bézier quadrática).
     * 
     * @param p1x coordenada x do ponto inicial.
     * @param p1y coordenada y do ponto inicial.
     * @param cx coordenada x do ponto de controle.
     * @param cy coordenada y do ponto de controle.
     * @param p2x coordenada x do ponto final.
     * @param p2y coordenada y do ponto final.
     * @param t Um valor de 0 a 1 que representa a posição, em porcentagem, do ponto desejado.
     * @return O ponto dentro da spline.
     */
    public static Point getSplinePointBezierQuad( double p1x, double p1y, double cx, double cy, double p2x, double p2y, double t ) {

        double a = Math.pow( 1.0 - t, 2 );
        double b = 2.0 * ( 1.0 - t ) * t;
        double c = Math.pow( t, 2 );

        double x = a * p1x + b * cx + c * p2x;
        double y = a * p1y + b * cy + c * p2y;

        return new Point( x, y );

    }

    /**
     * Obtém um ponto dentro de uma spline quadrática (curva Bézier quadrática).
     * 
     * @param p1 ponto inicial.
     * @param c ponto de controle.
     * @param p2 ponto final.
     * @param t Um valor de 0 a 1 que representa a posição, em porcentagem, do ponto desejado.
     * @return O ponto dentro da spline.
     */
    public static Point getSplinePointBezierQuad( Vector2D p1, Vector2D c, Vector2D p2, double t ) {
        return getSplinePointBezierQuad( p1.x, p1.y, c.x, c.y, p2.x, p2.y, t );
    }

    /**
     * Obtém um ponto dentro de uma spline quadrática (curva Bézier quadrática).
     * 
     * @param p1 ponto inicial.
     * @param c ponto de controle.
     * @param p2 ponto final.
     * @param t Um valor de 0 a 1 que representa a posição, em porcentagem, do ponto desejado.
     * @return O ponto dentro da spline.
     */
    public static Point getSplinePointBezierQuad( Point p1, Point c, Point p2, double t ) {
        return getSplinePointBezierQuad( p1.x, p1.y, c.x, c.y, p2.x, p2.y, t );
    }
    
    /**
     * Obtém um ponto dentro de uma spline quadrática (curva Bézier quadrática).
     * 
     * @param quadCurve uma curva Bézier quadrática.
     * @param t Um valor de 0 a 1 que representa a posição, em porcentagem, do ponto desejado.
     * @return O ponto dentro da spline.
     */
    public static Point getSplinePointBezierQuad( QuadCurve quadCurve, double t ) {
        return getSplinePointBezierQuad( quadCurve.x1, quadCurve.y1, quadCurve.cx, quadCurve.cy, quadCurve.x2, quadCurve.y2, t );
    }

    /**
     * Obtém um ponto dentro de uma spline cúbica (curva Bézier cúbica).
     * 
     * @param p1x coordenada x do ponto inicial.
     * @param p1y coordenada y do ponto inicial.
     * @param c1x coordenada x do primeiro ponto de controle.
     * @param c1y coordenada y do primeiro ponto de controle.
     * @param c2x coordenada x do segundo ponto de controle.
     * @param c2y coordenada y do segundo ponto de controle.
     * @param p2x coordenada x do ponto final.
     * @param p2y coordenada y do ponto final.
     * @param t Um valor de 0 a 1 que representa a posição, em porcentagem, do ponto desejado.
     * @return O ponto dentro da spline.
     */
    public static Point getSplinePointBezierCubic( double p1x, double p1y, double c1x, double c1y, double c2x, double c2y, double p2x, double p2y, double t ) {

        double a = Math.pow( 1.0 - t, 3 );
        double b = 3.0 * Math.pow( 1.0 - t, 2 ) * t;
        double c = 3.0 * ( 1.0 - t ) * Math.pow( t, 2 );
        double d = Math.pow( t, 3 );

        double x = a * p1x + b * c1x + c * c2x + d * p2x;
        double y = a * p1y + b * c1y + c * c2y + d * p2y;

        return new Point( x, y );

    }

    /**
     * Obtém um ponto dentro de uma spline cúbica (curva Bézier cúbica).
     * 
     * @param p1 ponto inicial.
     * @param c1 primeiro ponto de controle.
     * @param c2 segundo ponto de controle.
     * @param p2 ponto final.
     * @param t Um valor de 0 a 1 que representa a posição, em porcentagem, do ponto desejado.
     * @return O ponto dentro da spline.
     */
    public static Point getSplinePointBezierCubic( Vector2D p1, Vector2D c1, Vector2D c2, Vector2D p2, double t ) {
        return getSplinePointBezierCubic( p1.x, p1.y, c1.x, c1.y, c2.x, c2.y, p2.x, p2.y, t );
    }

    /**
     * Obtém um ponto dentro de uma spline cúbica (curva Bézier cúbica).
     * 
     * @param p1 ponto inicial.
     * @param c1 primeiro ponto de controle.
     * @param c2 segundo ponto de controle.
     * @param p2 ponto final.
     * @param t Um valor de 0 a 1 que representa a posição, em porcentagem, do ponto desejado.
     * @return O ponto dentro da spline.
     */
    public static Point getSplinePointBezierCubic( Point p1, Point c1, Point c2, Point p2, double t ) {
        return getSplinePointBezierCubic( p1.x, p1.y, c1.x, c1.y, c2.x, c2.y, p2.x, p2.y, t );
    }

    /**
     * Obtém um ponto dentro de uma spline cúbica (curva Bézier cúbica).
     * 
     * @param cubicCurve uma curva Bézier cúbica.
     * @param t Um valor de 0 a 1 que representa a posição, em porcentagem, do ponto desejado.
     * @return O ponto dentro da spline.
     */
    public static Point getSplinePointBezierCubic( CubicCurve cubicCurve, double t ) {
        return getSplinePointBezierCubic( cubicCurve.x1, cubicCurve.y1, cubicCurve.c1x, cubicCurve.c1y, cubicCurve.c2x, cubicCurve.c2y, cubicCurve.x2, cubicCurve.y2, t );
    }

}
