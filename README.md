# MDM - Movimento Pelo Direito à Moradia

O Movimento Pelo Direito à Moradia (MDM) é um movimento social de moradia popular que luta por moradia digna, prioritariamente para famílias de baixa renda, moradoras de áreas de risco e irregular, atuando na regularização fundiária, urbanização de favela, e na defesa de moradia popular na região central da cidade. Também defende uma educação de qualidade para todos, saúde, cultura, lazer e transporte público, como forma de melhorar as condições de vida da população.

## 📌 Contexto do Problema

O movimento utiliza um sistema de carteirinhas físicas para registrar a presença em reuniões e controlar os pagamentos mensais. Cada associado possui sua própria carteirinha com um identificador único.

Atualmente, esse processo é feito de forma manual, o que torna a conferência de presença e pagamento lenta e ineficiente, causando atrasos tanto nas reuniões quanto para os próprios associados.

Outro problema recorrente é a falta de controle sobre a validade de documentos, que frequentemente expiram sem aviso prévio, comprometendo o andamento do projeto.

## ✅ Solução Proposta

Desenvolver um sistema completo composto por:

- 📱 **Aplicativo mobile** para registro de presença e pagamentos
- 🤖 **Chatbot via WhatsApp** para envio automático de avisos aos associados
- 🧠 **Backend centralizado** para processamento, validação e sincronização dos dados

A ideia é aumentar a eficiência da administração, agilizar processos e automatizar avisos importantes, como:

- Ausência em reuniões
- Pagamentos em atraso
- Documentos vencendo

## 📌 Critérios de Aceitação

- O aplicativo deve funcionar offline, sincronizando com o servidor quando houver internet
- Interface simples e prática, com foco em usabilidade rápida
- Exportação dos dados para planilhas Excel

## 🛠️ Stack Tecnológica

| Camada | Ferramenta |
|--------|------------|
| Backend | Go (Golang) com banco de dados SQLite. Tabelas e requisições são geradas via sqlc |
| Frontend | React Native com cache local via SQLite |
| Chatbot | Go (Golang) utilizando a biblioteca Whatsmeow |
| Hospedagem | Amazon Web Services (AWS) |

## 📁 Estrutura do Repositório

### Frontend
```
src/app          # O main da aplicação
assets/fonts     # Fontes da aplicação
assets/imagens   # Imagens e recursos visuais
components       # Componentes reutilizáveis
styles           # Estilos e temas
test/            # Testes unitários e de integração
```

### Backend
```
cmd/app           # Ponto de entrada do backend
internal/database # Queries geradas via sqlc
internal/domain   # Entidades (DDD)
internal/handler  # Handlers e controladores HTTP
internal/service  # Lógica e manipulação de dados
test/             # Testes unitários e de integração
schema.sql        # Esquema SQL das tabelas
queries.sql       # Comandos SQL
```

### Chatbot
```
em processo de desenvolvimento...
```

### Rodar aplicação
```npx expo start
```