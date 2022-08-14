package com.pinitservicfes.intro.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.mongodb.lang.Nullable;
import com.pinitservicfes.intro.MongoDBUtils;
import com.pinitservicfes.intro.model.BasicEntity;
import com.pinitservicfes.intro.repositories.BasicEntityRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

@Log
@RequiredArgsConstructor
public class BasicEntityService<T extends BasicEntity> {
    protected final Class<T> _class;
    private final BasicEntityRepository<T> repository;
    @Autowired
    private MongoTemplate template;

    public static Update addLastUpdate(Update update) {
        update.set(BasicEntity.Fields.lastUpdate, System.currentTimeMillis());
        return update;
    }

    protected T findIfNewer(final String id, final long date) {
        return repository.findIfNewer(id, date);
    }

    public <S extends T> List<S> saveAll(final Iterable<S> entities) {
        return repository.saveAll(entities);
    }

    public List<T> findAll() {
        return repository.findAll();
    }

    public List<T> findAll(final Sort sort) {
        return repository.findAll(sort);
    }

    public Page<T> findAll(final Pageable pageable) {
        return repository.findAll(pageable);
    }

    public T save(final T entity) {
        return repository.save(entity);
    }

    public void deleteById(final String s) {
        if (s != null) {
            repository.deleteById(s);
        }
    }

    public DeleteResult remove(final Query query) {
        return this.template.remove(query, this._class);
    }

    @Nullable
    public T findOne(final Query query) {
        return this.template.findOne(query, this._class);
    }

    public boolean exists(final Query query) {
        return this.template.exists(query, this._class);
    }

    public List<T> find(final Query query) {
        return this.template.find(query, this._class);
    }

    @Nullable
    public T findById(final String id) {
        if (id == null) {
            return null;
        }
        return this.template.findById(id, this._class);
    }

    @Nullable
    public T findAndModify(final Query query, final Update update, final FindAndModifyOptions options) {
        return this.template.findAndModify(query, addLastUpdate(update), options, this._class);

    }

    @Nullable
    public T findAndModify(final String entityId, final Update update, final FindAndModifyOptions options) {
        return findAndModify(MongoDBUtils.id(entityId), update, options);
    }

    @Nullable
    public T findAndModify(final String entityId, final Update update) {
        return findAndModify(entityId, update, null);
    }

    public T findAndModify(final Query query, final Update update) {
        return findAndModify(query, update, null);
    }

    public T findOne(final String entityId) {
        return this.findOne(MongoDBUtils.id(entityId));
    }

    public long count(final Query query) {
        return this.template.count(query, this._class);
    }

    public long count() {
        return repository.count();
    }

    public List<T> findAll(final Iterable<String> ids) {
        final List<T> list = new ArrayList<>();

        final var iterable = repository.findAllById(ids);
        for (final T t : iterable) {
            list.add(t);
        }
        return list;
    }

    public T updateOneField(final String entityId, final String field, final Object value) {
        return updateOneField(MongoDBUtils.id(entityId), field, value);
    }

    public T updateOneField(final Query query, final String field, final Object value) {
        return findAndModify(query, MongoDBUtils.set(field, value));
    }

    @Nullable
    public T findAndRemove(final Query query) {
        return this.template.findAndRemove(query, this._class);
    }

    @Nullable
    public T findAndRemove(final String entityId) {
        return this.template.findAndRemove(MongoDBUtils.id(entityId), this._class);
    }

    public UpdateResult updateMulti(final Query query, final Update update) {
        var r = template.updateMulti(query, addLastUpdate(update), this._class);
        return r;
    }

    public long countByCreationDate(final long creationDate) {
        return repository.countByCreationDate(creationDate);
    }

    public T unsetIf(String entityId, String field, String ifValue) {
        return findAndModify(Query.query(MongoDBUtils.idCriteria(entityId).and(field).is(ifValue)),
                new Update().unset(field));
    }

    public UpdateResult updateFirst(Query query, Update update) {
        var r = template.updateFirst(query, addLastUpdate(update), _class);
        return r;
    }

    public UpdateResult updateFirst(String entityId, Update update) {
        return updateFirst(MongoDBUtils.id(entityId), update);
    }

    public UpdateResult upsert(Query query, Update update) {
        return template.upsert(query, addLastUpdate(update), _class);
    }
}
