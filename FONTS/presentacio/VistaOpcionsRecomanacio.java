package presentacio;

import javax.swing.*;
import java.awt.*;

/**
 * Vista que permet a l'usuari seleccionar les opcions del
 * tipus de recomanació que hagi escollit
 */
public class VistaOpcionsRecomanacio extends Vista
{
    //Components de la interficie gràfica
    private final JFrame frameVista = new JFrame();
    private final JMenuBar menu = new JMenuBar();
    private final JMenu menuAjuda = new JMenu("Ajuda");
    private final JMenuItem ajuda = new JMenuItem("Opcions recomanació?");
    private final Label mLabel  = new Label("Número d'ítems: ");
    private final Label kLabel = new Label("Precisió: ");
    private final Label dLabel = new Label("Eficiència (més alta l'algorisme és més ràpid però menys precís): ");
    private JSpinner k;
    private JSpinner m;
    private JSpinner d;
    private final JPanel panelContinguts = new JPanel();
    private final JPanel panelText = new JPanel();
    private final JPanel panelBotons = new JPanel();
    private final JButton buttonSubmit = new JButton("Submit");
    private final JButton buttonEndarrere = new JButton("Tornar Enrere");
    private VistaTriaRecomanacio.tipusRecomanacio t;

    /**
     * Creadora de la vista
     * @param c referència al controlador de presentació
     */
    public VistaOpcionsRecomanacio(CtrlPresentacio c)
    {
        controladorPresentacio = c;
        inicialitzarComponents();
    }

    //Metodes publics

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

    /**
     * Dona valor al tipus recomanació, i modifica la vista adientment
     * @param tipus tipus de recomanació
     */
    public void setTipusRecomanacio(VistaTriaRecomanacio.tipusRecomanacio tipus)
    {
        //s'ha de canviar per enums
        t = tipus;
        int numItems = Integer.parseInt(controladorPresentacio.getNumItems());
        SpinnerModel spinnerModelk = new SpinnerNumberModel(1, 1, numItems, 1);
        SpinnerModel spinnerModelm = new SpinnerNumberModel(1, 1,  numItems, 1);

        k.setModel(spinnerModelk);
        m.setModel(spinnerModelm);
        if (t == VistaTriaRecomanacio.tipusRecomanacio.COLLABORATIVE)
        {
            frameVista.setTitle("Collaborative Filtering");
            dLabel.setVisible(true);
            dLabel.repaint();
            d.setVisible(true);
            d.repaint();
        }
        else if (t == VistaTriaRecomanacio.tipusRecomanacio.CONTENT)
        {
            frameVista.setTitle("Content Based Filtering");
            dLabel.setVisible(false);
            dLabel.repaint();
            d.setVisible(false);
            d.repaint();

        }
        else
        {
            frameVista.setTitle("Hybrid Filtering");
            dLabel.setVisible(true);
            dLabel.repaint();
            d.setVisible(true);
            d.repaint();
        }
    }

    //Mètodes privats
    private void actionPerformedAjuda()
    {
        String titol = "Ajuda: Opcions recomanació?";
        String missatge = "Per demanar una recomanació introdueix els següents valors: \n" +
                "- Número d'ítems: total d'ítems que vols que retorni la recomanació \n" +
                "- Precisió: el número de clusters que farà \n" +
                "- Eficiència: com més baixa sigui més lent serà l'algorisme però més precís \n" +
                "Per últim, fes click a \"Submit\".";
        controladorPresentacio.mostraMissatge(titol, missatge);
    }

    private void actionPerformedSubmit()
    {
        controladorPresentacio.setRecomanacioFeta(true);
        if (t == VistaTriaRecomanacio.tipusRecomanacio.COLLABORATIVE)
        {
            String mS, kS, dS;
            mS = String.valueOf(m.getValue());
            kS = String.valueOf(k.getValue());
            dS = String.valueOf(d.getValue());
            controladorPresentacio.executeCollaborativeFiltering(mS, kS, dS);
            controladorPresentacio.activarVistaConsultarRecomanacio();
        }

        else if (t == VistaTriaRecomanacio.tipusRecomanacio.CONTENT)
        {
            String mS, kS;
            mS = String.valueOf(m.getValue());
            kS = String.valueOf(k.getValue());
            controladorPresentacio.executeContentBasedFiltering(mS, kS);
            controladorPresentacio.activarVistaConsultarRecomanacio();
        }

        else
        {
            String mS, kS, dS;
            mS = String.valueOf(m.getValue());
            kS = String.valueOf(k.getValue());
            dS = String.valueOf(d.getValue());
            controladorPresentacio.executeHybridFiltering(mS, kS, dS);
            controladorPresentacio.activarVistaConsultarRecomanacio();
        }
    }

    private void actionPerformedTornarEnrere()
    {
        controladorPresentacio.activarVistaTriaRecomanacio();
    }

    //Assignacio de listeners

    private void assignarListenersComponents()
    {
        ajuda.addActionListener(event -> actionPerformedAjuda());

        // Listeners pels botons

        buttonSubmit.addActionListener(event -> actionPerformedSubmit());

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

        // S'agrega panelBotons al contentPane
        JPanel contentPane = (JPanel) frameVista.getContentPane();
        contentPane.add(panelContinguts, BorderLayout.CENTER);
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
        // Layout
        panelContinguts.setLayout(new BorderLayout());

        // Paneles
        panelContinguts.add(panelText, BorderLayout.CENTER);
        panelContinguts.add(panelBotons, BorderLayout.SOUTH);
    }

    private void inicialitzarPanelBotons()
    {
        // Panel
        panelBotons.setBorder(BorderFactory.createEmptyBorder(30, 100, 30, 100));
        panelBotons.setLayout(new GridLayout(0, 1));

        // Botó Submit
        buttonSubmit.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelBotons.add(buttonSubmit);

        panelBotons.add(Box.createRigidArea(new Dimension(0, 10)));

        panelBotons.add(Box.createRigidArea(new Dimension(0, 10)));

        // Botó Endarrere
        buttonEndarrere.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelBotons.add(buttonEndarrere);
    }

    private void inicialitzarText()
    {
        panelText.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        panelText.setLayout(new GridLayout(7, 2));

        SpinnerModel spinnerModel1 = new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1);
        SpinnerModel spinnerModel2 = new SpinnerNumberModel(1, 1,  Integer.MAX_VALUE, 1);
        SpinnerModel spinnerModel3 = new SpinnerNumberModel(1, 1, 100, 1);

        k = new JSpinner(spinnerModel1);
        m = new JSpinner(spinnerModel2);
        d = new JSpinner(spinnerModel3);

        panelText.add(mLabel);
        panelText.add(m);
        panelText.add(kLabel);
        panelText.add(k);
        panelText.add(dLabel);
        panelText.add(d);

        panelText.add(Box.createRigidArea(new Dimension(0, 10)));
    }
}