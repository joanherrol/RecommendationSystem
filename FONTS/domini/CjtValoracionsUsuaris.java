package domini;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

/**
 * Classe contenidor per poder emmagatzemar les valoracions dels usuaris
 */
public class CjtValoracionsUsuaris
{
    private Map<Integer, CjtValoracions> cjtValoracionsUsuaris;

    /**
     * Constructora per defecte
     */
    public CjtValoracionsUsuaris()
    {
        this.cjtValoracionsUsuaris = new HashMap<>();
    }

    /**
     * Constructora
     * @param cjtValoracionsUsuaris conjunt de valoracions dels usuaris
     */
    public CjtValoracionsUsuaris(Map<Integer, CjtValoracions> cjtValoracionsUsuaris)
    {
        this.cjtValoracionsUsuaris = cjtValoracionsUsuaris;
    }

    /**
     * Seteja el conjunt de valoracions dels usuaris
     * @param cjtValoracionsUsuaris conjunt de valoracions dels usuaris
     */
    public void setCjtValoracionsUsuaris(Map<Integer, CjtValoracions> cjtValoracionsUsuaris)
    {
        this.cjtValoracionsUsuaris = cjtValoracionsUsuaris;
    }

    /**
     * Retorna el conjunt de valoracions dels usuaris
     * @return conjunt de valoracions dels usuaris
     */
    public Map<Integer, CjtValoracions> getCjtValoracionsUsuaris()
    {
        return cjtValoracionsUsuaris;
    }

    /**
     * Retorna el conjunt de valoracions de l'usuari indicat
     * @param userId identificador de l'usuari
     * @return conjunt de valoracions de l'usuari
     */
    public CjtValoracions getCjtValoracions(int userId)
    {
        return cjtValoracionsUsuaris.get(userId);
    }

    /**
     * Funció que tracta la informació llegida del fitxer de valoracions i inicialitza el conjunt de valoracions dels usuaris
     * @param valoracions vector de strings que representa la informació llegida del fitxer de valoracions
     * @param llistaItems set de strings amb tots els identificadors dels items del dataset carregat
     */
    public void readFitxerValoracions(Vector<String> valoracions, Set<Integer> llistaItems)
    {
        int u = 0, i = 0, r = 0;
        for (int j = 0; j < valoracions.size(); j++)
        {
            String[] fields = valoracions.get(j).split(",");
            if (fields.length > 0)
            {
                if (j == 0)
                {  // capçalera fitxer
                    switch (fields[1])
                    {
                        case "userId":
                            u = 1;
                            break;
                        case "itemId":
                            i = 1;
                            break;
                        case "rating":
                            r = 1;
                            break;
                    }
                    switch (fields[2])
                    {
                        case "userId":
                            u = 2;
                            break;
                        case "itemId":
                            i = 2;
                            break;
                        case "rating":
                            r = 2;
                            break;
                    }
                }
                else
                {
                    int itemId = Integer.parseInt(fields[i]);
                    if (llistaItems.contains(itemId))
                    {
                        int userId = Integer.parseInt(fields[u]);
                        float puntuacio = Float.parseFloat(fields[r]);
                        afegirValoracions(userId, itemId, puntuacio);
                    }
                }
            }
        }
    }

    /**
     * Funció que tracta el conjunt de valoracions dels usuaris per poder realitzar l'escriptura del fitxer de valoracions
     * @return vector de strings amb la informació del conjunt de valoracions dels usuaris
     */
    public Vector<String> writeFitxerValoracions()
    {
        Vector<String> dades = new Vector<>();
        String header = "userId, itemId, rating";
        dades.add(header);

        for (Map.Entry<Integer, CjtValoracions> cjtVU : cjtValoracionsUsuaris.entrySet())
        {
            for (Map.Entry<Integer, Float> cjtV : cjtVU.getValue().getCjtValoracions().entrySet())
            {
                String userId = String.valueOf(cjtVU.getKey());
                String itemId = String.valueOf(cjtV.getKey());
                String rating = String.valueOf(cjtV.getValue());
                String dadesValoracions = userId+","+itemId+","+rating;
                dades.add(dadesValoracions);
            }
        }
        return dades;
    }

    /**
     * Afegeix al conjunt de valoracions dels usuaris el conjunt de valoracions indicat a l'usuari indicat
     * @param userId identificador de l'usuari
     * @param cjtValoracions conjunt de valoracions
     */
    public void afegirCjtValoracions(int userId, CjtValoracions cjtValoracions)
    {
        cjtValoracionsUsuaris.put(userId, cjtValoracions);
    }

    /**
     * Afegeix al conjunt de valoracions de l'usuari indicat la valoració amb els paràmetres indicats
     * @param userId identificador de l'usuari
     * @param itemId identificador de l'item
     * @param puntuacio puntuació de l'item
     */
    public void afegirValoracions(int userId, int itemId, float puntuacio)
    {
        if (existeixCjtValoracions(userId))
        {
            cjtValoracionsUsuaris.get(userId).afegirValoracio(itemId, puntuacio);
        }
        else
        {
            CjtValoracions cjtValoracions = new CjtValoracions();
            cjtValoracions.afegirValoracio(itemId, puntuacio);
            afegirCjtValoracions(userId, cjtValoracions);
        }
    }

    /**
     * Elimina del conjunt de valoracions de cada usuari la valoració de l'item indicat
     * @param itemId identificador de l'item
     */
    public void eliminarValoracio(int itemId)
    {
        Vector<Integer> userIdContainsItem = new Vector<>();
        for (int id : cjtValoracionsUsuaris.keySet())
        {
            for (int idI : cjtValoracionsUsuaris.get(id).getCjtValoracions().keySet())
            {
                if (idI == itemId)
                {
                    userIdContainsItem.add(id);
                }
            }
        }

        for (int idU : userIdContainsItem)
        {
            cjtValoracionsUsuaris.get(idU).eliminarValoracio(itemId);
        }
    }

    /**
     * Retorna si l'usuari indicat té algun conjunt de valoracions al conjunt de valoracions dels usuaris
     * @param userId identificador de l'usuari
     * @return true si l'usuari té algun conjunt de valoracions al conjunt de valoracions dels usuaris i false en cas contrari
     */
    public boolean existeixCjtValoracions(int userId)
    {
        return cjtValoracionsUsuaris.containsKey(userId);
    }

    /**
     * Número total d'usuaris amb conjunt de valoracions
     * @return tamany del conjunt de valoracions dels usuaris
     */
    public int size()
    {
        return cjtValoracionsUsuaris.size();
    }
}