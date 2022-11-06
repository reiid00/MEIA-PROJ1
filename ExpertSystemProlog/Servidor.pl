% Bibliotecas HTTP
:- use_module(library(http/thread_httpd)).
:- use_module(library(http/http_dispatch)).
:- use_module(library(http/http_parameters)).
:- use_module(library(http/http_client)).
:- use_module(library(http/http_open)).
:- use_module(library(http/thread_httpd)).
:- use_module(library(http/http_dispatch)).
:- use_module(library(http/http_error)).
:- use_module(library(http/http_cors)).
:- use_module(library(http/http_header)).
:- use_module(library(http/html_write)).
:- use_module(library(http/http_ssl_plugin)).
:- use_module(library(settings)).
:- use_module(library(uri)).
:- use_module(library(ssl)).

:- set_setting(http:cors, [*]).

% Bibliotecas JSON
:- use_module(library(http/json_convert)).
:- use_module(library(http/http_json)).
:- use_module(library(http/json)).

% Routes
:- http_handler('/findBestPC', findBestPC, []).

% Carregar dependências

:- consult('BC.pl').
:- consult('UI.pl').
:- consult('Solucao.pl').

% Criação de servidor HTTP em 'Port' que trata pedidos em JSON
server(Port):-
        loadBC(),
        http_server(http_dispatch, [port(Port)]).

stopServer(Port):-
        http_stop_server(Port,_).

findBestPC(Request):-
        http_read_json(Request, Json),
        json_to_prolog(Json, JsonProlog),
        insertBCData(JsonProlog),
        findBestPC1(PCJson),
        prolog_to_json(PCJson,PCJsonReply),
        reply_json(PCJsonReply).

insertBCData(JsonProlog):-
    arg(1,JsonProlog,Args),
    Args=[_=X1,_=X2,_=A3,_=A4,_=X5,_=A6,_=A7,_=X8,_=X9,_=A10, _=X11, _=X12, _=A13, _=A14, _=A15, _=A16, _=A17],
    atom_string(A3,X3),
    atom_string(A4,X4),
    atom_string(A6,B6),
    (B6 == "true", !, X6 = true; X6 = false),
    atom_string(A7,B7),
    (B7 == "true", !, X7 = true; X7 = false),
    atom_string(A10,X10),
    atom_string(A13,X13),
    atom_string(A14,X14),
    atom_string(A15,B15),
    (B15 == "true", !, X15 = true; X15 = false),
    atom_string(A16,B16),
    (B16 == "true", !, X16 = true; X16 = false),
    atom_string(A17,B17),
    (B17 == "true", !, X17 = true; X17 = false),
    retractall(fact(_,_)),
    retractall(answer(_,_)),
    assert(fact(1, adequateMinGPUBenchmark(X1))),
    assert(fact(2, adequateMinCPUBenchmark(X2))),
    (X3 \= "NA", !, assert(fact(3, gpuManufacturerPreferred(X3))); true),
    (X4 \= "NA", !, assert(fact(4, cpuManufacturerPreferred(X4))); true),
    assert(fact(4, cpuManufacturerPreferred(X4))),
    assert(answer(choose_budget, X5)),
    assert(fact(6, cpuCooler_isFanless(X6))),
    assert(fact(7, cpuCooler_isWaterCooled(X7))),
    assert(fact(8, minRAM(X8))),
    assert(fact(9, minRAMSpeed(X9))),
    assert(fact(10, minEnergyEfficiencyNeeded(X10))),
    assert(fact(11, minStorage_capacity(X11))),
    assert(fact(12, minSecondStorage_capacity(X12))),
    assert(fact(13, caseSizePreferred(X13))),
    (X14 \= "NA", !, assert(fact(14, caseColorPreferred(X14))); true),
    assert(fact(15, needsCPUCooler(X15))),
    assert(fact(16, needsDedicatedGPU(X16))),
    assert(fact(17, prefersDedicatedGPU(X17))).

findBestPC1(PCJson):-
	findBestPossibleHandmadeComputerBasedOnCurrentStateOfKnowledgeBase1(PC, _), !,
    PC = [GPU, CPU, CPUCooler, Motherboard, RAM, SSD, HDD, PowerSupply, Case],
    GPU = gpu(GPU_ID, _, _, _, _, _, _, _, _, _, _, _, _),
    CPU = cpu(CPU_ID, _, _, _, _, _, _, _, _, _, _, _),
    Motherboard = motherboard(Motherboard_ID, _, _, _, _, _, _, _, _, _, _),
    CPUCooler = cpuCooler(CPUCooler_ID, _, _, _, _, _, _, _, _),
    RAM = ram(RAM_ID, _, _, _, _, _, _, _, _, _),
    SSD = storage(SSD_ID, _, _, _, _, _, _, _, _, _),
    HDD = storage(HDD_ID, _, _, _, _, _, _, _, _, _),
    PowerSupply = powerSupply(PowerSupply_ID, _, _, _, _, _, _, _, _),
    Case = case(Case_ID, _, _, _, _, _, _, _), 
    PCJson = json([
        'GPU' = GPU_ID,
        'CPU' = CPU_ID,
        'Motherboard' = Motherboard_ID,
        'CPUCooler' = CPUCooler_ID,
        'RAM' = RAM_ID,
        'SSD' = SSD_ID,
        'HDD' = HDD_ID,
        'PowerSupply' = PowerSupply_ID,
        'Case' = Case_ID
        ]).