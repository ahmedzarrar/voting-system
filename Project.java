// import java.util.Scanner;
// import java.util.HashMap;
// import java.util.ArrayList;
// import java.util.InputMismatchException;
// import java.util.Map;

//Importing Libraries : 
import java.util.*;
import java.util.regex.Pattern;
import java.io.*;

public class Project {
    public static void main(String[] args) {
		// Creating HashMap To store Data of Parties and Voters in Keys and Values Format
        HashMap<String, ArrayList<Object>> parties = new HashMap<>(); // HashMap for Parties
		HashMap<String, ArrayList<Object>> voters = new HashMap<>();  // HashMap for Voters
        Scanner input = new Scanner(System.in);
        System.out.println("\n<<<<<<<<  Welcome to the Election Management System  >>>>>>>>");
		// Creating an Account for the administration.
        System.out.println("Creating an account........");
        System.out.print("Create username: ");
        String defineUserName = input.nextLine();
        System.out.print("Create Password of at least 8 characters: ");
        String definePassword = input.nextLine();

		// Checking if Password is of atleast 8 Digit.
        while (true) {
            if (definePassword.length() >= 8) {
                System.out.println("Account created successfully\n");
                break;
            } else {
                System.out.println("Password must be at least 8 characters");
                System.out.print("Create password again: ");
                definePassword = input.nextLine();
            }
        }

        // Logging in to Administration Menu
        System.out.println("Logging in........");
        while (true) {
            System.out.print("Enter username: ");
            String userName = input.nextLine();
            System.out.print("Enter Password: ");
            String password = input.nextLine();
            if (userName.equals(defineUserName) && password.equals(definePassword)) { // Verifying account
                System.out.println("Account Verified\n");
                while (true) {
					System.out.println("\n<<<<<<<<  Management Menu  >>>>>>>> ");
					System.out.println("1) Add party \n2) Remove party \n3) Display Previous Election Result \n4)Start Voting \n5) Logout ");
					int choice = 0;
					while (true) {
						try {
							System.out.print("Enter your choice: ");
							choice = input.nextInt();
							input.nextLine(); // consume newline character
							break;
						} 
						catch (InputMismatchException e) {
							System.out.println("You have entered invalid input.");
							input.nextLine();
						}
					}

					if (choice == 1) {
						// Calling method for adding parties
						addParty(parties);
					} 
					else if (choice == 2) {
						// Calling method for removing parties
						removeParty(parties);

					}
					else if(choice == 3){
						previousResults("election_results.txt");
					}
					else if (choice == 4){
						// You have to enter atleast 3 parties in order to do voting
						if(parties.size() < 3){
							System.out.println("Please register atleast three parties first.");
							continue;
						}
						while (true){
							System.out.println("\nVoting in Progress........");
							System.out.println("1. Management Login");
							System.out.println("2. Vote");
							int option = 0;
							while (true) {
								try {
									System.out.print("Enter your choice: ");
									option = input.nextInt();
									input.nextLine(); // consume newline character
									break;
								} 
								catch (InputMismatchException e) {
									System.out.println("You have entered invalid input.");
									input.nextLine();
								}
							}
							if(option == 1){
								System.out.println("Enter username: ");
								userName = input.nextLine();
								System.out.println("Enter Password: ");
								password = input.nextLine();
								if (userName.equals(defineUserName) && password.equals(definePassword)) {
									System.out.println("Account Verified\n"); // Verifying account
									// Displaying Administration Second menu.....
									while (true){
										System.out.println("1. Stop Voting and display Results");
										System.out.println("2. Logout");
										int decision = 0;
										while (true) {
											try {
												System.out.print("Enter your choice: ");
												decision = input.nextInt();
												input.nextLine(); // consume newline character
												break;
											} 
											catch (InputMismatchException e) {
												System.out.println("You have entered invalid input.");
												input.nextLine();
											}
										}
										if(decision == 1){
											result(parties);
											winner(parties);
											// Store all the Data in a file named election_results.txt
											storeResult(parties,"election_results.txt");
											System.out.println("Thank you \nThe Election is over.");
                                           
											System.exit(0);
										}
										// 	Choice for showing previous result.....
										
										else if(decision == 2){
											break;
										}
										else{
											System.out.println("Invalid choice.");
										}
									}	
								}
								else {
									System.out.println("Username or Password are incorrect");
									System.out.println("TRY AGAIN");
								}	
							}
							else if(option == 2){
								// Calling votingMethod 
								votingMethod(voters, parties);
							}
							else{
								System.out.println("Invalid Choice");
							}
						}

					}
					else if (choice == 4 ){
						break;
					}
					else if (choice == 5){
					    break;
					}
					else {
						System.out.println("Invalid input");
					}
				}
            } 
			else {
                System.out.println("Username or Password are incorrect");
                System.out.println("TRY AGAIN");
            }
		}
    }


