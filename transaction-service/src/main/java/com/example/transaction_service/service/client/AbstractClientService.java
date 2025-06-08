package com.example.transaction_service.service.client;

import com.example.transaction_service.exception.ProcessingException;
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
    public abstract C save(C client);

    public abstract C update(C client);

    /**
     * Получить клиента {@link Client} по его Id
     * @param id Id существующего клиента
     * @return Существующий клиент {@link Client}
     */
    public abstract C getById(long id);

    public abstract C getByClientId(UUID clientId);

    public abstract C getByUserId(long userId);
    public abstract C getByUserEmail(String email);
    /**
     * Проверить, существует ли клиент {@link Client} по Id
     * @param id Id потенциально существующего клиента {@link Client}
     * @return {@link Boolean} результат проверки на наличие клиента {@link Client}
     */
    public boolean existsById(long id) {
        return clientRepository.existsById(id);
    }

    public abstract boolean existsByClientId(UUID clientId);

    public UUID buildUUID(){
        UUID uuid = UUID.randomUUID();
        for(int i = 0; i < 10; i++){
            if(existsByClientId(uuid)){
                uuid = UUID.randomUUID();
            } else {
                return uuid;
            }
        }
        throw new ProcessingException("uuid is not generated");
    }
}
