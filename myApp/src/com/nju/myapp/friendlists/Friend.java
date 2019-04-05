package com.nju.myapp.friendlists;

public class Friend {
	private int order;
	private int logo;
	private String name;
	private String gender;
	private int age;

	public Friend(int o, int l, String n, String g, int a) {
		order = o;
		logo = l;
		name = n;
		gender = g;
		age = a;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public int getLogo() {
		return logo;
	}

	public void setLogo(int logo) {
		this.logo = logo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

}
