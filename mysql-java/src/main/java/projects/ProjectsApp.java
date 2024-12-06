package projects;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import projects.entity.Project;
import projects.exception.DbException;
import projects.service.ProjectService;


public class ProjectsApp {
	
		private ProjectService projectService = new ProjectService();
		private Scanner scanner = new Scanner(System.in);
		
					//@formatter:off
					//Creates menu list options
		private List<String> operations = List.of("1) Add a project");
				    //@formatter:on
	
	public static void main(String[] args) {
		
		new ProjectsApp().processUserSelections();
		
	}


//Checks to see if the user input is valid || if they want to exit.
	private void processUserSelections() {
			boolean done = false;
			
				while(!done) {
					try {
						int selection = getUserSelection();
						
						switch (selection) {
						case -1:
							done = exitMenu();
							break;
						case 1:
							createProject();
							break;
						default:
							System.out.println("\n" + selection + " is not a valid selection. Try again.");							
						}						
				}
				catch(Exception e) {
					System.out.println("\nError: " + e + "Try again.");
				}
			}
		} 
	private boolean exitMenu() {
		System.out.println("Exiting menu.");
			return true;
	}
	private void createProject() {
		String projectName = getStringInput("Enter the project name");
		BigDecimal estimatedHours = getDecimalInput("Enter the estimated length of project, in hours");
		BigDecimal actualHours = getDecimalInput("Enter the actual length of the project, in hours");
		Integer difficulty = getIntInput("Enter the difficulty level (1-5)");
		String notes = getStringInput("Enter any projects notes you may have");
					
		Project project = new Project();
		project.setProjectName(projectName);
		project.setEstimatedHours(estimatedHours);
		project.setActualHours(actualHours);
		project.setDifficulty(difficulty);
		project.setNotes(notes);
		Project dbProject = projectService.addProject(project);
		System.out.println("You have successfully created project: " + dbProject);
	}
	
				//method converts decimal input into BigDecimal
	private BigDecimal getDecimalInput(String prompt) {
		String input = getStringInput(prompt);
			if (Objects.isNull(input)) {
				return null;
		}
		try {
			return new BigDecimal(input).setScale(2);
				} catch (NumberFormatException e) {
					throw new DbException(input + " is not a valid decimal number.");
		}
	}
				
					// method calls to getIntInput(). which returns the user's menu selection.
					// The value may be null.
					
	private int getUserSelection() {
			printOperations();
				Integer input = getIntInput("Enter a menu selection");
					return Objects.isNull(input) ? -1 : input;		// Checks the value in local variable input to see if it's null. returns -1 if so. ( -1 will exit the menu process ) 		
					
					//method converts String inputs into Integers. 
	}				//If the conversion fails it will throw a NumberFormatException. 
	private Integer getIntInput(String prompt) {
		String input = getStringInput(prompt);
		
		if(Objects.isNull(input)) {
			return null;
	    	} 
				try {
					return Integer.valueOf(input);
					}
						catch(NumberFormatException e) {
							throw new DbException(input + "is not a valid number. Try again.");
						}
	  				}
					//method of lowest input. The other input methods call this method & convert the input value to the appropriate type. 
					// Will also be called by methods that need to collect String data from the user. Should return a string within the method. 
	private String getStringInput(String prompt) {
		System.out.println(prompt + ": ");
			String input = scanner.nextLine();
				return input.isBlank() ? null : input.trim();	
				}
	
					//prints available selections to choose from in the console from the printOperations() method.
	private void printOperations() {
			System.out.println("\nThese are the available selections. Press the Enter key to quit: ");
				operations.forEach(line -> System.out.println("   " + line));
						
					}
	
			}
