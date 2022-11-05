package org.engcia.Utils;

import org.engcia.BD.Storage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Questions {

    public static final String CHOOSE_BUDGET = "Quanto pretende gastar no computador?";
    public static final String CHOOSE_FINALITY_LOW_BUDGET= "Qual é a sua finalidade? \n" +
                                                            "1. Navegação WEB \n" +
                                                            "2. Aplicações Office \n" +
                                                            "3. Aplicações Profissionais \n";

    public static final String CHOOSE_FINALITY_NON_LOW_BUDGET = "Qual é a sua finalidade? \n" +
                                                                    "1. Navegação WEB \n" +
                                                                    "2. Aplicações Office \n" +
                                                                    "3. Aplicações Profissionais \n" +
                                                                    "4. Jogos Básicos \n" +
                                                                    "5. Programas Avançados \n" +
                                                                    "6. Gaming \n" +
                                                                    "7. Tratamento de Imagem \n" +
                                                                    "8. Edição de Vídeo 4K a 8K \n";
    public static final String CHOOSE_FINALITY_NAVEGACAO_WEB = "Pretende ter uma utilização com muitas ou poucas janelas? \n" +
                                                                "1. Poucas janelas. \n" +
                                                                "2. Muitas janelas. \n";
    public static final String CHOOSE_FINALITY_APLICACOES_OFFICE = "Pretende utilizar o Office de forma básica ou profissional estas apliacações? \n" +
                                                                    "1. Forma Básica. \n" +
                                                                    "2. Profissional. \n";
    public static final String CHOOSE_FINALITY_APLICACOES_PROFISSIONAIS = "Pretende realizar regularmente operações de pesquisa e modificação à base de dados? \n" +
                                                                            "1. Vou utilizar Base de Dados. \n" +
                                                                            "2. Não vou utilizar Base de Dados. \n";

    public static final String CHOOSE_FINALITY_TYPE_OF_DATA_BASE = "Que tipo de Base de Dados irá utilizar? \n" +
                                                                    "1. Base de Dados Local \n" +
                                                                    "2. Base de Dados Remotas \n" +
                                                                    "3. Ambas \n";
    public static final String CHOOSE_FINALITY_PROGRAMAS_AVANCADOS = "Que tipo de programas pretende utilizar? \n" +
                                                                        "1. Modelação 3D. \n" +
                                                                        "2. Design Gráfico. \n" +
                                                                        "3. Calculo Matemático. \n";

    public static final String CHOOSE_FILE_TYPE = "Que tipo de ficheiros irão ser mais utilizados? \n" +
                                                    "1. Word/excel/PowerPoint \n" +
                                                    "2. Imagens \n";

    public static final String CHOOSE_DURATION_PER_DAY = "Quanto tempo, em média, irá utilizar o computador por dia? \n" +
                                                            "1. 2 a 4 horas \n" +
                                                            "2. Mais de 4 horas \n";

    public static final String CHOOSE_OPTIONAL_CASE_COLOR = "Tem preferência na cor da caixa? \n" +
            "1. Sim \n" +
            "2. Não \n";
    public static final String CHOOSE_CASE_COLOR = "Qual das cores prefere?\n" +
            "1. Preto \n" +
            "2. Branco \n";
    public static final String CHOOSE_CASE_SIZE = "Pretende que tamanho de caixa?\n" +
            "1. Normal \n" +
            "2. Pequena \n"+
            "3. Muito pequena \n";

    public static final String CHOOSE_ENVIRONMENT_ENERGY_EFFICIENCY_INSTABILITY = "Considera o ambiente (casa/trabalho) onde pretende utilizar o PC como sendo instável/ineficiente energeticamente?\n" +
            "1. Não \n" +
            "2. Sim, um pouco problemático \n"+
            "3. Sim, é um problema muito grave \n";

    public static final String CHOOSE_GPU_MANUFACTURER = "Que fabricante de placas gráficas prefere? \n" +
                                            "1. NVIDIA \n" +
                                            "2. AMD \n" +
                                            "3. Indiferente \n";

    public static final String CHOOSE_IF_WANTS_DEDICATED_GRAPHICS_CARD = "Pretende realizar trabalhos mais pesados a nível gráfico (jogos, sistemas 3D)? \n" +
                                                                        "1. Sim \n" +
                                                                        "2. Nao \n";

    public static final String CHOOSE_IF_WANTS_PREFERRED_GPU_BRAND = "Tem preferência na marca da placa gráfica? \n" +
                                                                    "1. Sim, pretendo ASUS \n" +
                                                                    "2. Sim, pretendo EVGA \n" +
                                                                    "3. Sim, pretendo Gigabyte \n" +
                                                                    "4. Sim, pretendo MSI \n" +
                                                                    "5. Sim, pretendo Sapphire \n" +
                                                                    "6. Nao \n";

    public static final String CHOOSE_IMAGE_TREATMENT_TYPE = "Pretende utilizar estas aplicações de forma básica ou profissional? \n" +
                                                            "1. Forma Básica. \n" +
                                                            "2. Profissional. \n";

    public static final String CHOOSE_INITIAL_STORAGE = "Pretende modificar o tamanho de armazenamento ? (SSD) Atualmente o Sistema sugeriu o tamanho ideal de XXX GB  \n"+
                                                                "1. Sim, aumentar para o dobro da capacidade \n" +
                                                                "2. Nao \n";

    public static final String CHOOSE_2ND_STORAGE = "Pretende adicionar/modificar um segundo local de armazenamento? (HDD) \n"+
                                                    "1. Sim, pretendo adicionar um disco rígido de 1TB  \n" +
                                                    "2. Sim, pretendo adicionar um disco rígido de 2TB  \n" +
                                                    "3. Sim, pretendo adicionar um disco rígido de 4TB  \n" +
                                                    "4. Nao \n";

    public static final String CHOOSE_RAM = "Pretende modificar o tamanho da RAM?  Atualmente o Sistema sugeriu o tamanho ideal de XXX GB \n "+
                                                "1. Sim, pretendo aumento para o dobro da capacidade \n" +
                                                "2. Nao \n";

    public static final String CHOOSE_CPU_MANUFACTURER =  "Que fabricante de processador prefere? \n" +
                                                            "1. INTEL \n" +
                                                            "2. AMD \n" +
                                                            "3. Indiferente \n";

    public static final String CHOOSE_CPU_OC = "Pretende fazer OverClock ao CPU? \n"+
                                                "1. Sim \n" +
                                                "2. Nao \n";

    public static final String CHOOSE_COOLER_TYPE = "Que tipo de CPU Cooler pretende? \n"+
                                                "1. Water Cooling \n" +
                                                "2. Fanless \n" +
                                                "3. Normal \n" +
                                                "4. Indiferente \n" ;



    public static List<Storage> questionStorage(Storage storage) throws IOException {

        List<Storage> storageList = new ArrayList<>();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("O sistema sugeriu o disco ideal, pretende modificá-lo/adicionar um novo?");
        System.out.println("Disco Ideal " + storage);
        System.out.println();
        System.out.println("1. Modificar / Adicionar um novo");
        System.out.println("2. manter");
        int choice = Integer.parseInt(br.readLine());
        if (choice == 1)
        {
            System.out.println("Pretende modificar o disco ou adicionar um novo?");
            System.out.println("1. Modificar");
            System.out.println("2. Adicionar um novo");
            choice = Integer.parseInt(br.readLine());
            if(choice == 1)
            {
                System.out.println("Indique o tipo de Disco que pretende");
                System.out.println("1. SSD");
                System.out.println("2. HDD");
                int typeChoice = Integer.parseInt(br.readLine());
                if (typeChoice == 1) storage.isSSD = true;
                else storage.isSSD = false;
                System.out.println("Indique o tamanho pretendido (GB)");
                int sizeChoice = Integer.parseInt(br.readLine());
                storage.capacity = sizeChoice;
                storageList.add(storage);
                return storageList;
            } else if (choice == 2) {
                storageList.add(storage);
                Storage s2 = new Storage();
                s2.isSSD = true;
                if (storage.isSSD) s2.isSSD = false;
                if(s2.isSSD) System.out.println("Indique o tamanho pretendido (GB) para o SSD");
                else System.out.println("Indique o tamanho pretendido (GB) para o HDD");
                s2.capacity = Integer.parseInt(br.readLine());
                storageList.add(s2);
                return storageList;
            }

        }
        storageList.add(storage);
        return storageList;
    }
}
