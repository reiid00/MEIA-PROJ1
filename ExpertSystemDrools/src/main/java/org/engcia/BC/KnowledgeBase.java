package org.engcia.BC;

import org.engcia.BD.CPUCooler;
import org.engcia.BD.Case.TowerSizeType;
import org.engcia.BD.GPU;
import org.engcia.BD.GPU.GPUBrand;
import org.engcia.BD.PowerSupply.EnergyEfficiency;
import org.engcia.BD.Storage;
import org.engcia.Main;

public class KnowledgeBase extends Fact{

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

    public void setMinBudget(int minBudget) {
        this.minBudget = minBudget;
        Conclusion c = new Conclusion("budget was set");
    }

    public void setMaxBudget(int maxBudget) {
        this.maxBudget = maxBudget;
    }

    public void setBudgetType(BudgetType budgetType) {
        this.budgetType = budgetType;
        new Hypothesis("Orcamento",budgetType.toString(), "");
    }

    public void setFinality(Finality finality) {
        this.finality = finality;
        new Hypothesis("Finalidade",finality.toString(), "");
    }

    public void setDurationDayChoice(int durationDayChoice) {
        if (durationDayChoice == 1) new Hypothesis("Horas passadas no computador ","entre 2 e 4 horas", "");
        else if (durationDayChoice == 2) new Hypothesis("Horas passadas no computador ","mais de 4 horas", "");
        this.durationDayChoice = durationDayChoice;
    }

    public void setMinStorage(int capacity ) {
        this.minStorage.isSSD = true;
        this.minStorage.isSATA = false;
        this.minStorage.capacity = capacity;
        new Hypothesis("Capacidade mínima para o disco principal (SSD) ",String.valueOf(capacity), "");
    }

    public void setMinScndStorage(int capacity ) {
        this.minScndStorage.isSATA = true;
        this.minScndStorage.isSSD = false;
        this.minScndStorage.capacity = capacity;
        new Hypothesis("Capacidade mínima para o disco principal (HDD) ",String.valueOf(capacity), "");
    }


    public void setMinRAM(int minRAM) {
        new Hypothesis("Mínimo de RAM sugerida pelo sistema ",String.valueOf(minRAM), "");
        this.minRAM = minRAM;
    }

    public void setMinRAMSpeed(int minRAMSpeed) {
        new Hypothesis("Mínimo da velocidade da RAM sugerida pelo sistema ",String.valueOf(minRAMSpeed), "");
        this.minRAMSpeed = minRAMSpeed;
    }

    public void setMinRAMPreferred(int minRAMPreferred) {
        new Hypothesis("Quantidade de RAM escolhida pelo utilizador ",String.valueOf(minRAMPreferred), "");
        this.minRAMPreferred = minRAMPreferred;
    }

    public void setNeedsDedicatedGPU(boolean needsDedicatedGPU) {
        if (needsDedicatedGPU) new Hypothesis("Dada a finalidade o sistema concluiu que ","precisa de placa gráfica", "");
        if (!needsDedicatedGPU) new Hypothesis("Dada a finalidade o sistema concluiu que ","não precisa de placa gráfica", "");
        this.needsDedicatedGPU = needsDedicatedGPU;
    }

    public void setPrefersDedicatedGPU(boolean prefersDedicatedGPU) {
        if (prefersDedicatedGPU) new Hypothesis("O utilizador ","preferiu ter placa gráfica", "");
        if (!prefersDedicatedGPU) new Hypothesis("O utilizador ","não preferiu ter placa gráfica", "");
        this.prefersDedicatedGPU = prefersDedicatedGPU;
    }

    public void setGpuManufacturerPreferred(String gpuManufacturerPreferred) {
        new Hypothesis("O utilizador preferiu placa gráfica do fabricante  ",gpuManufacturerPreferred, "");
        this.gpuManufacturerPreferred = gpuManufacturerPreferred;
    }

    public void setCpuManufacturerPreferred(String cpuManufacturerPreferred) {
        new Hypothesis("O utilizador preferiu processador do fabricante  ",cpuManufacturerPreferred, "");
        this.cpuManufacturerPreferred = cpuManufacturerPreferred;
    }


    public void setNeedsCPUCooler(boolean needsCPUCooler){
        if (needsCPUCooler) new Hypothesis("O sistema concluiu que  ","precisa de um CPU Cooler", "");
        if (!needsCPUCooler) new Hypothesis("O sistema concluiu que  ","não precisa de um CPU Cooler", "");
        this.needsCPUCooler = needsCPUCooler;
    }

    public void setCpuCooler(String cpuCooler) {
        if (cpuCooler.equalsIgnoreCase("waterCooled")){
            new Hypothesis("O utilizador preferiu um CPU cooler  ","Water Cooled", "");
            this.cpuCooler.isWaterCooled = true;
        } else if (cpuCooler.equalsIgnoreCase("fanless")){
            new Hypothesis("O utilizador preferiu um CPU cooler  ","Fanless", "");
            this.cpuCooler.isFanless = true;
        }
    }

    public void setPreferredGPUBrand(String preferredGPUBrand) {
        new Hypothesis("O utilizador preferiu placa gráfica da marca  ",preferredGPUBrand, "");
        this.preferredGPUBrand = GPU.GPUBrand.valueOf(preferredGPUBrand.toUpperCase());
    }

    public void setAdequateMinCPUBenchmark(int adequateMinCPUBenchmark) {
        new Hypothesis("O sistema concluiu que o processador deve ter um mínimo benchmark de  ",String.valueOf(adequateMinCPUBenchmark), "");
        this.adequateMinCPUBenchmark = adequateMinCPUBenchmark;
    }

    public void setAdequateMinGPUBenchmark(int adequateMinGPUBenchmark) {
        new Hypothesis("O sistema concluiu que a placa gráfica deve ter um mínimo benchmark de  ",String.valueOf(adequateMinGPUBenchmark), "");
        this.adequateMinGPUBenchmark = adequateMinGPUBenchmark;
    }

    public void setCaseColorPreferred(String caseColorPreferred) {
        new Hypothesis("O utilizador preferiu a caixa da cor ",caseColorPreferred, "");
        this.caseColorPreferred = caseColorPreferred;
    }

    public void setCaseSizePreferred(TowerSizeType caseSizePreferred) {
        new Hypothesis("O utilizador preferiu a caixa do tamanha ",caseSizePreferred.toString(), "");
        this.caseSizePreferred = caseSizePreferred;
    }

    public void setMinEnergyEfficiencyNeeded(EnergyEfficiency minEnergyEfficiencyNeeded) {
        new Hypothesis("O sistema concluiu que a fonte de energia deve ser de eficiência mínima ",minEnergyEfficiencyNeeded.toString(), "");
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
