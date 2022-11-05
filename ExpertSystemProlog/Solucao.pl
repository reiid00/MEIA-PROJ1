
% Utiliza CLP
:-use_module(library(clpfd)).

% Procura o melhor computador possível respeitando todas as regras de compatibilidade e com base no estado atual da base de conhecimento,
% isto é, após o sistema pericial já ter adquirido conhecimento, idealmente considerado suficiente de forma a atingir uma solução não genérica
findBestPossibleHandmadeComputerBasedOnCurrentStateOfKnowledgeBase(PC):-
    getValidGPUsListFromKnowledgeBase(ValidGPUsList),
    getValidCPUsListFromKnowledgeBase(ValidCPUsList),
    getValidCPUCoolersListFromKnowledgeBase(ValidCPUCoolersList),
    getValidMotherboardsListFromKnowledgeBase(ValidMotherboardsList),
    getValidMemoryRAMsListFromKnowledgeBase(ValidMemoryRAMsList),
    getValidSSDStoragesListFromKnowledgeBase(ValidSSDStoragesList),
    getValidHDDStoragesListFromKnowledgeBase(ValidHDDStoragesList),
    getValidPowerSuppliesListFromKnowledgeBase(ValidPowerSuppliesList),
    getValidCasesListFromKnowledgeBase(ValidCasesList),
    answer(choose_budget, MaxBudget),

    length(ValidGPUsList, NGPUs),
    GPUIndex in 1..NGPUs,
    nth1(GPUIndex, ValidGPUsList, GPU),
    GPU = gpu(_, GPUManufacturer, GPUName, GPUBasePrice, _, _, GPUMemory, GPUMemoryType, _, GPUVoltage, _, GPUATXCompatibilityList, GPUBenchmarkScore),

    GPUBasePrice #=< ((MaxBudget * 5) // 10),

    length(ValidCPUsList, NCPUs),
    CPUIndex in 1..NCPUs,
    nth1(CPUIndex, ValidCPUsList, CPU),
    CPU = cpu(_, CPUManufacturer, CPUName, CPUBasePrice, _, _, _, CPUBoostClock, CPUVoltage, CPUBenchmarkScore, CPUSocket, CPUHasIntegratedGPU),

    GPUBasePrice + CPUBasePrice #=< ((MaxBudget * 75) // 100),

    length(ValidMotherboardsList, NMotherboards),
    MotherboardIndex in 1..NMotherboards,
    nth1(MotherboardIndex, ValidMotherboardsList, Motherboard),
    Motherboard = motherboard(_, MotherboardManufacturer, MotherboardName, MotherboardBasePrice, _, MotherboardSocketCompatibilityList, MotherboardATXType, MotherboardMaxMemoryRam, MotherboardRAMType, MotherboardRAMSlots, MotherboardRAMSpeedList),

    length(ValidCPUCoolersList, NCPUCoolers),
    CPUCoolerIndex in 1..NCPUCoolers,
    nth1(CPUCoolerIndex, ValidCPUCoolersList, CPUCooler),
    CPUCooler = cpuCooler(_, CPUCoolerManufacturer, CPUCoolerName, CPUCoolerBasePrice, _, CPUCoolerVoltage, _, _, CPUCoolerSocketCompatibilityList),

    length(ValidMemoryRAMsList, NRAMs),
    RAMIndex in 1..NRAMs,
    nth1(RAMIndex, ValidMemoryRAMsList, RAM),
    RAM = ram(_, RAMManufacturer, RAMName, RAMBasePrice, _, RAMSpeed, RAMCapacity, RAMSlotsCount, RAMRamType, _),

    RAMSlotsCount #=< MotherboardRAMSlots,
    RAMCapacity #=< MotherboardMaxMemoryRam,

    length(ValidSSDStoragesList, NSSDs),
    SSDIndex in 1..NSSDs,
    nth1(SSDIndex, ValidSSDStoragesList, SSD),
    SSD = storage(_, SSDManufacturer, SSDName, SSDBasePrice, _, _, _, _, _, _),

    length(ValidHDDStoragesList, NHDDs),
    HDDIndex in 1..NHDDs,
    nth1(HDDIndex, ValidHDDStoragesList, HDD),
    HDD = storage(_, HDDManufacturer, HDDName, HDDBasePrice, _, _, _, _, _, _),

    length(ValidPowerSuppliesList, NPowerSupplys),
    PowerSupplyIndex in 1..NPowerSupplys,
    nth1(PowerSupplyIndex, ValidPowerSuppliesList, PowerSupply),
    PowerSupply = powerSupply(_, PowerSupplyManufacturer, PowerSupplyName, PowerSupplyBasePrice, _, PowerSupplyCapacity, PowerSupplyEnergyEfficiency, PowerSupplyModular, PowerSupplyATXCompatibilityList),

    PCVoltage #= GPUVoltage + CPUVoltage + CPUCoolerVoltage,
    MinPCVoltageCapacity #= ((PCVoltage * 15) // 10),
    MinPCVoltageCapacity #=< PowerSupplyCapacity,

    length(ValidCasesList, NCases),
    CaseIndex in 1..NCases,
    nth1(CaseIndex, ValidCasesList, Case),
    Case = case(_, CaseManufacturer, CaseName, CaseBasePrice, _, CaseSizeType, CaseATXCompatibilityList, CaseColor), 

    PCCost #= GPUBasePrice + CPUBasePrice + MotherboardBasePrice + CPUCoolerBasePrice + RAMBasePrice + SSDBasePrice + HDDBasePrice + PowerSupplyBasePrice + CaseBasePrice,
    PCCost #=< MaxBudget,

    once(labeling(
        [], 
        [GPUIndex, CPUIndex, MotherboardIndex, CPUCoolerIndex, RAMIndex, SSDIndex, HDDIndex, PowerSupplyIndex, CaseIndex]
        )),
    PC = [GPU, CPU, CPUCooler, Motherboard, RAM, SSD, HDD, PowerSupply, Case],
    nl, write('********************************************************'), nl,
    write('Foi concluído o seguinte PC feito à medida com um custo total de '), write(PCCost), write('€ :'), nl,
    write('GPU: '), write(GPUManufacturer), write(' '), write(GPUName), write(' --> '), write(GPUBasePrice), write('€'), nl,
    write('CPU: '), write(CPUManufacturer), write(' '), write(CPUName), write(' --> '), write(CPUBasePrice), write('€'), nl,
    write('CPU Cooler: '), write(CPUCoolerManufacturer), write(' '), write(CPUCoolerName), write(' --> '), write(CPUCoolerBasePrice), write('€'), nl,
    write('Motherboard: '), write(MotherboardManufacturer), write(' '), write(MotherboardName), write(' --> '), write(MotherboardBasePrice), write('€'), nl,
    write('RAM: '), write(RAMManufacturer), write(' '), write(RAMName), write(' --> '), write(RAMBasePrice), write('€'), nl,
    write('1ª Disco, SSD: '), write(SSDManufacturer), write(' '), write(SSDName), write(' --> '), write(SSDBasePrice), write('€'), nl,
    write('2ª Disco, HDD: '), write(HDDManufacturer), write(' '), write(HDDName), write(' --> '), write(HDDBasePrice), write('€'), nl,
    write('Fonte de Alimentação: '), write(PowerSupplyManufacturer), write(' '), write(PowerSupplyName), write(' --> '), write(PowerSupplyBasePrice), write('€'), nl,
    write('Caixa: '), write(CaseManufacturer), write(' '), write(CaseName), write(' --> '), write(CaseBasePrice), write('€'), nl,
    write('********************************************************'), nl.
