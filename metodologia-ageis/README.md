# 🚀 Metodologias Ágeis para Desenvolvedores

[![Agile](https://img.shields.io/badge/methodology-Agile-blue.svg)]()
[![Scrum](https://img.shields.io/badge/framework-Scrum-green.svg)]()
[![Kanban](https://img.shields.io/badge/method-Kanban-orange.svg)]()

> Guia prático de metodologias ágeis para entrevistas técnicas e trabalho em equipe eficaz.

---

## 🎯 Por que Metodologias Ágeis são Essenciais?

### 💼 **Importância Profissional**

- **95% das empresas** usam metodologias ágeis
- **Requisito obrigatório** em vagas de desenvolvimento
- **Diferencial competitivo** - conhecimento prático valorizado
- **Evolução de carreira** - Scrum Master, Product Owner, Tech Lead

### 🚀 **Benefícios para Desenvolvedores**

- **Entregas frequentes** - feedback rápido e ajustes
- **Colaboração efetiva** - comunicação clara entre equipes
- **Adaptabilidade** - resposta rápida a mudanças
- **Qualidade** - testes contínuos e refatoração

### 🔧 **Para Entrevistas Técnicas**

- **Perguntas obrigatórias** - experiência com Scrum/Kanban
- **Soft skills** - trabalho em equipe e comunicação
- **Resolução de problemas** - como lidar com impedimentos
- **Liderança técnica** - mentoria e tomada de decisões

---

## 📋 Manifesto Ágil - Fundamentos

### 🔸 **4 Valores Fundamentais**

#### **1. Indivíduos e interações** > processos e ferramentas

```
❌ Foco excessivo em documentação e processos rígidos
✅ Comunicação direta, colaboração e confiança mútua

Exemplo prático:
- Daily standup de 15 min vs relatórios semanais de 2h
- Pair programming vs code review formal extenso
- Conversa direta vs emails longos
```

#### **2. Software funcionando** > documentação abrangente

```
❌ Documentação extensa que fica desatualizada
✅ Código limpo, testes automatizados e demos funcionais

Exemplo prático:
- README.md atualizado vs documento de 50 páginas
- Testes automatizados vs casos de teste manuais
- Demo ao final do sprint vs apresentação PowerPoint
```

#### **3. Colaboração com cliente** > negociação de contratos

```
❌ Escopo fixo definido no início do projeto
✅ Feedback contínuo e ajustes baseados no valor

Exemplo prático:
- Product Owner participando das dailies
- Demos regulares com stakeholders
- Backlog refinement colaborativo
```

#### **4. Responder a mudanças** > seguir um plano

```
❌ Plano rígido de 6 meses sem flexibilidade
✅ Adaptação baseada em aprendizado e feedback

Exemplo prático:
- Sprint planning flexível baseado em prioridades
- Retrospectivas para melhorias contínuas
- Pivot rápido baseado em dados de usuário
```

---

## 🏃‍♂️ Scrum Framework

### 🔸 **Papéis (Roles)**

#### **Product Owner (PO)**

**Responsabilidades:**

- Definir e priorizar o Product Backlog
- Comunicar a visão do produto para a equipe
- Aceitar ou rejeitar entregas do sprint
- Maximizar o valor do produto

**Interação com Desenvolvedores:**

```
Daily Scrum:
- PO disponível para esclarecer dúvidas sobre user stories
- Desenvolvedores reportam progresso e impedimentos
- Ajustes de prioridade se necessário

Sprint Review:
- PO apresenta o que foi entregue
- Desenvolvedores demonstram funcionalidades
- Feedback dos stakeholders é coletado
```

#### **Scrum Master**

**Responsabilidades:**

- Facilitar eventos Scrum
- Remover impedimentos da equipe
- Proteger a equipe de interrupções externas
- Coaching em práticas ágeis

**Como ajuda Desenvolvedores:**

```
Remoção de Impedimentos:
- Acesso a ambientes de desenvolvimento
- Resolução de conflitos entre equipes
- Facilitação de decisões técnicas
- Proteção contra mudanças de escopo no sprint
```

#### **Development Team**

**Características:**

- Auto-organizável e multifuncional
- 3-9 pessoas (ideal: 5-7)
- Responsável coletiva pela entrega
- Sem hierarquia interna

**Responsabilidades:**

```
Sprint Planning:
- Estimar user stories (story points)
- Definir tasks técnicas necessárias
- Comprometer-se com o sprint goal

Daily Scrum:
- Reportar progresso das últimas 24h
- Planejar próximas 24h
- Identificar impedimentos
```

---

### 🔸 **Eventos Scrum**

#### **Sprint Planning**

**Duração:** 8h para sprint de 1 mês (proporcional)
**Participantes:** Todo o Scrum Team

**Parte 1 - O QUE fazer:**

```
Input:
- Product Backlog priorizado
- Velocity da equipe (sprints anteriores)
- Capacity da equipe (férias, feriados)

Output:
- Sprint Goal definido
- Sprint Backlog selecionado
- Commitment da equipe
```

**Parte 2 - COMO fazer:**

```
Atividades:
- Quebrar user stories em tasks técnicas
- Estimar esforço em horas
- Identificar dependências
- Definir Definition of Done

Exemplo de Task Breakdown:
User Story: "Como usuário, quero fazer login"
├── Criar endpoint POST /api/auth/login
├── Implementar validação de credenciais
├── Gerar JWT token
├── Criar tela de login (frontend)
├── Integrar frontend com API
├── Escrever testes unitários
├── Escrever testes de integração
└── Atualizar documentação da API
```

#### **Daily Scrum**

**Duração:** 15 minutos
**Horário:** Mesmo horário todos os dias
**Formato:** Cada membro responde 3 perguntas

```
1. O que fiz ontem que ajudou a atingir o sprint goal?
   "Terminei a implementação do endpoint de login e escrevi os testes unitários"

2. O que vou fazer hoje para ajudar a atingir o sprint goal?
   "Vou integrar o frontend com a API e fazer os testes de integração"

3. Há algum impedimento no meu caminho?
   "Preciso de acesso ao ambiente de staging para testar a integração"

❌ Evitar:
- Relatórios detalhados para o Scrum Master
- Discussões técnicas longas
- Resolver problemas durante a daily

✅ Fazer:
- Comunicação direta entre desenvolvedores
- Identificar colaborações necessárias
- Marcar reuniões específicas para discussões técnicas
```

#### **Sprint Review**

**Duração:** 4h para sprint de 1 mês
**Participantes:** Scrum Team + Stakeholders

```
Agenda:
1. PO apresenta o que foi planejado vs entregue
2. Development Team demonstra funcionalidades
3. Stakeholders dão feedback
4. Discussão sobre próximos passos
5. Atualização do Product Backlog

Exemplo de Demo:
- Mostrar funcionalidade funcionando (não slides)
- Explicar valor de negócio entregue
- Demonstrar em ambiente similar à produção
- Coletar feedback específico e acionável
```

#### **Sprint Retrospective**

**Duração:** 3h para sprint de 1 mês
**Participantes:** Apenas Scrum Team

```
Formato "Start, Stop, Continue":

Start (Começar a fazer):
- Pair programming para tasks complexas
- Code review mais rigoroso
- Testes automatizados para todas as features

Stop (Parar de fazer):
- Commits direto na main branch
- Reuniões longas sem agenda
- Assumir tasks sem estimativa

Continue (Continuar fazendo):
- Daily standups eficientes
- Documentação de APIs atualizada
- Refactoring contínuo do código
```

---

### 🔸 **Artefatos Scrum**

#### **Product Backlog**

```
Características:
- Lista priorizada de funcionalidades
- Itens descritos como user stories
- Estimados em story points
- Refinados continuamente

Exemplo de User Story:
Título: Login com Google
Como: usuário do sistema
Quero: fazer login usando minha conta Google
Para que: não precise criar nova senha

Critérios de Aceitação:
- [ ] Botão "Login com Google" na tela de login
- [ ] Redirecionamento para OAuth do Google
- [ ] Criação automática de conta se não existir
- [ ] Redirecionamento para dashboard após login
- [ ] Tratamento de erro se login falhar

Story Points: 5
Prioridade: Alta
```

#### **Sprint Backlog**

```
Composição:
- User stories selecionadas para o sprint
- Tasks técnicas necessárias
- Sprint goal
- Estimativas em horas

Exemplo:
Sprint Goal: "Usuários podem se autenticar no sistema"

User Stories:
1. Login com email/senha (8 pts)
2. Login com Google (5 pts)
3. Logout (2 pts)

Tasks:
- Implementar JWT authentication (8h)
- Criar middleware de autorização (4h)
- Integrar OAuth Google (6h)
- Testes de segurança (4h)
```

#### **Increment**

```
Definition of Done:
- [ ] Código revisado por pelo menos 1 desenvolvedor
- [ ] Testes unitários com cobertura > 80%
- [ ] Testes de integração passando
- [ ] Documentação da API atualizada
- [ ] Deploy em ambiente de staging
- [ ] Aprovação do Product Owner
- [ ] Sem bugs críticos conhecidos
```

---

## 📊 Kanban Method

### 🔸 **Princípios Fundamentais**

#### **1. Visualizar o Trabalho**

```
Exemplo de Board Kanban:

┌─────────────┬─────────────┬─────────────┬─────────────┬─────────────┐
│   Backlog   │   To Do     │ In Progress │   Review    │    Done     │
├─────────────┼─────────────┼─────────────┼─────────────┼─────────────┤
│ User Story A│ Task 1      │ Task 2      │ Task 3      │ Task 4      │
│ User Story B│ Task 5      │ Task 6      │             │ Task 7      │
│ User Story C│             │             │             │ Task 8      │
│ Bug Fix D   │             │             │             │             │
└─────────────┴─────────────┴─────────────┴─────────────┴─────────────┘

WIP Limits:     ∞           3            2            2            ∞
```

#### **2. Limitar Work in Progress (WIP)**

```
Benefícios do WIP Limit:
- Reduz multitasking
- Identifica gargalos rapidamente
- Melhora qualidade (foco)
- Acelera entrega (flow)

Exemplo prático:
❌ Sem WIP Limit:
- Dev 1: trabalhando em 4 tasks simultaneamente
- Dev 2: trabalhando em 3 tasks simultaneamente
- Resultado: nada é entregue rapidamente

✅ Com WIP Limit (2 por pessoa):
- Dev 1: foca em 2 tasks, termina mais rápido
- Dev 2: foca em 2 tasks, termina mais rápido
- Resultado: entregas contínuas
```

#### **3. Gerenciar Flow**

```
Métricas importantes:

Lead Time: Tempo total desde request até entrega
Cycle Time: Tempo desde início do trabalho até entrega
Throughput: Número de itens entregues por período

Exemplo:
Task criada: 01/01 (entra no backlog)
Trabalho iniciado: 05/01 (entra em "In Progress")
Task entregue: 10/01 (entra em "Done")

Lead Time: 9 dias (01/01 a 10/01)
Cycle Time: 5 dias (05/01 a 10/01)
```

#### **4. Tornar Políticas Explícitas**

```
Definition of Ready (DoR):
- [ ] User story tem critérios de aceitação claros
- [ ] Dependências identificadas e resolvidas
- [ ] Estimativa de esforço definida
- [ ] Mockups/wireframes aprovados (se necessário)

Definition of Done (DoD):
- [ ] Código implementado e testado
- [ ] Code review aprovado
- [ ] Testes automatizados passando
- [ ] Deploy em staging realizado
- [ ] Documentação atualizada
```

#### **5. Melhorar Colaborativamente**

```
Reuniões Kanban:

Daily Standup (15 min):
- Foco no board, não nas pessoas
- Identificar bloqueios e gargalos
- Discutir prioridades

Replenishment Meeting (semanal):
- Adicionar novos itens ao backlog
- Priorizar baseado em valor e urgência
- Ajustar capacity baseado em métricas

Delivery Planning (mensal):
- Revisar métricas de flow
- Identificar melhorias no processo
- Ajustar WIP limits se necessário
```

---

## 🔄 Scrum vs Kanban - Quando Usar?

### 📊 **Comparação Detalhada**

| Aspecto          | Scrum              | Kanban                |
| ---------------- | ------------------ | --------------------- |
| **Estrutura**    | Time-boxed sprints | Fluxo contínuo        |
| **Papéis**       | PO, SM, Dev Team   | Flexível              |
| **Planejamento** | Sprint Planning    | Contínuo              |
| **Entregas**     | Final do sprint    | Contínuo              |
| **Mudanças**     | Entre sprints      | A qualquer momento    |
| **Métricas**     | Velocity, Burndown | Lead Time, Throughput |

### 🎯 **Quando Usar Scrum**

```
✅ Use Scrum quando:
- Equipe nova em metodologias ágeis
- Projeto com escopo bem definido
- Necessidade de entregas regulares
- Stakeholders querem previsibilidade
- Equipe precisa de estrutura clara

Exemplos:
- Desenvolvimento de MVP
- Projeto com deadline fixo
- Equipe distribuída geograficamente
- Cliente quer demos regulares
```

### 🎯 **Quando Usar Kanban**

```
✅ Use Kanban quando:
- Trabalho de manutenção/suporte
- Prioridades mudam frequentemente
- Equipe madura e auto-organizável
- Fluxo de trabalho irregular
- Necessidade de entrega contínua

Exemplos:
- Bug fixes e melhorias
- Suporte técnico
- DevOps e infraestrutura
- Pesquisa e desenvolvimento
```

---

## 🛠️ Ferramentas Práticas

### 🔸 **Jira - Configuração para Scrum**

```
Configuração de Projeto Scrum:

1. Criar Épicos:
   - Autenticação de Usuários
   - Gestão de Produtos
   - Sistema de Pagamentos

2. Criar User Stories:
   Título: Login com email
   Tipo: Story
   Épico: Autenticação de Usuários
   Story Points: 3

   Descrição:
   Como usuário registrado
   Quero fazer login com email e senha
   Para que possa acessar minha conta

   Critérios de Aceitação:
   - [ ] Validação de email obrigatório
   - [ ] Validação de senha obrigatório
   - [ ] Mensagem de erro para credenciais inválidas
   - [ ] Redirecionamento após login bem-sucedido

3. Configurar Sprint:
   - Duração: 2 semanas
   - Capacity: 40 story points
   - Sprint Goal: "Usuários podem se autenticar"
```

### 🔸 **Trello - Board Kanban Simples**

```
Listas do Board:

📋 Backlog
- Ideias e requests não priorizados
- WIP Limit: ∞

📝 To Do (Ready)
- Tasks priorizadas e prontas para desenvolvimento
- WIP Limit: 5

⚡ In Progress
- Tasks sendo desenvolvidas ativamente
- WIP Limit: 3 (1 por desenvolvedor)

👀 Code Review
- Tasks aguardando review
- WIP Limit: 2

✅ Testing
- Tasks em teste/QA
- WIP Limit: 2

🚀 Done
- Tasks completadas e entregues
- WIP Limit: ∞

Cards incluem:
- Título descritivo
- Labels (bug, feature, improvement)
- Assignee
- Due date
- Checklist de subtasks
```

### 🔸 **GitHub Projects - Integração com Código**

```
Configuração:

1. Criar Project Board
2. Configurar Colunas:
   - Backlog
   - Sprint Backlog
   - In Progress
   - In Review
   - Done

3. Automatizações:
   - Issue criada → move para Backlog
   - PR aberto → move para In Review
   - PR merged → move para Done
   - Issue fechada → move para Done

4. Templates de Issue:
---
name: User Story
about: Nova funcionalidade
title: '[STORY] '
labels: story
assignees: ''
---

## User Story
Como [tipo de usuário]
Quero [objetivo]
Para que [benefício]

## Critérios de Aceitação
- [ ] Critério 1
- [ ] Critério 2
- [ ] Critério 3

## Tasks Técnicas
- [ ] Task 1
- [ ] Task 2
- [ ] Task 3

## Definition of Done
- [ ] Código implementado
- [ ] Testes unitários
- [ ] Code review
- [ ] Documentação
```

---

## 🎯 Preparação para Entrevistas

### ✅ **Tópicos Essenciais**

#### **Fundamentos Ágeis**

- 4 valores e 12 princípios do Manifesto Ágil
- Diferenças entre metodologias tradicionais e ágeis
- Benefícios e desafios das metodologias ágeis
- Quando usar Scrum vs Kanban vs outros frameworks

#### **Scrum Framework**

- Papéis, eventos e artefatos do Scrum
- Como estimar user stories (Planning Poker, T-shirt sizes)
- Definition of Ready e Definition of Done
- Como lidar com mudanças durante o sprint

#### **Práticas de Desenvolvimento**

- Test-Driven Development (TDD)
- Continuous Integration/Continuous Deployment
- Pair Programming e Code Review
- Refactoring e Technical Debt

### 🗣️ **Perguntas Frequentes**

#### **Experiência com Scrum**

**P:** "Conte sobre sua experiência com Scrum"
**R:**

```
"Trabalhei em uma equipe Scrum de 6 desenvolvedores por 2 anos.
Fazíamos sprints de 2 semanas com:

- Sprint Planning às segundas (4h): estimávamos stories com Planning Poker
- Daily Scrum às 9h (15 min): focávamos no board e impedimentos
- Sprint Review às sextas (2h): demo para stakeholders
- Retrospective (1h): melhorias contínuas

Exemplo de melhoria: identificamos que code reviews estavam atrasando
entregas, então implementamos pair programming para tasks complexas,
reduzindo o cycle time de 3 para 1.5 dias."
```

#### **Lidando com Mudanças**

**P:** "Como lidar quando o cliente quer mudar o escopo no meio do sprint?"
**R:**

```
"Primeiro, explico que mudanças durante o sprint comprometem o sprint goal.
Opções:

1. Adicionar ao Product Backlog para próximo sprint
2. Se urgente, avaliar impacto com a equipe
3. Se necessário, cancelar sprint atual e replanejar
4. Trocar story de mesmo tamanho (swap)

Exemplo real: cliente queria mudança crítica de segurança.
Avaliamos com equipe, removemos uma feature de menor prioridade
e incluímos o fix, mantendo o commitment do sprint."
```

#### **Estimativas**

**P:** "Como vocês estimavam user stories?"
**R:**

```
"Usávamos Planning Poker com sequência Fibonacci (1,2,3,5,8,13,21):

1. PO explicava a story
2. Desenvolvedores faziam perguntas
3. Cada um escolhia carta em segredo
4. Revelávamos simultaneamente
5. Discutíamos diferenças (maior vs menor)
6. Re-estimávamos até consenso

Story points representavam complexidade relativa, não tempo.
Calibramos com stories de referência:
- 1 pt: Alterar texto de botão
- 3 pts: Novo endpoint CRUD simples
- 8 pts: Integração com API externa
- 13 pts: Nova funcionalidade complexa
```

#### **Retrospectivas**

**P:** "Dê exemplo de melhoria que surgiu em retrospectiva"
**R:**

```
"Identificamos que bugs em produção estavam aumentando.

Root Cause Analysis:
- Testes manuais insuficientes
- Code review superficial
- Pressão para entregar rápido

Actions:
1. Implementamos testes automatizados obrigatórios
2. Criamos checklist para code review
3. Adicionamos "testing" como critério no DoD
4. Reservamos 20% do sprint para technical debt

Resultado: bugs em produção reduziram 70% em 3 sprints."
```

---

## 🎓 Conclusão

Metodologias Ágeis são **essenciais** para desenvolvedores porque:

- ✅ **Padrão da indústria** - 95% das empresas usam práticas ágeis
- ✅ **Melhora qualidade** - entregas frequentes e feedback contínuo
- ✅ **Acelera desenvolvimento** - foco no valor e eliminação de desperdícios
- ✅ **Desenvolve soft skills** - comunicação, colaboração, adaptabilidade
- ✅ **Oportunidades de carreira** - Scrum Master, Product Owner, Agile Coach

**Lembre-se:** Agilidade é sobre pessoas e interações, não apenas processos! 🤝

---

_Desenvolvido para preparação em entrevistas técnicas e crescimento profissional em Metodologias Ágeis._
