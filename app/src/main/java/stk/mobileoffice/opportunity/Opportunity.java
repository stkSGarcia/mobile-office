package stk.mobileoffice.opportunity;

public class Opportunity {
	private String name;
	private String description;
	private int level;

	public Opportunity(String name, String description, int level) {
		this.name = name;
		this.description = description;
		this.level = level;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getLevel() {
		return level;
	}
}
