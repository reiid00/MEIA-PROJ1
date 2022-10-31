%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% Utils

:-op(220,xfx,then).
:-op(35,xfy,when).
:-op(240,fx,rule).
:-op(500,fy,not).
:-op(600,xfy,and).

:-dynamic fact/2.
% fact(ID, Fact)
% fact(1, budgetType("NA")).

getNextFactID(NextFactID):- 
    findall(ID, fact(ID, _), LFacts),
    (max_list(LFacts, LastFactID), NextFactID is LastFactID + 1
    ; NextFactID is 1).

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% Regras

% Budget question between 300 and 600
rule "1a"
        when
        [evalQuestion(askBudgetQuestion(300, 600))]
        then
        [cria_facto(budgetType("BAIXO"))].

% Budget question between 600 and 1000
rule "1b"
        when
        [evalQuestion(askBudgetQuestion(600, 1000))]
        then
        [cria_facto(budgetType("MEDIO_BAIXO"))].

% Budget question between 1000 and 1500
rule "1c"
        when
        [evalQuestion(askBudgetQuestion(1000, 1500))]
	then
        [cria_facto(budgetType("MEDIO"))].

% Budget question between 1500 and 2000
rule "1d"
	when
        [evalQuestion(askBudgetQuestion(1500, 2000))]
	then
        [cria_facto(budgetType("MEDIO_ALTO"))].

% Budget question more than 2000
rule "1e"
	when
        [evalQuestion(askBudgetQuestion(2000, 999999999))]
	then
        [cria_facto(budgetType("ALTO"))].

% Finality question when Budget type is low and answer is Simple Web Navigation
rule "2a1"
        when
        [budgetType("BAIXO") and evalQuestion(askQuestion(choose_finality_low_budget, 1)) and evalQuestion(askQuestion(choose_finality_navegacao_web, 1))]
	then
        [cria_facto(finality("NAVEGACAO_WEB_SIMPLES"))].

% Finality question when Budget type is low and answer is Complex Web Navigation
rule "2a2"
	when
        [budgetType("BAIXO") and evalQuestion(askQuestion(choose_finality_low_budget, 1)) and evalQuestion(askQuestion(choose_finality_navegacao_web, 2))]
	then
        [cria_facto(finality("NAVEGACAO_WEB_COMPLEXO"))].

% Finality question when Budget type is low and answer is Simple Office Applications
rule "2b1"
	when
        [budgetType("BAIXO") and evalQuestion(askQuestion(choose_finality_low_budget, 2)) and evalQuestion(askQuestion(choose_finality_aplicacoes_office, 1))]
	then
        [cria_facto(finality("APLICACOES_OFFICE_BASICO"))].

% Finality question when Budget type is low and answer is Professional Office Applications
rule "2b2"
	when
        [budgetType("BAIXO") and evalQuestion(askQuestion(choose_finality_low_budget, 2)) and evalQuestion(askQuestion(choose_finality_aplicacoes_office, 2))]
	then
        [cria_facto(finality("APLICACOES_OFFICE_PROFISSIONAL"))].

% Finality question when Budget type is low and answer is Professional Applications with DB
rule "2c1"
	when
        [budgetType("BAIXO") and evalQuestion(askQuestion(choose_finality_low_budget, 3)) and evalQuestion(askQuestion(choose_finality_aplicacoes_profissionais, 1))]
	then
        [cria_facto(finality("APLICACOES_PROFISSIONAIS_COM_BASE_DADOS"))].

% Finality question when Budget type is low and answer is Professional Applications without DB
rule "2c2"
	when
        [budgetType("BAIXO") and evalQuestion(askQuestion(choose_finality_low_budget, 3)) and evalQuestion(askQuestion(choose_finality_aplicacoes_profissionais, 2))]
	then
        [cria_facto(finality("APLICACOES_PROFISSIONAIS_SEM_BASE_DADOS"))].

% Finality question when Budget type is not low and answer is Simple Web Navigation
rule "3a1"
	when
        [not budgetType("NA") and not budgetType("BAIXO") and evalQuestion(askQuestion(choose_finality_non_low_budget, 1)) and evalQuestion(askQuestion(choose_finality_navegacao_web, 1))]
	then
        [cria_facto(finality("NAVEGACAO_WEB_SIMPLES"))].

