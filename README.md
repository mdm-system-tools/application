
## ✅ Telas Principais

- [ ] Cadastro de Pessoas / Agenda  
- [ ] Perfil das Pessoas  
- [ ] Marcar Reunião  
- [ ] Filtro  
- [ ] Pesquisa por Nome / Chamada  
- [ ] Exportação de dados `.xlsx`  
- [ ] Banco em Cache  

---

## 📁 Estrutura do Repositório

### 🖼️ Frontend (Expo / React Native)

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
```

### Chatbot
```
em processo de desenvolvimento...
```

### Rodar aplicação
``` bash
npx expo start
```