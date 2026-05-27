# Antimicrobial Resistance (AMR) Modeling

## Overview
This repository contains the source code, research, and documentation for the Calculus II Capstone Project at Jala University. The project focuses on the mathematical modeling of bacterial resistance to antibiotics over time, utilizing Ordinary Differential Equations (ODEs) and population dynamics models.

## Objectives
The primary goal is to simulate how susceptible bacterial populations decrease while resistant strains multiply under specific antibiotic treatments. To achieve this, the project bridges applied mathematics and software engineering through a polyglot architecture.

## Architecture & Tech Stack
The project follows a strict separation of concerns, dividing heavy mathematical processing from transactional API logic:

* **Mathematical Engine & Data Processing (Python):** Handles the ETL (Extract, Transform, Load) processes and executes the differential equation models using libraries such as `scipy` and `pandas`.
* **Core Backend API (Java / Spring Boot):** Manages simulation requests, orchestrates communication with the Python engine, and handles the application's business logic.
* **Database (MongoDB):** Provides a flexible, document-oriented schema to store unstructured raw datasets, simulation parameters, and mathematical projections.

## Data Sources
The mathematical models are grounded in real-world Antimicrobial Resistance (AMR) datasets sourced from:
* Centers for Disease Control and Prevention (CDC)
* World Health Organization (WHO)
* Kaggle (Open-source AMR datasets)