package domini;

import java.util.*;

/**
 * Classe contenidor per poder emmagatzemar la informació d'un filtre
 */
public class Filtre
{
    private String nomFiltre;
    private Map<String, Vector<String>> filtre;

    /**
     * Constructora per defecte
     */
    public Filtre()
    {
        this.nomFiltre = "";
        this.filtre = new HashMap<>();
    }

    /**
     * Constructora
     * @param nomFiltre nom del filtre
     */
    public Filtre(String nomFiltre)
    {
        this.nomFiltre = nomFiltre;
        this.filtre = new HashMap<>();
    }

    /**
     * Constructora
     * @param nomFiltre nom del filtre
     * @param filtres valors del filtre
     */
    public Filtre(String nomFiltre, Map<String, Vector<String>> filtres)
    {
        this.nomFiltre = nomFiltre;
        this.filtre = filtres;
    }

    /**
     * Retorna el nom del filtre
     * @return nom del filtre
     */
    public String getNomFiltre()
    {
        return nomFiltre;
    }

    /**
     * Seteja el nom del filtre
     * @param nomFiltre nom del filtre
     */
    public void setNomFiltre(String nomFiltre)
    {
        this.nomFiltre = nomFiltre;
    }

    /**
     * Retorna els valors del filtre
     * @return filtre
     */
    public Map<String, Vector<String>> getFiltres()
    {
        return filtre;
    }

    /**
     * Seteja els valors del filtre
     * @param filtres filtre
     */
    public void setFiltres(Map<String, Vector<String>> filtres)
    {
        this.filtre = filtres;
    }

    /**
     * Afegeix un nom d'atribut amb el seu valor als valors del filtre
     * @param nomAtribut nom d'atribut
     * @param valorAtributs valor de l'atribut
     */
    public void afegirAtribut(String nomAtribut, Vector<String> valorAtributs)
    {
        filtre.put(nomAtribut, valorAtributs);
    }

    /**
     * Retorna si existeix el nom d'atribut als valors del filtre
     * @param nomAtribut nom d'atribut
     * @return true si existeix el nom d'atribut als valors del filtre i false en cas contrari
     */
    public boolean existeixAtribut(String nomAtribut)
    {
        return filtre.containsKey(nomAtribut);
    }

    /**
     * Retorna si existeix el nom d'atribut i el valor als valors del filtre
     * @param nomAtribut nom d'atribut
     * @param valorAtribut valor de l'atribut
     * @return true si existeix el nom d'atribut i el valor als valors del filtre i false en cas contrari
     */
    public boolean existeixNomValorAtribut(String nomAtribut, Object valorAtribut)
    {
        if (existeixAtribut(nomAtribut))
        {
            int count = 0;

            for (String valorAtributF : filtre.get(nomAtribut))
            {
                if (valorAtribut.getClass().getSimpleName().equals("ArrayList"))
                {
                    ArrayList<String> valorA = (ArrayList) valorAtribut;
                    for (String a : valorA)
                    {
                        if (Objects.equals(a, valorAtributF))
                        {
                            count += 1;

                            if (Objects.equals(count, filtre.get(nomAtribut).size()))
                                return true;
                        }
                    }
                }
                else
                {
                    String[] valorA = valorAtribut.toString().split(";");
                    for (String s : valorA)
                    {
                        if (Objects.equals(s, valorAtributF))
                        {
                            count += 1;

                            if (Objects.equals(count, filtre.get(nomAtribut).size()))
                                return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * Número total de valors del filtre
     * @return tamany del filtre
     */
    public int size()
    {
        return filtre.size();
    }
}