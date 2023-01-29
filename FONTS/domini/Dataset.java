package domini;

import java.util.*;

/**
 * Classe contenidor per poder emmagatzemar els items d'un dataset
 */
public class Dataset
{
    private CtrlDomini controladorDomini;

    private String[] header;
    private Vector<String> tipusAtributs;
    private Map<Integer, Item> cjtItems;
    private CjtCategorics cjtCategorics;
    private Map<Integer, Item> cjtItemsFiltrat;

    /**
     * Constructora per defecte
     */
    public Dataset()
    {
        this.header = new String[0];
        this.tipusAtributs = new Vector<>();
        this.cjtItems = new LinkedHashMap<>();
        this.cjtCategorics = new CjtCategorics();
        this.cjtItemsFiltrat = new LinkedHashMap<>();
    }

    /**
     * Constructora
     * @param header capçalera del dataset
     * @param cjtItems conjunt d'items del dataset
     * @param cjtCategorics atributs categòrics del dataset
     */
    public Dataset(String[] header, Map<Integer, Item> cjtItems, CjtCategorics cjtCategorics)
    {
        this.header = header;
        this.cjtItems = cjtItems;
        this.cjtCategorics = cjtCategorics;
    }

    /**
     * Seteja la capçalera del dataset
     * @param header capçalera del dataset
     */
    public void setHeader(String[] header)
    {
        this.header = header;
    }

    /**
     * Seteja els atributs categòrics del dataset
     * @param cjtCategorics conjunt de categorics del dataset
     */
    public void setCategorics(CjtCategorics cjtCategorics)
    {
        this.cjtCategorics = cjtCategorics;
    }

    /**
     * Retorna la capçalera del dataset
     * @return capçalera del dataset
     */
    public String[] getHeader()
    {
        return header;
    }

    /**
     * Retorna el conjunt d'items del dataset
     * @return conjunt d'items del dataset
     */
    public Map<Integer, Item> getCjtItems()
    {
        return cjtItems;
    }

    /**
     * Retorna els atributs categòrics del dataset
     * @return conjunt de categorics del dataset
     */
    public CjtCategorics getCategorics()
    {
        return cjtCategorics;
    }

    /**
     * Retorna el conjunt d'items filtrat del dataset
     * @return conjunt d'items filtrat del dataset
     */
    public Map<Integer, Item> getCjtItemsFiltrat()
    {
        return cjtItemsFiltrat;
    }

    private void tractarCapcalera(String capcaleraFitxer)
    {
        String[] header = capcaleraFitxer.split(",");
        if (header.length > 0)
            this.header = header;
    }

    private void afegirIntegerAlCjtAtributs(Map<String, Object> cjtAtributs, String nomAtribut, String valorAtribut) throws NumberFormatException
    {
        int i = Integer.parseInt(valorAtribut);
        cjtAtributs.put(nomAtribut, i);
    }

    private void afegirDoubleAlCjtAtributs(Map<String, Object> cjtAtributs, String nomAtribut, String valorAtribut) throws NumberFormatException
    {
        double d = Double.parseDouble(valorAtribut);
        cjtAtributs.put(nomAtribut, d);
    }

    private void afegirFloatAlCjtAtributs(Map<String, Object> cjtAtributs, String nomAtribut, String valorAtribut) throws NumberFormatException
    {
        float f = Float.parseFloat(valorAtribut);
        cjtAtributs.put(nomAtribut, f);
    }

    private void afegirBooleanAlCjtAtributs(Map<String, Object> cjtAtributs, String nomAtribut, String valorAtribut) throws NumberFormatException
    {
        boolean b = Boolean.parseBoolean(valorAtribut);
        cjtAtributs.put(nomAtribut, b);
    }

    private void afegirLongAlCjtAtributs(Map<String, Object> cjtAtributs, String nomAtribut, String valorAtribut) throws NumberFormatException
    {
        long l = Long.parseLong(valorAtribut);
        cjtAtributs.put(nomAtribut, l);
    }

    private void afegirArrayAlCjtAtributs(Map<String, Object> cjtAtributs, String nomAtribut, String valorAtribut)
    {
        //separem els valors de la casella, si tenen ;
        String[] f = valorAtribut.split(";");
        ArrayList<String> lCategoric = new ArrayList<>(Arrays.asList(f));
        cjtAtributs.put(nomAtribut, lCategoric);
    }

    private void afegirStringAlCjtAtributs(Map<String, Object> cjtAtributs, String nomAtribut, String valorAtribut)
    {
        cjtAtributs.put(nomAtribut, valorAtribut);
    }

