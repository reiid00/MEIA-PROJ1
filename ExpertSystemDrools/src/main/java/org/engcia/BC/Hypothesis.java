package org.engcia.BC;

import org.engcia.BD.CPUCooler;
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

    public String cpuManufacturerPreferred = "";

    public boolean needsCPUCooler = false;

    public CPUCooler cpuCooler = new CPUCooler();


    public String preferredGPUBrand = "";


    public int adequateMinCPUBenchmark = 0;


    public int adequateMinGPUBenchmark = 0;
    public String caseColorPreferred = "";
    public TowerSizeType caseSizePreferred = TowerSizeType.NA;

    public EnergyEfficiency minEnergyEfficiencyNeeded = EnergyEfficiency.NA;

    public Hypothesis() {
    }

    public void setMinBudget(int minBudget) {
        this.minBudget = minBudget;
        Conclusion c = new Conclusion("budget was set");
    }

    public void setMaxBudget(int maxBudget) {
        this.maxBudget = maxBudget;
    }

    public void setBudgetType(BudgetType budgetType) {
        this.budgetType = budgetType;
    }

    public void setFinality(Finality finality) {
        this.finality = finality;
    }

    public void setDurationDayChoice(int durationDayChoice) {
        this.durationDayChoice = durationDayChoice;
    }

    public void setMinStorage(Storage minStorage) {
        this.minStorage = minStorage;
    }

    public void setMinScndStorage(Storage minScndStorage) {
        this.minScndStorage = minScndStorage;
    }

    public void setFinalityEstimatedDurationTime(int finalityEstimatedDurationTime) {
        this.finalityEstimatedDurationTime = finalityEstimatedDurationTime;
    }

    public void setMinRAM(int minRAM) {
        this.minRAM = minRAM;
    }

    public void setMinRAMSpeed(int minRAMSpeed) {
        this.minRAMSpeed = minRAMSpeed;
    }

    public void setMinRAMPreferred(int minRAMPreferred) {
        this.minRAMPreferred = minRAMPreferred;
    }

    public void setNeedsDedicatedGPU(boolean needsDedicatedGPU) {
        this.needsDedicatedGPU = needsDedicatedGPU;
    }

    public void setPrefersDedicatedGPU(boolean prefersDedicatedGPU) {
        this.prefersDedicatedGPU = prefersDedicatedGPU;
    }

    public void setGpuBrandPreferred(GPUBrand gpuBrandPreferred) {
        this.gpuBrandPreferred = gpuBrandPreferred;
    }

    public void setCpuManufacturerPreferred(String cpuManufacturerPreferred) {
        this.cpuManufacturerPreferred = cpuManufacturerPreferred;
    }

    public void setNeedsCPUCooler(boolean needsCPUCooler) {
        this.needsCPUCooler = needsCPUCooler;
    }

    public void setCpuCooler(CPUCooler cpuCooler) {
        this.cpuCooler = cpuCooler;
    }

    public void setPreferredGPUBrand(String preferredGPUBrand) {
        this.preferredGPUBrand = preferredGPUBrand;
    }

    public void setAdequateMinCPUBenchmark(int adequateMinCPUBenchmark) {
        this.adequateMinCPUBenchmark = adequateMinCPUBenchmark;
    }

    public void setAdequateMinGPUBenchmark(int adequateMinGPUBenchmark) {
        this.adequateMinGPUBenchmark = adequateMinGPUBenchmark;
    }

    public void setCaseColorPreferred(String caseColorPreferred) {
        this.caseColorPreferred = caseColorPreferred;
    }

    public void setCaseSizePreferred(TowerSizeType caseSizePreferred) {
        this.caseSizePreferred = caseSizePreferred;
    }

    public void setMinEnergyEfficiencyNeeded(EnergyEfficiency minEnergyEfficiencyNeeded) {
        this.minEnergyEfficiencyNeeded = minEnergyEfficiencyNeeded;
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
