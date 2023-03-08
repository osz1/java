public class MainLargestKNumberInAArray {
    public static void main(String[] args) {
        int[] A = new int[] {2, 4, 6, 8, 10, 12, 14, 16, 18, 20, 22};
        int k = A[0];
        int sumOfNumbers;
        for (int i = 0; i < A.length; i++) {
            for (int j = (i + 1); j < A.length; j++) {
                for (int m = (j + 1); m < A.length; m++) {
                    sumOfNumbers = A[i] + A[j] + A[m];
                    if (sumOfNumbers > k) {
                        for (int n = (m + 1); n < A.length; n++) {
                            if (sumOfNumbers == A[n]) {
                                k = sumOfNumbers;
                            }
                        }
                    }
                }
            }
        }
        System.out.println(k);
    }
}