package br.com.davidbuzatto.mysimplegameengine.utils;

import br.com.davidbuzatto.mysimplegameengine.geom.CubicCurve;
import br.com.davidbuzatto.mysimplegameengine.geom.Line;
import br.com.davidbuzatto.mysimplegameengine.geom.Point;
import br.com.davidbuzatto.mysimplegameengine.geom.QuadCurve;
import br.com.davidbuzatto.mysimplegameengine.geom.Vector;

import java.awt.Color;

/**
 * Classe com métodos estáticos utilitários.
 * As implementações são baseadas na raylib e em seus módulos (www.raylib.com)
 * 
 * @author Prof. Dr. David Buzatto
 */
public class Utils {
    
    /**
     * Realiza a interpolação linear entre dois valores.
     * 
     * @param start valor inicial.
     * @param end valor final.
     * @param amount quantidade (0 a 1)
     * @return A interpolação linear entre dois valores.
     */
    public static double lerp( double start, double end, double amount ) {
        return start + amount * ( end - start );
    }

    /**
     * Limita um valor entre dois valores.
     * 
     * @param value O valor.
     * @param min O valor mínimo.
     * @param max O valor máximo.
     * @return O valor fixado entre mínimo e máximo.
     */
    public static double clamp( double value, double min, double max ) {
        double result = value < min ? min : value;
        if ( result > max ) result = max;
        return result;
    }    

    /**
     * Normaliza o valor dentro do intervalo fornecido.
     * 
     * @param value O valor.
     * @param start O valor inicial.
     * @param end O valor final.
     * @return O valor normalizado entre o valor inicial e final.
     */
    public static double normalize(double value, double start, double end ) {
        return ( value - start ) / ( end - start );
    }

    /**
     * Remapeia um valor entre um intervalo de entrada e um intervalo de saída.
     * 
     * @param value O valor.
     * @param inputStart O valor inicial do intervalo de entrada.
     * @param inputEnd O valor final do intervalo de entrada.
     * @param outputStart O valor inicial do intervalo de saída.
     * @param outputEnd O valor final do intervalo de saída.
     * @return O valor remapeado.
     */
    public static double remap( double value, double inputStart, double inputEnd, double outputStart, double outputEnd ) {
        return ( value - inputStart ) / ( inputEnd - inputStart ) * ( outputEnd - outputStart ) + outputStart;
    }

    /**
     * Coloca um valor entre um valor mínimo e máximo.
     * 
     * @param value O valor.
     * @param min O valor mínimo.
     * @param max O valor máximo.
     * @return O valor ajustado.
     */
    public static double wrap( double value, double min, double max ) {
        return value - ( max - min ) * Math.floor( ( value - min ) / ( max - min ) );
    }

    /**
     * Realiza a interpolação linear entre dois pontos.
     * 
     * @param start ponto inicial.
     * @param end ponto final.
     * @param amount quantidade (0 a 1)
     * @return Um ponto que representa a interpolação linear entre dois pontos.
     */
    public static Point lerp( Point start, Point end, double amount ) {
        double x = start.x + ( end.x - start.x ) * amount;
        double y = start.y + ( end.y - start.y ) * amount;
        return new Point( x, y );
    }

    /**
     * Cria um vetor 2D com ambos os componentes iguais a 0.0.
     * 
     * @return Um vetor 2D com ambos os componentes iguais a 0.0.
     */
    public static Vector vector2DZero() {
        return new Vector( 0.0, 0.0 );
    }

    /**
     * Cria um vetor 2D com ambos os componentes iguais a 1.0.
     * 
     * @return Um vetor 2D com ambos os componentes iguais a 1.0.
     */
    public static Vector vector2DOne() {
        return new Vector( 1.0, 1.0 );
    }

    /**
     * Soma dois vetores 2D.
     * 
     * @param v1 Um vetor.
     * @param v2 Outro vetor.
     * @return Um novo vetor 2D com a soma dos vetores passados.
     */
    public static Vector add( final Vector v1, final Vector v2 ) {
        return new Vector( v1.x + v2.x, v1.y + v2.y );
    }

    /**
     * Soma um valor a um vetor 2D.
     * 
     * @param v Um vetor.
     * @param value O valor a somar.
     * @return Um novo vetor 2D com os componentes somados ao valor passado.
     */
    public static Vector addValue( final Vector v, double value ) {
        return new Vector( v.x + value, v.y + value );
    }

