package com.company; /** Course Project CS 22c
 *Autor: Chengyun Li
 */
import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

public class UserInterface {
	public static void main(String[] args) throws IOException {
		final int NUM_ACCOUNT = 30;
		final int NUM_USERS = 30;
		final int NUM_INTEREST= 20;
		int userId = -1;
		HashTable<UserAccount> userAccounts= new HashTable<>(NUM_ACCOUNT);
		HashTable<User> userNames = new HashTable<>(NUM_USERS);
		HashTable<Interest> interest = new HashTable<>(NUM_INTEREST);
		ArrayList<BST<User>> NewFriendByInterest = new ArrayList<BST<User>>();
		ArrayList<String> tempUsers = new ArrayList<String>();
		ArrayList<List<String>> tempFriends = new ArrayList<List<String>>();
		// output: 
		ArrayList<String> outputNames = new ArrayList<>();
		for(int i = 0; i < NUM_INTEREST; i++) {
			NewFriendByInterest.add(new BST<User>());
		}

		File file = new File("PrincessesV2.txt");
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
				List tempFriendsList = new List();
				for(int h = 0; h < friendNum; h++) {
					tempFriendsList.addLast(str[h+5]);
				}
				tempFriends.add(tempFriendsList);
				for(int j = 0; j < Integer.parseInt(str[5 + friendNum]); j++) {

					String[] temp = str[j + friendNum + 6].split("#", 2);
					Interest inter = new Interest(temp[0], Integer.parseInt(temp[1]));

					userInterest.add(inter);
				}
				User user = new User(name[0], name[1],str[1], str[2], str[3],++userId, friendArray, userInterest);
				tempUsers.add(name[0] + " " + name[1]);
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

		//Rebuild the friend bst for everyone
		//create user graph
		Graph userGraph = new Graph(userNames.getNumElements());
		for(int i = 0; i < userNames.getNumElements(); i++) {
			User tempU = new User(tempUsers.get(i));
			User currentUser = userNames.get(tempU);

			for(int j = 0; j < tempFriends.get(i).getLength(); j++) {
				tempFriends.get(i).placeIterator();
				User tempF = new User(tempFriends.get(i).getIterator());
				User currentFriend = userNames.get(tempF);
				currentUser.addFriend(currentFriend);
				userGraph.addUndirectedEdge(i,currentFriend.getUserId());
			}
		}
		userGraph.BFS(0);
		userGraph.printBFS();

		//login process optionA
		//problem: do not know how to assign a userId to a new user
		System.out.println("Welcome to The Princess System!");

		Scanner input = new Scanner(System.in);
		System.out.print("\nPlease enter your Username: ");
		String userName = input.nextLine();

		System.out.print("\nPlease enter your Password: ");
		String password = input.nextLine();

		User user = new User(password, userName);
		UserAccount userAccount = new UserAccount(user);
		if (!(userAccounts.contains(userAccount))) {
			System.out.println("\nWe don't have your account on file...");
			System.out.println("\nLet's create an account for you!");
			System.out.print("\nEnter your First Name: ");
			String first = input.nextLine();
			System.out.print("\nEnter your Last Name: ");
			String last = input.nextLine();
			user.setFirstName(first);
			user.setLastName(last);
			user.setUserId(++userId);
			outputNames.add(user.getFirstName() + " " + user.getLastName());
			userNames.put(user);
			userAccounts.put(userAccount);
		} else {
			userAccount = userAccounts.get(userAccount);
			user = userAccount.getUser();
		}
		System.out.println("\nWelcome, " + userAccount.getUser().getFirstName() + " " + userAccount.getUser().getLastName() + "!\n");

		System.out.println("Welcome to The Princess System!");

		while(true) {
			//print menu
			System.out.println("\nA. View My Friends");
			System.out.println("B. Search for a New Friend");
			System.out.println("C. Get Friend Recommendations");
			System.out.println("D. Quit and Write Records to a File\n");
			System.out.print("Enter your choice (A - D): ");
			String in = input.nextLine();
			//option B view View My Friends (has sub-menu)
			// did not work, because we decide only pass name inside user's private bst<user> friends
			if (in.equals("A"))  {
				while(true) {
					System.out.println("\nPlease select from the options below: \n");
					System.out.println("A. View All Friends by Name");
					System.out.println("B. View a Friend's Profile");
					System.out.println("C. Remove a Friend From Your Friends List");
					System.out.println("D. Back to Main Menu.\n");

					System.out.print("Enter your choice: ");
					String viewFriend = input.nextLine();
					System.out.println();
					if(viewFriend.equals("A")) {
						if(user.getNumFriends() == 0) {
							System.out.println("You currently do not have any friends added.");
							System.out.println("You can (D.) go back to the Main Menu and Add Friends!");
						}
						else {
							user.printUserFriends();
						}
					}
					else if(viewFriend.equals("B")) {
						System.out.print("Enter a Friend's Name: ");
						String name = input.nextLine();
						User tempUserFriend = user.getFriendByName(name);
						if (tempUserFriend == null){
							System.out.println("Sorry, that User is not in your Friends List!");
						}
						else {
							System.out.println(tempUserFriend);
						}
					}
					else if(viewFriend.equals("C")) {
						System.out.print("Enter your Friend's Full Name: ");
						String name = input.nextLine();
						User temp = new User(name);
						User tempUserFriend = userNames.get(temp);
						if(tempUserFriend == null) {
							System.out.println("Sorry, that User is not in your Friends List!");
						}
						else {
							System.out.println("\nAre you sure you want to remove " + name + " from your Friends List?\n");
							System.out.print("Yes or No: ");
							String removeVerification = input.nextLine();
							if(removeVerification.equals("Yes")) {
								user.RemoveFriendByNamed(name);
								System.out.println("\n" + name + " has been removed from your Friends List.\n");
							}
							else {
								return;
							}
						}
					}
					else if(viewFriend.equals("D")) {
						break;
					}
					else {
						System.out.print("invalid input");
					}
				}
			}
			else if(in.equals("B")) {
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
								System.out.println("No Users are interested in " + interestName + ".");
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
			else if(in.equals("C")) {
				System.out.println(userGraph.toString());
			}
			else if (in.equals("D")) {
				//quit and write records to file OutputPrincesses
				System.out.println("\nGoodbye!");
				writeFile("OutputPrincesses.txt", userNames, outputNames);
				break;
			} else {
				System.out.println("\nInvalid menu option. Please enter A-C or D to Save and Exit.");
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