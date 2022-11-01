%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% Utils

:-op(220,xfx,then).
:-op(35,xfy,when).
:-op(240,fx,rule).
:-op(250,xfy,priorityGroup).
:-op(500,fy,not).
:-op(600,xfy,and).

:-dynamic fact/2.

getNextFactID(NextFactID):- 
    findall(ID, fact(ID, _), LFacts),
    (max_list(LFacts, LastFactID), NextFactID is LastFactID + 1
    ; NextFactID is 1).

% Finds all unique values of priority groups used by the rules.
getListOfUniqueRulesPriorityGroups(LRPGs):- findall(RPG, rule _ priorityGroup RPG when _ then _, LRPGs1), sort(LRPGs1, LRPGs).

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% Regras

% Budget question between 300 and 600
rule "1a" priorityGroup 1
        when
        [evalQuestion(askBudgetQuestion(300, 600))]
        then
        [insert_fact(budgetType("BAIXO"))].

% Budget question between 600 and 1000
rule "1b" priorityGroup 1
        when
        [evalQuestion(askBudgetQuestion(600, 1000))]
        then
        [insert_fact(budgetType("MEDIO_BAIXO"))].

% Budget question between 1000 and 1500
rule "1c" priorityGroup 1
        when
        [evalQuestion(askBudgetQuestion(1000, 1500))]
	then
        [insert_fact(budgetType("MEDIO"))].

% Budget question between 1500 and 2000
rule "1d" priorityGroup 1
	when
        [evalQuestion(askBudgetQuestion(1500, 2000))]
	then
        [insert_fact(budgetType("MEDIO_ALTO"))].

% Budget question more than 2000
rule "1e" priorityGroup 1
	when
        [evalQuestion(askBudgetQuestion(2000, 999999999))]
	then
        [insert_fact(budgetType("ALTO"))].

% Finality question when Budget type is low and answer is Simple Web Navigation
rule "2a1" priorityGroup 1
        when
        [budgetType("BAIXO") and evalQuestion(askQuestion(choose_finality_low_budget, 1)) and evalQuestion(askQuestion(choose_finality_navegacao_web, 1))]
	then
        [insert_fact(finality("NAVEGACAO_WEB_SIMPLES")), insert_fact(needsDedicatedGPU(false))].

% Finality question when Budget type is low and answer is Complex Web Navigation
rule "2a2" priorityGroup 1
	when
        [budgetType("BAIXO") and evalQuestion(askQuestion(choose_finality_low_budget, 1)) and evalQuestion(askQuestion(choose_finality_navegacao_web, 2))]
	then
        [insert_fact(finality("NAVEGACAO_WEB_COMPLEXO")), insert_fact(needsDedicatedGPU(false))].

% Finality question when Budget type is low and answer is Simple Office Applications
rule "2b1" priorityGroup 1
	when
        [budgetType("BAIXO") and evalQuestion(askQuestion(choose_finality_low_budget, 2)) and evalQuestion(askQuestion(choose_finality_aplicacoes_office, 1))]
	then
        [insert_fact(finality("APLICACOES_OFFICE_BASICO")), insert_fact(needsDedicatedGPU(false))].

% Finality question when Budget type is low and answer is Professional Office Applications
rule "2b2" priorityGroup 1
	when
        [budgetType("BAIXO") and evalQuestion(askQuestion(choose_finality_low_budget, 2)) and evalQuestion(askQuestion(choose_finality_aplicacoes_office, 2))]
	then
        [insert_fact(finality("APLICACOES_OFFICE_PROFISSIONAL")), insert_fact(needsDedicatedGPU(false))].

% Finality question when Budget type is low and answer is Professional Applications with DB
rule "2c1" priorityGroup 1
	when
        [budgetType("BAIXO") and evalQuestion(askQuestion(choose_finality_low_budget, 3)) and evalQuestion(askQuestion(choose_finality_aplicacoes_profissionais, 1))]
	then
        [insert_fact(finality("APLICACOES_PROFISSIONAIS_COM_BASE_DADOS")), insert_fact(needsDedicatedGPU(false))].

% Finality question when Budget type is low and answer is Professional Applications without DB
rule "2c2" priorityGroup 1
	when
        [budgetType("BAIXO") and evalQuestion(askQuestion(choose_finality_low_budget, 3)) and evalQuestion(askQuestion(choose_finality_aplicacoes_profissionais, 2))]
	then
        [insert_fact(finality("APLICACOES_PROFISSIONAIS_SEM_BASE_DADOS")), insert_fact(needsDedicatedGPU(false))].

