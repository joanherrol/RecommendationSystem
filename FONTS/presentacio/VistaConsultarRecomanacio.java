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

/**
 * Vista que permet consultar a l'usuari la recomanació que acaba de fer, o una carregada,
 * en una taula interactiva
 */
public class VistaConsultarRecomanacio extends JFrame implements MouseListener
{
    //Controlador de presentació
    private final CtrlPresentacio controladorPresentacio;

    //Components de la interficie gràfica
    private final JFrame frameVista = new JFrame("Consultar Recomanacio");
    private final JMenuBar menu = new JMenuBar();
    private final JMenu menuAjuda = new JMenu("Ajuda");
    private final JMenuItem ajuda = new JMenuItem("Com consultar una recomanació?");
    private final JPanel panelContinguts = new JPanel();
    private final JPanel panelTaula = new JPanel();
    private final JPanel panelBotons = new JPanel();
    private final JLabel valRecomLabel = new JLabel();
    private JScrollPane scrollPaneTabla = new JScrollPane();
    private final JTable taulaRecomanacio = new JTable();
    private final JButton buttonEndarrere = new JButton("Tornar Enrere");

    //Altres atributs
    private String[][] llistaRecomanacions;
    private int columnesTaula;
    private boolean first = true;
    private boolean tester = false;
    private boolean carregat = false;

    /**
     * Constructora de la classe
     * @param ctrlPresentacio referència al controlador de presentació
     */
    public VistaConsultarRecomanacio(CtrlPresentacio ctrlPresentacio)
    {
        controladorPresentacio = ctrlPresentacio;
    }

    //metodes publics
    /**
     * Dona valor a la llista de recomanacions
     * @param llistaRecomanacions valor de la llista de recomanacions
     */
    public void setLlistaRecomanacions(String[][] llistaRecomanacions)
    {
        this.llistaRecomanacions = llistaRecomanacions;
    }

    /**
     * Dona valor al flag carregat, que indica a on es torna enrere
     * @param b valor del flag carregat
     */
    public void setCarregat(boolean b)
    {
            carregat = b;
    }

    /**
     * Dona valor al flag tester
     * @param t valor del flag tester
     */
    public void setTester(boolean t)
    {
        tester = t;
    }

    /**
     * Activa la vista
     */
    public void activar()
    {
        frameVista.setEnabled(true);
        frameVista.pack();
        frameVista.setVisible(true);
        construirTaula();
        if (first)
        {
            first = false;
            inicialitzarComponents();
        }
        if (tester)
        {
            valRecomLabel.setText("Valoració Algoritme: " + controladorPresentacio.getDCG());
        }
        else valRecomLabel.setText("");

        String titol = "Insuficients recomanacions";
        String missatge = "No tenim suficients recomanacions com per mostrar les solicitades, es mostaran les que s'han aconseguit";
        if (!controladorPresentacio.isSuficientsItems()) controladorPresentacio.mostraMissatge(titol, missatge);
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
        String titol = "Ajuda: Com consultar una recomanació?";
        String missatge = "La recomanació mostra els ítems recomanats de millor a pitjor. \n" +
                "Per consultar un ítem concret de la recomanació fes click a l'icona de la lupa de l'ítem.";
        controladorPresentacio.mostraMissatge(titol, missatge);
    }

    private void actionPerformedTornarEnrere()
    {
        if (carregat) controladorPresentacio.activarVistaRecomanacio();
        else controladorPresentacio.activarVistaOpcionsRecomanacio();
    }

    //Assignacio de listeners

    private void assignarListenersComponents()
    {
        ajuda.addActionListener(event -> actionPerformedAjuda());

        // Listeners pels botons
        buttonEndarrere.addActionListener(event -> actionPerformedTornarEnrere());
    }

