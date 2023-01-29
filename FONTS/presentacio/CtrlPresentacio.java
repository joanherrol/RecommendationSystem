package presentacio;

import domini.CtrlDomini;

import java.util.Objects;
import java.util.Set;
import java.util.Vector;
import java.io.IOException;

import javax.sound.sampled.*;
import java.net.URL;

/**
 * Implementació de la classe controlador de presentació.
 * És el controlador que s'encarrega de sincronitzar totes les vistes
 * i de comunicar-se amb domini
 **/
public class CtrlPresentacio
{
    private static CtrlPresentacio singletonObject;

    //Controlador de domini
    private final CtrlDomini controladorDomini;

    //Vistes
    private final VistaPrincipal vistaPrincipal;
    private final VistaUsuaris vistaUsuaris;
    private final VistaCrearUsuari vistaCrearUsuari;
    private final VistaLogin vistaLogin;
    private final VistaLoginTest vistaLoginTest;
    private final VistaPerfilUsuari vistaPerfilUsuari;
    private final VistaDataset vistaDataset;
    private final VistaFiltres vistaFiltres;
    private final VistaCrearFiltre vistaCrearFiltre;
    private final VistaEliminarFiltre vistaEliminarFiltre;
    private final VistaItems vistaItems;
    private final VistaAfegirItem vistaAfegirItem;
    private final VistaEliminarItem vistaEliminarItem;
    private final VistaValorarItem vistaValorarItem;
    private final VistaRecomanacio vistaRecomanacio;
    private final VistaTriaRecomanacio vistaTriaRecomanacio;
    private final VistaOpcionsRecomanacio vistaOpcionsRecomanacio;
    private VistaConsultarDataset vistaConsultarDataset;
    private final VistaConsultarRecomanacio vistaConsultarRecomanacio;

    //Altres atributs
    private URL url;
    private AudioInputStream audioInputStream;
    private Clip clip;
    private boolean suficientsItems = true;

    /**
     * Funció per obtenir el controlador de presentació
     * @return La instància del controlador de presentació
     */
    public static CtrlPresentacio getInstance()
    {
        if (singletonObject == null)
            singletonObject = new CtrlPresentacio() {};
        return singletonObject;
    }

    private CtrlPresentacio()
    {
        //controladorDomini = CtrlDomini.getInstance();
        controladorDomini = new CtrlDomini(this);
        vistaPrincipal = new VistaPrincipal(this);
        vistaUsuaris = new VistaUsuaris(this);
        vistaCrearUsuari = new VistaCrearUsuari(this);
        vistaLogin = new VistaLogin(this);
        vistaLoginTest = new VistaLoginTest(this);
        vistaPerfilUsuari = new VistaPerfilUsuari(this);
        vistaDataset = new VistaDataset(this);
        vistaFiltres = new VistaFiltres(this);
        vistaCrearFiltre = new VistaCrearFiltre(this);
        vistaEliminarFiltre = new VistaEliminarFiltre(this);
        vistaItems = new VistaItems(this);
        vistaAfegirItem = new VistaAfegirItem(this);
        vistaEliminarItem = new VistaEliminarItem(this);
        vistaValorarItem =  new VistaValorarItem(this);
        vistaRecomanacio = new VistaRecomanacio(this);
        vistaTriaRecomanacio = new VistaTriaRecomanacio(this);
        vistaOpcionsRecomanacio = new VistaOpcionsRecomanacio(this);
        vistaConsultarDataset = new VistaConsultarDataset(this, "");
        vistaConsultarRecomanacio = new VistaConsultarRecomanacio(this);
    }

    /**
     * Carrega els usuaris i inicialitza la primera vista de l'aplicació
     */
    public void inicialitzarPresentacio() throws IOException
    {
        carregarUsuaris("GestioUsuaris.csv");
        carregarFiltres("GestioFiltres.csv");
        activarVistaUsuaris();
    }

    //Mètodes de sincronització entre vistes.
    /**
     * Activa la vista principal, desactivant les anteriors
     */
    public void activarVistaPrincipal()
    {
        vistaLogin.desactivar();
        vistaLoginTest.desactivar();
        vistaCrearUsuari.desactivar();
        vistaPerfilUsuari.desactivar();
        vistaRecomanacio.desactivar();
        vistaDataset.desactivar();
        vistaItems.desactivar();
        vistaFiltres.desactivar();

        vistaPrincipal.activar();
    }