% Finality question when Budget type is not low and answer is Simple Web Navigation
rule "3a1" priorityGroup 1
	when
        [not budgetType("NA") and not budgetType("BAIXO") and evalQuestion(askQuestion(choose_finality_non_low_budget, 1)) and evalQuestion(askQuestion(choose_finality_navegacao_web, 1))]
	then
        [insert_fact(finality("NAVEGACAO_WEB_SIMPLES")), insert_fact(needsDedicatedGPU(false))].

% Finality question when Budget type isn"t low and answer is Complex Web Navigation
rule "3a2" priorityGroup 1
	when
        [not budgetType("NA") and not budgetType("BAIXO") and evalQuestion(askQuestion(choose_finality_non_low_budget, 1)) and evalQuestion(askQuestion(choose_finality_navegacao_web, 2))]
	then
        [insert_fact(finality("NAVEGACAO_WEB_COMPLEXO")), insert_fact(needsDedicatedGPU(false))].

% Finality question when Budget type isn"t low and answer is Simple Office Applications
rule "3b1" priorityGroup 1
	when
        [not budgetType("NA") and not budgetType("BAIXO") and evalQuestion(askQuestion(choose_finality_non_low_budget, 2)) and evalQuestion(askQuestion(choose_finality_aplicacoes_office, 1))]
	then
        [insert_fact(finality("APLICACOES_OFFICE_BASICO")), insert_fact(needsDedicatedGPU(false))].

% Finality question when Budget type isn"t low and answer is Professional Office Applications
rule "3b2" priorityGroup 1
	when
        [not budgetType("NA") and not budgetType("BAIXO") and evalQuestion(askQuestion(choose_finality_non_low_budget, 2)) and evalQuestion(askQuestion(choose_finality_aplicacoes_office, 2))]
	then
        [insert_fact(finality("APLICACOES_OFFICE_PROFISSIONAL")), insert_fact(needsDedicatedGPU(false))].

% Finality question when Budget type isn"t low and answer is Professional Applications with DB
rule "3c1" priorityGroup 1
        when
        [not budgetType("NA") and not budgetType("BAIXO") and evalQuestion(askQuestion(choose_finality_non_low_budget, 3)) and evalQuestion(askQuestion(choose_finality_aplicacoes_profissionais, 1))]
	then
        [insert_fact(finality("APLICACOES_PROFISSIONAIS_COM_BASE_DADOS")), insert_fact(needsDedicatedGPU(false))].

% Finality question when Budget type isn"t low and answer is Professional Applications without DB
rule "3c2" priorityGroup 1
        when
        [not budgetType("NA") and not budgetType("BAIXO") and evalQuestion(askQuestion(choose_finality_non_low_budget, 3)) and evalQuestion(askQuestion(choose_finality_aplicacoes_profissionais, 2))]
	then
        [insert_fact(finality("APLICACOES_PROFISSIONAIS_SEM_BASE_DADOS")), insert_fact(needsDedicatedGPU(false))].

% Finality question when Budget type isn"t low and answer is Basic Games
rule "3d1" priorityGroup 1
        when
        [not budgetType("NA") and not budgetType("BAIXO") and evalQuestion(askQuestion(choose_finality_non_low_budget, 4))]
	then
        [insert_fact(finality("JOGOS_BASICOS")), insert_fact(needsDedicatedGPU(false))].

% Finality question when Budget type isn"t low and answer is Advanced Programs of 3d Modelation
rule "3e1" priorityGroup 1
        when
        [not budgetType("NA") and not budgetType("BAIXO") and evalQuestion(askQuestion(choose_finality_non_low_budget, 5)) and evalQuestion(askQuestion(choose_finality_programas_avancados, 1))]
	then
        [insert_fact(finality("PROGRAMAS_AVANCADOS_MODELACAO_3D")), insert_fact(needsDedicatedGPU(true))].

% Finality question when Budget type isn"t low and answer is Advanced Programs of Graphic Design
rule "3e2" priorityGroup 1
        when
        [not budgetType("NA") and not budgetType("BAIXO") and evalQuestion(askQuestion(choose_finality_non_low_budget, 5)) and evalQuestion(askQuestion(choose_finality_programas_avancados, 2))]
	then
        [insert_fact(finality("PROGRAMAS_AVANCADOS_DESIGN_GRAFICO")), insert_fact(needsDedicatedGPU(true))].

