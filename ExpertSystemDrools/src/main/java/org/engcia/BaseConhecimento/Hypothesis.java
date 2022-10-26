package org.engcia.BaseConhecimento;

import org.engcia.Components.GPU.GPUBrand;
import org.engcia.Components.Storage;

public class Hypothesis {

    private int minBudget = 0; // opcional
    private int maxBudget = 0;
    public BudgetType budgetType;
    public Finality finality;

    public Semi_Finality semi_finality;

    public int durationDayChoice = 0;

    public Storage storage = new Storage();
    public Storage scndStorage = new Storage();
    public int finalityEstimatedDurationTime = 0;
    public int minRAM = 0;
    public int minRAMPreferred = 0;

    public boolean needsDedicatedGPU = false;
    public boolean prefersDedicatedGPU = false;
    public GPUBrand gpuBrandPreffered = GPUBrand.NA;
    public String caseColorPreferred = "";

    public Hypothesis() {
        super();
    }

    public enum Semi_Finality{
        NAVEGACAO_WEB,
        APLICACOES_OFFICE,
        APLICACOES_PROFISSIONAIS,

        JOGOS_BASICOS,

        PROGRAMAS_AVANÇADOS,

        GAMING,

        TRATAMENTO_DE_IMAGEM,

        EDIÇAO_DE_VIDEO_4k_8K
    }
    public enum Finality{
        NAVEGACAO_WEB_SIMPLES,
        NAVEGACAO_WEB_COMPLEXO,
        APLICACOES_OFFICE_BASICO,
        APLICACOES_OFFICE_PROFISSIONAL,
        APLICACOES_PROFISSIONAIS_SEM_BASE_DADOS,
        APLICACOES_PROFISSIONAIS_COM_BASE_DADOS,

        JOGOS_BASICOS,

        PROGRAMAS_AVANÇADOS_MODELACAO_3D,

        PROGRAMAS_AVANÇADOS_DESIGN_GRAFICO,

        PROGRAMAS_AVANÇADOS_CALCULO_MATEMATICO,

        GAMING,

        TRATAMENTO_DE_IMAGEM,

        EDIÇAO_DE_VIDEO_4k_8K
    }

    public enum BudgetType {
        BAIXO,
        MEDIO_BAIXO,
        MEDIO,
        MEDIO_ALTO,
        ALTO
    }
}
