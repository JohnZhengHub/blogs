
##　KMP算法

思想：朴素字符串匹配算法采用暴力求解的方式，首先从主串的第一个字符与匹配串的第一个字符进行比较，如果相同则依次往后比较。如果在某个位置不同，则将主串指针回溯到第二个位置，匹配串指针回溯到第一位置再按上述方式进行匹配。导致时间复杂度为O(n*m).
该算法其中做了很多没必要的回溯，KMP算法就是利用匹配串的信息构建一个部分匹配值数组π，数组π可以使我们按需要“即时”计算j的转移函数，通过回溯匹配串指针j来调整下一次匹配的位置，避免主串指针的回溯。使得算法的复杂度为O(n+m);
	
	匹配串向前移动的位数 = 已匹配的字符数 - 对应的部分匹配值

其实移动后的匹配串的位置 = 对应的匹配值。
算法的关键是构建部分匹配值数组，"部分匹配值"就是"前缀"和"后缀"的最长的共有元素的长度。"前缀"指除了最后一个字符以外，一个字符串的全部头部组合；"后缀"指除了第一个字符以外，一个字符串的全部尾部组合。

以"ABCDABD"为例，
A"的前缀和后缀都为空集，共有元素的长度为0；
"AB"的前缀为[A]，后缀为[B]，共有元素的长度为0；
ABC"的前缀为[A, AB]，后缀为[BC, C]，共有元素的长度0；
"ABCD"的前缀为[A, AB, ABC]，后缀为[BCD, CD, D]，共有元素的长度为0；
"ABCDA"的前缀为[A, AB, ABC, ABCD]，后缀为[BCDA, CDA, DA, A]，共有元素为"A"，长度为1；
ABCDAB"的前缀为[A, AB, ABC, ABCD, ABCDA]，后缀为[BCDAB, CDAB, DAB, AB, B]，共有元素为"AB"，长度为2；
"ABCDABD"的前缀为[A, AB, ABC, ABCD, ABCDA, ABCDAB]，后缀为[BCDABD, CDABD, DABD, ABD, BD, D]，共有元素的长度为0。


  public int kmp(String str,String subStr){
  		char[] main = str.toCharArray();
  		char[] sub = subStr.toCharArray();
  		int[] next = makeNext(sub);
  		int i,q,n,m;
  		n = main.length;
  		m = sub.length;
  		for(i= 0,q = 0;i<n;i++ ){
  			while(q > 0 && main[i] != sub[q]){
  				q = next[q-1];
  			}
  			if(main[i] == sub[q]){
  				q++;
  			}
  			if( q== m){
  				return i - m+1;
  			}
  		}
  		return -1;
  	}
  	private int[] makeNext(char[] P){
  		int[] next = new int[P.length];
  		int idx,k;//idx模板字符串下标，k表示最大后缀长度.
  		next[0] = 0; //模板字符串第一个字符的最大前后缀长度为0,
  		for(idx = 1, k =0;idx < P.length;idx++){
  			while(k >0 && P[idx] != P[k]){
  				k = next[k-1];
  			}
  			if(P[k] == P[idx]){
  				k++;
  			}
  			next[idx] = k;
  		}
  		return next;
  	}
