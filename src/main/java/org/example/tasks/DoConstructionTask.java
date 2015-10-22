package org.example.tasks;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

/**
 *A RecursiveTask is a task that returns a result. It may split its work up into smaller tasks
 *, and merge the result of these smaller tasks into a collective result.
 * <br>
 * 
 * 
 * This class has the logic for actual work to be done and splitting of the work
 * in case any worker work load then other workers will take that work.
 * 
 * This class extends {@link RecursiveTask RecursiveTask}.
 * @author Anand j.kadhi
 *
 */

public class DoConstructionTask extends RecursiveTask<Long> {

	/******* Parameters *******/
	private static final long serialVersionUID = -1956693503155632281L;
	private long workLoad = 0;

	/******* constructor *******/
	
	/**
	 * Instantiate with the required workload.
	 * 
	 * @param workload
	 */
	public DoConstructionTask(long workLoad) {
		this.workLoad = workLoad;
	}
	
	/******* Methods *******/
	protected Long compute() {

		// if work is above threshold, break tasks up into smaller tasks
		if (this.workLoad > 16) {
			System.out.println("Splitting the workload :: " + this.workLoad
					+ " among other workers by worker name :: "
					+ Thread.currentThread().getName());

			List<DoConstructionTask> subtasks = new ArrayList<DoConstructionTask>();
			subtasks.addAll(createSubtasks());

			for (DoConstructionTask subtask : subtasks) {
				subtask.fork();
			}

			long result = 0;
			for (DoConstructionTask subtask : subtasks) {
				result += subtask.join(); 
			}
			return result;

		} else {
			System.out.println("Doing work :: " + this.workLoad
					+ " worker name :: " + Thread.currentThread().getName());
			return workLoad;
		}
	}

	private List<DoConstructionTask> createSubtasks() {
		List<DoConstructionTask> subtasks = new ArrayList<DoConstructionTask>();
		System.out.println("Dividing the work of worker name :: "
				+ Thread.currentThread().getName());
		DoConstructionTask subtask1 = new DoConstructionTask(this.workLoad / 2);
		DoConstructionTask subtask2 = new DoConstructionTask(this.workLoad / 2);

		subtasks.add(subtask1);
		subtasks.add(subtask2);

		return subtasks;
	}
}