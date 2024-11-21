
package application;

import java.util.Set;
import java.util.HashSet;

public class Articles {
	private String title, description, keywords, authors, body, references, group;
	private boolean special;
	private int id;
    private Set<String> allowedUsers;

	
	//default constructor
	public Articles()
	{
		this.allowedUsers = new HashSet<>(); //initialize set
	}
	
	//overloaded constructor
	public Articles(String title, String description, String keywords, String authors, String body, String references, String group, boolean special)
	{
		this.title = title;
		this.authors = authors;
		this.body = body;
		this.keywords = keywords;
		this.description = description;
		this.references = references;
		this.special = special;
		id = 0;
		this.allowedUsers = new HashSet<>(); //initialize set
		for(int i = 0; i < title.length(); i++)
		{
			char ch = title.charAt(i);
			this.id += (int)ch;
		}
		this.group = group;
	}
	
	
	//accessors
	public String getTitle()
	{
		return this.title;
	}
	
	public String getDescription()
	{
		return this.description;
	}
	
	public String getKeywords()
	{
		return this.keywords;
	}
	
	public String getAuthors()
	{
		return this.authors;
	}
	
	public String getBody()
	{
		return this.body;
	}
	
	public String getReferences()
	{
		return this.references;
	}
	
	public int getId()
	{
		return this.id;
	}
	
	public String getGroup()
	{
		return this.group;
	}
	
	public void setBody(String body)
	{
		this.body = body;
	}
	
	public boolean isSpecial() 
	{
        return this.special;
    }

    public void setSpecial(boolean special) 
    { 
        this.special = special;
    }
    
    public void addAllowedUser(String username) {
        this.allowedUsers.add(username);
    }
    
    public void setAllowedUsers(Set<String> allowedUsers) {
        this.allowedUsers = allowedUsers;
    }
    
    public Set<String> getAllowedUsers() {
        return allowedUsers;
    }
    
    public boolean isUserAllowed(String username) {
        return this.allowedUsers.contains(username);
    }
    
	public String toString()
	{
		return "Title: " + this.title + "\n"
				+ "Authors: " + this.authors + "\n"
				+ "Description: " + this.description + "\n"
				+ "Group: " + this.group + "\n"
				+ "ID: " + this.id + "\n";
	}
	
	public String toStringFull()
	{
		return "Title: " + this.title + "\n"
				+ "Authors: " + this.authors + "\n"
				+ "Description: " + this.description + "\n"
				+ "Keywords: " + this.keywords + "\n" 
				+ "Body: " + this.body + "\n"
				+ "References: " + this.references + "\n";
				
	}
	
}
