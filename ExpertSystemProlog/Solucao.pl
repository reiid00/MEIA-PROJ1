
% Utiliza CLP
use_module(library(clpfd)).

% Procura o melhor computador possível respeitando todas as regras de compatibilidade e com base no estado atual da base de conhecimento,
% isto é, após o sistema pericial já ter adquirido conhecimento, idealmente considerado suficiente de forma a atingir uma solução não genérica
findBestPossibleHandmadeComputerBasedOnCurrentStateOfKnowledgeBase(PC):-
    getValidGPUsFromKnowledgeBase(ValidGPUsList),
    getValidCPUsListFromKnowledgeBase(ValidCPUsList),
    getValidCPUCoolersListFromKnowledgeBase(ValidCPUCoolersList),
    getValidMotherboardsListFromKnowledgeBase(ValidMotherboardsList),
    getValidMemoryRAMsListFromKnowledgeBase(ValidMemoryRAMsList),
    getValidSSDStoragesListFromKnowledgeBase(ValidSSDStoragesList),
    getValidHDDStoragesListFromKnowledgeBase(ValidHDDStoragesList),
    getValidPowerSuppliesListFromKnowledgeBase(ValidPowerSuppliesList),
    getValidCasesFromKnowledgeBase(ValidCasesList).
%    PC = [ChosenGPU, ChosenCPU, ChosenMotherboard, ChosenMemoryRAM, ChosenSSDStorage, ChosenHDDStorage, ChosenPowerSupply],
%    ChosenGPU = ValidGPUsList,
%    ChosenCPU = ValidCPUsList,
%    isCompatible(ComputadorIdeal), // O que este método faria, como neste momento ainda só foi escolhido o GPU e o CPU, seria, por exemplo "ChosenCPU.brand = ChosenGPU.brand",
%    ChosenMotherboard = ValidMotherboardsList,
%    isCompatible(ComputadorIdeal), // verifica se a motherboard é compatível com o GPU e CPU escolhidos
%    ChosenMemoryRAM = ValidMemoryRAMsList,
%    ChosenStorage = ValidStoragesList,
%    ChosenPowerSupply = ValidPowerSuppliesList,
%    isCompatible(PC), // verifica se os restantes componentes escolhidos também são compatíveis com os já previamente escolhidos,
%
%    labeling(PC). // Devolve a primeira solução possível, se existir alguma.



% Devolve a lista de GPUs válidos com base no conhecimento atual (i. e. após as regras já terem descartado diversas opções)
getValidGPUsFromKnowledgeBase(ValidGPUsList):- 
    answer(choose_budget, MaxBudget),
    fact(_, caseSizePreferred(CaseSizePreferred)),
    (fact(_, adequateMinGPUBenchmark(MinGPUBenchmark)), !; MinGPUBenchmark = 0),
    fact(_, gpuManufacturerPreferred(GPUManufacturerPreferred)),
    (
    (CaseSizePreferred =  "MICRO_ATX_TOWER", !, ATXCompatibility = "MICRO_ATX", MaxFansCount = 2); 
    (CaseSizePreferred = "MINI_ATX_TOWER", !, ATXCompatibility = "MINI_ATX", MaxFansCount = 2);
    (ATXCompatibility = "ATX", MaxFansCount = 5)
    ),
    findall(gpu(ID, Manufacturer, Name, BasePrice, LaunchDate, Brand, Memory, MemoryType, MaxClock, Voltage, FansCount, ATXCompatibilityList, BenchmarkScore), 
        (
        gpu(ID, Manufacturer, Name, BasePrice, LaunchDate, Brand, Memory, MemoryType, MaxClock, Voltage, FansCount, ATXCompatibilityList, BenchmarkScore),
        BasePrice < MaxBudget, 
        Manufacturer = GPUManufacturerPreferred,
        BenchmarkScore >= MinGPUBenchmark,
        FansCount =< MaxFansCount,
        member(ATXCompatibility, ATXCompatibilityList)
        ),
        ValidGPUsList).

