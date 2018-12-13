package advent;

import java.util.ArrayDeque;

public class CircularDeque<T> extends ArrayDeque<T>
{
    public void rotate(int shift)
    {
        if (shift == 0) return;

        if (shift > 0)
        {
            for (int i = 0; i < shift; i++)
            {
                T item = this.removeLast();
                this.addFirst(item);
            }
        }
        else
        {
            for (int i = 0; i < Math.abs(shift) - 1; i++)
            {
                T item = this.remove();
                this.addLast(item);
            }
        }
    }
}
