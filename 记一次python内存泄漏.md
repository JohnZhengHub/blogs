## 场景
4.1 学长和我说我的进程占用太多内存,那边准备kill了.我回来看看代码


### 问题1
因为每个结点前向传播后将隐含层的数据存放在结点内部,没有清除.导致随着数据量的增大内存占用增大. 所以在反向传播的时候将结点的隐藏层的向来值为none.

改进前

                        types |   # objects |   total size
    ========================= | =========== | ============
                numpy.ndarray |       22432 |      1.71 MB
      collections.defaultdict |           0 |    144.00 KB
                         list |         301 |     30.56 KB
                numpy.float64 |         303 |      7.10 KB
                          str |           2 |     97     B
                          int |           1 |     24     B
         
通过改进后:

      
                          types |   # objects |   total size
      ========================= | =========== | ============
        collections.defaultdict |           0 |    144.00 KB
                  numpy.ndarray |         633 |     49.45 KB
                           list |           0 |     80     B
                  numpy.float64 |           3 |     72     B


可见list已经变为了0,同时numpy.ndarray从22432个对象降低到了633个对象.内存泄漏从 1.71 MB变为49.45kb. 从整体上说是降低很多内存消耗.


### 问题2

## 参考资料

http://pythonhosted.org/Pympler/tutorials/muppy_tutorial.html
