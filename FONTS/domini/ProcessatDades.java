package domini;

/**
 * Classe que s'encarrega de processar les dades segons el seu tipus
 */
public class ProcessatDades
{
    /**
     * Funció que retorna un boolea a true si el paràmetre és un integer
     * @param cadena string amb el valor de l'atribut d'un item a castejar
     * @return boolea amb el resultat
     */
    public static boolean isNumeric(String cadena)
    {
        boolean result;
        try
        {
            Integer.parseInt(cadena);
            result = true;
        }
        catch(NumberFormatException e)
        {
            result = false;
        }
        return result;
    }

    /**
     * Funció que retorna un boolea a true si el paràmetre és un boolea
     * @param cadena string amb el valor de l'atribut d'un item a castejar
     * @return boolea amb el resultat
     */
    public static boolean isBoolean(String cadena)
    {
        boolean result = false;
        try
        {
            if (cadena.contains("True") || cadena.contains("true") || cadena.contains("False") || cadena.contains("false") || cadena.contains("TRUE") || cadena.contains("FALSE"))
            {
                result = true;
            }
        }
        catch(NumberFormatException e)
        {
            result = false;
        }
        return result;
    }

    /**
     * Funció que retorna un boolea a true si el paràmetre és un double
     * @param cadena string amb el valor de l'atribut d'un item a castejar
     * @return boolea amb el resultat
     */
    public static boolean isDouble(String cadena)
    {
        boolean result;
        try
        {
            Double.parseDouble(cadena);
            result = true;
        }
        catch(NumberFormatException e)
        {
            result = false;
        }
        return result;
    }

    /**
     * Funció que retorna un boolea a true si el paràmetre és un Float
     * @param cadena string amb el valor de l'atribut d'un item a castejar
     * @return boolea amb el resultat
     */
    public static boolean isFloat(String cadena)
    {
        boolean result;
        try
        {
            Float.parseFloat(cadena);
            result = true;
        }
        catch(NumberFormatException e)
        {
            result = false;
        }
        return result;
    }

    /**
     * Funció que retorna un boolea a true si el paràmetre és un Long
     * @param cadena string amb el valor de l'atribut d'un item a castejar
     * @return boolea amb el resultat
     */
    public static boolean isLong(String cadena)
    {
        boolean result;
        try
        {
            Long.parseLong(cadena);
            result = true;
        }
        catch (NumberFormatException e)
        {
            result = false;
        }
        return result;
    }
}