    private void inicialitzarComponents()
    {
        inicialitzarFrameVista();
        inicialitzarPanelContinguts();
        inicialitzarMenu();
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

    private void inicialitzarMenu()
    {
        // Afegim menu Ajuda
        menuAjuda.add(ajuda);
        menu.add(menuAjuda);
        frameVista.setJMenuBar(menu);
    }

    private void inicialitzarPanelTaula()
    {
        // Panel
        panelTaula.setBorder(new EmptyBorder(5, 5, 5, 5));
        panelTaula.setLayout(new BorderLayout());

        // Titol taula
        JLabel lbltaulaDataset = new JLabel("Llista recomanacions: " + "\n");
        lbltaulaDataset.setFont(new Font("Verdana", Font.BOLD, 25));
        panelTaula.add(lbltaulaDataset, BorderLayout.NORTH);

        // Afegim scroll
        scrollPaneTabla = new JScrollPane();
        panelTaula.add(scrollPaneTabla);

        // Creem taula
        taulaRecomanacio.setBackground(Color.WHITE);
        taulaRecomanacio.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
        taulaRecomanacio.addMouseListener(this);
        taulaRecomanacio.setOpaque(false);
        scrollPaneTabla.setViewportView(taulaRecomanacio);
    }

    private void inicialitzarPanelBotons()
    {
        // Layout
        panelBotons.setBorder(BorderFactory.createEmptyBorder(30, 100, 30, 100));
        panelBotons.setLayout(new GridLayout(0, 1));

        panelBotons.add(valRecomLabel);
        // Afegim botó Endarrere
        buttonEndarrere.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelBotons.add(buttonEndarrere);
    }

    /*
     * Mètode que permet construir la taula de items
     * es creen primer les columnes i després s'assigna la informació
     */
    private void construirTaula()
    {
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

    /*
     * Omple la informació de la taula usant la llista d'items treballada
     * anteriorment, guardant-la en una matriu que es retorna amb tota
     * la informació per després sera assignada al model
     */
    private Object[][] obtenirMatriuDades(ArrayList<String> titulosList)
    {
        /* es crea la matriu on les files i les columnes són dinàmiques
         * ja que corresponen a tots els items i la capçalera del dataset
         */

        String[][] informacio = new String[llistaRecomanacions.length][titulosList.size()];
        System.out.println(informacio.length+ " " + titulosList.size());
        for (int i = 0; i < informacio.length; i++)
        {
            for (int j = 0; j < informacio[0].length; j++)
            {
                if (j != (informacio[0].length-1))
                {
                    System.out.println(llistaRecomanacions[i].length);
                    informacio[i][j] = llistaRecomanacions[i][j] + "";
                }
                else
                    informacio[i][j] = "LUPA"; //paraula clau per assignar l'icono de la Lupa a la classe GestioCeles
            }
        }
        return informacio;
    }

    /*
     * Amb els títols i la informació a mostrar es crea el model per
     * poder personalitzar la taula, assignant mida de cel·les tant en amplada com en alt
     * així com els tipus de dades que podrà suportar.
     */
    private void construirTaula(String[] titulos, Object[][] data)
    {
        //model definit a la classe ModelTaula
        ModelTaula model = new ModelTaula(data, titulos);
        //s'assigna el model a la taula
        taulaRecomanacio.setModel(model);

        columnesTaula = taulaRecomanacio.getColumnCount();

        //s'assigna el tipus de dada que tindran les cel·les de cada columna definida respectivament per validar-ne la personalització
        for (int i = 0; i < titulos.length-1; i++)
        {
            taulaRecomanacio.getColumnModel().getColumn(i).setCellRenderer(new GestioCeles("text"));
        }
        taulaRecomanacio.getColumnModel().getColumn(titulos.length-1).setCellRenderer(new GestioCeles("icono"));
        taulaRecomanacio.getColumnModel().getColumn(titulos.length-1).setPreferredWidth(30); //tamany columna icono

        taulaRecomanacio.getTableHeader().setReorderingAllowed(false);
        taulaRecomanacio.setRowHeight(25); //mida de les cel·les
        taulaRecomanacio.setGridColor(new java.awt.Color(0, 0, 0));

        //personalitza la capçalera
        JTableHeader jtableHeader = taulaRecomanacio.getTableHeader();
        jtableHeader.setDefaultRenderer(new GestioCapcaleraTaula());
        taulaRecomanacio.setTableHeader(jtableHeader);

        //s'assigna la taula a l'scrollPane
        scrollPaneTabla.setViewportView(taulaRecomanacio);
    }

    @Override
    public void mouseClicked(MouseEvent e)
    {
        //capturo fila i columna
        int fila = taulaRecomanacio.rowAtPoint(e.getPoint());
        int columna = taulaRecomanacio.columnAtPoint(e.getPoint());

        /* utilitzem la columna per validar si correspon a la columna de Lupa garantint
         * que només es produeixi alguna cosa si seleccioneu una fila d'aquesta columna
         */
        if (columna == (columnesTaula-1))   //si estem a la columna de la Lupa (l'última)
        {
            //sabent que correspon a la columna de Lupa, envio la posició de la fila seleccionada
            validarSeleccioMouse(fila);
        }
    }

    /*
     * Aquest mètode simula el procés o l'acció que es vol fer si
     * es pressiona l'icono de la Lupa de la taula
     */
    private void validarSeleccioMouse(int fila)
    {
        //tenint la fila llavors s'obté l'objecte corresponent per imprimir la informació
        StringBuilder info = new StringBuilder("");

        for (int j = 0; j < columnesTaula-1; j++)
        {
            String nomAtribut = taulaRecomanacio.getColumnModel().getColumn(j).getHeaderValue().toString();
            String valorAtribut = taulaRecomanacio.getValueAt(fila, j).toString();
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