% Finality question when Budget type isn"t low and answer is Advanced Programs of Mathematic Calculus
rule "3e3" priorityGroup 1
        when
        [not budgetType("NA") and not budgetType("BAIXO") and evalQuestion(askQuestion(choose_finality_non_low_budget, 5)) and evalQuestion(askQuestion(choose_finality_programas_avancados, 3))]
	then
        [insert_fact(finality("PROGRAMAS_AVANCADOS_CALCULO_MATEMATICO")), insert_fact(needsDedicatedGPU(false))].

% Finality question when Budget type isn"t low and answer is Gaming
rule "3f1" priorityGroup 1
        when
        [not budgetType("NA") and not budgetType("BAIXO") and evalQuestion(askQuestion(choose_finality_non_low_budget, 6))]
	then
        [insert_fact(finality("GAMING")), insert_fact(needsDedicatedGPU(true))].

% Finality question when Budget type isn"t low and answer is Image Treatment
rule "3g1" priorityGroup 1
        when
        [not budgetType("NA") and not budgetType("BAIXO") and evalQuestion(askQuestion(choose_finality_non_low_budget, 7))]
	then
        [insert_fact(finality("TRATAMENTO_DE_IMAGEM")), insert_fact(needsDedicatedGPU(true))].

% Finality question when Budget type isn"t low and answer is Video Edition 4K/8K
rule "3h1" priorityGroup 1
        when
        [not budgetType("NA") and not budgetType("BAIXO") and evalQuestion(askQuestion(choose_finality_non_low_budget, 8))]
	then
        [insert_fact(finality("EDICAO_DE_VIDEO_4k_8K")), insert_fact(needsDedicatedGPU(true))].

% File Type and Daily use question when Finality is Simple Web Navigation and answers are Word/excel/PowerPoint and 2 to 4 hours a day - used to find define ideal storage and RAM
rule "r5a1" priorityGroup 1
        when
	[finality("NAVEGACAO_WEB_SIMPLES") and evalQuestion(askQuestion(choose_file_type, 1)) and evalQuestion(askQuestion(choose_duration_per_day, 1))]
	then
        [insert_fact(minStorage_isSSD(true)), insert_fact(minStorage_capacity(240)), insert_fact(minRAM(8)), insert_fact(minRAMSpeed(2600)), insert_fact(adequateMinCPUBenchmark(120)), insert_fact(durationDayChoice(1))].

% File Type and Daily use question when Finality is Simple Web Navigation and answers are Word/excel/PowerPoint and more than 4 hours a day - used to find define ideal storage and RAM
rule "r5a2" priorityGroup 1
        when
	[finality("NAVEGACAO_WEB_SIMPLES") and evalQuestion(askQuestion(choose_file_type, 1)) and evalQuestion(askQuestion(choose_duration_per_day, 2))]
	then
        [insert_fact(minStorage_isSSD(true)), insert_fact(minStorage_capacity(240)), insert_fact(minRAM(8)), insert_fact(minRAMSpeed(2600)), insert_fact(adequateMinCPUBenchmark(120)), insert_fact(durationDayChoice(2))].

% File Type and Daily use question when Finality is Simple Web Navigation and answers are Images and 2 to 4 hours a day - used to find define ideal storage and RAM
rule "r5a3" priorityGroup 1
        when
	[finality("NAVEGACAO_WEB_SIMPLES") and evalQuestion(askQuestion(choose_file_type, 2)) and evalQuestion(askQuestion(choose_duration_per_day, 1))]
	then
        [insert_fact(minStorage_isSSD(true)), insert_fact(minStorage_capacity(480)), insert_fact(minRAM(8)), insert_fact(minRAMSpeed(2600)), insert_fact(adequateMinCPUBenchmark(120)), insert_fact(durationDayChoice(1))].

% File Type and Daily use question when Finality is Simple Web Navigation and answers are Images and more than 4 hours a day - used to find define ideal storage and RAM
rule "r5a4" priorityGroup 1
        when
	[finality("NAVEGACAO_WEB_SIMPLES") and evalQuestion(askQuestion(choose_file_type, 2)) and evalQuestion(askQuestion(choose_duration_per_day, 2))]
	then
        [insert_fact(minStorage_isSSD(true)), insert_fact(minStorage_capacity(480)), insert_fact(minRAM(8)), insert_fact(minRAMSpeed(2600)), insert_fact(adequateMinCPUBenchmark(120)), insert_fact(durationDayChoice(2))].

