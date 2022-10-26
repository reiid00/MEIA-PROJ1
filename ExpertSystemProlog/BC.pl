% Components - Os primeiros 5 atributos estão reservados aos atributos genéricos de todos os componentes X + 5(component)
:-dynamic case/8.
:-dynamic cpu/12.
:-dynamic cpuCooler/8.
:-dynamic gpu/13.
:-dynamic motherboard/11.
:-dynamic powerSupply/9.
:-dynamic ram/10.
:-dynamic storage/9.

numAtributos(case, 8).
numAtributos(cpu, 12).
numAtributos(cpuCooler, 8).
numAtributos(gpu, 13).
numAtributos(motherboard, 11).
numAtributos(powerSupply, 9).
numAtributos(ram, 10).
numAtributos(storage, 9).

% Leitura da Base de Dados

readLines(Stream, Lines):- readLines(Stream, [], Lines).

readLines(Stream, LinesPrev, LinesPrev):- at_end_of_stream(Stream),!.
readLines(Stream, LinesPrev, Lines):- 
        read_line_to_string(Stream, LineStr),
        (splitLineString(LineStr, SplitLineStr), !, append(LinesPrev, [SplitLineStr], LinesPrev1)
        ; LinesPrev1 = LinesPrev),
        readLines(Stream, LinesPrev1, Lines).
        
splitLineString(LineStr, _):- (isStrEmpty(LineStr); isStrComment(LineStr)), !, fail.
splitLineString(LineStr, SplitLineStr):- split_string(LineStr, ';', '', SplitLineStr).

isStrEmpty(Str):- re_match(Str, '^\s*$').
isStrComment(Str):- sub_string(Str, 0, 2, _, '--').

loadGPUs():-
        open('../BD/GPUs.txt', read, Stream),
        readLines(Stream, Lines),
        assertGPUs(Lines),
        close(Stream).

assertGPUs([]):- !.
assertGPUs([X|Lines]):- 
        numAtributos(gpu, NumAtributos),
        length(X, NumAtributos),!,
        X = [ID, Manufacturer, Name, BasePrice1, LaunchDate, Brand, Memory1, MemoryType, MaxClock1, Voltage1, FansCount1, ATXCompatibilityList1, BenchmarkScore1],
        number_string(BasePrice, BasePrice1),
        number_string(Memory, Memory1),
        number_string(MaxClock, MaxClock1),
        number_string(Voltage, Voltage1),
        number_string(FansCount, FansCount1),

        string_length(ATXCompatibilityList1, N_ATXCompatibilityList1),
        N_ATXCompatibilityList is N_ATXCompatibilityList1 - 2,
        sub_string(ATXCompatibilityList1, 1, N_ATXCompatibilityList, _, ATXCompatibilityList2),
        split_string(ATXCompatibilityList2, ',', '', ATXCompatibilityList),

        number_string(BenchmarkScore, BenchmarkScore1),
        assert(gpu(ID, Manufacturer, Name, BasePrice, LaunchDate, Brand, Memory, MemoryType, MaxClock, Voltage, FansCount, ATXCompatibilityList, BenchmarkScore)),
        assertGPUs(Lines).
assertGPUs([_|Lines]):- assertGPUs(Lines).

loadCPUs():-
        open('../BD/CPUs.txt', read, Stream),
        readLines(Stream, Lines),
        assertCPUs(Lines),
        close(Stream).

assertCPUs([]):- !.
assertCPUs([X|Lines]):- 
        numAtributos(cpu, NumAtributos),
        length(X, NumAtributos),!,
        X = [ID, Manufacturer, Name, BasePrice1, LaunchDate, CoreCount1, ThreadsCount1, BoostClock1, Voltage1, Benchmark1, Socket, HasIntegratedGPU1],
        number_string(BasePrice, BasePrice1),
        number_string(CoreCount, CoreCount1),
        number_string(ThreadsCount, ThreadsCount1),
        number_string(BoostClock, BoostClock1),
        number_string(Voltage, Voltage1),
        number_string(Benchmark, Benchmark1),

        (HasIntegratedGPU1 == 'true', !, HasIntegratedGPU = true
        ; HasIntegratedGPU = false),

        assert(cpu(ID, Manufacturer, Name, BasePrice, LaunchDate, CoreCount, ThreadsCount, BoostClock, Voltage, Benchmark, Socket, HasIntegratedGPU)),
        assertCPUs(Lines).