	// ADD PARTIES METHOD......
	public static void addParty(HashMap<String, ArrayList<Object>> parties){
		Scanner input = new Scanner(System.in);
		// Format for CNIC...
		String cnicPattern = "\\d{5}-\\d{7}-\\d";
		while (true) {
			// Creating and ArrayList to store multiple values to a single key....
			ArrayList<Object> partyList = new ArrayList<>();
			System.out.println("Enter Party Name or Party Number: ");
			String partyName = input.nextLine();
			partyName = partyName.toUpperCase();
			// Checking if party is already registered.....
			if(parties.containsKey(partyName)){
				System.out.println("This party is already registered.");
				return;
			}
			System.out.println("Enter name of the Party leader: ");
			String partyLeaderName = input.nextLine();
			partyList.add(partyLeaderName);
			String partyLeaderCNIC = "";
			// Checking if we have entered a valid CNIC pattern....
			while (true) {
				System.out.println("Enter CNIC of the Party leader (format: XXXXX-YYYYYYY-Z): ");
				partyLeaderCNIC = input.nextLine();
				if (Pattern.matches(cnicPattern, partyLeaderCNIC)) {
					System.out.println("CNIC ADDED SUCCESSFULLY"); //CNIC VERIFIED.....
					break;
				} else {
					System.out.println("Invalid CNIC format. Please enter in the format XXXXX-YYYYYYY-Z.");
				}
			}
			// Adding PartyLeaderCNIC into the Arraylist
			partyList.add(partyLeaderCNIC);
			partyList.add(0); //for votes count
			parties.put(partyName, partyList);
			System.out.print("Enter yes to add another party or enter any key to stop adding party: ");
			String reply = input.nextLine();
			if (!reply.equalsIgnoreCase("yes")) {
				break;
			}
		}
	}


	// Method to removeParties......
	public static void removeParty(HashMap<String, ArrayList<Object>> parties){
		Scanner input = new Scanner(System.in);
		System.out.println("Enter Name or Number of the party you want to remove: ");
		String keyToRemove = input.nextLine();
		keyToRemove = keyToRemove.toUpperCase();
		// Checking if Party is avaliable......
		if (parties.containsKey(keyToRemove)) {
			parties.remove(keyToRemove);
			System.out.println("The key " + keyToRemove + " is removed.");
		} else {
			System.out.println("The party name is not registered");
		}
	}


    //  Method for voting.....
	public static void votingMethod(HashMap<String, ArrayList<Object>> voters, HashMap<String, ArrayList<Object>> parties){
		// Creating a new arraylist for voter to store multiple value to a key.....
		ArrayList<Object> voterList = new ArrayList<>();
		Scanner input = new Scanner(System.in);
		String CNIC = "";
		String cnicPattern = "\\d{5}-\\d{7}-\\d"; //Checking for the CNIC
		while (true) {
			System.out.println("Enter your CNIC (format: XXXXX-YYYYYYY-Z): ");
			CNIC = input.nextLine();
			if (Pattern.matches(cnicPattern, CNIC)) {
				break;
			} else {
				System.out.println("Invalid CNIC format. Please enter in the format XXXXX-YYYYYYY-Z.");
			}
		}
		// Checking if the voter has already voted.....
		if(voters.containsKey(CNIC)){
			System.out.println("You have already voted.");
			return;
		}
		// Asking for voter information.......
		System.out.print("Enter your name: ");
		String voterName = input.nextLine();
		String gender = "";
		while(true){
			try{
				System.out.print("Enter your gender (Male/Female): ");
				gender = input.nextLine();
				gender = gender.substring(0,1).toUpperCase() + gender.substring(1).toLowerCase();
				if(gender.equals("Male") || gender.equals("Female")){
					break;
				}
				else{
					throw new IllegalArgumentException("Invalid Input. Enter again");
				}
			}
			catch(IllegalArgumentException ex){
				System.out.println(ex.getMessage());
			}
		}
		// Adding the voterName and gender to the ArrayList 
		voterList.add(voterName);
		voterList.add(gender);
		voters.put(CNIC, voterList);

		// Display parties and Cast vote
		System.out.println("Here is the list of the parties: ");
		for (String partyName : parties.keySet()) {
            System.out.println(partyName);
        }
		String selectedParty = "";
        while (true) {
            System.out.print("Enter the name of the party you want to vote for: ");
            selectedParty = input.nextLine().toUpperCase();
            if (parties.containsKey(selectedParty)) {
                break;
            } else {
                System.out.println("Invalid party name. Please enter a valid party name from the list.");
            }
        }
		// Creating an arraylist for partyDetails
		ArrayList<Object> partydetail = parties.get(selectedParty);
		int newVote = (int) partydetail.get(2); //the second index is the vote count
		// Adding the votecount of the specific party.....
		partydetail.set(2, newVote+1);
		                System.out.println("Done");
					}