% File Type and Daily use question when Finality is Complex Web Navigation and answers are Word/excel/PowerPoint and 2 to 4 hours a day - used to find define ideal storage and RAM
rule "r5b1" priorityGroup 1
        when
	[finality("NAVEGACAO_WEB_COMPLEXO") and evalQuestion(askQuestion(choose_file_type, 1)) and evalQuestion(askQuestion(choose_duration_per_day, 1))]
	then
        [insert_fact(minStorage_isSSD(true)), insert_fact(minStorage_capacity(240)), insert_fact(minRAM(16)), insert_fact(minRAMSpeed(2600)), insert_fact(adequateMinCPUBenchmark(120)), insert_fact(durationDayChoice(1))].

% File Type and Daily use question when Finality is Complex Web Navigation and answers are Word/excel/PowerPoint and more than 4 hours a day - used to find define ideal storage and RAM
rule "r5b2" priorityGroup 1
        when
	[finality("NAVEGACAO_WEB_COMPLEXO") and evalQuestion(askQuestion(choose_file_type, 1)) and evalQuestion(askQuestion(choose_duration_per_day, 2))]
	then
        [insert_fact(minStorage_isSSD(true)), insert_fact(minStorage_capacity(240)), insert_fact(minRAM(16)), insert_fact(minRAMSpeed(3600)), insert_fact(adequateMinCPUBenchmark(120)), insert_fact(durationDayChoice(2))].

% File Type and Daily use question when Finality is Complex Web Navigation and answers are Images and 2 to 4 hours a day - used to find define ideal storage and RAM
rule "r5b3" priorityGroup 1
        when
	[finality("NAVEGACAO_WEB_COMPLEXO") and evalQuestion(askQuestion(choose_file_type, 2)) and evalQuestion(askQuestion(choose_duration_per_day, 1))]
	then
        [insert_fact(minStorage_isSSD(true)), insert_fact(minStorage_capacity(480)), insert_fact(minRAM(16)), insert_fact(minRAMSpeed(2600)), insert_fact(adequateMinCPUBenchmark(120)), insert_fact(durationDayChoice(1))].

% File Type and Daily use question when Finality is Complex Web Navigation and answers are Images and more than 4 hours a day - used to find define ideal storage and RAM
rule "r5b4" priorityGroup 1
        when
	[finality("NAVEGACAO_WEB_COMPLEXO") and evalQuestion(askQuestion(choose_file_type, 2)) and evalQuestion(askQuestion(choose_duration_per_day, 2))]
	then
        [insert_fact(minStorage_isSSD(true)), insert_fact(minStorage_capacity(480)), insert_fact(minRAM(16)), insert_fact(minRAMSpeed(3600)), insert_fact(adequateMinCPUBenchmark(120)) and  insert_fact(durationDayChoice(2))].

% File Type and Daily use question when Finality is Simple Office Applications and answers are Word/excel/PowerPoint and 2 to 4 hours a day - used to find define ideal storage and RAM
rule "r5c1" priorityGroup 1
        when
	[finality("APLICACOES_OFFICE_BASICO") and evalQuestion(askQuestion(choose_file_type, 1)) and evalQuestion(askQuestion(choose_duration_per_day, 1))]
	then
        [insert_fact(minStorage_isSSD(true)), insert_fact(minStorage_capacity(240)), insert_fact(minRAM(8)), insert_fact(minRAMSpeed(2600)), insert_fact(adequateMinCPUBenchmark(120)), insert_fact(durationDayChoice(1))].

% File Type and Daily use question when Finality is Simple Office Applications and answers are Word/excel/PowerPoint and more than 4 hours a day - used to find define ideal storage and RAM
rule "r5c2" priorityGroup 1
        when
	[finality("APLICACOES_OFFICE_BASICO") and evalQuestion(askQuestion(choose_file_type, 1)) and evalQuestion(askQuestion(choose_duration_per_day, 2))]
	then
        [insert_fact(minStorage_isSSD(true)), insert_fact(minStorage_capacity(240)), insert_fact(minRAM(8)), insert_fact(minRAMSpeed(2600)), insert_fact(adequateMinCPUBenchmark(120)), insert_fact(durationDayChoice(2))].

