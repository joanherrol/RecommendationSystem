package presentacio;

import presentacio.utilitats.CheckableItem;
import presentacio.utilitats.CheckedComboBox;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Set;
import java.util.Vector;

public class VistaCrearFiltre
{
    /** Controlador de presentació **/
    private final CtrlPresentacio controladorPresentacio;

    /** Components de la interficie gràfica **/
    private final JFrame frameVista = new JFrame("Crear Filtre");
    private final JMenuBar menu = new JMenuBar();
    private final JMenu menuAjuda = new JMenu("Ajuda");
    private final JMenuItem ajuda = new JMenuItem("Com crear un filtre?");
    private final JPanel panelContinguts = new JPanel();
    private final JPanel panelText = new JPanel();
    private final JPanel panelFiltres = new JPanel();
    private JTextField nomFiltre;
    private JLabel[] nomsAtributs;
    private CheckedComboBox<CheckableItem>[] valorsAtributs;
    private final JPanel panelBotons = new JPanel();
    private final JButton buttonCrearFiltre = new JButton("Crear Filtre");
    private final JButton buttonEndarrere = new JButton("Tornar Enrere");
    private JScrollPane scrollPane = new JScrollPane();


    /**
     * Constructora
     * @param ctrlPresentacio
     */
    public VistaCrearFiltre(CtrlPresentacio ctrlPresentacio)
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
        String titol = "Ajuda: Com crear un filtre?";
        String missatge = "Per crear un filtre has d'introduir el nom que l'identificarà, " +
                "seleccionar els atributs per filtrar el dataset carregat i fer click a \"Crear Filtre\".";
        controladorPresentacio.mostraMissatge(titol, missatge);
    }

    private void actionPerformedCrearFiltre()
    {
        if (nomFiltre.getText().isBlank())
            controladorPresentacio.mostraError("Per crear un nou filtre introdueix el nom que l'identificarà");
        else
        {
            Vector<Vector<String>> filtres = new Vector<>();
            Vector<String> atributsFiltre = new Vector<>();
            for (int i = 0; i < nomsAtributs.length; i++)
            {
                for (int j = 0; j <= valorsAtributs[i].getItemCount(); j++)
                {
                    if (j == 0)
                    {
                        // Guardem el nom d'atribut a la columna 0 de la fila i
                        atributsFiltre.add(j, nomsAtributs[i].getText());
                    }
                    else
                    {
                        if (valorsAtributs[i].getItemAt(j-1).isSelected())
                        {
                            // Guardem el valor d'atribut SELECCIONAT a la columna j+1 de la fila i
                            atributsFiltre.add(valorsAtributs[i].getItemAt(j - 1).toString());
                        }
                    }
                }
                if (atributsFiltre.size() > 1)
                    filtres.add(atributsFiltre);
                atributsFiltre = new Vector<>();
            }

            if (filtres.size() == 0)
                controladorPresentacio.mostraError("Per crear un nou filtre selecciona els atributs del filtre");
            else
            {
                controladorPresentacio.crearFiltre(nomFiltre.getText(), filtres);
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

        buttonCrearFiltre.addActionListener(event -> actionPerformedCrearFiltre());

        buttonEndarrere.addActionListener(event -> actionPerformedTornarEnrere());
    }

    private void inicialitzarComponents()
    {
        inicialitzarFrameVista();
        inicialitzarMenu();
        inicialitzarPanelContinguts();
        inicialitzarPanelText();
        inicialitzarPanelFiltres();
        inicialitzarPanelBotons();
        assignarListenersComponents();
    }

    private void inicialitzarFrameVista()
    {
        // Tamany
        frameVista.setMinimumSize(new Dimension(1000, 800));
        frameVista.setResizable(true);
        frameVista.setExtendedState(JFrame.MAXIMIZED_BOTH);

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
        panelContinguts.add(panelText, BorderLayout.NORTH);
        panelContinguts.add(panelFiltres, BorderLayout.CENTER);
        panelContinguts.add(panelBotons, BorderLayout.SOUTH);

        // Scroll
        scrollPane = new JScrollPane(panelFiltres);
        scrollPane.setPreferredSize(new Dimension(500, 500));
        panelContinguts.add(scrollPane);
    }

    private void inicialitzarPanelText()
    {
        panelText.setBorder(BorderFactory.createEmptyBorder(30, 100, 30, 100));
        panelText.setLayout(new GridLayout(0, 2));

        JLabel nomFiltreLabel = new JLabel("Nom del filtre: ");
        nomFiltre = new JTextField(20);

        panelText.add(nomFiltreLabel);
        panelText.add(nomFiltre);
    }

    private void inicialitzarPanelFiltres()
    {
        panelFiltres.setBorder(BorderFactory.createEmptyBorder(30, 100, 30, 100));
        panelFiltres.setLayout(new GridLayout(0, 2));

        String[] header = controladorPresentacio.getHeaderDataset();
        int nAtributs = header.length;
        nomsAtributs = new JLabel[nAtributs];
        valorsAtributs = new CheckedComboBox[nAtributs];

        for (int i = 0; i < nAtributs; i++)
        {
            String nomAtribut = header[i];
            nomsAtributs[i] = new JLabel(nomAtribut);
            nomsAtributs[i].setAlignmentX(Component.CENTER_ALIGNMENT);
            panelFiltres.add(nomsAtributs[i]);

            Set<String> llistaValorsAtributs = controladorPresentacio.getValorsAtributs(nomAtribut);
            valorsAtributs[i] = new CheckedComboBox<>(makeModel(llistaValorsAtributs));

            valorsAtributs[i].setEnabled(llistaValorsAtributs.size() > 0);

            panelFiltres.add(valorsAtributs[i]);
        }
    }

    private static ComboBoxModel<CheckableItem> makeModel(Set<String> llistaValorsAtributs)
    {
        CheckableItem[] valorsAtribut = new CheckableItem[llistaValorsAtributs.size()];
        int i = 0;
        for (String valorAtribut : llistaValorsAtributs)
        {
            valorsAtribut[i] = new CheckableItem(valorAtribut, false);
            i++;
        }
        return new DefaultComboBoxModel<>(valorsAtribut);
    }

    private void inicialitzarPanelBotons()
    {
        // Layout
        panelBotons.setBorder(BorderFactory.createEmptyBorder(30, 100, 30, 100));
        panelBotons.setLayout(new GridLayout(0, 1));

        // Botó Crear Filtre
        buttonCrearFiltre.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelBotons.add(buttonCrearFiltre);

        panelBotons.add(Box.createRigidArea(new Dimension(0, 20)));

        // Botó Endarrere
        buttonEndarrere.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelBotons.add(buttonEndarrere);
    }

    private void actualitzar()
    {
        nomFiltre.setText("");
        panelFiltres.removeAll();
        inicialitzarPanelFiltres();
        frameVista.setVisible(true);
    }
}