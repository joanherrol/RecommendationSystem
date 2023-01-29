package domini;

/**
 * Classe contenidor per poder emmagatzemar la informació d'una valoració
 */
public class Valoracio
{
    private int id;
    private float puntuacio;

    /**
     * Constructora
     * @param id identificador de l'item
     * @param puntuacio puntuació de la valoració de l'item
     */
    public Valoracio(int id, float puntuacio)
    {
        this.id = id;
        this.puntuacio = puntuacio;
    }

    /**
     * Seteja l'item a la valoració
     * @param id identificador de l'item
     */
    public void setId(int id)
    {
        this.id = id;
    }

    /**
     * Seteja la puntuació de la valoració
     * @param puntuacio puntuació de la valoració
     */
    public void setPuntuacio(float puntuacio)
    {
        this.puntuacio = puntuacio;
    }

    /**
     * Retorna l'identificador de l'item
     * @return identificador de l'item
     */
    public int getId()
    {
        return id;
    }

    /**
     * Retorna la puntuació de la valoració
     * @return puntuació de la valoració
     */
    public float getPuntuacio()
    {
        return puntuacio;
    }
}
