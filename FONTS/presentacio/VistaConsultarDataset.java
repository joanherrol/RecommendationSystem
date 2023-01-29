package presentacio;

import presentacio.utilitats.*;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.table.JTableHeader;

import java.util.ArrayList;
import java.util.Arrays;

public class VistaConsultarDataset extends JFrame implements MouseListener
{
    /** Controlador de presentació **/
    private final CtrlPresentacio controladorPresentacio;

    /** Components de la interficie gràfica **/
    private final JFrame frameVista = new JFrame("Consultar Dataset");
    private final JMenuBar menu = new JMenuBar();
    private final JMenu menuAjuda = new JMenu("Ajuda");
    private final JMenuItem ajudaConsultar = new JMenuItem("Com consultar un item del dataset?");
    private final JMenuItem ajudaFiltrar = new JMenuItem("Com filtrar un dataset?");
    private final JPanel panelContinguts = new JPanel();
    private final JPanel panelTaula = new JPanel();
    private final JPanel panelBotons = new JPanel();
    private JScrollPane scrollPaneTabla = new JScrollPane();
    private final JTable taulaDataset = new JTable();
    private JComboBox<String> selectorFiltres = new JComboBox<>();
    private final JButton buttonFiltrar = new JButton("Filtrar Dataset");
    private final JButton buttonEndarrere = new JButton("Tornar Enrere");

    /** Altres atributs **/
    private final String fileName;
    private String[][] llistaItems;
    private int columnesTaula;


    /**
     *Constructora
     * @param ctrlPresentacio Referència al controlador presentació
     * @param nomFitxer nom del fitxer del dataset
     */
    public VistaConsultarDataset(CtrlPresentacio ctrlPresentacio, String nomFitxer)
    {
        controladorPresentacio = ctrlPresentacio;
        fileName = nomFitxer;
    }

