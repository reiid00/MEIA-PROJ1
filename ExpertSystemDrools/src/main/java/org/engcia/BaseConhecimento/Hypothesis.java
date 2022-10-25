package org.engcia.BaseConhecimento;

import org.engcia.Components.GPU.GPUBrand;
import org.engcia.Components.Storage;

public class Hypothesis {
    public BudgetType budgetType;
    public Finality finality;

    public int durationDayChoice = 0;

    public Storage storage = new Storage();
    public Storage scndStorage = new Storage();
    private int finalityEstimatedDurationTime = 0;
    private int minRAM = 0;
    private int minRAMPreferred = 0;
    private boolean needsDedicatedGPU = false;
    private boolean prefersDedicatedGPU = false;
    private GPUBrand gpuBrandPreffered = GPUBrand.NA;
    private String caseColorPreferred = "";

    public Hypothesis() {
        super();
    }

    public enum Finality{
        NAVEGACAO_WEB_SIMPLES,
        NAVEGACAO_WEB_COMPLEXO,
        APLICACOES_OFFICE_BASICO,
        APLICACOES_OFFICE_PROFISSIONAL,
        APLICACOES_PROFISSIONAIS_SEM_BASE_DADOS,
        APLICACOES_PROFISSIONAIS_COM_BASE_DADOS,
    }

    public enum BudgetType {
        BAIXO,
        MEDIO_BAIXO,
        MEDIO,
        MEDIO_ALTO,
        ALTO
    }
}
