package presentacio;

import javax.swing.*;
import java.awt.*;

/**
 * Vista principal de l'aplicació, que permet a l'usuari
 * escollir entre les diferents funcions principals
 */
public class VistaPrincipal extends Vista
{
    //Components de la interficie gràfica
    private final JFrame frameVista = new JFrame("Sistema Recomanador");
    private final JPanel panelBotons = new JPanel();
    private final JButton buttonGestioUsuaris = new JButton("Perfil Usuari");
    private final JButton buttonGestioDataset = new JButton("Gestió Dataset");
    private final JButton buttonGestioItems = new JButton("Gestió Items");
    private final JButton buttonGestioRecomanacions = new JButton("Gestió Recomanacions");
    private final JButton buttonGestioFiltres = new JButton("Gestió Filtres");

    //Altres atributs
    boolean sessioIniciada = false;
    boolean datasetCarregat = false;

    /**
     * Creadora de la vista
     * @param ctrlP referència al controlador de presentació
     */
    public VistaPrincipal(CtrlPresentacio ctrlP)
    {
        controladorPresentacio = ctrlP;
        inicialitzarComponents();
    }

    // Mètodes públics
    /**
     * Activa la flag sessió iniciada
     */
    public void setSessioIniciada()
    {
        sessioIniciada = true;
    }

    /**
     * Activa la flag dataset carregat
     */
    public void setDatasetCarregat(boolean b)
    {
        datasetCarregat = b;
    }

    /**
     * Retorna el flag del dataset carregat
     * @return boolea a cert si el dataset esta carregat o a fals en cas contrari
     */
    public boolean isDatasetCarregat()
    {
        return datasetCarregat;
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
    private void actionPerformedGestioUsuaris()
    {
        controladorPresentacio.activarVistaPerfilUsuari();
    }

    private void actionPerformedGestioDataset()
    {
        if (sessioIniciada)
        {
            controladorPresentacio.activarVistaDataset();
        }
        else
            controladorPresentacio.mostraError("No has iniciat sessió");
    }

    private void actionPerformedGestioItems()
    {
        if (sessioIniciada && datasetCarregat)
        {
            controladorPresentacio.activarVistaItems();
        }
        else if (sessioIniciada) controladorPresentacio.mostraError("No hi ha cap dataset carregat");
        else controladorPresentacio.mostraError("No has iniciat sessió");
    }

    private void actionPerformedGestioRecomanacio()
    {
        if (sessioIniciada && datasetCarregat)
        {
            controladorPresentacio.activarVistaRecomanacio();
        }
        else if (sessioIniciada) controladorPresentacio.mostraError("No hi ha cap dataset carregat");
        else controladorPresentacio.mostraError("No has iniciat sessió");
    }

    private void actionPerformedGestioFiltres()
    {
        if (sessioIniciada && datasetCarregat)
        {
            controladorPresentacio.activarVistaFiltres();
        }
        else if (sessioIniciada) controladorPresentacio.mostraError("No hi ha cap dataset carregat");
        else controladorPresentacio.mostraError("No has iniciat sessió");
    }

    //Assignacio de listeners
    private void assignarListenersComponents()
    {
        // Listeners pels botons

        buttonGestioUsuaris.addActionListener(event -> actionPerformedGestioUsuaris());

        buttonGestioDataset.addActionListener(event -> actionPerformedGestioDataset());

        buttonGestioItems.addActionListener(event -> actionPerformedGestioItems());

        buttonGestioRecomanacions.addActionListener(event -> actionPerformedGestioRecomanacio());

        buttonGestioFiltres.addActionListener(event -> actionPerformedGestioFiltres());
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

        // Botó Gestió Usuaris
        buttonGestioUsuaris.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelBotons.add(buttonGestioUsuaris);

        panelBotons.add(Box.createRigidArea(new Dimension(0, 10)));

        // Botó Gestió Dataset
        buttonGestioDataset.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelBotons.add(buttonGestioDataset);

        panelBotons.add(Box.createRigidArea(new Dimension(0, 10)));

        // Botó Gestió Items
        buttonGestioItems.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelBotons.add(buttonGestioItems);

        panelBotons.add(Box.createRigidArea(new Dimension(0, 10)));

        // Botó Gestió Recomanacions
        buttonGestioRecomanacions.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelBotons.add(buttonGestioRecomanacions);

        panelBotons.add(Box.createRigidArea(new Dimension(0, 10)));

        // Botó Gestió Filtres
        buttonGestioFiltres.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelBotons.add(buttonGestioFiltres);
    }
}