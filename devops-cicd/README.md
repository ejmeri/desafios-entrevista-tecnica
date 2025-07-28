# 🚀 DevOps & CI/CD

[![DevOps](https://img.shields.io/badge/culture-DevOps-blue.svg)]()
[![CI/CD](https://img.shields.io/badge/automation-CI%2FCD-green.svg)]()
[![Infrastructure](https://img.shields.io/badge/infra-IaC-orange.svg)]()

> Guia completo de DevOps e CI/CD para desenvolvimento moderno e preparação para entrevistas técnicas.

---

## 🎯 Por que DevOps é Fundamental?

### 💼 **Transformação Cultural**
- **Colaboração** - Dev e Ops trabalhando juntos
- **Automação** - Redução de trabalho manual
- **Feedback rápido** - Ciclos de desenvolvimento menores
- **Qualidade** - Testes automatizados e deploy seguro

### 🚀 **Benefícios Técnicos**
- **Deploy frequency** - Múltiplos deploys por dia
- **Lead time** - Código em produção mais rápido
- **MTTR** - Recuperação rápida de falhas
- **Change failure rate** - Menos bugs em produção

---

## 🔄 CI/CD Pipeline

### 🔸 **Continuous Integration**

```yaml
# GitHub Actions CI Pipeline
name: CI Pipeline

on:
  push:
    branches: [ main, develop ]
  pull_request:
    branches: [ main ]

jobs:
  test:
    runs-on: ubuntu-latest
    
    services:
      postgres:
        image: postgres:13
        env:
          POSTGRES_PASSWORD: postgres
        options: >-
          --health-cmd pg_isready
          --health-interval 10s
          --health-timeout 5s
          --health-retries 5
    
    steps:
    - uses: actions/checkout@v3
    
    - name: Set up JDK 17
      uses: actions/setup-java@v3
      with:
        java-version: '17'
        distribution: 'temurin'
    
    - name: Cache Maven dependencies
      uses: actions/cache@v3
      with:
        path: ~/.m2
        key: ${{ runner.os }}-m2-${{ hashFiles('**/pom.xml') }}
    
    - name: Run tests
      run: mvn clean test
    
    - name: Run integration tests
      run: mvn verify -P integration-tests
    
    - name: Generate test report
      uses: dorny/test-reporter@v1
      if: success() || failure()
      with:
        name: Maven Tests
        path: target/surefire-reports/*.xml
        reporter: java-junit
    
    - name: Code coverage
      run: mvn jacoco:report
    
    - name: Upload coverage to Codecov
      uses: codecov/codecov-action@v3
      with:
        file: target/site/jacoco/jacoco.xml
```

### 🔸 **Continuous Deployment**

```yaml
# CD Pipeline
name: CD Pipeline

on:
  push:
    branches: [ main ]

jobs:
  deploy:
    runs-on: ubuntu-latest
    needs: test
    
    steps:
    - uses: actions/checkout@v3
    
    - name: Build Docker image
      run: |
        docker build -t myapp:${{ github.sha }} .
        docker tag myapp:${{ github.sha }} myapp:latest
    
    - name: Security scan
      uses: aquasecurity/trivy-action@master
      with:
        image-ref: myapp:${{ github.sha }}
        format: 'sarif'
        output: 'trivy-results.sarif'
    
    - name: Push to registry
      run: |
        echo ${{ secrets.DOCKER_PASSWORD }} | docker login -u ${{ secrets.DOCKER_USERNAME }} --password-stdin
        docker push myapp:${{ github.sha }}
        docker push myapp:latest
    
    - name: Deploy to staging
      run: |
        kubectl set image deployment/myapp myapp=myapp:${{ github.sha }} -n staging
        kubectl rollout status deployment/myapp -n staging
    
    - name: Run smoke tests
      run: |
        curl -f http://staging.myapp.com/health || exit 1
    
    - name: Deploy to production
      if: success()
      run: |
        kubectl set image deployment/myapp myapp=myapp:${{ github.sha }} -n production
        kubectl rollout status deployment/myapp -n production
```

---

## 🐳 Containerização

### 🔸 **Dockerfile Otimizado**

```dockerfile
# Multi-stage build para otimização
FROM maven:3.8.6-openjdk-17-slim AS build

WORKDIR /app
COPY pom.xml .
COPY src ./src

# Build da aplicação
RUN mvn clean package -DskipTests

# Runtime stage
FROM openjdk:17-jre-slim

# Criar usuário não-root
RUN groupadd -r appuser && useradd -r -g appuser appuser

# Instalar dependências de sistema
RUN apt-get update && apt-get install -y \
    curl \
    && rm -rf /var/lib/apt/lists/*

WORKDIR /app

# Copiar apenas o JAR necessário
COPY --from=build /app/target/myapp.jar app.jar

# Configurar usuário
RUN chown appuser:appuser app.jar
USER appuser

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=5s --retries=3 \
    CMD curl -f http://localhost:8080/actuator/health || exit 1

# Configurar JVM para containers
ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0"

EXPOSE 8080

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
```

### 🔸 **Docker Compose para Desenvolvimento**

```yaml
# docker-compose.yml
version: '3.8'

services:
  app:
    build: .
    ports:
      - "8080:8080"
    environment:
      - SPRING_PROFILES_ACTIVE=docker
      - DB_HOST=postgres
      - REDIS_HOST=redis
    depends_on:
      postgres:
        condition: service_healthy
      redis:
        condition: service_started
    volumes:
      - ./logs:/app/logs
    networks:
      - app-network

  postgres:
    image: postgres:13-alpine
    environment:
      POSTGRES_DB: myapp
      POSTGRES_USER: myapp
      POSTGRES_PASSWORD: password
    ports:
      - "5432:5432"
    volumes:
      - postgres_data:/var/lib/postgresql/data
      - ./init.sql:/docker-entrypoint-initdb.d/init.sql
    healthcheck:
      test: ["CMD-SHELL", "pg_isready -U myapp"]
      interval: 10s
      timeout: 5s
      retries: 5
    networks:
      - app-network

  redis:
    image: redis:7-alpine
    ports:
      - "6379:6379"
    volumes:
      - redis_data:/data
    command: redis-server --appendonly yes
    networks:
      - app-network

  nginx:
    image: nginx:alpine
    ports:
      - "80:80"
    volumes:
      - ./nginx.conf:/etc/nginx/nginx.conf
    depends_on:
      - app
    networks:
      - app-network

volumes:
  postgres_data:
  redis_data:

networks:
  app-network:
    driver: bridge
```

---

## ☸️ Kubernetes Deployment

### 🔸 **Deployment Manifest**

```yaml
# deployment.yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: myapp
  labels:
    app: myapp
spec:
  replicas: 3
  strategy:
    type: RollingUpdate
    rollingUpdate:
      maxSurge: 1
      maxUnavailable: 1
  selector:
    matchLabels:
      app: myapp
  template:
    metadata:
      labels:
        app: myapp
    spec:
      containers:
      - name: myapp
        image: myapp:latest
        ports:
        - containerPort: 8080
        env:
        - name: SPRING_PROFILES_ACTIVE
          value: "kubernetes"
        - name: DB_HOST
          valueFrom:
            secretKeyRef:
              name: db-secret
              key: host
        - name: DB_PASSWORD
          valueFrom:
            secretKeyRef:
              name: db-secret
              key: password
        resources:
          requests:
            memory: "512Mi"
            cpu: "250m"
          limits:
            memory: "1Gi"
            cpu: "500m"
        livenessProbe:
          httpGet:
            path: /actuator/health
            port: 8080
          initialDelaySeconds: 60
          periodSeconds: 30
        readinessProbe:
          httpGet:
            path: /actuator/health/readiness
            port: 8080
          initialDelaySeconds: 30
          periodSeconds: 10
        volumeMounts:
        - name: config-volume
          mountPath: /app/config
      volumes:
      - name: config-volume
        configMap:
          name: myapp-config
---
apiVersion: v1
kind: Service
metadata:
  name: myapp-service
spec:
  selector:
    app: myapp
  ports:
  - protocol: TCP
    port: 80
    targetPort: 8080
  type: ClusterIP
---
apiVersion: networking.k8s.io/v1
kind: Ingress
metadata:
  name: myapp-ingress
  annotations:
    kubernetes.io/ingress.class: nginx
    cert-manager.io/cluster-issuer: letsencrypt-prod
spec:
  tls:
  - hosts:
    - myapp.example.com
    secretName: myapp-tls
  rules:
  - host: myapp.example.com
    http:
      paths:
      - path: /
        pathType: Prefix
        backend:
          service:
            name: myapp-service
            port:
              number: 80
```

---

## 🏗️ Infrastructure as Code

### 🔸 **Terraform AWS Infrastructure**

```hcl
# main.tf
terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "~> 5.0"
    }
  }
}

provider "aws" {
  region = var.aws_region
}

# VPC
resource "aws_vpc" "main" {
  cidr_block           = "10.0.0.0/16"
  enable_dns_hostnames = true
  enable_dns_support   = true

  tags = {
    Name = "${var.project_name}-vpc"
  }
}

# Subnets
resource "aws_subnet" "public" {
  count = 2

  vpc_id                  = aws_vpc.main.id
  cidr_block              = "10.0.${count.index + 1}.0/24"
  availability_zone       = data.aws_availability_zones.available.names[count.index]
  map_public_ip_on_launch = true

  tags = {
    Name = "${var.project_name}-public-${count.index + 1}"
  }
}

resource "aws_subnet" "private" {
  count = 2

  vpc_id            = aws_vpc.main.id
  cidr_block        = "10.0.${count.index + 10}.0/24"
  availability_zone = data.aws_availability_zones.available.names[count.index]

  tags = {
    Name = "${var.project_name}-private-${count.index + 1}"
  }
}

# EKS Cluster
resource "aws_eks_cluster" "main" {
  name     = "${var.project_name}-cluster"
  role_arn = aws_iam_role.eks_cluster.arn
  version  = "1.27"

  vpc_config {
    subnet_ids = concat(aws_subnet.public[*].id, aws_subnet.private[*].id)
  }

  depends_on = [
    aws_iam_role_policy_attachment.eks_cluster_policy,
  ]
}

# EKS Node Group
resource "aws_eks_node_group" "main" {
  cluster_name    = aws_eks_cluster.main.name
  node_group_name = "${var.project_name}-nodes"
  node_role_arn   = aws_iam_role.eks_node.arn
  subnet_ids      = aws_subnet.private[*].id

  scaling_config {
    desired_size = 2
    max_size     = 4
    min_size     = 1
  }

  instance_types = ["t3.medium"]

  depends_on = [
    aws_iam_role_policy_attachment.eks_worker_node_policy,
    aws_iam_role_policy_attachment.eks_cni_policy,
    aws_iam_role_policy_attachment.eks_container_registry_policy,
  ]
}

# RDS Database
resource "aws_db_instance" "main" {
  identifier = "${var.project_name}-db"

  engine         = "postgres"
  engine_version = "13.7"
  instance_class = "db.t3.micro"

  allocated_storage     = 20
  max_allocated_storage = 100
  storage_encrypted     = true

  db_name  = var.db_name
  username = var.db_username
  password = var.db_password

  vpc_security_group_ids = [aws_security_group.rds.id]
  db_subnet_group_name   = aws_db_subnet_group.main.name

  backup_retention_period = 7
  backup_window          = "03:00-04:00"
  maintenance_window     = "sun:04:00-sun:05:00"

  skip_final_snapshot = true

  tags = {
    Name = "${var.project_name}-database"
  }
}
```

### 🔸 **Ansible Playbook**

```yaml
# playbook.yml
---
- name: Deploy Application
  hosts: webservers
  become: yes
  vars:
    app_name: myapp
    app_version: "{{ lookup('env', 'APP_VERSION') | default('latest') }}"
    
  tasks:
    - name: Update system packages
      apt:
        update_cache: yes
        upgrade: dist
        
    - name: Install Docker
      apt:
        name: docker.io
        state: present
        
    - name: Start Docker service
      systemd:
        name: docker
        state: started
        enabled: yes
        
    - name: Pull application image
      docker_image:
        name: "{{ app_name }}:{{ app_version }}"
        source: pull
        
    - name: Stop existing container
      docker_container:
        name: "{{ app_name }}"
        state: stopped
      ignore_errors: yes
      
    - name: Remove existing container
      docker_container:
        name: "{{ app_name }}"
        state: absent
      ignore_errors: yes
      
    - name: Start new container
      docker_container:
        name: "{{ app_name }}"
        image: "{{ app_name }}:{{ app_version }}"
        state: started
        restart_policy: always
        ports:
          - "8080:8080"
        env:
          SPRING_PROFILES_ACTIVE: production
          DB_HOST: "{{ db_host }}"
          DB_PASSWORD: "{{ db_password }}"
        healthcheck:
          test: ["CMD", "curl", "-f", "http://localhost:8080/health"]
          interval: 30s
          timeout: 10s
          retries: 3
          
    - name: Wait for application to be ready
      uri:
        url: "http://localhost:8080/health"
        method: GET
        status_code: 200
      register: result
      until: result.status == 200
      retries: 30
      delay: 10
```

---

## 📊 Monitoring e Observabilidade

### 🔸 **Prometheus + Grafana**

```yaml
# prometheus.yml
global:
  scrape_interval: 15s

scrape_configs:
  - job_name: 'myapp'
    static_configs:
      - targets: ['myapp:8080']
    metrics_path: '/actuator/prometheus'
    scrape_interval: 5s

  - job_name: 'node-exporter'
    static_configs:
      - targets: ['node-exporter:9100']

rule_files:
  - "alert_rules.yml"

alerting:
  alertmanagers:
    - static_configs:
        - targets:
          - alertmanager:9093
```

```yaml
# alert_rules.yml
groups:
- name: application
  rules:
  - alert: HighErrorRate
    expr: rate(http_requests_total{status=~"5.."}[5m]) > 0.1
    for: 5m
    labels:
      severity: critical
    annotations:
      summary: "High error rate detected"
      description: "Error rate is {{ $value }} errors per second"

  - alert: HighMemoryUsage
    expr: (node_memory_MemTotal_bytes - node_memory_MemAvailable_bytes) / node_memory_MemTotal_bytes > 0.8
    for: 5m
    labels:
      severity: warning
    annotations:
      summary: "High memory usage"
      description: "Memory usage is above 80%"
```

---

## 🎯 Preparação para Entrevistas

### ✅ **Perguntas Frequentes**

#### **CI/CD Pipeline**
**P:** "Descreva um pipeline CI/CD ideal"
**R:**
1. **Trigger:** Push/PR para repositório
2. **Build:** Compilar e criar artefatos
3. **Test:** Unit, integration, security tests
4. **Package:** Docker image build
5. **Deploy:** Staging → Production
6. **Monitor:** Health checks e rollback

#### **Blue-Green Deployment**
**P:** "Como implementar zero-downtime deployment?"
**R:**
- **Blue-Green:** Duas versões paralelas, switch instantâneo
- **Rolling:** Atualização gradual de instâncias
- **Canary:** Pequeno percentual de tráfego para nova versão

#### **Infrastructure as Code**
**P:** "Vantagens do IaC?"
**R:**
- **Versionamento:** Infraestrutura no Git
- **Reprodutibilidade:** Ambientes idênticos
- **Automação:** Deploy automatizado
- **Documentação:** Código como documentação

---

## 📈 Roadmap de Aprendizado

### 🥇 **Nível Iniciante (1-2 meses)**
1. Git workflows e CI básico
2. Docker fundamentals
3. Basic scripting (Bash/Python)
4. Cloud basics (AWS/Azure)

### 🥈 **Nível Intermediário (2-4 meses)**
1. Kubernetes deployment
2. Infrastructure as Code (Terraform)
3. Monitoring (Prometheus/Grafana)
4. Security scanning

### 🥉 **Nível Avançado (4+ meses)**
1. Advanced orchestration
2. Multi-cloud strategies
3. GitOps workflows
4. Site Reliability Engineering

---

## 🎓 Conclusão

DevOps & CI/CD são **essenciais** porque:

- ✅ **Velocity** - Deploy rápido e seguro
- ✅ **Quality** - Testes automatizados
- ✅ **Reliability** - Sistemas mais estáveis
- ✅ **Collaboration** - Dev e Ops alinhados
- ✅ **Innovation** - Foco em features, não em processo

**Lembre-se:** DevOps é cultura, não apenas ferramentas! 🤝

---

*Desenvolvido para preparação em entrevistas técnicas e crescimento profissional em DevOps & CI/CD.*
