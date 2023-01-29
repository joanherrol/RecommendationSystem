package presentacio;

/**
 * Esquelet per construir totes les vistes
 */
public abstract class Vista
{
    /**Referència al controlador presentació**/
    protected CtrlPresentacio controladorPresentacio;

    /**
     * Funció abstracta per activar la vista
     */
    abstract void activar();

    /**
     * Funció abstracta per desactivar la vista
     */
    abstract void desactivar();
}
