package de.seven.fate.moa.dao;

import de.seven.fate.model.util.ClassUtil;
import de.seven.fate.model.util.CollectionUtil;
import org.apache.commons.beanutils.BeanUtils;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.Validate.notNull;


public abstract class AbstractEntityDAO<E extends IdAble<I>, I extends Serializable> {

    @PersistenceContext(unitName = "moa")
    protected EntityManager em;

    private Class<E> entityType;

    /**
     * Persist takes an entity instance,
     * adds it to the context and makes that instance managed
     * (ie future updates to the entity will be tracked).
     *
     * @param entity
     */
    public void save(E entity) {
        notNull(entity);

        saveImpl(entity);

        em.flush();
    }

    public void save(Collection<E> entities) {

        Optional.ofNullable(entities).orElse(Collections.emptyList()).forEach(this::save);

        em.flush();
    }


    /**
     * Check for existing entity
     * and if found copy properties to existing one
     * and then merge else save entity
     *
     * @param entity
     * @return entity
     */
    public E saveOrUpdate(E entity) {
        notNull(entity);

        if (entity.getId() == null) {
            save(entity);

            return entity;
        }

        return update(entity);
    }

    public void saveOrUpdate(Collection<E> entities) {

        Optional.ofNullable(entities).orElse(Collections.emptyList()).forEach(this::update);
    }

    public E update(E entity) {
        notNull(entity);

        return saveOrUpdate(get(entity), entity);
    }

    protected E saveOrUpdate(E recent, E entity) {
        notNull(entity);

        if (recent == null) {
            save(entity);
            return entity;
        } else if (recent.equals(entity)) {
            return recent;
        }

        updateProperties(recent, entity);

        return mergeImpl(recent);
    }

    public void updateProperties(E recent, E entity) {
        notNull(recent);
        notNull(entity);

        try {
            BeanUtils.copyProperties(recent, entity);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * @param id
     * @return founded entity or null
     */
    public E get(I id) {
        notNull(id);

        return em.find(entityType, id);
    }

    /**
     * @return founded entity by id or throw NoSuchEntityException
     */
    public E get(E entity) {
        notNull(entity);

        if (entity.getId() == null) {
            return null;
        }

        if (em.contains(entity)) {
            return entity;
        }

        return get(entity.getId());
    }

    /**
     * @param entity
     */
    public void remove(E entity) {
        notNull(entity);

        removeImpl(entity);
    }

    /**
     * remove entity by entityId
     *
     * @param entityId
     */
    public void remove(I entityId) {
        notNull(entityId);

        removeImpl(get(entityId));
    }

    /**
     * @param entities
     */
    public void remove(Collection<E> entities) {

        Optional.ofNullable(entities).orElse(Collections.emptyList()).forEach(this::remove);
    }

    public void removeAll() {
        remove(list());
    }

    public List<E> list() {
        return list(-1, -1);
    }

    public List<E> list(int startPosition, int maxResult) {

        CriteriaBuilder builder = em.getCriteriaBuilder();

        CriteriaQuery<E> criteria = builder.createQuery(entityType);
        Root<E> from = criteria.from(entityType);

        criteria.select(from);

        TypedQuery<E> query = em.createQuery(criteria);

        if (startPosition > -1) {
            query.setFirstResult(startPosition);
        }

        if (maxResult > -1) {
            query.setMaxResults(maxResult);
        }

        return query.getResultList();
    }

    public Long count() {

        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Long> criteria = builder.createQuery(Long.class);

        Root<E> from = criteria.from(entityType);

        criteria.select(builder.count(from));

        return em.createQuery(criteria).getSingleResult();
    }

    public TypedQuery<E> createNamedQuery(String namedQuery, Object... params) {
        notNull(namedQuery);

        TypedQuery<E> query = em.createNamedQuery(namedQuery, entityType);

        Map<String, Object> parameters = CollectionUtil.createMap(params);

        parameters.forEach(query::setParameter);

        return query;
    }

    @PostConstruct
    private void init() {

        entityType = ClassUtil.getGenericType(getClass());
    }

    protected void saveImpl(E entity) {
        assert entity != null;

        em.persist(entity);
    }

    protected void removeImpl(E entity) {
        assert entity != null;

        E e = get(entity);

        Optional.of(e).ifPresent(em::remove);
    }

    protected E mergeImpl(E entity) {
        assert entity != null;

        E merge = em.merge(entity);

        em.flush();

        return merge;
    }

    public E attach(E entity) {
        if (entity == null) {
            return null;
        }

        if (isManaged(entity)) {
            return entity;
        }

        return get(entity);
    }

    public List<E> attach(List<E> entries) {

        if (entries == null) {
            return null;
        }

        return entries.stream().map(this::attach).filter((entry) -> entry != null).collect(Collectors.toList());
    }

    public Set<E> attach(Set<E> entries) {

        if (entries == null) {
            return null;
        }

        return entries.stream().map(this::attach).filter((entry) -> entry != null).collect(Collectors.toSet());
    }

    /**
     * Check if the instance is a managed entity instance belonging to the current persistence context.
     *
     * @param entity
     * @return true if entity instance belonging to the current persistence context
     */
    private boolean isManaged(E entity) {
        return em.contains(entity);
    }
}