package org.engcia.BC;

import org.engcia.BD.CPUCooler;
import org.engcia.BD.Case.TowerSizeType;
import org.engcia.BD.GPU.GPUBrand;
import org.engcia.BD.PowerSupply.EnergyEfficiency;
import org.engcia.BD.Storage;

public class Hypothesis {

    public Integer minBudget = null; // opcional
    public Integer maxBudget = null;
    public BudgetType budgetType = BudgetType.NA;
    public Finality finality = Finality.NA;
    public Integer durationDayChoice = null;
    public Storage minStorage = null;
    public Storage minScndStorage = null;
    public Integer finalityEstimatedDurationTime = null;
    public Integer minRAM = null;
    public Integer minRAMSpeed = null;
    public Integer minRAMPreferred = null;
    public Boolean needsDedicatedGPU=null;
    public Boolean prefersDedicatedGPU=null ;
    public GPUBrand gpuManufacturerPreferred = GPUBrand.NA;
    public String cpuManufacturerPreferred = null;
    public Boolean needsCPUCooler = null;
    public CPUCooler cpuCooler =null;
    public String preferredGPUBrand = null;
    public Integer adequateMinCPUBenchmark = null;
    public Integer adequateMinGPUBenchmark = null;
    public String caseColorPreferred = null;
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
