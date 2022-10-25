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
splitLineString(LineStr, SplitLineStr):- split_string(LineStr, ';', '', SplitLineStr), !.

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

loadBC():- loadGPUs(), loadCPUs(), loadCPUCoolers(), loadMotherboards(), loadRAMs(), loadPowerSupplies(), loadStorages(), loadCases().