package org.engcia.Components;



public class Storage extends Component{
    public boolean isSSD = false;
    public String capacityGB = ""; // GB
    // NÃO ESTÁ BEM, PASSAR TUDO PARA VALORES, PARA UTILIZARMOS NAS REGRAS (ALTERAR DEPOIS, TAMBÉM, NO BOOSTRAP EM JAVA E PROLOG) !!!
    public String cacheGB = ""; // GB
    public String benchmarkScore = ""; // Mb/s

    public Storage(){
        super();
    }

    @Override
    public String toString()
    {
        if (isSSD) return "SSD " + capacityGB;
        return "HDD " + capacityGB;
    }
}
