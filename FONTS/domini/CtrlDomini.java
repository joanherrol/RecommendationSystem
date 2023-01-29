package domini;

import data.CtrlData;
import presentacio.CtrlPresentacio;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Implementació de la classe controlador de domini encarregada de la comunicació amb les altres capes i de dur a terme tota la lògica de l'aplicació.
 **/
public class CtrlDomini
{
    private static CtrlDomini singletonObject;

    private final CtrlPresentacio controladorPresentacio;

    private final CtrlData controladorData;

    private CjtUsuaris usuaris_registrats;
    private CjtUsuaris testers;
    private Usuari mainUser;
    private Dataset datasetActiu;
    private CjtValoracionsUsuaris fitxerValActiu;
    private CjtFiltresUsuaris fitxerFiltresActiu;
    private GestioRecomanacio gestioRecomanacio;
    private boolean filtrat = false;
    private boolean tester = false;
    private Vector<String> ultimaRecomanacio;


    /*
    public static CtrlDomini getInstance()
    {
        if (singletonObject == null)
            singletonObject = new CtrlDomini() {};
        return singletonObject;
    }
     */

    /**
     * seteja un boolea segons si la recomanació sera feta amb usuaris de testeig o no
     * @param t boolea que esta a true si l'aplicació esta a mode tester i false en cas contrari
     */
    public void setTester(boolean t)
    {
        this.tester = t;
    }

    /**
     * seteja un boolea segons si el dataset esta carregat o no
     * @param carregat boolea que esta a true si el dataset esta carregat i a false en cas contrari
     */
    public void setDatasetCarregat(boolean carregat)
    {
        controladorPresentacio.setDatasetCarregat(carregat);
    }

    /**
     * seteja un boolea segons si el dataset esta filtrat o no
     * @param filtrat boolea que esta a true si el dataset esta filtrat i a false en cas contrari
     */
    public void setFiltrat(boolean filtrat)
    {
        this.filtrat = filtrat;
    }

    /**
     * Constructora
     * @param controladorPresentacio controlador enllaçat amb el de domini
     * */
    public CtrlDomini(CtrlPresentacio controladorPresentacio)
    {
        this.controladorPresentacio = controladorPresentacio;
        controladorData = CtrlData.getInstance();
        inicialitzarCtrlDomini();
    }

    /**
     * Funció que inicialitza la classe
     */
    public void inicialitzarCtrlDomini()
    {
        usuaris_registrats = new CjtUsuaris();
        testers = new CjtUsuaris();
        mainUser = new Usuari();
        datasetActiu = new Dataset();
        fitxerValActiu = new CjtValoracionsUsuaris();
        fitxerFiltresActiu = new CjtFiltresUsuaris();
        gestioRecomanacio = new GestioRecomanacio();
    }

    /**
     * Funció que parla amb el controlador presentació i demana un diàleg amb l'usuari
     * @param titol títol del diàleg
     * @param missatge missatge del diàleg
     * @return retorna la resposta del controlador presentació
     */
    public String demanarResposta(String titol, String missatge)
    {
        return controladorPresentacio.demanarResposta(titol, missatge);
    }

    /**
     * Funció que parla amb el controlador presentació i demana un dialeg on es pregunta la ponderació d'un categoric
     * @param titol títol del diàleg
     * @param missatge missatge del diàleg
     * @return retorna la ponderació
     */
    public float demanarPonderacio(String titol, String missatge)
    {
        return Float.parseFloat(controladorPresentacio.demanarPonderacio(titol, missatge));
    }

    /**
     * Funció que parla amb el controlador presentació i mostra un missatge d'error a l'usuari
     * @param missatge missatge del diàleg
     */
    public void mostraError(String missatge)
    {
        controladorPresentacio.mostraError(missatge);
    }

    /**
     * Funcions que es criden des del controlador de presentació. Per convenció,
     * únicament s'utilitzen Strings per la comunicació entre les dues capes.
     **/

    /**
     * Funció que passa en una string la informació del main user
     * @return string amb la informació de l'usuari
     */
    public String[] getInfoUsuari()
    {
        String id, nom, edat, email;
        id = String.valueOf(-mainUser.getId());
        nom = mainUser.getNom();
        edat = String.valueOf(mainUser.getEdat());
        email = mainUser.getEmail();
        return new String[]{id, nom, edat, email};
    }

