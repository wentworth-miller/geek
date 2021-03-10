package org.geektimes.projects.user.service.impl;

import java.util.Objects;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.apache.commons.lang.StringUtils;
import org.geektimes.projects.user.domain.User;
import org.geektimes.projects.user.service.UserService;
import org.geektimes.utils.IdGeneratedUtil;
import org.hibernate.HibernateException;

/**
 * 哎，太忙了，以后周日搞
 * 
 * @author xubin
 * @date 2021/3/3 21:11
 */
public class UserServiceImpl implements UserService {

    private final Logger logger = Logger.getLogger(this.getClass().getName());

    @Resource(name = "bean/EntityManager")
    private EntityManager entityManager;

    @Resource(name = "bean/Validator")
    private Validator validator;

    @Override
    public boolean register(User user) {
        // return UserRepositoryFactory.getInstance().save(user);
        user.setId(IdGeneratedUtil.getNext());
        if (StringUtils.isNotBlank(checkParameters(user))) {
            return false;
        }
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        try {
            entityManager.persist(user);
            transaction.commit();
            return true;
        } catch (HibernateException e) {
            logger.warning("db 异常：" + e.getMessage());
            transaction.rollback();
            return false;
        }
    }

    private <T> String checkParameters(T t) {
        StringBuilder sb = new StringBuilder();
        if (Objects.isNull(t)) {
            sb.append("the check obj is null");
        } else {
            try {
                Set<ConstraintViolation<T>> validate = validator.validate(t);
                sb.append(validate.stream().map(item -> item.getPropertyPath() + ": " + item.getMessage())
                    .collect(Collectors.joining(";")));
            } catch (RuntimeException e) {
                sb.append("please check the annotations are all right");
            }
        }
        return sb.toString();
    }

    @Override
    public boolean deregister(User user) {
        try {
            entityManager.remove(user);
            return true;
        } catch (HibernateException e) {
            logger.warning("db 异常：" + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean update(User user) {
        try {
            entityManager.refresh(user);
            return true;
        } catch (HibernateException e) {
            logger.warning("db 异常：" + e.getMessage());
            return false;
        }
    }

    @Override
    public User queryUserById(Long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public User queryUserByNameAndPassword(String name, String password) {
        String sql = "select * from users where name = ? and password = ?";
        TypedQuery<User> query = entityManager.createQuery(sql, User.class);
        query.setParameter(1, name);
        query.setParameter(2, password);
        return query.getSingleResult();
    }
}
