package presentacio;

import javax.swing.*;
import java.awt.*;

/**
 * Vista que permet a l'usuari seleccionar diferents opcions relacionades amb els items
 */
public class VistaItems extends Vista
{
    //Components de la interficie gràfica
    private final JFrame frameVista = new JFrame("Gestió Items");
    private final JPanel panelBotons = new JPanel();
    private final JButton buttonAfegirItem = new JButton("Afegir Item");
    private final JButton buttonEliminarItem = new JButton("Eliminar Item");
    private final JButton buttonValorarItem = new JButton("Valorar Item");
    private final JButton buttonEndarrere = new JButton("Tornar Enrere");

    /**
     * Creadora de la vista
     * @param ctrlPresentacio referència al controlador de presentació
     */
    public VistaItems(CtrlPresentacio ctrlPresentacio)
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
    private void actionPerformedAfegirItem()
    {
        controladorPresentacio.activarVistaAfegirItem();
    }

    private void actionPerformedEliminarItem()
    {
        controladorPresentacio.activarVistaEliminarItem();
    }

    private void actionPerformedValorarItem()
    {
        controladorPresentacio.activarVistaValorarItem();
    }

    private void actionPerformedTornarEnrere()
    {
        controladorPresentacio.activarVistaPrincipal();
    }

    //Assignacio de listeners
    private void assignarListenersComponents()
    {
        // Listeners pels botons

        buttonAfegirItem.addActionListener(event -> actionPerformedAfegirItem());

        buttonEliminarItem.addActionListener(event -> actionPerformedEliminarItem());

        buttonValorarItem.addActionListener(event -> actionPerformedValorarItem());

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

        // Botó Afegir Item
        buttonAfegirItem.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelBotons.add(buttonAfegirItem);

        panelBotons.add(Box.createRigidArea(new Dimension(0, 10)));

        // Botó Eliminar Item
        buttonEliminarItem.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelBotons.add(buttonEliminarItem);

        panelBotons.add(Box.createRigidArea(new Dimension(0, 10)));

        // Botó Valorar Item
        buttonValorarItem.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelBotons.add(buttonValorarItem);

        panelBotons.add(Box.createRigidArea(new Dimension(0, 10)));

        // Botó Endarrere
        buttonEndarrere.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelBotons.add(buttonEndarrere);
    }
}