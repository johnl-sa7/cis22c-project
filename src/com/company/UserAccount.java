package com.company;

import java.util.Comparator;

public class UserAccount {
	private User user;

	/**CONSTRUCTORS*/

	/**
	 * One-argument constructor
	 * @param mf the mutual fund for this account
	 */
	public UserAccount(User user) {
		this.user = user;
	}


	/**ACCESORS*/

	/**
	 * Accesses the user
	 * @return the user
	 */
	public User getUser() {
		return user;
	}


	/**ADDITIONAL OPERATIONS*/

	@Override public String toString() {
		return user.toString();
	}

	/**
	 * Compares this UserAccount to another
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
		else if(!(o instanceof UserAccount)) {
			return false;
		}
		else {
			UserAccount temp = (UserAccount)o;
			if(this.user.getUserName().equals(temp.getUser().getUserName()) && user.passwordMatch(temp.getUser().getPassword())){
				return true;
			}
		}
		return false;
	}

	/**
	 * Returns a consistent hash code for
	 * each User by summing the unicode values
	 * of each character in the key
	 * Key = userName + password
	 * @return the hash code
	 */
	@Override public int hashCode() {
		String key = user.getUserName() + user.getPassword();
		int sum = 0;
		for(int i = 0; i < key.length(); i++) {
			sum += (int) key.charAt(i);
		}
		return sum;
	}
}

