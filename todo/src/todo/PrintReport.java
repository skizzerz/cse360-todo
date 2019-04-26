// CSE 360 Project - Team 10
// Noah Anderson, Adam Harvey, Adwith Malpe, Ryan Schmidt
// Generates a report containing all information in a To do List

package todo;

import java.io.File;
import java.io.PrintWriter;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;

//import javafx.print.PrinterJob;
//import javafx.scene.text.Text;

public class PrintReport {
	public static String generateReport(Iterable<ITodoListItem> todoList) {

		String report = "";
		
		for(ITodoListItem item : todoList) {
			report += "Description: " + item.getDescription() + "\r\n";
			report += "Priority:    " + item.getPriority() + "\r\n";
			report += "Due Date:    " + item.getDueDate() + "\r\n";
			report += "Status:      " + item.getStatus() + "\r\n\r\n";
		}
		
		return report;
	}
	public static void saveReport(ITodoList printList) {
		String writeable = generateReport(printList.getList());
		FileChooser chooser = new FileChooser();
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        File file = chooser.showSaveDialog(Program.getStage());
        if (file != null) {
            try {
            	PrintWriter printer = new PrintWriter(file.getAbsolutePath());
                printer.print(writeable);
                printer.close();
            } catch (Exception e) {
                Alert alert = new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK);
                alert.showAndWait();
            }
        }
	}
	//Old print function - actually tries to print!
	/*public static void printReport(ITodoList printList) {
		PrinterJob printJob = PrinterJob.createPrinterJob();
    	if(printJob.showPrintDialog(null)){
    		Text printableNode = new Text("\n" + generateReport(printList.getList()));
    		while(printJob.getJobStatus() == PrinterJob.JobStatus.NOT_STARTED || printJob.getJobStatus() == PrinterJob.JobStatus.PRINTING) {
	    		if(!printJob.printPage(printableNode)) {
	    			break;
	    		}
    		}
    		printJob.endJob();
    	}
	}*/
}
