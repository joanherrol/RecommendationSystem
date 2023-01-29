package domini;

import java.lang.Math;
import java.util.*;

/**
 * Classe que s’encarrega d’executar els diferents algoritmes de recomanació, veure si tenim suficients valors o no per retornar i com podem solucionar-ho si la resposta és negativa, i de valorar la recomanació donada
 */
public class GestioRecomanacio
{
    //usuari que demana la recomanació
    protected Usuari mainUser = new Usuari();

    //numero que ens pasara l'usuari dient el numero de recomenacions que vol a la llista
    protected Integer m;

    private Integer tipusRecom;

    private boolean valRecomNeeded;

    private float DCG;

    private boolean suficientsItems = true;

    protected CjtCategorics categorics = new CjtCategorics();

    //valoracions del main user
    protected CjtValoracions cjtValoracions = new CjtValoracions();

    protected Map<Integer, CjtValoracions> vExternes = new HashMap<>();

    private Map<Integer,Float> valoracionsTester = new HashMap<>();

    protected Map<Integer, Item> dS = new HashMap<>();

    private Map<Integer, Float> recomanacio = new HashMap<>();

    /**
     * seteja les valoracions del usuari de tester
     * @param valoracionsTester valoracions del usuari tester
     */
    public void setValoracionsTester(Map<Integer, Float> valoracionsTester)
    {
        this.valoracionsTester = valoracionsTester;
    }

    /**
     * seteja un boolea segons si estem en mode testeig (true) o no (false)
     * @param valRecomNeeded valor al que s'igualara el boolea
     */
    public void setValRecomNeeded(boolean valRecomNeeded)
    {
        this.valRecomNeeded = valRecomNeeded;
    }

    /**
     * seteja el nombre de recomanacions demanades
     * @param m nombre de recomanacions
     */
    public void setM(Integer m)
    {
        this.m = m;
    }

    /**
     * seteja el tipus de recomanacio
     * @param tipusRecom tipus de recomanació
     */
    public void setTipusRecom(Integer tipusRecom)
    {
        this.tipusRecom = tipusRecom;
    }

    /**
     * seteja les valoracions del usuari principal
     */
    public void setCjtValoracions()
    {
        cjtValoracions = mainUser.getCjtValoracions();
    }

    /**
     * seteja l'usuari principal
     * @param user usuari principal
     */
    public void setUsuari(Usuari user)
    {
        mainUser = user;
    }

    /**
     * seteja les valoracions dels usuaris que no son el principal
     * @param val valoracions dels usuaris que no son el principal
     */
    public void setFitxerValoracions(Map<Integer, CjtValoracions> val)
    {
        vExternes = val;
    }

    /**
     * seteja els conjunt d'items categorics
     * @param categorics conjunt d'items categorics
     */
    public void setCategorics(CjtCategorics categorics)
    {
        this.categorics = categorics;
    }

    /**
     * seteja el dataset un cop ja processat
     * @param dataset dataset processat
     */
    public void setDatasetProcesat(Map<Integer, Item> dataset)
    {
        dS = dataset;
    }

    /**
     * retorna la recomanacio ja calculada
     * @return recomanacio recomanacio ja calculada
     */
    public Map<Integer, Float> getRecomanacio()
    {
        return recomanacio;
    }

    /**
     * retorna el resultat de l'avaluació de l'algoritme
     * @return avaluació de l'algoritme
     */
    public float getDCG()
    {
        return DCG;
    }

    private static float log2(int N)
    {
        return (float) (Math.log(N) / Math.log(2));
    }

    /**
     * retorna si tenim o no suficients items per retornar respecte als que ens han demanat
     * @return boolea de true si tenim suficients items, false en un altre cas
     */
    public boolean isSuficientsItems() {
        return suficientsItems;
    }

    private Map<Integer, Float> utilitzemCollaborative(Float d, Integer k)
    {
        Map<Integer, Float> Recom1 = new HashMap<>();
        CollaborativeFiltering alg1 = new CollaborativeFiltering();
        alg1.setUsuari(mainUser);
        alg1.setDatasetProcesat(dS);
        alg1.setFitxerValoracions(vExternes);
        alg1.setM(m);
        alg1.setCjtValoracions();
        alg1.setD(d);
        Recom1 = alg1.getRecomanacio(k);
        return Recom1;
    }

    private Vector<Integer> utilitzemContent(Integer k) 
    {
        Vector<Integer> Recom2 = new Vector<>();
        ContentBasedFiltering alg2 = new ContentBasedFiltering();
        alg2.setUsuari(mainUser);
        alg2.setDatasetProcesat(dS);
        alg2.setFitxerValoracions(vExternes);
        alg2.setM(m);
        alg2.setCjtValoracions();
        Recom2 = alg2.getRecomanacio(k);
        return Recom2;
    }

