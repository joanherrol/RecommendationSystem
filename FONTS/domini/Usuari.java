package domini;

import java.util.Objects;

public class Usuari
{
    private int id;
    private String nom;
    private int edat;
    private String email;
    private  String password;
    private CjtValoracions cjtValoracions;
    private CjtFiltres cjtFiltres;

    public Usuari()
    {
        this.id = -1;
        this.nom = null;
        this.edat = 0;
        this.email = null;
        this.password = null;
        this.cjtValoracions = null;
        this.cjtFiltres = null;
    }

    public Usuari (int id, String nom, int edat, String email, String password)
    {
        this.id = id;
        this.nom = nom;
        this.edat = edat;
        this.email = email;
        this.password = password;
        this.cjtValoracions = new CjtValoracions();
        this.cjtFiltres = new CjtFiltres();
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public void setNom(String nom)
    {
        this.nom = nom;
    }

    public void setEdat(int edat)
    {
        this.edat = edat;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public void setCjtValoracions(CjtValoracions cjtValoracions)
    {
        this.cjtValoracions = cjtValoracions;
    }

    public void setCjtFiltres(CjtFiltres cjtFiltres)
    {
        this.cjtFiltres = cjtFiltres;
    }

    public int getId()
    {
        return id;
    }

    public String getNom()
    {
        return nom;
    }

    public int getEdat()
    {
        return edat;
    }

    public String getEmail()
    {
        return email;
    }

    public String getPassword()
    {
        return password;
    }

    public CjtValoracions getCjtValoracions()
    {
        return cjtValoracions;
    }

    public CjtFiltres getCjtFiltres()
    {
        return cjtFiltres;
    }

    public boolean majorEdat()
    {
        return (edat >= 18);
    }

    public boolean comprovaPassword(String password)
    {
        return Objects.equals(password, this.password);
    }
}
