# Jogos Olímpicos de Tokio 2020

jogos-olimpicos é uma API Resful destinada a gerir as competições dos Jogos Olímpicos de Tokyo 2020.
A aplicação cotempla o cadastro de competições assim como a consulta das mesmas, aplicando-se filtros ou realizando-se consultas gerais.

### Metodologias e Frameworks utilizados
Nesse projeto foram utilizados os seguintes items:
- Spring Boot: Foi escolhido para essa aplicação devido a facilidade que ele fornece para criar aplicações desse tipo, evitando diversas configurações de ambiente manuais, por exemplo.
- JUnit: JUnit foi o framework escolhido para os testes unitários da aplicação, devido facilidade do uso, bem como maior experiência do desenvolvedor com essa ferramenta.
- SQLite: Banco de Dados em memória escolhido para essa aplicação. Além de atendar as necessidades da mesma, o SQLite é um dos bancos de dados em memória mais performáticos e seguros que existem.
- Maven: o Maven foi a ferramenta escolhida para auxiliar na construção, compilação e build do projeto. Uma das mais utilizadas para esse tipo de atividade.

### Instalação

*Pré-requisito: Ambiente Java instalado e configurado, Maven, SQLite

1. Faça o clone deste projeto com `https://github.com/tzinsly/jogos-olimpicos.git`
2. Entre na pasta do projeto e execute `mvn clean package`
3. Crie um Banco de dados com o SQLite em C:\dev\sqlite\db chamado `jogos.db'`
4. Crie uma tabela com o seguinte descrição:
`create table competicao(
id integer primary key autoincrement,
modalidade text not null,
local text, 
pais1 text,
pais2 text,
etapa text,
dataHoraIni text,
dataHoraFim text);`

5. Rode a aplicação com `mvn spring-boot:run`

### Testando a aplicação
*Testes executados utilizando plugin do Firefox: RESTED

##Incluindo uma Competição:
1. Abrir o RESTED e inserir  a página `http://localhost:9000`

### Screenshot