    private void utilizemHybrid(Map<Integer, Float> Recom1, Vector<Integer> Recom2) 
    {
        intersect(Recom2, Recom1);
        if (recomanacio.size() == 0)
        {
            union(Recom2, Recom1);
            if (m > recomanacio.size()) suficientsItems = false;
        }
    }

    private void acabemSolucioCollaborative(Map<Integer, Float> Recom1) 
    {
        if (m > Recom1.size())
        {
            suficientsItems = false;
            Vector<Integer> keys = new Vector<>(Recom1.keySet());
            for (int i = 0; i < keys.size(); ++i)
            {
                recomanacio.put(keys.elementAt(i), Recom1.get(keys.elementAt(i)));
            }
        }
        else
        {
            Vector<Integer> keys = new Vector<>(Recom1.keySet());
            for (int i = 0; i < m; ++i)
            {
                recomanacio.put(keys.elementAt(i), Recom1.get(keys.elementAt(i)));
            }
        }
    }

    private void acabemSolucioContent(Vector<Integer> Recom2) 
    { 
        if (m > Recom2.size())
            {
                suficientsItems = false;
                for (int i = 0; i < Recom2.size(); ++i)
                {
                    recomanacio.put(Recom2.elementAt(i), 0.f);
                }
            }
            else
            {
                for (int i = 0; i < m; ++i)
                {
                    recomanacio.put(Recom2.elementAt(i), 0.f);
                }
            }
    }

    /**
     * inicialitza la recomanació
     * @param k precisió de l'algoritme
     * @param d eficiència de l'algoritme
     */
    public void initRecomanacio(Integer k, Float d)
    {
        suficientsItems = true;
        recomanacio = new HashMap<>();
        Map<Integer, Float> Recom1 = new HashMap<>();
        Vector<Integer> Recom2 = new Vector<>();

        //crida als dos algoritmes
        if (tipusRecom == 1 || tipusRecom == 3)
        {
            Recom1 = utilitzemCollaborative(d,k);
        }

        if (tipusRecom == 2 || tipusRecom == 3)
        {
            Recom2 = utilitzemContent(k);
        }

        if (tipusRecom == 3)
        {
            utilizemHybrid(Recom1,Recom2);
        }
        //si era collaborative
        else if (tipusRecom == 1)
        {
            acabemSolucioCollaborative(Recom1);
        }
        //si era content
        else
        {
            acabemSolucioContent(Recom2);
        }

        if (valRecomNeeded) DCG = valRecomanacio();
    }

    //Map<Integer,Float> unknown
    private float valRecomanacio()
    {
        float suma = 0;
        int i = 1;
        float Val_ant = -1;
        for (Integer key : recomanacio.keySet())
        {
            if (valoracionsTester.containsKey(key))
            {
                suma += (Math.pow(2, valoracionsTester.get(key)) - 1) / (log2(i + 1));
                if (Val_ant != valoracionsTester.get(key)) ++i;
                Val_ant = valoracionsTester.get(key);
            }
            else suma += (0.0 - 1.0) / (log2(i + 1));
        }
        return suma;
    }

    private void union(Vector<Integer> recomanacioCBF, Map<Integer, Float> recomanacioCF)
    {
        for (Integer key : recomanacioCBF)
        {
            recomanacio.put(key,0.f);
        }
        for (Integer integer : recomanacioCF.keySet())
        {
            recomanacio.put(integer, recomanacioCF.get(integer));
        }
    }

    private void intersect(Vector<Integer> recomanacioCBF, Map<Integer, Float> recomanacioCF)
    {

        Vector<Integer> i = new Vector<>(recomanacioCF.keySet());
        HashSet<Integer> set = new HashSet<>(i);
        set.retainAll(recomanacioCBF);

        Integer[] intersection = {};
        intersection = set.toArray(intersection);

        Map<Integer, Float> sort = new HashMap<>();
        for (Integer integer : intersection)
        {
            Float value = recomanacioCF.get(integer);
            sort.put(integer, value);
        }

        List<Map.Entry<Integer, Float>> list = new LinkedList<>(sort.entrySet());

        list.sort((o1, o2) -> -(o1.getValue()).compareTo(o2.getValue()));

        if (list.size() != 0)
        {
            if (list.size() < m)
            {
                System.out.println("Només podem recomanar-te " + list.size() + " elements amb aquest settings.");
                for (Map.Entry<Integer, Float> integerFloatEntry : list)
                {
                    recomanacio.put(integerFloatEntry.getKey(), integerFloatEntry.getValue());
                }
            }
            else
            {
                for (int j = 0; j < m; ++j)
                {
                    recomanacio.put(list.get(j).getKey(), list.get(j).getValue());
                }
            }
        }
    }
}