package ie.atu.sw;


import java.util.Scanner;
public class Menu{
	Scanner input = new Scanner(System.in);
	
	
	private Mapping dataRegistry;
	String quit;
	
	
	public Menu() {	
		dataRegistry = new Mapping();
	}
	public void menu() throws Exception {
		do {
		System.out.println("************************************************************");
		System.out.println("*     ATU - Dept. of Computer Science & Applied Physics    *");
		System.out.println("*                                                          *");
		System.out.println("*             Virtual Threaded Sentiment Analyser          *");
		System.out.println("*                                                          *");
		System.out.println("************************************************************");
		System.out.println("(1) Specify name of the lexicon file: ");
		String filename = input.nextLine();
		dataRegistry.filetoMap("./lexicons/" + filename);
		
		
		//Twitter user
		System.out.println("(2) Enter twitter user: ");
		filename = input.nextLine();
		
		System.out.println(dataRegistry.getSentiment("./100-twitter-users/" + filename));

		System.out.println("(?) Quit: Press -1/ Continue: Press any key");
		quit = input.nextLine();
		
		
		
		}while(!quit.equals("-1"));
	}
	


}

