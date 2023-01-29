package presentacio.utilitats;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.util.Objects;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * Aquesta classe permet gestionar la taula i els esdeveniments realizats amb ella
 */
public class GestioCeles extends DefaultTableCellRenderer
{
    //es defineixen per defecte els tipus de dades a utilitzar
    private final Font normal = new Font("Verdana", Font.PLAIN, 10);
    private final String tipus;
    //icones disponibles per ser mostrats en l'ultima columna de la taula
    private final ImageIcon iconoBuscar = new ImageIcon(Objects.requireNonNull(getClass().getResource("/recursos/icones/icono_cercar.png")));
    //etiqueta que guarda l'icono
    private final JLabel label = new JLabel();

    public GestioCeles(String tipus)
    {
        this.tipus = tipus;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean selected, boolean focused, int row, int column)
    {
        /*
         * Aquest mètode controla tota la taula, podem obtenir el valor que conté
         * definir quina cel·la està seleccionada, la fila i columna en tenir el focus en ella.
         * Cada esdeveniment sobre la taula invocarà aquest mètode
         */

        //definim colors per defecte
        Color colorFondo;
        Color colorFondoPorDefecto = new Color(192, 192, 192);
        Color colorFondoSeleccion = new Color(140, 140, 140);

        /*
         * Si la cel·la de l'esdeveniment és la seleccionada s'assigna el fons per defecte per a la selecció
         */
        if (selected)
        {
            this.setBackground(colorFondoPorDefecto);
        }
        else
        {
            //Per a les que no estan seleccionades es pinta el fons de les cel·les de blanc
            this.setBackground(Color.white);
        }

        /*
         * Es defineixen els tipus de dades que contindran les cel·les basades en la instància que
         * es fa a la finestra de la taula al moment de construir-la
         */
        if (tipus.equals("text"))
        {
            //si és tipus text defineix el color de fons del text i de la cel·la així com l'alineació
            if (focused)
            {
                colorFondo = colorFondoSeleccion;
            }
            else
            {
                colorFondo = colorFondoPorDefecto;
            }
            this.setHorizontalAlignment(JLabel.LEFT);
            this.setText((String) value);
            this.setBackground((selected) ? colorFondo : Color.WHITE);
            this.setFont(normal);
            return this;
        }

        //si el tipus és icona llavors valida quina icona assignar a letiqueta
        if (tipus.equals("icono"))
        {
            if (String.valueOf(value).equals("LUPA"))
            {
                label.setIcon(iconoBuscar);
            }
            label.setHorizontalAlignment(JLabel.LEFT);
            label.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
            return label;
        }

        return this;
    }
}