from fastapi import FastAPI
from pydantic import BaseModel
import numpy as np
from scipy.integrate import odeint

app = FastAPI()

# 1. Adicionamos as variáveis biológicas na recepção da API
class SimulationData(BaseModel):
    initialBacterialLoad: float
    mutationRate: float
    treatmentDays: int
    growthRate: float          # Novo
    antibioticEfficacy: float  # Novo

# 2. Atualizamos a EDO para não usar mais números fixos
def amr_model(y, t, mutation_rate, growth_rate, antibiotic_efficacy):
    S, R = y
    K = 1e7 # Capacidade de carga
    
    # Derivadas com as variáveis dinâmicas reais
    dSdt = growth_rate * S * (1 - (S + R) / K) - antibiotic_efficacy * S - mutation_rate * S
    dRdt = growth_rate * R * (1 - (S + R) / K) + mutation_rate * S
    
    return [dSdt, dRdt]

@app.post("/calculate")
def calculate_resistance(data: SimulationData):
    S0 = data.initialBacterialLoad
    R0 = S0 * 0.001
    y0 = [S0, R0]

    steps = data.treatmentDays * 10 
    t = np.linspace(0, data.treatmentDays, steps + 1)
    
    solution = odeint(amr_model, y0, t, args=(data.mutationRate, data.growthRate, data.antibioticEfficacy))
    
    results = []
    for i in range(len(t)):
        results.append({
            "day": round(float(t[i]), 1), 
            "susceptible": max(0, round(float(solution[i, 0]), 2)),
            "resistant": max(0, round(float(solution[i, 1]), 2))
        })
        
    return {"status": "success", "data": results}