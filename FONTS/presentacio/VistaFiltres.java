package presentacio;

import javax.swing.*;
import java.awt.*;

/**
 * Vista que permet a l'usuari seleccionar diferents opcions relacionades amb els filtres
 */
public class VistaFiltres extends Vista
{
    //Components de la interficie gràfica
    private final JFrame frameVista = new JFrame("Gestió Filtres");
    private final JPanel panelBotons = new JPanel();
    private final JButton buttonCrearFiltre = new JButton("Crear Filtre");
    private final JButton buttonEliminarFiltre = new JButton("Eliminar Filtre");
    private final JButton buttonEndarrere = new JButton("Tornar Enrere");

    /**
     * Creadora de la vista
     * @param ctrlPresentacio referència al controlador de presentació
     */
    public VistaFiltres(CtrlPresentacio ctrlPresentacio)
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
    private void actionPerformedCrearFiltre()
    {
        controladorPresentacio.activarVistaCrearFiltre();
    }

    private void actionPerformedEliminarFiltre()
    {
        controladorPresentacio.activarVistaEliminarFiltre();
    }

    private void actionPerformedTornarEnrere()
    {
        controladorPresentacio.activarVistaPrincipal();
    }

    //Assignacio de listeners
    private void assignarListenersComponents()
    {
        // Listeners pels botons

        buttonCrearFiltre.addActionListener(event -> actionPerformedCrearFiltre());

        buttonEliminarFiltre.addActionListener(event -> actionPerformedEliminarFiltre());

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

        // Botó Crea Filtre
        buttonCrearFiltre.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelBotons.add(buttonCrearFiltre);

        panelBotons.add(Box.createRigidArea(new Dimension(0, 10)));

        // Botó Eliminar Filtre
        buttonEliminarFiltre.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelBotons.add(buttonEliminarFiltre);

        panelBotons.add(Box.createRigidArea(new Dimension(0, 10)));

        // Botó Endarrere
        buttonEndarrere.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelBotons.add(buttonEndarrere);
    }
}