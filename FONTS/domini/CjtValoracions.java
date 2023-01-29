package domini;

import java.util.Map;
import java.util.HashMap;

/**
 * Classe contenidor per poder emmagatzemar les valoracions
 */
public class CjtValoracions
{
    private Map<Integer, Float> cjtValoracions;

    /**
     * Constructora per defecte
     */
    public CjtValoracions()
    {
        cjtValoracions = new HashMap<>();
    }

    /**
     * Seteja el conjunt de valoracions
     * @param cjtValoracions conjunt de valoracions
     */
    public void setCjtValoracions(Map<Integer, Float> cjtValoracions)
    {
        this.cjtValoracions = cjtValoracions;
    }

    /**
     * Retorna el conjunt de valoracions
     * @return conjunt de valoracions
     */
    public Map<Integer, Float> getCjtValoracions()
    {
        return cjtValoracions;
    }

    /**
     * Retorna la puntuació de la valoració de l'item indicat
     * @param id identificador de l'item
     * @return puntuació de l'item
     */
    public float getVal(int id)
    {
        return cjtValoracions.get(id);
    }

    /**
     * Afegeix la valoració al conjunt de valoracions
     * @param id identificador de l'item
     * @param puntuacio puntuació de l'item
     */
    public void afegirValoracio(int id, float puntuacio)
    {
        cjtValoracions.put(id, puntuacio);
    }

    /**
     * Elimina la valoració del conjunt de valoracions
     * @param id identificador de l'item
     */
    public void eliminarValoracio(int id)
    {
        cjtValoracions.remove(id);
    }

    /**
     * Retorna si existeix la valoració de l'item indicat al conjunt de valoracions
     * @param id identificador de l'item
     * @return true si existeix una valoració de l'item al conjunt de valoracions
     */
    public boolean existeixValoracio(int id)
    {
        return cjtValoracions.containsKey(id);
    }

    /**
     * Número total de valoracions del conjunt de valoracions
     * @return tamany del conjunt de valoracions
     */
    public int size()
    {
        return cjtValoracions.size();
    }
}