    /**
     * Activa la vista usuaris, desactivant les anteriors
     */
    public void activarVistaUsuaris()
    {
        vistaPerfilUsuari.desactivar();
        vistaCrearUsuari.desactivar();
        vistaLogin.desactivar();
        vistaLoginTest.desactivar();

        vistaUsuaris.activar();
    }

    /**
     * Activa la vista crear usuari, desactivant les anteriors
     */
    public void activarVistaCrearUsuari()
    {
        vistaUsuaris.desactivar();
        vistaCrearUsuari.activar();
    }

    /**
     * Activa la vista login, desactivant les anteriors
     */
    public void activarVistaLogin()
    {
        vistaUsuaris.desactivar();
        vistaLogin.activar();
    }

    /**
     * Activa la vista login test, desactivant les anteriors
     */
    public void activarVistaLoginTest()
    {
        vistaUsuaris.desactivar();
        vistaLoginTest.activar();
    }

    /**
     * Activa la vista perfil usuari, desactivant les anteriors
     */
    public void activarVistaPerfilUsuari()
    {
        vistaPrincipal.desactivar();
        vistaPerfilUsuari.activar();
    }

    /**
     * Activa la vista dataset, desactivant les anteriors
     */
    public void activarVistaDataset()
    {
        vistaPrincipal.desactivar();
        vistaUsuaris.desactivar();
        vistaDataset.activar();
        vistaConsultarDataset.desactivar();
        vistaFiltres.desactivar();
        vistaItems.desactivar();
    }

    /**
     * Activa la vista consultar dataset, desactivant les anteriors
     */
    public void activarVistaConsultarDataset(String nomFitxer)
    {
        vistaDataset.desactivar();
        vistaConsultarDataset = new VistaConsultarDataset(this, nomFitxer);
        vistaConsultarDataset.activar();
    }

    /**
     * Activa la vista filtres, desactivant les anteriors
     */
    public void activarVistaFiltres()
    {
        vistaPrincipal.desactivar();
        vistaCrearFiltre.desactivar();
        vistaEliminarFiltre.desactivar();

        vistaFiltres.activar();
    }

    /**
     * Activa la vista crear filtre, desactivant les anteriors
     */
    public void activarVistaCrearFiltre()
    {
        vistaFiltres.desactivar();
        vistaCrearFiltre.activar();
    }

    /**
     * Activa la vista eliminar filtre, desactivant les anteriors
     */
    public void activarVistaEliminarFiltre()
    {
        vistaFiltres.desactivar();
        vistaEliminarFiltre.activar();
    }

    /**
     * Activa la vista items, desactivant les anteriors
     */
    public void activarVistaItems()
    {
        vistaPrincipal.desactivar();
        vistaAfegirItem.desactivar();
        vistaEliminarItem.desactivar();
        vistaValorarItem.desactivar();

        vistaItems.activar();
    }

    /**
     * Activa la vista afegir items, desactivant les anteriors
     */
    public void activarVistaAfegirItem()
    {
        vistaItems.desactivar();
        vistaAfegirItem.activar();
    }

    /**
     * Activa la vista eliminar item, desactivant les anteriors
     */
    public void activarVistaEliminarItem()
    {
        vistaItems.desactivar();
        vistaEliminarItem.activar();
    }

    /**
     * Activa la vista valorar item, desactivant les anteriors
     */
    public void activarVistaValorarItem()
    {
        vistaItems.desactivar();
        vistaValorarItem.activar();
    }

    /**
     * Activa la vista tria recomanacio, desactivant les anteriors
     */
    public void activarVistaTriaRecomanacio()
    {
        vistaRecomanacio.desactivar();
        vistaOpcionsRecomanacio.desactivar();
        vistaTriaRecomanacio.activar();
    }

    /**
     * Activa la vista opcions recomanacio, desactivant les anteriors
     */
    public void activarVistaOpcionsRecomanacio()
    {
        vistaConsultarRecomanacio.desactivar();
        vistaTriaRecomanacio.desactivar();
        vistaRecomanacio.desactivar();
        vistaOpcionsRecomanacio.activar();
    }

