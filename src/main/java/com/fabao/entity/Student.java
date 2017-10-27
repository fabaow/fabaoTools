package com.fabao.entity;

public class Student {
	private String Name;
	private int age;

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
	public int aa(){
		return age*age;
	}
}
