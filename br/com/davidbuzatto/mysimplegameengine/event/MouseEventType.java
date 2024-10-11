package br.com.davidbuzatto.mysimplegameengine.event;

/**
 * Enumeração para representar os tipos de eventos do mouse.
 */
public enum MouseEventType {
    CLICKED,    // clicou (pressionar e soltar)
    PRESSED,    // pressionou
    RELEASED,   // soltou
    ENTERED,    // entrou
    EXITED,     // saiu
    DRAGGED,    // arrastou (botão pressionado + movimento)
    MOVED       // moveu
}
