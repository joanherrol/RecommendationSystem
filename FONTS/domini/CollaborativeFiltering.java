package domini;

import java.util.Vector;
import java.util.*;

/**
 * Classe que s’encarrega d’executar l’algoritme de recomanació Collaborative Filtering
 */

public class CollaborativeFiltering extends GestioRecomanacio 
{
    //distancia a la que volem arribar entre les mitjanes de la funcio kMeans(k)
    private static Float d;
    private Map<Integer, Float> mitgesAct = new HashMap<>();
    private final float MAXNUMBER = 999999999999.f;

    /**
     * Retorna la recomanació un cop ja calculada
     * @param k precisió de l'algoritme
     * @return recomanació
     */
    public Map<Integer,Float> getRecomanacio(int k)
    {
        return executeAlgorithm(k);
    }

    private Vector<Integer> calculMillorCentroide(Map<Float,Vector<Integer>> centroides) 
    {
        float minDist = MAXNUMBER;
        float distAct;
        Set<Float> keysC = centroides.keySet();
        Iterator<Float> itC = keysC.iterator();
        Float cent, centBo;
        centBo = 0.f;
        while (itC.hasNext())
        {
            cent = itC.next();
            distAct = userDistance(cent, userMean(cjtValoracions));
            if (distAct < minDist)
            {
                minDist = distAct;
                centBo = cent;
            }
        }
        return centroides.get(centBo);
    }

    private Map<Integer,Float> executeAlgorithm(int k) 
    {
        Map<Float,Vector<Integer>> centroides = kMeans(k);

        Vector<Integer> v = calculMillorCentroide(centroides);
        Map<Integer, CjtValoracions> subcjt = new HashMap<>();

        //emplenar el subconjunt a passar al slope one
        for (int i = 0; i < v.size(); ++i)
        {
            subcjt.put(v.elementAt(i),vExternes.get(v.elementAt(i)));
        }

        return slopeOne(subcjt);
    }

    /**
     * seteja la distancia a la que calcularem els centroides
     * @param dist distancia dels centroides
     */
    public void setD(Float dist)
    {
        d = dist/10;
    }

    //calcul aleatori del centroide
    //s'haura de contar amb una excepció per si la k és major a n
    private void calculCentroides(Map<Float,Vector<Integer>> centroides, Integer k) 
    {
        Set<Integer> keysVExt= vExternes.keySet();
        Iterator<Integer> it = keysVExt.iterator();
        //en aquest for agafrem els k primers elements de valoracions externes com a centroides
        int i = 0;
        while (i < k && it.hasNext())
        {
            Vector<Integer> element = new Vector<>();
            Integer id = it.next();
            element.add(id);
            centroides.put(mitgesAct.get(id),element);
            ++i;
        }
    }

    private Map<Integer,Boolean> firstIt() 
    {
        Set<Integer> k = vExternes.keySet();
        Iterator<Integer> it = k.iterator();
        Map<Integer,Boolean> aux = new HashMap<>();
        while (it.hasNext())
        {
            Integer i = it.next();
            aux.put(i,true);
        }
        return aux;
    }

    private Map<Integer,Float> centroidesInit() 
    {
        Set<Integer> valExternes = vExternes.keySet();
        Iterator<Integer> it = valExternes.iterator();
        Map<Integer,Float> centroides = new HashMap<>();
        while (it.hasNext())
        {
            Integer i = it.next();
            centroides.put(i,0.f);
        }
        return centroides;
    }

    private float userDistance(Float MitjaA, Float MitjaB) 
    {
        float aux =  (MitjaA-MitjaB);
        return aux*aux;
    }

    private float userMean(CjtValoracions a)
    {
        float sum = 0;
        for (Float val : a.getCjtValoracions().values())
        {
            sum += val;
        }
        return sum/(a.getCjtValoracions().size());
    }