findBestPossibleHandmadeComputerBasedOnCurrentStateOfKnowledgeBase([]):-
    nl, write('********************************************************'), nl,
    write('Não foi possível montar um PC consoante as necessidades apresentadas.'), nl,
    write('********************************************************'), nl.

% Devolve a lista de GPUs válidos com base no conhecimento atual (i. e. após as regras já terem descartado diversas opções)
getValidGPUsListFromKnowledgeBase(ValidGPUsList):- 
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
        ValidGPUsList1),
    sort(13, @>=, ValidGPUsList1, ValidGPUsList).

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
        ValidCPUsList1),
    sort(10, @>=, ValidCPUsList1, ValidCPUsList).

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
        ValidCPUCoolersList1),
    sort(4, @=<, ValidCPUCoolersList1, ValidCPUCoolersList).
    
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
        ValidMotherboardsList1),
    sort(4, @=<, ValidMotherboardsList1, ValidMotherboardsList).

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
        ValidMemoryRAMsList1),
    sort(4, @=<, ValidMemoryRAMsList1, ValidMemoryRAMsList).

% Devolve a lista de Discos SSD/Principais válidos com base no conhecimento atual (i. e. após as regras já terem descartado diversas opções)
getValidSSDStoragesListFromKnowledgeBase(ValidSSDStoragesList):-
    (fact(_, minStorage_capacity(MinSSDCapacity)), !; MinSSDCapacity = 0),
    findall(storage(ID, Manufacturer, Name, BasePrice, LaunchDate, IsSSD, IsSATA, Capacity, Cache, BenchmarkScore),
        (
        storage(ID, Manufacturer, Name, BasePrice, LaunchDate, IsSSD, IsSATA, Capacity, Cache, BenchmarkScore),
        IsSSD = true,
        Capacity >= MinSSDCapacity
        ),
        ValidSSDStoragesList1),
    sort(10, @>=, ValidSSDStoragesList1, ValidSSDStoragesList).

