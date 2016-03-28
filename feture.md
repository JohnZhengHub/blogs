# Future
Future是一个接口，该接口用来异步的处理结果,Future对象本身可以看作是一个显式的引用，一个对异步处理结果的引用。Future模式的核心在于：去除了主函数的等待时间，并使得原本需要等待的时间段可以用于处理其他业务逻辑.
主要提供了三类功能：
 - 任务结果的获取:这个功能由 get 方法提供和重载的超时get()提供, 在调用get时会被阻塞
 - 任务取消: cancel()
 - 获取已完成的任务 ,isDone()
 
 Future 是有其局限性的。Future 主要功能在于获取任务执行结果和对异步任务的控制。但如果要获取批量任务的执行结果，从上面的例子我们已经可以看到，单使用 Future 是很不方便的。其原因在于：一是我们没有好的方法去获取第一个完成的任务；二是 Future.get 是阻塞方法，使用不当会造成线程的浪费。解决第一个问题可以用 CompletionService 解决，CompletionService 提供了一个 take() 阻塞方法，用以依次获取所有已完成的任务。

## FutureTask
FutureTask除了Future接口外,还实现了Runnable接口,因此FutureTask可以交给Executor执行,也可以由调用线程直接执行.

FutureTask处于下面3种状态:
1. 未启动,