% Finality question when Budget type isn"t low and answer is Complex Web Navigation
rule "3a2"
	when
        [not budgetType("NA") and not budgetType("BAIXO") and evalQuestion(askQuestion(choose_finality_non_low_budget, 1)) and evalQuestion(askQuestion(choose_finality_navegacao_web, 2))]
	then
        [cria_facto(finality("NAVEGACAO_WEB_COMPLEXO"))].

% Finality question when Budget type isn"t low and answer is Simple Office Applications
rule "3b1"
	when
        [not budgetType("NA") and not budgetType("BAIXO") and evalQuestion(askQuestion(choose_finality_non_low_budget, 2)) and evalQuestion(askQuestion(choose_finality_aplicacoes_office, 1))]
	then
        [cria_facto(finality("APLICACOES_OFFICE_BASICO"))].

% Finality question when Budget type isn"t low and answer is Professional Office Applications
rule "3b2"
	when
        [not budgetType("NA") and not budgetType("BAIXO") and evalQuestion(askQuestion(choose_finality_non_low_budget, 2)) and evalQuestion(askQuestion(choose_finality_aplicacoes_office, 2))]
	then
        [cria_facto(finality("APLICACOES_OFFICE_PROFISSIONAL"))].

% Finality question when Budget type isn"t low and answer is Professional Applications with DB
rule "3c1"
        when
        [not budgetType("NA") and not budgetType("BAIXO") and evalQuestion(askQuestion(choose_finality_non_low_budget, 3)) and evalQuestion(askQuestion(choose_finality_aplicacoes_profissionais, 1))]
	then
        [cria_facto(finality("APLICACOES_PROFISSIONAIS_COM_BASE_DADOS"))].

% Finality question when Budget type isn"t low and answer is Professional Applications without DB
rule "3c2"
        when
        [not budgetType("NA") and not budgetType("BAIXO") and evalQuestion(askQuestion(choose_finality_non_low_budget, 3)) and evalQuestion(askQuestion(choose_finality_aplicacoes_profissionais, 2))]
	then
        [cria_facto(finality("APLICACOES_PROFISSIONAIS_SEM_BASE_DADOS"))].

% Finality question when Budget type isn"t low and answer is Basic Games
rule "3d1"
        when
        [not budgetType("NA") and not budgetType("BAIXO") and evalQuestion(askQuestion(choose_finality_non_low_budget, 4))]
	then
        [cria_facto(finality("JOGOS_BASICOS"))].

% Finality question when Budget type isn"t low and answer is Advanced Programs of 3d Modelation
rule "3e1"
        when
        [not budgetType("NA") and not budgetType("BAIXO") and evalQuestion(askQuestion(choose_finality_non_low_budget, 5)) and evalQuestion(askQuestion(choose_finality_programas_avancados, 1))]
	then
        [cria_facto(finality("PROGRAMAS_AVANCADOS_MODELACAO_3D"))].

% Finality question when Budget type isn"t low and answer is Advanced Programs of Graphic Design
rule "3e2"
        when
        [not budgetType("NA") and not budgetType("BAIXO") and evalQuestion(askQuestion(choose_finality_non_low_budget, 5)) and evalQuestion(askQuestion(choose_finality_programas_avancados, 2))]
	then
        [cria_facto(finality("PROGRAMAS_AVANCADOS_DESIGN_GRAFICO"))].

% Finality question when Budget type isn"t low and answer is Advanced Programs of Mathematic Calculus
rule "3e3"
        when
        [not budgetType("NA") and not budgetType("BAIXO") and evalQuestion(askQuestion(choose_finality_non_low_budget, 5)) and evalQuestion(askQuestion(choose_finality_programas_avancados, 3))]
	then
        [cria_facto(finality("PROGRAMAS_AVANCADOS_CALCULO_MATEMATICO"))].

% Finality question when Budget type isn"t low and answer is Gaming
rule "3f1"
        when
        [not budgetType("NA") and not budgetType("BAIXO") and evalQuestion(askQuestion(choose_finality_non_low_budget, 6))]
	then
        [cria_facto(finality("GAMING"))].

% Finality question when Budget type isn"t low and answer is Image Treatment
rule "3g1"
        when
        [not budgetType("NA") and not budgetType("BAIXO") and evalQuestion(askQuestion(choose_finality_non_low_budget, 7))]
	then
        [cria_facto(finality("TRATAMENTO_DE_IMAGEM"))].

