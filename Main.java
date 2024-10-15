import java.awt.Font;

import br.com.davidbuzatto.mysimplegameengine.core.Engine;
import br.com.davidbuzatto.mysimplegameengine.geom.Vector2;

/**
 * Classe básica de exemplo de utilização da engine.
 * 
 * @author Prof. Dr. David Buzatto
 * @copyright Copyright (c) 2024
 */
public class Main extends Engine {

    // declaração de variáveis

    public Main() {

        // cria a janela do jogo ou simulação
        super( 
            800,                  // 800 pixels de largura
            450,                  // 600 pixels de largura
            "Título da Janela",   // título da janela
            60,                   // 60 quadros por segundo
            true,                 // ativa a suavização (antialiasing)
            false,                // redimensionável (resizable)
            false,                // tela cheia (fullscreen na resolução atual)
            false,                // sem decoração (undecorated)
            false                 // sempre acima (always on top)
        );

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

        clearBackground( WHITE );
        setFontStyle( Font.BOLD );
        
        String text = "Basic game template";
        Vector2 m = new Vector2( measureText( text, 40 ), 40 );
        double x = getScreenWidth() / 2 - m.x / 2;
        double y = getScreenHeight() / 2 - m.y / 2;
        fillRectangle( x, y, m.x, m.y, BLACK );
        drawText( text, x, y + 30, 40, WHITE );

        drawFPS( 10, 20 );

    }

    public static void main( String[] args ) {
        new Main();
    }

}