    private Map<Integer,Float> emplenarMitjesUsuaris()
    {

        Set<Integer> valExternes = vExternes.keySet();
        Iterator<Integer> it = valExternes.iterator();
        Map<Integer,Float> mitjes = new HashMap<>();
        while (it.hasNext())
        {
            Integer i = it.next();
            mitjes.put(i,userMean(vExternes.get(i)));
        }
        return mitjes;
    }

    private boolean canviCentroides(Map<Float,Vector<Integer>> centroides, boolean stop)
    {
        //canvi de centroides
        int total;
        Set<Float> keysC = centroides.keySet();
        Vector<Float> auxiliar = new Vector<>(keysC);
        stop = true;

        for (int i2 = 0; i2 < auxiliar.size(); ++i2)
        {
            Float average = 0.f;
            Float meanAnt = auxiliar.elementAt(i2);
            Vector<Integer> v = new Vector<>(centroides.get(meanAnt));
            total = v.size();
            for (int i = 0; i < total; ++i)
            {
                average += mitgesAct.get(v.elementAt(i));
            }
            average = average/total;

            if (Math.abs(average - meanAnt) > d)
            {
                stop = false;
                Vector<Integer> vC = new Vector<>();
                centroides.remove(auxiliar.elementAt(i2));
                centroides.put(average, vC);
            }
        }
        return stop;
    }

    private void canviCentroideAct(Set<Float> keysC,float centAct,Integer vExt, Float minDist, Float centAnt, Map<Integer, Float> centroidesAct, Map<Float,Vector<Integer>> centroides) 
    {
     //s'ha de canviar per el verdader centroide actual (depenent del conjunt en el que estigui)
        for (Float cent : keysC)
        {
            //no farem la distancia amb un mateix
            float actDist = userDistance(mitgesAct.get(vExt), cent);
            if (actDist < minDist)
            {
                minDist = actDist;
                centAct = cent;
            }
        }

        if (!Objects.equals(centAct, centAnt))
        {
            centroidesAct.put(vExt, centAct);
            centroides.get(centAct).add(vExt);
        }
    }


    private void reCalcular(Set<Integer> keysVExt, Map<Float,Vector<Integer>> centroides) 
    {
        Iterator<Integer> it = keysVExt.iterator();
        //és la primera it?
        Map<Integer, Boolean> firstIt = firstIt();
        //centroides de cada un (inicialitzem a 0 ja que no existeix un usuari amb aquest id)
        Map<Integer, Float> centroidesAct = centroidesInit();
        //minima distancia de cada usuari
        Float centAct;
        Float centAnt;
        Float minDist;

        Set<Float> keysC = centroides.keySet();

        //mentres hi hagi usuaris amb valoracions externes
        while (it.hasNext())
        {
            Integer vExt = it.next();
            if (firstIt.get(vExt))
            {
                firstIt.put(vExt, false);
                if (centroides.containsKey(mitgesAct.get(vExt)))
                {
                    centAct = mitgesAct.get(vExt);
                    centAnt = mitgesAct.get(vExt);
                }
                else
                {
                    centAct = 0.f;
                    centAnt = 0.f;
                }
            } 
            else
            {
                centAct = centroidesAct.get(vExt);
                centAnt = centroidesAct.get(vExt);
            }
            minDist = mitgesAct.get(vExt);
            //mirarem per a cada centroide

           canviCentroideAct(keysC,centAct, vExt,minDist, centAnt,centroidesAct,centroides);
        }
    }

    //separem les valoracions del fitxer en clusters
    private Map<Float,Vector<Integer>> kMeans(int k) 
    {
        //PAS 1: triem "k" centroides aleatoris, en el nostre cas per ordre d'arribada
        //getSize retorna el nombre d'usuaris diferents que hi ha a el fitxer de valoracions
        Set<Integer> keysVExt = vExternes.keySet();
        boolean stop = false;

        mitgesAct = emplenarMitjesUsuaris();

        //map<mitja del centroide,vector<id>>
        Map<Float,Vector<Integer>> centroides = new HashMap<>();
        calculCentroides(centroides,k);

        while (!stop)
        {
            reCalcular(keysVExt,centroides);
            stop = canviCentroides(centroides, stop);
        }

        //un cop tenim tots els centroides hem de mirar quin té minima distancia amb l'usuari
        return centroides;
    }

