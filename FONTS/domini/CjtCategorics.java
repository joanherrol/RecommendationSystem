package domini;

import java.util.HashMap;
import java.util.Map;

/**
 * Classe contenidor per poder emmagatzemar els categorics
 */
public class CjtCategorics
{
    private Map<String, Float> cjtCategorics;

    /**
     * Constructura per defecte
     */
    public CjtCategorics()
    {
        this.cjtCategorics = new HashMap<>();
    }

    /**
     * Seteja el conjunt de categorics
     * @param cjtCategorics conjunt de categorics
     */
    public void setCategorics(Map<String, Float> cjtCategorics)
    {
        this.cjtCategorics = cjtCategorics;
    }

    /**
     * Retorna el conjunt de categorics
     * @return conjunt de categorics
     */
    public Map<String, Float> getCategorics()
    {
        return cjtCategorics;
    }

    /**
     * Afegeix un categòric al conjunt de categorics
     * @param nomAtribut nom de l'atribut categòric
     * @param ponderacio ponderació de l'atribut categòric
     */
    public void afegirCategoric(String nomAtribut, float ponderacio)
    {
        cjtCategorics.put(nomAtribut, ponderacio);
    }

    /**
     * Retorna si un atribut és categòric
     * @param nomAtribut nom de l'atribut
     * @return true si l'atribut indicat és categòric i false en cas contrari
     */
    public boolean esCategoric(String nomAtribut)
    {
        return cjtCategorics.containsKey(nomAtribut);
    }

    /**
     * Número total d'atributs categòrics
     * @return tamany del conjunt de categorics
     */
    public int size()
    {
        return cjtCategorics.size();
    }
}