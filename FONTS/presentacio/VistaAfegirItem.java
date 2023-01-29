package presentacio;

import javax.swing.*;
import java.awt.*;

public class VistaAfegirItem
{
    /** Controlador de presentació **/
    private final CtrlPresentacio controladorPresentacio;

    /** Components de la interficie gràfica **/
    private final JFrame frameVista = new JFrame("Afegir Item");
    private final JMenuBar menu = new JMenuBar();
    private final JMenu menuAjuda = new JMenu("Ajuda");
    private final JMenuItem ajuda = new JMenuItem("Com afegir un item?");
    private JLabel[] nomAtributs;
    private JTextField[] valorAtributs;
    private final JPanel panelContinguts = new JPanel();
    private final JPanel panelText = new JPanel();
    private final JPanel panelBotons = new JPanel();
    private final JButton buttonAfegirItem = new JButton("Afegir Item");
    private final JButton buttonEndarrere = new JButton("Tornar Enrere");
    private JScrollPane scrollPane = new JScrollPane();


    /**
     * Constructora
     * @param ctrlPresentacio Referència al controlador presentació
     */
    public VistaAfegirItem(CtrlPresentacio ctrlPresentacio)
    {
        controladorPresentacio = ctrlPresentacio;
        inicialitzarComponents();
    }

    /**
     * Funció abstracta per activar la vista
     */
    public void activar()
    {
        frameVista.setEnabled(true);
        frameVista.pack();
        inicialitzarFrameVista();
        actualitzar();
    }

    /**
     * Funció abstracta per desactivar la vista
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
        String titol = "Ajuda: Com afegir un item?";
        String missatge = "Per afegir un item has d'introduir els valors dels atributs que es mostren " +
                "i fer click a \"Afegir Item\". \n \n" +
                "Si vols afegir més d'un valor en una casella, separal's per ; de la manera següent: \n" +
                "Exemple: projecte;PROP;recomanador";
        controladorPresentacio.mostraMissatge(titol, missatge);
    }

    private void actionPerformedAfegirItem()
    {
        String id = "";
        StringBuilder dadesItem = new StringBuilder();
        boolean first = true;

        for (int i = 0; i < nomAtributs.length; i++)
        {
            if (nomAtributs[i].getText().equals("id"))
            {
                if (valorAtributs[i].getText().isBlank())
                {
                    String missatge = "Per afegir un item introdueix l'id de l'item i els seus atributs";
                    controladorPresentacio.mostraError(missatge);
                    return;
                }
                else id = valorAtributs[i].getText();
            }
            if (!first)
                dadesItem.append(",");
            if (i == (nomAtributs.length - 1) && valorAtributs[i].getText().isBlank())
                dadesItem.append(" ");
            else
                dadesItem.append(valorAtributs[i].getText());
            if (first)
                first = false;
        }

        if (controladorPresentacio.afegirItem(id, dadesItem.toString()))
            actualitzar();
    }

    private void actionPerformedTornarEnrere()
    {
        controladorPresentacio.activarVistaItems();
    }

    /** Assignacio de listeners **/

    private void assignarListenersComponents()
    {
        ajuda.addActionListener(event -> actionPerformedAjuda());

        // Listeners pels botons

        buttonAfegirItem.addActionListener(event -> actionPerformedAfegirItem());

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
        frameVista.setMinimumSize(new Dimension(650, 650));
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
        // S'agrega panelBotons al contentPane
        JPanel contentPane = (JPanel) frameVista.getContentPane();
        contentPane.add(panelContinguts, BorderLayout.CENTER);

        // Layout
        panelContinguts.setLayout(new BorderLayout());

        // Panels
        panelContinguts.add(panelBotons, BorderLayout.SOUTH);
        panelContinguts.add(panelText, BorderLayout.CENTER);

        // Scroll
        scrollPane = new JScrollPane(panelText);
        scrollPane.setPreferredSize(new Dimension(500, 500));
        panelContinguts.add(scrollPane);
    }

    private void inicialitzarText()
    {
        String[] header = controladorPresentacio.getHeaderDataset();
        int nAtributs = header.length;

        nomAtributs = new JLabel[nAtributs];
        valorAtributs = new JTextField[nAtributs];

        panelText.setBorder(BorderFactory.createEmptyBorder(30, 100, 30, 100));
        panelText.setLayout(new GridLayout(0, 2));

        for (int i = 0; i < nAtributs; i++)
        {
            String nomA = header[i];
            nomAtributs[i] = new JLabel(nomA);
            valorAtributs[i] = new JTextField(20);
            nomAtributs[i].setLabelFor(valorAtributs[i]);

            panelText.add(nomAtributs[i]);
            panelText.add(valorAtributs[i]);
        }
    }

    private void inicialitzarPanelBotons()
    {
        // Layout
        panelBotons.setBorder(BorderFactory.createEmptyBorder(30, 100, 30, 100));
        panelBotons.setLayout(new GridLayout(0, 1));

        // Botó Eliminar Item
        buttonAfegirItem.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelBotons.add(buttonAfegirItem);

        panelBotons.add(Box.createRigidArea(new Dimension(0, 20)));

        // Botó Endarrere
        buttonEndarrere.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelBotons.add(buttonEndarrere);
    }

    private void actualitzar()
    {
        panelText.removeAll();
        inicialitzarText();
        frameVista.setVisible(true);
    }
}