/*
    Trần Quang Hiển
    202418893
 */

package Assignment_7;

import Assignment_7.exception.NoSuitableItemException;
import java.util.List;
import java.util.function.ToDoubleFunction;

public class HighestPriorityStrategy<T> implements ISelectionStrategy<T> {
    private final ToDoubleFunction<T> scoreSelector;

    public HighestPriorityStrategy(ToDoubleFunction<T> scoreSelector) {
        if (scoreSelector == null) {
            throw new IllegalArgumentException("scoreSelector khong duoc null");
        }
        this.scoreSelector = scoreSelector;
    }

    @Override
    public T select(List<T> candidates) throws NoSuitableItemException {
        if (candidates == null) {
            throw new IllegalArgumentException("candidates khong duoc null");
        }
        if (candidates.isEmpty())
            throw new NoSuitableItemException();

        T best = candidates.get(0);
        double bestScore = scoreSelector.applyAsDouble(best);

        for (int i = 1; i < candidates.size(); i++) {
            double score = scoreSelector.applyAsDouble(candidates.get(i));
            if (score > bestScore) {
                best = candidates.get(i);
                bestScore = score;
            }
        }
        return best;
    }
}