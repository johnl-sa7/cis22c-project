package com.company; /** Course Project CS 22c
*Autor: Chengyun Li
*/
import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

public class UserInterface {
	
	public static void main(String[] args) throws IOException {

		final int NUM_ACCOUNT = 30;
		final  int NUM_USERS = 30;
		final int NUM_INTEREST= 20;
		int userId = -1;
		HashTable<UserAccount> userAccounts= new HashTable<>(NUM_ACCOUNT);
		HashTable<User> userNames = new HashTable<>(NUM_USERS);
		HashTable<Interest> interest = new HashTable<>(NUM_INTEREST);
		ArrayList<BST<User>> NewFriendByInterest = new ArrayList<BST<User>>();
		// output: 
		ArrayList<String> outputNames = new ArrayList<>();
		for(int i = 0; i < NUM_INTEREST; i++) {
			NewFriendByInterest.add(new BST<User>());
		}
		 
		File file = new File("princessesv2.txt");
		//reading input file
		try {
			Scanner input = new Scanner(file);
			while (input.hasNextLine()) { 
				String[] str = new String[35];
				for(int i = 0; i < 35; i++) {
					if (input.hasNextLine()) {
						str[i] = input.nextLine();
						if (str[i].isEmpty()) {
							break;
						}
					}
				}
				outputNames.add(str[0]);
				String[] name = str[0].split(" ", 2);
				int friendNum = Integer.parseInt(str[4]);
				ArrayList<User> friendArray = new ArrayList<>();
				ArrayList<Interest> userInterest = new ArrayList<>();
				for(int h = 0; h < friendNum; h++) {
					User temp = new User(str[h + 5]);
					friendArray.add(temp);
				}
				for(int j = 0; j < Integer.parseInt(str[5 + friendNum]); j++) {	
					
					String[] temp = str[j + friendNum + 6].split("#", 2);
					Interest inter = new Interest(temp[0], Integer.parseInt(temp[1]));
					
					userInterest.add(inter);
				}
				User user = new User(name[0], name[1],str[1], str[2], str[3],++userId, friendArray, userInterest);
				System.out.println("Chengyun userId: " + user.getUserId());
				Interest[] temp = user.getInterest();
				for(int k = 0; k < temp.length; k++) {
					int index = temp[k].getID();
					NewFriendByInterest.get(index).insert(user, new NameComparator());			
					if(!(interest.contains(temp[k]))){
						interest.put(temp[k]);
					}
				}
				UserAccount userAccount = new UserAccount(user);
				userNames.put(user);
				userAccounts.put(userAccount);	
		}	
		input.close();
		}catch (IOException e) {
			e.printStackTrace();
		}
        //login process optionA
		//problem: do not know how to assign a userId to a new user
        System.out.println("Welcome to Princess system!");
        
		Scanner input = new Scanner(System.in);
		System.out.print("\nPlease enter your user name: ");
		String userName = input.nextLine();
		
		System.out.print("\nPlease enter your password: ");
		String password = input.nextLine();
		
		User user = new User(password, userName);
		UserAccount userAccount = new UserAccount(user);
		if (!(userAccounts.contains(userAccount))) {
			System.out.println("\nWe don't have your account on file...\n");
			System.out.println("\nLet's create an account for you!");
			System.out.print("\nEnter your first name: ");
			String first = input.nextLine();
			System.out.print("\nEnter your last name: ");
			String last = input.nextLine();
			user.setFirstName(first);
			user.setLastName(last);
			user.setUserId(++userId);
			System.out.println("Chengyun new user userId: " + user.getUserId());
			outputNames.add(user.getFirstName() + " " + user.getLastName());
			userNames.put(user);
			userAccounts.put(userAccount);	
		} else {
			userAccount = userAccounts.get(userAccount);
			user = userAccount.getUser();
	}
		System.out.println("\nWelcome, " + userAccount.getUser().getFirstName() + " " + userAccount.getUser().getLastName() + "!\n");
		
		System.out.println("Welcome to Princess system!");
	
		while(true) {
			//print menu
			System.out.println("\nA. Login");
			System.out.println("B. View My Friends (has sub-menu)");
			System.out.println("C. Search for a New Friend (has sub-menu)");
			System.out.println("D. Get Friend Recommendations (has sub-menu)");
			System.out.println("E. Quit and Write Records to a File\n");
			System.out.println();
			System.out.print("Enter your choice: ");
			String in = input.nextLine();
			//option B view View My Friends (has sub-menu)
			// did not work, because we decide only pass name inside user's private bst<user> friends
			if (in.equals("B"))  {
				while(true) {
					System.out.println("\nPlease select from the options below: \n");
					System.out.println("a. View Friends by name");
					System.out.println("b. View a friend's profile");	
					System.out.println("c. Remove a friend from your friend's list");
					System.out.println("d. Go back to main menu.\n");
					
					System.out.print("Enter your choice: ");
					String viewFriend = input.nextLine();
					System.out.println();
						if(viewFriend.equals("a")) {
							user.printUserFriends();
						}
						else if(viewFriend.equals("b")) {
							System.out.print("Enter friend's name: ");
							String name = input.nextLine();
							User tempUserFriend = user.getFriendByName(name);
					        if (tempUserFriend == null){
					            System.out.println("Sorry, could not find your friend :(");
					        }
					        else {
					            System.out.println(tempUserFriend);
					        }
						}
						else if(viewFriend.equals("c")) {
							System.out.print("Enter friend's full name: ");
							String name = input.nextLine();
							User temp = new User(name);
					        User tempUserFriend = userNames.get(temp);
							if(tempUserFriend == null) {
								System.out.println("Sorry, could not find your friend :(");
					        }
							else {
								user.RemoveFriendByNamed(name);
							}
						}
						else if(viewFriend.equals("d")) {
							break;
						}
						else {
							System.out.print("invalid input");
						}
				}
			}	
			else if(in.equals("C")) {
				while(true) {
					NameComparator nameC = new NameComparator();
					System.out.println("\nPlease select from the options below: \n");
					System.out.println("a. Search by name");
					System.out.println("b. Search by interest");
					System.out.println("c. Go back to main menu\n");
					System.out.print("Enter your choice: ");
					String addNewFriend = input.nextLine();
					//By Name
					if(addNewFriend.equals("a")) {
						System.out.print("\nPlease enter a full name: ");
						String name = input.nextLine();
						// check if name has last name and first name
						if(name.split(" ", 2).length != 2) {
							System.out.println("\ninvalid name");
						}
						else {
							User searchedFriend = new User(name);
							User addedFriend = userNames.get(searchedFriend);
							if(addedFriend == null) {
								System.out.println("Sorry," + name + "user does not exist in our database yet.");
							}
							else if(user.getFriendByName(name)!= null) {
								//please revise this sentence
								System.out.println();
								System.out.println(name + "already is in your friend list.");
							}
							else {
								System.out.println("\n1. Add Friend");
								System.out.println("2. Back to Main Menu\n");
								System.out.print("Enter your choice: ");
								String viewingFriendChoice = input.nextLine();
								if(viewingFriendChoice.equals("1")) {
									user.addFriend((addedFriend));
								}
								else if(viewingFriendChoice.equals("2")) {
									break;
								}
							}	
						}
					}
					//by interest
					else if(addNewFriend.equals("b")){
						System.out.print("\nPlease enter an interest: ");
						String interestName = input.nextLine();
						Interest tempInterest = new Interest(interestName);
						Interest foundInterest = interest.get(tempInterest);
						//Check if the interest exists
						if(foundInterest == null) {
							System.out.println("\nSorry, that interest does not exist in our database yet.");
						}
						else {
							//Get the interest ID
							//ArrayList<BST<User>> NewFriendByInterest = new ArrayList<BST<User>>();
							int interestID = foundInterest.getID();
							if(NewFriendByInterest.get(interestID) == null) {
								System.out.println("No user has this interest.");
							}
							else {
								ArrayList<User> interestUserList = NewFriendByInterest.get(interestID).getNodes();
								String name = "";
								for(int i = 0; i <interestUserList.size(); i++) {
									name += interestUserList.get(i).getFirstName() + " " + interestUserList.get(i).getLastName() + "\n";	
								}
								System.out.println("\nUsers that also like: " + interestName + "\n\n" + name);
								System.out.println("\n1. Add Friend");
								System.out.println("2. Back to Main Menu\n");
								System.out.print("Enter your choice: ");
								String viewingFriendChoice = input.nextLine();
								if(viewingFriendChoice.equals("1")) {
									System.out.print("\nPlease enter the name of the user you would like to add: ");
									String AddName = input.nextLine();
									User searchedFriend = new User(AddName);
									User addedFriend = userNames.get(searchedFriend);
									user.addFriend((addedFriend));
								}
								else if(viewingFriendChoice.equals("2")) {
									break;
								}
							}	
						}
					}
					else if(addNewFriend.equals("c")) {
						break;
					}	
				}	
			}
			 else if(in.equals("D")) {
				 
			 }
			 else if (in.equals("E")) {
				//quit and write records to file OutputPrincesses
				System.out.println("\nGoodbye!");
				writeFile("OutputPrincesses.txt", userNames, outputNames);
				break;
			} else {
			System.out.println("\nInvalid menu option. Please enter A-D or E to exit.");
			}
		}
	}
	
	public static void writeFile(String fileName, HashTable<User> userNames, ArrayList<String> outputNames){
        try {
            PrintWriter out = new PrintWriter(fileName);
            for(int i = 0; i < outputNames.size(); i++) {
            	User user = new User(outputNames.get(i));
            	User outUser = userNames.get(user);
            	String name = outUser.getFirstName() + " " + outUser.getLastName();
            	out.println(name);
            	String userName = outUser.getUserName();
            	out.println(userName);
            	String password = outUser.getPassword();
            	out.println(password);
            	String city = outUser.getCity();
            	out.println(city);
            	int numFriends = outUser.getNumFriends();
            	out.println(numFriends);
            	// add name of User' friend
            	String friendNames = outUser.getAllFriendName();
            	out.print(friendNames);
            	
            	int numInterests = outUser.getNumInterests();
            	out.println(numInterests);
            	String interests = "";
            	for(int j = 0; j < outUser.getInterest().length; j++) {
            	 	interests += outUser.getInterest()[j].getName() + "#" + outUser.getInterest()[j].getID() + "\n";
            	}
            	out.print(interests);
            	out.println("");
            }
            
            out.flush();
            out.close();
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}


