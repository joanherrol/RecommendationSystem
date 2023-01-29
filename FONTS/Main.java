import presentacio.CtrlPresentacio;

import java.io.IOException;

public class Main
{
    public static void main(String[] args)
    {
        javax.swing.SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
                CtrlPresentacio mainWindow = CtrlPresentacio.getInstance();
                try
                {
                    mainWindow.inicialitzarPresentacio();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                    mainWindow.mostraError("ERROR");
                }
            }
        });
    }
}