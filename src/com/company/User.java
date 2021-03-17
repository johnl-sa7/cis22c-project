package com.company; /**
 * Interest.java
 * @author Chengyun Li Group 2 
 * CIS 22C, Final Project
 */

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Comparator;


public class User {
	private String firstName;
	private String lastName;
	private String userName;
	private String password;
	private String city;
	private int userId;
	
	private BST<User> friends = new BST<>();
	private List<Interest> interests = new List<>();
	

	
	/**CONSTRUCTORS*/
	
	/**
	 * One-arguments constructor
	 * @param name  the name of user 
	 */

	public User(String name) {
		String[] n = name.split(" ", 2);
		password = "";
		userName = "";
		this.firstName = n[0];
		this.lastName = n[1];
		userId = -1;
		city = "unknown";
		
	}
	
	/**
	* Creates a new User when only password and user name are known
	* @param userName the User userName
	* @param password the Customer password
	* Assigns firstName to "first name unknown"
	* Assigns lastName to "last name unknown"
	* Assigns userId by getUserId();
	*/
	public User(String password, String userName) {
		this.password = password;
		this.userName = userName;
		firstName = "first name unknown";
		lastName = "unknown";
		userId = -1;
		city = "unknown";
		
	}
	/**
	* Creates a new User
	* @param firstName member first name
	* @param lastName member last name
	* @param password member password
	*/
	public User(String firstName, String lastName, String userName, String password, int userId, String city) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.userName = userName;
		this.password = password;
		this.userId = userId;
		this.city = city;
	}
	
	
	/**
	* Creates a new User
	* @param firstName member first name
	* @param lastName member last name
	* @param password member password
	* @param fri the friends owned by this User
	* @param inter the interests owned by this User
	*/
	public User(String firstName, String lastName, String userName, String password, String city, int userId, ArrayList<User> alUser, ArrayList<Interest> alInterest) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.userName = userName;
		this.password = password;
		this.userId = userId;
		this.city = city;
		for(int i = 0; i < alUser.size(); i++) {
			friends.insert(alUser.get(i), new NameComparator());
		}
		for(int i = 0; i < alInterest.size(); i++) {
			interests.addLast(alInterest.get(i));
		}
	}
	
	/**ACCESORS*/

	/**
	* Accesses the user first name
	* @return the first name
	*/
	public String getFirstName() {
		return firstName;
	}

	/**
	* Accesses the user last name
	* @return the last name
	*/
	public String getLastName() {
		return lastName;
	}
	
	/**
	* Accesses the user's userName
	* @return the userName
	*/
	public String getUserName() {
		return userName;
	}
	
	/**
	* Accesses the user's city
	* @return the city
	*/
	public String getCity() {
		return city;
	}
	
	/**
	* Accesses the user's password
	* @return the password
	*/
	public String getPassword() {
		return password;
	}
	
	/**
	* Accesses the user's userId
	* @return the userId
	*/
	public int getUserId() {
		return userId;
	}

	/** Determines whether a given password matches the User password 
	* @param anotherPassword the password to compare 
	* @return whether the two passwords match 
	*/ 
	public boolean passwordMatch(String anotherPassword) { 
		return password.equals(anotherPassword); 
	}
	/**
	 * Accesses the user's number of friends
	 * @return number of friends of the user
	 */
	public int getNumFriends() {
		return friends.getSize();
	}
	
	/**
	 * Accesses the user's number of interests
	 * @return number of interests of the user
	 */
	public int getNumInterests() {
		return getInterest().length;
	}
	
	/**
	* Accesses a specific friend
	* @param name the friend name
	* @return the specified friend 
	*/
	public User getFriendByName(String name) {
		User fri = new User(name);
		return friends.search(fri, new NameComparator());
	}
	
	/**
	* Accesses a specific friend
	* @param name the friend name
	* @return the specified friend 
	*/
	public String getAllFriendName() {
		String names = "";
		ArrayList<User> str = friends.getNodes();
		if(str == null) {
			return "";
		}
		else{
			for(int i = 0; i <str.size(); i++) {
				names += str.get(i).getFirstName() + " " + str.get(i).getLastName() + "\n";	
			}
		}
		return names;
	}
	
	
	/**
	* Accesses the interests of the user
	* @param name user
	* @return all the interests of this user
	*/
	public Interest[] getInterest() {
		Interest[] inter = new Interest[interests.getLength()];
		interests.placeIterator();
		for(int i = 0; i < interests.getLength(); i++) {
			inter[i] = interests.getIterator();
			interests.advanceIterator();	
		}
		return inter;
	}
	

	/**MUTATORS*/

	/**
	* Updates the user first name
	* @param firstName a new first name
	*/
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	* Updates the user last name
	* @param lastName a new last name
	*/
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	* Updates the user's userName
	* @param userName the Customer's user name
	*/
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	* Updates the value of the password
	* @param name the User password
	*/
	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	* Updates city the user is living
	* @param city the city
	*/
	public void setCity(String city) {
		this.city = city;
	}
	
	/**
	* Updates the user first name
	* @param firstName a new first name
	*/
	public void setUserId(int userId) {
		this.userId = userId;
	}

	
	/**
	* Remove a friend from friend list
	* @param name the name of the friend 
	*/
	public void RemoveFriendByNamed(String name) {
		if(getFriendByName(name) != null) {
			User fri = new User(name);
			friends.remove(fri, new NameComparator());
		}
	}
	
	/**
	* Adds a new friend to the user's 
	* list of friends and updates Interest
	* @param newFriends new friend will add into the bst
	* @return whether the friend was added
	*/
	public boolean addFriend(User newFriend) {
		if(friends.search(newFriend, new NameComparator())!= null){
			return false;
		}
		else {
			friends.insert(newFriend, new NameComparator());
			return true;
		}
	}
	
	
	/**ADDITIONAL OPERATIONS*/
	
	
	/**
	* Prints out all the friends
	* alphabetized by name
	*/
	public void printUserFriends() {
		//System.out.println("List of friends of " + this.getFirstName() + " "  + this.getLastName() + ": ");
		friends.inOrderPrint();
	}
	
	
	/**
	 * Creates a String of the User
	 * information along with the following:
	 * Name: <firstName> l<astName>
	 * City: <city>
	 * Friends: <friends>
	 * Interests:<interest>
	 */

	@Override public String toString() {
		String name = "name: " + this.getFirstName() + " " + this.getLastName() + "\n";
		String city = "city: " + this.getCity() + "\n";
		String inter = "List of Interests: ";
		for(int i = 0; i < this.getInterest().length; i++) {
			inter += this.getInterest()[i].getName() + " ";
		}
		return name + city + inter;
	}
	/**
	* Compares this User to another
	* Object for equality
	* You must use the formula presented
	* in class (See Lesson 4)
	* @param o another Object
	* @return true if o is a User
	* and has a matching user name and password
	* to this User
	*/
	@Override public boolean equals(Object o) {
		if(o == this) {
			return true;
		}
		else if(!(o instanceof User)) {
			return false;
		}
		else {
			User temp = (User)o;
			if(this.firstName.equals(temp.getFirstName()) && this.lastName.equals(temp.getLastName())){
				return true;
			}
		}
		return false;
	}
	
	/**
	* Returns a consistent hash code for
	* each User by summing the Unicode values
	* of each character in the key
	* Key = userName + password
	* @return the hash code
	*/
	@Override public int hashCode() {
		String key = firstName + lastName;
		int sum = 0;
		for(int i = 0; i < key.length(); i++) {
			sum += (int) key.charAt(i);
		}
		return sum;
	}
}
class NameComparator implements Comparator<User> {
    /**
   * Compares the two users by name of the user
   * uses the String compareTo method to make the comparison
   * @param friend1 the first User
   * @param friend2 the second User
   */
   @Override public int compare(User friend1, User friend2) {
       String friend1Name = friend1.getFirstName() + friend1.getLastName();
       String friend2Name = friend2.getFirstName() + friend2.getLastName();
       return friend1Name.compareTo(friend2Name);
   }
}  //end class NameComparator