% Devolve a lista de CPUs válidos com base no conhecimento atual (i. e. após as regras já terem descartado diversas opções)
getValidCPUsListFromKnowledgeBase(ValidCPUsList):-
    answer(choose_budget, MaxBudget),
    (fact(_, adequateMinCPUBenchmark(MinCPUBenchmark)), !; MinCPUBenchmark = 0),
    fact(_, cpuManufacturerPreferred(CPUManufacturerPreferred)),
    findall(cpu(ID, Manufacturer, Name, BasePrice, LaunchDate, CoreCount, ThreadsCount, BoostClock, Voltage, BenchmarkScore, Socket, HasIntegratedGPU), 
        (
        cpu(ID, Manufacturer, Name, BasePrice, LaunchDate, CoreCount, ThreadsCount, BoostClock, Voltage, BenchmarkScore, Socket, HasIntegratedGPU),
        BasePrice < MaxBudget, 
        Manufacturer = CPUManufacturerPreferred,
        BenchmarkScore >= MinCPUBenchmark
        ),
        ValidCPUsList).

% Devolve a lista de CPU Coolers válidos com base no conhecimento atual (i. e. após as regras já terem descartado diversas opções)
getValidCPUCoolersListFromKnowledgeBase(ValidCPUCoolersList):-
    (fact(_, cpuCooler_isFanless(CPUCooler_isFanless)), !; CPUCooler_isFanless = false),
    (fact(_, cpuCooler_isWaterCooled(CPUCooler_isWaterCooled)), !; CPUCooler_isWaterCooled = false),
    findall(cpuCooler(ID, Manufacturer, Name, BasePrice, LaunchDate, Voltage, IsWaterCooled, IsFanless, SocketCompatibilityList),
        (
        cpuCooler(ID, Manufacturer, Name, BasePrice, LaunchDate, Voltage, IsWaterCooled, IsFanless, SocketCompatibilityList),
        IsFanless = CPUCooler_isFanless,
        IsWaterCooled = CPUCooler_isWaterCooled
        ),
        ValidCPUCoolersList).
    
% Devolve a lista de motherboards válidas com base no conhecimento atual (i. e. após as regras já terem descartado diversas opções)
getValidMotherboardsListFromKnowledgeBase(ValidMotherboardsList):-
    fact(_, caseSizePreferred(CaseSizePreferred)),
    (fact(_, minRAM(MinRAM)), !, MinMaxRAM is MinRAM * 2; MinMaxRAM = 0),
    (
    (CaseSizePreferred =  "MICRO_ATX_TOWER", !, ATXCompatibilityList = ["MICRO_ATX"]); 
    (CaseSizePreferred = "MINI_ATX_TOWER", !, ATXCompatibilityList = ["MINI_ATX"]);
    (ATXCompatibilityList = ["ATX", "EATX"])
    ),
    findall(motherboard(ID, Manufacturer, Name, BasePrice, LaunchDate, SocketCompatibilityList, ATXType, MaxMemoryRam, RAMType, RAMSlots, RAMSpeedList),
        (
        motherboard(ID, Manufacturer, Name, BasePrice, LaunchDate, SocketCompatibilityList, ATXType, MaxMemoryRam, RAMType, RAMSlots, RAMSpeedList),
        MaxMemoryRam >= MinMaxRAM,
        member(ATXType, ATXCompatibilityList)
        ),
        ValidMotherboardsList).

% Devolve a lista de memórias RAM válidas com base no conhecimento atual (i. e. após as regras já terem descartado diversas opções)
getValidMemoryRAMsListFromKnowledgeBase(ValidMemoryRAMsList):-
    (fact(_, minRAM(MinRAM)), !; MinRAM = 0),
    (fact(_, minRAMSpeed(MinRAMSpeed)), !; MinRAMSpeed = 0),
    findall(ram(ID, Manufacturer, Name, BasePrice, LaunchDate, Speed, Capacity, SlotsCount, RamType, Voltage),
        (
        ram(ID, Manufacturer, Name, BasePrice, LaunchDate, Speed, Capacity, SlotsCount, RamType, Voltage),
        Capacity >= MinRAM,
        Speed >= MinRAMSpeed
        ),
        ValidMemoryRAMsList).

% Devolve a lista de Discos SSD/Principais válidos com base no conhecimento atual (i. e. após as regras já terem descartado diversas opções)
getValidSSDStoragesListFromKnowledgeBase(ValidSSDStoragesList):-
    (fact(_, minStorage_capacity(MinSSDCapacity)), !; MinSSDCapacity = 0),
    findall(storage(ID, Manufacturer, Name, BasePrice, LaunchDate, IsSSD, IsSATA, Capacity, Cache, BenchmarkScore),
        (
        storage(ID, Manufacturer, Name, BasePrice, LaunchDate, IsSSD, IsSATA, Capacity, Cache, BenchmarkScore),
        IsSSD = true,
        Capacity >= MinSSDCapacity
        ),
        ValidSSDStoragesList).

