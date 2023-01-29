package data;

import java.io.IOException;
import java.util.Vector;

/**
 * Implementació de la classe gestionadora de dades.
 **/
public class CtrlData
{
    /*
     * Implementació del patró de diseny "Singleton", amb l'objectiu que hi
     * hagi una única instància d'aquesta classe en el sistema. En aquest cas,
     * aquesta propietat és desitjada ja que aquesta classe no té estat.
     * Per aconseguir-ho, declarem la constructora privada i afegim una
     * operació estàtica que retorna sempre la mateixa instància.
     * Per accedir a aquesta instància ho fem mitjantçant la crida a
     * CtrlData.getInstance();
     */

    private static CtrlData singletonObject;

    /* Controlador de fitxer */
    private final CtrlFitxer controladorFitxer;

    /**
     * Operació estàtica que retorna la instància del CtrlData
     * @return retorna la instància del CtrlData
     */
    public static CtrlData getInstance()
    {
        if (singletonObject == null)
            singletonObject = new CtrlData() {};
        return singletonObject;
    }

    /* Constructora privada. */
    private CtrlData()
    {
        controladorFitxer = CtrlFitxer.getInstance();
    }

    /*
     * Funcions que es criden des del controlador de domini. Per convenció,
     * únicament s'utilitzen Strings per la comunicació entre les dues capes.
     */

    /**
     * Indica al CtrlFitxer que llegeixi el fitxer amb el path indicat
     * @param filePath path del fitxer
     * @return vector de strings amb les dades del fitxer llegit
     * @throws IOException excepció generada al llegir un fitxer
     */
    public Vector<String> readFitxer(String filePath) throws IOException
    {
        return controladorFitxer.read(filePath);
    }

    /**
     * Escriu al CtrlFitxer que escrigui al fitxer del path indicat
     * @param filePath path del fitxer
     * @param dades vector de strings amb les dades que s'han d'escriure al fitxer
     * @throws IOException excepció generada al escriure un fitxer
     */
    public void writeFitxer(String filePath, Vector<String> dades) throws IOException
    {
        controladorFitxer.write(filePath, dades);
    }
}