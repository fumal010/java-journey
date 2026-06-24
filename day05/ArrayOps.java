public class ArrayOps {

    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 5};
        sum(arr);
        average(arr);
        reverse(arr);
        isSorted(arr, true);
        isSorted(arr, false);
    }

    public static long sum(int[] arr) {
        long total = sumValues(arr);

        System.out.println(total);
        return total;
    }



    public static double average(int[] arr) {
        double result = (double) sumValues(arr) / arr.length;

        System.out.println(result);
        return result;
    }



    public static int[] reverse(int[] arr) {
        int[] reversed = new int[arr.length];

        for (int i = 0; i < arr.length; i++) {
            reversed[i] = arr[arr.length - 1 - i];
        }

        System.out.println(formatArray(reversed));
        return reversed;
    }



    public static boolean isSorted(int[] arr, boolean ascending) {
       
        for (int i = 1; i < arr.length; i++) {
            if (ascending) {
                if (arr[i] < arr[i - 1]) {
                    System.out.println(false);
                    return false;
                }
            } else {
                if (arr[i] > arr[i - 1]) {
                    System.out.println(false);
                    return false;
                }
            }
        }

        System.out.println(true);
        return true;
    }



    private static long sumValues(int[] arr) {
        long total = 0L;
        for (int value : arr) {
            total += value;
        }
        return total;
    }
    

    private static String formatArray(int[] arr) {
        StringBuilder builder = new StringBuilder("[");

        for (int i = 0; i < arr.length; i++) {
            if (i > 0) {
                builder.append(", ");
            }
            builder.append(arr[i]);
        }

        builder.append(']');
        return builder.toString();
    }



    public static int[] merge(int[] a, int[] b) {

        int[] merged = new int[a.length + b.length];
        int i = 0;
        int j = 0;
        int k = 0;
        while (i < a.length && j < b.length) {
            if (a[i] <= b[j]) {
                merged[k++] = a[i++];
            } else {
                merged[k++] = b[j++];
            }
        }
        while (i < a.length) {
            merged[k++] = a[i++];
        }
        while (j < b.length) {
            merged[k++] = b[j++];
        }
        return merged;
    }



    public static int[] removeDuplicates(int[] arr) {
        int[] buffer = new int[arr.length];
        int uniqueCount = 0;

        for (int i = 0; i < arr.length; i++) {
            boolean seen = false;
            for (int j = 0; j < uniqueCount; j++) {
                if (buffer[j] == arr[i]) {
                    seen = true;
                    break;
                }
            }
            if (!seen) {
                buffer[uniqueCount++] = arr[i];
            }
        }

        int[] result = new int[uniqueCount];

        for (int i = 0; i < uniqueCount; i++) {
            result[i] = buffer[i];
        }

        return result;
    }



    public static int[] rotate(int[] arr, int k) {

        int length = arr.length;

        int shiftValue = k % length;
        if (shiftValue < 0) {
            shiftValue += length;
        }
        if (shiftValue == 0) {
            int[] copy = new int[length];
            System.arraycopy(arr, 0, copy, 0, length);
            return copy;
        }
        int[] rotated = new int[length];
        for (int i = 0; i < length; i++) {
            rotated[(i + shiftValue) % length] = arr[i];
        }
        return rotated;
    }




    public static int[][] multiplyMatrices(int[][] leftMatrix, int[][] rightMatrix) {

        int leftColumnCount = leftMatrix[0].length;
        int rightRowCount = rightMatrix.length;


        int rightColumnCount = rightMatrix[0].length;
        int rowCount = leftMatrix.length;

        int[][] resultMatrix = new int[rowCount][rightColumnCount];

        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            if (leftMatrix[rowIndex].length != leftColumnCount) {
                throw new IllegalArgumentException("Left matrix is not valid");
            }

            for (int columnIndex = 0; columnIndex < rightColumnCount; columnIndex++) {
                int dotProductSum = 0;
                for (int sharedIndex = 0; sharedIndex < leftColumnCount; sharedIndex++) {
                    if (rightMatrix[sharedIndex].length != rightColumnCount) {
                        throw new IllegalArgumentException("Right matrix is not valid");
                    }
                    dotProductSum += leftMatrix[rowIndex][sharedIndex]
                            * rightMatrix[sharedIndex][columnIndex];
                }
                resultMatrix[rowIndex][columnIndex] = dotProductSum;
            }
        }

        return resultMatrix;
    }




    public static int findPeakElement(int[] arr) {
        if (arr.length == 1) {
            return 0;
        }
        for (int i = 0; i < arr.length; i++) {
            boolean greaterThanLeft = i == 0 || arr[i] > arr[i - 1];
            boolean greaterThanRight = i == arr.length - 1 || arr[i] > arr[i + 1];
            if (greaterThanLeft && greaterThanRight) {
                return i;
            }
        }
    }
}
