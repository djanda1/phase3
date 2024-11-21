package application;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;

public class Articles {
	private String title, description, keywords, authors, body, references, group, level;
	private int id, idForSearch;
	private boolean special;
	private Set<String> allowedUsers;
	//default constructor
	public Articles()
	{
	}
	
	//overloaded constructor
	public Articles(String title, String description, String keywords, String authors, String body, String references, String group, String level, boolean special)
	{
		this.title = title;
		this.authors = authors;
		this.body = body;
		this.keywords = keywords;
		this.description = description;
		this.references = references;
		id = 0;
		this.special = special;
		this.allowedUsers = new HashSet<String>();
		for(int i = 0; i < title.length(); i++)
		{
			char ch = title.charAt(i);
			this.id += (int)ch;
		}
		this.group = group;
		this.level = level;
	}
	
	
	//accessors
	public boolean isSpecial()
	{
		return this.special;
	}
	
	public String getTitle()
	{
		return this.title;
	}
	
	public void setSpecial(boolean special)
	{
		this.special = special;
	}
	
	public void addAllowedUser(String username) {
        this.allowedUsers.add(username);
    }
    
    public void setAllowedUsers(String allowedUsers) {
        this.allowedUsers.add(allowedUsers);
    }
    
    public Set<String> getAllowedUsers() {
        return allowedUsers;
    }
    
    public boolean isUserAllowed(String username) {
        return this.allowedUsers.contains(username);
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
	
	public String getLevel()
	{
		return this.level;
	}
	
	public int getIdForSearch()
	{
		return this.idForSearch;
	}
	
	public void setIdForSearch(int i)
	{
		this.idForSearch = i;
	}
	
	public void setTitle(String title)
	{
		this.title = title;
	}
	
	public void setDescription(String d) {
		this.description = d;
	}
	
	public void setKeywords(String k)
	{
		this.keywords = k;
	}
	
	public void setAuthor(String a)
	{
		this.authors = a;
	}
	
	public void setReferences(String r)
	{
		this.references = r;
	}
	
	public void setGroup(String g)
	{
		this.group = g;
	}
	
	public void setLevel(String l)
	{
		this.level = l;
	}
	public String toString()
	{
		String text = "";
		
		if(idForSearch > 0)
			text += this.idForSearch + "\n";
		text += "Title: " + this.title + "\n"
				+ "Authors: " + this.authors + "\n"
				+ "Description: " + this.description + "\n"
				+ "Group: " + this.group + "\n"
				+ "ID: " + this.id + "\n"
				+ "Level: " + this.level + "\n";
		return text;
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