    private Vector<Integer> emplenarObjSCjt(Map<Integer,CjtValoracions> subCjt) 
    {
        Vector<Integer> objScjt = new Vector<>();
        Set<Integer> subCjtId = subCjt.keySet();
        for (Integer key : subCjtId)
        {
            Set<Integer> idObjValExt = vExternes.get(key).getCjtValoracions().keySet();
            for (Integer key2 : idObjValExt)
            {
                if (!objScjt.contains(key2)) objScjt.add(key2);
            }
        }
        return objScjt;
    }

    //pre: arriba un subconjunt de les valoracions del fitxer extern
    //i les valoracions del mainUser
    //tenim el map<idUsuari,map<idObj,valoracio>>
    //article
    //suposem que k-means li pasa el cluster amb mes proximitat al usuari a slope one
    ///u vector amb valoracions de mainUser
    private Map<Integer,Float> slopeOne(Map<Integer,CjtValoracions> subCjt) 
    {
 
        Map<Integer,Float> pred = new HashMap<>();

        Vector<Integer> objScjt = emplenarObjSCjt(subCjt);

        //Calcular dev
        Set<Integer> cjtVal = cjtValoracions.getCjtValoracions().keySet();
        Integer idItemD;

        //primer for per mirar els items que el mainUser no ha valorat quan trobem un ens parem
        for (int q = 0; q < objScjt.size(); ++q)
        {
            idItemD = objScjt.elementAt(q);
            float totU = 0.f;
            float resultat = 0.f;
            boolean parche = false;
            if (!cjtValoracions.getCjtValoracions().containsKey(idItemD))
            {
                //recorrem tots els usuaris i anem fent 1-2, 1-3, 1-4, etc
                //mentres hi hagi valoracions del usuari
                for (Integer idItemDS2 : cjtVal)
                {
                    Set<Integer> key = subCjt.keySet();
                    Iterator<Integer> itSubC = key.iterator();
                    float c = 0;
                    float desv = 0;
                    boolean valorated = false;
                    while (itSubC.hasNext())
                    {
                        Integer idSubCjt = itSubC.next();
                        CjtValoracions cjtV = subCjt.get(idSubCjt);
                        //cjtV és el subconjunt de valoracions del cluster del kmeans
                        if (cjtV.existeixValoracio(idItemD) && cjtV.existeixValoracio(idItemDS2))
                        {
                            valorated = true;
                            ++c;
                            float val1, val2;
                            val1 = cjtV.getVal(idItemD);
                            val2 = cjtV.getVal(idItemDS2);
                            float a = (val1 - val2);
                            desv = a + desv;
                        }
                    }
                    if (valorated)
                    {
                        desv = (desv / c + cjtValoracions.getVal(idItemDS2)) * c;
                        resultat = resultat + desv;
                        totU = totU + c;
                        parche = true;
                    }
                }

                if (parche)
                {
                    resultat = resultat / totU;
                    pred.put(idItemD,resultat);
                }
            }
        }
        return sortByValue(pred);
    }

    private Map<Integer,Float> sortByValue(Map<Integer,Float> unsortMap) 
    {
        List<Map.Entry<Integer,Float>> list = new LinkedList<>(unsortMap.entrySet());

        list.sort((o1, o2) -> -(o1.getValue()).compareTo(o2.getValue()));

        Map<Integer,Float> sortedMap = new LinkedHashMap<>();
        for (Map.Entry<Integer,Float> entry : list)
        {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;
    }
}