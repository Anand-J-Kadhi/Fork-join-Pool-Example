----------------------------------------------------
               Fork-Join Pool Example    
----------------------------------------------------
This example demonstrates the basic use of Java 7 Feature Fork-Join pool. As the fork join pool uses Divide and conquer strategy
and can be used for performing Recursive Tasks keeping that in mind this example uses a simple Building consturction project,
in which there are some labours (worker threads) which have their tasks divided with workload specified at runtime. If the workload is too heavy
for one worker then that workload is divided among other idle labours(worker threads). The construction work is assumed to be recursive here.
Two types of tasks are demonstarted here 
1.Recursive Task
2.Recursive Action

This example is extended from [Jenknov Fork-Join-Pool example](http://tutorials.jenkov.com/java-util-concurrent/java-fork-and-join-forkjoinpool.html)
with only difference of added use case.
