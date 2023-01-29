package domini;

import java.util.*;

/**
 * Classe contenidor per poder emmagatzemar els filtres
 */
public class CjtFiltres
{
    private Map<String, Filtre> cjtFiltres;

    /**
     * Constructora per defecte
     */
    public CjtFiltres()
    {
        this.cjtFiltres = new HashMap<>();
    }

    /**
     * Retorna el conjunt de filtres
     * @return conjunt de filtres
     */
    public Map<String, Filtre> getCjtFiltres()
    {
        return cjtFiltres;
    }

    /**
     * Afegeix el filtre indicat al conjunt de filtres
     * @param f filtre
     */
    public void afegirFiltre(Filtre f)
    {
        cjtFiltres.put(f.getNomFiltre(), f);
    }

    /**
     * Elimina el filtre identificat pel nom indicat del conjunt de filtres
     * @param nomFiltre nom del filtre
     */
    public void eliminarFiltre(String nomFiltre)
    {
        cjtFiltres.remove(nomFiltre);
    }

    /**
     * Retorna si existeix el filtre identificat pel nom indicat al conjunt de filtres
     * @param nomFiltre nom del filtre
     * @return true si el filtre existeix al conjunt de filtres i false en cas contrari
     */
    public boolean existeixFiltre(String nomFiltre)
    {
        return cjtFiltres.containsKey(nomFiltre);
    }

    /**
     * Retorna un vector de strings amb els noms dels filtres del conjunt de filtres
     * @return vector de strings amb els noms dels filtres
     */
    public Vector<String> getNomsFiltres()
    {
        Vector<String> llistaNomsFiltres = new Vector<>();
        for (Map.Entry<String, Filtre> cjtF : cjtFiltres.entrySet())
        {
            llistaNomsFiltres.add(cjtF.getKey());
        }
        Collections.sort(llistaNomsFiltres);
        return llistaNomsFiltres;
    }

    /** Número total de filtres del conjunt de filtres
     * @return tamany del conjunt de filtres
     */
    public int size()
    {
        return cjtFiltres.size();
    }
}