package presentacio;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;

import java.io.File;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Vista que permet seleccionar diferents opcions relacionades amb el dataset
 */
public class VistaDataset extends Vista
{
    /** Components de la interficie gràfica **/
    private final JFrame frameVista = new JFrame("Gestió Dataset");
    private final JPanel panelBotons = new JPanel();
    private final JButton buttonCarregarDataset = new JButton("Carregar Dataset");
    private final JButton buttonGuardarDataset = new JButton("Guardar Dataset");
    private final JButton buttonConsultarDataset = new JButton("Consultar Dataset");
    private final JButton buttonEndarrere = new JButton("Tornar Enrere");

    /** Altres atributs **/
    boolean datasetCarregat;
    boolean tester;
    String nomFitxer;

    /** Constructora **/
    public VistaDataset(CtrlPresentacio ctrlPresentacio)
    {
        controladorPresentacio = ctrlPresentacio;
        datasetCarregat = false;
        inicialitzarComponents();
    }

    //Mètodes públics

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

    /**
     * Dona valor a la flag tester
     * @param b valor que es posarà a la flag tester
     */
    public void setTester(boolean b)
    {
        tester = b;
    }

    public void setDatasetCarregat(boolean datasetCarregat)
    {
        this.datasetCarregat = datasetCarregat;
    }

    //Mètodes de les interficies Listener **/
    private void actionPerformedCarregarDataset()
    {
        JFileChooser chooserDataset = new JFileChooser();
        chooserDataset.setDialogTitle("Carregar Dataset");
        chooserDataset.setFileSelectionMode(JFileChooser.FILES_ONLY);

        chooserDataset.removeChoosableFileFilter(chooserDataset.getFileFilter());
        FileNameExtensionFilter extensions = new FileNameExtensionFilter("csv, txt", "csv", "txt");
        chooserDataset.setFileFilter(extensions);

        chooserDataset.setCurrentDirectory(new File(System.getProperty("user.dir")));
        int returnValue = chooserDataset.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION)
        {
            File fitxerD = chooserDataset.getSelectedFile();
            try
            {
                controladorPresentacio.carregarDataset(fitxerD.getAbsolutePath());
                nomFitxer = fitxerD.getName();
            }
            catch (IOException e)
            {
                controladorPresentacio.mostraError("Error al carregar el dataset");
            }
        }
        else
        {
            return;
        }

        if (controladorPresentacio.isDatasetCarregat())
        {
            JFileChooser chooserValoracions = new JFileChooser();
            chooserValoracions.setDialogTitle("Carregar Valoracions");
            chooserDataset.setFileSelectionMode(JFileChooser.FILES_ONLY);

            chooserDataset.removeChoosableFileFilter(chooserDataset.getFileFilter());
            chooserDataset.setFileFilter(extensions);

            chooserValoracions.setCurrentDirectory(new File(System.getProperty("user.dir")));
            int returnValue2 = chooserValoracions.showOpenDialog(null);
            if (returnValue2 == JFileChooser.APPROVE_OPTION)
            {
                File fitxerV = chooserValoracions.getSelectedFile();
                try
                {
                    controladorPresentacio.carregarValoracions(fitxerV.getAbsolutePath());
                    controladorPresentacio.setDatasetCarregat(true);
                }
                catch (IOException e)
                {
                    controladorPresentacio.mostraError("Error al carregar les valoracions");
                }
            }
            else
            {
                controladorPresentacio.setDatasetCarregat(false);
                return;
            }

            if (tester)
            {
                JFileChooser chooserTesters = new JFileChooser();
                chooserTesters.setDialogTitle("CarregarValoracionsTesters (ratings.test.unknown.csv)");
                chooserTesters.setCurrentDirectory(new File(System.getProperty("user.dir")));
                int returnValue3 = chooserTesters.showOpenDialog(null);
                if (returnValue3 == JFileChooser.APPROVE_OPTION)
                {
                    File fitxerV = chooserTesters.getSelectedFile();
                    try
                    {
                        controladorPresentacio.carregarValoracionsTesters(fitxerV.getAbsolutePath());
                        controladorPresentacio.setDatasetCarregat(true);
                    }
                    catch (IOException e)
                    {
                        controladorPresentacio.mostraError("Error al carregar les valoracions tester");
                    }
                }
                else
                {
                    controladorPresentacio.setDatasetCarregat(false);
                }
            }
        }
    }

    private void actionPerformedGuardarDataset()
    {
        if (datasetCarregat)
        {
            JFileChooser chooserDataset = new JFileChooser();
            chooserDataset.setDialogTitle("Guardar Dataset");
            chooserDataset.setCurrentDirectory(new File(System.getProperty("user.dir")));
            int returnValue = chooserDataset.showSaveDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION)
            {
                File fitxerD = chooserDataset.getSelectedFile();
                try
                {
                    controladorPresentacio.guardarDataset(fitxerD.getAbsolutePath());
                }
                catch (IOException e)
                {
                    controladorPresentacio.mostraError("Error al guardar el dataset");
                }
            }
        }
        else
        {
            controladorPresentacio.mostraError("No hi ha cap dataset carregat");
        }
    }

    private void actionPerformedConsultarDataset()
    {
        if (datasetCarregat)
        {
            controladorPresentacio.activarVistaConsultarDataset(nomFitxer);
        }
        else
        {
            controladorPresentacio.mostraError("No hi ha cap dataset carregat");
        }
    }

    private void actionPerformedTornarEnrere()
    {
        controladorPresentacio.activarVistaPrincipal();
    }

    /** Assignacio de listeners **/

    private void assignarListenersComponents()
    {
        // Listeners pels botons

        buttonCarregarDataset.addActionListener(event -> actionPerformedCarregarDataset());

        buttonGuardarDataset.addActionListener(event -> actionPerformedGuardarDataset());

        buttonConsultarDataset.addActionListener(event -> actionPerformedConsultarDataset());

        buttonEndarrere.addActionListener(event -> actionPerformedTornarEnrere());
    }

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
        buttonCarregarDataset.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelBotons.add(buttonCarregarDataset);

        panelBotons.add(Box.createRigidArea(new Dimension(0, 10)));

        // Botó Guardar Dataset
        buttonGuardarDataset.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelBotons.add(buttonGuardarDataset);

        panelBotons.add(Box.createRigidArea(new Dimension(0, 10)));

        // Botó Consultar Dataset
        buttonConsultarDataset.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelBotons.add(buttonConsultarDataset);

        panelBotons.add(Box.createRigidArea(new Dimension(0, 10)));

        // Botó Endarrere
        buttonEndarrere.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelBotons.add(buttonEndarrere);
    }
}