% Finality question when Budget type isn"t low and answer is Video Edition 4K/8K
rule "3h1"
        when
        [not budgetType("NA") and not budgetType("BAIXO") and evalQuestion(askQuestion(choose_finality_non_low_budget, 8))]
	then
        [cria_facto(finality("EDICAO_DE_VIDEO_4k_8K"))].

% File Type and Daily use question when Finality is Simple Web Navigation and answers are Word/excel/PowerPoint and 2 to 4 hours a day - used to find define ideal storage and RAM
rule "r5a1"
        when
	[finality(NAVEGACAO_WEB_SIMPLES) and evalQuestion(askQuestion(CHOOSE_FILE_TYPE, "1")) and evalQuestion(askQuestion(CHOOSE_DURATION_PER_DAY, "1"))]
        then
	    $hypothesis.minStorage=new Storage();
	    $hypothesis.minStorage.isSSD = true;
        $hypothesis.minStorage.capacity = 240;
        $hypothesis.minRAMPreferred =8;
        $hypothesis.minRAMSpeed=2600;
        $hypothesis.adequateMinCPUBenchmark=120;
        $hypothesis.durationDayChoice=1;

% File Type and Daily use question when Finality is Simple Web Navigation and answers are Word/excel/PowerPoint and more than 4 hours a day - used to find define ideal storage and RAM
rule "r5a2"
        when
	[finality(NAVEGACAO_WEB_SIMPLES) and evalQuestion(askQuestion(CHOOSE_FILE_TYPE, "1")) and evalQuestion(askQuestion(CHOOSE_DURATION_PER_DAY, "2"))]
	then
	    $hypothesis.minStorage.isSSD = true;
        $hypothesis.minStorage.capacity = 240;
        $hypothesis.minRAMPreferred =8;
        $hypothesis.minRAMSpeed=2600;
        $hypothesis.adequateMinCPUBenchmark=120;
        $hypothesis.durationDayChoice=2;
        update($hypothesis);

% File Type and Daily use question when Finality is Simple Web Navigation and answers are Images and 2 to 4 hours a day - used to find define ideal storage and RAM
rule "r5a3"
        when
	[finality(NAVEGACAO_WEB_SIMPLES) and evalQuestion(askQuestion(CHOOSE_FILE_TYPE, "2")) and evalQuestion(askQuestion(CHOOSE_DURATION_PER_DAY, "1"))]
        then
	    $hypothesis.minStorage.isSSD = true;
        $hypothesis.minStorage.capacity = 480;
        $hypothesis.minRAMPreferred =8;
        $hypothesis.minRAMSpeed=2600;
        $hypothesis.adequateMinCPUBenchmark=120;
        $hypothesis.durationDayChoice=1;
        update($hypothesis);

% File Type and Daily use question when Finality is Simple Web Navigation and answers are Images and more than 4 hours a day - used to find define ideal storage and RAM
rule "r5a4"
        when
	[finality(NAVEGACAO_WEB_SIMPLES) and evalQuestion(askQuestion(CHOOSE_FILE_TYPE, "2")) and evalQuestion(askQuestion(CHOOSE_DURATION_PER_DAY, "1"))]
	then
	    $hypothesis.minStorage.isSSD = true;
        $hypothesis.minStorage.capacity = 480;
        $hypothesis.minRAMPreferred =8;
        $hypothesis.minRAMSpeed=2600;
        $hypothesis.adequateMinCPUBenchmark=120;
        $hypothesis.durationDayChoice=1;
        update($hypothesis);

% File Type and Daily use question when Finality is Complex Web Navigation and answers are Word/excel/PowerPoint and 2 to 4 hours a day - used to find define ideal storage and RAM
rule "r5b1"
        when
	[finality(NAVEGACAO_WEB_COMPLEXO) and evalQuestion(askQuestion(CHOOSE_FILE_TYPE, "1")) and evalQuestion(askQuestion(CHOOSE_DURATION_PER_DAY, "1"))]
	then
	    $hypothesis.minStorage.isSSD = true;
        $hypothesis.minStorage.capacity = 240;
        $hypothesis.minRAMPreferred =16;
        $hypothesis.minRAMSpeed=2600;
        $hypothesis.adequateMinCPUBenchmark=120;
        $hypothesis.durationDayChoice=1;
        update($hypothesis);