% Devolve a lista de Discos HDD/Secundários válidos com base no conhecimento atual (i. e. após as regras já terem descartado diversas opções)
getValidHDDStoragesListFromKnowledgeBase(ValidHDDStoragesList):-
    (fact(_, minSecondStorage_capacity(MinHDDCapacity)), !; MinHDDCapacity = 0),
    findall(storage(ID, Manufacturer, Name, BasePrice, LaunchDate, IsSSD, IsSATA, Capacity, Cache, BenchmarkScore),
        (
        storage(ID, Manufacturer, Name, BasePrice, LaunchDate, IsSSD, IsSATA, Capacity, Cache, BenchmarkScore),
        IsSSD = false,
        Capacity >= MinHDDCapacity
        ),
        ValidHDDStoragesList1),
    sort(10, @>=, ValidHDDStoragesList1, ValidHDDStoragesList).

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
        ValidPowerSuppliesList1),
    sort(4, @=<, ValidPowerSuppliesList1, ValidPowerSuppliesList).

% Devolve a lista de Caixas válidas com base no conhecimento atual (i. e. após as regras já terem descartado diversas opções)
getValidCasesListFromKnowledgeBase(ValidCasesList):-
    fact(_, caseSizePreferred(CaseSizePreferred)),
    (fact(_, caseColorPreferred(CaseColorPreferred)), !; CaseColorPreferred = "ANY"),
    findall(case(ID, Manufacturer, Name, BasePrice, LaunchDate, SizeType, ATXCompatibilityList, Color),
        (
        case(ID, Manufacturer, Name, BasePrice, LaunchDate, SizeType, ATXCompatibilityList, Color),
        (CaseColorPreferred = "ANY"; Color = CaseColorPreferred),
        (CaseSizePreferred = SizeType; (CaseSizePreferred = "MID_TOWER", SizeType = "FULL_TOWER"))
        ),
        ValidCasesList1),
    sort(4, @=<, ValidCasesList1, ValidCasesList).