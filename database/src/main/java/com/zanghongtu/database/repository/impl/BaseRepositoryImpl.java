package com.zanghongtu.database.repository.impl;

import com.zanghongtu.database.repository.BaseRepository;
import com.zanghongtu.database.repository.model.BaseModel;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import java.io.Serializable;
import java.util.*;

/**
 * @author : Hongtu Zang
 * @date : Created in 下午1:18 19-5-28
 */
@Transactional(rollbackFor = Exception.class)
public class BaseRepositoryImpl<T extends BaseModel, ID extends Serializable>
        extends SimpleJpaRepository<T, ID>
        implements BaseRepository<T, ID> {

    private final EntityManager entityManager;

    public BaseRepositoryImpl(JpaEntityInformation<T, ID> entityInformation,
                              EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityManager = entityManager;
    }

    @Override
    public T create(T model) {
        Date now = new Date();
        model.setCreateTime(now);
        model.setUpdateTime(now);
        model.setAvailable(true);
        return save(model);
    }

    @Override
    public T delete(ID id) {
        T model = getOne(id);
        model.setAvailable(false);
        return update(model);
    }

    @Override
    public T physicalDelete(ID id) {
        T model = getOne(id);
        model.setAvailable(false);
        delete(model);
        return model;
    }

    @Override
    public T update(T model) {
        model.setUpdateTime(new Date());
        return save(model);
    }

    @Override
    public T get(ID id) {
        return getOne(id);
    }

    @Override
    public List<T> list() {
        Specification<T> spec = (Specification<T>) (root, query, criteriaBuilder) -> {
            Predicate p1 = criteriaBuilder.equal(root.get("available"), true);
            query.where(p1);
            return query.getRestriction();
        };
        return findAll(spec);
    }

    @Override
    public List<T> list(T model) {
        model.setAvailable(true);
        return findAll(Example.of(model));
    }

    @Override
    public List<T> list(T model, Sort sort) {
        model.setAvailable(true);
        return findAll(Example.of(model), sort);
    }

    @Override
    public Page<T> list(T model, Sort sort, int page, int pageSize) {
        model.setAvailable(true);
        Pageable pageable = PageRequest.of(page, pageSize, sort);
        return findAll(pageable);
    }

    @Override
    public List<T> listAll() {
        return findAll();
    }

    @Override
    public List<T> listAll(T model) {
        return findAll(Example.of(model));
    }

    @Override
    public List<T> listAll(T model, Sort sort) {
        return findAll(Example.of(model), sort);
    }

    @Override
    public Page<T> listAll(T model, Sort sort, int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize, sort);
        return findAll(Example.of(model), pageable);
    }

}
