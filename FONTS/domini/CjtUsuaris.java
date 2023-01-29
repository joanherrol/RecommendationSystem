package domini;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

/**
 * Classe contenidor per poder emmagatzemar els usuaris
 */
public class CjtUsuaris
{
    private Map<Integer, Usuari> cjtUsuaris;

    public CjtUsuaris()
    {
        cjtUsuaris = new HashMap<>();
    }

    /**
     * processa unes strings provinents del Controlador de Domini, el qual les ha obtingut del Controlador de Data, per transformar-les en un cjtUsuaris
     * @param usuaris vector d'string que conté els usuaris
     */
    public void readFitxerUsuaris(Vector<String> usuaris)
    {
        for (int i = 1; i < usuaris.size(); i++)
        {
            String[] fields = usuaris.get(i).split(",");
            if (fields.length > 0)
            {
                Usuari user = new Usuari(Integer.parseInt(fields[0]), //id
                        fields[1],                                    //nom
                        Integer.parseInt(fields[2]),                  //edat
                        fields[3],                                    //email
                        fields[4]);                                   //password
                cjtUsuaris.put(user.getId(), user);
            }
        }
    }

    /**
     * processa unes strings provinents del Controlador de Domini, el qual les ha obtingut del Controlador de Data, per transformar-les en un cjtUsuarisTester
     * @param testers vector d'strings que conté els usuaris de tester
     */
    public void readFitxerUsuarisTester(Vector<String> testers) throws NumberFormatException
    {
        int userColumn = 0;
        int itemColumn = 0;
        int valColumn = 0;

        for (int i = 0; i < testers.size(); i++)
        {
            String[] fields = testers.get(i).split(",");
            if (fields.length > 0) {
                if (i == 0)
                { //capçalera del fitxer
                    if ("userId".equals(fields[1]))
                        userColumn = 1;
                    else if ("userId".equals(fields[2]))
                        userColumn = 2;
                    if ("itemId".equals(fields[1]))
                        itemColumn = 1;
                    else if ("itemId".equals(fields[2]))
                        itemColumn = 2;
                    if ("rating".equals(fields[1]))
                        valColumn = 1;
                    else if ("rating".equals(fields[2]))
                        valColumn = 2;
                }
                else
                {
                    int idU = Integer.parseInt(fields[userColumn]);
                    int idI = Integer.parseInt(fields[itemColumn]);
                    float val = Float.parseFloat(fields[valColumn]);
                    if (cjtUsuaris.containsKey(idU)) {
                        cjtUsuaris.get(idU).getCjtValoracions().afegirValoracio(idI, val);
                    }
                    else {
                        Usuari tester = new Usuari(idU,                                  //id
                                "Tester",                                           //nom
                                20,                                                 //edat
                                "tester@tester.com",                               //email
                                "tester");                                      //password
                        tester.setCjtValoracions(new CjtValoracions());
                        cjtUsuaris.put(tester.getId(), tester);
                    }
                }
            }
        }
    }

    /**
     * transforma el cjtUsuarisTester en strings per passar-lo al Controlador de Domini, i aquest pugui passar-li al Controlador de Data
     * @return cjtUsuaris ja en Strings
     */
    public Vector<String> writeFitxerUsuaris()
    {
        Vector<String> dades = new Vector<>();

        String header = "Id,Nom,Edat,Email,Password";
        dades.add(header);

        for (Map.Entry<Integer, Usuari> usuari : cjtUsuaris.entrySet())
        {
            String id = String.valueOf(usuari.getKey());
            String nom = usuari.getValue().getNom();
            String edat = String.valueOf(usuari.getValue().getEdat());
            String email = usuari.getValue().getEmail();
            String password = usuari.getValue().getPassword();
            String dadesUsuari = id+","+nom+","+edat+","+email+","+password;
            dades.add(dadesUsuari);
        }
        return dades;
    }

    /**
     * transforma el cjtUsuarisTester en strings per passar-lo al Controlador de Domini, i aquest pugui passar-li al Controlador de Data
     * @return cjtUsuarisTester ja en Strings
     */
    public Vector<String> writeFitxerUsuarisTester()
    {
        Vector<String> dades = new Vector<>();

        String header = "Id,Nom,Edat,Email,Password";
        dades.add(header);

        for (Map.Entry<Integer, Usuari> usuari : cjtUsuaris.entrySet())
        {
            String id = String.valueOf(usuari.getKey());
            String nom = usuari.getValue().getNom();
            String edat = String.valueOf(usuari.getValue().getEdat());
            String email = usuari.getValue().getEmail();
            String password = usuari.getValue().getPassword();
            String dadesUsuari = id+","+nom+","+edat+","+email+","+password;
            dades.add(dadesUsuari);
        }
        return dades;
    }

    /**
     * afegeix un usuari al conjunt
     * @param u usuari a afegir
     */
    public void afegirUsuari(Usuari u)
    {
        cjtUsuaris.put(u.getId() , u);
    }

    /**
     * retorna true si l’usuari identificat per id està registrat
     * @param id identificador de l'usuari a comprovar
     * @return true si l’usuari identificat per id està registrat, false en cas contrari
     */
    public boolean existeixUsuari(int id)
    {
        return cjtUsuaris.containsKey(id);
    }

    /**
     * retorna l’usuari amb l’id indicat
     * @param id identificador de l'usuari a retornar
     * @return usuari amb l’id indicat
     */
    public Usuari getUsuari(int id)
    {
        return cjtUsuaris.get(id);
    }

    /**
     * retorna el tamany del conjunt d’usuaris
     * @return el tamany del conjunt d’usuaris
     */
    public int size()
    {
        return cjtUsuaris.size();
    }
}