package com.caiguantianxia.database.repository;

import com.caiguantianxia.database.repository.model.BaseModelPO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.List;

/**
 * @author : Hongtu Zang
 * @date : Created in 下午1:10 19-5-27
 */
@NoRepositoryBean
public interface BaseRepository<T extends BaseModelPO, ID extends Serializable>
        extends JpaRepository<T, ID> {
    T create(T model) throws Exception;

    /**
     * 逻辑删除, available = false
     *
     * @param id
     * @return
     */
    T delete(ID id);

    /**
     * 物理删除
     *
     * @param id
     * @return
     */
    T physicalDelete(ID id);

    /**
     * 更新，自动设置update_time
     *
     * @param model
     * @return
     */
    T update(T model);

    T get(ID id);

    /**
     * 查询，available=true
     *
     * @return
     */
    List<T> list();

    /**
     * 查询，available=true
     *
     * @param model
     * @return
     */
    List<T> list(T model);

    /**
     * 查询，available=true
     *
     * @param model
     * @param sort
     * @return
     */
    List<T> list(T model, Sort sort);

    /**
     * 查询，available=true
     *
     * @param model
     * @param sort
     * @param page
     * @param pageSize
     * @return
     */
    Page<T> list(T model, Sort sort, int page, int pageSize);

    List<T> listAll();

    List<T> listAll(T model);

    List<T> listAll(T model, Sort sort);

    Page<T> listAll(T model, Sort sort, int page, int pageSize);

}
