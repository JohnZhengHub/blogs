## 场景
由于某种原因,我实验被转移到了另一台服务器上运行,实验主要是使用python来实现TreeLSTM+NTN的组合神经网络模型来识别两个句子之间的关系.

2016.04.01 学长和我说我的进程占用太多内存,那边准备kill了.于是我在服务器上运行了我的程序,并使用top来观察线程运行情况.发现一个很奇怪的现象,随着运行时间的增加,进程使用的内存容量也不断增加.当时的第一反映就是可能发生了内存泄漏,但是这种问题如何查看,如何确定是内存泄漏,内存泄漏发生在什么地方?

于是上网查了相关资料,发现了一个内存泄漏排查工具,


### 问题分析
因为每个结点前向传播后将隐含层的数据存放在结点内部,没有清除.导致随着数据量的增大内存占用增大. 所以在反向传播的时候将结点的隐藏层的向来值为none.

下面是以TreeLSTM的前向传播代码:

     def forward_prop(self, node, depth=-1,test=False):
        cost = 0.0
        if node.isLeaf:
            node.c = self.W_in.dot(self.L[:, node.word]) + self.b_in
            node.o = sigmoid(self.W_out.dot(self.L[:, node.word]) + self.b_out)
            node.ct = np.tanh(node.c)
            if not test:
                node.mask = np.random.binomial(1, self.node_keep, self.mem_dim)
                node.hActs1 = node.o * node.ct * node.mask
            else:
                node.hActs1 = node.o * node.ct * self.node_keep
            node.hActs1 = node.o * node.ct
        else:
            node.left = self.forward_prop(node.left, depth - 1)
            node.right = self.forward_prop(node.right, depth - 2)
            children = np.hstack((node.left.hActs1, node.right.hActs1))
            node.i = sigmoid(self.Ui.dot(children) + self.bi)
            node.f_l = sigmoid(self.Uf_l.dot(children) + self.bf)
            node.f_r = sigmoid(self.Uf_r.dot(children) + self.bf)
            node.o = sigmoid(self.Uo.dot(children) + self.bo)
            node.u = np.tanh(self.Uu.dot(children) + self.bu)
            node.c = node.i * node.u + node.f_l * node.left.c + node.f_r * node.right.c
            node.ct = np.tanh(node.c)
            if not test:
                node.mask = np.random.binomial(1, self.node_keep, self.mem_dim)
                node.hActs1 = node.o * node.ct * node.mask
            else:
                node.hActs1 = node.o * node.ct * self.node_keep
        return node

    
这里为每个结点新增了 i,f_l,f_r,o,u,c,ct. 这些都是向量,内部保存着一个dim维度的浮点型数组,假设每个float占4byte 那么100维的数组则占用 400byte,一样本有平均有 40多个结点,所需需要存储1.6 MB左右的内存容量,这里有7个变量,所以需要存储 7*1.6 = 11.2MB的内存,所以每个样本需要额外消耗这么多内存,如果说不清除这块内存则继续保存在内存空间里,当然下次是可以重复利用的.但是内存空间有限,如果将所有将本的数据都保存在内存中 1W个样本就得占用10多G的内存,所以是不科学的.


改进前

                          types |   # objects |   total size
      ========================= | =========== | ============
                  numpy.ndarray |       53157 |      4.06 MB
                           list |        8532 |    869.02 KB
                            str |        8436 |    498.85 KB
        collections.defaultdict |           1 |    192.27 KB
                            int |         783 |     18.35 KB
                           dict |          10 |      4.23 KB
                  numpy.float64 |         101 |      2.37 KB
             wrapper_descriptor |          21 |      1.64 KB
                        weakref |          11 |    968     B
              method_descriptor |          12 |    864     B
              member_descriptor |           3 |    216     B
              getset_descriptor |           2 |    144     B
                           code |           1 |    128     B
          function (store_info) |           1 |    120     B
                           cell |           2 |    112     B
                           
                          types |   # objects |      total size
      ========================= | =========== | ===============
                  numpy.ndarray |      149236 |        11.39 MB
                           list |         301 |        30.56 KB
                  numpy.float64 |         303 |         7.10 KB
                            str |           2 |        97     B
                            int |           1 |        24     B
        collections.defaultdict |           0 |   -147456     B

从上面的前后对比可以看出来第二次在numpy.ndarry中增加了 149236个对象,需要泄漏11.39mb的内存,所以随着时间的推移,内存泄漏越来越明显
list,  numpy.float65同样存在内存泄漏的问题.

## 解决措施
因此我在每次迭代完成后就将所有的结点的相关变量设为None 让垃圾回收器回收这块内存空间.提高内存空间的利用率.

treeLSTM中增加该方法

    def clear(self,node):
          node.hActs1 = None
          node.u = None
          node.ct = None
          node.c = None
          node.u = None
          node.o = None
          node.f_l =None
          node.f_r = None
          node.i = None
          node.mask = None
          if not node.isLeaf:
              self.clear(node.left)
              self.clear(node.right)


在DiscourseRel.py中,完成后向传播之后加入以下代码,清除所有结点的数据

    ## clear data of node 
    for data in mbdata:
        self.relModel.clear(data)
        self.sentModel.clear(data.arg1_tree)
        self.sentModel.clear(data.arg2_tree)
        #pass

         
通过改进后:

                              types |   # objects |   total size
    ========================= | =========== | ============
                         list |        8432 |    858.87 KB
                          str |        8436 |    498.85 KB
      collections.defaultdict |           1 |    192.27 KB
                numpy.ndarray |        1647 |    128.67 KB
                          int |         783 |     18.35 KB
                         dict |          10 |      4.23 KB
           wrapper_descriptor |          21 |      1.64 KB
                      weakref |          11 |    968     B
            method_descriptor |          12 |    864     B
            member_descriptor |           3 |    216     B
            getset_descriptor |           2 |    144     B
                         code |           1 |    128     B
        function (store_info) |           1 |    120     B
                         cell |           2 |    112     B
                numpy.float64 |           1 |     24     B
                        types |   # objects |      total size
    ========================= | =========== | ===============
                numpy.ndarray |         516 |        40.31 KB
                          str |           2 |        97     B
                         list |           1 |        96     B
                numpy.float64 |           3 |        72     B
                          int |           1 |        24     B
      collections.defaultdict |           0 |   -147456     B

       

可见list已经变为了0,同时numpy.ndarray从149236个对象降低到了512个对象.内存泄漏从 11.39mb变为40.31kb. 从整体上说是降低很多内存消耗.


## 参考资料

http://pythonhosted.org/Pympler/tutorials/muppy_tutorial.html
