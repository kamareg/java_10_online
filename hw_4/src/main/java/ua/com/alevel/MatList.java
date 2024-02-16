package ua.com.alevel;

public class MatList<E extends Number> {

    private E[] matList;
    private int size;

    public MatList() {
        clear();
    }

    public MatList(E[]... numbers) {
        this();
        for (E[] number : numbers) {
            add(number);
        }
    }

    public MatList(MatList<E>... numbers) {
        this();
        join(numbers);
    }

    public void add(E n) {
        if (n != null) {
            if (matList.length <= size) {
                createNewArray();
            }
            matList[size++] = n;
        }
    }

    public void add(E... n) {
        for (E e : n) {
            add(e);
        }
    }

    public void join(MatList<E>... ml) {
        for (MatList<E> eMatList : ml) {
            add((E[]) eMatList.toArray());
        }
    }

    public void intersection(MatList<E>... ml) {
        E[] copy = (E[]) new Number[matList.length];
        int copySize = 0;
        for (E e : matList) {
            boolean present = true;
            for (MatList<E> eMatList : ml) {
                if (!isContains((E[]) eMatList.toArray(), e)) {
                    present = false;
                }
            }
            if (present) {
                copy[copySize++] = e;
            }
        }
        matList = copy;
        size = copySize;
    }

    public void sortDesc() {
        sortDesc(0, size - 1);
    }

    public void sortDesc(int firstIndex, int lastIndex) {
        sort(firstIndex, lastIndex, false);
    }

    public void sortDesc(E value) {
        int index = findIndexByValue(value);
        if (index != -1) {
            sortDesc(index, size - 1);
        }
    }

    public void sortAsc() {
        sortAsc(0, size - 1);
    }

    public void sortAsc(int firstIndex, int lastIndex) {
        sort(firstIndex, lastIndex, true);
    }

    public void sortAsc(E value) {
        int index = findIndexByValue(value);
        if (index != -1) {
            sortAsc(index, size - 1);
        }
    }

    public Number get(int index) {
        return matList[index];
    }

    public Number getMax() {
        MatList<E> ml = new MatList<>(this);
        ml.sortDesc();
        return ml.get(0);
    }

    public Number getMin() {
        MatList<E> ml = new MatList<>(this);
        ml.sortAsc();
        return ml.get(0);
    }

    public Number getAverage() {
        double sum = 0.0;
        for (E e : matList) {
            if (e != null) {
                sum += e.doubleValue();
            }
        }
        return sum / size;
    }

    public Number getMedian() {
        if (size == 0) {
            return 0;
        }
        MatList<E> ml = new MatList<>(this);
        ml.sortDesc();
        if (size % 2 == 0) {
            return (ml.get((size / 2) - 1).doubleValue() + ml.get(size / 2).doubleValue()) / 2;
        }
        return ml.get(size / 2);
    }

    public Number[] toArray() {
        Number[] numbers = new Number[size];
        int copySize = 0;
        for (int i = 0; i < size; i++) {
            if (matList[i] != null) {
                numbers[copySize++] = matList[i];
            }
        }
        return numbers;
    }

    public Number[] toArray(int firstIndex, int lastIndex) {
        if (!(firstIndex < 0 || lastIndex >= size || firstIndex > lastIndex)) {
            MatList<E> ml = new MatList<>();
            for (int i = firstIndex; i <= lastIndex; i++) {
                ml.add(matList[i]);
            }
            return ml.toArray();
        }
        return null;
    }

    public MatList<E> cut(int firstIndex, int lastIndex) {
        if (!(firstIndex < 0 || lastIndex >= size || firstIndex > lastIndex)) {
            System.arraycopy(matList, lastIndex + 1, matList, firstIndex, size - lastIndex - 1);
            size -= (lastIndex - firstIndex + 1);
            E[] copy = (E[]) new Number[size];
            System.arraycopy(matList, 0, copy, 0, size);
            matList = copy;
            return this;
        }
        return null;
    }

    public void clear() {
        matList = (E[]) new Number[10];
        size = 0;
    }

    public void clear(Number[] numbers) {
        E[] copy = (E[]) new Number[matList.length];
        int copySize = 0;
        for (int i = 0; i < size; i++) {
            if (!isContains((E[]) numbers, matList[i])) {
                copy[copySize++] = matList[i];
            }
        }
        matList = copy;
        size = copySize;
    }

    private void createNewArray() {
        E[] copy = (E[]) new Number[matList.length * 2];
        System.arraycopy(matList, 0, copy, 0, matList.length);
        matList = copy;
    }

    private boolean isContains(E[] matList, E value) {
        for (E e : matList) {
            if (e != null && e.equals(value)) {
                return true;
            }
        }
        return false;
    }

    private int findIndexByValue(E value) {
        if (value != null) {
            for (int i = 0; i < matList.length; i++) {
                if (matList[i] != null && matList[i].equals(value)) {
                    return i;
                }
            }
        }
        return -1;
    }

    private void sort(int firstIndex, int lastIndex, boolean asc) {
        if (!(firstIndex < 0 || lastIndex >= size || firstIndex >= lastIndex)) {
            for (int i = firstIndex; i < lastIndex; i++) {
                for (int j = i + 1; j <= lastIndex; j++) {
                    if (asc ? matList[i].doubleValue() > matList[j].doubleValue() :
                            matList[i].doubleValue() < matList[j].doubleValue()) {
                        E temp = matList[i];
                        matList[i] = matList[j];
                        matList[j] = temp;
                    }
                }
            }
        }
    }
}
