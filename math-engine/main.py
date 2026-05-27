from fastapi import FastAPI
from pydantic import BaseModel
import numpy as np
from scipy.integrate import odeint

app = FastAPI()

class SimulationData(BaseModel):
    initialBacterialLoad: float
    mutationRate: float
    treatmentDays: int

# Sistema de Equações Diferenciais
def amr_model(y, t, mutation_rate):
    S, R = y
    r = 1.0       # Taxa de crescimento populacional natural
    K = 1e7       # Capacidade de carga do ambiente (limite de bactérias)
    a = 1.5       # Taxa de mortalidade induzida pelo antibiótico (efeito em S)
    
    # Derivadas (Taxas de variação)
    dSdt = r * S * (1 - (S + R) / K) - a * S - mutation_rate * S
    dRdt = r * R * (1 - (S + R) / K) + mutation_rate * S
    
    return [dSdt, dRdt]

@app.post("/calculate")
def calculate_resistance(data: SimulationData):
    S0 = data.initialBacterialLoad
    R0 = S0 * 0.001 # Assume-se que a infecção começa com 0.1% de bactérias mutantes
    y0 = [S0, R0]
    
    # Vetor de tempo (dias de tratamento)
    t = np.linspace(0, data.treatmentDays, data.treatmentDays + 1)
    
    # Resolve as EDOs
    solution = odeint(amr_model, y0, t, args=(data.mutationRate,))
    
    # Formata a resposta para o frontend
    results = []
    for i in range(len(t)):
        results.append({
            "day": int(t[i]),
            "susceptible": max(0, round(float(solution[i, 0]), 2)),
            "resistant": max(0, round(float(solution[i, 1]), 2))
        })
        
    return {"status": "success", "data": results}