package org.example.gymmanagementjdbc.model;

public class Class {
	private int classId;
	private String name;

	public int getClassId() {
		return classId;
	}

	public void setClassId(int classId) {
		this.classId = classId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Class [classId=" + classId + ", name=" + name + "]";
	}

}
