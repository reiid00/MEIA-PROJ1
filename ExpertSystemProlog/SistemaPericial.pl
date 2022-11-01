%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% Carregar dependências

:- consult('BC.pl').
:- consult('UI.pl').
:- consult('Regras.pl').

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% Utils

:-dynamic justifica/3.
% justifica(FactID, RuleID, LHSFactos)

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% Sistema Pericial

arranca_motor():-	
	loadBC(),
	setof((ID, LHS, RHS), rule ID when LHS then RHS, LRegras),
	length(LRegras, N), write(N), write('  <--> Total de Regras'),nl,
	dispara_regras(LRegras).

dispara_regras([(ID, LHS, RHS)|LRegras]):-
	verifica_condicoes(LHS, LHSFactos),
	concluir(RHS, ID, LHSFactos),
	!,
	dispara_regras(LRegras).
dispara_regras([_|LRegras]):- dispara_regras(LRegras).
dispara_regras([]).

verifica_condicoes([not evalQuestion(X) and Y], [not X|LF]):- 
    !,
	\+ evalQuestion(X),
	verifica_condicoes([Y],LF).
verifica_condicoes([evalQuestion(X) and Y], [X|LF]):- 
    !,
	evalQuestion(X),
	verifica_condicoes([Y],LF).
verifica_condicoes([not X and Y], [not X|LF]):- 
    !,
	\+ fact(_, X),
	verifica_condicoes([Y], LF).
verifica_condicoes([X and Y], [N|LF]):- 
    !,
	fact(N, X),
	verifica_condicoes([Y], LF).
verifica_condicoes([not evalQuestion(X)],[not X]):- !, \+ evalQuestion(X).
verifica_condicoes([evalQuestion(X)],[X]):- !, evalQuestion(X).
verifica_condicoes([not X], [not X]):- !, \+ fact(_, X).
verifica_condicoes([X], [N]):- fact(N, X).

evalQuestion(AskQuestion):- AskQuestion.

concluir([cria_facto(F)|Y], ID, LHSFactos):-
	!,
	cria_facto(F, ID, LHSFactos),
	concluir(Y, ID, LHSFactos).
concluir([], _, _):-!.

cria_facto(F, _ , _):-
	fact(_, F), !.
cria_facto(F, RuleID, LHSFactos):-
    (F =.. [Functor, _], abolish(Functor/1); true),
    getNextFactID(FactID),
	assert(justifica(FactID, RuleID, LHSFactos)),
	assert(fact(FactID, F)),
	write('Foi concluído o facto nº '),write(FactID),write(' -> '),write(F),nl,!.

% Teste!!!
% verifica_condicoes([not budgetType(NA)and not budgetType(BAIXO)and evalQuestion(askQuestion(choose_finality_non_low_budget,2))and evalQuestion(askQuestion(choose_finality_aplicacoes_office,1))],L). 