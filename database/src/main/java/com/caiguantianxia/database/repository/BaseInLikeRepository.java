package com.caiguantianxia.database.repository;

import com.caiguantianxia.database.repository.model.BaseModelPO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author : Hongtu Zang
 * @date : Created in 下午1:10 19-5-27
 */
@NoRepositoryBean
public interface BaseInLikeRepository<T extends BaseModelPO, ID extends Serializable>
        extends BaseRepository<T, ID> {

    <V> List<T> in(Map<String, Set<V>> conditions);

    <V> List<T> in(Map<String, Set<V>> conditions, Sort sort);

    <V> Page<T> in(Map<String, Set<V>> conditions, Sort sort, int page, int pageSize);

    <V> List<T> in(String key, Set<V> values);

    <V> List<T> in(String key, Set<V> values, Sort sort);

    <V> Page<T> in(String key, Set<V> values, Sort sort, int page, int pageSize);

    <V> List<T> inAll(Map<String, Set<V>> conditions);

    <V> List<T> inAll(Map<String, Set<V>> conditions, Sort sort);

    <V> Page<T> inAll(Map<String, Set<V>> conditions, Sort sort, int page, int pageSize);

    <V> List<T> inAll(String key, Set<V> values);

    <V> List<T> inAll(String key, Set<V> values, Sort sort);

    <V> Page<T> inAll(String key, Set<V> values, Sort sort, int page, int pageSize);

    List<T> like(String key, String value);

    List<T> like(String key, String value, Sort sort);

    Page<T> like(String key, String value, Sort sort, int page, int pageSize);

    List<T> likeAll(String key, String value);

    List<T> likeAll(String key, String value, Sort sort);

    Page<T> likeAll(String key, String value, Sort sort, int page, int pageSize);

    List<T> like(Map<String, String> conditions);

    List<T> like(Map<String, String> conditions, Sort sort);

    Page<T> like(Map<String, String> conditions, Sort sort, int page, int pageSize);

    List<T> likeAll(Map<String, String> conditions);

    List<T> likeAll(Map<String, String> conditions, Sort sort);

    Page<T> likeAll(Map<String, String> conditions, Sort sort, int page, int pageSize);

    List<T> leftlike(String key, String value);

    List<T> leftlike(String key, String value, Sort sort);

    Page<T> leftlike(String key, String value, Sort sort, int page, int pageSize);

    List<T> leftlike(Map<String, String> conditions);

    List<T> leftlike(Map<String, String> conditions, Sort sort);

    Page<T> leftlike(Map<String, String> conditions, Sort sort, int page, int pageSize);

    List<T> leftlikeAll(String key, String value);

    List<T> leftlikeAll(String key, String value, Sort sort);

    Page<T> leftlikeAll(String key, String value, Sort sort, int page, int pageSize);

    List<T> leftlikeAll(Map<String, String> conditions);

    List<T> leftlikeAll(Map<String, String> conditions, Sort sort);

    Page<T> leftlikeAll(Map<String, String> conditions, Sort sort, int page, int pageSize);

    List<T> rightlike(String key, String value);

    List<T> rightlike(String key, String value, Sort sort);

    Page<T> rightlike(String key, String value, Sort sort, int page, int pageSize);

    List<T> rightlike(Map<String, String> conditions);

    List<T> rightlike(Map<String, String> conditions, Sort sort);

    Page<T> rightlike(Map<String, String> conditions, Sort sort, int page, int pageSize);
}
