package presentacio;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.*;

/**
 * Vista que permet a l'usuari triar el tipus
 * de recomanació que vol
 */
public class VistaTriaRecomanacio extends Vista
{
    enum tipusRecomanacio
    {
        COLLABORATIVE, CONTENT, HYBRID
    }

    //Components de la interficie gràfica
    private final JFrame frameVista = new JFrame("Tria Recomanacio");
    private final JMenuBar menu = new JMenuBar();
    private final JMenu menuAjuda = new JMenu("Ajuda");
    private final JMenuItem ajuda = new JMenuItem("Com triar una recomanació?");
    private final JPanel panelBotons = new JPanel();
    private final JButton buttonCollaborativeFiltering = new JButton("Collaborative filtering");
    private final JButton buttonContentBasedFiltering = new JButton("Content based filtering");
    private final JButton buttonHybrid = new JButton("Hybrid");
    private final JButton buttonEndarrere = new JButton("Tornar Enrere");

    /**
     * Creadora de la vista
     * @param ctrlPresentacio referència al controlador de presentació
     */
    public VistaTriaRecomanacio(CtrlPresentacio ctrlPresentacio)
    {
        controladorPresentacio = ctrlPresentacio;
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

    //Mètodes de les interficies Listener
    private void actionPerformedAjuda()
    {
        String titol = "Ajuda: Com triar una recomanació?";
        String missatge = "Per triar una recomanació escull l'algorisme que s'utilitzarà per realitzar la recomanació.";
        controladorPresentacio.mostraMissatge(titol, missatge);
    }

    private void actionPerformedCollaborative()
    {
        controladorPresentacio.setTipusRecomanacio(tipusRecomanacio.COLLABORATIVE);
    }

    private void actionPerformedContentBased()
    {
        controladorPresentacio.setTipusRecomanacio(tipusRecomanacio.CONTENT);
    }

    private void actionPerformedHybrid()
    {
        controladorPresentacio.setTipusRecomanacio(tipusRecomanacio.HYBRID);
    }

    private void actionPerformedTornarEnrere()
    {
        controladorPresentacio.activarVistaRecomanacio();
    }

    //Assignacio de listeners
    private void assignarListenersComponents()
    {
        ajuda.addActionListener(event -> actionPerformedAjuda());

        // Listeners pels botons

        buttonCollaborativeFiltering.addActionListener(event -> actionPerformedCollaborative());

        buttonContentBasedFiltering.addActionListener(event -> actionPerformedContentBased());

        buttonHybrid.addActionListener(event -> actionPerformedHybrid());

        buttonEndarrere.addActionListener(event -> actionPerformedTornarEnrere());
    }

    //Mètodes privats
    private void inicialitzarComponents()
    {
        inicialitzarFrameVista();
        inicialitzarMenu();
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

    private void inicialitzarMenu()
    {
        // Afegim menu Ajuda
        menuAjuda.add(ajuda);
        menu.add(menuAjuda);
        frameVista.setJMenuBar(menu);
    }

    private void inicialitzarPanelBotons()
    {
        // Layout
        panelBotons.setBorder(BorderFactory.createEmptyBorder(30, 100, 30, 100));
        panelBotons.setLayout(new GridLayout(0, 1));

        // Botó Carregar Dataset
        buttonCollaborativeFiltering.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelBotons.add(buttonCollaborativeFiltering);

        panelBotons.add(Box.createRigidArea(new Dimension(0, 10)));

        // Botó Guardar Dataset
        buttonContentBasedFiltering.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelBotons.add(buttonContentBasedFiltering);

        panelBotons.add(Box.createRigidArea(new Dimension(0, 10)));

        // Botó Consultar Dataset
        buttonHybrid.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelBotons.add(buttonHybrid);

        panelBotons.add(Box.createRigidArea(new Dimension(0, 10)));

        // Botó Endarrere
        buttonEndarrere.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelBotons.add(buttonEndarrere);
    }
}