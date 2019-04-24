// CSE 360 Project - Team 10
// Noah Anderson, Adam Harvey, Adwith Malpe, Ryan Schmidt
// Generates a report containing all information in a To do List

package todo;

public class PrintReport {
	public String generateReport(Iterable<ITodoListItem> todoList) {

		String report = "";
		
		for(ITodoListItem item : todoList) {
			report += "Description: " + item.getDescription() + "\n";
			report += "Priority:    " + item.getPriority() + "\n";
			report += "Due Date:    " + item.getDueDate() + "\n";
			report += "Status:      " + item.getStatus() + "\n\n";
		}
		
		return report;
	}
}