    /**
     * Funció abstracta per activar la vista
     */
    public void activar()
    {
        frameVista.setEnabled(true);
        frameVista.pack();
        frameVista.setVisible(true);
        construirTaula(false);
        inicialitzarComponents();
        if (selectorFiltres.getItemCount() == 0) buttonFiltrar.setEnabled(false);
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
    private void actionPerformedAjudaConsultar()
    {
        String titol = "Ajuda: Com consultar un ítem del dataset?";
        String missatge = "Per consultar un ítem concret del dataset fes click a l'icona de la lupa de l'ítem.";
        controladorPresentacio.mostraMissatge(titol, missatge);
    }

    private void actionPerformedAjudaFiltrar()
    {
        String titol = "Ajuda: Com filtrar un dataset?";
        String missatge = "Per filtrar un dataset has de seleccionar al desplegable (a sota) el nom del filtre " +
                "que vols aplicar i fer click a \"Filtrar Dataset\". \n" +
                "Si no pots seleccionar-ne cap significa que no has creat cap filtre.";
        controladorPresentacio.mostraMissatge(titol, missatge);
    }

    private void actionPerformedFiltrarDataset()
    {
        String nomFiltre = selectorFiltres.getItemAt(selectorFiltres.getSelectedIndex());
        controladorPresentacio.filtrarDataset(nomFiltre);
        construirTaula(true);
    }

    private void actionPerformedTornarEnrere()
    {
        controladorPresentacio.activarVistaDataset();
    }

    /** Assignacio de listeners **/

    private void assignarListenersComponents()
    {
        ajudaConsultar.addActionListener(event -> actionPerformedAjudaConsultar());

        ajudaFiltrar.addActionListener(event -> actionPerformedAjudaFiltrar());

        // Listeners pels botons
        buttonFiltrar.addActionListener(event -> actionPerformedFiltrarDataset());

        buttonEndarrere.addActionListener(event -> actionPerformedTornarEnrere());
    }

    private void inicialitzarComponents()
    {
        inicialitzarFrameVista();
        inicialitzarPanelContinguts();
        inicialitzarPanelTaula();
        inicialitzarPanelBotons();
        assignarListenersComponents();
    }

    private void inicialitzarFrameVista()
    {
        // Tamany
        frameVista.setMinimumSize(new Dimension(700, 500));
        frameVista.setResizable(true);
        frameVista.setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Posicio i operacions per defecte
        frameVista.setLocationRelativeTo(null);
        frameVista.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Afegim menu Ajuda
        menuAjuda.add(ajudaConsultar);
        menuAjuda.add(ajudaFiltrar);
        menu.add(menuAjuda);
        frameVista.setJMenuBar(menu);

        // S'agrega contentPane al contentPane
        JPanel contentPane = (JPanel) frameVista.getContentPane();
        contentPane.add(panelContinguts, BorderLayout.CENTER);
    }

    private void inicialitzarPanelContinguts()
    {
        // Layout
        panelContinguts.setLayout(new BorderLayout());

        // Paneles
        panelContinguts.add(panelTaula, BorderLayout.CENTER);
        panelContinguts.add(panelBotons, BorderLayout.SOUTH);
    }

    private void inicialitzarPanelTaula()
    {
        // Panel
        panelTaula.setBorder(new EmptyBorder(5, 5, 5, 5));
        panelTaula.setLayout(new BorderLayout());

        // Titol taula
        JLabel lbltaulaDataset = new JLabel("Taula: " + fileName + "\n");
        lbltaulaDataset.setFont(new Font("Verdana", Font.BOLD, 25));
        panelTaula.add(lbltaulaDataset, BorderLayout.NORTH);

        // Afegim scroll
        scrollPaneTabla = new JScrollPane();
        panelTaula.add(scrollPaneTabla);

        // Creem taula
        taulaDataset.setBackground(Color.WHITE);
        taulaDataset.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
        taulaDataset.addMouseListener(this);
        taulaDataset.setOpaque(false);
        scrollPaneTabla.setViewportView(taulaDataset);
    }

    private void inicialitzarPanelBotons()
    {
        // Layout
        panelBotons.setBorder(BorderFactory.createEmptyBorder(30, 100, 30, 100));
        panelBotons.setLayout(new GridLayout(0, 2));

        // Afegim els noms dels conjunts de filtres de l'usuari al selector filtres
        selectorFiltres = new JComboBox<>();
        for (String nomCjtFiltres : controladorPresentacio.getNomsFiltres())
            selectorFiltres.addItem(nomCjtFiltres);
        selectorFiltres.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelBotons.add(selectorFiltres);

        // Afegim botó filtrar
        buttonFiltrar.setAlignmentX(Component.RIGHT_ALIGNMENT);
        panelBotons.add(buttonFiltrar);

        panelBotons.add(Box.createRigidArea(new Dimension(0, 10)));

        panelBotons.add(Box.createRigidArea(new Dimension(0, 10)));

        // Afegim botó Endarrere
        buttonEndarrere.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelBotons.add(buttonEndarrere);
    }

    /**
     * Mètode que permet construir la taula de items
     * es creen primer les columnes i després s'assigna la informació
     */
    private void construirTaula(boolean datasetFiltrat)
    {
        if (datasetFiltrat)
            llistaItems = controladorPresentacio.getAllItemsFiltrat();
        else
            llistaItems = controladorPresentacio.getAllItems();

        ArrayList<String> titolsList = new ArrayList<>(Arrays.asList(controladorPresentacio.getHeaderDataset()));
        titolsList.add(" ");   //icono Lupa (columna extra, no inclosa a la header del dataset)

        //s'assignen les columnes a l'arranjament per enviar-se al moment de construir la taula
        String[] titols = new String[titolsList.size()];
        for (int i = 0; i < titols.length; i++)
        {
            titols[i] = titolsList.get(i);
        }

        /* obtenim les dades de la llista i les guardem a la matriu
         * que després s'envia a construir la taula
         */
        Object[][] data = obtenirMatriuDades(titolsList);
        construirTaula(titols,data);
    }

    /**
     * Omple la informació de la taula usant la llista d'items treballada
     * anteriorment, guardant-la en una matriu que es retorna amb tota
     * la informació per després ser assignada al model
     */
    private Object[][] obtenirMatriuDades(ArrayList<String> titulosList) {

        /* es crea la matriu on les files i les columnes són dinàmiques
         * ja que corresponen a tots els items i la capçalera del dataset
         */
        String[][] informacio = new String[llistaItems.length][titulosList.size()];

        for (int i = 0; i < informacio.length; i++)
        {
            for (int j = 0; j < informacio[0].length; j++)
            {
                if (j != (informacio[0].length-1))
                    informacio[i][j] = llistaItems[i][j]+ "";
                else
                    informacio[i][j] = "LUPA"; //paraula clau per assignar l'icono de la Lupa a la classe GestioCeles
            }
        }
        return informacio;
    }

    /**
     * Amb els títols i la informació a mostrar es crea el model per
     * poder personalitzar la taula, assignant mida de cel·les tant en amplada com en alt
     * així com els tipus de dades que podrà suportar.
     */
    private void construirTaula(String[] titulos, Object[][] data)
    {
        //model definit a la classe ModelTaula
        ModelTaula model = new ModelTaula(data, titulos);
        //s'assigna el model a la taula
        taulaDataset.setModel(model);

        columnesTaula = taulaDataset.getColumnCount();

        //s'assigna el tipus de dada que tindran les cel·les de cada columna definida respectivament per validar-ne la personalització
        for (int i = 0; i < titulos.length-1; i++)
        {
            taulaDataset.getColumnModel().getColumn(i).setCellRenderer(new GestioCeles("text"));
        }
        taulaDataset.getColumnModel().getColumn(titulos.length-1).setCellRenderer(new GestioCeles("icono"));
        taulaDataset.getColumnModel().getColumn(titulos.length-1).setPreferredWidth(30); //tamany columna icono

        taulaDataset.getTableHeader().setReorderingAllowed(false);
        taulaDataset.setRowHeight(25); //mida de les cel·les
        taulaDataset.setGridColor(new java.awt.Color(0, 0, 0));

        //personalitza la capçalera
        JTableHeader jtableHeader = taulaDataset.getTableHeader();
        jtableHeader.setDefaultRenderer(new GestioCapcaleraTaula());
        taulaDataset.setTableHeader(jtableHeader);

        //s'assigna la taula a l'scrollPane
        scrollPaneTabla.setViewportView(taulaDataset);
    }

    @Override
    public void mouseClicked(MouseEvent e)
    {
        //capturo fila i columna
        int fila = taulaDataset.rowAtPoint(e.getPoint());
        int columna = taulaDataset.columnAtPoint(e.getPoint());

        /* utilitzem la columna per validar si correspon a la columna de Lupa garantint
         * que només es produeixi alguna cosa si seleccioneu una fila d'aquesta columna
         */
        if (columna == (columnesTaula-1))   //si estem a la columna de la Lupa (l'última)
        {
            //sabent que correspon a la columna de Lupa, envio la posició de la fila seleccionada
            validarSeleccioMouse(fila);
        }
    }

    /**
     * Aquest mètode simula el procés o l'acció que es vol fer si
     * es pressiona l'icono de la Lupa de la taula
     */
    private void validarSeleccioMouse(int fila)
    {
        //tenint la fila llavors s'obté l'objecte corresponent per imprimir la informació
        StringBuilder info = new StringBuilder("");

        for (int j = 0; j < columnesTaula-1; j++)
        {
            String nomAtribut = taulaDataset.getColumnModel().getColumn(j).getHeaderValue().toString();
            String valorAtribut = taulaDataset.getValueAt(fila, j).toString();
            info.append(nomAtribut).append(": ").append(valorAtribut).append("\n\n");
        }
        mostrarItemLupa(info.toString());
    }

    private void mostrarItemLupa(String info)
    {
        JTextArea textArea = new JTextArea(info);
        JScrollPane scrollPane = new JScrollPane(textArea);
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        scrollPane.setPreferredSize(new Dimension(500, 500));
        JOptionPane.showMessageDialog(null, scrollPane, "Info item", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void mouseEntered(MouseEvent arg0)
    {
        // TODO Auto-generated method stub
    }

    @Override
    public void mouseExited(MouseEvent arg0)
    {
        // TODO Auto-generated method stub
    }

    @Override
    public void mousePressed(MouseEvent arg0)
    {
        // TODO Auto-generated method stub
    }

    @Override
    public void mouseReleased(MouseEvent arg0)
    {
        // TODO Auto-generated method stub
    }
}