# 🌐 RESTful APIs - Guia Completo

[![REST](https://img.shields.io/badge/architecture-REST-blue.svg)]()
[![HTTP](https://img.shields.io/badge/protocol-HTTP-green.svg)]()
[![JSON](https://img.shields.io/badge/format-JSON-orange.svg)]()

> Guia completo de APIs RESTful com implementação prática em Java Spring Boot para entrevistas técnicas.

---

## 🎯 Por que RESTful APIs são Fundamentais?

### 💼 **Importância Profissional**
- **Padrão da indústria** - 90% das APIs modernas são REST
- **Microserviços** - base para arquiteturas distribuídas
- **Integração** - comunicação entre sistemas diferentes
- **Mobile/Web** - backend para aplicações frontend

### 🚀 **Benefícios Técnicos**
- **Simplicidade** - usa HTTP padrão
- **Escalabilidade** - stateless por design
- **Flexibilidade** - múltiplos formatos (JSON, XML)
- **Cacheable** - melhora performance
- **Interoperabilidade** - funciona em qualquer plataforma

### 🔧 **Para Entrevistas Técnicas**
- **Perguntas obrigatórias** - design de APIs, status codes
- **System design** - como APIs se integram na arquitetura
- **Best practices** - versionamento, documentação, segurança
- **Implementação** - código prático e funcional

---

## 📋 Princípios REST (Representational State Transfer)

### 🔸 **1. Client-Server Architecture**
```
Separação clara de responsabilidades:

Client (Frontend):
├── Interface do usuário
├── Experiência do usuário
├── Apresentação dos dados
└── Interação com usuário

Server (Backend):
├── Lógica de negócio
├── Persistência de dados
├── Segurança e autorização
└── Processamento de dados

Benefícios:
✅ Desenvolvimento independente
✅ Escalabilidade separada
✅ Tecnologias diferentes
✅ Evolução independente
```

### 🔸 **2. Stateless**
```
Cada request contém toda informação necessária:

❌ Stateful (não REST):
Request 1: login(user, password) → session_id
Request 2: getProfile() → usa session_id do servidor
Request 3: updateProfile(data) → usa session_id do servidor

✅ Stateless (REST):
Request 1: POST /auth/login → retorna JWT token
Request 2: GET /users/profile + Authorization: Bearer <token>
Request 3: PUT /users/profile + Authorization: Bearer <token>

Benefícios:
✅ Escalabilidade horizontal
✅ Simplicidade de implementação
✅ Confiabilidade (sem estado perdido)
✅ Facilita load balancing
```

### 🔸 **3. Cacheable**
```
Responses devem indicar se podem ser cacheadas:

Headers de Cache:
Cache-Control: max-age=3600, public
ETag: "33a64df551425fcc55e4d42a148795d9f25f89d4"
Last-Modified: Wed, 21 Oct 2015 07:28:00 GMT

Exemplo prático:
GET /api/products/123
Response:
{
  "id": 123,
  "name": "Smartphone",
  "price": 999.99,
  "cache-control": "max-age=3600"
}

Benefícios:
✅ Reduz latência
✅ Diminui carga no servidor
✅ Melhora experiência do usuário
✅ Economiza bandwidth
```

### 🔸 **4. Uniform Interface**
```
Interface consistente com 4 constraints:

1. Identificação de recursos via URI
   GET /api/users/123
   GET /api/products/456

2. Manipulação via representações
   JSON, XML, HTML

3. Mensagens auto-descritivas
   HTTP methods, status codes, headers

4. HATEOAS (Hypermedia as the Engine of Application State)
   Links para ações relacionadas
```

### 🔸 **5. Layered System**
```
Arquitetura em camadas:

Client → Load Balancer → API Gateway → Microservice → Database

Cada camada só conhece a próxima:
- Client não sabe sobre load balancer
- API Gateway não sabe sobre database
- Microservice não sabe sobre client

Benefícios:
✅ Segurança (firewalls, proxies)
✅ Escalabilidade (cache layers)
✅ Flexibilidade (mudanças internas)
✅ Reutilização (shared gateways)
```

### 🔸 **6. Code on Demand (Opcional)**
```
Servidor pode enviar código executável:

Exemplos:
- JavaScript para browsers
- Applets Java (histórico)
- WebAssembly modules

Raramente usado em APIs REST modernas.
```

---

## 🛠️ HTTP Methods e Semântica

### 🔸 **CRUD Operations**

| HTTP Method | CRUD | Idempotente | Safe | Uso |
|-------------|------|-------------|------|-----|
| **GET** | Read | ✅ | ✅ | Buscar dados |
| **POST** | Create | ❌ | ❌ | Criar recursos |
| **PUT** | Update | ✅ | ❌ | Atualizar completo |
| **PATCH** | Update | ❌ | ❌ | Atualizar parcial |
| **DELETE** | Delete | ✅ | ❌ | Remover recursos |

### 🔸 **Exemplos Práticos**

#### **GET - Buscar Dados**
```http
# Buscar todos os usuários
GET /api/users
Response: 200 OK
[
  {"id": 1, "name": "João", "email": "joao@email.com"},
  {"id": 2, "name": "Maria", "email": "maria@email.com"}
]

# Buscar usuário específico
GET /api/users/1
Response: 200 OK
{"id": 1, "name": "João", "email": "joao@email.com", "createdAt": "2024-01-01"}

# Buscar com filtros
GET /api/users?status=active&page=1&size=10
Response: 200 OK
{
  "content": [...],
  "totalElements": 50,
  "totalPages": 5,
  "currentPage": 1
}

# Recurso não encontrado
GET /api/users/999
Response: 404 Not Found
{"error": "User not found", "code": "USER_NOT_FOUND"}
```

#### **POST - Criar Recursos**
```http
# Criar novo usuário
POST /api/users
Content-Type: application/json
{
  "name": "Pedro",
  "email": "pedro@email.com",
  "password": "senha123"
}

Response: 201 Created
Location: /api/users/3
{
  "id": 3,
  "name": "Pedro",
  "email": "pedro@email.com",
  "createdAt": "2024-01-15T10:30:00Z"
}

# Dados inválidos
POST /api/users
{
  "name": "",
  "email": "email-inválido"
}

Response: 400 Bad Request
{
  "error": "Validation failed",
  "details": [
    {"field": "name", "message": "Name is required"},
    {"field": "email", "message": "Invalid email format"}
  ]
}
```

#### **PUT - Atualizar Completo**
```http
# Atualizar usuário completo
PUT /api/users/1
Content-Type: application/json
{
  "name": "João Silva",
  "email": "joao.silva@email.com",
  "status": "active"
}

Response: 200 OK
{
  "id": 1,
  "name": "João Silva",
  "email": "joao.silva@email.com",
  "status": "active",
  "updatedAt": "2024-01-15T11:00:00Z"
}

# Criar se não existir (upsert)
PUT /api/users/999
Response: 201 Created (se criado) ou 200 OK (se atualizado)
```

#### **PATCH - Atualizar Parcial**
```http
# Atualizar apenas campos específicos
PATCH /api/users/1
Content-Type: application/json
{
  "status": "inactive"
}

Response: 200 OK
{
  "id": 1,
  "name": "João Silva",
  "email": "joao.silva@email.com",
  "status": "inactive",
  "updatedAt": "2024-01-15T11:30:00Z"
}

# JSON Patch (RFC 6902)
PATCH /api/users/1
Content-Type: application/json-patch+json
[
  {"op": "replace", "path": "/status", "value": "active"},
  {"op": "add", "path": "/phone", "value": "+5511999999999"}
]
```

#### **DELETE - Remover Recursos**
```http
# Deletar usuário
DELETE /api/users/1
Response: 204 No Content

# Deletar recurso que não existe
DELETE /api/users/999
Response: 404 Not Found

# Soft delete (marcar como inativo)
DELETE /api/users/1
Response: 200 OK
{
  "id": 1,
  "status": "deleted",
  "deletedAt": "2024-01-15T12:00:00Z"
}
```

---

## 📊 HTTP Status Codes

### 🔸 **Códigos Essenciais**

#### **2xx Success**
```http
200 OK - Request bem-sucedido
201 Created - Recurso criado com sucesso
202 Accepted - Request aceito para processamento assíncrono
204 No Content - Sucesso sem conteúdo de resposta
```

#### **3xx Redirection**
```http
301 Moved Permanently - Recurso movido permanentemente
302 Found - Redirecionamento temporário
304 Not Modified - Recurso não modificado (cache válido)
```

#### **4xx Client Error**
```http
400 Bad Request - Dados inválidos enviados pelo cliente
401 Unauthorized - Autenticação necessária
403 Forbidden - Acesso negado (autorização)
404 Not Found - Recurso não encontrado
405 Method Not Allowed - Método HTTP não permitido
409 Conflict - Conflito com estado atual do recurso
422 Unprocessable Entity - Dados válidos mas não processáveis
429 Too Many Requests - Rate limit excedido
```

#### **5xx Server Error**
```http
500 Internal Server Error - Erro interno do servidor
502 Bad Gateway - Gateway inválido
503 Service Unavailable - Serviço temporariamente indisponível
504 Gateway Timeout - Timeout do gateway
```

### 🔸 **Uso Correto dos Status Codes**

```java
@RestController
@RequestMapping("/api/users")
public class UserController {

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        Optional<User> user = userService.findById(id);

        if (user.isPresent()) {
            return ResponseEntity.ok(user.get()); // 200 OK
        } else {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
    }

    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody CreateUserRequest request) {
        try {
            User user = userService.create(request);
            URI location = URI.create("/api/users/" + user.getId());
            return ResponseEntity.created(location).body(user); // 201 Created
        } catch (EmailAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ErrorResponse("Email already exists")); // 409 Conflict
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id,
                                         @Valid @RequestBody UpdateUserRequest request) {
        try {
            User user = userService.update(id, request);
            return ResponseEntity.ok(user); // 200 OK
        } catch (UserNotFoundException e) {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        try {
            userService.delete(id);
            return ResponseEntity.noContent().build(); // 204 No Content
        } catch (UserNotFoundException e) {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
    }
}
```

---

## 🏗️ Exemplo Prático - API de Usuários

### 📁 **Estrutura do Projeto**

```
restful-api/exemplo-pratico/
├── pom.xml
├── src/main/java/com/example/restapi/
│   ├── RestApiApplication.java
│   ├── controller/
│   │   ├── UserController.java
│   │   └── GlobalExceptionHandler.java
│   ├── service/
│   │   ├── UserService.java
│   │   └── UserServiceImpl.java
│   ├── repository/
│   │   └── UserRepository.java
│   ├── model/
│   │   └── User.java
│   ├── dto/
│   │   ├── CreateUserRequest.java
│   │   ├── UpdateUserRequest.java
│   │   ├── UserResponse.java
│   │   └── ErrorResponse.java
│   └── config/
│       ├── OpenApiConfig.java
│       └── SecurityConfig.java
└── src/test/java/
    └── com/example/restapi/
        ├── controller/
        │   └── UserControllerTest.java
        └── integration/
            └── UserIntegrationTest.java
```

### 🔸 **Controller RESTful Completo**

```java
@RestController
@RequestMapping("/api/v1/users")
@Validated
@Tag(name = "Users", description = "API para gerenciamento de usuários")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @Operation(summary = "Listar usuários", description = "Retorna lista paginada de usuários")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de usuários retornada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Parâmetros de paginação inválidos")
    })
    public ResponseEntity<Page<UserResponse>> getAllUsers(
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "10") @Min(1) @Max(100) int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir,
            @RequestParam(required = false) String status) {

        Pageable pageable = PageRequest.of(page, size,
            Sort.by(Sort.Direction.fromString(sortDir), sortBy));

        Page<UserResponse> users = userService.findAll(pageable, status);

        return ResponseEntity.ok()
            .header("X-Total-Count", String.valueOf(users.getTotalElements()))
            .body(users);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar usuário por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Usuário encontrado"),
        @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        UserResponse user = userService.findById(id);
        return ResponseEntity.ok(user);
    }

    @PostMapping
    @Operation(summary = "Criar novo usuário")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "409", description = "Email já existe")
    })
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody CreateUserRequest request) {
        UserResponse user = userService.create(request);

        URI location = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(user.getId())
            .toUri();

        return ResponseEntity.created(location).body(user);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar usuário completo")
    public ResponseEntity<UserResponse> updateUser(@PathVariable Long id,
                                                  @Valid @RequestBody UpdateUserRequest request) {
        UserResponse user = userService.update(id, request);
        return ResponseEntity.ok(user);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Atualizar usuário parcial")
    public ResponseEntity<UserResponse> patchUser(@PathVariable Long id,
                                                 @RequestBody UpdateUserRequest request) {
        UserResponse user = userService.patch(id, request);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar usuário")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Usuário deletado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/exists")
    @Operation(summary = "Verificar se usuário existe")
    public ResponseEntity<Void> checkUserExists(@PathVariable Long id) {
        boolean exists = userService.exists(id);
        return exists ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }
}
```

### 🔸 **Service Layer**

```java
@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserResponse> findAll(Pageable pageable, String status) {
        Page<User> users;

        if (status != null) {
            UserStatus userStatus = UserStatus.valueOf(status.toUpperCase());
            users = userRepository.findByStatus(userStatus, pageable);
        } else {
            users = userRepository.findAll(pageable);
        }

        return users.map(UserResponse::from);
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse findById(Long id) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new UserNotFoundException("Usuário não encontrado com ID: " + id));
        return UserResponse.from(user);
    }

    @Override
    public UserResponse create(CreateUserRequest request) {
        // Verificar se email já existe
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException("Email já está em uso: " + request.getEmail());
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setStatus(UserStatus.ACTIVE);

        User savedUser = userRepository.save(user);
        return UserResponse.from(savedUser);
    }

    @Override
    public UserResponse update(Long id, UpdateUserRequest request) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new UserNotFoundException("Usuário não encontrado com ID: " + id));

        // Verificar email único se estiver sendo alterado
        if (request.hasEmail() && !request.getEmail().equals(user.getEmail())) {
            if (userRepository.existsByEmail(request.getEmail())) {
                throw new EmailAlreadyExistsException("Email já está em uso: " + request.getEmail());
            }
        }

        // Atualizar todos os campos (PUT = replace completo)
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        if (request.hasPassword()) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }
        if (request.hasStatus()) {
            user.setStatus(request.getStatus());
        }

        User savedUser = userRepository.save(user);
        return UserResponse.from(savedUser);
    }

    @Override
    public UserResponse patch(Long id, UpdateUserRequest request) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new UserNotFoundException("Usuário não encontrado com ID: " + id));

        // Atualizar apenas campos fornecidos (PATCH = update parcial)
        if (request.hasName()) {
            user.setName(request.getName());
        }

        if (request.hasEmail()) {
            if (userRepository.existsByEmail(request.getEmail())) {
                throw new EmailAlreadyExistsException("Email já está em uso: " + request.getEmail());
            }
            user.setEmail(request.getEmail());
        }

        if (request.hasPassword()) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        if (request.hasStatus()) {
            user.setStatus(request.getStatus());
        }

        User savedUser = userRepository.save(user);
        return UserResponse.from(savedUser);
    }

    @Override
    public void delete(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException("Usuário não encontrado com ID: " + id);
        }
        userRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean exists(Long id) {
        return userRepository.existsById(id);
    }
}
```

---

## 🔧 Best Practices Implementadas

### 🔸 **1. Versionamento de API**

```java
// URL Versioning (mais comum)
@RequestMapping("/api/v1/users")
@RequestMapping("/api/v2/users")

// Header Versioning
@GetMapping(value = "/users", headers = "API-Version=1")
@GetMapping(value = "/users", headers = "API-Version=2")

// Media Type Versioning
@GetMapping(value = "/users", produces = "application/vnd.api.v1+json")
@GetMapping(value = "/users", produces = "application/vnd.api.v2+json")
```

### 🔸 **2. Paginação e Filtros**

```java
@GetMapping
public ResponseEntity<Page<UserResponse>> getUsers(
    @RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "10") int size,
    @RequestParam(defaultValue = "id") String sortBy,
    @RequestParam(defaultValue = "asc") String sortDir,
    @RequestParam(required = false) String name,
    @RequestParam(required = false) String email,
    @RequestParam(required = false) String status) {

    // Implementação com Specification para filtros dinâmicos
    Specification<User> spec = Specification.where(null);

    if (name != null) {
        spec = spec.and((root, query, cb) ->
            cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
    }

    if (email != null) {
        spec = spec.and((root, query, cb) ->
            cb.like(cb.lower(root.get("email")), "%" + email.toLowerCase() + "%"));
    }

    if (status != null) {
        spec = spec.and((root, query, cb) ->
            cb.equal(root.get("status"), UserStatus.valueOf(status.toUpperCase())));
    }

    Pageable pageable = PageRequest.of(page, size,
        Sort.by(Sort.Direction.fromString(sortDir), sortBy));

    Page<User> users = userRepository.findAll(spec, pageable);
    Page<UserResponse> response = users.map(UserResponse::from);

    return ResponseEntity.ok()
        .header("X-Total-Count", String.valueOf(response.getTotalElements()))
        .header("X-Total-Pages", String.valueOf(response.getTotalPages()))
        .body(response);
}
```

### 🔸 **3. Tratamento Global de Erros**

```java
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFound(UserNotFoundException ex) {
        ErrorResponse error = new ErrorResponse(
            "USER_NOT_FOUND",
            ex.getMessage(),
            HttpStatus.NOT_FOUND.value(),
            Instant.now()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleEmailAlreadyExists(EmailAlreadyExistsException ex) {
        ErrorResponse error = new ErrorResponse(
            "EMAIL_ALREADY_EXISTS",
            ex.getMessage(),
            HttpStatus.CONFLICT.value(),
            Instant.now()
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleValidationErrors(
            MethodArgumentNotValidException ex) {

        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        Map<String, String> errors = fieldErrors.stream()
            .collect(Collectors.toMap(
                FieldError::getField,
                FieldError::getDefaultMessage,
                (existing, replacement) -> existing
            ));

        ValidationErrorResponse error = new ValidationErrorResponse(
            "VALIDATION_FAILED",
            "Dados de entrada inválidos",
            HttpStatus.BAD_REQUEST.value(),
            Instant.now(),
            errors
        );

        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        ErrorResponse error = new ErrorResponse(
            "INTERNAL_SERVER_ERROR",
            "Erro interno do servidor",
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            Instant.now()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}
```

---

## 📖 Documentação com OpenAPI/Swagger

### 🔸 **Configuração OpenAPI**

```java
@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "RESTful API Example",
        version = "1.0.0",
        description = "API RESTful completa seguindo melhores práticas",
        contact = @Contact(
            name = "Equipe de Desenvolvimento",
            email = "dev@example.com"
        ),
        license = @License(
            name = "MIT License",
            url = "https://opensource.org/licenses/MIT"
        )
    ),
    servers = {
        @Server(url = "http://localhost:8080", description = "Desenvolvimento"),
        @Server(url = "https://api.example.com", description = "Produção")
    }
)
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .components(new Components()
                .addSecuritySchemes("bearer-jwt",
                    new SecurityScheme()
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT")
                        .description("JWT token para autenticação")
                )
            )
            .addSecurityItem(new SecurityRequirement().addList("bearer-jwt"));
    }
}
```

### 🔸 **Anotações Swagger nos DTOs**

```java
@Schema(description = "Request para criação de usuário")
public class CreateUserRequest {

    @Schema(description = "Nome completo do usuário", example = "João Silva", required = true)
    @NotBlank(message = "Nome é obrigatório")
    private String name;

    @Schema(description = "Email único do usuário", example = "joao@email.com", required = true)
    @Email(message = "Email deve ter formato válido")
    private String email;

    @Schema(description = "Senha do usuário", example = "senha123", required = true, minLength = 6)
    @Size(min = 6, message = "Senha deve ter pelo menos 6 caracteres")
    private String password;
}

@Schema(description = "Resposta com dados do usuário")
public class UserResponse {

    @Schema(description = "ID único do usuário", example = "1")
    private Long id;

    @Schema(description = "Nome completo", example = "João Silva")
    private String name;

    @Schema(description = "Email do usuário", example = "joao@email.com")
    private String email;

    @Schema(description = "Status atual", example = "ACTIVE")
    private UserStatus status;

    @Schema(description = "Data de criação", example = "2024-01-15T10:30:00")
    private LocalDateTime createdAt;
}
```

---

## 🧪 Testes da API RESTful

### 🔸 **Testes de Integração**

```java
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-test.properties")
class UserIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    @Test
    void shouldCreateUserSuccessfully() {
        // Given
        CreateUserRequest request = new CreateUserRequest("João", "joao@email.com", "senha123");

        // When
        ResponseEntity<UserResponse> response = restTemplate.postForEntity(
            "/api/v1/users", request, UserResponse.class);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getHeaders().getLocation()).isNotNull();
        assertThat(response.getBody().getName()).isEqualTo("João");
        assertThat(response.getBody().getEmail()).isEqualTo("joao@email.com");
        assertThat(response.getBody().getId()).isNotNull();
    }

    @Test
    void shouldReturnConflictWhenEmailAlreadyExists() {
        // Given
        User existingUser = new User("Maria", "maria@email.com", "senha123");
        userRepository.save(existingUser);

        CreateUserRequest request = new CreateUserRequest("João", "maria@email.com", "senha456");

        // When
        ResponseEntity<ErrorResponse> response = restTemplate.postForEntity(
            "/api/v1/users", request, ErrorResponse.class);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
        assertThat(response.getBody().getCode()).isEqualTo("EMAIL_ALREADY_EXISTS");
    }

    @Test
    void shouldReturnValidationErrorForInvalidData() {
        // Given
        CreateUserRequest request = new CreateUserRequest("", "email-inválido", "123");

        // When
        ResponseEntity<ValidationErrorResponse> response = restTemplate.postForEntity(
            "/api/v1/users", request, ValidationErrorResponse.class);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody().getFieldErrors()).hasSize(3);
        assertThat(response.getBody().getFieldErrors()).containsKeys("name", "email", "password");
    }

    @Test
    void shouldUpdateUserPartially() {
        // Given
        User user = userRepository.save(new User("João", "joao@email.com", "senha123"));
        UpdateUserRequest request = new UpdateUserRequest();
        request.setName("João Silva");

        HttpEntity<UpdateUserRequest> entity = new HttpEntity<>(request);

        // When
        ResponseEntity<UserResponse> response = restTemplate.exchange(
            "/api/v1/users/" + user.getId(), HttpMethod.PATCH, entity, UserResponse.class);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getName()).isEqualTo("João Silva");
        assertThat(response.getBody().getEmail()).isEqualTo("joao@email.com"); // Não alterado
    }

    @Test
    void shouldReturnPaginatedUsers() {
        // Given
        userRepository.saveAll(Arrays.asList(
            new User("User1", "user1@email.com", "senha123"),
            new User("User2", "user2@email.com", "senha123"),
            new User("User3", "user3@email.com", "senha123")
        ));

        // When
        ResponseEntity<String> response = restTemplate.getForEntity(
            "/api/v1/users?page=0&size=2&sortBy=name&sortDir=asc", String.class);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getHeaders().get("X-Total-Count")).contains("3");

        // Verificar JSON response
        JsonNode jsonResponse = objectMapper.readTree(response.getBody());
        assertThat(jsonResponse.get("content")).hasSize(2);
        assertThat(jsonResponse.get("totalElements").asInt()).isEqualTo(3);
        assertThat(jsonResponse.get("totalPages").asInt()).isEqualTo(2);
    }
}
```

### 🔸 **Testes de Controller (Unit)**

```java
@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldReturnUserWhenFound() throws Exception {
        // Given
        UserResponse user = new UserResponse(1L, "João", "joao@email.com",
            UserStatus.ACTIVE, LocalDateTime.now(), LocalDateTime.now());
        when(userService.findById(1L)).thenReturn(user);

        // When & Then
        mockMvc.perform(get("/api/v1/users/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.name").value("João"))
            .andExpect(jsonPath("$.email").value("joao@email.com"))
            .andExpect(jsonPath("$.status").value("ACTIVE"));
    }

    @Test
    void shouldReturnNotFoundWhenUserDoesNotExist() throws Exception {
        // Given
        when(userService.findById(999L)).thenThrow(new UserNotFoundException("Usuário não encontrado"));

        // When & Then
        mockMvc.perform(get("/api/v1/users/999"))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.code").value("USER_NOT_FOUND"));
    }

    @Test
    void shouldCreateUserWithValidData() throws Exception {
        // Given
        CreateUserRequest request = new CreateUserRequest("João", "joao@email.com", "senha123");
        UserResponse response = new UserResponse(1L, "João", "joao@email.com",
            UserStatus.ACTIVE, LocalDateTime.now(), LocalDateTime.now());

        when(userService.create(any(CreateUserRequest.class))).thenReturn(response);

        // When & Then
        mockMvc.perform(post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated())
            .andExpect(header().exists("Location"))
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.name").value("João"));
    }
}
```

---

## 🎯 Preparação para Entrevistas

### ✅ **Tópicos Essenciais**

#### **Fundamentos REST**
- [ ] 6 princípios REST (Client-Server, Stateless, Cacheable, etc.)
- [ ] Diferença entre REST e SOAP
- [ ] Idempotência e Safety dos métodos HTTP
- [ ] Status codes apropriados para cada situação
- [ ] HATEOAS e quando aplicar

#### **Design de APIs**
- [ ] Naming conventions para recursos
- [ ] Versionamento de APIs (URL, Header, Media Type)
- [ ] Paginação, filtros e ordenação
- [ ] Tratamento de erros consistente
- [ ] Documentação com OpenAPI/Swagger

#### **Implementação Prática**
- [ ] DTOs vs Entities
- [ ] Validação de entrada
- [ ] Exception handling global
- [ ] Testes de API (unit e integration)
- [ ] Segurança (autenticação, autorização)

### 🗣️ **Perguntas Frequentes**

#### **Conceitos REST**
**P:** "Explique a diferença entre PUT e PATCH"
**R:**
- **PUT:** Substitui o recurso completo (idempotente)
- **PATCH:** Atualiza parcialmente (não necessariamente idempotente)
- **Exemplo:** PUT /users/1 com {name, email, status} vs PATCH /users/1 com {name}

#### **Status Codes**
**P:** "Quando usar 201 vs 200?"
**R:**
- **201 Created:** Recurso criado com sucesso (POST)
- **200 OK:** Operação bem-sucedida (GET, PUT, PATCH)
- **204 No Content:** Sucesso sem conteúdo (DELETE)

#### **Design de URLs**
**P:** "Como projetar URLs RESTful?"
**R:**
```
✅ Boas práticas:
GET /api/v1/users          # Lista usuários
GET /api/v1/users/123      # Usuário específico
POST /api/v1/users         # Criar usuário
PUT /api/v1/users/123      # Atualizar usuário
DELETE /api/v1/users/123   # Deletar usuário

❌ Evitar:
GET /api/getUsers
POST /api/createUser
GET /api/user?action=delete
```

#### **Versionamento**
**P:** "Como versionar uma API?"
**R:**
1. **URL Versioning:** /api/v1/users (mais comum)
2. **Header Versioning:** API-Version: 1
3. **Media Type:** application/vnd.api.v1+json
4. **Query Parameter:** /api/users?version=1 (não recomendado)

---

## 📈 Roadmap de Aprendizado

### 🥇 **Nível Iniciante (1-2 meses)**
1. **Fundamentos HTTP**
   - Métodos HTTP e semântica
   - Status codes principais
   - Headers importantes
   - Request/Response structure

2. **Princípios REST**
   - 6 constraints REST
   - Stateless vs Stateful
   - Resource identification
   - Uniform interface

3. **Implementação Básica**
   - Spring Boot REST controllers
   - DTOs e validação
   - Exception handling
   - Testes básicos

### 🥈 **Nível Intermediário (2-4 meses)**
1. **Design Avançado**
   - HATEOAS implementation
   - API versioning strategies
   - Pagination e filtering
   - Content negotiation

2. **Qualidade e Documentação**
   - OpenAPI/Swagger
   - API testing strategies
   - Performance optimization
   - Caching strategies

3. **Segurança**
   - Authentication (JWT, OAuth)
   - Authorization patterns
   - CORS configuration
   - Rate limiting

### 🥉 **Nível Avançado (4+ meses)**
1. **Arquitetura**
   - API Gateway patterns
   - Microservices communication
   - Event-driven APIs
   - GraphQL vs REST

2. **Operações**
   - API monitoring
   - Logging e tracing
   - Error tracking
   - Performance tuning

3. **Especialização**
   - API design leadership
   - Standards definition
   - Team mentoring
   - Architecture decisions

---

## 🎓 Conclusão

RESTful APIs são **fundamentais** para desenvolvedores porque:

- ✅ **Padrão da indústria** - 90% das APIs modernas são REST
- ✅ **Base para microserviços** - comunicação entre serviços
- ✅ **Integração universal** - funciona em qualquer plataforma
- ✅ **Carreira essencial** - requisito em todas as vagas
- ✅ **Fundação sólida** - base para tecnologias avançadas

### 🚀 **Próximos Passos**
1. **Implemente o exemplo** - clone e execute o projeto prático
2. **Pratique design** - crie APIs para diferentes domínios
3. **Estude ferramentas** - Postman, Swagger, testing frameworks
4. **Aplique segurança** - JWT, OAuth, rate limiting
5. **Monitore qualidade** - testes, documentação, performance

**Lembre-se:** Uma boa API é intuitiva, consistente e bem documentada! 📚

---

*Desenvolvido para preparação em entrevistas técnicas e crescimento profissional em APIs RESTful.*