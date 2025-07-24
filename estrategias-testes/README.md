# 🧪 Testing Strategies

[![Testing](https://img.shields.io/badge/focus-Testing-green.svg)]()
[![Quality](https://img.shields.io/badge/quality-Assurance-blue.svg)]()
[![TDD](https://img.shields.io/badge/methodology-TDD-orange.svg)]()

> Guia completo de estratégias de teste para desenvolvimento de software de qualidade e preparação para entrevistas técnicas.

---

## 🎯 Por que Testing é Fundamental?

### 💼 **Importância Profissional**
- **Qualidade de software** - Reduz bugs em produção
- **Confiança no código** - Permite refatoração segura
- **Documentação viva** - Testes como especificação
- **Requisito obrigatório** - Esperado em todas as vagas

### 🚀 **Benefícios Técnicos**
- **Detecção precoce** - Bugs encontrados cedo custam menos
- **Regressão** - Evita quebrar funcionalidades existentes
- **Design melhor** - Código testável é melhor estruturado
- **Manutenibilidade** - Facilita mudanças futuras

---

## 🏗️ Pirâmide de Testes

```
        /\
       /  \
      / E2E \     ← Poucos, caros, lentos
     /______\
    /        \
   / Integration \  ← Alguns, médio custo
  /______________\
 /                \
/   Unit Tests     \  ← Muitos, baratos, rápidos
/____________________\
```

### 🔸 **Unit Tests (Base da Pirâmide)**
**70% dos testes devem ser unitários**

```java
// Exemplo de teste unitário
@ExtendWith(MockitoExtension.class)
class CalculatorTest {
    
    private Calculator calculator;
    
    @BeforeEach
    void setUp() {
        calculator = new Calculator();
    }
    
    @Test
    @DisplayName("Should add two positive numbers correctly")
    void shouldAddTwoPositiveNumbers() {
        // Given
        int a = 5;
        int b = 3;
        
        // When
        int result = calculator.add(a, b);
        
        // Then
        assertThat(result).isEqualTo(8);
    }
    
    @Test
    @DisplayName("Should throw exception when dividing by zero")
    void shouldThrowExceptionWhenDividingByZero() {
        // Given
        int dividend = 10;
        int divisor = 0;
        
        // When & Then
        assertThatThrownBy(() -> calculator.divide(dividend, divisor))
            .isInstanceOf(ArithmeticException.class)
            .hasMessage("Division by zero is not allowed");
    }
    
    @ParameterizedTest
    @ValueSource(ints = {-1, 0, 1, 100, Integer.MAX_VALUE})
    @DisplayName("Should handle various input values")
    void shouldHandleVariousInputValues(int input) {
        // When
        boolean result = calculator.isPositive(input);
        
        // Then
        assertThat(result).isEqualTo(input > 0);
    }
}
```

### 🔸 **Integration Tests (Meio da Pirâmide)**
**20% dos testes devem ser de integração**

```java
@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class UserServiceIntegrationTest {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private TestEntityManager entityManager;
    
    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }
    
    @Test
    @DisplayName("Should create user and save to database")
    void shouldCreateUserAndSaveToDatabase() {
        // Given
        CreateUserRequest request = CreateUserRequest.builder()
            .name("João Silva")
            .email("joao@email.com")
            .password("senha123")
            .build();
        
        // When
        UserResponse response = userService.create(request);
        
        // Then
        assertThat(response.getId()).isNotNull();
        assertThat(response.getName()).isEqualTo("João Silva");
        assertThat(response.getEmail()).isEqualTo("joao@email.com");
        
        // Verify database state
        Optional<User> savedUser = userRepository.findById(response.getId());
        assertThat(savedUser).isPresent();
        assertThat(savedUser.get().getName()).isEqualTo("João Silva");
    }
    
    @Test
    @DisplayName("Should handle email uniqueness constraint")
    void shouldHandleEmailUniquenessConstraint() {
        // Given
        User existingUser = User.builder()
            .name("Maria")
            .email("maria@email.com")
            .password("senha123")
            .status(UserStatus.ACTIVE)
            .build();
        userRepository.save(existingUser);
        
        CreateUserRequest request = CreateUserRequest.builder()
            .name("João")
            .email("maria@email.com") // Email duplicado
            .password("senha456")
            .build();
        
        // When & Then
        assertThatThrownBy(() -> userService.create(request))
            .isInstanceOf(EmailAlreadyExistsException.class)
            .hasMessageContaining("Email já está em uso");
    }
}
```

### 🔸 **End-to-End Tests (Topo da Pirâmide)**
**10% dos testes devem ser E2E**

```java
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerE2ETest {
    
    @Autowired
    private TestRestTemplate restTemplate;
    
    @Autowired
    private UserRepository userRepository;
    
    @Test
    @DisplayName("Should complete full user lifecycle")
    void shouldCompleteFullUserLifecycle() {
        // 1. Create user
        CreateUserRequest createRequest = CreateUserRequest.builder()
            .name("João Silva")
            .email("joao@email.com")
            .password("senha123")
            .build();
        
        ResponseEntity<UserResponse> createResponse = restTemplate.postForEntity(
            "/api/v1/users", createRequest, UserResponse.class);
        
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Long userId = createResponse.getBody().getId();
        
        // 2. Get user
        ResponseEntity<UserResponse> getResponse = restTemplate.getForEntity(
            "/api/v1/users/" + userId, UserResponse.class);
        
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getResponse.getBody().getName()).isEqualTo("João Silva");
        
        // 3. Update user
        UpdateUserRequest updateRequest = UpdateUserRequest.builder()
            .name("João Santos")
            .build();
        
        HttpEntity<UpdateUserRequest> updateEntity = new HttpEntity<>(updateRequest);
        ResponseEntity<UserResponse> updateResponse = restTemplate.exchange(
            "/api/v1/users/" + userId, HttpMethod.PATCH, updateEntity, UserResponse.class);
        
        assertThat(updateResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(updateResponse.getBody().getName()).isEqualTo("João Santos");
        
        // 4. Delete user
        restTemplate.delete("/api/v1/users/" + userId);
        
        ResponseEntity<String> getDeletedResponse = restTemplate.getForEntity(
            "/api/v1/users/" + userId, String.class);
        
        assertThat(getDeletedResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}
```

---

## 🔄 Test-Driven Development (TDD)

### 🔸 **Ciclo Red-Green-Refactor**

```java
// 1. RED - Escrever teste que falha
@Test
void shouldCalculateAreaOfRectangle() {
    // Given
    Rectangle rectangle = new Rectangle(5, 3);
    
    // When
    double area = rectangle.calculateArea();
    
    // Then
    assertThat(area).isEqualTo(15.0);
}

// 2. GREEN - Implementação mínima para passar
public class Rectangle {
    private double width;
    private double height;
    
    public Rectangle(double width, double height) {
        this.width = width;
        this.height = height;
    }
    
    public double calculateArea() {
        return width * height; // Implementação simples
    }
}

// 3. REFACTOR - Melhorar o código mantendo os testes passando
public class Rectangle {
    private final double width;
    private final double height;
    
    public Rectangle(double width, double height) {
        validateDimensions(width, height);
        this.width = width;
        this.height = height;
    }
    
    public double calculateArea() {
        return width * height;
    }
    
    private void validateDimensions(double width, double height) {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("Dimensions must be positive");
        }
    }
}
```

---

## 🎭 Test Doubles

### 🔸 **Mocks, Stubs, Fakes e Spies**

```java
@ExtendWith(MockitoExtension.class)
class OrderServiceTest {
    
    @Mock
    private PaymentService paymentService;
    
    @Mock
    private InventoryService inventoryService;
    
    @Mock
    private EmailService emailService;
    
    @InjectMocks
    private OrderService orderService;
    
    @Test
    @DisplayName("Should process order successfully")
    void shouldProcessOrderSuccessfully() {
        // Given
        Order order = Order.builder()
            .productId(1L)
            .quantity(2)
            .customerId(100L)
            .build();
        
        // Stub - retorna valor predefinido
        when(inventoryService.isAvailable(1L, 2)).thenReturn(true);
        when(paymentService.processPayment(any())).thenReturn(
            PaymentResult.success("payment-123"));
        
        // When
        OrderResult result = orderService.processOrder(order);
        
        // Then
        assertThat(result.isSuccess()).isTrue();
        
        // Verify - verifica interações
        verify(inventoryService).reserveItems(1L, 2);
        verify(paymentService).processPayment(any(PaymentRequest.class));
        verify(emailService).sendOrderConfirmation(eq(100L), any(Order.class));
    }
    
    @Test
    @DisplayName("Should handle payment failure")
    void shouldHandlePaymentFailure() {
        // Given
        Order order = Order.builder()
            .productId(1L)
            .quantity(1)
            .customerId(100L)
            .build();
        
        when(inventoryService.isAvailable(1L, 1)).thenReturn(true);
        when(paymentService.processPayment(any())).thenReturn(
            PaymentResult.failure("Insufficient funds"));
        
        // When
        OrderResult result = orderService.processOrder(order);
        
        // Then
        assertThat(result.isSuccess()).isFalse();
        assertThat(result.getErrorMessage()).contains("Insufficient funds");
        
        // Verify rollback
        verify(inventoryService).releaseReservation(1L, 1);
        verify(emailService, never()).sendOrderConfirmation(any(), any());
    }
}
```

---

## 🎯 Estratégias de Teste por Tipo

### 🔸 **Testes de API REST**

```java
@WebMvcTest(UserController.class)
class UserControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private UserService userService;
    
    @Test
    @DisplayName("Should return user when found")
    void shouldReturnUserWhenFound() throws Exception {
        // Given
        UserResponse user = UserResponse.builder()
            .id(1L)
            .name("João")
            .email("joao@email.com")
            .status(UserStatus.ACTIVE)
            .build();
        
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
    @DisplayName("Should validate input on create user")
    void shouldValidateInputOnCreateUser() throws Exception {
        // Given
        String invalidRequest = """
            {
                "name": "",
                "email": "invalid-email",
                "password": "123"
            }
            """;
        
        // When & Then
        mockMvc.perform(post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidRequest))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.fieldErrors.name").exists())
            .andExpect(jsonPath("$.fieldErrors.email").exists())
            .andExpect(jsonPath("$.fieldErrors.password").exists());
    }
}
```

### 🔸 **Testes de Banco de Dados**

```java
@DataJpaTest
class UserRepositoryTest {
    
    @Autowired
    private TestEntityManager entityManager;
    
    @Autowired
    private UserRepository userRepository;
    
    @Test
    @DisplayName("Should find user by email")
    void shouldFindUserByEmail() {
        // Given
        User user = User.builder()
            .name("João")
            .email("joao@email.com")
            .password("senha123")
            .status(UserStatus.ACTIVE)
            .build();
        
        entityManager.persistAndFlush(user);
        
        // When
        Optional<User> found = userRepository.findByEmail("joao@email.com");
        
        // Then
        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("João");
    }
    
    @Test
    @DisplayName("Should return empty when email not found")
    void shouldReturnEmptyWhenEmailNotFound() {
        // When
        Optional<User> found = userRepository.findByEmail("notfound@email.com");
        
        // Then
        assertThat(found).isEmpty();
    }
}
```

---

## 📊 Métricas de Qualidade

### 🔸 **Cobertura de Código**

```xml
<!-- Maven Jacoco Plugin -->
<plugin>
    <groupId>org.jacoco</groupId>
    <artifactId>jacoco-maven-plugin</artifactId>
    <version>0.8.8</version>
    <executions>
        <execution>
            <goals>
                <goal>prepare-agent</goal>
            </goals>
        </execution>
        <execution>
            <id>report</id>
            <phase>test</phase>
            <goals>
                <goal>report</goal>
            </goals>
        </execution>
        <execution>
            <id>check</id>
            <goals>
                <goal>check</goal>
            </goals>
            <configuration>
                <rules>
                    <rule>
                        <element>CLASS</element>
                        <limits>
                            <limit>
                                <counter>LINE</counter>
                                <value>COVEREDRATIO</value>
                                <minimum>0.80</minimum>
                            </limit>
                        </limits>
                    </rule>
                </rules>
            </configuration>
        </execution>
    </executions>
</plugin>
```

### 🔸 **Mutation Testing**

```xml
<!-- PIT Mutation Testing -->
<plugin>
    <groupId>org.pitest</groupId>
    <artifactId>pitest-maven</artifactId>
    <version>1.9.0</version>
    <configuration>
        <targetClasses>
            <param>com.example.*</param>
        </targetClasses>
        <targetTests>
            <param>com.example.*Test</param>
        </targetTests>
        <mutationThreshold>75</mutationThreshold>
    </configuration>
</plugin>
```

---

## 🎯 Preparação para Entrevistas

### ✅ **Perguntas Frequentes**

#### **Conceitos**
**P:** "Qual a diferença entre Mock e Stub?"
**R:** 
- **Mock:** Verifica comportamento (interações)
- **Stub:** Fornece respostas predefinidas
- **Exemplo:** Mock verifica se método foi chamado, Stub retorna valor fixo

#### **Estratégias**
**P:** "Como você estruturaria testes para uma API REST?"
**R:**
1. **Unit tests:** Lógica de negócio isolada
2. **Integration tests:** Controller + Service + Repository
3. **Contract tests:** Validação de contratos de API
4. **E2E tests:** Fluxos completos de usuário

#### **TDD**
**P:** "Explique o ciclo Red-Green-Refactor"
**R:**
1. **Red:** Escrever teste que falha
2. **Green:** Implementação mínima para passar
3. **Refactor:** Melhorar código mantendo testes

---

## 📈 Roadmap de Aprendizado

### 🥇 **Nível Iniciante (2-4 semanas)**
1. Fundamentos de testes unitários
2. JUnit 5 e AssertJ
3. Mockito básico
4. Cobertura de código

### 🥈 **Nível Intermediário (1-2 meses)**
1. TDD na prática
2. Testes de integração
3. Test containers
4. Mutation testing

### 🥉 **Nível Avançado (2-3 meses)**
1. BDD com Cucumber
2. Performance testing
3. Contract testing
4. Test automation strategies

---

## 🎓 Conclusão

Testing é **fundamental** porque:

- ✅ **Qualidade garantida** - Software mais confiável
- ✅ **Refatoração segura** - Mudanças sem medo
- ✅ **Documentação viva** - Especificação executável
- ✅ **Diferencial profissional** - Skill valorizada
- ✅ **Entrega contínua** - Base para DevOps

**Lembre-se:** Teste não é sobre quantidade, é sobre qualidade e confiança! 🎯

---

*Desenvolvido para preparação em entrevistas técnicas e crescimento profissional em Testing Strategies.*
