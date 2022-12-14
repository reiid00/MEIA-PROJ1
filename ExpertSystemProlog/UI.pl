%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% Questões/Respostas

% answer(QuestionID, Answer)
:-dynamic answer/2.

% question(QuestionID, Question, ListValidAnswers)
question(choose_budget, 'Quanto pretende gastar no computador?', _).
question(choose_finality_low_budget, 'Qual é a sua finalidade? \n 1. Navegação WEB \n 2. Aplicações Office \n 3. Aplicações Profissionais \n', [1, 2, 3]).
question(choose_finality_non_low_budget, 'Qual é a sua finalidade? \n 1. Navegação WEB \n 2. Aplicações Office \n 3. Aplicações Profissionais \n 4. Jogos Básicos \n 5. Programas Avançados \n 6. Gaming \n 7. Tratamento de Imagem \n 8. Edição de Vídeo 4K a 8K \n', [1, 2, 3, 4, 5, 6, 7, 8]).
question(choose_finality_navegacao_web, 'Pretende ter uma utilização com muitas ou poucas janelas? \n 1. Poucas janelas. \n 2. Muitas janelas. \n', [1, 2]).
question(choose_finality_aplicacoes_office, 'Pretende utilizar o Office de forma básica ou profissional estas aplicações? \n 1. Forma Básica. \n 2. Profissional. \n', [1, 2]).
question(choose_finality_aplicacoes_profissionais, 'Pretende realizar regularmente operações de pesquisa e modificação à base de dados? \n 1. Vou utilizar Base de Dados. \n 2. Não vou utilizar Base de Dados. \n', [1, 2]).
question(choose_finality_type_of_database, 'Que tipo de Base de Dados irá utilizar? \n 1. Base de Dados Local \n 2. Base de Dados Remotas \n 3. Ambas \n', [1, 2, 3]).
question(choose_finality_programas_avancados, 'Que tipo de programas pretende utilizar? \n 1. Modelação 3D. \n 2. Design Gráfico. \n 3. Calculo Matemático. \n', [1, 2, 3]).
question(choose_file_type, 'Que tipo de ficheiros irão ser mais utilizados? \n 1. Word/excel/PowerPoint \n 2. Imagens \n', [1, 2]).
question(choose_duration_per_day, 'Quanto tempo, em média, irá utilizar o computador por dia? \n 1. 2 a 4 horas \n 2. Mais de 4 horas \n', [1, 2]).
question(choose_optional_case_color, 'Tem preferência na cor da caixa? \n 1. Sim \n 2. Não \n', [1, 2]).
question(choose_case_color, 'Qual das cores prefere? \n 1. Preto \n 2. Branco \n ', [1, 2]).
question(choose_case_size, 'Pretende que tamanho de caixa?\n 1. Normal \n 2. Pequena \n 3. Muito pequena \n', [1, 2, 3]).
question(choose_environment_energy_efficiency_instability, 'Considera o ambiente (casa/trabalho) onde pretende utilizar o PC como sendo instável/ineficiente energeticamente? \n 1. Não \n 2. Sim, um pouco problemático \n 3. Sim, é um problema muito grave',[1, 2, 3]).
question(choose_gpu_manufacturer, 'Que fabricante de placas gráficas prefere? \n 1. Nvidia \n 2. AMD \n 3. Indiferente \n',[1, 2, 3]).
question(choose_if_wants_dedicated_graphics_card, 'Pretende realizar trabalhos mais pesados a nível gráfico (jogos, sistemas 3D)? \n 1. Sim \n 2. Não \n',[1, 2]).
question(choose_if_wants_preferred_gpu_brand, 'Tem preferência na marca da placa gráfica? \n 1. Sim, pretendo ASUS \n 2. Sim, pretendo EVGA \n 3. Sim, pretendo Gigabyte \n 4. Sim, pretendo MSI \n 5. Sim, pretendo Sapphire \n 6. Nao \n',[1, 2, 3, 4, 5, 6]).
question(choose_image_treatment_type, 'Pretende utilizar estas aplicações de forma básica ou profissional? \n 1. Forma Básica. \n 2. Profissional. \n',[1, 2]).
question(choose_initial_storage, 'Pretende modificar o tamanho de armazenamento ?(SSD) Atualmente o Sistema sugeriu o tamanho ideal de XXX GB \n 1. Sim, aumentar para o dobro da capacidade \n 2. Nao \n',[1, 2]).
question(choose_2nd_storage, 'Pretende adicionar/modificar um segundo local de armazenamento? (HDD) \n 1. Sim, pretendo adicionar um disco rígido de 1TB \n 2. Sim, pretendo adicionar um disco rígido de 2TB  \n 3. Sim, pretendo adicionar um disco rígido de 4TB \n 4. Nao \n',[1, 2, 3, 4]).
question(choose_ram, 'Pretende modificar o tamanho da RAM?  Atualmente o Sistema sugeriu o tamanho ideal de XXX GB \n 1. Sim, pretendo aumento para o dobro da capacidade \n 2. Nao \n',[1, 2]).
question(choose_cpu_manufacturer, 'Que fabricante de processador prefere? \n 1. Intel \n 2. AMD \n 3. Indiferente \n',[1, 2, 3]).
question(choose_cpu_oc, 'Pretende fazer OverClock ao CPU? \n 1. Sim \n 2. Nao \n',[1, 2]).
question(choose_cooler_type, 'Que tipo de CPU Cooler pretende? \n 1. Water Cooling \n 2. Fanless \n 3. Normal \n 4. Indiferente \n',[1, 2, 3, 4]).

askQuestion(QuestionID, Value) :- 
    answer(QuestionID, Answer), !,
    Answer = Value.
askQuestion(QuestionID, Value) :- 
    question(QuestionID, Question, ListValidAnswers),
    write(Question), nl,
    read(Answer),
    (member(Answer, ListValidAnswers), !
    ; write('Resposta inválida, por favor tente novamente.'), nl, askQuestion(QuestionID, Value)),
    assert(answer(QuestionID, Answer)),
    Answer = Value.

askBudgetQuestion(MinValue, MaxValue) :- 
    answer(choose_budget, Answer),!,
    Answer > MinValue,
    Answer =< MaxValue.
askBudgetQuestion(MinValue, MaxValue) :- 
    question(choose_budget, Question, _),
    write(Question), nl,
    read(Answer),
    assert(answer(choose_budget, Answer)),
    Answer > MinValue,
    Answer =< MaxValue.

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
