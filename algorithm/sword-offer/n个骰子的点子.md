#n个骰子的点子
##描述
把n个骰子扔在地上，所有骰子朝上一面的点数只和为s。 输入n，打印出s的所有可能的值出现的频率。
## 思路
用两个数组存储骰子点数的每个总数出现的次数。这两个数组轮流使用，骰子数量从1开始不断增加骰子数量，每次增加骰子数量时，点数何为n的骰子出现的次数等于上一轮循环骰子点子数和为n-1、n-2、n-3、n-4、n-5和n-6的次数总和。
##代码实现
  public List<Double> printProbability(int number) {
        List<Double> result = new ArrayList<Double>();
        int maxValue = 6;
        if (number < 1) {
            return result;
        }
        int[][] probabilities = new int[2][];
        probabilities[0] = new int[maxValue * number + 1];
        probabilities[1] = new int[maxValue * number + 1];
        for (int i = 0; i < maxValue * number + 1; i++) {
            probabilities[0][i] = 0;
            probabilities[1][i] = 0;
        }
        int flag = 0;
        for (int i = 1; i <= maxValue; i++) {
            probabilities[flag][i] = 1;
        }
        for (int k = 2; k <= number; k++) {
            for (int i = 0; i < k; i++) {
                probabilities[1 - flag][i] = 0;
            }

            for (int i = k; i <= maxValue * k; i++) {
                probabilities[1 - flag][i] = 0; //
                for (int j = 1; j <= i && j <= maxValue; j++) {
                    probabilities[1 - flag][i] += probabilities[flag][i - j];
                }

            }
            flag = 1 - flag;
        }
        double total = Math.pow(maxValue, number);
        for (int i = number; i <= maxValue * number; i++) {
            double ratio = (double) probabilities[flag][i] / total;
            result.add(ratio);
        }
        return result;
    }
