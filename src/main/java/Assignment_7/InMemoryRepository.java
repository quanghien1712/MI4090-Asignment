/*
    Trần Quang Hiển
    202418893
 */

package Assignment_7;

import Assignment_7.exception.DuplicateIdException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class InMemoryRepository<T extends IIdentifiable> implements IRepository<T> {
    private final Map<String, T> itemsById = new LinkedHashMap<>();

    @Override
    public void add(T item) throws DuplicateIdException {
        if (item == null) {
            throw new IllegalArgumentException("item khong duoc null");
        }
        String id = item.getId();
        if (itemsById.containsKey(id))
            throw new DuplicateIdException(id);
        itemsById.put(id, item);
    }

    @Override
    public boolean remove(String id) {
        if (id == null) return false;
        return itemsById.remove(id) != null;
    }

    @Override
    public T findById(String id) {
        if (id == null) return null;
        return itemsById.get(id);
    }

    @Override
    public List<T> getAll() {
        return List.copyOf(itemsById.values());
    }

    @Override
    public List<T> findAll(Predicate<T> condition) {
        if (condition == null) {
            throw new IllegalArgumentException("condition khong duoc null");
        }
        return itemsById.values().stream().filter(condition).toList();
    }
}