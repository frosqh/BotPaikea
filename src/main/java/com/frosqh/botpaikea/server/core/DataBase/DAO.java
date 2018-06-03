package com.frosqh.botpaikea.server.core.DataBase;

import java.sql.Connection;
import java.util.ArrayList;

public abstract class DAO<T> {
    public Connection connect = ConnectionSQLite.getInstance();

    public abstract T find(int id);

    public abstract T create(T obj);

    public abstract T update(T obj);

    public abstract void delete(T obj);

    public abstract ArrayList<T> getList();

    public abstract ArrayList<T> filter(String[] args);
}
