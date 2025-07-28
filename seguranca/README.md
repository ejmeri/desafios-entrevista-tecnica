# 🔐 Segurança para Desenvolvedores

[![Security](https://img.shields.io/badge/focus-Security-red.svg)]()
[![OWASP](https://img.shields.io/badge/standard-OWASP-blue.svg)](https://owasp.org/)
[![Best Practices](https://img.shields.io/badge/practices-Security-green.svg)]()

> Guia essencial de segurança em desenvolvimento para entrevistas técnicas e aplicações robustas.

---

## 🎯 Por que Segurança é Fundamental?

### 💼 **Importância Profissional**

- **Requisito obrigatório** - 100% das empresas exigem
- **Responsabilidade legal** - LGPD, GDPR, compliance
- **Diferencial competitivo** - conhecimento valorizado
- **Carreira especializada** - Security Engineer, DevSecOps

### 🚨 **Impacto dos Ataques**

- **Financeiro** - Multas de milhões (LGPD: até 2% do faturamento)
- **Reputacional** - Perda de confiança dos clientes
- **Operacional** - Downtime e recuperação custosa
- **Legal** - Processos e responsabilização

### 🔧 **Para Desenvolvedores**

- **Secure by design** - segurança desde o início
- **Shift-left security** - testes de segurança no CI/CD
- **Threat modeling** - identificar riscos na arquitetura
- **Incident response** - como reagir a incidentes

---

## 🛡️ OWASP Top 10 - Vulnerabilidades Críticas

### 🔸 **A01: Broken Access Control**

**Problema:** Usuários acessam recursos sem autorização adequada

**Exemplos:**

```java
// ❌ Vulnerável - sem verificação de autorização
@GetMapping("/user/{id}/orders")
public List<Order> getUserOrders(@PathVariable Long id) {
    return orderService.findByUserId(id); // Qualquer um pode ver pedidos de outros
}

// ✅ Seguro - verificação de autorização
@GetMapping("/user/{id}/orders")
public List<Order> getUserOrders(@PathVariable Long id, Authentication auth) {
    User currentUser = (User) auth.getPrincipal();

    // Verificar se o usuário pode acessar esses pedidos
    if (!currentUser.getId().equals(id) && !currentUser.hasRole("ADMIN")) {
        throw new AccessDeniedException("Acesso negado");
    }

    return orderService.findByUserId(id);
}
```

**Prevenção:**

- Implementar controle de acesso em todas as rotas
- Princípio do menor privilégio
- Testes automatizados de autorização

---

### 🔸 **A02: Cryptographic Failures**

**Problema:** Dados sensíveis expostos por criptografia inadequada

**Exemplos:**

```java
// ❌ Vulnerável - senha em texto plano
@Entity
public class User {
    private String password; // Armazenada sem hash
}

// ✅ Seguro - hash com salt
@Entity
public class User {
    private String passwordHash;

    public void setPassword(String plainPassword) {
        // BCrypt com salt automático
        this.passwordHash = BCrypt.hashpw(plainPassword, BCrypt.gensalt(12));
    }

    public boolean checkPassword(String plainPassword) {
        return BCrypt.checkpw(plainPassword, this.passwordHash);
    }
}

// ❌ Vulnerável - dados sensíveis em logs
logger.info("User login: " + user.getEmail() + " with password: " + password);

// ✅ Seguro - logs sanitizados
logger.info("User login attempt: " + user.getEmail().replaceAll("(.{2}).*@", "$1***@"));
```

**Prevenção:**

- Usar algoritmos seguros (BCrypt, Argon2, PBKDF2)
- HTTPS obrigatório para dados sensíveis
- Criptografia de dados em repouso

---

### 🔸 **A03: Injection**

**Problema:** Dados não validados executados como código

**SQL Injection:**

```java
// ❌ Vulnerável - concatenação de strings
public User findByEmail(String email) {
    String sql = "SELECT * FROM users WHERE email = '" + email + "'";
    // Ataque: email = "'; DROP TABLE users; --"
    return jdbcTemplate.queryForObject(sql, User.class);
}

// ✅ Seguro - prepared statements
public User findByEmail(String email) {
    String sql = "SELECT * FROM users WHERE email = ?";
    return jdbcTemplate.queryForObject(sql, User.class, email);
}

// ✅ Ainda melhor - JPA/Hibernate
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email); // Query automática e segura
}
```

**NoSQL Injection (MongoDB):**

```javascript
// ❌ Vulnerável
const user = await User.findOne({
  email: req.body.email,
  password: req.body.password,
});
// Ataque: { "password": { "$ne": null } }

// ✅ Seguro - validação e sanitização
const { email, password } = req.body;

// Validar tipos
if (typeof email !== "string" || typeof password !== "string") {
  throw new Error("Invalid input types");
}

const user = await User.findOne({ email });
if (user && (await bcrypt.compare(password, user.passwordHash))) {
  // Login válido
}
```

---

### 🔸 **A04: Insecure Design**

**Problema:** Falhas fundamentais na arquitetura de segurança

**Exemplos de Design Inseguro:**

```java
// ❌ Vulnerável - reset de senha sem rate limiting
@PostMapping("/forgot-password")
public ResponseEntity<?> forgotPassword(@RequestBody String email) {
    // Sem limite de tentativas - permite ataques de força bruta
    userService.sendPasswordResetEmail(email);
    return ResponseEntity.ok("Email enviado");
}

// ✅ Seguro - com rate limiting e validação
@PostMapping("/forgot-password")
@RateLimited(maxAttempts = 3, timeWindow = "1h")
public ResponseEntity<?> forgotPassword(@RequestBody @Valid EmailRequest request) {

    // Validar formato do email
    if (!EmailValidator.isValid(request.getEmail())) {
        throw new BadRequestException("Email inválido");
    }

    // Rate limiting por IP e email
    rateLimitService.checkLimit(request.getRemoteAddr(), request.getEmail());

    // Sempre retornar sucesso (não vazar se email existe)
    userService.sendPasswordResetEmailIfExists(request.getEmail());

    return ResponseEntity.ok("Se o email existir, você receberá instruções");
}
```

**Threat Modeling:**

```
1. Identificar assets (dados de usuários, APIs, etc.)
2. Identificar threats (STRIDE):
   - Spoofing (falsificação de identidade)
   - Tampering (alteração de dados)
   - Repudiation (negação de ações)
   - Information Disclosure (vazamento)
   - Denial of Service (indisponibilidade)
   - Elevation of Privilege (escalação)
3. Implementar controles
4. Validar efetividade
```

---

### 🔸 **A05: Security Misconfiguration**

**Problema:** Configurações inseguras em produção

**Exemplos:**

```yaml
# ❌ Vulnerável - application.yml em produção
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/mydb
    username: postgres
    password: 123456  # Senha fraca e hardcoded
  jpa:
    show-sql: true    # Logs SQL em produção
    hibernate:
      ddl-auto: create-drop  # Recria BD em produção!
  security:
    debug: true       # Debug de segurança ativo

# ✅ Seguro - configuração para produção
spring:
  datasource:
    url: ${DB_URL}
    username: ${DB_USERNAME}
    password: ${DB_PASSWORD}  # Variáveis de ambiente
  jpa:
    show-sql: false
    hibernate:
      ddl-auto: validate      # Apenas validar schema
  security:
    debug: false

# Headers de segurança
server:
  servlet:
    session:
      cookie:
        secure: true          # HTTPS only
        http-only: true       # Não acessível via JS
        same-site: strict     # CSRF protection
```

**Docker Security:**

```dockerfile
# ❌ Vulnerável
FROM ubuntu:latest
USER root
COPY . /app
RUN chmod 777 /app
EXPOSE 8080

# ✅ Seguro
FROM openjdk:17-jre-slim
RUN groupadd -r appuser && useradd -r -g appuser appuser
COPY --chown=appuser:appuser target/app.jar /app/app.jar
USER appuser
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
```

---

## 🔑 Autenticação e Autorização

### 🔸 **JWT (JSON Web Tokens)**

**Implementação Segura:**

```java
@Service
public class JwtService {

    // ❌ Vulnerável - chave fraca
    // private final String SECRET = "mySecret";

    // ✅ Seguro - chave forte e configurável
    @Value("${jwt.secret}")
    private String jwtSecret; // Mínimo 256 bits

    @Value("${jwt.expiration:3600}") // 1 hora padrão
    private int jwtExpiration;

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", userDetails.getAuthorities());

        return Jwts.builder()
            .setClaims(claims)
            .setSubject(userDetails.getUsername())
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration * 1000))
            .signWith(SignatureAlgorithm.HS512, jwtSecret) // Algoritmo forte
            .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            logger.warn("Invalid JWT token: " + e.getMessage());
            return false;
        }
    }
}

// Configuração de segurança
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
            .csrf(csrf -> csrf.disable()) // Para APIs REST
            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers("/api/admin/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/products/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/products/**").hasRole("MANAGER")
                .anyRequest().authenticated()
            )
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
            .build();
    }
}
```

### 🔸 **OAuth 2.0 / OpenID Connect**

**Configuração Spring Security:**

```java
@Configuration
public class OAuth2Config {

    @Bean
    public SecurityFilterChain oauth2FilterChain(HttpSecurity http) throws Exception {
        return http
            .oauth2Login(oauth2 -> oauth2
                .loginPage("/login")
                .defaultSuccessUrl("/dashboard")
                .userInfoEndpoint(userInfo -> userInfo
                    .userService(customOAuth2UserService)
                )
            )
            .oauth2ResourceServer(oauth2 -> oauth2
                .jwt(jwt -> jwt
                    .jwtAuthenticationConverter(jwtAuthenticationConverter())
                )
            )
            .build();
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter authoritiesConverter =
            new JwtGrantedAuthoritiesConverter();
        authoritiesConverter.setAuthorityPrefix("ROLE_");
        authoritiesConverter.setAuthoritiesClaimName("roles");

        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(authoritiesConverter);
        return converter;
    }
}
```

---

## 🛡️ Proteção contra Ataques Comuns

### 🔸 **CSRF (Cross-Site Request Forgery)**

```java
// Configuração CSRF
@Configuration
public class CsrfConfig {

    @Bean
    public CsrfTokenRepository csrfTokenRepository() {
        HttpSessionCsrfTokenRepository repository =
            new HttpSessionCsrfTokenRepository();
        repository.setHeaderName("X-XSRF-TOKEN");
        return repository;
    }

    @Bean
    public SecurityFilterChain csrfFilterChain(HttpSecurity http) throws Exception {
        return http
            .csrf(csrf -> csrf
                .csrfTokenRepository(csrfTokenRepository())
                .ignoringRequestMatchers("/api/public/**") // APIs públicas
            )
            .build();
    }
}

// Frontend - incluir token CSRF
// JavaScript
const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');

fetch('/api/protected', {
    method: 'POST',
    headers: {
        'Content-Type': 'application/json',
        [csrfHeader]: csrfToken
    },
    body: JSON.stringify(data)
});
```

### 🔸 **XSS (Cross-Site Scripting)**

```java
// Sanitização de entrada
@Component
public class InputSanitizer {

    private final Policy policy;

    public InputSanitizer() {
        this.policy = new HtmlPolicyBuilder()
            .allowElements("p", "br", "strong", "em")
            .allowAttributes("class").onElements("p")
            .toFactory();
    }

    public String sanitizeHtml(String input) {
        if (input == null) return null;
        return policy.sanitize(input);
    }

    public String escapeHtml(String input) {
        if (input == null) return null;
        return StringEscapeUtils.escapeHtml4(input);
    }
}

// Controller com validação
@RestController
public class CommentController {

    @PostMapping("/comments")
    public ResponseEntity<Comment> createComment(
            @Valid @RequestBody CommentRequest request) {

        // Sanitizar conteúdo HTML
        String sanitizedContent = inputSanitizer.sanitizeHtml(request.getContent());

        Comment comment = new Comment();
        comment.setContent(sanitizedContent);
        comment.setAuthor(getCurrentUser());

        return ResponseEntity.ok(commentService.save(comment));
    }
}

// Validação de entrada
public class CommentRequest {

    @NotBlank(message = "Conteúdo é obrigatório")
    @Size(max = 1000, message = "Conteúdo muito longo")
    @Pattern(regexp = "^[\\p{L}\\p{N}\\p{P}\\p{Z}]*$",
             message = "Caracteres inválidos detectados")
    private String content;

    // getters/setters
}
```

### 🔸 **Rate Limiting**

```java
@Component
public class RateLimitingService {

    private final RedisTemplate<String, String> redisTemplate;

    public boolean isAllowed(String key, int maxRequests, Duration window) {
        String redisKey = "rate_limit:" + key;

        // Sliding window com Redis
        long now = System.currentTimeMillis();
        long windowStart = now - window.toMillis();

        // Remover entradas antigas
        redisTemplate.opsForZSet().removeRangeByScore(redisKey, 0, windowStart);

        // Contar requests atuais
        Long currentRequests = redisTemplate.opsForZSet().count(redisKey, windowStart, now);

        if (currentRequests >= maxRequests) {
            return false; // Rate limit excedido
        }

        // Adicionar request atual
        redisTemplate.opsForZSet().add(redisKey, UUID.randomUUID().toString(), now);
        redisTemplate.expire(redisKey, window);

        return true;
    }
}

// Interceptor para aplicar rate limiting
@Component
public class RateLimitInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
                           HttpServletResponse response,
                           Object handler) throws Exception {

        String clientIp = getClientIp(request);
        String endpoint = request.getRequestURI();
        String key = clientIp + ":" + endpoint;

        // 100 requests por minuto por IP/endpoint
        if (!rateLimitingService.isAllowed(key, 100, Duration.ofMinutes(1))) {
            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            response.getWriter().write("Rate limit exceeded");
            return false;
        }

        return true;
    }
}
```

---

## 🔍 Security Testing

### 🔸 **Testes Automatizados**

```java
@SpringBootTest
@AutoConfigureTestDatabase
class SecurityTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void shouldPreventSqlInjection() {
        // Tentar SQL injection
        String maliciousEmail = "'; DROP TABLE users; --";

        ResponseEntity<String> response = restTemplate.postForEntity(
            "/api/login",
            new LoginRequest(maliciousEmail, "password"),
            String.class
        );

        // Deve retornar erro de validação, não erro de SQL
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).contains("Email inválido");
    }

    @Test
    void shouldEnforceRateLimit() {
        String endpoint = "/api/forgot-password";

        // Fazer 3 requests (limite)
        for (int i = 0; i < 3; i++) {
            ResponseEntity<String> response = restTemplate.postForEntity(
                endpoint,
                new EmailRequest("test@email.com"),
                String.class
            );
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        }

        // 4º request deve ser bloqueado
        ResponseEntity<String> response = restTemplate.postForEntity(
            endpoint,
            new EmailRequest("test@email.com"),
            String.class
        );
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.TOO_MANY_REQUESTS);
    }

    @Test
    void shouldPreventUnauthorizedAccess() {
        // Tentar acessar endpoint protegido sem token
        ResponseEntity<String> response = restTemplate.getForEntity(
            "/api/admin/users",
            String.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }
}
```

### 🔸 **Ferramentas de Security Scanning**

```yaml
# GitHub Actions - Security Pipeline
name: Security Scan
on: [push, pull_request]

jobs:
  security:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v3

      # SAST - Static Application Security Testing
      - name: Run CodeQL Analysis
        uses: github/codeql-action/init@v2
        with:
          languages: java

      - name: Run SonarQube Scan
        uses: sonarqube-quality-gate-action@master
        env:
          SONAR_TOKEN: ${{ secrets.SONAR_TOKEN }}

      # Dependency Scanning
      - name: Run OWASP Dependency Check
        uses: dependency-check/Dependency-Check_Action@main
        with:
          project: "my-project"
          path: "."
          format: "ALL"

      # Container Scanning
      - name: Run Trivy vulnerability scanner
        uses: aquasecurity/trivy-action@master
        with:
          image-ref: "my-app:latest"
          format: "sarif"
          output: "trivy-results.sarif"
```

---

## 🎯 Preparação para Entrevistas

### ✅ **Tópicos Essenciais**

#### **Fundamentos**

- OWASP Top 10 e como prevenir cada vulnerabilidade
- Diferença entre autenticação e autorização
- Princípios de segurança (CIA Triad, Defense in Depth)
- Criptografia simétrica vs assimétrica
- Hashing vs encryption

#### **Implementação**

- JWT vs Session-based authentication
- OAuth 2.0 flows (Authorization Code, Client Credentials)
- CORS e quando configurar
- HTTPS e certificados SSL/TLS
- Input validation e sanitization

#### **Arquitetura**

- Security headers (HSTS, CSP, X-Frame-Options)
- Rate limiting strategies
- Logging e monitoring de segurança
- Incident response procedures
- Compliance (LGPD, GDPR, SOX)

### 🗣️ **Perguntas Frequentes**

#### **Autenticação**

**P:** "Qual a diferença entre JWT e sessions?"
**R:**

- **Sessions:** Estado no servidor, mais seguro, fácil revogação
- **JWT:** Stateless, escalável, mas difícil revogação
- **Escolha:** Sessions para web apps, JWT para APIs/microserviços

#### **Vulnerabilidades**

**P:** "Como prevenir SQL Injection?"
**R:**

1. **Prepared statements** sempre
2. **Input validation** rigorosa
3. **Least privilege** para usuário do BD
4. **WAF** como camada adicional
5. **Code review** e testes automatizados

#### **Criptografia**

**P:** "Quando usar hash vs encryption?"
**R:**

- **Hash:** Senhas, integridade (irreversível)
- **Encryption:** Dados que precisam ser recuperados
- **Exemplos:** BCrypt para senhas, AES para dados sensíveis

#### **Compliance**

**P:** "Como implementar LGPD em uma API?"
**R:**

1. **Consentimento** explícito e granular
2. **Minimização** de dados coletados
3. **Direito ao esquecimento** (delete endpoints)
4. **Portabilidade** (export endpoints)
5. **Logs de auditoria** completos

---

## 📈 Roadmap de Aprendizado

### 🥇 **Nível Iniciante (1-2 meses)**

1. **Fundamentos**

   - OWASP Top 10
   - Autenticação vs Autorização
   - HTTPS básico
   - Input validation

2. **Implementação Básica**

   - Spring Security configuração
   - JWT implementation
   - Password hashing
   - Basic rate limiting

3. **Ferramentas**

   - Postman para testes de API
   - Browser dev tools
   - OWASP ZAP básico

### 🥈 **Nível Intermediário (2-4 meses)**

1. **Arquitetura Segura**

   - OAuth 2.0 / OpenID Connect
   - Security headers
   - CORS configuration
   - Logging de segurança

2. **Testing**

   - Security unit tests
   - Integration tests
   - SAST tools (SonarQube)
   - Dependency scanning

3. **Compliance**

   - LGPD/GDPR basics
   - Audit logging
   - Data encryption
   - Backup security

### 🥉 **Nível Avançado (4+ meses)**

1. **DevSecOps**

   - Security pipeline automation
   - Container security
   - Infrastructure as Code security
   - Threat modeling

2. **Incident Response**

   - Security monitoring
   - Log analysis
   - Forensics basics
   - Recovery procedures

3. **Especialização**

   - Penetration testing
   - Security architecture
   - Compliance frameworks
   - Security consulting

---

## 🔗 Recursos Recomendados

### 📚 **Documentação e Guias**

- **OWASP** - Guias e checklists
- **Spring Security Reference** - Documentação oficial
- **NIST Cybersecurity Framework** - Padrões de segurança
- **SANS Top 25** - Vulnerabilidades mais perigosas

### 🛠️ **Ferramentas**

- **OWASP ZAP** - Security testing
- **Burp Suite** - Web application testing
- **SonarQube** - Static analysis
- **Dependency-Check** - Vulnerability scanning

## 🎓 Conclusão

Segurança é **fundamental** para desenvolvedores porque:

- ✅ **Responsabilidade legal** - LGPD, GDPR, compliance obrigatório
- ✅ **Diferencial competitivo** - conhecimento valorizado no mercado
- ✅ **Proteção do negócio** - evita perdas financeiras e reputacionais
- ✅ **Carreira especializada** - Security Engineer, DevSecOps, Architect
- ✅ **Requisito básico** - todas as empresas exigem conhecimento

### 🚀 **Próximos Passos**

1. **Estude OWASP Top 10** - base fundamental
2. **Pratique implementações** - JWT, OAuth, rate limiting
3. **Configure ferramentas** - SonarQube, dependency scanning
4. **Faça testes** - security unit tests, penetration testing
5. **Mantenha-se atualizado** - vulnerabilidades evoluem constantemente

**Lembre-se:** Segurança não é um produto, é um processo contínuo! 🔐

---

_Desenvolvido para preparação em entrevistas técnicas e crescimento profissional em Segurança._