% File Type and Daily use question when Finality is Complex Web Navigation and answers are Word/excel/PowerPoint and more than 4 hours a day - used to find define ideal storage and RAM
rule "r5b2"
        when
	[finality(NAVEGACAO_WEB_COMPLEXO) and evalQuestion(askQuestion(CHOOSE_FILE_TYPE, "1")) and evalQuestion(askQuestion(CHOOSE_DURATION_PER_DAY, "2"))]
	then
	    $hypothesis.minStorage.isSSD = true;
        $hypothesis.minStorage.capacity = 240;
        $hypothesis.minRAMPreferred =16;
        $hypothesis.minRAMSpeed=3600;
        $hypothesis.adequateMinCPUBenchmark=120;
        $hypothesis.durationDayChoice=1;

% File Type and Daily use question when Finality is Complex Web Navigation and answers are Images and 2 to 4 hours a day - used to find define ideal storage and RAM
rule "r5b3"
        when
	[finality(NAVEGACAO_WEB_COMPLEXO) and evalQuestion(askQuestion(CHOOSE_FILE_TYPE, "2")) and evalQuestion(askQuestion(CHOOSE_DURATION_PER_DAY, "1"))]
	then
	    $hypothesis.minStorage.isSSD = true;
        $hypothesis.minStorage.capacity = 480;
        $hypothesis.minRAMPreferred =16;
        $hypothesis.minRAMSpeed=2600;
        $hypothesis.adequateMinCPUBenchmark=120;
        $hypothesis.durationDayChoice=1;

% File Type and Daily use question when Finality is Complex Web Navigation and answers are Images and more than 4 hours a day - used to find define ideal storage and RAM
rule "r5b4"
        when
	[finality(NAVEGACAO_WEB_COMPLEXO) and evalQuestion(askQuestion(CHOOSE_FILE_TYPE, "2")) and evalQuestion(askQuestion(CHOOSE_DURATION_PER_DAY, "2"))]
	then
	    $hypothesis.minStorage.isSSD = true;
        $hypothesis.minStorage.capacity = 480;
        $hypothesis.minRAMPreferred =16;
        $hypothesis.minRAMSpeed=3600;
        $hypothesis.durationDayChoice=2;

% File Type and Daily use question when Finality is Simple Office Applications and answers are Word/excel/PowerPoint and 2 to 4 hours a day - used to find define ideal storage and RAM
rule "r5c1"
        when
	[finality(APLICACOES_OFFICE_BASICO) and evalQuestion(askQuestion(CHOOSE_FILE_TYPE, "1")) and evalQuestion(askQuestion(CHOOSE_DURATION_PER_DAY, "1"))]
	then
	    $hypothesis.minStorage.isSSD = true;
        $hypothesis.minStorage.capacity = 240;
        $hypothesis.minRAMPreferred =8;
        $hypothesis.minRAMSpeed=2600;
        $hypothesis.adequateMinCPUBenchmark=120;
        $hypothesis.durationDayChoice=1;

% File Type and Daily use question when Finality is Simple Office Applications and answers are Word/excel/PowerPoint and more than 4 hours a day - used to find define ideal storage and RAM
rule "r5c2"
        when
	[finality(APLICACOES_OFFICE_BASICO) and evalQuestion(askQuestion(CHOOSE_FILE_TYPE, "1")) and evalQuestion(askQuestion(CHOOSE_DURATION_PER_DAY, "2"))]
	then
	    $hypothesis.minStorage.isSSD = true;
        $hypothesis.minStorage.capacity = 240;
        $hypothesis.minRAMPreferred =8;
        $hypothesis.minRAMSpeed=2600;
        $hypothesis.adequateMinCPUBenchmark=120;
        $hypothesis.durationDayChoice=2;


rule "r5c3 - File Type and Daily use question when Finality is Simple Office Applications and answers are Images and 2 to 4 hours a day - used to find define ideal storage and RAM"
	
    when
	    $hypothesis : Hypothesis( finality == Finality.APLICACOES_OFFICE_BASICO )
        evalQuestion(askQuestion(CHOOSE_FILE_TYPE, "2"))
        evalQuestion(askQuestion(CHOOSE_DURATION_PER_DAY, "1"))
	then
	    $hypothesis.minStorage.isSSD = true;
        $hypothesis.minStorage.capacity = 240;
        $hypothesis.minRAMPreferred =8;
        $hypothesis.minRAMSpeed=2600;
        $hypothesis.adequateMinCPUBenchmark=120;
        $hypothesis.durationDayChoice=1;
        update($hypothesis);
end

