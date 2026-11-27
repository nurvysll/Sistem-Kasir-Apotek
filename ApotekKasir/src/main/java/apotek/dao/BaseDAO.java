package apotek.dao;

import java.sql.SQLException;
import java.util.List;

/** Kontrak CRUD generik. T = tipe model, K = tipe primary key. */
public interface BaseDAO<T, K> {
    List<T> findAll() throws SQLException;

    T findById(K id) throws SQLException;

    void insert(T data) throws SQLException;

    void update(T data) throws SQLException;

    void delete(K id) throws SQLException;
}