    /**
     * Subtrai dois vetores 2D.
     * 
     * @param v1 Um vetor.
     * @param v2 Outro vetor.
     * @return Um novo vetor 2D com a subtração dos vetores passados.
     */
    public static Vector subtract( final Vector v1, final Vector v2 ) {
        return new Vector( v1.x - v2.x, v1.y - v2.y );
    }

    /**
     * Subtrai um valor de um vetor 2D.
     * 
     * @param v Um vetor.
     * @param value O valor a subtrair.
     * @return Um novo vetor 2D com os componentes subtraídos do valor passado.
     */
    public static Vector subtractValue( final Vector v, double value ) {
        return new Vector( v.x - value, v.y - value );
    }

    /**
     * Calcula o comprimento de um vetor 2D.
     * 
     * @param v Um vetor.
     * @return O comprimento do vetor passado.
     */
    public static double length( final Vector v ) {
        return Math.sqrt( v.x * v.x + v.y * v.y );
    }

    /**
     * Calcula o produto escalar entre dois vetores 2D.
     * 
     * @param v1 Um vetor.
     * @param v2 Outro vetor.
     * @return O produto escalar entre os vetores passados.
     */
    public static double dotProduct( final Vector v1, final Vector v2 ) {
        return v1.x * v2.x + v1.y * v2.y;
    }

    /**
     * Calcula a distância entre dois vetores 2D.
     * 
     * @param v1 Um vetor.
     * @param v2 Outro vetor.
     * @return A distância entre os vetores passados.
     */
    public static double distance( final Vector v1, final Vector v2 ) {
        return Math.sqrt( ( v1.x - v2.x ) * ( v1.x - v2.x ) + ( v1.y - v2.y ) * ( v1.y - v2.y ) );
    }

    /**
     * Calcula o ângulo entre dois vetores 2D, sendo que esse ângulo
     * é calculado a partir do ponto de origem (0, 0).
     * 
     * @param v1 Um vetor.
     * @param v2 Outro vetor.
     * @return O ângulo entre os dois vetores passados.
     */
    public static double angle( final Vector v1, final Vector v2 ) {

        double dot = v1.x * v2.x + v1.y * v2.y;
        double det = v1.x * v2.y - v1.y * v2.x;

        return Math.atan2( det, dot );

    }

    /**
     * Escalona um vetor 2D, análogo à multiplicação por escalar.
     * 
     * @param v O vetor.
     * @param scale A escala.
     * @return Um novo vetor 2D escalonado.
     */
    public static Vector scale( final Vector v, double scale ) {
        return new Vector( v.x * scale, v.y * scale );
    }

    /**
     * Multiplica dois vetores 2D.
     * 
     * @param v1 Um vetor.
     * @param v2 Outro vetor.
     * @return Um novo vetor com o resultado da multiplicação dos vetores passados.
     */
    public static Vector multiply( final Vector v1, final Vector v2 ) {
        return new Vector( v1.x * v2.x, v1.y * v2.y );
    }

    /**
     * Nega um vetor 2D.
     * 
     * @param v Um vetor.
     * @return Um novo vetor com a negação do vetor passado.
     */
    public static Vector negate( final Vector v ) {
        return new Vector( -v.x, -v.y );
    }

    /**
     * Divide dois vetores.
     * 
     * @param v1 Um vetor.
     * @param v2 Outro vetor.
     * @return Um novo vetor com o resultado da divisão dos vetores passados.
     */
    public static Vector divide( final Vector v1, final Vector v2 ) {
        return new Vector( v1.x / v2.x, v1.y / v2.y );
    }

    /**
     * Normaliza um vetor 2D.
     * 
     * @param v Um vetor.
     * @return Um novo vetor 2D normalizado.
     */
    public static Vector normalize( final Vector v ) {

        Vector result = vector2DZero();
        double length = Math.sqrt( v.x * v.x + v.y * v.y );

        if ( length > 0 ) {
            double ilength = 1.0 / length;
            result.x = v.x * ilength;
            result.y = v.y * ilength;
        }

        return result;

    }

    /**
     * Realiza a interpolação linear entre dois vetores.
     * 
     * @param start vetor inicial.
     * @param end vetor final.
     * @param amount quantidade (0 a 1)
     * @return Um vetor que representa a interpolação linear entre dois vetores.
     */
    public static Vector lerp( final Vector start, final Vector end, double amount ) {
        double x = start.x + ( end.x - start.x ) * amount;
        double y = start.y + ( end.y - start.y ) * amount;
        return new Vector( x, y );
    }

