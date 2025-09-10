import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import components.queue.Queue;
import components.queue.Queue1L;

/**
 * Layered implementations of secondary method {@code sort} for
 * {@code Queue<String>}.
 */
public final class Queue1LSort1 extends Queue1L<String> {

    /**
     * No-argument constructor.
     */
    public Queue1LSort1() {
        super();
    }

    /**
     * Removes and returns the minimum value from {@code q} according to the
     * ordering provided by the {@code compare} method from {@code order}.
     *
     * @param q
     *            the queue
     * @param order
     *            ordering by which to compare entries
     * @return the minimum value from {@code q}
     * @updates q
     * @requires <pre>
     * q /= empty_string  and
     *  [the relation computed by order.compare is a total preorder]
     * </pre>
     * @ensures <pre>
     * perms(q * <removeMin>, #q)  and
     *  for all x: string of character
     *      where (x is in entries (q))
     *    ([relation computed by order.compare method](removeMin, x))
     * </pre>
     */
    private static String removeMin(Queue<String> q, Comparator<String> order) {
        assert q != null : "Violation of: q is not null";
        assert order != null : "Violation of: order is not null";

        List<String> elements = new ArrayList<>();
        // Dequeue all elements into the list
        while (q.length() > 0) {
            elements.add(q.dequeue());
        }
        // Find the minimum element
        String minVal = elements.get(0);
        for (String s : elements) {
            if (order.compare(s, minVal) < 0) {
                minVal = s;
            }
        }
        // Remove the first occurrence of minVal
        elements.remove(minVal);
        // Enqueue all elements back into the queue
        for (String s : elements) {
            q.enqueue(s);
        }
        return minVal;
    }

    @Override
    public void sort(Comparator<String> order) {
        assert order != null : "Violation of: order is not null";

        // Assuming Queue has a default constructor
        Queue<String> tempQueue = new Queue1L<>();
        // Repeatedly remove the minimum element from q and add it to tempQueue
        while (this.length() > 0) {
            String min = removeMin(this, order);
            tempQueue.enqueue(min);
        }
        // Transfer the sorted elements back to the original queue
        while (tempQueue.length() > 0) {
            this.enqueue(tempQueue.dequeue());
        }
    }

}
