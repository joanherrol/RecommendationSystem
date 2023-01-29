package presentacio;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Vista que permet a l'usuari escollir entre
 * les diferents opcions relacionades amb recomanacions
 */
public class VistaRecomanacio extends Vista
{
    //Components de la interficie gràfica
    private final JFrame frameVista = new JFrame("Gestió Recomanació");
    private final JPanel panelBotons = new JPanel();
    private final JButton buttonDemanarRecomanacio = new JButton("Demanar Recomanació");
    private final JButton buttonCarregarRecomanacio = new JButton("Carregar Recomanació");
    private final JButton buttonGuardarRecomanacio = new JButton("Guardar Recomanació");
    private final JButton buttonEndarrere = new JButton("Tornar Enrere");

    //Altres atributs
    boolean recomanacioFeta = false;

    /**
     * Constructora de la vista
     * @param ctrlPresentacio referència al controlador de presentació
     */
    public VistaRecomanacio(CtrlPresentacio ctrlPresentacio)
    {
        controladorPresentacio = ctrlPresentacio;
        inicialitzarComponents();
    }

    //Mètodes públics
    /**
     * Dona valor al flag recomanació feta
     * @param b valor que es posarà al flag recomanació feta
     */
    public void setRecomanacioFeta(boolean b)
    {
        recomanacioFeta = b;
    }

    /**
     * Activa la vista
     */
    public void activar()
    {
        frameVista.setEnabled(true);
        frameVista.pack();
        frameVista.setVisible(true);
    }

    /**
     * Desactiva la vista
     */
    public void desactivar()
    {
        frameVista.setEnabled(false);
        frameVista.setVisible(false);
    }

    //Mètodes de les interficies Listener

    private void actionPerformedDemanarRecomanacio()
    {
        if (!controladorPresentacio.isCjtValoracionsBuit())
        {
            controladorPresentacio.activarVistaTriaRecomanacio();
        }

        else controladorPresentacio.mostraError("No has valorat cap item");
    }

    private void actionPerformedCarregarRecomanacio()
    {
        JFileChooser chooserRecomanacio = new JFileChooser();
        chooserRecomanacio.setDialogTitle("Carregar Recomanacio");
        chooserRecomanacio.setCurrentDirectory(new File(System.getProperty("user.dir")));
        int returnValue = chooserRecomanacio.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION)
        {
            File fitxerD = chooserRecomanacio.getSelectedFile();
            try
            {
                controladorPresentacio.carregarRecomanacio(fitxerD.getAbsolutePath());
                controladorPresentacio.activarVistaConsultarRecomanacio();
            }
            catch (IOException e) //no funciona
            {
                String missatge = String.valueOf(e);
                controladorPresentacio.mostraError(missatge);
            }
        }
    }

    private void actionPerformedGuardarRecomanacio()
    {
        if (recomanacioFeta)
        {
            JFileChooser chooserDataset = new JFileChooser();
            chooserDataset.setDialogTitle("Guardar Recomanacio");
            chooserDataset.setCurrentDirectory(new File(System.getProperty("user.dir")));
            int returnValue = chooserDataset.showSaveDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION)
            {
                File fitxerD = chooserDataset.getSelectedFile();
                try
                {
                    controladorPresentacio.guardarRecomanacio(fitxerD.getAbsolutePath());
                }
                catch (IOException e) //no funciona
                {
                    String missatge = String.valueOf(e);
                    controladorPresentacio.mostraError(missatge);
                }
            }
        }
        else
        {
            controladorPresentacio.mostraError("No hi ha cap Recomanació feta");
        }
    }

    private void actionPerformedTornarEnrere()
    {
        controladorPresentacio.activarVistaPrincipal();
    }

    //Assignacio de listeners
    private void assignarListenersComponents()
    {
        // Listeners pels botons

        buttonDemanarRecomanacio.addActionListener(event -> actionPerformedDemanarRecomanacio());

        buttonCarregarRecomanacio.addActionListener(event -> actionPerformedCarregarRecomanacio());

        buttonGuardarRecomanacio.addActionListener(event -> actionPerformedGuardarRecomanacio());

        buttonEndarrere.addActionListener(event -> actionPerformedTornarEnrere());
    }

    //Mètodes privats
    private void inicialitzarComponents()
    {
        inicialitzarFrameVista();
        inicialitzarPanelBotons();
        assignarListenersComponents();
    }

    private void inicialitzarFrameVista()
    {
        // Tamany
        frameVista.setMinimumSize(new Dimension(500, 400));
        frameVista.setPreferredSize(frameVista.getMinimumSize());
        frameVista.setResizable(true);

        // Posicio i operacions per defecte
        frameVista.setLocationRelativeTo(null);
        frameVista.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // S'agrega panelBotons al contentPane
        JPanel contentPane = (JPanel) frameVista.getContentPane();
        contentPane.add(panelBotons, BorderLayout.CENTER);
    }

    private void inicialitzarPanelBotons()
    {
        // Layout
        panelBotons.setBorder(BorderFactory.createEmptyBorder(30, 100, 30, 100));
        panelBotons.setLayout(new GridLayout(0, 1));

        // Botó Carregar Dataset
        buttonDemanarRecomanacio.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelBotons.add(buttonDemanarRecomanacio);

        panelBotons.add(Box.createRigidArea(new Dimension(0, 10)));

        buttonGuardarRecomanacio.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelBotons.add(buttonGuardarRecomanacio);
        panelBotons.add(Box.createRigidArea(new Dimension(0, 10)));

        // Botó Guardar Dataset
        buttonCarregarRecomanacio.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelBotons.add(buttonCarregarRecomanacio);

        panelBotons.add(Box.createRigidArea(new Dimension(0, 10)));

        // Botó Endarrere
        buttonEndarrere.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelBotons.add(buttonEndarrere);
    }
}