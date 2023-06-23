insert into pais(id_pais,nome_pais) values(1,'Pais 1');
insert into pais(id_pais,nome_pais) values(2,'Pais 2');
insert into equipe(id_equipe,nome_equipe) values(1,'Equipe 1');
insert into equipe(id_equipe,nome_equipe) values(2,'Equipe 2');
insert into piloto(id_piloto,nome_piloto,pais_id_pais,equipe_id_equipe) values(1,'Piloto 1',1,1);
insert into piloto(id_piloto,nome_piloto,pais_id_pais,equipe_id_equipe) values(2,'Piloto 2',2,2);
insert into pais(id_pais,nome_pais) values(1,'Pais 1');
insert into pais(id_pais,nome_pais) values(2,'Pais 2');
insert into campeonato(id_campeonato,descricao_campeonato,ano_campeonato) values(1,'Campeonato 1',2005);
insert into campeonato(id_campeonato,descricao_campeonato,ano_campeonato) values(2,'Campeonato 2',2010);
insert into pista(id_pista,tamanho_pista,pais_id_pais) values(1,3000,1);
insert into pista(id_pista,tamanho_pista,pais_id_pais) values(2,5000,2);
insert into corrida(id_corrida,data_corrida,pista_id_pista,campeonato_id_campeonato) values(1,'2023-09-14',1,1);
insert into corrida(id_corrida,data_corrida,pista_id_pista,campeonato_id_campeonato) values(2,'2023-10-14',2,2);

insert into piloto_corrida(id, piloto_id, corrida_id, colocacao) values(1, 1, 1, 'Primeiro Lugar');
insert into piloto_corrida(id, piloto_id, corrida_id, colocacao) values(2, 2, 1, 'Segundo Lugar');