    /**
     * Calcula um vetor 2D refletido pela normal.
     * 
     * @param v Um vetor.
     * @param normal Vetor normal.
     * @return Um novo vetor refletido.
     */
    public static Vector reflect( final Vector v, final Vector normal ) {

        Vector result = vector2DZero();

        double dotProduct = ( v.x * normal.x + v.y * normal.y ); // produto escalar

        result.x = v.x - ( 2.0 * normal.x ) * dotProduct;
        result.y = v.y - ( 2.0 * normal.y ) * dotProduct;

        return result;

    }

    /**
     * Obtem um novo vetor 2D com o mínimo dos componentes.
     * 
     * @param v1 Um vetor.
     * @param v2 Outro vetor.
     * @return Um novo vetor com o mínimo dos componentes dos vetores passados.
     */
    public static Vector min( final Vector v1, final Vector v2 ) {

        Vector result = vector2DZero();

        result.x = Math.min( v1.x, v2.x );
        result.y = Math.min( v1.y, v2.y );

        return result;

    }

    /**
     * Obtem um novo vetor 2D com o máximo dos componentes.
     * 
     * @param v1 Um vetor.
     * @param v2 Outro vetor.
     * @return Um novo vetor com o máximo dos componentes dos vetores passados.
     */
    public static Vector max( final Vector v1, final Vector v2 ) {

        Vector result = vector2DZero();

        result.x = Math.max( v1.x, v2.x );
        result.y = Math.max( v1.y, v2.y );

        return result;

    }

    /**
     * Rotaciona um vetor 2D usando um ângulo (em radianos).
     * 
     * @param v Um vetor.
     * @param angle O ângulo.
     * @return Um novo vetor rotacionado.
     */
    public static Vector rotate( final Vector v, double angle ) {

        Vector result = vector2DZero();

        double cos = Math.cos( angle );
        double sin = Math.sin( angle );

        result.x = v.x * cos - v.y * sin;
        result.y = v.x * sin + v.y * cos;

        return result;

    }

    /**
     * Cria um novo vetor movido em direção a um alvo.
     * 
     * @param v Um vetor.
     * @param target O alvo.
     * @param maxDistance A distância máxima.
     * @return Um novo vetor movido em direção ao alvo.
     */
    public static Vector moveTowards( final Vector v, final Vector target, double maxDistance ) {

        Vector result = vector2DZero();

        double dx = target.x - v.x;
        double dy = target.y - v.y;
        double value = dx * dx + dy * dy;

        if ( ( value == 0 ) || ( ( maxDistance >= 0 ) && ( value <= maxDistance * maxDistance ) ) ) {
            return target;
        }

        double dist = Math.sqrt( value );

        result.x = v.x + dx / dist * maxDistance;
        result.y = v.y + dy / dist * maxDistance;

        return result;

    }

    /**
     * Cria um vetor 2D invertido.
     * 
     * @param v Um vetor.
     * @return Um novo vetor invertido.
     */
    public static Vector invert( final Vector v ) {
        return new Vector( 1.0 / v.x, 1.0 / v.y );
    }

    /**
     * Limita um vetor entre dois vetores 2D.
     * 
     * @param v O vetor.
     * @param min O vetor mínimo.
     * @param max O vetor máximo.
     * @return Um novo vetor fixado entre o vetor mínimo e o vetor máximo.
     */
    public static Vector clamp( final Vector v, final Vector min, final Vector max ) {

        Vector result = vector2DZero();

        result.x = Math.min( max.x, Math.max( min.x, v.x ) );
        result.y = Math.min( max.y, Math.max( min.y, v.y ) );

        return result;

    }

    /**
     * Limita a magnitude de um vetor entre mínimo e máximo.
     * 
     * @param v O vetor.
     * @param min O valor mínimo.
     * @param max O valor máximo.
     * @return o valor fixado da magnitude entre os valores mínimo e máximo.
     */
    public static Vector clampValue( final Vector v, double min, double max ) {

        Vector result = new Vector( v.x, v.y );

        double length = v.x * v.x + v.y * v.y;

        if ( length > 0.0 ) {

            length = Math.sqrt( length );

            double scale = 1;    // By default, 1 as the neutral element.
            if ( length < min ) {
                scale = min / length;
            } else if ( length > max ) {
                scale = max / length;
            }

            result.x = v.x * scale;
            result.y = v.y * scale;

        }

        return result;

    }