		// Method for result.....
	public static void result(HashMap<String, ArrayList<Object>> parties) {
        System.out.println("Election Results:");
        for (Map.Entry<String, ArrayList<Object>> entry : parties.entrySet()) {
            String partyName = entry.getKey();
            ArrayList<Object> partyDetails = entry.getValue();
            int votes = (int) partyDetails.get(2); 
            System.out.println(partyName + ": " + votes + " votes");
        }
	}


    // Method for winner.... 
	public static void winner(HashMap<String, ArrayList<Object>> parties) {
        String winningParty = null;
        int maxVotes = -1;
        boolean tie = false;
		// Creating an arraylist for winners.....
        ArrayList<String> winners = new ArrayList<>();

        for (Map.Entry<String, ArrayList<Object>> entry : parties.entrySet()) {
            String partyName = entry.getKey();
            ArrayList<Object> partyDetails = entry.getValue();
            int votes = (int) partyDetails.get(2);

            if (votes > maxVotes) {
                maxVotes = votes;
                winningParty = partyName;
                tie = false;
                winners.clear();
                winners.add(partyName);
            } else if (votes == maxVotes) {
                tie = true;
                winners.add(partyName);
            }
        }
		// If votes and maxVotes are the same.........
        if (tie) {
            System.out.println("It's a tie between the following parties with " + maxVotes + " votes each:");
            for (String party : winners) {
                System.out.println(party);
            }
        } else if (winningParty != null) {
            System.out.println("Congratulations " + winningParty + " for winning with " + maxVotes + "votes");
        }

    }

	// Store Result in a File.......
	public static void storeResult(HashMap<String, ArrayList<Object>> parties, String filename) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) {
			writer.write("Election Results:\n");
			
			// Loop through each entry in the parties HashMap
			for (Map.Entry<String, ArrayList<Object>> entry : parties.entrySet()) {
				String partyName = entry.getKey();
				ArrayList<Object> partyDetails = entry.getValue();
				String partyLeaderName = (String) partyDetails.get(0);
				String partyLeaderCNIC = (String) partyDetails.get(1);
				int votes = (int) partyDetails.get(2);
	
				// Write the details to the file
				writer.write("Party Name: " + partyName + "\tParty Leader Name:  " + partyLeaderName + "\tParty Leader CNIC: " + partyLeaderCNIC + "\tNumber of votes: " + votes + "\n");
			}
	
			String winningParty = null;
			int maxVotes = -1;
			boolean tie = false;
			ArrayList<String> winners = new ArrayList<>();
	
			// Determine the winning party
			for (Map.Entry<String, ArrayList<Object>> entry : parties.entrySet()) {
				String partyName = entry.getKey();
				ArrayList<Object> partyDetails = entry.getValue();
				int votes = (int) partyDetails.get(2);
	
				if (votes > maxVotes) {
					maxVotes = votes;
					winningParty = partyName;
					tie = false;	
					winners.clear();
					winners.add(partyName);
				} else if (votes == maxVotes) {
					tie = true;
					winners.add(partyName);
				}
			}
	
			// Handle the tie case or declare the winner
			if (tie) {
				writer.write("It's a tie between the following parties with " + maxVotes + " votes each:\n");
				for (String party : winners) {
					writer.write(party + "\n");
				}
			} else if (winningParty != null) {
				writer.write("Congratulations " + winningParty + " for winning with " + maxVotes + " votes\n");
			}
	
			writer.write("\n");
		} catch (IOException e) {
			e.getMessage();
		}
	}
	
	
	// Displaying previous result.......
	public static void previousResults(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader("election_results.txt"))) {
            String line;
            System.out.println("Previous Election Results:");
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("No previous results found.");
        }
    }
        
    					
}

