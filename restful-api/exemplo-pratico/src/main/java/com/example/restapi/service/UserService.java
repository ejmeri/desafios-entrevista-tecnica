package com.example.restapi.service;

import com.example.restapi.dto.CreateUserRequest;
import com.example.restapi.dto.UpdateUserRequest;
import com.example.restapi.dto.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Interface do serviço de usuários
 * 
 * Define as operações de negócio para gerenciamento de usuários
 * seguindo os princípios REST e boas práticas de design.
 */
public interface UserService {

    /**
     * Busca todos os usuários com paginação e filtro opcional por status
     * 
     * @param pageable configuração de paginação e ordenação
     * @param status filtro opcional por status do usuário
     * @return página de usuários
     */
    Page<UserResponse> findAll(Pageable pageable, String status);

    /**
     * Busca usuário por ID
     * 
     * @param id identificador único do usuário
     * @return dados do usuário
     * @throws UserNotFoundException se usuário não for encontrado
     */
    UserResponse findById(Long id);

    /**
     * Cria novo usuário
     * 
     * @param request dados para criação do usuário
     * @return usuário criado
     * @throws EmailAlreadyExistsException se email já estiver em uso
     */
    UserResponse create(CreateUserRequest request);

    /**
     * Atualiza usuário completo (PUT)
     * 
     * @param id identificador do usuário
     * @param request dados para atualização
     * @return usuário atualizado
     * @throws UserNotFoundException se usuário não for encontrado
     * @throws EmailAlreadyExistsException se email já estiver em uso
     */
    UserResponse update(Long id, UpdateUserRequest request);

    /**
     * Atualiza usuário parcialmente (PATCH)
     * 
     * @param id identificador do usuário
     * @param request dados para atualização parcial
     * @return usuário atualizado
     * @throws UserNotFoundException se usuário não for encontrado
     * @throws EmailAlreadyExistsException se email já estiver em uso
     */
    UserResponse patch(Long id, UpdateUserRequest request);

    /**
     * Remove usuário
     * 
     * @param id identificador do usuário
     * @throws UserNotFoundException se usuário não for encontrado
     */
    void delete(Long id);

    /**
     * Verifica se usuário existe
     * 
     * @param id identificador do usuário
     * @return true se usuário existir, false caso contrário
     */
    boolean exists(Long id);
}