    /**
     * Funció que carrega els usuaris del fitxer en un vector d'strings
     * @param filePath fitxer d'usuaris
     * @throws IOException
     */
    public void carregarUsuaris(String filePath) throws IOException
    {
        File f = new File(filePath);
        if (f.createNewFile())
        {
            usuaris_registrats = new CjtUsuaris();
        }
        else
        {
            Vector<String> dades = controladorData.readFitxer(filePath);
            usuaris_registrats.readFitxerUsuaris(dades);
        }
    }

    /**
     * Funció que guarda els usuaris en el fitxer que se li passa com a paràmetre
     * @param filePath fitxer on guardarem els usuaris
     * @throws IOException
     */
    public void guardarUsuaris(String filePath) throws IOException
    {
        Vector<String> dades = usuaris_registrats.writeFitxerUsuaris();
        controladorData.writeFitxer(filePath, dades);
    }

    /**
     * Funció que carrega els usuaris de testeig del fitxer en un vector d'strings
     * @param filePath fitxer d'usuaris
     * @throws IOException
     */
    public void carregarTesters(String filePath) throws IOException, NumberFormatException
    {
        Vector<String> dades = controladorData.readFitxer(filePath);
        testers.readFitxerUsuarisTester(dades);
    }

    /**
     * Funció que guarda els usuaris de testeig en el fitxer que se li passa com a paràmetre
     * @param filePath fitxer on guardarem els usuaris de testeig
     * @throws IOException
     */
    public void guardarTesters(String filePath) throws IOException
    {
        Vector<String> dades = testers.writeFitxerUsuarisTester();
        controladorData.writeFitxer(filePath, dades);
    }

    /**
     * Funció que carrega les valoracions del fitxer en un vector d'strings
     * @param filePath fitxer amb les valoracions
     * @throws IOException
     */
    public void carregarValoracions(String filePath) throws IOException
    {
        Vector<String> dades = controladorData.readFitxer(filePath);
        Set<Integer> llistaItems = datasetActiu.getCjtItems().keySet();
        fitxerValActiu.readFitxerValoracions(dades, llistaItems);
        if (fitxerValActiu == null)
        {
            setDatasetCarregat(false);
            controladorPresentacio.mostraError("Error al carregar les valoracions");
        }
    }

    /**
     * Guarda les valoracions actuals de l'usuari en un fitxer que li passem per paràmetre
     * @param filePath fitxer on guardem les valoracions
     * @throws IOException
     */
    public void guardarValoracions(String filePath) throws IOException
    {
        Vector<String> dades = fitxerValActiu.writeFitxerValoracions();
        controladorData.writeFitxer(filePath, dades);
    }

    /**
     * Funció que carrega les valoracions del fitxer, concretament les dels usuaris de testeig, en un vector d'strings
     * @param filePath fitxer amb les valoracions dels usuaris de testeig
     * @throws IOException
     */
    public void carregarValoracionsTesters(String filePath) throws IOException
    {
        Vector<String> dades = controladorData.readFitxer(filePath);
        Set<Integer> llistaItems = datasetActiu.getCjtItems().keySet();
        CjtValoracionsUsuaris valoracionsTesters = new CjtValoracionsUsuaris();
        valoracionsTesters.readFitxerValoracions(dades, llistaItems);
        gestioRecomanacio.setValoracionsTester(valoracionsTesters.getCjtValoracions(mainUser.getId()).getCjtValoracions());
    }

    /**
     * Funció que retorna el numèro d'items del dataset actiu
     * @return numèro d'ítems
     */
    public String getNumItems()
    {
        return String.valueOf(datasetActiu.size());
    }

    /**
     * Funció que retorna el resultat de l'avaluació de l'algoritme
     * @return resultat de l'avaluació de l'algoritme
     */
    public String getDCG()
    {
        return String.valueOf(gestioRecomanacio.getDCG());
    }

    /**
     * Guarda la recomanació actual de l'usuari en un fitxer que li passem per paràmetre
     * @param filePath fitxer on guardem la recomanació
     * @throws IOException
     */
    public void guardarRecomanacio(String filePath) throws IOException
    {
        controladorData.writeFitxer(filePath, ultimaRecomanacio);
    }