% File Type and Daily use question when Finality is Simple Office Applications and answers are Images and 2 to 4 hours a day - used to find define ideal storage and RAM
rule "r5c3" priorityGroup 1
	when
	[finality("APLICACOES_OFFICE_BASICO") and evalQuestion(askQuestion(choose_file_type, 2)) and evalQuestion(askQuestion(choose_duration_per_day, 1))]
	then
        [insert_fact(minStorage_isSSD(true)), insert_fact(minStorage_capacity(240)), insert_fact(minRAM(8)), insert_fact(minRAMSpeed(2600)), insert_fact(adequateMinCPUBenchmark(120)), insert_fact(durationDayChoice(1))].

% File Type and Daily use question when Finality is Simple Office Applications and answers are Images and more than 4 hours a day - used to find define ideal storage and RAM
rule "r5c4" priorityGroup 1
        when
	[finality("APLICACOES_OFFICE_BASICO") and evalQuestion(askQuestion(choose_file_type, 2)) and evalQuestion(askQuestion(choose_duration_per_day, 2))]
	then
        [insert_fact(minStorage_isSSD(true)), insert_fact(minStorage_capacity(480)), insert_fact(minRAM(8)), insert_fact(minRAMSpeed(2600)), insert_fact(adequateMinCPUBenchmark(120)), insert_fact(durationDayChoice(2))].

% File Type and Daily use question when Finality is Professional Office Applications and answers are Word/excel/PowerPoint and 2 to 4 hours a day - used to find define ideal storage and RAM
rule "r5d1" priorityGroup 1
	when
	[finality("APLICACOES_OFFICE_PROFISSIONAL") and evalQuestion(askQuestion(choose_file_type, 1)) and evalQuestion(askQuestion(choose_duration_per_day, 1))]
	then
        [insert_fact(minStorage_isSSD(true)), insert_fact(minStorage_capacity(240)), insert_fact(minRAM(16)), insert_fact(minRAMSpeed(2600)), insert_fact(adequateMinCPUBenchmark(140)), insert_fact(durationDayChoice(1))].

% File Type and Daily use question when Finality is Professional Office Applications and answers are Word/excel/PowerPoint and more than 4 hours a day - used to find define ideal storage and RAM
rule "r5d2" priorityGroup 1
	when
	[finality("APLICACOES_OFFICE_PROFISSIONAL") and evalQuestion(askQuestion(choose_file_type, 1)) and evalQuestion(askQuestion(choose_duration_per_day, 2))]
	then
        [insert_fact(minStorage_isSSD(true)), insert_fact(minStorage_capacity(240)), insert_fact(minRAM(16)), insert_fact(minRAMSpeed(3600)), insert_fact(adequateMinCPUBenchmark(140)), insert_fact(durationDayChoice(2))].

% File Type and Daily use question when Finality is Professional Office Applications and answers are Images and 2 to 4 hours a day - used to find define ideal storage and RAM
rule "r5d3" priorityGroup 1
	when
	[finality("APLICACOES_OFFICE_PROFISSIONAL") and evalQuestion(askQuestion(choose_file_type, 2)) and evalQuestion(askQuestion(choose_duration_per_day, 1))]
	then
        [insert_fact(minStorage_isSSD(true)), insert_fact(minStorage_capacity(240)), insert_fact(minRAM(16)), insert_fact(minRAMSpeed(2600)), insert_fact(adequateMinCPUBenchmark(140)), insert_fact(durationDayChoice(1))].

% File Type and Daily use question when Finality is Professional Office Applications and answers are Word/excel/PowerPoint and more than 4 hours a day - used to find define ideal storage and RAM
rule "r5d4" priorityGroup 1
        when
	[finality("APLICACOES_OFFICE_PROFISSIONAL") and evalQuestion(askQuestion(choose_file_type, 2)) and evalQuestion(askQuestion(choose_duration_per_day, 2))]
	then
        [insert_fact(minStorage_isSSD(true)), insert_fact(minStorage_capacity(480)), insert_fact(minRAM(16)), insert_fact(minRAMSpeed(3600)), insert_fact(adequateMinCPUBenchmark(140)), insert_fact(durationDayChoice(2))].

% When Finality is Professional Applications without DB - used to find define ideal storage and RAM
rule "r5e1" priorityGroup 1
	when
	[finality("APLICACOES_PROFISSIONAIS_SEM_BASE_DADOS")]
	then
        [insert_fact(minStorage_isSSD(true)), insert_fact(minStorage_capacity(480)), insert_fact(minRAM(16)), insert_fact(minRAMSpeed(3600)), insert_fact(adequateMinCPUBenchmark(140))].

