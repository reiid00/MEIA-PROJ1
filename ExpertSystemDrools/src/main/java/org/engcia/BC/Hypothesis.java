package org.engcia.BC;

import org.engcia.BD.CPUCooler;
import org.engcia.BD.Case.TowerSizeType;
import org.engcia.BD.GPU;
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
    public int minRAM = 0;
    public int minRAMSpeed = 0;
    public int minRAMPreferred = 0;


    public boolean needsDedicatedGPU = false;
    public boolean prefersDedicatedGPU = false;
    public String gpuManufacturerPreferred = "";

    public String cpuManufacturerPreferred = "";

    public boolean needsCPUCooler = false;

    public CPUCooler cpuCooler = new CPUCooler();


    public GPUBrand preferredGPUBrand = GPU.GPUBrand.NA;


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
        Conclusion c = new Conclusion("Budget type of " + budgetType);
        this.budgetType = budgetType;
    }

    public void setFinality(Finality finality) {
        Conclusion c = new Conclusion("Finality  " + finality);
        this.finality = finality;
    }

    public void setDurationDayChoice(int durationDayChoice) {
        Conclusion c = new Conclusion("Uses the computer more than  " + durationDayChoice + " hours");
        this.durationDayChoice = durationDayChoice;
    }

    public void setMinStorage(int capacity ) {
        this.minStorage.isSSD = true;
        this.minStorage.isSATA = false;
        this.minStorage.capacity = capacity;
        Conclusion c = new Conclusion("Storage with capacity" + capacity);
    }

    public void setMinScndStorage(int capacity ) {
        this.minScndStorage.isSATA = true;
        this.minScndStorage.isSSD = false;
        this.minScndStorage.capacity = capacity;
        Conclusion c = new Conclusion("Secondary Storage with capacity " + capacity);
    }


    public void setMinRAM(int minRAM) {
        Conclusion c = new Conclusion("RAM preferred by user " + minRAM);
        this.minRAM = minRAM;
    }

    public void setMinRAMSpeed(int minRAMSpeed) {
        Conclusion c = new Conclusion("Minimum RAM speed required " + minRAMSpeed);
        this.minRAMSpeed = minRAMSpeed;
    }

    public void setMinRAMPreferred(int minRAMPreferred) {
        Conclusion c = new Conclusion("Minimum RAM required " + minRAMPreferred);
        this.minRAMPreferred = minRAMPreferred;
    }

    public void setNeedsDedicatedGPU(boolean needsDedicatedGPU) {
        Conclusion c = new Conclusion("Needs dedicated GPU");
        this.needsDedicatedGPU = needsDedicatedGPU;
    }

    public void setPrefersDedicatedGPU(boolean prefersDedicatedGPU) {
        Conclusion c = new Conclusion("User prefers dedicated GPU");
        this.prefersDedicatedGPU = prefersDedicatedGPU;
    }

    public void setGpuManufacturerPreferred(String gpuManufacturerPreferred) {
        Conclusion c = new Conclusion("User prefers GPUs of brand " + gpuManufacturerPreferred);
        this.gpuManufacturerPreferred = gpuManufacturerPreferred;
    }

    public void setCpuManufacturerPreferred(String cpuManufacturerPreferred) {
        Conclusion c = new Conclusion("User prefers CPUs of manufacturer " + cpuManufacturerPreferred);
        this.cpuManufacturerPreferred = cpuManufacturerPreferred;
    }


    public void setNeedsCPUCooler(boolean needsCPUCooler) {
        Conclusion c = new Conclusion("User wants CPU cooler");
        this.needsCPUCooler = needsCPUCooler;
    }

    public void setCpuCooler(CPUCooler cpuCooler) {
        Conclusion c = new Conclusion("User chooses CPU cooler");
        this.cpuCooler = cpuCooler;
    }

    public void setPreferredGPUBrand(String preferredGPUBrand) {
        Conclusion c = new Conclusion("User prefers GPUs of manufacturer " + preferredGPUBrand);
        this.preferredGPUBrand = GPU.GPUBrand.valueOf(preferredGPUBrand.toUpperCase());
    }

    public void setAdequateMinCPUBenchmark(int adequateMinCPUBenchmark) {
        Conclusion c = new Conclusion("Minimum CPU benchmark of  " + adequateMinCPUBenchmark);
        this.adequateMinCPUBenchmark = adequateMinCPUBenchmark;
    }

    public void setAdequateMinGPUBenchmark(int adequateMinGPUBenchmark) {
        Conclusion c = new Conclusion("Minimum GPU benchmark of  " + adequateMinGPUBenchmark);
        this.adequateMinGPUBenchmark = adequateMinGPUBenchmark;
    }

    public void setCaseColorPreferred(String caseColorPreferred) {
        Conclusion c = new Conclusion("User chose case color  " + caseColorPreferred);
        this.caseColorPreferred = caseColorPreferred;
    }

    public void setCaseSizePreferred(TowerSizeType caseSizePreferred) {

        Conclusion c = new Conclusion("User chose case size  " + caseSizePreferred);
        this.caseSizePreferred = caseSizePreferred;
    }

    public void setMinEnergyEfficiencyNeeded(EnergyEfficiency minEnergyEfficiencyNeeded) {
        Conclusion c = new Conclusion("User needs power supply with minimum energy efficiency of " + minEnergyEfficiencyNeeded);
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
