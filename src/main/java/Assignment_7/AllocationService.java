/*
    Trần Quang Hiển
    202418893
 */

package Assignment_7;

import Assignment_7.exception.DeviceNotReadyException;
import Assignment_7.exception.NoSuitableItemException;
import java.util.List;
import java.util.function.Predicate;

public class AllocationService<T extends TeachingDevice> {
    private final IRepository<T> repository;
    private ISelectionStrategy<T> strategy;

    public AllocationService(IRepository<T> repository, ISelectionStrategy<T> strategy) {
        if (repository == null) {
            throw new IllegalArgumentException("candidates khong duoc null");
        }
        if (strategy == null) {
            throw new IllegalArgumentException("candidates khong duoc null");
        }
        this.repository = repository;
        this.strategy = strategy;
    }

    public void changeStrategy(ISelectionStrategy<T> newStrategy) {
        if (newStrategy == null) {
            throw new IllegalArgumentException("newStrategy khong duoc null");
        }
        this.strategy = newStrategy;
    }

    public T allocate(Predicate<T> additionalCondition) throws NoSuitableItemException, DeviceNotReadyException {
        List<T> candidates = repository.findAll(device -> device.isReadyForUse() &&
              (additionalCondition == null || additionalCondition.test(device)));
        T selected = strategy.select(candidates);
        selected.borrow();
        return selected;
    }
}