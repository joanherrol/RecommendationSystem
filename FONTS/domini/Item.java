package domini;

import java.util.Map;

/**
 * Classe contenidor per poder emmagatzemar els atributs d'un item
 */
public class Item
{
    private int id;
    private Map<String, Object> cjtAtributs;

    /**
     * Constructora
     * @param id identificador de l'item
     * @param cjtAtributs conjunt d'atributs de l'item
     */
    public Item(int id, Map<String, Object> cjtAtributs)
    {
        this.id = id;
        this.cjtAtributs = cjtAtributs;
    }

    /**
     * Seteja l'identificador de l'item
     * @param id identificador de l'item
     */
    public void setId(int id)
    {
        this.id = id;
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
     * Retorna el conjunt d'atributs de l'item
     * @return conjunt d'atributs
     */
    public Map<String, Object> getCjtAtributs()
    {
        return cjtAtributs;
    }
}