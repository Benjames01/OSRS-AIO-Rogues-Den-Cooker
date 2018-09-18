package tasks;

public class Task {

	private String fishName;
	private int goal;
	private boolean unlimitedTask;
	
	public Task(String fishName, int goal, boolean unlimitedTask) {
		this.fishName = fishName;
		this.goal = goal;
		this.unlimitedTask = unlimitedTask;
	}
	
	public String getFish() {
		return fishName;
	}
	
	public int getGoal() {
		return goal;
	}
	
	public boolean isUnlimitedTask() {
		return unlimitedTask;
	}
	
	public void setFish(String fishName) {
		this.fishName = fishName;
	}
	
	public void setGoal(int goal) {
		this.goal = goal;
	}
	
	public void setUnlimitedTask(boolean unlimitedTask) {
		this.unlimitedTask = unlimitedTask;
	}
	
	@Override
	public String toString() {
		if(unlimitedTask)
			return "Fish Type: " + fishName + "; " + "Goal Type: Unlimited"; 
		return "Fish Type: "+ fishName + "; " + "Goal: " + goal;
	}
		
}
