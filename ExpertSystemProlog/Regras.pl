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

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%