package presentacio;

import javax.swing.*;
import java.awt.*;

public class VistaValorarItem
{
    /** Controlador de presentació **/
    private final CtrlPresentacio controladorPresentacio;

    /** Components de la interficie gràfica **/
    private final JFrame frameVista = new JFrame("Valorar Item");
    private final JMenuBar menu = new JMenuBar();
    private final JMenu menuAjuda = new JMenu("Ajuda");
    private final JMenuItem ajuda = new JMenuItem("Com valorar un item?");
    private final JLabel idLabel = new JLabel("Id Item: ");
    private final JLabel puntuacioLabel  = new JLabel("Puntuació: ");
    private JTextField itemId;
    private JTextField score;
    private final JPanel panelContinguts = new JPanel();
    private final JButton buttonValorarItem = new JButton("Valorar Item");
    private final JButton buttonEndarrere = new JButton("Tornar Enrere");

    public VistaValorarItem(CtrlPresentacio ctrlPresentacio)
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
        frameVista.setVisible(true);
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
        String titol = "Ajuda: Com valorar un item?";
        String missatge = "Per valorar un item has d'introduir l'id que l'identifica, la seva puntuació " +
                "i fer click a \"Valorar Item\".";
        controladorPresentacio.mostraMissatge(titol, missatge);
    }

    private void actionPerformedValorarItem()
    {
        String id = itemId.getText();
        String puntuacio = score.getText();

        if (itemId.getText().isBlank() || score.getText().isBlank())
            controladorPresentacio.mostraError("Per valorar un item introdueix l'id de l'item " +
                    "i la seva puntuació");
        else
        {
            controladorPresentacio.afegirValoracio(id, puntuacio);
            actualitzar();
        }
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

        buttonValorarItem.addActionListener(event -> actionPerformedValorarItem());

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
        frameVista.setMinimumSize(new Dimension(500, 400));
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

        // Border i Layout
        panelContinguts.setBorder(BorderFactory.createEmptyBorder(30, 100, 30, 100));
        panelContinguts.setLayout(new GridLayout(0, 1));
    }

    private void inicialitzarText()
    {
        itemId = new JTextField(20);
        score = new JTextField(20);

        idLabel.setLabelFor(itemId);
        panelContinguts.add(idLabel);
        panelContinguts.add(itemId);

        puntuacioLabel.setLabelFor(score);
        panelContinguts.add(puntuacioLabel);
        panelContinguts.add(score);

        panelContinguts.add(Box.createRigidArea(new Dimension(0, 20)));
    }

    private void inicialitzarPanelBotons()
    {
        // Botó Valorar Item
        buttonValorarItem.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelContinguts.add(buttonValorarItem);

        panelContinguts.add(Box.createRigidArea(new Dimension(0, 20)));

        // Botó Endarrere
        buttonEndarrere.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelContinguts.add(buttonEndarrere);
    }

    private void actualitzar()
    {
        itemId.setText("");
        score.setText("");
    }
}