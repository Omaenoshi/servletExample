package org.example.demo.service;

import org.example.demo.dao.UserDAO;
import org.example.demo.db.HibernateSessionFactoryUtil;
import org.example.demo.entity.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.tool.schema.spi.SqlScriptException;

public class UserService implements UserDAO {
    @Override
    public User getByLogin(String login) throws SqlScriptException {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        return session.byNaturalId(User.class).using("login", login).load();
    }

    @Override
    public void add(User user) throws SqlScriptException {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.persist(user);
        transaction.commit();
        session.close();
    }
}
