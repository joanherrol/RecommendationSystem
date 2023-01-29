package presentacio.utilitats;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.util.Objects;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.TableCellRenderer;

/**
 * Permet personalitzar la capçalera de la taula per definir el color que tindrà a les columnes
 */
public class GestioCapcaleraTaula implements TableCellRenderer
{
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
    {
        JLabel jcomponent = null;

        if (value instanceof String)
        {
            jcomponent = new JLabel((String) value);
            jcomponent.setHorizontalAlignment(SwingConstants.CENTER);
            jcomponent.setSize(30, jcomponent.getWidth());
            jcomponent.setPreferredSize(new Dimension(6, jcomponent.getWidth()));
        }

        Objects.requireNonNull(jcomponent).setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 1, new java.awt.Color(255, 255, 255)));
        jcomponent.setOpaque(true);
        jcomponent.setBackground( new Color(65,65,65));
        jcomponent.setToolTipText("Taula Dataset");
        jcomponent.setForeground(Color.white);

        return jcomponent;
    }
}