# 📂 Guia de Organização de Imagens - Projeto MDM

## 🎯 Estrutura de Pastas

### 📁 `/assets/images/`
Para imagens grandes que são carregadas programaticamente.

#### 🌅 `/backgrounds/`
- Wallpapers e fundos de tela
- Imagens de hero/banner
- Texturas e padrões

#### 🎨 `/illustrations/`
- Ilustrações decorativas
- Gráficos explicativos
- Imagens de onboarding

#### 👤 `/photos/`
- Fotos de perfil padrão
- Avatars placeholder
- Imagens de exemplo

### 📁 `/animations/`
- Arquivos Lottie (.json)
- GIFs animados
- Recursos de animação

### 📁 `/res/drawable/`
Para ícones e recursos vetoriais pequenos.

#### Convenção de Nomenclatura:
- `ic_*` - Ícones de interface
- `bg_*` - Backgrounds e gradientes
- `shape_*` - Formas customizadas
- `img_*` - Imagens bitmap pequenas

## 🔧 Como Usar

### Para imagens em assets:
```kotlin
// Carregar de assets
val inputStream = context.assets.open("images/backgrounds/login_bg.png")
val bitmap = BitmapFactory.decodeStream(inputStream)
```

### Para recursos drawable:
```kotlin
// Usar em Compose
Icon(
    painter = painterResource(id = R.drawable.ic_user),
    contentDescription = "Usuário"
)

// Usar imagem
Image(
    painter = painterResource(id = R.drawable.img_logo),
    contentDescription = "Logo"
)
```

## 📱 Densidades Recomendadas

### Para drawable (se não usar vetoriais):
- `mdpi` (160dpi) - Baseline
- `hdpi` (240dpi) - 1.5x
- `xhdpi` (320dpi) - 2x
- `xxhdpi` (480dpi) - 3x
- `xxxhdpi` (640dpi) - 4x

### Tamanhos de Ícones:
- **Ícones de ação**: 24dp
- **Ícones de navegação**: 24dp
- **Ícones de app**: 48dp
- **Ícones grandes**: 96dp

## 🎨 Recomendações de Design

1. **Use SVG/Vector sempre que possível** - Melhor qualidade e menor tamanho
2. **Otimize imagens** - Use ferramentas como TinyPNG
3. **Considere modo escuro** - Crie versões para `drawable-night`
4. **WebP > PNG > JPG** - Ordem de preferência para bitmaps
5. **Nomeação clara** - Use nomes descritivos e consistentes

## 🚀 Próximos Passos

1. Adicionar logo da empresa em `drawable/img_logo.xml`
2. Criar ícones customizados para o app
3. Adicionar fundos para telas de login/cadastro
4. Implementar suporte a modo escuro