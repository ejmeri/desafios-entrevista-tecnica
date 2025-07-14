import java.util.HashMap;
import java.util.Map;

class TwoSums {
    public static void main(String[] args) {
        int[] nums = { 2, 7, 11, 15 };
        int target = 9;

        int[] result = twoSum(nums, target);
        System.out.println(result[0] + " " + result[1]);

        int[] result2 = twoSumHashTable(nums, target);
        System.out.println(result2[0] + " " + result2[1]);
    }

    // Brute force solution
    public static int[] twoSum(int[] nums, int target) {
        System.out.println("Brute force solution");

        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] + nums[j] == target) {
                    return new int[] { i, j };
                }
            }
        }

        throw new IllegalArgumentException("No two sum solution found");
    }

    // Hash table solution
    public static int[] twoSumHashTable(int[] nums, int target) {
        System.out.println("Hash table solution");

        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];
            if (map.containsKey(complement)) {
                return new int[] { map.get(complement), i };
            }
            map.put(nums[i], i);
        }

        throw new IllegalArgumentException("No two sum solution found");
    }
}