assertCPUs([_|Lines]):- assertCPUs(Lines).

loadCPUCoolers():-
        open('../BD/CPUCoolers.txt', read, Stream),
        readLines(Stream, Lines),
        assertCPUCoolers(Lines),
        close(Stream).

assertCPUCoolers([]):- !.
assertCPUCoolers([X|Lines]):- 
        numAtributos(cpuCooler, NumAtributos),
        length(X, NumAtributos),!,
        X = [ID, Manufacturer, Name, BasePrice1, LaunchDate, IsWaterCooled1, IsFanless1, SocketCompatibilityList1],
        number_string(BasePrice, BasePrice1),

        (IsWaterCooled1 == 'true', !, IsWaterCooled = true
        ; IsWaterCooled = false),

        (IsFanless1 == 'true', !, IsFanless = true
        ; IsFanless = false),

        string_length(SocketCompatibilityList1, N_SocketCompatibilityList1),
        N_SocketCompatibilityList is N_SocketCompatibilityList1 - 2,
        sub_string(SocketCompatibilityList1, 1, N_SocketCompatibilityList, _, SocketCompatibilityList2),
        split_string(SocketCompatibilityList2, ',', '', SocketCompatibilityList),

        assert(cpuCooler(ID, Manufacturer, Name, BasePrice, LaunchDate, IsWaterCooled, IsFanless, SocketCompatibilityList)),
        assertCPUCoolers(Lines).
assertCPUCoolers([_|Lines]):- assertCPUCoolers(Lines).

loadMotherboards():-
        open('../BD/Motherboards.txt', read, Stream),
        readLines(Stream, Lines),
        assertMotherboards(Lines),
        close(Stream).

assertMotherboards([]):- !.
assertMotherboards([X|Lines]):- 
        numAtributos(motherboard, NumAtributos),
        length(X, NumAtributos),!,
        X = [ID, Manufacturer, Name, BasePrice1, LaunchDate, SocketCompatibilityList1, ATXType, MaxMemoryRam1, RAMType, RAMSlots1, RAMSpeedList1],
        number_string(BasePrice, BasePrice1),
        number_string(MaxMemoryRam, MaxMemoryRam1),
        number_string(RAMSlots, RAMSlots1),
        
        string_length(SocketCompatibilityList1, N_SocketCompatibilityList1),
        N_SocketCompatibilityList is N_SocketCompatibilityList1 - 2,
        sub_string(SocketCompatibilityList1, 1, N_SocketCompatibilityList, _, SocketCompatibilityList2),
        split_string(SocketCompatibilityList2, ',', '', SocketCompatibilityList),

        string_length(RAMSpeedList1, N_RAMSpeedList1),
        N_RAMSpeedList is N_RAMSpeedList1 - 2,
        sub_string(RAMSpeedList1, 1, N_RAMSpeedList, _, RAMSpeedList2),
        split_string(RAMSpeedList2, ',', '', RAMSpeedList),

        assert(motherboard(ID, Manufacturer, Name, BasePrice, LaunchDate, SocketCompatibilityList, ATXType, MaxMemoryRam, RAMType, RAMSlots, RAMSpeedList)),
        assertMotherboards(Lines).
assertMotherboards([_|Lines]):- assertMotherboards(Lines).

loadRAMs():-
        open('../BD/RAMs.txt', read, Stream),
        readLines(Stream, Lines),
        assertRAMs(Lines),
        close(Stream).

