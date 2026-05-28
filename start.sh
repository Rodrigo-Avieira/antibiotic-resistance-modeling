#!/bin/bash

echo "A iniciar o Motor Matemático (Python) na porta 8000..."
(cd math-engine && source venv/bin/activate && uvicorn main:app --port 8000) &

echo "A iniciar o Backend (Java) na porta 8080..."
(cd api-backend && SPRING_DATA_MONGODB_URI="mongodb://127.0.0.1:27017/arm_database" ./mvnw spring-boot:run) &

echo "A iniciar o Frontend (React) na porta 5173..."
(cd amr-frontend && npm run dev) &

echo "=========================================================="
echo "Todos os serviços estão a iniciar em paralelo."
echo "Pressione Ctrl+C neste terminal para encerrar tudo de uma vez."
echo "=========================================================="

# O comando wait impede que o script termine imediatamente, mantendo os processos ativos
wait