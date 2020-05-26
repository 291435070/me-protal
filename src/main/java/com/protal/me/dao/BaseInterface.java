package com.protal.me.dao;

import java.util.List;

public interface BaseInterface<T> {

    boolean insert(T t);

    boolean delete(T t);

    boolean update(T t);

    T select(T t);

    List<T> list(T t);

}