    /**
     * Activa la vista recomanacio, desactivant les anteriors
     */
    public void activarVistaRecomanacio()
    {
        vistaPrincipal.desactivar();
        vistaTriaRecomanacio.desactivar();
        vistaOpcionsRecomanacio.desactivar();
        vistaConsultarRecomanacio.desactivar();
        vistaRecomanacio.activar();
    }

    /**
     * Activa la vista consultar recomanacio, desactivant les anteriors
     */
    public void activarVistaConsultarRecomanacio()
    {
        vistaOpcionsRecomanacio.desactivar();
        vistaConsultarRecomanacio.activar();
    }

    //Crides al controlador de domini.
    /**
     * Demana al controlador de domini el numero de items carregat
     * @return el numero de items, com a String
     */
    public String getNumItems()
    {
        return controladorDomini.getNumItems();
    }

    /**
     * Demana al controlador de domini el DCG, resultat de valorar l'algoritme
     * @return el DCG com a String
     */
    public String getDCG()
    {
        return controladorDomini.getDCG();
    }

    /**
     * Demana al controlador informació sobre l'usuari i la posa a la vista perfil usuari
     */
    public void setUsuariActiu()
    {
        String id, nom, edat, email;
        String[] info = controladorDomini.getInfoUsuari();
        id = info[0];
        nom = info[1];
        edat = info[2];
        email = info[3];
        vistaPerfilUsuari.setUsuariActiu(id, nom, edat, email);
    }

    /**
     * Passa al controlador de domini els paràmetres necessaris per crear un usuari
     * @param id identificador del nou usuari
     * @param nom nom del nou usuari
     * @param edat edat del nou usuari
     * @param email email del nou usuari
     * @param password password del nou usuari
     */
    public boolean crearUsuari(String id, String nom, String edat, String email, String password)
    {
        return controladorDomini.crearUsuari(id, nom, edat, email, password);
    }

    /**
     * Demana fer login al controlador de domini
     * @param id identificador de l'usuari que intenta fer login
     * @param password password de l'usuari que intenta fer login
     * @return boolea que indica si ha sigut possible fer login o no
     */
    public boolean login(String id, String password)
    {
        return controladorDomini.login(id, password);
    }

    /**
     * Demana fer login com a tester al controlador de domini
     * @param id identificador del tester que intenta fer login
     * @param password password del tester que intenta fer login
     * @return boolea que indica si ha sigut possible fer login com a tester o no
     */
    public boolean loginTester(String id, String password)
    {
        return controladorDomini.loginTester(id, password);
    }

    /**
     * Passa al controlador de domini els paràmetres necessaris per afegir una valoració
     * @param id identificador de l'ítem a valorar
     * @param puntuacio puntuació de l'ítem a valorar
     */
    public void afegirValoracio(String id, String puntuacio)
    {
        controladorDomini.afegirValoracio(id, puntuacio);
    }

    /**
     * Demana al controlador de domini si existeix la valoració amb identificador id
     * @param id identificador de l'item a mirar si existeix
     * @return boolea que indica si existeix la valoracio o no
     */
    public boolean existeixValoracio(String id)
    {
        return controladorDomini.existeixValoracio(id);
    }

    /**
     * Passa al controlador de domini els paràmetres necessaris per afegir un ítem
     * @param id identificador de l'item a afegir
     * @param dadesItem atributs de l'item a afegir
     */
    public boolean afegirItem(String id, String dadesItem)
    {
        return controladorDomini.afegirItem(id, dadesItem);
    }

    /**
     * Passa al controlador de domini els paràmetres necessaris per eliminar un ítem
     * @param id identificador de l'ítem a eliminar
     */
    public void eliminarItem(String id)
    {
        controladorDomini.eliminarItem(id);
    }

    /**
     * Pregunta al controlador de domini si existeix un item
     * @param id identifador de l'ítem a mirar si existeix
     * @return boolea que indica si existeix o no l'ítem
     */
    public boolean existeixItem(String id)
    {
        return controladorDomini.existeixItem(id);
    }

    /**
     * Passa al controlador de domini els paràmetres necessaris perquè executi una recomanació utilitzant
     * collaborative filtering, i passa el resultat a la vista consultar recomanació
     * @param m numero de items a recomanar
     * @param k precisió de l'algoritme
     * @param d eficiència de l'algoritme
     */
    public void executeCollaborativeFiltering(String m, String k, String d)
    {
        vistaConsultarRecomanacio.setLlistaRecomanacions(controladorDomini.CollaborativeFiltering(m,k,d));
        vistaConsultarRecomanacio.setCarregat(false);
    }

