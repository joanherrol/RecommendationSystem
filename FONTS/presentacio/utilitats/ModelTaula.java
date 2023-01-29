package presentacio.utilitats;

import javax.swing.table.DefaultTableModel;

public class ModelTaula extends DefaultTableModel
{
    String[] titols;
    Object[][] dades;

    /**
     * Determina el model amb el que es construeix la taula
     */
    public ModelTaula(Object[][] dades, String[] titols)
    {
        super();
        this.titols = titols;
        this.dades = dades;
        setDataVector(dades, titols);
    }

    public boolean isCellEditable(int row, int column)
    {
        //Definim que les cel·les no poden ser editables
        return false;
    }
}