rule "r5c4 - File Type and Daily use question when Finality is Simple Office Applications and answers are Images and more than 4 hours a day - used to find define ideal storage and RAM"
	
    when
	    $hypothesis : Hypothesis( finality == Finality.APLICACOES_OFFICE_BASICO )
        evalQuestion(askQuestion(CHOOSE_FILE_TYPE, "2"))
        evalQuestion(askQuestion(CHOOSE_DURATION_PER_DAY, "2"))
	then
	    $hypothesis.minStorage.isSSD = true;
        $hypothesis.minStorage.capacity = 480;
        $hypothesis.minRAMPreferred =8;
        $hypothesis.minRAMSpeed=2600;
        $hypothesis.adequateMinCPUBenchmark=120;
        $hypothesis.durationDayChoice=2;
        update($hypothesis);
end

% File Type and Daily use question when Finality is Professional Office Applications and answers are Word/excel/PowerPoint and 2 to 4 hours a day - used to find define ideal storage and RAM
rule "r5d1"
	when
	[finality("APLICACOES_OFFICE_PROFISSIONAL") and evalQuestion(askQuestion(CHOOSE_FILE_TYPE, "1")) and evalQuestion(askQuestion(CHOOSE_DURATION_PER_DAY, "1"))]
	then
        [cria_facto(minStorage_isSSD(true)) and cria_facto(minStorage_capacity(240)) and cria_facto(minStorage_minRAMPreferred(16)) and cria_facto(minStorage_minRAMSpeed(2600)) and cria_facto(adequateMinCPUBenchmark(140)) and cria_facto(durationDayChoice(1))].

% File Type and Daily use question when Finality is Professional Office Applications and answers are Word/excel/PowerPoint and more than 4 hours a day - used to find define ideal storage and RAM
rule "r5d2"
	when
	[finality("APLICACOES_OFFICE_PROFISSIONAL") and evalQuestion(askQuestion(CHOOSE_FILE_TYPE, "1")) and evalQuestion(askQuestion(CHOOSE_DURATION_PER_DAY, "2"))]
	then
        [cria_facto(minStorage_isSSD(true)) and cria_facto(minStorage_capacity(240)) and cria_facto(minStorage_minRAMPreferred(16)) and cria_facto(minStorage_minRAMSpeed(3600)) and cria_facto(adequateMinCPUBenchmark(140)) and cria_facto(durationDayChoice(2))].

% File Type and Daily use question when Finality is Professional Office Applications and answers are Images and 2 to 4 hours a day - used to find define ideal storage and RAM
rule "r5d3"
	when
	[finality("APLICACOES_OFFICE_PROFISSIONAL") and evalQuestion(askQuestion(CHOOSE_FILE_TYPE, "2")) and evalQuestion(askQuestion(CHOOSE_DURATION_PER_DAY, "1"))]
	then
        [cria_facto(minStorage_isSSD(true)) and cria_facto(minStorage_capacity(240)) and cria_facto(minStorage_minRAMPreferred(16)) and cria_facto(minStorage_minRAMSpeed(2600)) and cria_facto(adequateMinCPUBenchmark(140)) and cria_facto(durationDayChoice(1))].

% File Type and Daily use question when Finality is Professional Office Applications and answers are Word/excel/PowerPoint and more than 4 hours a day - used to find define ideal storage and RAM
rule "r5d4"
        when
	[finality("APLICACOES_OFFICE_PROFISSIONAL") and evalQuestion(askQuestion(CHOOSE_FILE_TYPE, "2")) and evalQuestion(askQuestion(CHOOSE_DURATION_PER_DAY, "2"))]
	then
        [cria_facto(minStorage_isSSD(true)) and cria_facto(minStorage_capacity(480)) and cria_facto(minStorage_minRAMPreferred(16)) and cria_facto(minStorage_minRAMSpeed(3600)) and cria_facto(adequateMinCPUBenchmark(140)) and cria_facto(durationDayChoice(2))].

% When Finality is Professional Applications without DB - used to find define ideal storage and RAM
rule "r5e1"
	when
	[finality("APLICACOES_PROFISSIONAIS_SEM_BASE_DADOS")]
	then
        [cria_facto(minStorage_isSSD(true)) and cria_facto(minStorage_capacity(480)) and cria_facto(minStorage_minRAMPreferred(16)) and cria_facto(minStorage_minRAMSpeed(3600)) and cria_facto(adequateMinCPUBenchmark(140))].