    /**
     * Passa al controlador de domini els paràmetres necessaris perquè executi una recomanació utilitzant
     * content based filtering, i passa el resultat a la vista consultar recomanació
     * @param m numero de items a recomanar
     * @param k precisió de l'algoritme
     */
    public void executeContentBasedFiltering(String m, String k)
    {
        vistaConsultarRecomanacio.setLlistaRecomanacions(controladorDomini.ContentBasedFiltering(m,k));
        vistaConsultarRecomanacio.setCarregat(false);
    }

    /**
     * Passa al controlador de domini els paràmetres necessaris perquè executi una recomanació utilitzant
     * hybrid filtering, i passa el resultat a la vista consultar recomanació
     * @param m numero de items a recomanar
     * @param k precisió de l'algoritme
     * @param d eficiència de l'algoritme
     */
    public void executeHybridFiltering(String m, String k, String d)
    {
        vistaConsultarRecomanacio.setLlistaRecomanacions(controladorDomini.HybridFiltering(m,k,d));
        vistaConsultarRecomanacio.setCarregat(false);
    }

    /**
     * Passa al controlador de domini els paràmetres necessaris per carregar una recomanació
     * @param filePath el path del fitxer on es troba la recomanació a carregar
     */
    public void carregarRecomanacio(String filePath) throws IOException
    {
        vistaConsultarRecomanacio.setLlistaRecomanacions(controladorDomini.carregarRecomanacio(filePath));
        vistaConsultarRecomanacio.setCarregat(true);
    }

    /**
     * Passa al controlador de domini els paràmetres necessaris per guardar una recomanació
     * @param filePath el path del fitxer on guardarem la recomanació
     */
    public void guardarRecomanacio(String filePath) throws IOException
    {
        controladorDomini.guardarRecomanacio(filePath);
    }

    /**
     * Passa al controlador de domini els paràmetres necessaris per carregar usuaris
     * @param filePath el path del fitxer on es troban els usuaris a carregar
     */
    public void carregarUsuaris(String filePath) throws IOException
    {
        controladorDomini.carregarUsuaris(filePath);
    }

    /**
     * Passa al controlador de domini els paràmetres necessaris per guardar usuaris
     * @param filePath el path del fitxer on guardarem els usuaris
     */
    public void guardarUsuaris(String filePath) throws IOException
    {
        controladorDomini.guardarUsuaris(filePath);
    }

    /**
     * Passa al controlador de domini els paràmetres necessaris per carregar testers
     * @param filePath el path del fitxer on es troban els testers a carregar
     */
    public void carregarTesters(String filePath) throws IOException
    {
        controladorDomini.carregarTesters(filePath);
    }

    /**
     * Passa al controlador de domini els paràmetres necessaris per guardar testers
     * @param filePath el path del fitxer on guardarem els testers
     */
    public void guardarTesters(String filePath) throws IOException
    {
        controladorDomini.guardarTesters(filePath);
    }

    /**
     * Passa al controlador de domini els paràmetres necessaris per carregar valoracions
     * @param filePath el path del fitxer on es troban les valoracions a carregar
     */
    public void carregarValoracions(String filePath) throws IOException
    {
        controladorDomini.carregarValoracions(filePath);
    }

    /**
     * Passa al controlador de domini els paràmetres necessaris per carregar valoracions de testers
     * @param filePath el path del fitxer on es troban les valoracions a carregar
     */
    public void carregarValoracionsTesters(String filePath) throws IOException
    {
        controladorDomini.carregarValoracionsTesters(filePath);
    }

    /**
     * Passa al controlador de domini els paràmetres necessaris per guardar valoracions
     * @param filePath el path del fitxer on guardarem les valoracions
     */
    public void guardarValoracions(String filePath) throws IOException
    {
        controladorDomini.guardarValoracions(filePath);
    }

    /**
     * Passa al controlador de domini els paràmetres necessaris per carregar un dataset
     * @param filePath el path del fitxer on es troban el datset a carregar
     */
    public void carregarDataset(String filePath) throws IOException
    {
        controladorDomini.carregarDataset(filePath);
    }