    /**
     * Carrega un dataset d'un fitxer que li passem per paràmetre
     * @param filePath fitxer que conté el dataset
     * @throws IOException
     */
    public void carregarDataset(String filePath) throws IOException
    {
        mainUser.setCjtValoracions(new CjtValoracions());
        Vector<String> dades = controladorData.readFitxer(filePath);
        datasetActiu.readFitxerDataset(this, dades);
    }

    /**
     * Guarda el dataset actiu en un fitxer que li passem per paràmetre
     * @param filePath fitxer on guardem el dataset actiu
     * @throws IOException
     */
    public void guardarDataset(String filePath) throws IOException
    {
        Vector<String> dades = datasetActiu.writeFitxerDataset();
        controladorData.writeFitxer(filePath, dades);
    }

    /**
     * Funció que retorna la capçalera del dataset actiu
     * @return capçalera del dataset actiu
     */
    public String[] getHeaderDataset()
    {
        return datasetActiu.getHeader();
    }

    /**
     * Funció que retorna tots els valors de l'atribut
     * @param nomAtribut nom de l'atribut
     * @return valors del atribut
     */
    public Set<String> getValorsAtributs(String nomAtribut)
    {
        return datasetActiu.getValorsAtributs(nomAtribut);
    }

    /**
     * Funció que retorna tots els items del dataset actiu
     * @return items del dataset actiu
     */
    public String[][] getAllItems()
    {
        return datasetActiu.getAllItems();
    }
    /**
     * Funció que retorna tots els items del dataset actiu filtrat
     * @return items del dataset actiu filtrat
     */
    public String[][] getAllItemsFiltrat()
    {
        return datasetActiu.getAllItemsFiltrat();
    }

    /**
     * Carrega els filtres des d'un fitxer que li passem com a paràmetre
     * @param filePath fitxer que conté els filtres
     * @throws IOException
     */
    public void carregarFiltres(String filePath) throws IOException
    {
        File f = new File(filePath);
        if (f.createNewFile())
        {
            fitxerFiltresActiu = new CjtFiltresUsuaris();
        }
        else
        {
            Vector<String> dades = controladorData.readFitxer(filePath);
            fitxerFiltresActiu.readFitxerFiltres(this, dades);
        }
    }

    /**
     * Funció que guarda els filtres actius en el fitxer que li passem com a paràmetre
     * @param filePath fitxer on guardarem els filtres
     * @throws IOException
     */
    public void guardarFiltres(String filePath) throws IOException
    {
        Vector<String> dades = fitxerFiltresActiu.writeFitxerFiltres();
        controladorData.writeFitxer(filePath, dades);
    }

    /**
     * Retorna l'usuari identificat per l'id
     * @param id identificador de l'usuari
     * @return usuari registrat identificat per l'id
     */
    public Usuari getUsuari(int id)
    {
        return usuaris_registrats.getUsuari(id);
    }

    /**
     * Funció que crea un usuari
     * @param id identificador de l'usuari
     * @param nom nom de l'usuari
     * @param edat edat de l'usuari
     * @param email email del usuari
     * @param password contrassenya de l'usuari
     */
    public boolean crearUsuari(String id, String nom, String edat, String email, String password)
    {
        if (ProcessatDades.isNumeric(id) && Integer.parseInt(id) >= 0)
        {
            int id_u, edat_u;
            id_u = -Integer.parseInt(id);
            if (!usuaris_registrats.existeixUsuari(id_u))
            {
                if (ProcessatDades.isNumeric(edat) && Integer.parseInt(edat) > 0)
                {
                    edat_u = Integer.parseInt(edat);
                    Usuari nouUsuari = new Usuari(id_u, nom, edat_u, email, password);
                    usuaris_registrats.afegirUsuari(nouUsuari);
                    mainUser = nouUsuari;
                    return true;
                }
                else
                {
                    mostraError("L'edat introduida és invàlida");
                    return false;
                }
            }
            else
            {
                mostraError("L'usuari ja existeix");
                return false;
            }
        }
        mostraError("L'identificador ha de ser un nombre enter positiu");
        return false;
    }

