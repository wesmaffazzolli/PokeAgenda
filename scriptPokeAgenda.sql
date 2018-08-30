create database pokeagenda;

use pokeagenda;

create table treinador (
	idTreinador INT NOT NULL AUTO_INCREMENT,
	nomeTreinador VARCHAR(255) NOT NULL,
    login VARCHAR(255) NOT NULL,
    senha VARCHAR(255) NOT NULL,
	PRIMARY KEY(idTreinador)
);

insert into treinador values (null, 'Tomás', 'tomjose', 'tom');
insert into treinador values (null, 'Wesley', 'wesmaffazzolli', 'wes');

select * from treinador where login = 'tomjose' and senha = 'tom';

create table pokemon_favorito (
	idTreinador INT NOT NULL,
    idPokemon INT NOT NULL,
    FOREIGN KEY (idTreinador) REFERENCES treinador(idTreinador),
    FOREIGN KEY (idPokemon) REFERENCES pokemon(idPokemon)
);

insert into pokemon_favorito values (1, 5);
insert into pokemon_favorito values (2, 4);

select * from pokemon_favorito;

create table pokemon (
	idPokemon INT NOT NULL AUTO_INCREMENT,
	nomePokemon VARCHAR(255) NOT NULL,
    especie VARCHAR(255) NOT NULL,
    peso DECIMAL(10,2) NOT NULL,
    altura DECIMAL(10,2) NOT NULL,
    idTreinador INT NOT NULL,
    foto TEXT,
    FOREIGN KEY (idTreinador) REFERENCES treinador(idTreinador),
	PRIMARY KEY(idPokemon)
);

insert into pokemon values (null, 'Bulbasauro', 'Pokemon Semente', 6.9, 0.7, 1, null);
insert into pokemon values (null, 'Charmander', 'Pokemon Lagarto', 8.5, 0.6, 1, null);
insert into pokemon values (null, 'Squirtle', 'Pokemon Mini-Tartaruga', 9, 0.5, 1, null);
insert into pokemon values (null, 'Arbok', 'Pokemon Cobra', 65, 3.5, 2, null);
insert into pokemon values (null, 'Pikachu', 'Pokemon Rato', 6, 0.4, 2, null);

select * from pokemon;