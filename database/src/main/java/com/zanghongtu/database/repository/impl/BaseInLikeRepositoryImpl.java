package com.zanghongtu.database.repository.impl;

import com.zanghongtu.database.repository.BaseInLikeRepository;
import com.zanghongtu.database.repository.model.BaseModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
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
public class BaseInLikeRepositoryImpl<T extends BaseModel, ID extends Serializable>
        extends BaseRepositoryImpl<T, ID>
        implements BaseInLikeRepository<T, ID> {

    private final EntityManager entityManager;

    public BaseInLikeRepositoryImpl(JpaEntityInformation<T, ID> entityInformation,
                                    EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityManager = entityManager;
    }

    @Override
    public <V> List<T> in(Map<String, Set<V>> conditions) {
        return in(conditions, Sort.unsorted());
    }

    @Override
    public <V> List<T> in(Map<String, Set<V>> conditions, Sort sort) {
        Specification<T> spec = getInSpec(conditions, false);
        return findAll(spec, sort);
    }

    @Override
    public <V> Page<T> in(Map<String, Set<V>> conditions, Sort sort, int page, int pageSize) {
        Specification<T> spec = getInSpec(conditions, false);
        Pageable pageable = PageRequest.of(page, pageSize, sort);
        return findAll(spec, pageable);
    }

    @Override
    public <V> List<T> in(String key, Set<V> values) {
        Map<String, Set<V>> conditions = kv2InCondition(key, values);
        return in(conditions);
    }

    @Override
    public <V> List<T> in(String key, Set<V> values, Sort sort) {
        Map<String, Set<V>> conditions = kv2InCondition(key, values);
        return in(conditions, sort);
    }

    @Override
    public <V> Page<T> in(String key, Set<V> values, Sort sort, int page, int pageSize) {
        Map<String, Set<V>> conditions = kv2InCondition(key, values);
        return in(conditions, sort, page, pageSize);
    }

    @Override
    public <V> List<T> inAll(Map<String, Set<V>> conditions) {
        return inAll(conditions, Sort.unsorted());
    }

    @Override
    public <V> List<T> inAll(Map<String, Set<V>> conditions, Sort sort) {
        Specification<T> spec = getInSpec(conditions, true);
        return findAll(spec, sort);
    }

    @Override
    public <V> Page<T> inAll(Map<String, Set<V>> conditions, Sort sort, int page, int pageSize) {
        Specification<T> spec = getInSpec(conditions, true);
        sort = initSort(sort);
        Pageable pageable = initPageable(sort, page, pageSize);
        return findAll(spec, pageable);
    }

    @Override
    public <V> List<T> inAll(String key, Set<V> values) {
        Map<String, Set<V>> conditions = kv2InCondition(key, values);
        return inAll(conditions);
    }

    @Override
    public <V> List<T> inAll(String key, Set<V> values, Sort sort) {
        Map<String, Set<V>> conditions = kv2InCondition(key, values);
        return inAll(conditions, sort);
    }

    @Override
    public <V> Page<T> inAll(String key, Set<V> values, Sort sort, int page, int pageSize) {
        Map<String, Set<V>> conditions = kv2InCondition(key, values);
        return inAll(conditions, sort, page, pageSize);
    }

    @Override
    public List<T> like(String key, String value) {
        Map<String, String> conditions = kv2likeCondition(key, value);
        return like(conditions);
    }

    @Override
    public List<T> like(String key, String value, Sort sort) {
        Map<String, String> conditions = kv2likeCondition(key, value);
        return like(conditions, sort);
    }

    @Override
    public Page<T> like(String key, String value, Sort sort, int page, int pageSize) {
        Map<String, String> conditions = kv2likeCondition(key, value);
        return like(conditions, sort, page, pageSize);
    }

    @Override
    public List<T> likeAll(String key, String value) {
        Map<String, String> conditions = kv2likeCondition(key, value);
        return likeAll(conditions);
    }

    @Override
    public List<T> likeAll(String key, String value, Sort sort) {
        Map<String, String> conditions = kv2likeCondition(key, value);
        return likeAll(conditions, sort);
    }

    @Override
    public Page<T> likeAll(String key, String value, Sort sort, int page, int pageSize) {
        Map<String, String> conditions = kv2likeCondition(key, value);
        return likeAll(conditions, sort, page, pageSize);
    }

    @Override
    public List<T> like(Map<String, String> conditions) {
        return like(conditions, Sort.unsorted());
    }

    @Override
    public List<T> like(Map<String, String> conditions, Sort sort) {
        return like(conditions, sort, false, true, true);
    }

    @Override
    public Page<T> like(Map<String, String> conditions, Sort sort, int page, int pageSize) {
        Specification<T> spec = getAvailableLikeSpec(conditions, true, true);
        Pageable pageable = initPageable(sort, page, pageSize);
        return findAll(spec, pageable);
    }

    @Override
    public List<T> likeAll(Map<String, String> conditions) {
        return likeAll(conditions, Sort.unsorted());
    }

    @Override
    public List<T> likeAll(Map<String, String> conditions, Sort sort) {
        Specification<T> spec = getAllLikeSpec(conditions, true, true);
        return findAll(spec);
    }

    @Override
    public Page<T> likeAll(Map<String, String> conditions, Sort sort, int page, int pageSize) {
        Specification<T> spec = getAllLikeSpec(conditions, true, true);
        Pageable pageable = initPageable(sort, page, pageSize);
        return findAll(spec, pageable);
    }

    @Override
    public List<T> leftlike(String key, String value) {
        Map<String, String> conditions = kv2likeCondition(key, value);
        return leftlike(conditions);
    }

    @Override
    public List<T> leftlike(String key, String value, Sort sort) {
        Map<String, String> conditions = kv2likeCondition(key, value);
        return leftlike(conditions, sort);
    }

    @Override
    public Page<T> leftlike(String key, String value, Sort sort, int page, int pageSize) {
        Map<String, String> conditions = kv2likeCondition(key, value);
        return leftlike(conditions, sort, page, pageSize);
    }

    @Override
    public List<T> leftlike(Map<String, String> conditions) {
        return leftlike(conditions, Sort.unsorted());
    }

    @Override
    public List<T> leftlike(Map<String, String> conditions, Sort sort) {
        return like(conditions, sort, false, true, false);
    }

    @Override
    public Page<T> leftlike(Map<String, String> conditions, Sort sort, int page, int pageSize) {
        Specification<T> spec = getAvailableLikeSpec(conditions, true, false);
        Pageable pageable = initPageable(sort, page, pageSize);
        return findAll(spec, pageable);
    }

    @Override
    public List<T> leftlikeAll(String key, String value) {
        Map<String, String> conditions = kv2likeCondition(key, value);
        return leftlikeAll(conditions);
    }

    @Override
    public List<T> leftlikeAll(String key, String value, Sort sort) {
        Map<String, String> conditions = kv2likeCondition(key, value);
        return leftlikeAll(conditions, sort);
    }

    @Override
    public Page<T> leftlikeAll(String key, String value, Sort sort, int page, int pageSize) {
        Map<String, String> conditions = kv2likeCondition(key, value);
        return leftlikeAll(conditions, sort, page, pageSize);
    }

    @Override
    public List<T> leftlikeAll(Map<String, String> conditions) {
        return leftlikeAll(conditions, Sort.unsorted());
    }

    @Override
    public List<T> leftlikeAll(Map<String, String> conditions, Sort sort) {
        return like(conditions, sort, true, true, false);
    }

    @Override
    public Page<T> leftlikeAll(Map<String, String> conditions, Sort sort, int page, int pageSize) {
        Specification<T> spec = getAllLikeSpec(conditions, true, false);
        Pageable pageable = initPageable(sort, page, pageSize);
        return findAll(spec, pageable);
    }

    @Override
    public List<T> rightlike(String key, String value) {
        Map<String, String> conditions = kv2likeCondition(key, value);
        return leftlike(conditions);
    }

    @Override
    public List<T> rightlike(String key, String value, Sort sort) {
        Map<String, String> conditions = kv2likeCondition(key, value);
        return leftlike(conditions, sort);
    }

    @Override
    public Page<T> rightlike(String key, String value, Sort sort, int page, int pageSize) {
        Map<String, String> conditions = kv2likeCondition(key, value);
        return leftlike(conditions, sort, page, pageSize);
    }

    @Override
    public List<T> rightlike(Map<String, String> conditions) {
        return rightlike(conditions, Sort.unsorted());
    }

    @Override
    public List<T> rightlike(Map<String, String> conditions, Sort sort) {
        return like(conditions, sort, false, false, true);
    }

    @Override
    public Page<T> rightlike(Map<String, String> conditions, Sort sort, int page, int pageSize) {
        Specification<T> spec = getAvailableLikeSpec(conditions, false, true);
        Pageable pageable = initPageable(sort, page, pageSize);
        return findAll(spec, pageable);
    }

    private List<T> like(Map<String, String> conditions, Sort sort, boolean all, boolean left, boolean right) {
        Specification<T> spec;
        if (all) {
            spec = getAllLikeSpec(conditions, left, right);
        } else {
            spec = getAvailableLikeSpec(conditions, left, right);
        }
        return findAll(spec, sort);
    }

    private Pageable initPageable(Sort sort, int page, int pageSize) {
        if (sort == null) {
            sort = Sort.unsorted();
        }
        Pageable pageable;
        if (page < 0 || pageSize <= 0) {
            pageable = Pageable.unpaged();
        } else {
            pageable = PageRequest.of(page, pageSize, sort);
        }
        return pageable;
    }

    private Sort initSort(Sort sort) {
        if (sort == null) {
            sort = Sort.unsorted();
        }
        return sort;
    }

    private <V> Specification<T> getInSpec(Map<String, Set<V>> conditions, boolean all) {
        if (all) {
            return getAllInSpec(conditions);
        }
        return getAvailableInSpec(conditions);
    }

    private <V> Specification<T> getAvailableInSpec(Map<String, Set<V>> conditions) {
        return (Specification<T>) (root, query, criteriaBuilder) -> {
            List<Predicate> list = new LinkedList<>();
            for (String colName : conditions.keySet()) {
                Expression<T> exp = root.get(colName);
                list.add(exp.in(conditions.get(colName)));
            }
            Predicate predicate;
            if (list.size() != 0) {
                Predicate[] p = new Predicate[list.size()];
                predicate = criteriaBuilder.and(list.toArray(p));
            } else {
                predicate = null;
            }
            Predicate available = criteriaBuilder.equal(root.get("available"), true);
            return criteriaBuilder.and(predicate, available);
        };
    }

    private <V> Specification<T> getAllInSpec(Map<String, Set<V>> conditions) {
        return (Specification<T>) (root, query, criteriaBuilder) -> {
            List<Predicate> list = new LinkedList<>();
            for (String colName : conditions.keySet()) {
                Expression<T> exp = root.get(colName);
                list.add(exp.in(conditions.get(colName)));
            }
            Predicate predicate;
            if (list.size() != 0) {
                Predicate[] p = new Predicate[list.size()];
                predicate = criteriaBuilder.and(list.toArray(p));
            } else {
                predicate = null;
            }
            return predicate;
        };
    }

    private Specification<T> getAvailableLikeSpec(Map<String, String> conditions, boolean left, boolean right) {
        initLikeCondition(conditions, left, right);
        return (Specification<T>) (root, query, criteriaBuilder) -> {
            List<Predicate> list = new LinkedList<>();
            for (String colName : conditions.keySet()) {
                Predicate predicate = criteriaBuilder.like(root.get(colName), conditions.get(colName));
                list.add(predicate);
            }
            Predicate predicate;
            if (list.size() != 0) {
                Predicate[] p = new Predicate[list.size()];
                predicate = criteriaBuilder.and(list.toArray(p));
            } else {
                predicate = null;
            }
            Predicate available = criteriaBuilder.equal(root.get("available"), true);
            return criteriaBuilder.and(predicate, available);
        };
    }

    private Specification<T> getAllLikeSpec(Map<String, String> conditions, boolean left, boolean right) {
        initLikeCondition(conditions, left, right);
        return (Specification<T>) (root, query, criteriaBuilder) -> {
            List<Predicate> list = new LinkedList<>();
            for (String colName : conditions.keySet()) {
                Predicate predicate = criteriaBuilder.like(root.get(colName), conditions.get(colName));
                list.add(predicate);
            }
            Predicate predicate;
            if (list.size() != 0) {
                Predicate[] p = new Predicate[list.size()];
                predicate = criteriaBuilder.and(list.toArray(p));
            } else {
                predicate = null;
            }
            return predicate;
        };
    }

    private <V> Map<String, Set<V>> kv2InCondition(String key, Set<V> values) {
        Map<String, Set<V>> conditions = new HashMap<>(1);
        conditions.put(key, values);
        return conditions;
    }

    private Map<String, String> kv2likeCondition(String key, String value) {
        Map<String, String> conditions = new HashMap<>(1);
        conditions.put(key, value);
        return conditions;
    }

    private void initLikeCondition(Map<String, String> conditions, boolean left, boolean right) {
        for (Map.Entry<String, String> entry : conditions.entrySet()) {
            if (left) {
                entry.setValue("%" + entry.getValue());
            }
            if (right) {
                entry.setValue(entry.getValue() + "%");
            }
        }
    }
}
