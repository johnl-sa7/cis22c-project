package com.company; /**
 * Interest.java
 * @author Michael Lin
 * @author Chengyun Li Group 2 
 * @
 * CIS 22C, Final Project
 */
import java.util.ArrayList;
import java.util.Comparator;

public class Interest {
	private String name;
	private int ID;
	
	
    /**CONSTRUCTORS*/
	
	/**
	 * One-argument constructor
	 * @param mf the mutual fund for this account
	 */
	
	public Interest(String name) {
		this.name = name;
		ID = -1;
	}
	
	/**
	* Creates a Interest when interest name and interest id are known
	* @param name interest name
	* @param ID interest id
	*/
	public Interest(String name, int ID) {
		this.name = name;
		this.ID = ID;
		
	}
	
	/**ACCESORS*/
	
	/**
	* Accesses the interest name
	* @return the interest name
	*/
	public String getName() {
		return name;
	}
	
	/**
	* Accesses the interest id
	* @return the interest id
	*/
	public int getID() {
		return ID;
	}
	
	/**MUTATORS*/
	
	/**
	* Adds a new friend to the user's 
	* list of friends and updates Interest
	* @param newFriends new friend will add into the bst
	* @return whether the friend was added
	*/
/**
	public boolean addUser(User newFriend) {
		if(fri.search(newFriend, new NameComparator())!= null){
			return false;
		}
		else {
			friends.insert(newFriend, new NameComparator());
			return true;
		}
	}
*/
	/**ADDITIONAL OPERATIONS*/
	
	/**
	 * Creates a String of Interest information in the form of: 
	 * Interest ID: <ID> 
	 * Interest Name: <name>
	 */

	@Override
	public String toString() {
		return "Interest ID: " + this.ID + "\n" + "Interest Name: " + this.name + "\n";
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
		else if(!(o instanceof Interest)) {
			return false;
		}
		else {
			Interest temp = (Interest)o;
			if(this.name.equals(temp.getName())){
				return true;
			}
		}
		return false;
	}
	
	/**
	* Returns a consistent hash code for
	* each Interest by summing the Unicode values
	* of each character in the key
	* Key = userName + password
	* @return the hash code
	*/
	@Override public int hashCode() {
			String key = name;
			int sum = 0;
			for (int i = 0; i < key.length(); i++) {
				sum += (int) key.charAt(i);
			}
			return sum;
		}
}


class InterestComparator implements Comparator<Interest> {
    /**
   * Compares the two interest by name of the interest
   * uses the String compareTo method to make the comparison
   * @param interest1 the first User
   * @param interest2 the second User
   */
   @Override public int compare(Interest interest1, Interest interest2) {
       return interest1.getName().compareTo(interest1.getName());
   }
}  //end class InterestComparator


