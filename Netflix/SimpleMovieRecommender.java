//Name: Matchatta Toyaem 
//Student ID:6088169
//Section : 2
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.*;
import org.apache.commons.io.FileUtils;

import com.google.common.collect.Sets;

public class SimpleMovieRecommender implements BaseMovieRecommender
{
	public Map<Integer, Movie> movies;//storage all movies
	public Map<Integer, User> users;// storage users
	Map<Integer, Double> MEAN = new HashMap<Integer, Double>();
	Map<Integer, Map<Integer, Double>> S = new HashMap<Integer, Map<Integer, Double>> ();
	@Override
	public Map<Integer, Movie> loadMovies(String movieFilename)
	{
		// TODO Auto-generated method stub
		String detail=null;
		String pattern="(\\d+),(.+) \\((\\d+)\\),(.+)";// pattern for not contain title
		String pattern2="(\\d+),\"(.+) \\((\\d+)\\)\",(.+)";// pattern for contain title
		Map<Integer, Movie> Movies = new TreeMap<Integer, Movie>();
		int MID, YEAR;
		String NAME, TAGS;
		try 
		{
			Pattern p = Pattern.compile(pattern);
			Pattern p2 = Pattern.compile(pattern2);
			FileReader file = new FileReader(movieFilename);//Read file
			BufferedReader lineMovie = new BufferedReader(file);//read line by line
			while((detail=lineMovie.readLine())!=null)
			{
				Matcher m = p.matcher(detail);
				Matcher m2 = p2.matcher(detail);
				if(m.find())// not contain title
				{
					MID=Integer.parseInt(m.group(1));
					NAME=m.group(2);
					YEAR=Integer.parseInt(m.group(3));
					TAGS=m.group(4);
					Movie movie = new Movie(MID, NAME, YEAR);
					//add tag to the movies
					for(String tag: TAGS.split("\\|"))
					{
						movie.addTag(tag);
					}
					Movies.put(MID, movie);
				}
				else if(m2.find())// contain title
				{
					MID=Integer.parseInt(m2.group(1));
					NAME=m2.group(2);
					YEAR=Integer.parseInt(m2.group(3));
					TAGS=m2.group(4);
					Movie movie = new Movie(MID, NAME, YEAR);
					//add tag to the movie
					for(String tag: TAGS.split("\\|"))
					{
						movie.addTag(tag);
					}
					Movies.put(MID, movie);
				}
			}
		} 
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return Movies;
	}