% When Finality is Professional Applications with Local DB - used to find define ideal storage and RAM
rule "r5e21"
	when
	[finality("APLICACOES_PROFISSIONAIS_COM_BASE_DADOS_LOCAIS")]
	then
        [cria_facto(minStorage_isSSD(true)) and cria_facto(minStorage_capacity(480)) and cria_facto(minStorage_minRAMPreferred(32)) and cria_facto(minStorage_minRAMSpeed(2600)) and cria_facto(adequateMinCPUBenchmark(140))].

% When Finality is Professional Applications with Remote DB - used to find define ideal storage and RAM
rule "r5e22"
	when
	[finality("APLICACOES_PROFISSIONAIS_COM_BASE_DADOS_REMOTAS")]
	then
        [cria_facto(minStorage_isSSD(true)) and cria_facto(minStorage_capacity(240)) and cria_facto(minStorage_minRAMPreferred(16)) and cria_facto(minStorage_minRAMSpeed(3600)) and cria_facto(adequateMinCPUBenchmark(140))].

% When Finality is Basic Games - used to find define ideal storage and RAM
rule "r5d1"
        when
	[finality("JOGOS_BASICOS")]
	then
        [cria_facto(minStorage_isSSD(true)) and cria_facto(minStorage_capacity(240)) and cria_facto(minStorage_minRAMPreferred(16)) and cria_facto(minStorage_minRAMSpeed(3600)) and cria_facto(adequateMinCPUBenchmark(140)) and cria_facto(adequateMinGPUBenchmark(100))].

% When Finality is Advanced Programs of 3D Modelation - used to find define ideal storage and RAM
rule "r5e1"
	when
	[finality("PROGRAMAS_AVANCADOS_MODELACAO_3D")]
	then
        [cria_facto(minStorage_isSSD(true)) and cria_facto(minStorage_capacity(240)) and cria_facto(minStorage_minRAMPreferred(32)) and cria_facto(minStorage_minRAMSpeed(2600)) and cria_facto(adequateMinCPUBenchmark(170))].

% When Finality is Advanced Programs of Graphic Design - used to find define ideal storage, RAM and CPU
rule "r5e2"
	when
	[finality("PROGRAMAS_AVANCADOS_DESIGN_GRAFICO")]
	then
        [cria_facto(minStorage_isSSD(true)) and cria_facto(minStorage_capacity(240)) and cria_facto(minStorage_minRAMPreferred(32)) and cria_facto(minStorage_minRAMSpeed(2600)) and cria_facto(adequateMinCPUBenchmark(170))].

% When Finality is Advanced Programs of Mathematic Calculus - used to find define ideal storage, RAM and CPU
rule "r5e3"
        when
	[finality("PROGRAMAS_AVANCADOS_CALCULO_MATEMATICO")]
	then
        [cria_facto(minStorage_isSSD(true)) and cria_facto(minStorage_capacity(240)) and cria_facto(minStorage_minRAMPreferred(32)) and cria_facto(minStorage_minRAMSpeed(2600)) and cria_facto(adequateMinCPUBenchmark(170))].

% When Finality is Basic Image Treatment - used to find define ideal storage, RAM and CPU
rule "r5f1"
	when
	[finality("TRATAMENTO_DE_IMAGEM_BASICO")]
	then
        [cria_facto(minStorage_isSSD(true)) and cria_facto(minStorage_capacity(240)) and cria_facto(minScndStorage_capacity(1000)) and cria_facto(minStorage_minRAMPreferred(32)) and cria_facto(minStorage_minRAMSpeed(2600)) and cria_facto(adequateMinCPUBenchmark(170))].

% When Finality is Professional Image Treatment - used to find define ideal storage, RAM and CPU
rule "r5f2"
	when
	[finality("TRATAMENTO_DE_IMAGEM_PROFISSIONAL")]
	then
        [cria_facto(minStorage_isSSD(true)) and cria_facto(minStorage_capacity(240)) and cria_facto(minScndStorage_capacity(2000)) and cria_facto(minStorage_minRAMPreferred(32)) and cria_facto(minStorage_minRAMSpeed(3200)) and cria_facto(adequateMinCPUBenchmark(180))].

