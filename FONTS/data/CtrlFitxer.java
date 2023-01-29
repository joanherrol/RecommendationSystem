package data;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;

/**
 * Implementació de la classe gestionadora de fitxers.
 **/
public class CtrlFitxer
{
    /*
     * Implementació del patró de diseny "Singleton", amb l'objectiu que hi
     * hagi una única instància d'aquesta classe en el sistema. En aquest cas,
     * aquesta propietat és desitjada ja que aquesta classe no té estat.
     * Per aconseguir-ho, declarem la constructora privada i afegim una
     * operació estàtica que retorna sempre la mateixa instància.
     * Per accedir a aquesta instància ho fem mitjantçant la crida a
     * CtrlFitxer.getInstance();
     */

    private static CtrlFitxer singletonObject;

    /**
     * Operació estàtica que retorna la instància del CtrlFitxer
     * @return retorna la instància del CtrlFitxer
     */
    public static CtrlFitxer getInstance()
    {
        if (singletonObject == null)
            singletonObject = new CtrlFitxer() {};
        return singletonObject;
    }

    /* Constructora privada. */
    private CtrlFitxer()
    {
    }

    /*
     * Funcions que es criden des del controlador de data. Per convenció,
     * únicament s'utilitzen Strings per la comunicació entre les dues capes.
     */

    /**
     * Llegeix el fitxer amb el path indicat
     * @param filePath path del fitxer
     * @return vector de strings amb les dades del fitxer llegit
     * @throws IOException excepció generada al llegir un fitxer
     */
    public Vector<String> read(String filePath) throws IOException
    {
        Vector<String> dades = new Vector<>();
        FileReader fileR;
        fileR = new FileReader(filePath);
        Scanner scan = new Scanner(fileR);

        while (scan.hasNextLine())
            dades.add(scan.nextLine());
        return dades;
    }

    /**
     * Escriu al fitxer del path indicat
     * @param filePath path del fitxer
     * @param dades vector de strings amb les dades que s'han d'escriure al fitxer
     * @throws IOException excepció generada al escriure un fitxer
     */
    public void write(String filePath, Vector<String> dades) throws IOException
    {
        FileWriter fileW;
        fileW = new FileWriter(filePath);
        for (String d : dades)
        {
            fileW.append(d);
            fileW.append("\n");
        }
        fileW.flush();
        fileW.close();
    }
}