    private boolean processarCategorics(Map<String, Object> cjtAtributs, String nomAtribut, String valorAtribut, Vector<String> tipusAtributs, int j)
    {
        String answer = controladorDomini.demanarResposta("Carregar Dataset","L'atribut " + nomAtribut + " és Categoric?");
        if (answer.equals("Yes"))
        { //es categoric
            tipusAtributs.add(j, "categoric");
            float ponderacio = controladorDomini.demanarPonderacio("Carregar Dataset", "Introdueix la ponderacio de l'atribut " + nomAtribut + ":");
            cjtCategorics.afegirCategoric(nomAtribut, ponderacio);

            afegirArrayAlCjtAtributs(cjtAtributs, nomAtribut, valorAtribut);
            return true;
        }
        return false;
    }

    private void processarDades(Map<String, Object> cjtAtributs, String nomAtribut, String valorAtribut, Vector<String> tipusAtributs, int j) throws NumberFormatException
    {
        if (ProcessatDades.isNumeric(valorAtribut))
        {
            afegirIntegerAlCjtAtributs(cjtAtributs, nomAtribut, valorAtribut);
            tipusAtributs.add(j, "int");
        }
        else if (ProcessatDades.isDouble(valorAtribut))
        {
            afegirDoubleAlCjtAtributs(cjtAtributs, nomAtribut, valorAtribut);
            tipusAtributs.add(j, "double");
        }
        else if (ProcessatDades.isFloat(valorAtribut))
        {
            afegirFloatAlCjtAtributs(cjtAtributs, nomAtribut, valorAtribut);
            tipusAtributs.add(j, "float");

        }
        else if (ProcessatDades.isBoolean(valorAtribut))
        {
            afegirBooleanAlCjtAtributs(cjtAtributs, nomAtribut, valorAtribut);
            tipusAtributs.add(j, "bool");
        }
        else if (ProcessatDades.isLong(valorAtribut))
        {
            afegirLongAlCjtAtributs(cjtAtributs, nomAtribut, valorAtribut);
            tipusAtributs.add(j, "long");
        }
        else
        {
            if (!processarCategorics(cjtAtributs, nomAtribut, valorAtribut, tipusAtributs, j))
            { //es string
                tipusAtributs.add(j, "string");
                afegirStringAlCjtAtributs(cjtAtributs, nomAtribut, valorAtribut);
            }
        }
    }

    private void controlColumnesDataset(Map<String, Object> cjtAtributs, int nColsDataset)
    {
        if (nColsDataset < header.length)
        {
            for (String AtributHeader : header)
            {
                if (!cjtAtributs.containsKey(AtributHeader))
                {
                    cjtAtributs.put(AtributHeader, "");
                }
            }
        }
    }

    private void tractarPrimeraLinia(String primeraLiniaFitxer, Vector<String> tipusAtributs) throws NumberFormatException
    {
        //llegim la primera linia del fitxer per guardar el tipus d'atribut de cada atribut en el vector tipusAtributs
        String[] fields = primeraLiniaFitxer.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
        int id = 0;
        String nomAtribut;
        Map<String, Object> cjtAtributs = new LinkedHashMap<>(); //cjtAtributs d'un Item

        if (fields.length > 0)
        {
            for (int j = 0; j < fields.length; j++)
            {
                nomAtribut = header[j];
                if (nomAtribut.equals("id"))
                    id = Integer.parseInt(fields[j]);

                processarDades(cjtAtributs, nomAtribut, fields[j], tipusAtributs, j);
                controlColumnesDataset(cjtAtributs, fields.length);
            }
        }
        Item item = new Item(id, cjtAtributs);
        cjtItems.put(id, item);
    }

    private boolean tractarLinia(String liniaFitxer, Vector<String> tipusAtributs) throws NumberFormatException, ArrayIndexOutOfBoundsException
    {
        int id = 0;
        String nomAtribut;
        String[] fields = liniaFitxer.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
        Map<String, Object> cjtAtributs = new LinkedHashMap<>();

        try
        {
            if (fields.length > 0) {
                for (int j = 0; j < fields.length; j++)
                {
                    nomAtribut = header[j];

                    if (nomAtribut.equals("id"))
                        id = Integer.parseInt(fields[j]);

                    if (Objects.equals(tipusAtributs.get(j), "string"))
                        afegirStringAlCjtAtributs(cjtAtributs, nomAtribut, fields[j]);
                    else if (Objects.equals(tipusAtributs.get(j), "categoric"))
                        afegirArrayAlCjtAtributs(cjtAtributs, nomAtribut, fields[j]);
                    else if (Objects.equals(tipusAtributs.get(j), "bool"))
                        afegirBooleanAlCjtAtributs(cjtAtributs, nomAtribut, fields[j]);
                    else
                    {
                        if (Objects.equals(fields[j], ""))
                            cjtAtributs.put(nomAtribut, 0);
                        else
                        {
                            if (Objects.equals(tipusAtributs.get(j), "int"))
                                afegirIntegerAlCjtAtributs(cjtAtributs, nomAtribut, fields[j]);
                            else if (Objects.equals(tipusAtributs.get(j), "double"))
                                afegirDoubleAlCjtAtributs(cjtAtributs, nomAtribut, fields[j]);
                            else if (Objects.equals(tipusAtributs.get(j), "float"))
                                afegirFloatAlCjtAtributs(cjtAtributs, nomAtribut, fields[j]);
                            else if (Objects.equals(tipusAtributs.get(j), "long"))
                                afegirLongAlCjtAtributs(cjtAtributs, nomAtribut, fields[j]);
                        }
                    }
                }
            }
            controlColumnesDataset(cjtAtributs, fields.length);
            Item item = new Item(id, cjtAtributs);
            cjtItems.put(id, item);
        }
        catch (NumberFormatException e)
        {
            return false;
        }
        return true;
    }