	@Override
	public Map<Integer, User> loadUsers(String ratingFilename) 
	{
		// TODO Auto-generated method stub
		int i=0, UID, MID;
		double RATE;
		long TIME;
		Map<Integer, User> Users = new HashMap<Integer, User>();
		String detail=null;
		String[] spt;
		try 
		{
			FileReader file = new FileReader(ratingFilename);
			BufferedReader read = new BufferedReader(file);
			while((detail=read.readLine())!=null)
			{
				spt = detail.split(",");
				if(i>0)
				{
					UID=Integer.parseInt(spt[0]);
					MID=Integer.parseInt(spt[1]);
					RATE=Double.parseDouble(spt[2]);
					TIME=Long.parseLong(spt[3]);
					//MAKE USER
					if(Users.containsKey(UID))
					{
						Users.get(UID).addRating(movies.get(MID), RATE, TIME);
					}
					else
					{
						Users.put(UID, new User(UID));
						Users.get(UID).addRating(movies.get(MID), RATE, TIME);
					}
				}
				i++;
			}
			
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Users;
	}

	@Override
	public void loadData(String movieFilename, String userFilename) 
	{
		// TODO Auto-generated method stub
		movies=loadMovies(movieFilename);
		users=loadUsers(userFilename);
	}

	@Override
	public Map<Integer, Movie> getAllMovies()
	{
		// TODO Auto-generated method stub
		return movies;
	}

	@Override
	public Map<Integer, User> getAllUsers() 
	{
		// TODO Auto-generated method stub
		return users;
	}

	@Override
	public void trainModel(String modelFilename) 
	{
		// TODO Auto-generated method stub
		Map<Integer ,ArrayList<Double>> Rating = new HashMap<Integer,ArrayList<Double>>();
		ArrayList<Double> rate;
		ArrayList<Integer> USER_ID ;
		Double[][] s = new Double[users.size()][users.size()];
		double RUI=0, RVI=0, Reminder=0, D1=0, D2=0, RUM=0, RVM=0;
		int i=0, j=0;
		try 
		{
			PrintWriter write = new PrintWriter(modelFilename,"UTF-8");
			write.println("@NUM_USERS "+users.size());
			write.print("@USER_MAP {");
			for(int UID: users.keySet())
			{
				if(users.size()-1>i)
				{
					write.print(i+"="+UID+", ");	
				}
				else
				{
					write.println(i+"="+UID+"}");
				}
				i++;
			}
			i=0;
			write.println("@NUM_MOVIES "+movies.size());
			write.print("@MOVIE_MAP {");
			for(int MID: movies.keySet())
			{
				if(movies.size()-1>i)
				{
					write.print(i+"="+MID+", ");	
				}
				else
				{
					write.println(i+"="+MID+"}");
				}
				i++;
			}
			System.out.println("@@@ Computing user rating matrix");
			for(int UID: users.keySet())
			{
				rate = new ArrayList<Double>();
				for(int MID: movies.keySet())
				{
					if(users.get(UID).ratings.containsKey(MID))
					{
						rate.add(users.get(UID).ratings.get(MID).rating);
					}
					else
					{
						rate.add(0.0);
					}
				}
				rate.add(users.get(UID).getMeanRating());
				Rating.put(UID, rate);
			}
			System.out.println("@@@ Computing user sim matrix");
			USER_ID = new ArrayList<Integer>();
			USER_ID.addAll(users.keySet());
			for(int h=0 ; h<USER_ID.size(); h++)
			{
				int UID=USER_ID.get(h);
				RUM=users.get(UID).getMeanRating();//set mean
				//s = new Double[users.size()][users.size()];
				for(int h2=h; h2<USER_ID.size(); h2++)
				{
					
					int UID2=USER_ID.get(h2);
					if(UID==UID2)
					{
						s[h][h2]=1.0;
					}
					else
					{
						//Calculate Similarity of user
						RVM=users.get(UID2).getMeanRating();//set mean
						RUI=0; RVI=0; Reminder=0; D1=0; D2=0;//reset
						Set<Integer> intersection = Sets.intersection(users.get(UID).ratings.keySet(), users.get(UID2).ratings.keySet());//find intersection of movie
						for(int MID : intersection)
						{
								RUI=users.get(UID).ratings.get(MID).rating-RUM;
								RVI=users.get(UID2).ratings.get(MID).rating-RVM;
								Reminder+= RUI*RVI;
								D1+= Math.pow(RUI, 2);
								D2+= Math.pow(RVI, 2);
						}
						D1=Math.sqrt(D1);
						D2=Math.sqrt(D2);
						if(D1==0||D2==0)
						{
							s[h][h2]=0.0;
							s[h2][h]=0.0;
						}
						else
						{
							if(Reminder/(D1*D2)>=1)
							{
								s[h][h2]=1.0;
								s[h2][h]=1.0;
							}
							else if(Reminder/(D1*D2)<=-1)
							{
								s[h][h2]=-1.0;
								s[h2][h]=-1.0;
							}
							else
							{
								s[h][h2]=Reminder/(D1*D2);
								s[h2][h]=Reminder/(D1*D2);
							}
						}
					}
				}
			}
			System.out.println("@@@ Writing out model file");
			write.println("@RATING_MATRIX");
			for(int UID : Rating.keySet())
			{
				for(i=0; i<Rating.get(UID).size(); i++)
				{
					write.print(Rating.get(UID).get(i)+" ");
				}
				write.println();
			}
			write.println("@USERSIM_MATRIX");
			for(i=0; i<users.size(); i++)
			{
				for(j=0; j<users.size(); j++)
				{
					write.print(s[i][j]+" ");
				}
				write.println();
			}
			write.close();
		} 
		catch (FileNotFoundException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (UnsupportedEncodingException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void loadModel(String modelFilename) 
	{
		// TODO Auto-generated method stub
		List<String> data;
		int start=0, start2=0, count=0, count2=0, US=0;
		double rate=0;
		Map<Integer, Double> SIM;
		ArrayList<Integer> UID = new ArrayList<Integer>();
		Pattern p =Pattern.compile("@USER_MAP \\{(.+)\\}");
		File file = new File(modelFilename);
		try
		{
			data=FileUtils.readLines(file);
			start = data.indexOf("@RATING_MATRIX")+1;
			start2 = data.indexOf("@USERSIM_MATRIX")+1;
			for(int i=0; i<data.size(); i++)
			{
				Matcher m = p.matcher(data.get(i));
				if(m.find())
				{
					for(String u : m.group(1).split(", "))
					{
						int uid = Integer.parseInt(u.split("=")[1]);
						UID.add(uid);
					}
					
					break;
				}
			}
			
				for(int i=start; i<start2-1; i++)
				{
					String[] RATE=data.get(i).split(" ");
					rate= Double.parseDouble(RATE[RATE.length-1]);
					MEAN.put(UID.get(US), rate);
					US++;
				}

				for(int k=start2; k<data.size(); k++)
				{
					count2=0;
					SIM = new HashMap<Integer, Double>();
					for(String spt : data.get(k).split(" "))
					{
						double sim = Double.parseDouble(spt);
						SIM.put(UID.get(count2), sim);
						count2++;
					}
					S.put(UID.get(count), SIM);
					count++;
				}
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public double predict(Movie m, User u) 
	{
		// TODO Auto-generated method stub
		double P_Value=0, Reminder=0, Dominater=0;
		Map<Integer, User> USER_NO_U = new HashMap<Integer, User>();
		if(MEAN.containsKey(u.uid))
		{
			for(int Key: users.keySet())
			{
				if(Key!=u.uid&&users.get(Key).ratings.containsKey(m.mid))
				{
					USER_NO_U.put(Key, users.get(Key));//all users rate movies m.mid except u
				}
			}
			if(USER_NO_U.isEmpty())
			{
				return MEAN.get(u.uid);
			}
			else
			{
				for(int Key : USER_NO_U.keySet())
				{
					Reminder += (S.get(u.uid).get(Key))*(USER_NO_U.get(Key).ratings.get(m.mid).rating-MEAN.get(Key));
					Dominater += Math.abs(S.get(u.uid).get(Key));
				}
				if(Dominater==0)
				{
					return MEAN.get(u.uid);
				}
				else
				{
					P_Value= MEAN.get(u.uid)+(Reminder/Dominater);
					if(P_Value>5)
					{
						P_Value=5;
					}
					else if(P_Value<0)
					{
						P_Value=0;
					}
				}
			}
			return P_Value;
		}
		//////////////////////
		else
		{
			return u.getMeanRating();
		}
	}

	@Override
	public List<MovieItem> recommend(User u, int fromYear, int toYear, int K) 
	{
		// TODO Auto-generated method stub
		List<MovieItem> Movies = new ArrayList<MovieItem>();
		for(int key : movies.keySet())
		{
			if(movies.get(key).year>=fromYear&&movies.get(key).year<=toYear)
			{
				Movies.add(new MovieItem(movies.get(key),predict(movies.get(key),u)));
			}
		}
		Collections.sort(Movies);
		if(Movies.size()>K)
		{
			Movies.subList(K, Movies.size()-1).clear();
		}
		else if(Movies.size()<K)
		{
			return Movies;
		}
		return Movies.subList(0, K);
	}

}
