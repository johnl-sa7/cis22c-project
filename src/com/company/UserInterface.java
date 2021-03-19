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
		final int NUM_INTEREST = 20;
		int userId = -1;
		HashTable<UserAccount> userAccounts = new HashTable<>(NUM_ACCOUNT);
		HashTable<User> userNames = new HashTable<>(NUM_USERS);
		HashTable<Interest> interest = new HashTable<>(NUM_INTEREST);
		ArrayList<BST<User>> NewFriendByInterest = new ArrayList<BST<User>>();
		ArrayList<String> tempUsers = new ArrayList<String>();
		ArrayList<List<String>> tempFriends = new ArrayList<List<String>>();
		// OUTPUT
		ArrayList<String> outputNames = new ArrayList<>();
		for (int i = 0; i < NUM_INTEREST; i++) {
			NewFriendByInterest.add(new BST<User>());
		}

		File file = new File("PrincessesV2.txt");
		// FILE READ IN
		try {
			Scanner input = new Scanner(file);
			while (input.hasNextLine()) {
				int userIndex = 0;
				String[] str = new String[35];
				for (int i = 0; i < 35; i++) {
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
				for (int h = 0; h < friendNum; h++) {
					tempFriendsList.addLast(str[h + 5]);
				}
				tempFriends.add(tempFriendsList);
				userIndex++;
				for (int j = 0; j < Integer.parseInt(str[5 + friendNum]); j++) {

					String[] temp = str[j + friendNum + 6].split("#", 2);
					Interest inter = new Interest(temp[0], Integer.parseInt(temp[1]));

					userInterest.add(inter);
				}
				User user = new User(name[0], name[1], str[1], str[2], str[3], ++userId, friendArray, userInterest);
				tempUsers.add(name[0] + " " + name[1]);
				Interest[] temp = user.getInterest();
				for (int k = 0; k < temp.length; k++) {
					int index = temp[k].getID();
					NewFriendByInterest.get(index).insert(user, new NameComparator());
					if (!(interest.contains(temp[k]))) {
						interest.put(temp[k]);
					}
				}
				UserAccount userAccount = new UserAccount(user);
				userNames.put(user);
				userAccounts.put(userAccount);

			}
			input.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		// REBUILD FRIENDS BST FOR ALL USERS
		for (int i = 0; i < userNames.getNumElements(); i++) {
			User tempU = new User(tempUsers.get(i));
			User currentUser = userNames.get(tempU);
			tempFriends.get(i).placeIterator();
			for (int j = 0; j < tempFriends.get(i).getLength(); j++) {
				User tempF = new User(tempFriends.get(i).getIterator());
				User currentFriend = userNames.get(tempF);
				currentUser.addFriend(currentFriend);
				tempFriends.get(i).advanceIterator();
			}

		}
		// CREATE USER GRAPH
		Graph userGraph = new Graph(userNames.getNumElements());
		for (int i = 0; i < userNames.getNumElements(); i++) {
			User tempU = new User(tempUsers.get(i));
			User currentUser = userNames.get(tempU);
			tempFriends.get(i).placeIterator();
			for (int j = 0; j < tempFriends.get(i).getLength(); j++) {
				User tempF = new User(tempFriends.get(i).getIterator());
				User currentFriend = userNames.get(tempF);
				currentUser.addFriend(currentFriend);
				userGraph.addUndirectedEdge(i, currentFriend.getUserId());
				tempFriends.get(i).advanceIterator();
			}
		}

		// LOGIN
		System.out.println("Welcome to The Princess System!");

		Scanner input = new Scanner(System.in);
		System.out.print("\nPlease enter your Username: ");
		String userName = input.nextLine();

		System.out.print("\nPlease enter your Password: ");
		String password = input.nextLine();

		User user = new User(password, userName);
		UserAccount userAccount = new UserAccount(user);

		// CREATE A NEW USER
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
			// LOGIN IF EXISTING
			userAccount = userAccounts.get(userAccount);
			user = userAccount.getUser();
		}
		System.out.println("\nWelcome, " + userAccount.getUser().getFirstName() + " "
				+ userAccount.getUser().getLastName() + "!\n");

		System.out.println("Welcome to The Princess System!");

		while (true) {

			// MAIN MENU
			System.out.println("\nA. View My Friends");
			System.out.println("B. Search for a New Friend");
			System.out.println("C. Get Friend Recommendations");
			System.out.println("D. Quit and Write Records to a File\n");
			System.out.print("Enter your choice (A - D): ");
			String in = input.nextLine();

			// VIEW MY FRIENDS
			if (in.equals("A")) {
				while (true) {
					// VIEW MY FRIENDS SUB MENU
					System.out.println("\nPlease select from the options below: \n");
					System.out.println("A. View All Friends by Name");
					System.out.println("B. View a Friend's Profile");
					System.out.println("C. Remove a Friend From Your Friends List");
					System.out.println("D. Back to Main Menu\n");
					System.out.print("Enter your choice: ");
					String viewFriend = input.nextLine();

					// VIEW ALL FRIENDS BY NAME (SUB MENU)
					if (viewFriend.equals("A")) {
						if (user.getNumFriends() == 0) {
							System.out.println("You currently do not have any friends added.");
							System.out.println("You can (D.) go back to the Main Menu and Add Friends!");
						} else {
							user.printUserFriends();
						}
						// VIEW A FRIEND'S PROFILE (SUB MENU)
					} else if (viewFriend.equals("B")) {
						System.out.print("Enter a Friend's Name: ");
						String name = input.nextLine();
						User tempUserFriend = user.getFriendByName(name);
						if (tempUserFriend == null) {
							System.out.println("\nSorry, that User is not in your Friends List!");
						} else {
							System.out.println("\n" + tempUserFriend);
						}
						// REMOVE A FRIEND (SUB MENU)
					} else if (viewFriend.equals("C")) {
						System.out.print("Enter your Friend's Full Name (First and Last): ");
						String name = input.nextLine();
						User temp = new User(name);
						User tempUserFriend = userNames.get(temp);
						if (tempUserFriend == null) {
							System.out.println("\nSorry, that User is not in your Friends List!");
						} else {
							System.out.println(
									"\nAre you sure you want to remove " + name + " from your Friends List?\n");
							System.out.print("Yes or No: ");
							String removeVerification = input.nextLine();
							if (removeVerification.equals("Yes")) {
								user.RemoveFriendByNamed(name);
								userGraph.removeUndirectedEdge(user.getUserId(), tempUserFriend.getUserId());
								System.out.println("\n" + name + " has been removed from your Friends List.\n");
							} else {
								return;
							}
						}
						// RETURN TO MAIN MENU
					} else if (viewFriend.equals("D")) {
						break;
						// INVALID INPUT
					} else {
						System.out.print("\n Invalid Input. Please select A-C or D to go back to the Main Menu.");
					}
				}

				// SEARCH FOR NEW FRIENDS
			} else if (in.equals("B")) {
				while (true) {
					// SEARCH FOR NEW FRIENDS SUB MENU
					System.out.println("\nPlease select from the options below: \n");
					System.out.println("A. Search by Name");
					System.out.println("B. Search by an Interest");
					System.out.println("C. Go back to Main Menu\n");
					System.out.print("Enter your choice: ");
					String addNewFriend = input.nextLine();

					NameComparator nameC = new NameComparator();

					// SEARCH BY NAME (SUB MENU)
					if (addNewFriend.equals("A")) {
						System.out.print("\nPlease enter a Full Name (First and Last): ");
						String name = input.nextLine();
						// CHECK IF NAME HAS FIRST & LAST NAME
						if (name.split(" ", 2).length != 2) {
							System.out.println("\nInvalid Name. Please enter a Full Name (First and Last)");
						} else {
							User searchedFriend = new User(name);
							User addedFriend = userNames.get(searchedFriend);
							if (addedFriend == null) {
								System.out.println("Sorry," + name + " does not exist in our system yet.");
							} else if (user.getFriendByName(name) != null) {
								System.out.println();
								System.out.println(name + " is already in your Friends List!");
							} else {
								// PRINT FOUND USER
								System.out.println("\n" + addedFriend.toString());
								// ADD FRIEND
								System.out.println("\n1. Add Friend");
								System.out.println("2. Back to Main Menu\n");
								System.out.print("Enter your choice: ");
								String viewingFriendChoice = input.nextLine();
								if (viewingFriendChoice.equals("1")) {
									user.addFriend((addedFriend));
									// ADD RELATIONSHIP TO GRAPH
									userGraph.addUndirectedEdge(user.getUserId(), addedFriend.getUserId());
									System.out.println("\nYou and " + name + " are now friends!");
								} else if (viewingFriendChoice.equals("2")) {
									break;
								}
							}
						}
					}
					// SEARCH BY INTEREST (SUB MENU)
					else if (addNewFriend.equals("B")) {
						System.out.print("\nPlease enter an Interest: ");
						String interestName = input.nextLine();
						Interest tempInterest = new Interest(interestName);
						Interest foundInterest = interest.get(tempInterest);
						// CHECK IF THE INTEREST EXISTS
						if (foundInterest == null) {
							System.out.println("Sorry that Interest does not exist in our system yet.");
						} else {
							// GET INTEREST ID & FIND IN BST
							int interestID = foundInterest.getID();
							if (NewFriendByInterest.get(interestID) == null) {
								System.out.println("No users are currently interested in " + interestName + ".");
							} else {
								// PRINT ALL USERS THAT SHARE THE INTEREST
								System.out.println("\nUsers that also like " + interestName);
								NewFriendByInterest.get(interestID).inOrderPrint();
								// ADD FRIEND
								System.out.println("\n1. Add Friend");
								System.out.println("2. Back to Sub Menu\n");
								System.out.print("Enter your choice: ");
								String viewingFriendChoice = input.nextLine();
								if (viewingFriendChoice.equals("1")) {
									System.out.print("\nPlease enter the name of the user you would like to add: ");
									String name = input.nextLine();
									User searchedFriend = new User(name);
									User addedFriend = userNames.get(searchedFriend);
									if (addedFriend == null) {
										System.out.println("Sorry," + name + " does not exist in our system yet.");
									} else if (user.getFriendByName(name) != null) {
										System.out.println();
										System.out.println(name + " is already in your Friends List!");
									} else {
										user.addFriend((addedFriend));
										// ADD RELATIONSHIP TO GRAPH
										userGraph.addUndirectedEdge(user.getUserId(), addedFriend.getUserId());
										System.out.println("\nYou and " + name + " are now friends!");
									}
									//BACK TO SUB MENU
								} else if (viewingFriendChoice.equals("2")) {
									break;
								}
							}
						}
						//BACK TO MAIN MENU
					} else if (addNewFriend.equals("C")) {
						break;
					}
				}

				//GET FRIEND RECOMMENDATIONS
			} else if (in.equals("C")) {
				while (true) {
					System.out.println("\nA. View Recommendations");
					System.out.println("B. Add Friend");
					System.out.println("C. Back to Main Menu\n");
					System.out.print("Enter your choice: ");
					String subChoice = input.nextLine();

					//VIEW RECOMMENDATIONS (SUB MENU)
					if (subChoice.equals("A")) {
						String recommended = "";
						userGraph.BFS(user.getUserId());
						for (int i = 0; i < tempUsers.size(); i++) {
							String tempUserName = tempUsers.get(i);
							int distance = userGraph.getDistance(i);
							if (distance >= 2 && user.getFriendByName(tempUserName) == null) {
								User futureFriend = new User(tempUserName);
								recommended += "\n";
								recommended += userNames.get(futureFriend).toString();
								recommended += "\n";
							}
						}
						//DISPLAY FRIEND RECOMMENDATIONS
						System.out.println("\nSome friends you may know:");
						if (recommended.length() == 0) {
							System.out.println("You currently do not have any recommendations.");
						} else {
							System.out.println(recommended);
						}
						//ADD FRIEND (SUB MENU)
					} else if (subChoice.equals("B")) {
						System.out.print("\nPlease enter the name of the user you would like to add: ");
						String name = input.nextLine();
						User searchedFriend = new User(name);
						User addedFriend = userNames.get(searchedFriend);
						if (addedFriend == null) {
							System.out.println("Sorry," + name + " does not exist in our system yet.");
						} else if (user.getFriendByName(name) != null) {
							System.out.println();
							System.out.println(name + " is already in your Friends List!");
						} else {
							user.addFriend((addedFriend));
							// ADD RELATIONSHIP TO GRAPH
							userGraph.addUndirectedEdge(user.getUserId(), addedFriend.getUserId());
							System.out.println("\nYou and " + name + " are now friends!");
						}
					}
					//BACK TO SUB MENU
					else if (subChoice.equals("C")) {
						break;
					}
				}
				//QUIT AND WRITE OUTPUT FILE
			} else if (in.equals("D")) {
				System.out.println("\nGoodbye!");
				writeFile("OutputPrincesses.txt", userNames, outputNames);
				break;
				//INVALID MAIN MENU
			} else {
				System.out.println("\nInvalid menu option. Please enter A-C or D to Save and Exit.");
			}
		}

	}

	public static void writeFile(String fileName, HashTable<User> userNames, ArrayList<String> outputNames) {
		try {
			PrintWriter out = new PrintWriter(fileName);
			for (int i = 0; i < outputNames.size(); i++) {
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
				for (int j = 0; j < outUser.getInterest().length; j++) {
					interests += outUser.getInterest()[j].getName() + "#" + outUser.getInterest()[j].getID() + "\n";
				}
				out.print(interests);
				out.println("");
			}

			out.flush();
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}