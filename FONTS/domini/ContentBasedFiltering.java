package domini;

import java.util.*;

@SuppressWarnings("unchecked")
/**
 * Classe que s’encarrega d’executar l’algoritme de recomanació Content Based Filtering
 */

public class ContentBasedFiltering extends GestioRecomanacio
{
    private final Vector<Integer> recomanacioCBF = new Vector<>();

    private float categDistance(List<String> aList, List<String> bList, float pond)
    {
        float sum = 0;
        if (bList != null && aList != null)
        {
            for (String s : aList)
            {
                float aux = 0;
                for (int j = 0; j < bList.size(); ++j)
                {
                    if (Objects.equals(s, aList.get(j)))
                    {
                        aux = 1;
                        break;
                    }
                }
                sum += aux*pond;
            }

            //  System.out.println(sum / (aList.size() + bList.size()));
            return sum / (aList.size() + bList.size());
        }
        return 0;
    }

    private float floatDistance(float a, float b)
    {
        float aux = a-b;
        return aux*aux;
    }

    private float boolDistance(boolean a, boolean b)
    {
        float af, bf;
        af= 0;
        bf = 0;
        if (a) af = 1;
        if (b) bf = 1;
        float aux = af-bf;
        return aux*aux;
    }

    private float itemDistance(Item a, Item b)
    {
        float d = 0;
        //fiquem a claus totes les claus dels atributs dels items
        Vector<String> claus = new Vector<>(a.getCjtAtributs().keySet());
        for (String key : claus)
        {
            String nom = a.getCjtAtributs().get(key).getClass().getSimpleName();

            if (categorics.esCategoric(key))
            {
                //System.out.println("Sumem categòrics "+key);
                List<String> aList = new ArrayList<>();
                List<String> bList = new ArrayList<>();
                if (nom.equals("ArrayList"))
                {
                    aList = (List<String>) a.getCjtAtributs().get(key);
                }
                else
                {
                    aList.add((String) a.getCjtAtributs().get(key));
                }
                if (b.getCjtAtributs().get(key).getClass().getSimpleName().equals("ArrayList"))
                {
                    bList = (List<String>) b.getCjtAtributs().get(key);
                }
                else
                {
                    bList.add((String) b.getCjtAtributs().get(key));
                }

                float pond = categorics.getCategorics().get(key);

                d += categDistance(aList, bList, pond);
            }
            else if (nom.equals("Boolean"))
            {
                //System.out.println("Sumem bools");
                d += boolDistance((boolean)a.getCjtAtributs().get(key), (boolean)b.getCjtAtributs().get(key));
               // System.out.println(aux);
            }
            else if (nom.equals("Integer"))
            {
                //System.out.println("Sumem ints");
                int x, y;
                x = (int) a.getCjtAtributs().get(key);
                y = (int) b.getCjtAtributs().get(key);
                float af, bf;
                af = (float) x;
                bf= (float) y;
                d += floatDistance(af, bf);
               // System.out.println(aux);
            }
            else if (nom.equals("Double"))
            {
                //System.out.println("Sumem doubles");
                double x, y;
                float af, bf;
                x = (double) a.getCjtAtributs().get(key);
                y = (double) b.getCjtAtributs().get(key);
                af = (float) x;
                bf= (float) y;
                d += floatDistance(af, bf);
               // System.out.println(aux);
            }
            else if (nom.equals("Float"))
            {
               // System.out.println("Sumem floats");
                float af = (float) (a.getCjtAtributs().get(key));
                float bf = (float) (b.getCjtAtributs().get(key));
                d += floatDistance(af, bf);
               // System.out.println(aux);
            }
        }
       // System.out.println(d);
        return d;
    }

    /**
     * funció que retorna els items que Content Based ha catal·logat com els millors
     * @param k precisió de l'algoritme
     * @return vector amb els identificadors dels items recomanats
     */
    public Vector<Integer> getRecomanacio(int k)
    {
        kNN(k);
        return recomanacioCBF;
    }

    private void kNN(int k)
    {
        Map<Integer, Float> recomanacions = new HashMap<>();
        for (Integer key : cjtValoracions.getCjtValoracions().keySet())
        {
            Item itemValoracio = dS.get(key);
            Map<Integer, Float> kRecomanacions = new HashMap<>();

            //Recorrem el dataset i fem les distancies
            for (Item itemPreprocessat : dS.values())
            {
                if (itemPreprocessat.getId() != itemValoracio.getId())
                {
                    float s = itemDistance(itemValoracio, itemPreprocessat);
                    kRecomanacions.put(itemPreprocessat.getId(), s);
                }
            }

            //ordenem el map
            List<Map.Entry<Integer, Float>> list = new LinkedList<>(kRecomanacions.entrySet());

            list.sort(Map.Entry.comparingByValue());

            for (int i = 0; i < k; ++i)
            {
                int id = list.get(i).getKey();
                float val = list.get(i).getKey();
                    recomanacions.put(id, val);
            }
        }

        //ordenem el map
        List<Map.Entry<Integer, Float>> list = new LinkedList<>(recomanacions.entrySet());

        list.sort(Map.Entry.comparingByValue());

        for (Map.Entry<Integer, Float> integerFloatEntry : list)
        {
            recomanacioCBF.add(integerFloatEntry.getKey());
        }
    }
}