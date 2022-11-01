%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% Carregar dependências

:- consult('BC.pl').
:- consult('UI.pl').
:- consult('Regras.pl').
:- consult('Solucao.pl').

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


%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% Visualização da base de factos

show_all_facts:-
	findall(N, fact(N, _), LFacts),
	print_facts(LFacts).

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% Explanation Module <----> How

how(FactID):-
	justifica(FactID, RuleID, LHSFacts),
	fact(FactID, Fact), !,
	write('Concluído o facto nº '), write(FactID), write(' -> '), write(Fact), nl,
	write('pela regra '), write(RuleID), nl,
	write('por se ter verificado que:'), nl,
	print_facts(LHSFacts),
	write('********************************************************'), nl,
	explain(LHSFacts).
how(_):- write('Esse facto não foi concluído.'), nl.

print_facts([FactID|LFacts]):-
	fact(FactID, Fact), !,
	write('O facto nº '), write(FactID), write(' -> '), write(Fact), write(' é verdadeiro'), nl,
	print_facts(LFacts).
print_facts([Condition|LFacts]):-
	write('A condição '), write(Condition), write(' é verdadeira'), nl,
	print_facts(LFacts).
print_facts([]).

explain([Condition|LFacts]):- 
	\+ integer(Condition), !,
	explain(LFacts).
explain([FactID|LFacts]):-
	how(FactID),
	explain(LFacts).
explain([]):- write('********************************************************'), nl.

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% Explanation Module <----> Why not
% Example: ?- why_not(needsDedicatedGPU(true)).

why_not(Fact):- why_not(Fact, 1).
why_not(Fact,_):-
	fact(_, Fact),
	!,
	write('O facto '), write(Fact), write(' não é falso!'), nl.
why_not(Fact, Level):-
	find_rules_why_not(Fact, LLPF),
	why_not1(LLPF, Level), !.
why_not(not Fact, Level):- format(Level), write('Porque:'), write(' O facto '), write(Fact), write(' é verdadeiro'), nl.
why_not(Fact, Level):- format(Level), write('Porque:'), write(' O facto '), write(Fact), write(' não está definido na base de conhecimento'), nl.

%  As explicações do why_not(Fact) devem considerar todas as regras que poderiam dar origem à conclusão relativa ao facto, Fact
find_rules_why_not(Fact, LLPF):-
	findall((RuleID, LPF),
		(
		rule RuleID priorityGroup _ when LHS then RHS,
		member(insert_fact(Fact), RHS),
		find_false_assumptions(LHS, LPF),
		LPF \== []
		),
		LLPF).

why_not1([], _).
why_not1([(ID,LPF)|LLPF], Level):-
	format(Level), write('Porque pela regra '), write(ID), write(':'), nl,
	Level1 is Level + 1,
	explain_why_not(LPF, Level1),
	why_not1(LLPF, Level).

find_false_assumptions([not evalQuestion(X) and Y], LPF):- verify_question_answer(not evalQuestion(X)), !, find_false_assumptions([Y], LPF).
find_false_assumptions([not evalQuestion(X) and Y], [not X|LPF]):- !, find_false_assumptions([Y], LPF).
find_false_assumptions([evalQuestion(X) and Y], LPF):- verify_question_answer(evalQuestion(X)), !, find_false_assumptions([Y], LPF).
find_false_assumptions([evalQuestion(X) and Y],[X|LPF]):- !, find_false_assumptions([Y], LPF).
find_false_assumptions([not X and Y], LPF):- verify_conditions([not X], _), !, find_false_assumptions([Y], LPF).
find_false_assumptions([not X and Y], [not X|LPF]):- !, find_false_assumptions([Y], LPF).
find_false_assumptions([X and Y], LPF):- verify_conditions([X], _), !, find_false_assumptions([Y], LPF).
find_false_assumptions([X and Y], [X|LPF]):- !, find_false_assumptions([Y], LPF).
find_false_assumptions([not evalQuestion(X)], []):- verify_question_answer(not evalQuestion(X)), !.
find_false_assumptions([not evalQuestion(X)], [not X]):- !.
find_false_assumptions([evalQuestion(X)], []):- verify_question_answer(evalQuestion(X)), !.
find_false_assumptions([evalQuestion(X)], [X]):- !.
find_false_assumptions([not X], []):- verify_conditions([not X], _), !.
find_false_assumptions([not X], [not X]):-!.
find_false_assumptions([X], []):- verify_conditions([X], _), !.
find_false_assumptions([X], [X]).
find_false_assumptions([]).

% Valida se a questão ainda não foi efetuada ou se já foi e a resposta é diferente da esperada.
% O predicado verify_conditions não foi reutilizado para validar as respostas de questões, dado que o mesmo foi implementado de forma a questionar o utilizador
% quando a questão a ser validada ainda não foi efetuada, validando a respetiva resposta. 
% Assim, neste novo predicado de validação de questões como justificação no why not, se a questão ainda não foi efetuada, devolve-se logo true/verdadeiro, 
% dado que ainda não é possível aferir se a resposta é inválida e, consequentemente, não pode ser utilizado como justificação no why not.
verify_question_answer(not X):- !, \+ verify_question_answer(X).
verify_question_answer(evalQuestion(askQuestion(QuestionID, Value))):- 
    answer(QuestionID, Answer), !,
    Answer = Value.
verify_question_answer(evalQuestion(askQuestion(_, _))).
verify_question_answer(evalQuestion(askBudgetQuestion(MinValue, MaxValue))):- 
    answer(choose_budget, Answer), !,
    Answer > MinValue,
    Answer =< MaxValue.
verify_question_answer(evalQuestion(askBudgetQuestion(_, _))).

explain_why_not([],_).
explain_why_not([not evalQuestion(X)|LPF], Level):-
	!,
	format(Level), write('A condição not '), write(X), write(' é falsa'), nl,
	explain_why_not(LPF, Level).
explain_why_not([evalQuestion(X)|LPF], Level):-
	!,
	format(Level), write('A condição '), write(X), write(' é falsa'), nl,
	explain_why_not(LPF, Level).
explain_why_not([P|LPF], Level):-
	format(Level), write('A premissa '), write(P), write(' é falsa'), nl,
	Level1 is Level + 1,
	why_not(P, Level1),
	explain_why_not(LPF, Level).

format(Level):-
	Esp is (Level - 1) * 5, tab(Esp).