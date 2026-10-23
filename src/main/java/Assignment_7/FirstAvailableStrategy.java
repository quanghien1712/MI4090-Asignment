/*
    Trần Quang Hiển
    202418893
 */

package Assignment_7;

import Assignment_7.exception.NoSuitableItemException;
import java.util.List;

public class FirstAvailableStrategy<T> implements ISelectionStrategy<T> {
    @Override
    public T select(List<T> candidates) throws NoSuitableItemException {
        if (candidates == null) {
            throw new IllegalArgumentException("candidates khong duoc null");
        }
        if (candidates.isEmpty())
            throw new NoSuitableItemException();
        return candidates.get(0);
    }
}