    /**
     * Funció que tracta la informació llegida del fitxer del dataset i inicialitza el conjunt d'items del dataset
     * @param controladorDomini instància del controlador de domini
     * @param dades vector de strings que representa la informació llegida del fitxer del dataset
     * @throws NumberFormatException excepció que s'activa en cas que s'intenti convertir un string a un tipus d'atribut que no li correspon
     * @throws ArrayIndexOutOfBoundsException excepció que s'activa quan s'ha accedit a una matriu amb un índex il·legal
     */
    public void readFitxerDataset(CtrlDomini controladorDomini, Vector<String> dades) throws NumberFormatException, ArrayIndexOutOfBoundsException
    {
        this.controladorDomini = controladorDomini;
        header = new String[0];
        cjtItems = new LinkedHashMap<>();
        cjtCategorics = new CjtCategorics();
        tipusAtributs = new Vector<>();
        controladorDomini.setFiltrat(false);
        controladorDomini.setDatasetCarregat(false);
        try
        {
            if (dades.size() == 0) controladorDomini.mostraError("El fitxer carregat està buit");
            else if (dades.size() == 1) controladorDomini.mostraError("El fitxer carregat és invàlid");
            else
            {
                for (int i = 0; i < dades.size(); i++)
                {
                    if (i == 0)
                        tractarCapcalera(dades.get(i));
                    else if (i == 1)
                        tractarPrimeraLinia(dades.get(i), tipusAtributs);
                    else
                    {
                        tractarLinia(dades.get(i), tipusAtributs);
                        if (i == dades.size()-1) controladorDomini.setDatasetCarregat(true);
                    }
                }
            }
        }
        catch (NumberFormatException e)
        {
            controladorDomini.mostraError("Error al carregar el dataset");
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            controladorDomini.mostraError("El fitxer carregat és invàlid");
        }
    }

    /**
     * Funció que escriu la capçalera
     * @param dades vector de strings que representa la informació que es vol escriure
     */
    public void escriureCapcalera(Vector<String> dades)
    {
        boolean first = true;
        StringBuilder dadesHeader = new StringBuilder();

        for (String nomAtribut : header)
        {
            if (!first)
                dadesHeader.append(",");
            dadesHeader.append(nomAtribut);
            if (first)
                first = false;
        }
        dades.add(dadesHeader.toString());
    }

    private void escriureItems(Vector<String> dades)
    {
        boolean first;
        for(Map.Entry<Integer, Item> cjtI : cjtItems.entrySet())
        {
            StringBuilder dadesItem = new StringBuilder();
            first = true;
            for(Map.Entry<String, Object> cjtA : cjtI.getValue().getCjtAtributs().entrySet())
            {
                if (!first)
                    dadesItem.append(",");
                if (cjtA.getValue().getClass().getSimpleName().equals("ArrayList"))
                {
                    String joined = cjtA.getValue().toString().replace("[", "").replace(", ", ";").replace("]", "");
                    dadesItem.append(joined);
                }
                else
                    dadesItem.append(cjtA.getValue());
                if (first)
                    first = false;
            }
            dades.add(dadesItem.toString());
        }
    }

    /**
     * Funció que tracta el conjunt d'items del dataset per poder realitzar l'escriptura del fitxer del dataset
     * @return vector de strings amb la informació del conjunt d'items del dataset
     */
    public Vector<String> writeFitxerDataset()
    {
        Vector<String> dades = new Vector<>();
        escriureCapcalera(dades);
        escriureItems(dades);
        return dades;
    }

    /**
     * Funció per afegir un item al conjunt d'items del dataset
     * @param dadesItem vector de strings amb la informació de l'item
     * @return true si l'item s'afegeix correctament i false en cas contrari
     */
    public boolean afegirItem(String dadesItem)
    {
        return tractarLinia(dadesItem, tipusAtributs);
    }