% Devolve a lista de Discos HDD/Secundários válidos com base no conhecimento atual (i. e. após as regras já terem descartado diversas opções)
getValidHDDStoragesListFromKnowledgeBase(ValidHDDStoragesList):-
    (fact(_, minSecondStorage_capacity(MinHDDCapacity)), !; MinHDDCapacity = 0),
    findall(storage(ID, Manufacturer, Name, BasePrice, LaunchDate, IsSSD, IsSATA, Capacity, Cache, BenchmarkScore),
        (
        storage(ID, Manufacturer, Name, BasePrice, LaunchDate, IsSSD, IsSATA, Capacity, Cache, BenchmarkScore),
        IsSSD = false,
        Capacity >= MinHDDCapacity
        ),
        ValidHDDStoragesList).

% Devolve a lista de Fontes de Alimentação válidas com base no conhecimento atual (i. e. após as regras já terem descartado diversas opções)
getValidPowerSuppliesListFromKnowledgeBase(ValidPowerSuppliesList):-
    fact(_, caseSizePreferred(CaseSizePreferred)),
    fact(_, minEnergyEfficiencyNeeded(MinEnergyEfficiencyNeeded)),
    (
    (MinEnergyEfficiencyNeeded = "_80PLUS", !, ValidEnergyEfficiencyList = ["_80PLUS", "_80PLUS_BRONZE", "_80PLUS_GOLD", "_80PLUS_PLATINUM", "_80PLUS_TITANIUM"]); 
    (MinEnergyEfficiencyNeeded = "_80PLUS_GOLD", !, ValidEnergyEfficiencyList = ["_80PLUS_GOLD", "_80PLUS_PLATINUM", "_80PLUS_TITANIUM"]);
    (MinEnergyEfficiencyNeeded = "_80PLUS_PLATINUM", !, ValidEnergyEfficiencyList = ["_80PLUS_PLATINUM", "_80PLUS_TITANIUM"])
    ),
    (
    (CaseSizePreferred =  "MICRO_ATX_TOWER", !, ATXCompatibility = "MICRO_ATX"); 
    (CaseSizePreferred = "MINI_ATX_TOWER", !, ATXCompatibility = "MINI_ATX");
    (ATXCompatibility = "ATX")
    ),
    findall(powerSupply(ID, Manufacturer, Name, BasePrice, LaunchDate, Capacity, EnergyEfficiency, Modular, ATXCompatibilityList),
        (
        powerSupply(ID, Manufacturer, Name, BasePrice, LaunchDate, Capacity, EnergyEfficiency, Modular, ATXCompatibilityList),
        member(EnergyEfficiency, ValidEnergyEfficiencyList),
        member(ATXCompatibility, ATXCompatibilityList)
        ),
        ValidPowerSuppliesList).

% Devolve a lista de Caixas válidas com base no conhecimento atual (i. e. após as regras já terem descartado diversas opções)
getValidCasesFromKnowledgeBase(ValidCasesList):-
    fact(_, caseSizePreferred(CaseSizePreferred)),
    (fact(_, caseColorPreferred(CaseColorPreferred)), !; CaseColorPreferred = "ANY"),
    findall(case(ID, Manufacturer, Name, BasePrice, LaunchDate, SizeType, ATXCompatibilityList, Color),
        (
        case(ID, Manufacturer, Name, BasePrice, LaunchDate, SizeType, ATXCompatibilityList, Color),
        (CaseColorPreferred = "ANY"; Color = CaseColorPreferred),
        (CaseSizePreferred = SizeType; (CaseSizePreferred = "MID_TOWER", SizeType = "FULL_TOWER"))
        ),
        ValidCasesList).




%exactly(List):- length(List, 4), List ins 1..4, exactly(3, List, 2), label(List).

%exactly(_, [], 0).
%exactly(X, [Y|L], N) :-
%B #<==> (X #= Y),
%N #= M+B,
%exactly(X, L, M).
