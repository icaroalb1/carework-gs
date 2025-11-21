#!/bin/bash
# Script para testar se a OpenAI está sendo usada

echo "=== Teste de Uso da OpenAI ==="
echo ""
echo "1. Verificando configuração..."
if [ -f "src/main/resources/application-local.yml" ]; then
    echo "✅ Arquivo application-local.yml encontrado"
    if grep -q "api-key" src/main/resources/application-local.yml; then
        echo "✅ Chave da OpenAI configurada no arquivo local"
    fi
else
    echo "❌ Arquivo application-local.yml não encontrado"
fi

echo ""
echo "2. Testando API de Relatório (vai usar a IA)..."
API_KEY="carework-secret"
USER_ID="11111111-1111-1111-1111-111111111111"

# Criar checkins de teste
echo "Criando checkins de teste..."
curl -s -X POST http://localhost:8080/api/checkins \
  -H "Content-Type: application/json" \
  -H "X-API-Key: $API_KEY" \
  -d '{"mood":4,"stress":2,"sleepQuality":4}' > /dev/null

curl -s -X POST http://localhost:8080/api/checkins \
  -H "Content-Type: application/json" \
  -H "X-API-Key: $API_KEY" \
  -d '{"mood":5,"stress":1,"sleepQuality":5}' > /dev/null

echo "✅ Checkins criados"
echo ""
echo "3. Gerando relatório (isso vai chamar a IA)..."
REPORT=$(curl -s -X GET "http://localhost:8080/api/reports/weekly/$USER_ID" \
  -H "X-API-Key: $API_KEY")

echo ""
echo "=== Resposta da IA ==="
AI_MESSAGE=$(echo "$REPORT" | python3 -c "import sys, json; data=json.load(sys.stdin); print(data.get('aiMessage', 'N/A'))" 2>/dev/null)
echo "$AI_MESSAGE"
echo ""

# Verificar características da resposta
if echo "$AI_MESSAGE" | grep -qi "equipe\|recomendações\|cuidar\|bem-estar"; then
    echo "✅ Resposta parece ser da OpenAI (elaborada e contextual)"
else
    echo "⚠️  Resposta pode ser do fallback local"
fi

echo ""
echo "=== Análise ==="
echo "Se a mensagem for elaborada, contextual e em português brasileiro natural,"
echo "é provável que esteja usando a OpenAI (GPT-4o-mini)."
echo ""
echo "Se a mensagem for simples e genérica, pode estar usando o LocalEnergyChatModel."

