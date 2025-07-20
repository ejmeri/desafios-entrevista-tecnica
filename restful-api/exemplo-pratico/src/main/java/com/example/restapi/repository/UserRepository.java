package com.example.restapi.repository;

import com.example.restapi.model.User;
import com.example.restapi.model.User.UserStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositório para entidade User
 * 
 * Fornece operações de acesso a dados seguindo padrões Spring Data JPA
 * e princípios REST para consultas eficientes.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    /**
     * Busca usuário por email
     * 
     * @param email email do usuário
     * @return usuário encontrado ou empty
     */
    Optional<User> findByEmail(String email);

    /**
     * Verifica se existe usuário com o email informado
     * 
     * @param email email a ser verificado
     * @return true se existir, false caso contrário
     */
    boolean existsByEmail(String email);

    /**
     * Busca usuários por status com paginação
     * 
     * @param status status do usuário
     * @param pageable configuração de paginação
     * @return página de usuários
     */
    Page<User> findByStatus(UserStatus status, Pageable pageable);

    /**
     * Busca usuários por nome contendo texto (case insensitive)
     * 
     * @param name texto a ser buscado no nome
     * @param pageable configuração de paginação
     * @return página de usuários
     */
    Page<User> findByNameContainingIgnoreCase(String name, Pageable pageable);

    /**
     * Busca usuários por email contendo texto (case insensitive)
     * 
     * @param email texto a ser buscado no email
     * @param pageable configuração de paginação
     * @return página de usuários
     */
    Page<User> findByEmailContainingIgnoreCase(String email, Pageable pageable);

    /**
     * Conta usuários por status
     * 
     * @param status status do usuário
     * @return quantidade de usuários
     */
    long countByStatus(UserStatus status);

    /**
     * Busca usuários ativos ordenados por nome
     * 
     * @return lista de usuários ativos
     */
    @Query("SELECT u FROM User u WHERE u.status = 'ACTIVE' ORDER BY u.name")
    Page<User> findActiveUsersOrderByName(Pageable pageable);

    /**
     * Busca usuários por múltiplos critérios
     * 
     * @param name nome (opcional)
     * @param email email (opcional)
     * @param status status (opcional)
     * @param pageable configuração de paginação
     * @return página de usuários
     */
    @Query("SELECT u FROM User u WHERE " +
           "(:name IS NULL OR LOWER(u.name) LIKE LOWER(CONCAT('%', :name, '%'))) AND " +
           "(:email IS NULL OR LOWER(u.email) LIKE LOWER(CONCAT('%', :email, '%'))) AND " +
           "(:status IS NULL OR u.status = :status)")
    Page<User> findByMultipleCriteria(@Param("name") String name,
                                     @Param("email") String email,
                                     @Param("status") UserStatus status,
                                     Pageable pageable);
}
