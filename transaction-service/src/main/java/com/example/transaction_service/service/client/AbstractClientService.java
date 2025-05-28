package com.example.transaction_service.service.client;

import com.example.transaction_service.model.client.entity.Client;
import com.example.transaction_service.repository.ClientRepository;

import java.util.UUID;

/**
 * Абстрактный класс, предоставляющий функционал для работы с клиентами {@link Client}
 * @param <C> Тип, являющийся клиентом {@link Client} или его наследником
 */
public abstract class AbstractClientService<C extends Client> {
    protected final ClientRepository clientRepository;

    public AbstractClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    /**
     * Сохранить несуществующего клиента {@link Client}
     * @param client несуществующий клиент {@link Client}
     */
    public abstract UUID save(C client);

    /**
     * Получить клиента {@link Client} по его Id
     * @param id Id существующего клиента
     * @return Существующий клиент {@link Client}
     */
    public abstract C getById(long id);

    public abstract C getByClientId(UUID clientId);
    /**
     * Проверить, существует ли клиент {@link Client} по Id
     * @param id Id потенциально существующего клиента {@link Client}
     * @return {@link Boolean} результат проверки на наличие клиента {@link Client}
     */
    public boolean existsById(long id) {
        return clientRepository.existsById(id);
    }

    public abstract boolean existsByClientId(UUID clientId);
}
