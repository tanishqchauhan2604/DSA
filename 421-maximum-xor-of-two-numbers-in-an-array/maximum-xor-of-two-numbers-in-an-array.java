class Node {
    Node links[] = new Node[2]; // Binary Trie (0 and 1)

    boolean containsKey(int bit) {
        return links[bit] != null;
    }

    Node get(int bit) {
        return links[bit];
    }

    void put(int bit, Node node) {
        links[bit] = node;
    }
}

class Trie {
    private static Node root;

    Trie() {
        root = new Node();
    }

    public static void insert(int num) {
        Node node = root;
        for (int i = 31; i >= 0; i--) {
            int bit = (num >> i) & 1;
            if (!node.containsKey(bit)) {
                node.put(bit, new Node());
            }
            node = node.get(bit);
        }
    }

    public int getMax(int num) {
        Node node = root;
        int maxNum = 0;
        for (int i = 31; i >= 0; i--) {
            int bit = (num >> i) & 1;
            if (node.containsKey(1 - bit)) { // Try to pick the opposite bit to maximize XOR
                maxNum = maxNum | (1 << i);
                node = node.get(1 - bit);
            } else {
                node = node.get(bit);
            }
        }
        return maxNum;
    }
}

class Solution {
    public int findMaximumXOR(int[] nums) {
        Trie trie = new Trie();
        for (int i = 0; i < nums.length; i++) {
            trie.insert(nums[i]); // Insert numbers into Trie
        }
        int maxi = 0;
        for (int i = 0; i < nums.length; i++) {
            maxi = Math.max(maxi, trie.getMax(nums[i])); // Find max XOR
        }
        return maxi;
    }
}
