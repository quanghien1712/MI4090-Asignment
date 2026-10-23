/*
    Trần Quang Hiển
    202418893
 */

package Assignment_7;

import java.util.List;
import java.util.function.Predicate;

public interface IRepository<T extends IIdentifiable> {
    void add(T item);

    boolean remove(String id);

    T findById(String id);

    List<T> getAll();

    List<T> findAll(Predicate<T> condition);
}