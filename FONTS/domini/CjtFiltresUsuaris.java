package domini;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

/**
 * Classe contenidor per poder emmagatzemar els conjunts de filtres de cada usuari
 */
public class CjtFiltresUsuaris
{
    private Map<Integer, CjtFiltres> cjtFiltresUsuaris;

    /**
     * Constructora per defecte
     */
    public CjtFiltresUsuaris()
    {
        this.cjtFiltresUsuaris = new HashMap<>();
    }

    /**
     * Retorna el conjunt de filtres de l'usuari amb l'identificador indicat
     * @param userId identificador de l'usuari
     * @return el conjunt de filtres de l'usuari
     */
    public CjtFiltres getCjtFiltres(int userId)
    {
        return cjtFiltresUsuaris.get(userId);
    }

    /**
     * Funció que tracta la informació llegida del fitxer de filtres i inicialitza el conjunt de filtres de cada usuari
     * i el conjunt de filtres dels usuaris
     * @param ctrlDomini instància del controlador de domini
     * @param filtres vector de strings que representa la informació llegida del fitxer de filtres
     */
    public void readFitxerFiltres(CtrlDomini ctrlDomini, Vector<String> filtres)
    {
        int u = 0, ncjtF = 0, fil = 0;
        for (int j = 0; j < filtres.size(); ++j)
        {
            String[] fields = filtres.get(j).split(",");
            if (fields.length > 0)
            {
                if (j == 0)
                { // capçalera fitxer
                    switch (fields[1])
                    {
                        case "userId":
                            u = 1;
                            break;
                        case "nomCjtFiltres":
                            ncjtF = 1;
                            break;
                        case "Filtres":
                            fil = 1;
                            break;
                    }
                    switch (fields[2])
                    {
                        case "userId":
                            u = 2;
                            break;
                        case "nomCjtFiltres":
                            ncjtF = 2;
                            break;
                        case "Filtres":
                            fil = 2;
                            break;
                    }
                }
                else
                {
                    String primerAtrib = "";
                    Map<String, Vector<String>> filtre = new HashMap<>();
                    int userId = Integer.parseInt(fields[u]);
                    String nomCjtF = fields[ncjtF];
                    String cjtf = fields[fil];
                    String[] filtresU = cjtf.split("\\|");
                    String[] atribut;
                    String[] valorsA;
                    for (String fU : filtresU)
                    {
                        atribut = fU.split(":");
                        int p = 1;
                        for (String a : atribut)
                        {
                            if (p == 1)
                            {
                                primerAtrib = a;
                                p = 2;
                            }
                            else
                            {
                                valorsA = a.split(";");
                                Vector<String> valorsAtributs = new Vector<>(Arrays.asList(valorsA));
                                filtre.put(primerAtrib, valorsAtributs);
                                p = 1;
                            }
                        }
                    }
                    Filtre f = new Filtre(nomCjtF, filtre);
                    Usuari usuari = ctrlDomini.getUsuari(userId);
                    CjtFiltres cjtFiltres = usuari.getCjtFiltres();
                    cjtFiltres.afegirFiltre(f);
                    cjtFiltresUsuaris.put(userId, cjtFiltres);
                }
            }
        }
    }

    /**
     * Funció que tracta el conjunt de filtres dels usuaris per poder realitzar l'escriptura del fitxer de filtres
     * @return vector de strings amb la informació del conjunt de filtres dels usuaris
     */
    public Vector<String> writeFitxerFiltres()
    {
        Vector<String> dades = new Vector<>();
        String header = "userId,nomCjtFiltres,Filtres";
        dades.add(header);

        for (Map.Entry<Integer, CjtFiltres> cjtFU: cjtFiltresUsuaris.entrySet())
        {
            String userId = String.valueOf(cjtFU.getKey());
            for (Map.Entry<String, Filtre> cjtF: cjtFU.getValue().getCjtFiltres().entrySet())
            {
                StringBuilder dadesFiltres = new StringBuilder(userId + ",");
                String nomCjtFiltres = cjtF.getKey();
                dadesFiltres.append(nomCjtFiltres).append(",");
                for (Map.Entry<String, Vector<String>> f: cjtF.getValue().getFiltres().entrySet())
                {
                    String nomF = f.getKey();
                    dadesFiltres.append(nomF).append(":");
                    boolean primerFiltre = true;
                    for (String v: f.getValue())
                    {
                        if (!primerFiltre) dadesFiltres.append(";").append(v);
                        else dadesFiltres.append(v);
                        primerFiltre = false;
                    }
                    dadesFiltres.append("|");
                }
                dades.add(dadesFiltres.toString());
            }
        }
        return dades;
    }

    /**
     * Afegeix al conjunt de filtres dels usuaris el conjunt de filtres indicat a l'usuari indicat
     * @param userId identificador de l'usuari
     * @param cjtFiltres conjunt de filtres
     */
    public void afegirCjtFiltres(int userId, CjtFiltres cjtFiltres)
    {
        cjtFiltresUsuaris.put(userId, cjtFiltres);
    }

    /**
     * Afegeix el filtre indicat al conjunt de filtres de l'usuari indicat
     * @param userId identificador de l'usuari
     * @param f filtre
     * @param cjtF conjunt de filtres de l'usuari
     */
    public void afegirFiltre(int userId, Filtre f, CjtFiltres cjtF)
    {
        cjtF.afegirFiltre(f);

        if (!existeixCjtFiltres(userId))
            afegirCjtFiltres(userId, cjtF);
    }

    /**
     * Elimina el filtre indicat del conjunt de filtres de l'usuari indicat
     * @param userId identificador de l'usuari
     * @param nomFiltre nom del filtre
     */
    public void eliminarFiltre(int userId, String nomFiltre)
    {
        if (existeixFiltre(userId, nomFiltre))
        {
            cjtFiltresUsuaris.get(userId).eliminarFiltre(nomFiltre);
        }
    }

    /**
     * Retorna si l'usuari indicat té algun conjunt de filtres al conjunt de filtres dels usuaris
     * @param userId identificador de l'usuari
     * @return true si l'usuari té algun conjunt de filtres al conjunt de filtres dels usuaris i false en cas contrari
     */
    public boolean existeixCjtFiltres(int userId)
    {
        return cjtFiltresUsuaris.containsKey(userId);
    }

    /**
     * Retorna si l'usuari té el nom del filtre indicat al seu conjunt de filtres
     * @param userId identificador de l'usuari
     * @param nomFiltre nom del filtre
     * @return true si l'usuari té el filtre al seu conjunt de filtres i false en cas contrari
     */
    public boolean existeixFiltre(int userId, String nomFiltre)
    {
        return existeixCjtFiltres(userId) && cjtFiltresUsuaris.get(userId).existeixFiltre(nomFiltre);
    }

    /**
     * Número total d'usuaris amb conjunt de filtres
     * @return tamany del conjunt de filtres dels usuaris
     */
    public int size()
    {
        return cjtFiltresUsuaris.size();
    }
}