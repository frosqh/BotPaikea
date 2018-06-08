package com.frosqh.botpaikea.server.core.DataBase;

import java.sql.Connection;
import java.util.ArrayList;

/**
 * Gestionnaire de la base de données.
 * @see DataBase
 * @param <T> Le type des éléments à manipuler.
 * @author Frosqh
 * @version 0.1
 */
public abstract class DAO<T> {
    Connection connect = ConnectionSQLite.getInstance();

    public abstract T find(int id);

    public abstract T create(T obj);

    public abstract T update(T obj);

    public abstract void delete(T obj);

    public abstract ArrayList<T> getList();

    public abstract ArrayList<T> filter(String[] args);
}
