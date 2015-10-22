package org.example;

import java.lang.management.ManagementFactory;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.ForkJoinWorkerThread;

import org.example.tasks.DoConstructionAction;
import org.example.tasks.DoConstructionTask;

/**
 * This Program is used to illustrate and understand the use of
 * {@link ForkJoinPool fork-join} mechanism introduced in <b>Java 7</b> . Using
 * the work of constructing a building. The number of threads are treated here
 * as the number of <b>workers</b>.<br>
 * In the {@link DoConstructionAction DoConstructionAction} and
 * {@link DoConstructionTask DoConstructionTask} class we check the work load on
 * a worker and decide to split the work among other workers which have
 * bandwidth.<br>
 * Fork join does this splitting and assigning by using <b>Work-Stealing</b>
 * algorithm.
 *
 * @see ForkJoinPool
 * @see ForkJoinTask
 * @see ForkJoinWorkerThread
 * 
 * @author anand.kadhi
 *
 */
public class ConstructBuilding {

	/**
	 * This method is the Execution start point for the {@link ForkJoinExecutor
	 * fork-join} example.This method instantiates {@link ForkJoinPool} with
	 * Parallelism level equal to the number of cores i.e 4, and sets the
	 * workload for the {@link DoConstructionTask} instance.
	 * 
	 * @param args
	 *            -Command line arguments
	 */
	public static void main(String[] args) {
		// This line creates a fork join pool instance with parallelism
		// level of 4.(since my current machine is Quad-Core)
		ForkJoinPool forkJoinPool = new ForkJoinPool();

		// Listing number of threads
		System.out.println("Total threads :: "
				+ ManagementFactory.getThreadMXBean().getThreadCount());
		System.out.println("Active threads :: " + Thread.activeCount());

		// Recursive Task example
		DoConstructionTask doConstruction = new DoConstructionTask(128);

		long totalWorkDone = forkJoinPool.invoke(doConstruction);

		System.out.println("Total work done  = " + totalWorkDone);

		// Recursive Action example
		DoConstructionAction constructionAction = new DoConstructionAction(36);
		forkJoinPool.invoke(constructionAction);

		// If timeout is not given then Main method exits before
		// Other threads have not even completed their tasks.
		try {
			Runtime.getRuntime().addShutdownHook(new Thread() {
				@Override
				public void run() {
					System.out.println("Inside Add Shutdown Hook");
					System.out.println("Main thread exiting :: "
							+ Thread.currentThread().getName());
				}
			});
			System.out.println("Shut Down Hook Attached.");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
