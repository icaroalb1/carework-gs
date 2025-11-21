# Configuração da OpenAI

## ✅ Chave já configurada!

A chave da OpenAI já está configurada no arquivo `src/main/resources/application-local.yml` (este arquivo está no `.gitignore` e **NÃO será commitado**).

## Como executar a aplicação com a OpenAI

### Opção 1: Script facilitado (Recomendado)

```bash
./run-local.sh
```

### Opção 2: Comando Maven direto

```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=local
```

### Opção 3: Variável de Ambiente (Alternativa)

Se preferir usar variável de ambiente ao invés do arquivo:

**Linux/macOS:**
```bash
export OPENAI_API_KEY="sua-chave-aqui"
./mvnw spring-boot:run
```

**Windows (PowerShell):**
```powershell
$env:OPENAI_API_KEY="sua-chave-aqui"
./mvnw spring-boot:run
```

## Importante

⚠️ **NUNCA** commite a chave da OpenAI no repositório Git!

- O arquivo `application-local.yml` está no `.gitignore`
- Use variáveis de ambiente em produção
- A chave será lida automaticamente da variável `OPENAI_API_KEY` ou do arquivo de configuração

## Fallback

Se a chave da OpenAI não estiver configurada, o sistema usará automaticamente o `LocalEnergyChatModel` como fallback, que gera mensagens simuladas baseadas nos indicadores.