assertRAMs([]):- !.
assertRAMs([X|Lines]):- 
        numAtributos(ram, NumAtributos),
        length(X, NumAtributos),!,
        X = [ID, Manufacturer, Name, BasePrice1, LaunchDate, Speed1, Capacity1, SlotsCount1, RamType, Voltage1],
        number_string(BasePrice, BasePrice1),
        number_string(Speed, Speed1),
        number_string(Capacity, Capacity1),
        number_string(SlotsCount, SlotsCount1),
        number_string(Voltage, Voltage1),
        assert(ram(ID, Manufacturer, Name, BasePrice, LaunchDate, Speed, Capacity, SlotsCount, RamType, Voltage)),
        assertRAMs(Lines).
assertRAMs([_|Lines]):- assertRAMs(Lines).

loadPowerSupplies():-
        open('../BD/PowerSupplies.txt', read, Stream),
        readLines(Stream, Lines),
        assertPowerSupplies(Lines),
        close(Stream).

assertPowerSupplies([]):- !.
assertPowerSupplies([X|Lines]):- 
        numAtributos(powerSupply, NumAtributos),
        length(X, NumAtributos),!,
        X = [ID, Manufacturer, Name, BasePrice1, LaunchDate, Capacity1, EnergyEfficiency, Modular, ATXCompatibilityList1],
        number_string(BasePrice, BasePrice1),
        number_string(Capacity, Capacity1),

        string_length(ATXCompatibilityList1, N_ATXCompatibilityList1),
        N_ATXCompatibilityList is N_ATXCompatibilityList1 - 2,
        sub_string(ATXCompatibilityList1, 1, N_ATXCompatibilityList, _, ATXCompatibilityList2),
        split_string(ATXCompatibilityList2, ',', '', ATXCompatibilityList),

        assert(powerSupply(ID, Manufacturer, Name, BasePrice, LaunchDate, Capacity, EnergyEfficiency, Modular, ATXCompatibilityList)),
        assertPowerSupplies(Lines).
assertPowerSupplies([_|Lines]):- assertPowerSupplies(Lines).

loadStorages():-
        open('../BD/Storages.txt', read, Stream),
        readLines(Stream, Lines),
        assertStorages(Lines),
        close(Stream).

assertStorages([]):- !.
assertStorages([X|Lines]):- 
        numAtributos(storage, NumAtributos),
        length(X, NumAtributos),!,
        X = [ID, Manufacturer, Name, BasePrice1, LaunchDate, IsSSD1, Capacity, Cache, Benchmark],
        number_string(BasePrice, BasePrice1),

        (IsSSD1 == 'true', !, IsSSD = true
        ; IsSSD = false),
       
        assert(storage(ID, Manufacturer, Name, BasePrice, LaunchDate, IsSSD, Capacity, Cache, Benchmark)),
        assertStorages(Lines).
assertStorages([_|Lines]):- assertStorages(Lines).

loadCases():-
        open('../BD/Cases.txt', read, Stream),
        readLines(Stream, Lines),
        assertCases(Lines),
        close(Stream).

assertCases([]):- !.
assertCases([X|Lines]):- 
        numAtributos(case, NumAtributos),
        length(X, NumAtributos),!,
        X = [ID, Manufacturer, Name, BasePrice1, LaunchDate, SizeType, ATXCompatibilityList1, Color],
        number_string(BasePrice, BasePrice1),

        string_length(ATXCompatibilityList1, N_ATXCompatibilityList1),
        N_ATXCompatibilityList is N_ATXCompatibilityList1 - 2,
        sub_string(ATXCompatibilityList1, 1, N_ATXCompatibilityList, _, ATXCompatibilityList2),
        split_string(ATXCompatibilityList2, ',', '', ATXCompatibilityList),

        assert(case(ID, Manufacturer, Name, BasePrice, LaunchDate, SizeType, ATXCompatibilityList, Color)),
        assertCases(Lines).
assertCases([_|Lines]):- assertCases(Lines).

loadBC():- loadGPUs(), loadCPUs(), loadCPUCoolers(), loadMotherboards(), loadRAMs(), loadPowerSupplies(), loadStorages(), loadCases().