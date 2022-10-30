
use_module(library(clpfd)).

exactly(List):- length(List, 4), List ins 1..4, exactly(3, List, 2), label(List).

exactly(_, [], 0).
exactly(X, [Y|L], N) :-
B #<==> (X #= Y),
N #= M+B,
exactly(X, L, M).