// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Facadas.java


class matadores
{

    matadores()
    {
        matador = null;
        vitima = null;
        quantidade = 0;
    }

    public String getMatador()
    {
        return matador;
    }

    public String getVitima()
    {
        return vitima;
    }

    public int getQuantidade()
    {
        return quantidade;
    }

    public void setMatador(String matador)
    {
        this.matador = matador;
    }

    public void setVitima(String vitima)
    {
        this.vitima = vitima;
    }

    public void setQuantidade(int quantidade)
    {
        this.quantidade = quantidade;
    }

    String matador;
    String vitima;
    int quantidade;
}