% When Finality is Professional Applications with Local DB - used to find define ideal storage and RAM
rule "r5e21" priorityGroup 1
	when
	[finality("APLICACOES_PROFISSIONAIS_COM_BASE_DADOS_LOCAIS")]
	then
        [insert_fact(minStorage_isSSD(true)), insert_fact(minStorage_capacity(480)), insert_fact(minRAM(32)), insert_fact(minRAMSpeed(2600)), insert_fact(adequateMinCPUBenchmark(140))].

% When Finality is Professional Applications with Remote DB - used to find define ideal storage and RAM
rule "r5e22" priorityGroup 1
	when
	[finality("APLICACOES_PROFISSIONAIS_COM_BASE_DADOS_REMOTAS")]
	then
        [insert_fact(minStorage_isSSD(true)), insert_fact(minStorage_capacity(240)), insert_fact(minRAM(16)), insert_fact(minRAMSpeed(3600)), insert_fact(adequateMinCPUBenchmark(140))].

% When Finality is Basic Games - used to find define ideal storage and RAM
rule "r5d1" priorityGroup 1
        when
	[finality("JOGOS_BASICOS")]
	then
        [insert_fact(minStorage_isSSD(true)), insert_fact(minStorage_capacity(240)), insert_fact(minRAM(16)), insert_fact(minRAMSpeed(3600)), insert_fact(adequateMinCPUBenchmark(140)), insert_fact(adequateMinGPUBenchmark(100))].

% When Finality is Advanced Programs of 3D Modelation - used to find define ideal storage and RAM
rule "r5e1" priorityGroup 1
	when
	[finality("PROGRAMAS_AVANCADOS_MODELACAO_3D")]
	then
        [insert_fact(minStorage_isSSD(true)), insert_fact(minStorage_capacity(240)), insert_fact(minRAM(32)), insert_fact(minRAMSpeed(2600)), insert_fact(adequateMinCPUBenchmark(170))].

% When Finality is Advanced Programs of Graphic Design - used to find define ideal storage, RAM and CPU
rule "r5e2" priorityGroup 1
	when
	[finality("PROGRAMAS_AVANCADOS_DESIGN_GRAFICO")]
	then
        [insert_fact(minStorage_isSSD(true)), insert_fact(minStorage_capacity(240)), insert_fact(minRAM(32)), insert_fact(minRAMSpeed(2600)), insert_fact(adequateMinCPUBenchmark(170))].

% When Finality is Advanced Programs of Mathematic Calculus - used to find define ideal storage, RAM and CPU
rule "r5e3" priorityGroup 1
        when
	[finality("PROGRAMAS_AVANCADOS_CALCULO_MATEMATICO")]
	then
        [insert_fact(minStorage_isSSD(true)), insert_fact(minStorage_capacity(240)), insert_fact(minRAM(32)), insert_fact(minRAMSpeed(2600)), insert_fact(adequateMinCPUBenchmark(170))].

% When Finality is Basic Image Treatment - used to find define ideal storage, RAM and CPU
rule "r5f1" priorityGroup 1
	when
	[finality("TRATAMENTO_DE_IMAGEM_BASICO")]
	then
        [insert_fact(minStorage_isSSD(true)), insert_fact(minStorage_capacity(240)), insert_fact(minScndStorage_capacity(1000)), insert_fact(minRAM(32)), insert_fact(minRAMSpeed(2600)), insert_fact(adequateMinCPUBenchmark(170))].

% When Finality is Professional Image Treatment - used to find define ideal storage, RAM and CPU
rule "r5f2" priorityGroup 1
	when
	[finality("TRATAMENTO_DE_IMAGEM_PROFISSIONAL")]
	then
        [insert_fact(minStorage_isSSD(true)), insert_fact(minStorage_capacity(240)), insert_fact(minScndStorage_capacity(2000)), insert_fact(minRAM(32)), insert_fact(minRAMSpeed(3200)), insert_fact(adequateMinCPUBenchmark(180))].

% When Finality is 4K/8K Video Editing - used to find define ideal storage, RAM and CPU
rule "r5g1" priorityGroup 1
	when
	[finality("EDICAO_DE_VIDEO_4k_8K")]
	then
        [insert_fact(minStorage_isSSD(true)), insert_fact(minStorage_capacity(240)), insert_fact(minScndStorage_capacity(1000)), insert_fact(minRAM(32)), insert_fact(minRAMSpeed(2600)), insert_fact(adequateMinCPUBenchmark(170)), insert_fact(adequateMinGPUBenchmark(100))].

