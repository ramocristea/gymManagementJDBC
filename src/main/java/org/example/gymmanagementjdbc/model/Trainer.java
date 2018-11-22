package org.example.gymmanagementjdbc.model;

public class Trainer {
	private int trainerId;
	private String firstName;
	private String lastName;
	private int experience;

	public int getTrainerId() {
		return trainerId;
	}

	public void setTrainerId(int trainerId) {
		this.trainerId = trainerId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getExperience() {
		return experience;
	}

	public void setExperience(int experience) {
		this.experience = experience;
	}

	@Override
	public String toString() {
		return "Trainer [trainerId=" + trainerId + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", experience=" + experience + "]";
	}

}
