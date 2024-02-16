package ua.com.alevel;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        MatList<Integer> matList1 = new MatList<>();
        MatList<Integer> matList2 = new MatList<>(new Integer[]{2, 4, 6}, new Integer[]{100}, new Integer[]{1, 3, 8},
                new Integer[]{10, 20, 30, 40, 50});
        matList1.add(1);
        matList1.add(2, 3, 8);
        MatList<Integer> matList3 = new MatList<>(matList1, matList2);
        MatList<Integer> matList4 = new MatList<>();
        matList4.join(matList3);
        System.out.println("matList1.toArray " + Arrays.toString(matList1.toArray()));
        System.out.println("matList2.toArray " + Arrays.toString(matList2.toArray()));
        System.out.println("matList3.toArray " + Arrays.toString(matList3.toArray()));
        System.out.println("matList4.toArray " + Arrays.toString(matList4.toArray()));
        matList4.intersection(matList1);
        System.out.println("matList4.intersection(matList1) " + Arrays.toString(matList4.toArray()));
        matList1.sortDesc(2);
        matList2.sortAsc();
        matList3.sortDesc(3, 8);
        System.out.println("matList1.sortDesc(2) " + Arrays.toString(matList1.toArray()));
        System.out.println("matList2.sortAsc() " + Arrays.toString(matList2.toArray()));
        System.out.println("matList3.sortDesc(3, 8) " + Arrays.toString(matList3.toArray()));
        System.out.println("matList1.get(1) " + matList1.get(1));
        System.out.println("matList2.getMax() " + matList2.getMax());
        System.out.println("matList3.getMin() " + matList3.getMin());
        System.out.println("matList3.getAverage() " + matList3.getAverage());
        System.out.println("matList3.getMedian() " + matList3.getMedian());
        System.out.println("matList3.toArray(3, 7) " + Arrays.toString(matList3.toArray(3, 7)));
        matList3.cut(1, 6);
        System.out.println("matList3.cut(1,6) " + Arrays.toString(matList3.toArray()));
        matList3.clear();
        System.out.println("matList3.clear() " + Arrays.toString(matList3.toArray()));
        matList2.clear(new Number[]{1, 2, 3, 4, 5, 6, 7, 40});
        System.out.println("matList2.clear(new Number[]{1,2,3,4,5,6,7,40}) " + Arrays.toString(matList2.toArray()));
    }
}
