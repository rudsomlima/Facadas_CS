// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Facadas.java

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class Facadas
{

    public Facadas()
    {
    }

    public static boolean contem(List<matadores> mt, matadores mt2)
    {
        for(Iterator<matadores> iterator = mt.iterator(); iterator.hasNext();)
        {
            matadores matadores = (matadores)iterator.next();
            if(matadores.getMatador().equalsIgnoreCase(mt2.getMatador()) && matadores.getVitima().equalsIgnoreCase(mt2.getVitima()))
                return true;
        }

        return false;
    }

    public static int indice(List<matadores> mt, matadores mt2)
    {
        int indice = -1;
        for(Iterator<matadores> iterator = mt.iterator(); iterator.hasNext();)
        {
            matadores matadores = (matadores)iterator.next();
            indice++;
            if(matadores.getMatador().equalsIgnoreCase(mt2.getMatador()) && matadores.getVitima().equalsIgnoreCase(mt2.getVitima()))
                return indice;
        }

        return -1;
    }

    public static void main(String args[])
    {
        String dir = args[0];
        File diretorio = new File(dir);
        File fList[] = diretorio.listFiles();
        List<matadores> facadas = new ArrayList<matadores>();
        try
        {
            int arquivo = 0;
            String log = null;
            for(int i = 0; i < fList.length; i++)
            {
                BufferedReader br = null;
                if(fList[i].getName().contains(".log"))
                {
                    log = fList[i].toString();
                    br = new BufferedReader(new FileReader(log));
                }
                while(br != null && br.ready()) 
                {
                    String linha = br.readLine();
                    if(linha.contains("with \"knife\""))
                    {
                        String matador = linha.substring(linha.indexOf("\"") + 1, linha.indexOf("<"));
                        String subLinha = linha.substring(linha.indexOf("killed \""));
                        String vitima = subLinha.substring(subLinha.indexOf("\"") + 1, subLinha.indexOf("<"));
                        matadores killer = new matadores();
                        killer.setMatador(matador.trim());
                        killer.setVitima(vitima.trim());
                        if(contem(facadas, killer))
                        {
                            int index = indice(facadas, killer);
                            ((matadores)facadas.get(index)).setQuantidade(((matadores)facadas.get(index)).getQuantidade() + 1);
                        } else
                        {
                            killer.setQuantidade(1);
                            facadas.add(killer);
                        }
                    }
                }
                if(br != null)
                    br.close();
            }

            Collections.sort(facadas, new Comparator<Object>() {

                public int compare(Object o1, Object o2)
                {
                    matadores m1 = (matadores)o1;
                    matadores m2 = (matadores)o2;
                    if(m1.getMatador().equalsIgnoreCase(m2.getMatador()) && m1.getVitima().equalsIgnoreCase(m2.getVitima()))
                        return 0;
                    else
                        return m1.getMatador().compareTo(m2.getMatador());
                }

            }
);
            File file = new File(args[0].concat("/facadas.html"));
            if(!file.exists())
                file.createNewFile();
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("<!DOCTYPE html><html lang=\"en\"><head><title>Facadas</title></head><body>");
            bw.write("<table border=\"1\"  width=\"100%\"><tr align=\"center\">  <th BGCOLOR=\"#99CCFF\">Esfaqueador</th>  <th BGCOLOR=\"#99CCFF\">Nome da Vitima</th>  <th BGCOLOR=\"#99CCFF\">Facadas</th></tr>");
            int total = 0;
            String matadorAtual = null;
            boolean primeira_linha = true;
            for(int index = 0; index < facadas.size(); index++)
            {
                matadores matadores = (matadores)facadas.get(index);
                matadorAtual = matadores.getMatador();
                if(primeira_linha)
                {
                    bw.write((new StringBuilder("<tr align=\"center\"><td>")).append(matadores.getMatador()).append("</td>").toString());
                    primeira_linha = false;
                } else
                {
                    bw.write("<td></td>");
                }
                String proximoMatadorS = "";
                if(index + 1 < facadas.size())
                    proximoMatadorS = ((matadores)facadas.get(index + 1)).getMatador();
                else
                    proximoMatadorS = "Ultimo";
                if(matadorAtual != null && !matadorAtual.equalsIgnoreCase(proximoMatadorS))
                {
                    total += matadores.getQuantidade();
                    System.out.println((new StringBuilder(String.valueOf(matadores.getMatador()))).append(" matou ").append(matadores.getVitima()).append(' ').append(matadores.getQuantidade()).append(" vezes!!!").toString());
                    bw.write((new StringBuilder("<td align=\"center\">")).append(matadores.getVitima()).append("</td>").append("<td align=\"center\">").append(matadores.getQuantidade()).append("</td>").append("</tr>").toString());
                    System.out.print((new StringBuilder("\t\tTotal para ")).append(matadorAtual).append(':').append(total).append('\n').toString());
                    bw.write((new StringBuilder("<tr ><th COLSPAN=2 BGCOLOR=\"#99CCFF\" align=\"right\">Total Facadas do ")).append(matadorAtual).append(":</th>").append("<td BGCOLOR=\"#99CCFF\" align=\"center\">").append(total).append("</td>").append("</tr>").toString());
                    primeira_linha = true;
                    total = 0;
                } else
                {
                    total += matadores.getQuantidade();
                    System.out.println((new StringBuilder(String.valueOf(matadores.getMatador()))).append(" matou ").append(matadores.getVitima()).append(' ').append(matadores.getQuantidade()).append(" vezes!!!").toString());
                    bw.write((new StringBuilder("<td align=\"center\">")).append(matadores.getVitima()).append("</td>").append("<td align=\"center\">").append(matadores.getQuantidade()).append("</td>").append("</tr>").toString());
                }
            }

            bw.write("</table></body></html>");
            bw.close();
            Process pro = Runtime.getRuntime().exec((new StringBuilder("cmd.exe /c \"")).append(file.getAbsolutePath()).append("\"").toString());
            pro.waitFor();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
        }
    }
}
