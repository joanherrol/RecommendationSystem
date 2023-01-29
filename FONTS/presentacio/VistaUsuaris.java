package presentacio;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Vista que permet a l'usuari escollir
 * entre les diferents opcions relacionades amb usuaris
 */
public class VistaUsuaris extends Vista
{
    //Components de la interfície gràfica
    private final JFrame frameVista = new JFrame("Gestió Usuaris");
    private final JPanel panelBotons = new JPanel();
    private final JButton buttonTesters = new JButton("Carregar Testers");
    private final JButton buttonCrearUsuari = new JButton("Crear Usuari");
    private final JButton buttonLogin = new JButton("Login");
    private final JButton buttonLoginTester = new JButton("LoginTester");
    private final JButton buttonSortir = new JButton("Sortir");
    private boolean testers;

    /**
     * Creadora de la vista
     * @param ctrlPresentacio referència al controlador presentació
     */
    public VistaUsuaris(CtrlPresentacio ctrlPresentacio)
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
    private void actionPerformedCarregarTesters()
    {
        JFileChooser chooserTesters = new JFileChooser();
        chooserTesters.setDialogTitle("CarregarTesters (ratings.test.known.csv)");
        chooserTesters.setCurrentDirectory(new File(System.getProperty("user.dir")));
        int returnValue = chooserTesters.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION)
        {
            File fitxerV = chooserTesters.getSelectedFile();
            try
            {
                controladorPresentacio.carregarTesters(fitxerV.getAbsolutePath());
                testers = true;
            }
            catch (IOException|NumberFormatException e)
            {
                controladorPresentacio.mostraError("Error al carregar Testers");
                testers = false;
            }
        }
    }

    private void actionPerformedCrearUsuari()
    {
        controladorPresentacio.activarVistaCrearUsuari();
    }

    private void actionPerformedLogin()
    {
        controladorPresentacio.activarVistaLogin();
    }

    private void actionPerformedLoginTester()
    {
        if (testers) controladorPresentacio.activarVistaLoginTest();
        else controladorPresentacio.mostraError("No hi ha cap fitxer de testers carregat");
    }

    private void actionPerformedSortir() throws IOException
    {
        controladorPresentacio.guardarUsuaris("GestioUsuaris.csv");
        controladorPresentacio.guardarFiltres("GestioFiltres.csv");
        System.exit(0);
    }

    //Assignacio de listeners

    private void assignarListenersComponents()
    {
        // Listeners pels botons
        buttonTesters.addActionListener(event -> actionPerformedCarregarTesters());

        buttonCrearUsuari.addActionListener(event -> actionPerformedCrearUsuari());

        buttonLogin.addActionListener(event -> actionPerformedLogin());

        buttonLoginTester.addActionListener(event -> actionPerformedLoginTester());

        buttonSortir.addActionListener(event ->
        {
            try
            {
                actionPerformedSortir();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        });
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

        // Botó Carregar Dataset
        buttonTesters.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelBotons.add(buttonTesters);
        panelBotons.add(Box.createRigidArea(new Dimension(0, 10)));
        buttonCrearUsuari.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelBotons.add(buttonCrearUsuari);

        panelBotons.add(Box.createRigidArea(new Dimension(0, 10)));

        // Botó Guardar Dataset
        buttonLogin.setAlignmentX(Component.CENTER_ALIGNMENT);

        panelBotons.add(buttonLogin);

        panelBotons.add(Box.createRigidArea(new Dimension(0, 10)));

        buttonLoginTester.setAlignmentX(Component.CENTER_ALIGNMENT);

        panelBotons.add(buttonLoginTester);

        panelBotons.add(Box.createRigidArea(new Dimension(0, 10)));

        // Botó Endarrere
        buttonSortir.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelBotons.add(buttonSortir);
    }
}