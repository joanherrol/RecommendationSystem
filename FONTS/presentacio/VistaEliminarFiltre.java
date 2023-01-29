package presentacio;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class VistaEliminarFiltre
{
    /** Controlador de presentació **/
    private final CtrlPresentacio controladorPresentacio;

    /** Components de la interficie gràfica **/
    private final JFrame frameVista = new JFrame("Eliminar Filtre");
    private final JMenuBar menu = new JMenuBar();
    private final JMenu menuAjuda = new JMenu("Ajuda");
    private final JMenuItem ajuda = new JMenuItem("Com eliminar un filtre?");
    private final JLabel nomFiltreLabel = new JLabel("Nom del filtre: ");
    private JComboBox nomFiltre;
    private final JPanel panelContinguts = new JPanel();
    private final JPanel panelText = new JPanel();
    private final JPanel panelBotons = new JPanel();
    private final JButton buttonEliminarFiltre = new JButton("Eliminar Filtre");
    private final JButton buttonEndarrere = new JButton("Tornar Enrere");

    /**
     * Constructora
     * @param ctrlPresentacio
     */
    public VistaEliminarFiltre(CtrlPresentacio ctrlPresentacio)
    {
        controladorPresentacio = ctrlPresentacio;
        inicialitzarComponents();
    }

    /** Mètodes públics **/
    public void activar()
    {
        frameVista.setEnabled(true);
        frameVista.pack();
        inicialitzarFrameVista();
        actualitzar();
    }

    /**
     * Desactiva la vista
     */
    public void desactivar()
    {
        frameVista.setEnabled(false);
        frameVista.setVisible(false);
    }

    /** Mètodes privats **/

    /** Mètodes de les interficies Listener **/
    private void actionPerformedAjuda()
    {
        String titol = "Ajuda: Com eliminar un filtre?";
        String missatge = "Per eliminar un filtre has de seleccionar el nom que l'identifica i fer click a \"Eliminar Filtre\". \n" +
                "Si no pots seleccionar-ne cap significa que no has creat cap filtre.";
        controladorPresentacio.mostraMissatge(titol, missatge);
    }

    private void actionPerformedEliminarFiltre()
    {
        controladorPresentacio.eliminarFiltre(nomFiltre.getSelectedItem().toString());
        actualitzar();
        try
        {
            controladorPresentacio.guardarFiltres("GestioFiltres.csv");
        }
        catch (IOException e)
        {
            controladorPresentacio.mostraError("Error al guardar el fitxer GestioFiltres.csv");
        }
    }

    private void actionPerformedTornarEnrere()
    {
        controladorPresentacio.activarVistaFiltres();
    }

    /** Assignacio de listeners **/

    private void assignarListenersComponents()
    {
        ajuda.addActionListener(event -> actionPerformedAjuda());

        buttonEliminarFiltre.addActionListener(event -> actionPerformedEliminarFiltre());

        buttonEndarrere.addActionListener(event -> actionPerformedTornarEnrere());
    }

    private void inicialitzarComponents()
    {
        inicialitzarFrameVista();
        inicialitzarMenu();
        inicialitzarPanelContinguts();
        inicialitzarText();
        inicialitzarPanelBotons();
        assignarListenersComponents();
    }

    private void inicialitzarFrameVista()
    {
        // Tamany
        frameVista.setMinimumSize(new Dimension(500, 450));
        frameVista.setPreferredSize(frameVista.getMinimumSize());
        frameVista.setResizable(true);

        // Posicio i operacions per defecte
        frameVista.setLocationRelativeTo(null);
        frameVista.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void inicialitzarMenu()
    {
        // Afegim menu Ajuda
        menuAjuda.add(ajuda);
        menu.add(menuAjuda);
        frameVista.setJMenuBar(menu);
    }

    private void inicialitzarPanelContinguts()
    {
        // S'agrega panelContinguts al contentPane
        JPanel contentPane = (JPanel) frameVista.getContentPane();
        contentPane.add(panelContinguts, BorderLayout.CENTER);

        // Layout
        panelContinguts.setLayout(new BorderLayout());

        // Panels
        panelContinguts.add(panelBotons, BorderLayout.SOUTH);
        panelContinguts.add(panelText, BorderLayout.CENTER);
    }

    private void inicialitzarPanelBotons()
    {
        // Layout
        panelBotons.setBorder(BorderFactory.createEmptyBorder(30, 100, 30, 100));
        panelBotons.setLayout(new GridLayout(0, 1));

        // Botó Eliminar Filtres
        buttonEliminarFiltre.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelBotons.add(buttonEliminarFiltre);

        panelBotons.add(Box.createRigidArea(new Dimension(0, 20)));

        // Botó Endarrere
        buttonEndarrere.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelBotons.add(buttonEndarrere);
    }

    private void inicialitzarText()
    {
        panelText.setBorder(BorderFactory.createEmptyBorder(30, 100, 30, 100));
        panelText.setLayout(new GridLayout(0, 1));

        panelText.add(nomFiltreLabel);

        nomFiltre = new JComboBox(controladorPresentacio.getNomsFiltres());
        panelText.add(nomFiltre);

        buttonEliminarFiltre.setEnabled(nomFiltre.getItemCount() != 0);
    }

    private void actualitzar()
    {
        panelText.removeAll();
        inicialitzarText();
        frameVista.setVisible(true);
    }
}