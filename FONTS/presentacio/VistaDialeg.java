package presentacio;

import javax.swing.*;
import java.awt.*;

/**
 * Vista auxiliar que permet fer diàlegs amb l'usuari
 */
public class VistaDialeg
{
    /** Constructora **/
    public VistaDialeg()
    {
    }

    //Mètodes públics

    /**
     * Dona valor als diferents paràmetres del diàleg
     * @param strTitulo Títol del diàleg
     * @param strTexto Text del diàleg
     * @param strBotones Text dels botons del diàleg
     * @param iTipo Tipus del diàleg
     * @return Valor seleccionat per l'usuari al diàleg
     */
    public int setDialeg(String strTitulo, String strTexto, String[] strBotones, int iTipo)
    {
        int oTipo = 1;
        switch (iTipo)
        {
            case 0:
                oTipo = JOptionPane.ERROR_MESSAGE;
                break;
            case 1:
                oTipo = JOptionPane.INFORMATION_MESSAGE;
                break;
            case 2:
                oTipo = JOptionPane.WARNING_MESSAGE;
                break;
            case 3:
                oTipo = JOptionPane.QUESTION_MESSAGE;
                break;
            case 4:
                oTipo = JOptionPane.PLAIN_MESSAGE;
                break;
        }

        // Crea i visualitza el dialeg
        JOptionPane optionPane = new JOptionPane(strTexto, oTipo);
        optionPane.setOptions(strBotones);
        JDialog dialogOptionPane = optionPane.createDialog(new JFrame(), strTitulo);
        dialogOptionPane.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        dialogOptionPane.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        dialogOptionPane.pack();
        dialogOptionPane.setVisible(true);

        // Captura la opcio escollida
        String vsel = (String) optionPane.getValue();
        int isel;
        for (isel = 0; isel < strBotones.length && !strBotones[isel].equals(vsel); isel++);
        return isel;
    }
}