package org.example.tasks;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveAction;

/**
 * A Recursive action is the one which does not return any result and exits.
 * However it waits until all the subtasks have finished their execution.<br>
 * Example : writing data to disk and exit. <br>
 * <br>
 * 
 * This class has the logic for actual work to be done and splitting of the work
 * in case any worker work load then other workers will take that work.
 * 
 * This class extends {@link RecursiveAction RecursiveAction}.
 * 
 * @author Anand j.kadhi
 * 
 */
public class DoConstructionAction extends RecursiveAction {

	/******* Parameters *******/
	private static final long serialVersionUID = -7042090957693631428L;

	private long workload = 0;

	/******* constructor *******/

	/**
	 * Instantiate with the required workload.
	 * 
	 * @param workload
	 */
	public DoConstructionAction(long workload) {
		super();
		this.workload = workload;
	}

	/******* Methods *******/

	@Override
	protected void compute() {
		// if work is above threshold then fork the task into subtasks.
		if (this.workload > 16) {
			
			System.out.println("Splitting the workload :: " + this.workload +" among other workers by worker name ::  " + Thread.currentThread().getName());

			List<DoConstructionAction> subtasks = new ArrayList<DoConstructionAction>();

			subtasks.addAll(createSubTasks());
			
			for (RecursiveAction subtask : subtasks) {
				subtask.fork();
			}
		} else {
			System.out.println("Doing work :: " + this.workload
					+ " worker name :: " + Thread.currentThread().getName());
		}
	}

	private List<DoConstructionAction> createSubTasks() {
		// Create subtasks here
		List<DoConstructionAction> subtasks = new ArrayList<DoConstructionAction>();
		System.out.println("Dividing the work of worker name :: "
				+ Thread.currentThread().getName());
		DoConstructionAction subtask1 = new DoConstructionAction(this.workload / 2);
		DoConstructionAction subtask2 = new DoConstructionAction(this.workload / 2);

		subtasks.add(subtask1);
		subtasks.add(subtask2);

		return subtasks;
	}

}