    /**
     * Funció que et permet entrar amb la teva conta ja existent a l'aplicació
     * @param id el teu identificador
     * @param password la teva contrassenya
     * @return boolea de true si l'inici de sessió és correcte, false sinò ho és
     */
    public boolean login(String id, String password)
    {
        if (ProcessatDades.isNumeric(id))
        {
            int id_u = -Integer.parseInt(id);
            if (usuaris_registrats.existeixUsuari(id_u))
            {
                Usuari u = usuaris_registrats.getUsuari(id_u);
                if (u.comprovaPassword(password))
                {
                    mainUser = u;
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Funció que et permet entrar amb una conta de testeig ja existent a l'aplicació
     * @param id el identificador
     * @param password la contrassenya (tester)
     * @return boolea de true si l'inici de sessió és correcte, false sinò ho és
     */
    public boolean loginTester(String id, String password)
    {
        if (ProcessatDades.isNumeric(id))
        {
            int id_u = Integer.parseInt(id);
            if (testers.existeixUsuari(id_u))
            {
                Usuari u = testers.getUsuari(id_u);
                if (u.comprovaPassword(password))
                {
                    mainUser = u;
                    return true;
                }
            }
        }
        return false;
    }

    //Cas D'ús Gestió Items

    /**
     * Afegeix un item al dataset actiu
     * @param id identificador de l'item a afegir
     * @param dadesItem atributs de l'item a afegir
     */
    public boolean afegirItem(String id, String dadesItem)
    {
        if (ProcessatDades.isNumeric(id))
        {
            if (existeixItem(id))
                controladorPresentacio.mostraError("L'item " + id + " ja existeix");
            else
            {
                if (datasetActiu.afegirItem(dadesItem))
                {
                    controladorPresentacio.mostraMissatge("Afegir Item", "L'item " + id + " s'ha afegit correctament");
                    return true;
                }
                else
                    controladorPresentacio.mostraError("Les dades introduides són invàlides");
            }
        }
        else
            controladorPresentacio.mostraError("L'identificador introduit " + id + " és invàlid");

        return false;
    }

    /**
     * Elimina un item del dataset actiu
     * @param id identificador de l'item a eliminar
     */
    public void eliminarItem(String id)
    {
        if (ProcessatDades.isNumeric(id))
        {
            if (existeixItem(id))
            {
                int idI = Integer.parseInt(id);
                datasetActiu.eliminarItem(idI);
                if(existeixValoracio(id))
                {
                    mainUser.getCjtValoracions().eliminarValoracio(idI);
                }
                fitxerValActiu.eliminarValoracio(idI);
                String titol = "Eliminar Item";
                String missatge = "L'item " + id + " s'ha eliminat correctament";
                controladorPresentacio.mostraMissatge(titol, missatge);
            }
            else
                controladorPresentacio.mostraError("L'item " + id + " no existeix");
        }
        else
            controladorPresentacio.mostraError("L'identificador introduit " + id + " és invàlid");
    }

    /**
     * Funció que comprova si un item existeix
     * @param id identificador de l'item
     * @return boolea a true si l'item existeix, false en cas contrari
     */
    public boolean existeixItem(String id)
    {
        return datasetActiu.existeixItem(Integer.parseInt(id));
    }

    /**
     * Es comprova que l'identificador i la puntuació siguin vàlids per tal d'afegir una valoració al conjunt de valoracions
     * @param id identificador de l'item
     * @param puntuacio puntuacio de l'item
     */
    public void afegirValoracio(String id, String puntuacio)
    {
        if (ProcessatDades.isNumeric(id))
        {
            if (ProcessatDades.isNumeric(puntuacio) || ProcessatDades.isDouble(puntuacio) || ProcessatDades.isFloat(puntuacio))
            {
                int id_i = Integer.parseInt(id);
                float punt = Float.parseFloat(puntuacio);
                if ((punt < 1) || (punt > 10))
                    mostraError("La puntuació introduida " + puntuacio + " és invàlida");
                else
                {
                    if (existeixItem(id))
                    {
                        if (existeixValoracio(id))
                            reemplacarVal(id_i, punt);
                        else
                            afegirVal(id_i, punt);
                    }
                    else
                        mostraError("L'item " + id + " no existeix");
                }
            }
            else
                mostraError("La puntuació introduida " + puntuacio + " és invàlida");
        }
        else
        {
            mostraError("L'identificador introduit " + id + " és invàlid");
        }
    }

    /**
     * En cas que l'usuari ho indiqui, es reemplaça la valoració de l'item amb la nova puntuació
     * @param id identificador de l'item
     * @param puntuacio puntuacio de l'item
     */
    private void reemplacarVal(int id, float puntuacio)
    {
        String puntActual = obtenirPuntuacioValoracioItem(id);
        String titol = "Valorar Item";
        String missatge = "Ja has valorat l'item " + id + " amb una puntuació de " + puntActual + ". \n" +
                "Vols tornar a valorar-lo?";
        String answer = demanarResposta(titol, missatge);

        if (answer.equals("Yes"))
            afegirVal(id, puntuacio);
    }

    /**
     * Afegeix una valoració al conjunt de valoracions
     * @param id identificador de l'item
     * @param puntuacio puntuacio de l'item
     */
    private void afegirVal(int id, float puntuacio)
    {
        mainUser.getCjtValoracions().afegirValoracio(id, puntuacio);
        String titol = "Valorar Item";
        String missatge = "La valoració de l'item " + id + " s'ha desat correctament";
        controladorPresentacio.mostraMissatge(titol, missatge);
    }

    /**
     * Funció que ens diu si existeix o no una valoració
     * @param id identificaador de l'item
     * @return boolea a true si existeix, false en cas contrari
     */
    public boolean existeixValoracio(String id)
    {
        return mainUser.getCjtValoracions().existeixValoracio(Integer.parseInt(id));
    }

    /**
     * Retorna la puntuació de la valoració d'un item en concret
     * @param id identificador de l'item
     * @return retorna la puntuació d l'item
     */
    public String obtenirPuntuacioValoracioItem(int id)
    {
        float puntuacio = mainUser.getCjtValoracions().getVal(id);
        return String.valueOf(puntuacio);
    }

    //Cas D'ús Gestió Recomanacions

    private String[][] passarLlistaRecomanacionsString()
    {
        Map<Integer, Item> cjtItems = datasetActiu.getCjtItems();
        Map<Integer, Float> recomanacions = gestioRecomanacio.getRecomanacio();
        String[][] llistaRecomanacions = new String[recomanacions.size()][datasetActiu.getHeader().length];
        int i = 0;
        int j = 0;
        //per a cada recomanacio que ens retorna l'algoritme
        for (Integer key : recomanacions.keySet())
        {
            //busquem els seus atributs i els escrivim en un string que utilitzarem per a la taula
            for (Map.Entry<String, Object> cjtA : cjtItems.get(key).getCjtAtributs().entrySet())
            {
                llistaRecomanacions[i][j] = String.valueOf(cjtA.getValue());
                j += 1;
            }
            i += 1;
            j = 0;
        }
        return llistaRecomanacions;
    }

    private void guardemRecom(String[][] recomanacioString)
    {
        ultimaRecomanacio = new Vector<>();
        datasetActiu.escriureCapcalera(ultimaRecomanacio);
        for (String[] strings : recomanacioString)
        {
            StringBuilder recomAux = new StringBuilder("");
            for (String string : strings)
            {
                recomAux.append(string).append(",");
            }
            ultimaRecomanacio.add(recomAux.toString());
        }
    }

    /**
     * Funció que crida i duu a terme tot el necessari per tal d'executar l'algoritme de Collaborative Filtering
     * @param m número d'items
     * @param k precisió de l'algoritme
     * @param d eficiència de l'algoritme
     * @return retorna la recomanació de l'algoritme en forma de matriu d'strings
     */
    public String[][] CollaborativeFiltering(String m, String k, String d)
    {
        //fem el collaborative de forma normal
        Integer auxK = Integer.parseInt(k);
        Float auxD = Float.parseFloat(d);
        Integer auxM = Integer.parseInt(m);
        gestioRecomanacio.setM(auxM);
        gestioRecomanacio.setValRecomNeeded(tester);
        if (!filtrat) gestioRecomanacio.setDatasetProcesat(datasetActiu.getCjtItems());
        else gestioRecomanacio.setDatasetProcesat(datasetActiu.getCjtItemsFiltrat());
        gestioRecomanacio.setUsuari(mainUser);
        gestioRecomanacio.setFitxerValoracions(fitxerValActiu.getCjtValoracionsUsuaris());
        gestioRecomanacio.setTipusRecom(1);
        gestioRecomanacio.initRecomanacio(auxK,auxD);
        Map<Integer, Float> recomanacio = gestioRecomanacio.getRecomanacio();

        //mirem si tenim suficients items, en cas que no es mostrara un missatge
        controladorPresentacio.setSuficientsItems(gestioRecomanacio.isSuficientsItems());

        //passem la recomanacio a string
        String[][] recomanacioString;
        recomanacioString = passarLlistaRecomanacionsString();

        guardemRecom(recomanacioString);

        //retornem a la vista
        return recomanacioString;
    }

    /**
     * Funció que crida i duu a terme tot el necessari per tal d'executar l'algoritme de Content Based Filtering
     * @param m número d'items
     * @param k precisió de l'algoritme
     * @return retorna la recomanació de l'algoritme en forma de matriu d'strings
     */
    public String[][] ContentBasedFiltering(String m, String k)
    {
        //fem el collaborative de forma normal
        Integer auxK = Integer.parseInt(k);
        Integer auxM = Integer.parseInt(m);
        gestioRecomanacio.setM(auxM);
        gestioRecomanacio.setValRecomNeeded(tester);
        if (!filtrat) gestioRecomanacio.setDatasetProcesat(datasetActiu.getCjtItems());
        else gestioRecomanacio.setDatasetProcesat(datasetActiu.getCjtItemsFiltrat());
        gestioRecomanacio.setUsuari(mainUser);
        gestioRecomanacio.setFitxerValoracions(fitxerValActiu.getCjtValoracionsUsuaris());
        gestioRecomanacio.setTipusRecom(2);
        gestioRecomanacio.setCategorics(datasetActiu.getCategorics());
        gestioRecomanacio.initRecomanacio(auxK,2.f);
        Map<Integer, Float> recomanacio = gestioRecomanacio.getRecomanacio();

        //mirem si tenim suficients items, en cas que no es mostrara un missatge
        controladorPresentacio.setSuficientsItems(gestioRecomanacio.isSuficientsItems());

        //passem la recomanacio a string
        String[][] recomanacioString;
        recomanacioString = passarLlistaRecomanacionsString();

        guardemRecom(recomanacioString);

        //retornem a la vista
        return recomanacioString;
    }

    /**
     * Funció que crida i duu a terme tot el necessari per tal d'executar l'algoritme hybrid Filtering
     * @param m número d'items
     * @param k precisió de l'algoritme
     * @param d eficiència de l'algoritme
     * @return retorna la recomanació de l'algoritme en forma de matriu d'strings
     */
    public String[][] HybridFiltering(String m, String k, String d)
    {
        //fem el collaborative de forma normal
        Integer auxK = Integer.parseInt(k);
        Float auxD = Float.parseFloat(d);
        Integer auxM = Integer.parseInt(m);
        gestioRecomanacio.setM(auxM);
        gestioRecomanacio.setValRecomNeeded(tester);
        if (!filtrat) gestioRecomanacio.setDatasetProcesat(datasetActiu.getCjtItems());
        else gestioRecomanacio.setDatasetProcesat(datasetActiu.getCjtItemsFiltrat());
        gestioRecomanacio.setUsuari(mainUser);
        gestioRecomanacio.setFitxerValoracions(fitxerValActiu.getCjtValoracionsUsuaris());
        gestioRecomanacio.setTipusRecom(3);
        gestioRecomanacio.setCategorics(datasetActiu.getCategorics());
        gestioRecomanacio.initRecomanacio(auxK,auxD);
        Map<Integer, Float> recomanacio = gestioRecomanacio.getRecomanacio();

        //mirem si tenim suficients items, en cas que no es mostrara un missatge
        controladorPresentacio.setSuficientsItems(gestioRecomanacio.isSuficientsItems());

        //passem la recomanacio a string
        String[][] recomanacioString;
        recomanacioString = passarLlistaRecomanacionsString();

        guardemRecom(recomanacioString);

        //retornem a la vista
        return recomanacioString;
    }

    /**
     * Carrega una recomanació des d'un fitxer que li passem com a paràmetre
     * @param filePath fitxer que conté la recomanació
     * @return retorna la recomanació en forma de matriu d'Strings
     * @throws IOException
     */
    public String[][] carregarRecomanacio(String filePath) throws IOException
    {
        Vector<String> dades = controladorData.readFitxer(filePath);
        String[] header = dades.get(0).split(",");
        String[][] recomanacio = new String[dades.size()-1][header.length];
        for (int i = 1; i < dades.size(); ++i)
        {
            String[] fields = dades.get(i).split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
            Vector<String> sFields = new Vector<>();
            Collections.addAll(sFields, fields);
            while (sFields.size() < header.length) sFields.add("");
            String[] finalFields = sFields.toArray(new String[0]);
            recomanacio[i-1] = finalFields;
        }
        return recomanacio;
    }

    //Cas D'ús Gestió Filtres

    /**
     * Afegeix un filtre al conjunt de filtres actius
     * @param nomFiltre nom del filtre
     * @param atributs atributs per els que filtrara
     */
    public void afegirFiltre(String nomFiltre, Vector<Vector<String>> atributs)
    {
        if (!fitxerFiltresActiu.existeixFiltre(mainUser.getId(), nomFiltre))
        {
            CjtFiltres cjtF = mainUser.getCjtFiltres();
            Filtre f = new Filtre(nomFiltre);

            for (Vector<String> valorsA : atributs)
            {
                String nomAtribut = valorsA.get(0);
                valorsA.remove(0);
                f.afegirAtribut(nomAtribut, valorsA);
            }

            //atributs es matriu on cada fila es un NomAtribut i en cada col estan els valorsAtribut
            fitxerFiltresActiu.afegirFiltre(mainUser.getId(), f, cjtF);

            String titol = "Crear Filtre";
            String missatge = "S'ha creat el filtre " + nomFiltre + " correctament";
            controladorPresentacio.mostraMissatge(titol, missatge);
        }
        else
        {
            String missatge = "El filtre " + nomFiltre + " ja s'ha creat";
            controladorPresentacio.mostraError(missatge);
        }
    }

    /**
     * Elimina un filtre del conjunt de filtres actius
     * @param nomFiltre nom del filtre
     */
    public void eliminarFiltre(String nomFiltre)
    {
        if (existeixFiltre(nomFiltre))
        {
            fitxerFiltresActiu.eliminarFiltre(mainUser.getId(), nomFiltre);
            String titol = "Eliminar Filtre";
            String missatge = "S'ha eliminat el filtre " + nomFiltre + " correctament";
            controladorPresentacio.mostraMissatge(titol, missatge);
        }
        else
        {
            String missatge = "El filtre " + nomFiltre + " no existeix";
            controladorPresentacio.mostraError(missatge);
        }    }

    /**
     * Funció que comprova si existeix un filtre
     * @param nomFiltre nom del filtre
     * @return retorna un boolea a true en cas de que existeixi el filtre i a false en cas contrari
     */
    public boolean existeixFiltre(String nomFiltre)
    {
        return fitxerFiltresActiu.existeixFiltre(mainUser.getId(), nomFiltre);
    }

    /**
     * Filtra el dataset
     * @param nomFiltre nom del filtre
     */
    public void filtrarDataset(String nomFiltre)
    {
        Filtre filtre = fitxerFiltresActiu.getCjtFiltres(mainUser.getId()).getCjtFiltres().get(nomFiltre);
        mainUser.getCjtFiltres().getCjtFiltres().get(nomFiltre);
        datasetActiu.filtrarDataset(filtre);
    }

    /**
     * Retorna els noms dels filtres actius
     * @return vector d'string amb els noms dels filtres actius
     */
    public Vector<String> getNomsFiltres()
    {
        Vector<String> nomsFiltres = new Vector<>();
        if (fitxerFiltresActiu.existeixCjtFiltres(mainUser.getId()))
            nomsFiltres = mainUser.getCjtFiltres().getNomsFiltres();
        return nomsFiltres;
    }

    /**
     * Funció que comprova si un conjunt de valoracions és buit
     * @return boolea a true en cas de que el conjunt de valoracions estigui buit, false en cas contrari
     */
    public boolean isCjtValoracionsBuit()
    {
        return mainUser.getCjtValoracions().size() == 0;
    }
}
