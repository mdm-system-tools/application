# Estrutura do Projeto

Este projeto segue a arquitetura MVVM (Model - View - ViewModel), com camadas bem definidas para garantir organização, escalabilidade e facilidade de manutenção.

---

## Camada Model

Responsável por gerenciar os dados da aplicação: buscar, criar, remover e atualizar informações através da API do backend.

- **DTO (Data Transfer Object)**  
  Define as estruturas de dados que serão convertidas de/para JSON. É usada principalmente no `Repository`, para transformar os dados da API em objetos que a aplicação entende (e vice-versa).

- **Repository**  
  Responsável por lidar com o tráfego de dados da aplicação. Nessa camada são implementadas as chamadas à API, o tratamento de respostas e a preparação dos dados para a ViewModel.  
  O padrão atual (que pode mudar no futuro) é criar uma **interface** para o repositório e uma implementação com o mesmo nome seguido do sufixo `Impl`, por exemplo:  
  `UserRepository` → `UserRepositoryImpl`.

---

## Camada View

Responsável pela visualização ou seja, tudo que o usuário vê na tela.  
Essa camada **não deve conter lógica de negócio** ou interações diretas com dados. Ela apenas renderiza o conteúdo com base nas informações fornecidas pela ViewModel.

- **Components**  
  Contém componentes visuais simples e reutilizáveis, como botões, ícones, campos de entrada, etc.  
  Exemplo: um botão com o ícone de "+" que pode ser usado em várias telas.

- **Screens**  
  Aqui ficam as telas completas da aplicação.  
  Cada tela é construída utilizando os `components` e recebe os dados via `viewmodel`.  
  Algumas telas podem ter múltiplas sub-telas ou estados diferentes, e devem ser organizadas conforme necessário.

---

## Camada ViewModel

Faz a ponte entre a `Model` e a `View`.  
É a única camada que pode acessar ambas. Ela:

- Coleta dados da API por meio dos `repositories`
- Trata e prepara os dados para exibição
- Expõe os dados e eventos para a `View` por meio de observáveis (`LiveData`, `StateFlow`, etc.)
- ViewModel **DEVE** usar `interfaces` e não a implementação.

A estrutura desta camada se baseia no nome da tela que o ViewModel será usado.  
Exemplo: para a tela `HomeScreen`, teremos um `HomeViewModel`.

A injeção de dependência é feita via **Hilt (Android)**, garantindo o desacoplamento das classes.

---

### Observações Finais

- Cada camada tem uma responsabilidade bem definida.
- A separação clara entre `view`, `viewmodel` e `model` ajuda a manter o código limpo e testável.
- A estrutura pode evoluir conforme o projeto cresce, mas o ideal é manter esse padrão como base.
