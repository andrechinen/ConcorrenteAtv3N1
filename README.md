# Simulação de Hotel em Java

## Descrição
Este projeto simula um sistema de reserva e controle de quartos em um hotel, utilizando threads em Java 17. O sistema representa as seguintes entidades:
- Quarto
- Hóspede
- Camareira
- Recepcionista

## Entidades
### Quarto
- O hotel possui 10 quartos.
- Cada quarto possui capacidade para até 4 hóspedes e uma única chave.
- Quando os hóspedes saem do quarto para passear, devem deixar a chave na recepção.
- Um quarto vago que passa por limpeza não pode ser alocado para um hóspede novo.

### Hóspede
- Cada hóspede é representado por uma thread.
- Existem no mínimo 50 hóspedes.
- Caso um grupo ou uma família possua mais do que 4 membros, eles devem ser divididos em vários quartos.
- Caso a pessoa tente duas vezes alugar um quarto e não consiga, ela deixa uma reclamação e vai embora.

### Camareira
- Cada camareira é representada por uma thread.
- Existem no mínimo 10 camareiras.
- Uma camareira só pode entrar em um quarto caso ele esteja vago ou os hóspedes não estejam nele.

### Recepcionista
- Cada recepcionista é representado por uma thread.
- Existem no mínimo 5 recepcionistas.
- Os recepcionistas devem alocar hóspedes apenas em quartos vagos.

## Regras
- O hotel deve contar com vários recepcionistas, que trabalham juntos e que devem alocar hóspedes apenas em quartos vagos.
- O hotel deve contar com várias camareiras.
- A limpeza dos quartos é feita sempre após a passagem dos hóspedes pelo quarto. Os hóspedes só podem retornar após o fim da limpeza.
- Caso uma pessoa chegue e não tenha quartos vagos, ela passeia pela cidade e retorna após um tempo para tentar alugar um quarto novamente.
- Não há a possibilidade de, para um mesmo quarto, somente parte dos hóspedes saírem para passear. Ou saem todos ou nenhum.

## Observações
- A implementação deve ser abrangente e simular várias situações: número diferentes de hóspedes chegando, grupos com mais de 4 pessoas, todos os quartos lotados, etc.
- Deve haver sincronia e coordenação entre as entidades.

