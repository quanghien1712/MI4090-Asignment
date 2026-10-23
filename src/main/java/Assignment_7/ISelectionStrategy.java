/*
    Trần Quang Hiển
    202418893
 */

package Assignment_7;

import Assignment_7.exception.NoSuitableItemException;

import java.util.List;

public interface ISelectionStrategy<T> {
    T select(List<T> candidates) throws NoSuitableItemException;
}