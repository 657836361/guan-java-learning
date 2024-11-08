# java_learn

### 如何寻找一个垃圾对象

#### 引用计数法

某个对象被引用时 引用计数器+1 直到引用计数器==0 则说明该对象没有被使用可以被回收

tips:

- 引用计数法不能解决循环引用产生问题

#### 根可达算法

只要不是GCRoot可达的对象就是可回收的垃圾对象

可以被当做GCRoot的有什么：线程栈变量、静态变量、常量池、JNI指针等

### GC算法

**标记清除算法**  
缺点：会导致内存碎片化  
**复制算法**  
缺点：浪费空间  
**标记整理算法**  
缺点：效率偏低

### 根据分代模型区分的垃圾收集器

#### 年轻代

**Serial 收集器**

Serial收集器是最基本的、发展历史最悠久的收集器。

**特点**
：单线程、简单高效（与其他收集器的单线程相比），对于限定单个CPU的环境来说，Serial收集器由于没有线程交互的开销，专心做垃圾收集自然可以获得最高的单线程手机效率。收集器进行垃圾回收时，必须暂停其他所有的工作线程，直到它结束（Stop
The World）。

**应用场景**：适用于Client模式下的虚拟机。

Serial / Serial Old收集器运行示意图
![](serial_serial old.png)

**ParNew收集器**

ParNew收集器其实就是Serial收集器的多线程版本。

**特点**：多线程、ParNew收集器默认开启的收集线程数与CPU的数量相同，在CPU非常多的环境中，可以使用-XX:
ParallelGCThreads参数来限制垃圾收集的线程数。
和Serial收集器一样存在Stop The World问题

**应用场景**：ParNew收集器是许多运行在Server模式下的虚拟机中首选的新生代收集器，因为它是除了Serial收集器外，唯一一个能与CMS收集器配合工作的。

ParNew/Serial Old组合收集器运行示意图如下：
![](parnew_serial old.png)

**Parallel Scavenge 收集器**

与吞吐量关系密切，故也称为吞吐量优先收集器。

**特点**：属于新生代收集器也是采用复制算法的收集器，又是并行的多线程收集器（与ParNew收集器类似）。

该收集器的目标是达到一个可控制的吞吐量。还有一个值得关注的点是：GC自适应调节策略（与ParNew收集器最重要的一个区别）

**GC自适应调节策略**：
Parallel Scavenge收集器可设置-XX:+UseAdptiveSizePolicy参数。
当开关打开时不需要手动指定新生代的大小（-Xmn）、Eden与Survivor区的比例（-XX:SurvivorRation）、晋升老年代的对象年龄（-XX:
PretenureSizeThreshold）等，虚拟机会根据系统的运行状况收集性能监控信息，动态设置这些参数以提供最优的停顿时间和最高的吞吐量，这种调节方式称为GC的自适应调节策略。

Parallel Scavenge收集器使用两个参数控制吞吐量：

- -XX:MaxGCPauseMillis 控制最大的垃圾收集停顿时间
- -XX:GCRatio 直接设置吞吐量的大小。

#### 老年代

**Serial Old 收集器**

Serial Old是Serial收集器的老年代版本。

**特点**：同样是单线程收集器，采用标记-整理算法。

**应用场景**：主要也是使用在Client模式下的虚拟机中。也可在Server模式下使用。

Server模式下主要的两大用途：

1. 在JDK1.5以及以前的版本中与Parallel Scavenge收集器搭配使用。
2. 作为CMS收集器的后备方案，在并发收集Concurent Mode Failure时使用。

Serial / Serial Old收集器工作过程图（Serial收集器图示相同）：
![](serial_serial old.png)

**Parallel Old 收集器**

是Parallel Scavenge收集器的老年代版本。

**特点**：多线程，采用标记-整理算法。

**应用场景**：注重高吞吐量以及CPU资源敏感的场合，都可以优先考虑Parallel Scavenge+Parallel Old 收集器。

Parallel Scavenge/Parallel Old收集器工作过程图：
![](Parallel Scavenge_Parallel Old.png)

**CMS收集器**

一种以获取最短回收停顿时间为目标的收集器。

**特点**：基于标记-清除算法实现。并发收集、低停顿。

**应用场景**：适用于注重服务的响应速度，希望系统停顿时间最短，给用户带来更好的体验等场景下。如web程序、b/s服务。

CMS收集器的运行过程分为下列5步：

1. 初始标记：标记GC Roots能直接到的对象。速度很快但是仍存在Stop The World问题。
2. 并发标记：进行GC Roots Tracing 的过程，找出存活对象且用户线程可并发执行。
3. 重新标记：为了修正并发标记期间因用户程序继续运行而导致标记产生变动的那一部分对象的标记记录。仍然存在Stop The World问题。
4. 并发清除：对标记的对象进行清除回收。
5. 重置：CMS清除内部状态，为下次回收做准备。

CMS收集器的内存回收过程是与用户线程一起并发执行的。

CMS收集器的缺点：

- 对CPU资源非常敏感。
- 无法处理浮动垃圾，可能出现Concurrent Model Failure失败而导致另一次Full GC的产生。
- 因为采用标记-清除算法所以会存在空间碎片的问题，导致大对象无法分配空间，不得不提前触发一次Full GC。

CMS收集器的工作过程图：

![](Parallel Scavenge_CMS.png)

### 根据分区模型区分的垃圾收集器

G1

ZGC



