insert into pais(id_pais,nome_pais) values(1,'Pais 1');
insert into pais(id_pais,nome_pais) values(2,'Pais 2');
insert into pista(id_pista,tamanho_pista,pais_id_pais) values(1,3000,1);
insert into pista(id_pista,tamanho_pista,pais_id_pais) values(2,5000,2);
insert into campeonato(descricao_campeonato,ano_campeonato) values('Campeonato 1',2005);
insert into campeonato(descricao_campeonato,ano_campeonato) values('Campeonato 2',2010);
insert into corrida(id_corrida,data_corrida,pista_id_pista,campeonato_id_campeonato) values(1,'2023-09-14T12:34:00',1,1);
insert into corrida(id_corrida,data_corrida,pista_id_pista,campeonato_id_campeonato) values(2,'2023-09-14T12:34:00',2,2);
