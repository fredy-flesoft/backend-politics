🗳️ SISTEMA WEB DE ENCUESTAS ELECTORALES

<p align="center">

Sistema desarrollado con <b>Java + Spring Boot + Arquitectura Hexagonal</b>  
para administrar información electoral y analizar encuestas políticas.

</p>

---

# 📌 DESCRIPCIÓN DEL PROYECTO

El sistema permite centralizar la información relacionada con:

- partidos políticos
- candidatos
- elecciones
- encuestas electorales
- resultados estadísticos

La plataforma facilita el análisis de tendencias electorales mediante gráficos, reportes y administración de información en tiempo real.

---

# ❗ PROBLEMA

Actualmente la información electoral se encuentra:

- dispersa
- poco organizada
- difícil de analizar
- sin centralización

Esto genera dificultades para:

- consultar candidatos
- visualizar resultados
- administrar elecciones
- analizar encuestas políticas

---

# 🎯 OBJETIVO GENERAL

Desarrollar un sistema web centralizado que permita administrar información electoral y analizar encuestas mediante estadísticas y reportes.

---

# ✅ OBJETIVOS ESPECÍFICOS

- Registrar partidos políticos
- Registrar candidatos
- Gestionar elecciones
- Registrar encuestas electorales
- Visualizar estadísticas
- Analizar tendencias políticas
- Generar reportes automáticos

---

# 🧱 ARQUITECTURA HEXAGONAL

El proyecto utiliza Arquitectura Hexagonal para separar responsabilidades y mantener un sistema modular y escalable.

---

# 🏗️ ESTRUCTURA GENERAL

```text
                    ┌──────────────────────┐
                    │      FRONTEND        │
                    │  Página Web / API    │
                    └──────────┬───────────┘
                               │
                               ▼
                    ┌──────────────────────┐
                    │      CONTROLLER      │
                    │   Endpoints REST     │
                    └──────────┬───────────┘
                               │
                               ▼
                    ┌──────────────────────┐
                    │      USE CASES       │
                    │   Lógica del negocio │
                    └──────────┬───────────┘
                               │
                               ▼
                    ┌──────────────────────┐
                    │       DOMAIN         │
                    │ Entidades y reglas   │
                    └──────────┬───────────┘
                               │
                               ▼
                    ┌──────────────────────┐
                    │        PORTS         │
                    │ Interfaces del core  │
                    └──────────┬───────────┘
                               │
                               ▼
                    ┌──────────────────────┐
                    │      ADAPTERS        │
                    │ Spring Data JPA      │
                    └──────────┬───────────┘
                               │
                               ▼
                    ┌──────────────────────┐
                    │       MYSQL DB       │
                    │    Base de datos     │
                    └──────────────────────┘
```

---

# 📂 ESTRUCTURA DEL PROYECTO

```text
src/
└── main/
    └── java/
        └── com/encuestas/electorales/

            ├── domain/
            │
            │   ├── model/
            │   │   ├── Partido.java
            │   │   ├── Candidato.java
            │   │   ├── Eleccion.java
            │   │   ├── Encuesta.java
            │   │   └── ResultadoEncuesta.java
            │   │
            │   └── repository/
            │       ├── PartidoRepository.java
            │       ├── CandidatoRepository.java
            │       ├── EleccionRepository.java
            │       └── EncuestaRepository.java
            │
            │
            ├── application/
            │
            │   ├── port/
            │   │
            │   │   ├── in/
            │   │   │   ├── RegistrarPartidoUseCase.java
            │   │   │   ├── RegistrarCandidatoUseCase.java
            │   │   │   ├── RegistrarEncuestaUseCase.java
            │   │   │   └── GenerarReporteUseCase.java
            │   │   │
            │   │   └── out/
            │   │       ├── PartidoPersistencePort.java
            │   │       ├── CandidatoPersistencePort.java
            │   │       ├── EncuestaPersistencePort.java
            │   │       └── ReportePersistencePort.java
            │   │
            │   └── service/
            │       ├── PartidoService.java
            │       ├── CandidatoService.java
            │       ├── EncuestaService.java
            │       └── ReporteService.java
            │
            │
            ├── infrastructure/
            │
            │   ├── persistence/
            │   │   ├── entity/
            │   │   ├── mapper/
            │   │   └── repository/
            │   │
            │   └── config/
            │       └── BeanConfiguration.java
            │
            │
            ├── adapters/
            │
            │   └── controller/
            │       ├── PartidoController.java
            │       ├── CandidatoController.java
            │       ├── EncuestaController.java
            │       └── ReporteController.java
            │
            │
            └── EncuestasElectoralesApplication.java
```

---

# 🧠 EXPLICACIÓN DE CAPAS

## 🌐 FRONTEND

Parte visual del sistema.

Permite:

- registrar candidatos
- registrar partidos
- visualizar encuestas
- mostrar estadísticas
