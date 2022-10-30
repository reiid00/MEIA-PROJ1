package org.engcia.BC;

import org.engcia.BD.Case.TowerSizeType;
import org.engcia.BD.GPU.GPUBrand;
import org.engcia.BD.PowerSupply.EnergyEfficiency;
import org.engcia.BD.Storage;

public class Hypothesis {


    public int minBudget = 0; // opcional
    public int maxBudget = 0;
    public BudgetType budgetType = BudgetType.NA;
    public Finality finality = Finality.NA;

    public int durationDayChoice = 0;

    public Storage minStorage = new Storage();
    public Storage minScndStorage = new Storage();
    public int finalityEstimatedDurationTime = 0;
    public int minRAM = 0;
    public int minRAMSpeed = 0;
    public int minRAMPreferred = 0;


    public boolean needsDedicatedGPU = false;
    public boolean prefersDedicatedGPU = false;
    public GPUBrand gpuBrandPreferred = GPUBrand.NA;

    public boolean wantsPreferredGPUBrand = false;

    public String preferredGPUBrand = "";


    public int adequateMinCPUBenchmark = 0;


    public int adequateMinGPUBenchmark = 0;
    public String caseColorPreferred = "";
    public TowerSizeType caseSizePreferred = TowerSizeType.NA;

    public EnergyEfficiency minEnergyEfficiencyNeeded = EnergyEfficiency.NA;

    public Hypothesis() {
    }

    public enum Finality{
        NA,
        NAVEGACAO_WEB_SIMPLES,
        NAVEGACAO_WEB_COMPLEXO,
        APLICACOES_OFFICE_BASICO,
        APLICACOES_OFFICE_PROFISSIONAL,
        APLICACOES_PROFISSIONAIS_SEM_BASE_DADOS,

        APLICACOES_PROFISSIONAIS_COM_BASE_DADOS_LOCAIS,

        APLICACOES_PROFISSIONAIS_COM_BASE_DADOS_REMOTAS,

        JOGOS_BASICOS,

        PROGRAMAS_AVANCADOS_MODELACAO_3D,

        PROGRAMAS_AVANCADOS_DESIGN_GRAFICO,

        PROGRAMAS_AVANCADOS_CALCULO_MATEMATICO,

        GAMING,

        TRATAMENTO_DE_IMAGEM_BASICO,

        TRATAMENTO_DE_IMAGEM_PROFISSIONAL,

        EDICAO_DE_VIDEO_4k_8K
    }

    public enum BudgetType {
        NA,
        BAIXO,
        MEDIO_BAIXO,
        MEDIO,
        MEDIO_ALTO,
        ALTO
    }


}