    /**
     * Passa al controlador de domini els paràmetres necessaris per guardar un dataset
     * @param filePath el path del fitxer on es guardarà el dataset
     */
    public void guardarDataset(String filePath) throws IOException
    {
        controladorDomini.guardarDataset(filePath);
    }

    /**
     * Demana al controlador de domini el header del dataset
     * @return el header del dataset com a array d'strings
     */
    public String[] getHeaderDataset()
    {
        return controladorDomini.getHeaderDataset();
    }

    /**
     * Demana els valors d'un tipus d'atribut
     * @param nomAtribut nom del tipus d'atribut
     * @return valors d'aquell tipus d'atributs com a String
     */
    public Set<String> getValorsAtributs(String nomAtribut)
    {
        return controladorDomini.getValorsAtributs(nomAtribut);
    }

    /**
     * Demana al controlador de domini tots els items del dataset
     * @return items del dataset com a matriu d'strings
     */
    public String[][] getAllItems()
    {
        return controladorDomini.getAllItems();
    }

    /**
     * Demana al controlador de domini tots els items del dataset filtrat
     * @return items del dataset filtrat com a matriu d'strings
     */
    public String[][] getAllItemsFiltrat()
    {
        return controladorDomini.getAllItemsFiltrat();
    }

    /**
     * Passa al controlador de domini els paràmetres necessaris per carregar filtres
     * @param filePath el path del fitxer on es troben els filtres a carregar
     */
    public void carregarFiltres(String filePath) throws IOException
    {
        controladorDomini.carregarFiltres(filePath);
    }

    /**
     * Passa al controlador de domini els paràmetres necessaris per guardar filtres
     * @param filePath el path del fitxer on es troben els filtres a guardar
     */
    public void guardarFiltres(String filePath) throws IOException
    {
        controladorDomini.guardarFiltres(filePath);
    }

    /**
     * Passa al controlador de domini els paràmetres necessaris per crear un filtre
     * @param nomFiltre nom del nou filtre
     * @param filtres valors dels filtres
     */
    public void crearFiltre(String nomFiltre, Vector<Vector<String>> filtres)
    {
        controladorDomini.afegirFiltre(nomFiltre, filtres);
    }

    /**
     * Passa al controlador de domini els paràmetres necessaris per eliminar un filtre
     * @param nomFiltre nom del filtre a eliminar
     */
    public void eliminarFiltre(String nomFiltre)
    {
        controladorDomini.eliminarFiltre(nomFiltre);
    }

    /**
     * Pregunta al controlador de domini si existeix un filtre
     * @param nomFiltre nom del filtre a mirar si existeix
     * @return boolea que indica si existeix o no el filtre
     */
    public boolean existeixFiltre(String nomFiltre)
    {
        return controladorDomini.existeixFiltre(nomFiltre);
    }

    /**
     * Passa al controlador de domini els paràmetres necessaris per filtrar el dataset
     * @param nomFiltre nom del filtre a utilitzar per filtrar
     */
    public void filtrarDataset(String nomFiltre)
    {
        controladorDomini.filtrarDataset(nomFiltre);
    }

    /**
     * Demana els noms dels filtres al controlador de domini
     * @return vector amb els noms dels filtres
     */
    public Vector<String> getNomsFiltres()
    {
        return controladorDomini.getNomsFiltres();
    }

    /**
     * Pregunta al controlador de domini si el conjunt de valoracions és buit
     * @return boolea que indica si el conjunt de valoracions està o no buit
     */
    public boolean isCjtValoracionsBuit()
    {
        return controladorDomini.isCjtValoracionsBuit();
    }

     //Funcions que es criden des del controlador de domini. Per convenció,
     //únicament s'utilitzen Strings per la comunicació entre les dues capes.

    /**
     * Genera un diàleg on es pregunta a l'usuari una pregunta de si o no
     * @param titol titol del dialeg
     * @param missatge missatge del dialeg
     * @return resposta de l'usuari
     */
    public String demanarResposta(String titol, String missatge)
    {
        VistaDialeg vistaDialeg = new VistaDialeg();
        String[] strBotones = {"Yes", "No"};
        int isel = vistaDialeg.setDialeg(titol, missatge, strBotones, 3);
        return strBotones[isel];
    }