    /**
     * Cria um ponto 2D usando um vetor 2D.
     * 
     * @param v Um vetor 2D.
     * @return Um ponto 2D.
     */
    public static Point vectorToPoint( Vector v ) {
        return new Point( v.x, v.y );
    }

    /**
     * Cria um vetor 2D usando um ponto 2D.
     * 
     * @param p Um ponto 2D.
     * @return Um vetor 2D.
     */
    public static Vector pointToVector( Point p ) {
        return new Vector( p.x, p.y );
    }

    /**
     * Realiza a interpolação linear entre duas cores.
     * 
     * @param start cor inicial.
     * @param end cor final.
     * @param amount quantidade (0 a 1)
     * @return Uma cor que representa a interpolação linear entre duas cores pontos.
     */
    public static Color lerp( final Color start, final Color end, double amount ) {
        int r = (int) clamp( lerp( start.getRed(), end.getRed(), amount ), 0, 255 );
        int g = (int) clamp( lerp( start.getGreen(), end.getGreen(), amount ), 0, 255 );
        int b = (int) clamp( lerp( start.getBlue(), end.getBlue(), amount ), 0, 255 );
        int a = (int) clamp( lerp( start.getAlpha(), end.getAlpha(), amount ), 0, 255 );
        return new Color( r, g, b, a );
    }

    /**
     * Obtém um ponto dentro de uma linha.
     * 
     * @param p1x coordenada x do ponto inicial.
     * @param p1y coordenada y do ponto inicial.
     * @param p2x coordenada x do ponto final.
     * @param p2y coordenada y do ponto final.
     * @param t Um valor de 0 a 1 que representa a posição, em porcentagem, do ponto desejado.
     * @return O ponto dentro da linha.
     */
    public static Point getPointAtLine( double p1x, double p1y, double p2x, double p2y, double t ) {

        double x = p1x * ( 1.0f - t ) + p2x * t;
        double y = p1y * ( 1.0f - t ) + p2y * t;

        return new Point( x, y );
        
    }

    /**
     * Obtém um ponto dentro de uma linha.
     * 
     * @param p1 ponto final.
     * @param p2 ponto final.
     * @param t Um valor de 0 a 1 que representa a posição, em porcentagem, do ponto desejado.
     * @return O ponto dentro da linha.
     */
    public static Point getPointAtLine( Vector p1, Vector p2, double t ) {
        return getPointAtLine( p1.x, p1.y, p2.x, p2.y, t )        ;
    }

    /**
     * Obtém um ponto dentro de uma linha.
     * 
     * @param p1 ponto final.
     * @param p2 ponto final.
     * @param t Um valor de 0 a 1 que representa a posição, em porcentagem, do ponto desejado.
     * @return O ponto dentro da linha.
     */
    public static Point getPointAtLine( Point p1, Point p2, double t ) {
        return getPointAtLine( p1.x, p1.y, p2.x, p2.y, t )        ;
    }

    /**
     * Obtém um ponto dentro de uma linha.
     * 
     * @param line uma linha.
     * @param t Um valor de 0 a 1 que representa a posição, em porcentagem, do ponto desejado.
     * @return O ponto dentro da linha.
     */
    public static Point getPointAtLine( Line line, double t ) {
        return getPointAtLine( line.x1, line.y1, line.x2, line.y2, t );
    }

    /**
     * Obtém um ponto dentro de uma curva quadrática (curva Bézier quadrática).
     * 
     * @param p1x coordenada x do ponto inicial.
     * @param p1y coordenada y do ponto inicial.
     * @param cx coordenada x do ponto de controle.
     * @param cy coordenada y do ponto de controle.
     * @param p2x coordenada x do ponto final.
     * @param p2y coordenada y do ponto final.
     * @param t Um valor de 0 a 1 que representa a posição, em porcentagem, do ponto desejado.
     * @return O ponto dentro da curva.
     */
    public static Point getPointAtQuadCurve( double p1x, double p1y, double cx, double cy, double p2x, double p2y, double t ) {

        double a = Math.pow( 1.0 - t, 2 );
        double b = 2.0 * ( 1.0 - t ) * t;
        double c = Math.pow( t, 2 );

        double x = a * p1x + b * cx + c * p2x;
        double y = a * p1y + b * cy + c * p2y;

        return new Point( x, y );

    }

