# Sistema de Gerenciamento de Biblioteca

## 📚 Descrição
Sistema desenvolvido em Java para gerenciamento de empréstimos de livros em uma biblioteca. O projeto permite controlar empréstimos, devoluções e gerenciar o acervo de forma eficiente.

## 🚀 Funcionalidades

### Gerenciamento de Empréstimos
- Cadastro de novos empréstimos
- Atualização de registros existentes
- Remoção de empréstimos
- Listagem de todos os empréstimos
- Registro de devoluções
- Controle automático de datas de empréstimo e devolução

### Controle de Acervo
- Verificação de disponibilidade de livros
- Controle automático de quantidade de livros
- Sistema de status para acompanhamento de empréstimos

## 🛠️ Tecnologias Utilizadas
- Java SDK 22
- Swing para interface gráfica
- JDBC para conexão com banco de dados
- Sistema de persistência em banco de dados relacional

## 📋 Pré-requisitos
- Java SDK 22 ou superior
- Banco de dados compatível com JDBC
- IDE compatível com Java (recomendado: IntelliJ IDEA)

## 🔧 Configuração
1. Clone o repositório
2. Configure as credenciais do banco de dados
3. Execute o script de criação do banco de dados (se fornecido)
4. Compile e execute o projeto

## 🖥️ Interface
O sistema possui uma interface gráfica intuitiva com:
- Campos para entrada de dados
- Botões de ação para operações principais
- Tabela para visualização dos empréstimos
- Popups para operações específicas

## ⚙️ Funcionalidades Principais
1. **Cadastro de Empréstimos**
   - Registro de ID do livro
   - Registro de ID do usuário
   - Data automática de empréstimo
   - Cálculo automático da data de devolução (7 dias)

2. **Gerenciamento de Devoluções**
   - Sistema de registro de devoluções
   - Atualização automática do status
   - Controle de disponibilidade do acervo

3. **Controle de Acervo**
   - Verificação automática de disponibilidade
   - Atualização de quantidade de livros
   - Sistema de status para monitoramento
