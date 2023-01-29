package presentacio;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * Vista que permet crear un usuari nou
 */
public class VistaCrearUsuari
{
    //Controlador de presentació
    private final CtrlPresentacio controladorPresentacio;

    //Components de la interficie gràfica
    private final JFrame frameVista = new JFrame("Crear Usuari");
    private final JMenuBar menu = new JMenuBar();
    private final JMenu menuAjuda = new JMenu("Ajuda");
    private final JMenuItem ajuda = new JMenuItem("Com crear un usuari?");
    private final Label idLabel = new Label("Id: ");
    private final Label userNameLabel  = new Label("Name: ");
    private final Label emailLabel = new Label("E-Mail: ");
    private final Label ageLabel = new Label("Age: ");
    private final Label passwordLabel = new Label("Password: ");
    private final Label passwordRepeatLabel = new Label("Repeat Password: ");
    private JTextField id;
    private JTextField userName;
    private JTextField age;
    private JTextField email;
    private JPasswordField password;
    private JPasswordField passwordRepeat;
    private JCheckBox showPassword;
    private final JPanel panelContinguts = new JPanel();
    private final JPanel panelText = new JPanel();
    private final JPanel panelBotons = new JPanel();
    private final JButton buttonCrearUsuari = new JButton("Crear usuari");
    private final JButton buttonEndarrere = new JButton("Tornar Enrere");

    /**
     * Creadora de la vista
     * @param c referència al controlador de presentació
     */
    public VistaCrearUsuari(CtrlPresentacio c)
    {
        controladorPresentacio = c;
        inicialitzarComponents();
    }

    /**
     * Activa la vista
     */
    public void activar()
    {
        frameVista.setEnabled(true);
        frameVista.pack();
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


    //Mètodes de les interficies Listener
    private void actionPerformedAjuda()
    {
        String titol = "Ajuda: Com crear un usuari?";
        String missatge = "Per crear un usuari introdueix totes les dades que se't demanen i fes click a \"Crear Usuari\".";
        controladorPresentacio.mostraMissatge(titol, missatge);
    }

    private void actionPerformedShowPassword()
    {
        if (showPassword.isSelected())
        {
            password.setEchoChar((char)0);
            passwordRepeat.setEchoChar((char)0);
        }
        else
        {
            password.setEchoChar('*');
            passwordRepeat.setEchoChar('*');
        }
    }

    private void actionPerformedCrearUsuari()
    {
        String idU, name,  mail, agee, pass, passR;
        idU = id.getText();
        name = userName.getText();
        agee = age.getText();
        mail = email.getText();
        pass = String.valueOf(password.getPassword());
        passR = String.valueOf(passwordRepeat.getPassword());
        if (name.equals("") || agee.equals("") || mail.equals("") || pass.equals(""))
        {
            controladorPresentacio.mostraError("Els camps no poden ser buits");
        }
        else
        {
            if (pass.equals(passR))
            {
                if (controladorPresentacio.crearUsuari(idU, name, agee, mail, pass))
                {
                    controladorPresentacio.setSessioIniciada();
                    controladorPresentacio.setUsuariActiu();
                    controladorPresentacio.activarVistaPrincipal();
                    controladorPresentacio.mostraMissatge("Crear Usuari", "Benvingut/da " + name + "!");
                    try
                    {
                        controladorPresentacio.guardarUsuaris("GestioUsuaris.csv");
                    }
                    catch (IOException e)
                    {
                        controladorPresentacio.mostraError("Error al guardar el fitxer GestioUsuaris.csv");
                    }
                }
            }
            else controladorPresentacio.mostraError("Els passwords no coincideixen");
        }
    }

    private void actionPerformedTornarEnrere()
    {
        controladorPresentacio.activarVistaUsuaris();
    }

    /** Assignacio de listeners **/

    private void assignarListenersComponents()
    {
        ajuda.addActionListener(event -> actionPerformedAjuda());

        showPassword.addActionListener(event -> actionPerformedShowPassword());

        // Listeners pels botons

        buttonCrearUsuari.addActionListener(event -> actionPerformedCrearUsuari());

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

        // Botó Crear Usuari
        buttonCrearUsuari.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelBotons.add(buttonCrearUsuari);

        panelBotons.add(Box.createRigidArea(new Dimension(0, 10)));

        panelBotons.add(Box.createRigidArea(new Dimension(0, 10)));

        // Botó Endarrere
        buttonEndarrere.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelBotons.add(buttonEndarrere);
    }

    private void inicialitzarText()
    {
        panelText.setBorder(BorderFactory.createEmptyBorder(30, 100, 30, 100));
        panelText.setLayout(new GridLayout(7, 2));

        id = new JTextField(20);
        userName = new JTextField(20);
        age = new JTextField(20);
        email = new JTextField(20);
        password = new JPasswordField(20);
        password.setEchoChar('*');
        passwordRepeat = new JPasswordField(20);
        passwordRepeat.setEchoChar('*');
        showPassword = new JCheckBox("Show password");

        panelText.add(idLabel);
        panelText.add(id);

        panelText.add(userNameLabel);
        panelText.add(userName);

        panelText.add(ageLabel);
        panelText.add(age);

        panelText.add(emailLabel);
        panelText.add(email);

        panelText.add(passwordLabel);
        panelText.add(password);

        panelText.add(passwordRepeatLabel);
        panelText.add(passwordRepeat);

        panelText.add(Box.createRigidArea(new Dimension(0, 10)));

        panelText.add(showPassword);
    }

    private void actualitzar()
    {
        id.setText("");
        userName.setText("");
        age.setText("");
        email.setText("");
        password.setText("");
        password.setEchoChar('*');
        passwordRepeat.setText("");
        passwordRepeat.setEchoChar('*');
        showPassword.setSelected(false);
    }
}