    /**
     * Genera un diàleg on es pregunta a l'usuari la ponderació d'un categòric
     * @param titol titol del dialeg
     * @param missatge missatge del dialeg
     * @return resposta de l'usuari
     */
    public String demanarPonderacio(String titol, String missatge)
    {
        VistaDialeg vistaDialeg = new VistaDialeg();
        String[] strBotones = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
        int isel = vistaDialeg.setDialeg(titol, missatge, strBotones, 4);
        return strBotones[isel];
    }

    //Funcions que es criden des de les diferents vistes.
    /**
     * Mostra un missatge
     * @param titol titol del missatge
     * @param missatge contingut del missatge
     */
    public void mostraMissatge(String titol, String missatge)
    {
        try
        {
            //Obre un audio inout stream
            url = this.getClass().getResource("/recursos/sons/message.wav");
            audioInputStream = AudioSystem.getAudioInputStream(Objects.requireNonNull(url));
            //Obtenim sound clip resource
            clip = AudioSystem.getClip();
            //Obrea audio clip i el carrega des del audio input stream
            clip.open(audioInputStream);
            clip.start();
        }
        catch(UnsupportedAudioFileException | LineUnavailableException | IOException e)
        {
            e.printStackTrace();
        }

        VistaDialeg vistaDialeg = new VistaDialeg();
        String[] strBotones = {"OK"};
        vistaDialeg.setDialeg(titol, missatge, strBotones, 1);
    }

    /**
     * Mostra un error
     * @param missatge missatge del error
     */
    public void mostraError(String missatge)
    {
        try
        {
            //Obre un audio inout stream
            url = this.getClass().getResource("/recursos/sons/error.wav");
            audioInputStream = AudioSystem.getAudioInputStream(Objects.requireNonNull(url));
            //Obtenim sound clip resource
            clip = AudioSystem.getClip();
            //Obrea audio clip i el carrega des del audio input stream
            clip.open(audioInputStream);
            clip.start();
        }
        catch(UnsupportedAudioFileException | IOException | LineUnavailableException e)
        {
            e.printStackTrace();
        }

        VistaDialeg vistaDialeg = new VistaDialeg();
        String[] strBotones = {"OK"};
        vistaDialeg.setDialeg("ERROR", missatge, strBotones, 0);
    }

    /**
     * Activa el flag suficients items del controlador presentacio
     * @param suficientsItems flag suficients items
     */
    public void setSuficientsItems(boolean suficientsItems)
    {
        this.suficientsItems = suficientsItems;
    }

    /**
     * Indica si hi ha suficients items
     * @return boolea que indica si hi ha suficients items
     */
    public boolean isSuficientsItems()
    {
        return suficientsItems;
    }

    /**
     * Activa el flag sessió iniciada de la vista principal
     */
    public void setSessioIniciada()
    {
        vistaPrincipal.setSessioIniciada();
    }

    /**
     * Activa el flag dataset carregat de la vista principal
     */
    public void setDatasetCarregat(boolean b)
    {
        vistaPrincipal.setDatasetCarregat(b);
        vistaDataset.setDatasetCarregat(b);
    }

    /**
     * Retorna el flag del dataset carregat
     * @return boolea a cert si el dataset esta carregat o a fals en cas contrari
     */
    public boolean isDatasetCarregat()
    {
        return vistaPrincipal.isDatasetCarregat();
    }

    /**
     * Indica el tipus de recomanació triat a la vista tria recomanacio
     * @param t tipus recomanació
     */
    public void setTipusRecomanacio(VistaTriaRecomanacio.tipusRecomanacio t)
    {
        vistaOpcionsRecomanacio.setTipusRecomanacio(t);
        activarVistaOpcionsRecomanacio();
    }

    /**
     * Dona valor al flag tester a les vistes necessaries
     * @param b valor del flag tester
     */
    public void setTester(boolean b)
    {
        vistaDataset.setTester(b);
        vistaConsultarRecomanacio.setTester(b);
        controladorDomini.setTester(b);
    }

    /**
     * Dona valor al flag recomanació feta a les vistes necessaries
     * @param b valor del flag recomanació
     */
    public void setRecomanacioFeta(boolean b)
    {
        vistaRecomanacio.setRecomanacioFeta(b);
    }
}