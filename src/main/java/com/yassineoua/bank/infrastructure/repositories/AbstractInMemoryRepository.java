package com.yassineoua.bank.infrastructure.repositories;

import com.yassineoua.bank.domain.model.PersistableObject;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public abstract class AbstractInMemoryRepository<E extends PersistableObject> {

    private Map<Long, E> dataStore = new ConcurrentHashMap<>();
    private AtomicLong IdGenerator = new AtomicLong(0L);

    public E save(E e) {
        if (Objects.isNull(e.getId())) {
            e.setId(IdGenerator.addAndGet(1L));
        }
        dataStore.put(e.getId(), e);
        return e;
    }

    public E findById(Long id) {
        return dataStore.get(id);
    }


    protected Map<Long, E> getDataStore() {
        return this.dataStore;
    }
}
