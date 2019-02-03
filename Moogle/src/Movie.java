// Name:Matchatta Toyaem
// Student ID:6088169
// Section: 2

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Movie {
	private int mid;
	private String title;
	private int year;
	private Set<String> tags;
	private Map<Integer, Rating> ratings;	//mapping userID -> rating
	private Double avgRating;
	//additional
	
	public Movie(int _mid, String _title, int _year){
		// YOUR CODE GOES HERE
		//FINISH
		mid=_mid;
		title=_title;
		year=_year;
		tags = new HashSet<String>();
		ratings= new HashMap<Integer, Rating>();
		
	}
	
	public int getID() {
		
		// YOUR CODE GOES HERE
		//FINISH
		return mid;
	}
	public String getTitle(){
		
		// YOUR CODE GOES HERE
		//FINISH
		return title;
	}
	
	public Set<String> getTags() {
		
		// YOUR CODE GOES HERE
		//FINISH
		return tags;
	}
	
	public void addTag(String tag){
		
		// YOUR CODE GOES HERE
		//FINISH
		tags.add(tag);
	}
	
	public int getYear(){
		
		// YOUR CODE GOES HERE
		//FINISH
		return year;
	}
	
	public String toString()
	{
		return "[mid: "+mid+":"+title+" ("+year+") "+tags+"] -> avg rating: " + (avgRating=getMeanRating());
	}
	
	
	public double calMeanRating(){
		
		// YOUR CODE GOES HERE
		//FINISH
		double total=0, avg=0;
		for(int key: ratings.keySet())
		{
			total += ratings.get(key).rating;
		}
		avg=total/ratings.size();
		return avg;
	}
	
	public Double getMeanRating(){
		
		// YOUR CODE GOES HERE
		//FINISH
		return calMeanRating();
	}
	
	public void addRating(User user, Movie movie, double rating, long timestamp) {
		// YOUR CODE GOES HERE
		//FINISH
		Rating Rating = new Rating(user, movie, rating, timestamp);
		ratings.put(user.uid, Rating);
	}
	
	public Map<Integer, Rating> getRating(){
		
		// YOUR CODE GOES HERE
		//FINISH
		return ratings;
	}
	
}
