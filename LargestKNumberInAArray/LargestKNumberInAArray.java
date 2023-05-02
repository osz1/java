/**
 * <h1>Legnagyobb k szám A halmazban</h1>
 * 
 * <p>A halmaz sorba van rendezve.</p>
 */
public class LargestKNumberInAArray {

    /**
     * <p>Legnagyobb k szám A halmazban.</p>
     * 
     * @param array A halmaz
     * @return legnagyobb k szám
     */
    public static int largestKNumber(int[] array) {
        int k = array[0];

        int sumOfNumbers;

        for (int i = 0; i < array.length; i++) {
            for (int j = (i + 1); j < array.length; j++) {
                for (int m = (j + 1); m < array.length; m++) {
                    sumOfNumbers = array[i] + array[j] + array[m];
                    if (sumOfNumbers > k) {
                        for (int n = (m + 1); n < array.length; n++) {
                            if (sumOfNumbers == array[n]) {
                                k = sumOfNumbers;
                            }
                        }
                    }
                }
            }
        }

        return k;
    }

    public static void main(String[] args) {
        int[] A = new int[] {2, 4, 6, 8, 10, 12, 14, 16, 18, 20, 22};

        System.out.println("legnagyobb K sz\u00E1m: " + largestKNumber(A));
    }
}