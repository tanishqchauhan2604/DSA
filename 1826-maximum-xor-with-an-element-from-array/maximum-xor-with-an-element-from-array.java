class Solution {
    class TrieNode {
        TrieNode[] nodes;
        TrieNode() {
            nodes = new TrieNode[2];
        }
    }

    public void insertIntoTrie(TrieNode root, int n) {
        TrieNode currNode = root;
        for(int i=31;i>=0;i--) {
            // ith bit starting from the first bit of the number
            int currBit = (n & (1 << i)) == 0 ? 0 : 1;
            // if reference not present, create reference
            if(currNode.nodes[currBit] == null) {
                currNode.nodes[currBit] = new TrieNode();
            }
            currNode = currNode.nodes[currBit];
        }
    }

    public int calcMaxXor(TrieNode root, int num) {
        TrieNode currNode = root;
        int max = 0, currNum = 0;
        for(int i=31;i>=0;i--) {
            // ith bit starting from thr first bit, but reversed using ^1
            int xorBit = 1 ^ ((num & (1 << i)) == 0 ? 0 : 1);
            if(currNode.nodes[xorBit] == null) {
                currNode = currNode.nodes[(xorBit ^ 1)];
            } else {
                currNode = currNode.nodes[xorBit];
                // OR with the existing num to make 0s to 1s for the new bit
                currNum |= (1 << i);
                max = Math.max(max, currNum);
            }
        }
        return max;
    }

    
    class State {
        int index;
        int[] query;
        State(int i, int[] q) {
            index = i;
            query = q;
        }
    }

    public int[] maximizeXor(int[] nums, int[][] queries) {
        Arrays.sort(nums);
        List<State> al = new ArrayList<>();
        for(int i=0;i<queries.length;i++) {
            al.add(new State(i, queries[i]));
        }
        Collections.sort(al, (a, b) -> a.query[1] - b.query[1]);
        TrieNode root = new TrieNode();
        int[] ans = new int[queries.length];
        int j = 0;
        for(int i=0;i<al.size();i++) {
            int queryIndex = al.get(i).index;

            int n = al.get(i).query[1];
            while(j < nums.length && nums[j] <= n) {
                insertIntoTrie(root, nums[j]);
                j++;
            }
            if(j == 0) {
                ans[queryIndex] = -1;
                continue;
            }
            int num = al.get(i).query[0];
            
            int maxXor = calcMaxXor(root, num);

            ans[queryIndex] = maxXor;
        }


        return ans;
    }
}