package presentacio;

import javax.swing.*;
import java.awt.*;

/**
 * Vista que permet a l'usuari consultar el seu perfil o fer logout
 */
public class VistaPerfilUsuari extends Vista
{
    //Components de la interficie gràfica
    private final JFrame frameVista = new JFrame("Perfil Usuari");
    private final JMenuBar menu = new JMenuBar();
    private final JMenu menuAjuda = new JMenu("Ajuda");
    private final JMenuItem ajuda = new JMenuItem("Com tancar sessió?");
    private final Label idLabel = new Label("Id: ");
    private final Label userNameLabel  = new Label("Name: ");
    private final Label emailLabel = new Label("E-Mail: ");
    private final Label ageLabel = new Label("Age: ");
    private final JTextField idL = new JTextField("id");
    private final JTextField userNameL  = new JTextField("name");
    private final JTextField emailL = new JTextField("email");
    private final JTextField ageL = new JTextField("age");
    private final JPanel panelContinguts = new JPanel();
    private final JPanel panelText = new JPanel();
    private final JPanel panelBotons = new JPanel();
    private final JButton buttonLogout = new JButton("Logout");
    private final JButton buttonEndarrere = new JButton("Tornar Enrere");

    /**
     * Creadora de la vista
     * @param c referència al controlador de presentació
     */
    public VistaPerfilUsuari(CtrlPresentacio c)
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
     * Dona valor a la informació de l'usuari actiu
     * @param id identificador de l'usuari actiu
     * @param nom nom de l'usuari actiu
     * @param edat edat de l'usuari actiu
     * @param email email de l'usuari actiu
     */
    public void setUsuariActiu(String id, String nom, String edat, String email)
    {
        idL.setText(id);
        userNameL.setText(nom);
        ageL.setText(edat);
        emailL.setText(email);
        frameVista.repaint();
    }

    //Mètodes de les interficies Listener
    private void actionPerformedAjuda()
    {
        String titol = "Ajuda: Com tancar sessió?";
        String missatge = "Per tancar la sessió de forma segura fes click a \"Logout\".";
        controladorPresentacio.mostraMissatge(titol, missatge);
    }

    private void actionPerformedLogout()
    {
        controladorPresentacio.setDatasetCarregat(false);
        controladorPresentacio.activarVistaUsuaris();
    }

    private void actionPerformedTornarEnrere()
    {
        controladorPresentacio.activarVistaPrincipal();
    }

    //Assignacio de listeners

    private void assignarListenersComponents()
    {
        ajuda.addActionListener(event -> actionPerformedAjuda());

        // Listeners pels botons
        buttonLogout.addActionListener(event -> actionPerformedLogout());

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
        buttonLogout.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelBotons.add(buttonLogout);

        panelBotons.add(Box.createRigidArea(new Dimension(0, 10)));

        panelBotons.add(Box.createRigidArea(new Dimension(0, 10)));

        // Botó Endarrere
        buttonEndarrere.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelBotons.add(buttonEndarrere);
    }

    private void inicialitzarText()
    {
        panelText.setBorder(BorderFactory.createEmptyBorder(30, 100, 30, 100));
        panelText.setLayout(new GridLayout(4,2));

        idL.setEditable(false);
        userNameL.setEditable(false);
        emailL.setEditable(false);
        ageL.setEditable(false);
        panelText.add(idLabel);
        panelText.add(idL);
        panelText.add(userNameLabel);
        panelText.add(userNameL);
        panelText.add(ageLabel);
        panelText.add(ageL);
        panelText.add(emailLabel);
        panelText.add(emailL);
    }
}