% When Finality is 4K/8K Video Editing - used to find define ideal storage, RAM and CPU
rule "r5g1"
	when
	[finality("EDICAO_DE_VIDEO_4k_8K")]
	then
        [cria_facto(minStorage_isSSD(true)) and cria_facto(minStorage_capacity(240)) and cria_facto(minScndStorage_capacity(1000)) and cria_facto(minStorage_minRAMPreferred(32)) and cria_facto(minStorage_minRAMSpeed(2600)) and cria_facto(adequateMinCPUBenchmark(170)) and cria_facto(adequateMinGPUBenchmark(100))].

% When Finality is Gaming and Budget Type is Medium Low  - used to find define ideal storage, RAM, CPU and GPU
rule "r5h11"
	when
	[finality("GAMING") and budgetType("MEDIO_BAIXO")]
	then
        [cria_facto(minStorage_isSSD(true)) and cria_facto(minStorage_capacity(240)) and cria_facto(minStorage_minRAMPreferred(32)) and cria_facto(minStorage_minRAMSpeed(2600)) and cria_facto(adequateMinCPUBenchmark(170)) and cria_facto(adequateMinGPUBenchmark(120))].

% When Finality is Gaming and Budget Type is Medium - used to find define ideal storage, RAM, CPU and GPU
rule "r5h12"
	when
	[finality("GAMING") and budgetType("MEDIO")]
	then
        [cria_facto(minStorage_isSSD(true)) and cria_facto(minStorage_capacity(240)) and cria_facto(minStorage_minRAMPreferred(32)) and cria_facto(minStorage_minRAMSpeed(2600)) and cria_facto(adequateMinCPUBenchmark(170)) and cria_facto(adequateMinGPUBenchmark(120))].

% When Finality is Gaming and Budget Type is Medium High - used to find define ideal storage, RAM, CPU and GPU
rule "r5h13"
	when
	[finality("GAMING") and budgetType("MEDIO_ALTO")]
	then
        [cria_facto(minStorage_isSSD(true)) and cria_facto(minStorage_capacity(240)) and cria_facto(minStorage_minRAMPreferred(32)) and cria_facto(minStorage_minRAMSpeed(2600)) and cria_facto(adequateMinCPUBenchmark(170)) and cria_facto(adequateMinGPUBenchmark(170))].

% When Finality is Gaming and Budget Type is High - used to find define ideal storage, RAM, CPU and GPU
rule "r5h14"
        when
	[finality("GAMING") and budgetType("ALTO")]
	then
        [cria_facto(minStorage_isSSD(true)) and cria_facto(minStorage_capacity(240)) and cria_facto(minStorage_minRAMPreferred(32)) and cria_facto(minStorage_minRAMSpeed(2600)) and cria_facto(adequateMinCPUBenchmark(170)) and cria_facto(adequateMinGPUBenchmark(200))].

% System doesn't need a Dedicated GPU but user prefers having one
rule "r7bf1a"
        when
        [evalQuestion(askQuestion(choose_if_wants_dedicated_graphics_card, "1"))]
        then
        [cria_facto(prefersDedicatedGPU(true))].

% System doesn't need a Dedicated GPU and user doesn't prefer one either
rule "r7bf1b"
        when
        [evalQuestion(askQuestion(choose_if_wants_dedicated_graphics_card, "2"))]
        then
        [cria_facto(prefersDedicatedGPU(false))].

% User needs dedicated GPU has manufacturer preference, answer INTEL
rule "r8a1a"
        when
        [needsDedicatedGPU(true) and evalQuestion(askQuestion(choose_gpu_manufacturer, "1"))]
        then
        [cria_facto(gpuManufacturerPreferred("NVIDIA"))].

% User prefers dedicated GPU and has manufacturer preference, answer INTEL
rule "r8a1b"
        when
        [prefersDedicatedGPU(true) and evalQuestion(askQuestion(choose_gpu_manufacturer, "1"))]
        then
        [cria_facto(gpuManufacturerPreferred("NVIDIA"))].

% User needs dedicated GPU and has manufacturer preference, answer AMD
rule "r8a2a"
        when
        [needsDedicatedGPU(true) and evalQuestion(askQuestion(choose_gpu_manufacturer, "2"))]
        then
        [cria_facto(gpuManufacturerPreferred("AMD"))].

% User prefers dedicated GPU and has manufacturer preference, answer AMD
rule "r8a2b"
        when
        [prefersDedicatedGPU(true) and evalQuestion(askQuestion(choose_gpu_manufacturer, "2"))]
        then
        [cria_facto(gpuManufacturerPreferred("AMD"))].

