package org.example.demo.dao;

import org.example.demo.entity.User;
import org.hibernate.tool.schema.spi.SqlScriptException;

public interface UserDAO {

    User getByLogin(String login) throws SqlScriptException;

    void add(User user) throws SqlScriptException;
}
