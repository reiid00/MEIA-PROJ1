%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% Carregar dependências

:- consult('BC.pl').
:- consult('UI.pl').
:- consult('Regras.pl').

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% Utils

:-dynamic justifica/3.
% justifica(FactID, RuleID, LHSFacts)

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% Sistema Pericial

start_engine():-	
	loadBC(),
	getListOfUniqueRulesPriorityGroups(LRPGs),
	fire_all_rules(LRPGs).

fire_all_rules([RPG|LRPGs]):- 
	setof((ID, LHS, RHS), rule ID priorityGroup RPG when LHS then RHS, LRules),
	length(LRules, N), write(N), write('  <--> Total de Regras do grupo '), write(RPG), nl,
	fire_rules(LRules), fire_all_rules(LRPGs).
fire_all_rules([]).

fire_rules([(ID, LHS, RHS)|LRules]):-
	verify_conditions(LHS, LHSFacts),
	conclude(RHS, ID, LHSFacts),
	!,
	fire_rules(LRules).
fire_rules([_|LRules]):- fire_rules(LRules).
fire_rules([]).

verify_conditions([not evalQuestion(X) and Y], [not X|LF]):- 
    !,
	\+ evalQuestion(X),
	verify_conditions([Y],LF).
verify_conditions([evalQuestion(X) and Y], [X|LF]):- 
    !,
	evalQuestion(X),
	verify_conditions([Y],LF).
verify_conditions([not X and Y], [not X|LF]):- 
    !,
	\+ fact(_, X),
	verify_conditions([Y], LF).
verify_conditions([X and Y], [N|LF]):- 
    !,
	fact(N, X),
	verify_conditions([Y], LF).
verify_conditions([not evalQuestion(X)],[not X]):- !, \+ evalQuestion(X).
verify_conditions([evalQuestion(X)],[X]):- !, evalQuestion(X).
verify_conditions([not X], [not X]):- !, \+ fact(_, X).
verify_conditions([X], [N]):- fact(N, X).

evalQuestion(AskQuestion):- AskQuestion.

conclude([insert_fact(F)|Y], ID, LHSFacts):-
	!,
	insert_fact(F, ID, LHSFacts),
	conclude(Y, ID, LHSFacts).
conclude([], _, _):-!.

insert_fact(F, _ , _):-
	fact(_, F), !.
insert_fact(F, RuleID, LHSFacts):-
    (F =.. [Functor, _], abolish(Functor/1); true),
    getNextFactID(FactID),
	assert(justifica(FactID, RuleID, LHSFacts)),
	assert(fact(FactID, F)),
	write('Foi concluído o Facto nº '), write(FactID), write(' -> '), write(F), nl,!.

% Teste!!!
% verify_conditions([not budgetType(NA)and not budgetType(BAIXO)and evalQuestion(askQuestion(choose_finality_non_low_budget,2))and evalQuestion(askQuestion(choose_finality_aplicacoes_office,1))],L). 