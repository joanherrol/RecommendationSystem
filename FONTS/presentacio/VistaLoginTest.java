package presentacio;

import javax.swing.*;
import java.awt.*;

/**
 * Vista que permet a l'usuari fer login com a tester
 */
public class VistaLoginTest extends Vista
{
    //Components de la interficie gràfica
    private final JFrame frameVista = new JFrame("Login Tester");
    private final JMenuBar menu = new JMenuBar();
    private final JMenu menuAjuda = new JMenu("Ajuda");
    private final JMenuItem ajuda = new JMenuItem("Com iniciar sessió (tester)?");
    private final Label testerIdLabel = new Label("Id");
    private final Label passwordLabel = new Label("Password");
    private JTextField testerId;
    private JPasswordField password;
    private JCheckBox showPassword;
    private final JPanel panelContinguts = new JPanel();
    private final JPanel panelText = new JPanel();
    private final JPanel panelBotons = new JPanel();
    private final JButton buttonLogin = new JButton("Login");
    private final JButton buttonEndarrere = new JButton("Tornar Enrere");

    /**
     * Creadora de la vista
     * @param c referència al controlador de presentació
     */
    public VistaLoginTest(CtrlPresentacio c)
    {
        controladorPresentacio = c;
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
        password.setEchoChar('*');
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

    //Mètodes de les interficies Listener
    private void actionPerformedAjuda()
    {
        String titol = "Ajuda: Com iniciar sessió (tester)?";
        String missatge = "Per iniciar sessió introdueix l'id i la contrasenya i fes click a \"Login\".";
        controladorPresentacio.mostraMissatge(titol, missatge);
    }

    private void actionPerformedShowPassword()
    {
        if (showPassword.isSelected())
            password.setEchoChar((char)0);
        else
            password.setEchoChar('*');
    }

    private void actionPerformedLogin()
    {
       String id, pass;
       id = testerId.getText();
       pass = String.valueOf(password.getPassword());
       if (controladorPresentacio.loginTester(id, pass))
       {
           controladorPresentacio.setSessioIniciada();
           controladorPresentacio.setTester(true);
           controladorPresentacio.setUsuariActiu();
           controladorPresentacio.activarVistaPrincipal();
           controladorPresentacio.mostraMissatge("Login", "Has iniciat sessió correctament");
       }
       else controladorPresentacio.mostraError("L'Id o el Password són incorrectes");
    }

    private void actionPerformedTornarEnrere()
    {
        controladorPresentacio.activarVistaUsuaris();
    }

    //Assignacio de listeners

    private void assignarListenersComponents()
    {
        ajuda.addActionListener(event -> actionPerformedAjuda());

        showPassword.addActionListener(event -> actionPerformedShowPassword());

        // Listeners pels botons

        buttonLogin.addActionListener(event -> actionPerformedLogin());

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
        panelContinguts.add(panelBotons,BorderLayout.SOUTH);
        panelContinguts.add(panelText,BorderLayout.CENTER);
    }

    private void inicialitzarPanelBotons()
    {
        // Layout
        panelBotons.setBorder(BorderFactory.createEmptyBorder(30, 100, 30, 100));
        panelBotons.setLayout(new GridLayout(0, 1));

        // Botó Login
        buttonLogin.setAlignmentX(Component.CENTER_ALIGNMENT);

        panelBotons.add(buttonLogin);

        panelBotons.add(Box.createRigidArea(new Dimension(0, 10)));

        panelBotons.add(Box.createRigidArea(new Dimension(0, 10)));

        // Botó Endarrere
        buttonEndarrere.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelBotons.add(buttonEndarrere);
    }

    private void inicialitzarText()
    {
        panelText.setBorder(BorderFactory.createEmptyBorder(30, 100, 30, 100));
        panelText.setLayout(new GridLayout(3, 2));

        testerId = new JTextField(20);
        testerId.setAlignmentX(Component.CENTER_ALIGNMENT);
        password = new JPasswordField(20);
        password.setEchoChar('*');
        password.setAlignmentX(Component.CENTER_ALIGNMENT);
        showPassword = new JCheckBox("Show password");

        panelText.add(testerIdLabel);
        panelText.add(testerId);

        panelText.add(passwordLabel);
        panelText.add(password);

        panelText.add(Box.createRigidArea(new Dimension(0, 10)));

        panelText.add(showPassword);
    }

    private void actualitzar()
    {
        testerId.setText("");
        password.setText("");
        password.setEchoChar('*');
        showPassword.setSelected(false);
    }
}