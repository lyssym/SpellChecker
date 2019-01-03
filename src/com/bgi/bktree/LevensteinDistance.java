package com.bgi.bktree;

public class LevensteinDistance implements MetricSpace<String> {
    private double insertCost = 1;           // 可以写成插入的函数，做更精细化处理
    private double deleteCost = 1;           // 可以写成删除的函数，做更精细化处理
    private double substitudeCost = 1.5;     // 可以写成替换的函数，做更精细化处理, 比如使用键盘距离

    /**
     * 编辑距离计算
     * @param target
     * @param source
     * @return
     */
    public double computeDistance(String target, String source) {
        int n = target.trim().length();
        int m = source.trim().length();
        double[][] distance = new double[n + 1][m + 1];

        distance[0][0] = 0;
        for (int i = 1; i <= m; i++) {
        	distance[0][i] = i;
        }

        for (int j = 1; j <= n; j++) {
            distance[j][0] = j;
        }

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                double min = distance[i-1][j] + insertCost;  // 插入
                if (target.charAt(i-1) == source.charAt(j-1)) {
                    if (min > distance[i-1][j-1]) {
                        min = distance[i-1][j-1];
                    }
                } else {
                    if (min > distance[i-1][j-1] + substitudeCost) {
                        min = distance[i-1][j-1] + substitudeCost; // 替换
                    }
                }
                
                if (min > distance[i][j-1] + deleteCost) { // 删除
                    min = distance[i][j-1] + deleteCost;
                }

                distance[i][j] = min;
            }
        }

        return distance[n][m];
    }

    
    /**
     * 通用编辑距离
     * @param object1
     * @param object2
     * @return
     */
	public int getDistance(Object object1, Object object2) {
		int distance[][];  // distance matrix
        int n;             // length of first string
        int m;             // length of second string
        int i;             // iterates through first string
        int j;             // iterates through second string
        char s_i;          // i-th character of first string
        char t_j;          // j-th character of second string
        int cost;          // cost

		String src = (String)object1;
		String dst = (String)object2;

        // Step 1
        n = src.length();
        m = dst.length();
        if (n == 0) {
            return m;
        }

        if (m == 0) {
            return n;
        }
        
        distance = new int[n + 1][m + 1];
        // Step 2
        for (i = 0; i <= n; i++) {
            distance[i][0] = i;
        }

        for (j = 0; j <= m; j++) {
            distance[0][j] = j;
        }

        // Step 3
        for (i = 1; i <= n; i++) {
            s_i = src.charAt(i - 1);
            // Step 4
            for (j = 1; j <= m; j++) {
                t_j = dst.charAt(j - 1);
                // Step 5
                if (s_i == t_j) {
                    cost = 0;
                } else {
                    cost = 1;
                }

                // Step 6
                distance[i][j] = findMinimum(distance[i - 1][j] + 1, 
                		distance[i][j - 1] + 1, distance[i - 1][j - 1] + cost);
            }
        }

        // Step 7
        return distance[n][m];
	}


    private int findMinimum(int a, int b, int c) {
        int min = a;
        if (b < min) {
            min = b;
        }

        if (c < min) {
            min = c;
        }

        return min;
    }


    @Override
    public double distance(String a, String b) {
        return computeDistance(a, b);
    }
    
    
    public double levensteinDistancePercent(String src, String dst) {
    	int maxLength = src.length() > dst.length() ? src.length() : dst.length();
    	double score = distance(src, dst);
    	return 1 - (score) / maxLength;
    }

    
    
    public static void main(String[] args) {
        LevensteinDistance distance = new LevensteinDistance();
        System.out.println(distance.computeDistance("hello", "hellt"));
    }
}