    /**
     * Obtém um ponto dentro de uma curva quadrática (curva Bézier quadrática).
     * 
     * @param p1 ponto inicial.
     * @param c ponto de controle.
     * @param p2 ponto final.
     * @param t Um valor de 0 a 1 que representa a posição, em porcentagem, do ponto desejado.
     * @return O ponto dentro da curva.
     */
    public static Point getPointAtQuadCurve( Vector p1, Vector c, Vector p2, double t ) {
        return getPointAtQuadCurve( p1.x, p1.y, c.x, c.y, p2.x, p2.y, t );
    }

    /**
     * Obtém um ponto dentro de uma curva quadrática (curva Bézier quadrática).
     * 
     * @param p1 ponto inicial.
     * @param c ponto de controle.
     * @param p2 ponto final.
     * @param t Um valor de 0 a 1 que representa a posição, em porcentagem, do ponto desejado.
     * @return O ponto dentro da curva.
     */
    public static Point getPointAtQuadCurve( Point p1, Point c, Point p2, double t ) {
        return getPointAtQuadCurve( p1.x, p1.y, c.x, c.y, p2.x, p2.y, t );
    }
    
    /**
     * Obtém um ponto dentro de uma curva quadrática (curva Bézier quadrática).
     * 
     * @param quadCurve uma curva Bézier quadrática.
     * @param t Um valor de 0 a 1 que representa a posição, em porcentagem, do ponto desejado.
     * @return O ponto dentro da curva.
     */
    public static Point getPointAtQuadCurve( QuadCurve quadCurve, double t ) {
        return getPointAtQuadCurve( quadCurve.x1, quadCurve.y1, quadCurve.cx, quadCurve.cy, quadCurve.x2, quadCurve.y2, t );
    }

    /**
     * Obtém um ponto dentro de uma curva cúbica (curva Bézier cúbica).
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
     * @return O ponto dentro da curva.
     */
    public static Point getPointAtCubicCurve( double p1x, double p1y, double c1x, double c1y, double c2x, double c2y, double p2x, double p2y, double t ) {

        double a = Math.pow( 1.0 - t, 3 );
        double b = 3.0 * Math.pow( 1.0 - t, 2 ) * t;
        double c = 3.0 * ( 1.0 - t ) * Math.pow( t, 2 );
        double d = Math.pow( t, 3 );

        double x = a * p1x + b * c1x + c * c2x + d * p2x;
        double y = a * p1y + b * c1y + c * c2y + d * p2y;

        return new Point( x, y );

    }

    /**
     * Obtém um ponto dentro de uma curva cúbica (curva Bézier cúbica).
     * 
     * @param p1 ponto inicial.
     * @param c1 primeiro ponto de controle.
     * @param c2 segundo ponto de controle.
     * @param p2 ponto final.
     * @param t Um valor de 0 a 1 que representa a posição, em porcentagem, do ponto desejado.
     * @return O ponto dentro da curva.
     */
    public static Point getPointAtCubicCurve( Vector p1, Vector c1, Vector c2, Vector p2, double t ) {
        return getPointAtCubicCurve( p1.x, p1.y, c1.x, c1.y, c2.x, c2.y, p2.x, p2.y, t );
    }

    /**
     * Obtém um ponto dentro de uma curva cúbica (curva Bézier cúbica).
     * 
     * @param p1 ponto inicial.
     * @param c1 primeiro ponto de controle.
     * @param c2 segundo ponto de controle.
     * @param p2 ponto final.
     * @param t Um valor de 0 a 1 que representa a posição, em porcentagem, do ponto desejado.
     * @return O ponto dentro da curva.
     */
    public static Point getPointAtCubicCurve( Point p1, Point c1, Point c2, Point p2, double t ) {
        return getPointAtCubicCurve( p1.x, p1.y, c1.x, c1.y, c2.x, c2.y, p2.x, p2.y, t );
    }

    /**
     * Obtém um ponto dentro de uma curva cúbica (curva Bézier cúbica).
     * 
     * @param cubicCurve uma curva Bézier cúbica.
     * @param t Um valor de 0 a 1 que representa a posição, em porcentagem, do ponto desejado.
     * @return O ponto dentro da curva.
     */
    public static Point getPointAtCubicCurve( CubicCurve cubicCurve, double t ) {
        return getPointAtCubicCurve( cubicCurve.x1, cubicCurve.y1, cubicCurve.c1x, cubicCurve.c1y, cubicCurve.c2x, cubicCurve.c2y, cubicCurve.x2, cubicCurve.y2, t );
    }

}