% When Finality is Gaming and Budget Type is Medium Low  - used to find define ideal storage, RAM, CPU and GPU
rule "r5h11" priorityGroup 1
	when
	[finality("GAMING") and budgetType("MEDIO_BAIXO")]
	then
        [insert_fact(minStorage_isSSD(true)), insert_fact(minStorage_capacity(240)), insert_fact(minRAM(32)), insert_fact(minRAMSpeed(2600)), insert_fact(adequateMinCPUBenchmark(170)), insert_fact(adequateMinGPUBenchmark(120))].

% When Finality is Gaming and Budget Type is Medium - used to find define ideal storage, RAM, CPU and GPU
rule "r5h12" priorityGroup 1
	when
	[finality("GAMING") and budgetType("MEDIO")]
	then
        [insert_fact(minStorage_isSSD(true)), insert_fact(minStorage_capacity(240)), insert_fact(minRAM(32)), insert_fact(minRAMSpeed(2600)), insert_fact(adequateMinCPUBenchmark(170)), insert_fact(adequateMinGPUBenchmark(120))].

% When Finality is Gaming and Budget Type is Medium High - used to find define ideal storage, RAM, CPU and GPU
rule "r5h13" priorityGroup 1
	when
	[finality("GAMING") and budgetType("MEDIO_ALTO")]
	then
        [insert_fact(minStorage_isSSD(true)), insert_fact(minStorage_capacity(240)), insert_fact(minRAM(32)), insert_fact(minRAMSpeed(2600)), insert_fact(adequateMinCPUBenchmark(170)), insert_fact(adequateMinGPUBenchmark(170))].

% When Finality is Gaming and Budget Type is High - used to find define ideal storage, RAM, CPU and GPU
rule "r5h14" priorityGroup 1
        when
	[finality("GAMING") and budgetType("ALTO")]
	then
        [insert_fact(minStorage_isSSD(true)), insert_fact(minStorage_capacity(240)), insert_fact(minRAM(32)), insert_fact(minRAMSpeed(2600)), insert_fact(adequateMinCPUBenchmark(170)), insert_fact(adequateMinGPUBenchmark(200))].

% System doesn't need a Dedicated GPU but user prefers having one
rule "r7bf1a" priorityGroup 2
        when
        [needsDedicatedGPU(false) and evalQuestion(askQuestion(choose_if_wants_dedicated_graphics_card, 1))]
        then
        [insert_fact(prefersDedicatedGPU(true))].

% System doesn't need a Dedicated GPU and user doesn't prefer one either
rule "r7bf1b" priorityGroup 2
        when
        [needsDedicatedGPU(false) and evalQuestion(askQuestion(choose_if_wants_dedicated_graphics_card, 2))]
        then
        [insert_fact(prefersDedicatedGPU(false))].

% User needs dedicated GPU has manufacturer preference, answer NVIDIA
rule "r8a1a" priorityGroup 2
        when
        [needsDedicatedGPU(true) and evalQuestion(askQuestion(choose_gpu_manufacturer, 1))]
        then
        [insert_fact(gpuManufacturerPreferred("NVIDIA"))].

% User prefers dedicated GPU and has manufacturer preference, answer NVIDIA
rule "r8a1b" priorityGroup 2
        when
        [prefersDedicatedGPU(true) and evalQuestion(askQuestion(choose_gpu_manufacturer, 1))]
        then
        [insert_fact(gpuManufacturerPreferred("NVIDIA"))].

% User needs dedicated GPU and has manufacturer preference, answer AMD
rule "r8a2a" priorityGroup 2
        when
        [needsDedicatedGPU(true) and evalQuestion(askQuestion(choose_gpu_manufacturer, 2))]
        then
        [insert_fact(gpuManufacturerPreferred("AMD"))].

% User prefers dedicated GPU and has manufacturer preference, answer AMD
rule "r8a2b" priorityGroup 2
        when
        [prefersDedicatedGPU(true) and evalQuestion(askQuestion(choose_gpu_manufacturer, 2))]
        then
        [insert_fact(gpuManufacturerPreferred("AMD"))].

% Finality is Gaming, user has GPU brand preference
% ANALISAR MELHOR!!!
%rule "r8b1"
%        when
%        [finality("GAMING") and evalQuestion(askQuestion(choose_if_wants_preferred_gpu_brand, 1)) and evalQuestion(askQuestion(choose_preferred_gpu_brand, Preferred_GPU_Brand))]
%        then
%        [insert_fact(preferredGPUBrand(Preferred_GPU_Brand))].