    /**
     * Elimina l'item indicat del conjunt d'items del dataset
     * @param itemId identificador de l'item
     */
    public void eliminarItem(int itemId)
    {
        cjtItems.remove(itemId);
    }

    /**
     * Retorna si existeix l'item indicat al conjunt d'items del dataset
     * @param itemId identificador de l'item
     * @return true si l'item existeix al conjunt d'items del dataset i false en cas contrari
     */
    public boolean existeixItem(int itemId)
    {
        return cjtItems.containsKey(itemId);
    }

    /**
     * Número total d'items del conjunt d'items del dataset
     * @return tamany del conjunt d'items del dataset
     */
    public int size()
    {
        return cjtItems.size();
    }

    /**
     * Retorna tots els valors que conté l'atribut indicat al conjunt d'items del dataset
     * @param nomAtribut nom de l'atribut
     * @return set de strings amb tots els valors que té l'atribut al conjunt d'items del dataset
     */
    public Set<String> getValorsAtributs(String nomAtribut)
    {
        Set<String> llistaValorsAtributs = new TreeSet<>();
        for (Map.Entry<Integer, Item> cjtI : cjtItems.entrySet())
        {
            for (Map.Entry<String, Object> cjtA : cjtI.getValue().getCjtAtributs().entrySet())
            {
                if (cjtA.getKey().equals(nomAtribut) && !Objects.equals(cjtA.getValue(), ""))
                {
                    Object valorAtribut = cjtA.getValue();
                    switch (valorAtribut.getClass().getSimpleName())
                    {
                        case "Boolean":
                            llistaValorsAtributs.add("false");
                            llistaValorsAtributs.add("true");
                            return llistaValorsAtributs;
                        case "ArrayList":
                            String atributCategoric = valorAtribut.toString().replace("[", "").replace("]", "").replace(" ", "");
                            String[] categoric = atributCategoric.split(",");
                            for (String cat : categoric)
                            {
                                if (!Objects.equals(cat, ""))
                                    llistaValorsAtributs.add(cat);
                            }
                            break;
                        case "String":
                            String[] valorA = valorAtribut.toString().split(";");
                            Collections.addAll(llistaValorsAtributs, valorA);
                            break;
                        default:
                            llistaValorsAtributs.add(String.valueOf(valorAtribut));
                            break;
                    }
                }
            }
        }
        return llistaValorsAtributs;
    }

    /**
     * Retorna tots els items del conjunt d'items del dataset
     * @return matriu de strings que conté tots els items del conjunt d'items del dataset
     */
    public String[][] getAllItems()
    {
        String[][] llistaItems = new String[cjtItems.size()][header.length];
        int i = 0, j = 0;
        for (Map.Entry<Integer, Item> cjtI : cjtItems.entrySet())
        {
            for (Map.Entry<String, Object> cjtA : cjtI.getValue().getCjtAtributs().entrySet())
            {
                llistaItems[i][j] = String.valueOf(cjtA.getValue());
                j += 1;
            }
            i += 1;
            j = 0;
        }
        return llistaItems;
    }

    /**
     * Retorna tots els items del conjunt d'items filtrat del dataset
     * @return matriu de strings que conté tots els items del conjunt d'items filtrat del dataset
     */
    public String[][] getAllItemsFiltrat()
    {
        String[][] llistaItems = new String[cjtItemsFiltrat.size()][header.length];
        int i = 0, j = 0;
        for (Map.Entry<Integer, Item> cjtI : cjtItemsFiltrat.entrySet())
        {
            for (Map.Entry<String, Object> cjtA : cjtI.getValue().getCjtAtributs().entrySet())
            {
                llistaItems[i][j] = String.valueOf(cjtA.getValue());
                j += 1;
            }
            i += 1;
            j = 0;
        }
        return llistaItems;
    }

    /**
     * Funció que filtra el conjunt d'atributs del dataset a partir del filtre indicat
     * @param filtre filtre pque s'aplica al dataset
     */
    public void filtrarDataset(Filtre filtre)
    {
        cjtItemsFiltrat = new LinkedHashMap<>();
        for (Map.Entry<Integer, Item>  cjtI: cjtItems.entrySet())
        {
            int count = 0;
            for (Map.Entry<String, Object> cjtA : cjtI.getValue().getCjtAtributs().entrySet())
            {
                if (filtre.existeixNomValorAtribut(cjtA.getKey(), cjtA.getValue()))
                {
                    count += 1;
                }
            }
            if (count == filtre.size())
            {
                cjtItemsFiltrat.put(cjtI.getKey(), cjtI.getValue());
            }
        }
        controladorDomini.setFiltrat(true);
    }
}