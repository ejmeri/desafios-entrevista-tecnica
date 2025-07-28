# ⚡ Performance Optimization

[![Performance](https://img.shields.io/badge/focus-Performance-red.svg)]()
[![Optimization](https://img.shields.io/badge/goal-Optimization-green.svg)]()
[![Monitoring](https://img.shields.io/badge/tool-Monitoring-blue.svg)]()

> Guia completo de otimização de performance para aplicações de alta escala e preparação para entrevistas técnicas.

---

## 🎯 Por que Performance é Fundamental?

### 💼 **Impacto no Negócio**
- **Experiência do usuário** - 100ms delay = 1% drop in sales
- **SEO ranking** - Google considera velocidade
- **Custos operacionais** - Recursos otimizados = menor custo
- **Competitividade** - Performance como diferencial

### 🚀 **Benefícios Técnicos**
- **Escalabilidade** - Mais usuários com mesmos recursos
- **Confiabilidade** - Sistemas mais estáveis
- **Manutenibilidade** - Código otimizado é mais limpo
- **Monitoramento** - Visibilidade de gargalos

---

## 📊 Métricas de Performance

### 🔸 **Core Web Vitals**

#### **Largest Contentful Paint (LCP)**
```javascript
// Medir LCP
new PerformanceObserver((entryList) => {
    for (const entry of entryList.getEntries()) {
        console.log('LCP:', entry.startTime);
        // Enviar para analytics
        analytics.track('lcp', entry.startTime);
    }
}).observe({entryTypes: ['largest-contentful-paint']});

// Meta: < 2.5 segundos
```

#### **First Input Delay (FID)**
```javascript
// Medir FID
new PerformanceObserver((entryList) => {
    for (const entry of entryList.getEntries()) {
        console.log('FID:', entry.processingStart - entry.startTime);
    }
}).observe({entryTypes: ['first-input']});

// Meta: < 100ms
```

#### **Cumulative Layout Shift (CLS)**
```javascript
// Medir CLS
let clsValue = 0;
new PerformanceObserver((entryList) => {
    for (const entry of entryList.getEntries()) {
        if (!entry.hadRecentInput) {
            clsValue += entry.value;
        }
    }
    console.log('CLS:', clsValue);
}).observe({entryTypes: ['layout-shift']});

// Meta: < 0.1
```

### 🔸 **Backend Metrics**

```java
// Spring Boot Actuator Metrics
@RestController
public class MetricsController {
    
    @Autowired
    private MeterRegistry meterRegistry;
    
    @GetMapping("/api/users")
    @Timed(name = "users.get", description = "Time taken to get users")
    public ResponseEntity<List<User>> getUsers() {
        Timer.Sample sample = Timer.start(meterRegistry);
        
        try {
            List<User> users = userService.findAll();
            
            // Custom metrics
            meterRegistry.counter("users.requests.total").increment();
            meterRegistry.gauge("users.count", users.size());
            
            return ResponseEntity.ok(users);
        } finally {
            sample.stop(Timer.builder("users.get.duration")
                .register(meterRegistry));
        }
    }
}
```

---

## 🚀 Frontend Optimization

### 🔸 **Code Splitting**

```javascript
// React Lazy Loading
import { lazy, Suspense } from 'react';

const Dashboard = lazy(() => import('./Dashboard'));
const Profile = lazy(() => import('./Profile'));

function App() {
    return (
        <Router>
            <Suspense fallback={<div>Loading...</div>}>
                <Routes>
                    <Route path="/dashboard" element={<Dashboard />} />
                    <Route path="/profile" element={<Profile />} />
                </Routes>
            </Suspense>
        </Router>
    );
}

// Webpack Bundle Analysis
// webpack-bundle-analyzer para visualizar bundles
```

### 🔸 **Image Optimization**

```html
<!-- Responsive Images -->
<picture>
    <source media="(min-width: 800px)" srcset="hero-large.webp" type="image/webp">
    <source media="(min-width: 400px)" srcset="hero-medium.webp" type="image/webp">
    <source srcset="hero-small.webp" type="image/webp">
    <img src="hero-fallback.jpg" alt="Hero image" loading="lazy">
</picture>

<!-- Next.js Image Optimization -->
<Image
    src="/hero.jpg"
    alt="Hero"
    width={800}
    height={400}
    priority={true}
    placeholder="blur"
    blurDataURL="data:image/jpeg;base64,..."
/>
```

### 🔸 **Caching Strategies**

```javascript
// Service Worker Caching
self.addEventListener('fetch', (event) => {
    if (event.request.destination === 'image') {
        event.respondWith(
            caches.open('images-v1').then(cache => {
                return cache.match(event.request).then(response => {
                    if (response) {
                        return response;
                    }
                    
                    return fetch(event.request).then(fetchResponse => {
                        cache.put(event.request, fetchResponse.clone());
                        return fetchResponse;
                    });
                });
            })
        );
    }
});

// HTTP Caching Headers
app.use('/static', express.static('public', {
    maxAge: '1y',
    etag: true,
    lastModified: true
}));
```

---

## ⚡ Backend Optimization

### 🔸 **Database Optimization**

#### **Query Optimization**
```sql
-- Antes: N+1 Query Problem
SELECT * FROM users;
-- Para cada user:
SELECT * FROM orders WHERE user_id = ?;

-- Depois: Join Otimizado
SELECT u.*, o.*
FROM users u
LEFT JOIN orders o ON u.id = o.user_id
WHERE u.status = 'active';

-- Índices Estratégicos
CREATE INDEX idx_orders_user_status ON orders(user_id, status, created_at);
CREATE INDEX idx_users_email_status ON users(email, status) WHERE status = 'active';
```

#### **Connection Pooling**
```java
// HikariCP Configuration
@Configuration
public class DatabaseConfig {
    
    @Bean
    @ConfigurationProperties("spring.datasource.hikari")
    public HikariDataSource dataSource() {
        HikariConfig config = new HikariConfig();
        config.setMaximumPoolSize(20);
        config.setMinimumIdle(5);
        config.setConnectionTimeout(30000);
        config.setIdleTimeout(600000);
        config.setMaxLifetime(1800000);
        config.setLeakDetectionThreshold(60000);
        
        return new HikariDataSource(config);
    }
}
```

### 🔸 **Caching Layers**

```java
// Multi-level Caching
@Service
public class UserService {
    
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    
    @Autowired
    private UserRepository userRepository;
    
    // L1: Application Cache (Caffeine)
    @Cacheable(value = "users", key = "#id")
    public User findById(Long id) {
        // L2: Distributed Cache (Redis)
        String cacheKey = "user:" + id;
        User user = (User) redisTemplate.opsForValue().get(cacheKey);
        
        if (user == null) {
            // L3: Database
            user = userRepository.findById(id).orElse(null);
            
            if (user != null) {
                // Cache for 1 hour
                redisTemplate.opsForValue().set(cacheKey, user, Duration.ofHours(1));
            }
        }
        
        return user;
    }
    
    @CacheEvict(value = "users", key = "#user.id")
    public User update(User user) {
        // Invalidate Redis cache
        redisTemplate.delete("user:" + user.getId());
        return userRepository.save(user);
    }
}
```

### 🔸 **Async Processing**

```java
// Async Methods
@Service
public class EmailService {
    
    @Async("taskExecutor")
    @Retryable(value = {Exception.class}, maxAttempts = 3)
    public CompletableFuture<Void> sendWelcomeEmail(User user) {
        try {
            // Simulate email sending
            Thread.sleep(2000);
            emailProvider.send(user.getEmail(), "Welcome!");
            return CompletableFuture.completedFuture(null);
        } catch (Exception e) {
            throw new EmailSendException("Failed to send email", e);
        }
    }
}

// Message Queue Processing
@RabbitListener(queues = "email.queue")
public void processEmailQueue(EmailMessage message) {
    emailService.sendEmail(message.getTo(), message.getSubject(), message.getBody());
}
```

---

## 📈 Profiling e Monitoring

### 🔸 **Application Profiling**

```java
// JProfiler Integration
@Component
public class PerformanceProfiler {
    
    @EventListener
    public void handleSlowQuery(SlowQueryEvent event) {
        if (event.getDuration() > Duration.ofSeconds(1)) {
            log.warn("Slow query detected: {} took {}ms", 
                event.getQuery(), event.getDuration().toMillis());
            
            // Send to monitoring system
            meterRegistry.timer("database.query.slow")
                .record(event.getDuration());
        }
    }
}

// Custom Performance Interceptor
@Component
public class PerformanceInterceptor implements HandlerInterceptor {
    
    @Override
    public boolean preHandle(HttpServletRequest request, 
                           HttpServletResponse response, 
                           Object handler) {
        request.setAttribute("startTime", System.currentTimeMillis());
        return true;
    }
    
    @Override
    public void afterCompletion(HttpServletRequest request, 
                              HttpServletResponse response, 
                              Object handler, Exception ex) {
        long startTime = (Long) request.getAttribute("startTime");
        long duration = System.currentTimeMillis() - startTime;
        
        if (duration > 1000) {
            log.warn("Slow request: {} took {}ms", 
                request.getRequestURI(), duration);
        }
    }
}
```

### 🔸 **Real-time Monitoring**

```yaml
# Prometheus + Grafana
management:
  endpoints:
    web:
      exposure:
        include: health,info,metrics,prometheus
  metrics:
    export:
      prometheus:
        enabled: true
    distribution:
      percentiles-histogram:
        http.server.requests: true
      percentiles:
        http.server.requests: 0.5, 0.95, 0.99
```

```java
// Custom Metrics
@Component
public class BusinessMetrics {
    
    private final Counter orderCounter;
    private final Timer orderProcessingTime;
    private final Gauge activeUsers;
    
    public BusinessMetrics(MeterRegistry meterRegistry) {
        this.orderCounter = Counter.builder("orders.total")
            .description("Total orders processed")
            .register(meterRegistry);
            
        this.orderProcessingTime = Timer.builder("orders.processing.time")
            .description("Order processing time")
            .register(meterRegistry);
            
        this.activeUsers = Gauge.builder("users.active")
            .description("Currently active users")
            .register(meterRegistry, this, BusinessMetrics::getActiveUserCount);
    }
    
    public void recordOrder() {
        orderCounter.increment();
    }
    
    public void recordOrderProcessingTime(Duration duration) {
        orderProcessingTime.record(duration);
    }
    
    private double getActiveUserCount() {
        return userService.getActiveUserCount();
    }
}
```

---

## 🔧 Load Testing

### 🔸 **JMeter Test Plan**

```xml
<!-- JMeter Test Plan -->
<jmeterTestPlan version="1.2">
    <hashTree>
        <TestPlan testname="API Load Test">
            <elementProp name="TestPlan.arguments" elementType="Arguments" guiclass="ArgumentsPanel">
                <collectionProp name="Arguments.arguments">
                    <elementProp name="baseUrl" elementType="Argument">
                        <stringProp name="Argument.name">baseUrl</stringProp>
                        <stringProp name="Argument.value">http://localhost:8080</stringProp>
                    </elementProp>
                </collectionProp>
            </elementProp>
        </TestPlan>
        
        <ThreadGroup testname="User Load">
            <stringProp name="ThreadGroup.num_threads">100</stringProp>
            <stringProp name="ThreadGroup.ramp_time">60</stringProp>
            <stringProp name="ThreadGroup.duration">300</stringProp>
        </ThreadGroup>
    </hashTree>
</jmeterTestPlan>
```

### 🔸 **K6 Load Testing**

```javascript
// K6 Performance Test
import http from 'k6/http';
import { check, sleep } from 'k6';

export let options = {
    stages: [
        { duration: '2m', target: 100 }, // Ramp up
        { duration: '5m', target: 100 }, // Stay at 100 users
        { duration: '2m', target: 200 }, // Ramp up to 200
        { duration: '5m', target: 200 }, // Stay at 200
        { duration: '2m', target: 0 },   // Ramp down
    ],
    thresholds: {
        http_req_duration: ['p(95)<500'], // 95% of requests under 500ms
        http_req_failed: ['rate<0.1'],    // Error rate under 10%
    },
};

export default function() {
    // Test user creation
    let createResponse = http.post('http://localhost:8080/api/users', {
        name: 'Test User',
        email: `test${Math.random()}@email.com`,
        password: 'password123'
    });
    
    check(createResponse, {
        'user created': (r) => r.status === 201,
        'response time < 500ms': (r) => r.timings.duration < 500,
    });
    
    if (createResponse.status === 201) {
        let userId = createResponse.json().id;
        
        // Test user retrieval
        let getResponse = http.get(`http://localhost:8080/api/users/${userId}`);
        
        check(getResponse, {
            'user retrieved': (r) => r.status === 200,
            'response time < 200ms': (r) => r.timings.duration < 200,
        });
    }
    
    sleep(1);
}
```

---

## 🎯 Preparação para Entrevistas

### ✅ **Perguntas Frequentes**

#### **Performance Bottlenecks**
**P:** "Como você identificaria gargalos de performance em uma aplicação?"
**R:**
1. **Monitoring:** APM tools (New Relic, DataDog)
2. **Profiling:** CPU, memory, I/O analysis
3. **Database:** Slow query logs, explain plans
4. **Network:** Latency, bandwidth analysis
5. **Load testing:** Stress testing com K6/JMeter

#### **Optimization Strategies**
**P:** "Sua API está lenta. Como você otimizaria?"
**R:**
1. **Identify:** Profile e encontrar gargalo
2. **Database:** Índices, query optimization
3. **Caching:** Redis, application cache
4. **Async:** Background processing
5. **CDN:** Static assets
6. **Load balancing:** Distribuir carga

#### **Caching Strategy**
**P:** "Explique diferentes níveis de cache"
**R:**
- **Browser cache:** Static assets
- **CDN:** Geographic distribution
- **Application cache:** In-memory (Caffeine)
- **Distributed cache:** Redis/Memcached
- **Database cache:** Query result cache

---

## 📈 Roadmap de Aprendizado

### 🥇 **Nível Iniciante (2-4 semanas)**
1. Métricas básicas de performance
2. Browser DevTools
3. Database indexing
4. Basic caching

### 🥈 **Nível Intermediário (1-2 meses)**
1. APM tools (New Relic, DataDog)
2. Load testing (K6, JMeter)
3. Database optimization
4. Caching strategies

### 🥉 **Nível Avançado (2-3 meses)**
1. Advanced profiling
2. Distributed systems performance
3. Custom monitoring solutions
4. Performance architecture

---

## 🎓 Conclusão

Performance Optimization é **crucial** porque:

- ✅ **User Experience** - Velocidade impacta satisfação
- ✅ **Business Impact** - Performance = revenue
- ✅ **Scalability** - Otimização permite crescimento
- ✅ **Cost Efficiency** - Menos recursos, menor custo
- ✅ **Competitive Advantage** - Diferencial no mercado

**Lembre-se:** Meça primeiro, otimize depois! 📊

---

*Desenvolvido para preparação em entrevistas técnicas e crescimento profissional em Performance Optimization.*
