package presentacio;

import javax.swing.*;
import java.awt.*;

public class VistaEliminarItem
{
    /** Controlador de presentació **/
    private final CtrlPresentacio controladorPresentacio;

    /** Components de la interficie gràfica **/
    private final JFrame frameVista = new JFrame("Eliminar Item");
    private final JMenuBar menu = new JMenuBar();
    private final JMenu menuAjuda = new JMenu("Ajuda");
    private final JMenuItem ajuda = new JMenuItem("Com eliminar un item?");
    private final JLabel idLabel = new JLabel("Id Item: ");
    private JTextField itemId;
    private final JPanel panelContinguts = new JPanel();
    private final JButton buttonEliminarItem = new JButton("Eliminar Item");
    private final JButton buttonEndarrere = new JButton("Tornar Enrere");

    /** Altres atributs **/

    public VistaEliminarItem(CtrlPresentacio ctrlPresentacio)
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
        frameVista.setVisible(true);
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
        String titol = "Ajuda: Com eliminar un item?";
        String missatge = "Per eliminar un item has d'introduir l'id que l'identifica i fer click a \"Eliminar Item\".";
        controladorPresentacio.mostraMissatge(titol, missatge);
    }

    private void actionPerformedEliminarItem()
    {
        String id = itemId.getText();

        if (itemId.getText().isBlank())
            controladorPresentacio.mostraError("Per eliminar un item introdueix el seu identificador");
        else
        {
            controladorPresentacio.eliminarItem(id);
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

        buttonEliminarItem.addActionListener(event -> actionPerformedEliminarItem());

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
        idLabel.setLabelFor(itemId);
        panelContinguts.add(idLabel);
        panelContinguts.add(itemId);

        panelContinguts.add(Box.createRigidArea(new Dimension(0, 20)));
    }

    private void inicialitzarPanelBotons()
    {
        // Botó Eliminar Item
        buttonEliminarItem.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelContinguts.add(buttonEliminarItem);

        panelContinguts.add(Box.createRigidArea(new Dimension(0, 20)));

        // Botó Endarrere
        buttonEndarrere.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelContinguts.add(buttonEndarrere);
    }

    private void actualitzar()
    {
        itemId.setText("");
    }
}