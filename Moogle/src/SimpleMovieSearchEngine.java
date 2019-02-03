// Name:Matchatta Toyaem
// Student ID:6088169
// Section: 2

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class SimpleMovieSearchEngine implements BaseMovieSearchEngine {
	public Map<Integer, Movie> movies= new HashMap<Integer, Movie>();
	
	@Override
	public Map<Integer, Movie> loadMovies(String movieFilename) {
		
		// YOUR CODE GOES HERE
		//FINISH
		String detail=null;
		String pattern="(\\d+),(.+) \\((\\d+)\\),(.+)";// pattern for not contain title
		String pattern2="(\\d+),\"(.+) \\((\\d+)\\)\",(.+)";// pattern for contain title
		try 
		{
			Pattern p = Pattern.compile(pattern);
			Pattern p2 = Pattern.compile(pattern2);
			FileReader file = new FileReader(movieFilename);//Read file
			BufferedReader lineMovie = new BufferedReader(file);//read line by line
			while((detail=lineMovie.readLine())!=null)
			{
			Matcher m = p.matcher(detail);
			if(m.find())
			{
				Movie movie = new Movie(Integer.parseInt(m.group(1)), m.group(2), Integer.parseInt(m.group(3)));
				for(String tag: m.group(4).split("\\|"))
				{
					movie.addTag(tag);
				}
				movies.put(Integer.parseInt(m.group(1)), movie);
			}
			else
			{
				Matcher m2 = p2.matcher(detail);
				if(m2.find())
				{
					Movie movie = new Movie(Integer.parseInt(m2.group(1)), m2.group(2), Integer.parseInt(m2.group(3)));
					for(String tag: m2.group(4).split("\\|"))
					{
						movie.addTag(tag);
					}
					movies.put(Integer.parseInt(m2.group(1)), movie);
				}	
			}
			}
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return movies;
	}

	@Override
	public void loadRating(String ratingFilename) {

		// YOUR CODE GOES HERE
		//FINISH
		String detail=null;
		String pattern="(\\d+),(\\d+),(.+),(\\d+)";
		try 
		{
			FileReader file = new FileReader(ratingFilename);// Read file
			BufferedReader lineRating = new BufferedReader(file);// Read line by line
			Pattern p = Pattern.compile(pattern);
			while((detail=lineRating.readLine())!=null)
			{
				Matcher m = p.matcher(detail);
				if(m.find())
				{
					User u = new User(Integer.parseInt(m.group(1)));
					movies.get(Integer.parseInt(m.group(2))).addRating(u, movies.get(Integer.parseInt(m.group(2))), Double.parseDouble(m.group(3)), Long.parseLong(m.group(4)));
				}	
			}
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void loadData(String movieFilename, String ratingFilename) {
	
		// YOUR CODE GOES HERE
		//FINISH
		loadMovies(movieFilename);
		loadRating(ratingFilename);
			
	}

	@Override
	public Map<Integer, Movie> getAllMovies() {

		// YOUR CODE GOES HERE
		//FINISH
		return movies;
	}

	@Override
	public List<Movie> searchByTitle(String title, boolean exactMatch) {

		// YOUR CODE GOES HERE
		//FINISH
		List<Movie> list = new LinkedList<Movie>();//Use to keep movie if it found movies
		if(exactMatch)
		{
			for(int key: movies.keySet())
			{
				if(movies.get(key).getTitle().toLowerCase().equals(title.toLowerCase()))
				{
					list.add(movies.get(key));
				}
				else
				{
					continue;
				}
			}	
		}
		else
		{
			Pattern p = Pattern.compile(title);
			for(int key: movies.keySet())
			{
				Matcher m = p.matcher(movies.get(key).getTitle().toLowerCase());
				if(m.find())
				{
					list.add(movies.get(key));
				}
				else
				{
					continue;
				}
			}	
		}
		return list;
	}

	@Override
	public List<Movie> searchByTag(String tag) {

		// YOUR CODE GOES HERE
		//FINISH
		List<Movie> list = new LinkedList<Movie>();//Use to keep movie if it found movies
		for(int key: movies.keySet())
		{
			if(movies.get(key).getTags().contains(tag))
			{
				list.add(movies.get(key));
			}
		}
		return list;
	}

	@Override
	public List<Movie>searchByYear(int year) {

		// YOUR CODE GOES HERE
		//FINISH
		List<Movie> list = new LinkedList<Movie>();//Use to keep movie if it found movies
		for(int key: movies.keySet())
		{
			if(movies.get(key).getYear()==year)
			{
				list.add(movies.get(key));
			}
		}
		return list;
	}

	@Override
	public List<Movie> advanceSearch(String title, String tag, int year) {

		// YOUR CODE GOES HERE
		//FINISH
		List<Movie> list = new LinkedList<Movie>();//Use to keep movie if it found movies
		for(int key: movies.keySet())
		{
			if(title!=null&&tag!=null&&year!=-1)
			{
				Pattern p = Pattern.compile(title);
				Matcher m = p.matcher(movies.get(key).getTitle().toLowerCase());
				if(m.find()&&movies.get(key).getTags().contains(tag)&&movies.get(key).getYear()==year)
				{
					list.add(movies.get(key));
				}
			}
			else if(title==null)
			{
				if(movies.get(key).getTags().contains(tag)&&movies.get(key).getYear()==year)
				{
					list.add(movies.get(key));
				}
			}
			else if(tag==null)
			{
				Pattern p = Pattern.compile(title);
				Matcher m = p.matcher(movies.get(key).getTitle().toLowerCase());
				if(m.find()&&movies.get(key).getYear()==year)
				{
					list.add(movies.get(key));
				}
			}
			else if(year==-1)
			{
				Pattern p = Pattern.compile(title);
				Matcher m = p.matcher(movies.get(key).getTitle().toLowerCase());
				if(m.find()&&movies.get(key).getTags().contains(tag))
				{
					list.add(movies.get(key));
				}
			}
		}
		return list;
	}

	@Override
	public List<Movie> sortByTitle(List<Movie> unsortedMovies, boolean asc) {

		// YOUR CODE GOES HERE
		//FINISH
		if(asc)
		{
			unsortedMovies.sort(Comparator.comparing(Movie::getTitle));//Sort movies by ascending order
		}
		else
		{
			unsortedMovies.sort(Comparator.comparing(Movie::getTitle).reversed());//Sort movies by descending order
		}
		return unsortedMovies;
	}

	@Override
	public List<Movie> sortByRating(List<Movie> unsortedMovies, boolean asc) {

		// YOUR CODE GOES HERE
		//FINISH
		if(asc)
		{
			unsortedMovies.sort(Comparator.comparing(Movie::getMeanRating));//Sort movies by ascending order
		}
		else
		{
			unsortedMovies.sort(Comparator.comparing(Movie::getMeanRating).reversed());//Sort movies by descending order
		}
		return unsortedMovies;
	}

}