% User has CPU manufacturer preference, INTEL
rule "r9a1" priorityGroup 3
        when
        [evalQuestion(askQuestion(choose_cpu_manufacturer, 1))]
        then
        [insert_fact(cpuManufacturerPreferred("INTEL"))].

% User has CPU manufacturer preference, AMD
rule "r9a2" priorityGroup 3
        when
        [evalQuestion(askQuestion(choose_cpu_manufacturer, 2))]
        then
        [insert_fact(cpuManufacturerPreferred("AMD"))].

% Finality is Gaming, user wants to do OC and prefers WaterCooling
rule "r10a1" priorityGroup 4
        when
        [finality("GAMING") and evalQuestion(askQuestion(choose_cpu_oc, 1)) and evalQuestion(askQuestion(choose_cooler_type, 1))]
        then
        [insert_fact(needsCPUCooler(true)), insert_fact(cpuCooler_isWaterCooled(true))].

% Finality is Gaming, user wants to do OC and prefers Fanless
rule "r10a2" priorityGroup 4
        when
        [finality("GAMING") and evalQuestion(askQuestion(choose_cpu_oc, 1)) and evalQuestion(askQuestion(choose_cooler_type, 2))]
        then
        [insert_fact(needsCPUCooler(true)), insert_fact(cpuCooler_isFanless(true))].

% Finality is Gaming, user wants to do OC and prefers Normal
rule "r10a3a" priorityGroup 4
        when
        [finality("GAMING") and evalQuestion(askQuestion(choose_cpu_oc, 1)) and evalQuestion(askQuestion(choose_cooler_type, 3))]
        then
        [insert_fact(needsCPUCooler(true))].

% Finality is Gaming, user wants to do OC and doesn't have preference
rule "r10a3b" priorityGroup 4
        when
        [finality("GAMING") and evalQuestion(askQuestion(choose_cpu_oc, 1)) and evalQuestion(askQuestion(choose_cooler_type, 4))]
        then
        [insert_fact(needsCPUCooler(true))].

% Choose Environment Energy Efficiency Instability, answer Negative
rule "r11a1" priorityGroup 5
        when
        [evalQuestion(askQuestion(choose_environment_energy_efficiency_instability, 1))]
	then
        [insert_fact(minEnergyEfficiencyNeeded("_80PLUS"))].

% Choose Environment Energy Efficiency, answer Positive, a bit problematic
rule "r11a2" priorityGroup 5
        when
        [evalQuestion(askQuestion(choose_environment_energy_efficiency_instability, 2))]
	then
        [insert_fact(minEnergyEfficiencyNeeded("_80PLUS_GOLD"))].

% Choose Environment Energy Efficiency, answer Positive, serious issue
rule "r11a3" priorityGroup 5
        when
        [evalQuestion(askQuestion(choose_environment_energy_efficiency_instability, 3))]
	then
        [insert_fact(minEnergyEfficiencyNeeded("_80PLUS_PLATINUM"))].

% Choose Case Color, answer Black
rule "r12a1" priorityGroup 5
        when
        [evalQuestion(askQuestion(choose_optional_case_color, 1)) and evalQuestion(askQuestion(choose_case_color, 1))]
	then
        [insert_fact(caseColorPreferred("Black"))].

% Choose Case Color, answer White
rule "r12a2" priorityGroup 5
        when
        [evalQuestion(askQuestion(choose_optional_case_color, 1)) and evalQuestion(askQuestion(choose_case_color, 2))]
	then
        [insert_fact(caseColorPreferred("White"))].

% Choose Case Size, answer Micro-ATX
rule "r12a1" priorityGroup 5
        when
        [evalQuestion(askQuestion(choose_case_size, 1))]
	then
        [insert_fact(caseSizePreferred("MID_TOWER"))].

% Choose Case Size, answer Mini-ATX
rule "r12a2" priorityGroup 5
        when
        [evalQuestion(askQuestion(choose_case_size, 2))]
	then
        [insert_fact(caseSizePreferred("MICRO_ATX_TOWER"))].

% Choose Case Size, answer MID Tower
rule "r12a3" priorityGroup 5
        when
        [evalQuestion(askQuestion(choose_case_size, 3))]
	then
        [insert_fact(caseSizePreferred("MINI_ATX_TOWER"))].

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%