% Finality is Gaming, user has GPU brand preference
% ANALISAR MELHOR!!!
rule "r8b1"
        when
        [finality("GAMING") and evalQuestion(askQuestion(choose_if_wants_preferred_gpu_brand, "1")) and evalQuestion(askQuestion(choose_preferred_gpu_brand, Preferred_GPU_Brand))]
        then
        [cria_facto(preferredGPUBrand(Preferred_GPU_Brand))].

% User has CPU manufacturer preference, INTEL
rule "r9a1"
        when
        [evalQuestion(askQuestion(choose_cpu_manufacturer, "1"))]
        then
        [cria_facto(cpuManufacturerPreferred("INTEL"))].

% User has CPU manufacturer preference, AMD
rule "r9a2"
        when
        [evalQuestion(askQuestion(choose_cpu_manufacturer, "2"))]
        then
        [cria_facto(cpuManufacturerPreferred("AMD"))].

% Finality is Gaming, user wants to do OC and prefers WaterCooling
rule "r10a1"
        when
        [finality("GAMING") and evalQuestion(askQuestion(choose_cpu_oc, "1")) and evalQuestion(askQuestion(choose_cooler_type, "1"))]
        then
        [cria_facto(needsCPUCooler(true)) and cria_facto(cpuCooler_isWaterCooled(true))].

% Finality is Gaming, user wants to do OC and prefers Fanless
rule "r10a2"
        when
        [finality("GAMING") and evalQuestion(askQuestion(choose_cpu_oc, "1")) and evalQuestion(askQuestion(choose_cooler_type, "2"))]
        then
        [cria_facto(needsCPUCooler(true)) and cria_facto(cpuCooler_isFanless(true))].

% Finality is Gaming, user wants to do OC and prefers Normal
rule "r10a3a"
        when
        [finality("GAMING") and evalQuestion(askQuestion(choose_cpu_oc, "1")) and evalQuestion(askQuestion(choose_cooler_type, "3"))]
        then
        [cria_facto(needsCPUCooler(true))].

% Finality is Gaming, user wants to do OC and doesn't have preference
rule "r10a3b"
        when
        [finality("GAMING") and evalQuestion(askQuestion(choose_cpu_oc, "1")) and evalQuestion(askQuestion(choose_cooler_type, "4"))]
        then
        [cria_facto(needsCPUCooler(true))].

% Choose Environment Energy Efficiency Instability, answer Negative
rule "r11a1"
        when
        [evalQuestion(askQuestion(choose_environment_energy_efficiency_instability, "1"))]
	then
        [cria_facto(minEnergyEfficiencyNeeded("_80PLUS"))].

% Choose Environment Energy Efficiency, answer Positive, a bit problematic
rule "r11a2"
        when
        [evalQuestion(askQuestion(choose_environment_energy_efficiency_instability, "2"))]
	then
        [cria_facto(minEnergyEfficiencyNeeded("_80PLUS_GOLD"))].

% Choose Environment Energy Efficiency, answer Positive, serious issue
rule "r11a3"
        when
        [evalQuestion(askQuestion(choose_environment_energy_efficiency_instability, "3"))]
	then
        [cria_facto(minEnergyEfficiencyNeeded("_80PLUS_PLATINUM"))].

% Choose Case Color, answer Black
rule "r12a1"
        when
        [evalQuestion(askQuestion(choose_optional_case_color, "1")) and evalQuestion(askQuestion(choose_case_color, "1"))]
	then
        [cria_facto(caseSizePreferred("black"))].

% Choose Case Color, answer White
rule "r12a2"
        when
        [evalQuestion(askQuestion(choose_optional_case_color, "1")) and evalQuestion(askQuestion(choose_case_color, "2"))]
	then
        [cria_facto(caseSizePreferred("white"))].

% Choose Case Size, answer Micro-ATX
rule "r12a1"
        when
        [evalQuestion(askQuestion(choose_case_size, "1"))]
	then
        [cria_facto(caseSizePreferred("MID_TOWER"))].

% Choose Case Size, answer Mini-ATX
rule "r12a2"
        when
        [evalQuestion(askQuestion(choose_case_size, "2"))]
	then
        [cria_facto(caseSizePreferred("MICRO_ATX_TOWER"))].

% Choose Case Size, answer MID Tower
rule "r12a3"
        when
        [evalQuestion(askQuestion(choose_case_size, "3"))]
	then
        [cria_facto(caseSizePreferred("MINI_ATX_TOWER"))].

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%