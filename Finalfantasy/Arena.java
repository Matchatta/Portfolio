//Matchatta Toyaem ID:6088169 Section:2//
import java.io.IOException; 
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;

//import Player.PlayerType;

public class Arena {

	public enum Row {Front, Back};	//enum for specifying the front or back row
	public enum Team {A, B};		//enum for specifying team A or B
	
	private Player[][] teamA = null;	//two dimensional array representing the players of Team A
	private Player[][] teamB = null;	//two dimensional array representing the players of Team B
	private static int numRowPlayers = 0;		//number of players in each row
    
	public static final int MAXROUNDS = 100;	//Max number of turn
	public static final int MAXEACHTYPE = 3;	//Max number of players of each type, in each team.
	private final Path logFile = Paths.get("battle_log.txt");
	private int numRounds = 0;	//keep track of the number of rounds so far
	
	/**
	 * Constructor. 
	 * @param _numRowPlayers is the number of players in each row.
	 */
	public Arena(int _numRowPlayers)
	{	
		//finish//
		numRowPlayers=_numRowPlayers;
		teamA=new Player[2][numRowPlayers];
		teamB=new Player[2][numRowPlayers];
		//INSERT YOUR CODE HERE
		
		
		
		////Keep this block of code. You need it for initialize the log file. 
		////(You will learn how to deal with files later)
		try {
			Files.deleteIfExists(logFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		/////////////////////////////////////////
		
	}
	
	/**
	 * Returns true if "player" is a member of "team", false otherwise.
	 * Assumption: team can be either Team.A or Team.B
	 * @param player
	 * @param team
	 * @return
	 */
	public boolean isMemberOf(Player player, Team team)
	{
		//INSERT YOUR CODE HERE
		//FINISH
		if(player.team==team)
		{
		return true;	
		}
		return false;
	}
	/**
	 * This methods receives a player configuration (i.e., team, type, row, and position), 
	 * creates a new player instance, and places him at the specified position.
	 * @param team is either Team.A or Team.B
	 * @param pType is one of the Player.Type  {Healer, Tank, Samurai, BlackMage, Phoenix}
	 * @param row	either Row.Front or Row.Back
	 * @param position is the position of the player in the row. Note that position starts from 1, 2, 3....
	 */
	public void addPlayer(Team team, Player.PlayerType pType, Row row, int position)
	{	
		//INSERT YOUR CODE HERE
		//FINISH
		//TeamA
		if(team==Team.A) {
			if(row==Row.Front) {
				teamA[0][position-1]=new Player(pType);
				teamA[0][position-1].team=team;
				teamA[0][position-1].row=row;
				teamA[0][position-1].position=position;
			}
			else if(row==Row.Back) {
				teamA[1][position-1]=new Player(pType);
				teamA[1][position-1].team=team;
				teamA[1][position-1].row=row;
				teamA[1][position-1].position=position;
			}
		}
		//Team B
		else if(team==Team.B) {
			if(row==Row.Front) {
				teamB[0][position-1]=new Player(pType);
				teamB[0][position-1].team=team;
				teamB[0][position-1].row=row;
				teamB[0][position-1].position=position;
			}
			else if(row==Row.Back) {
				teamB[1][position-1]=new Player(pType);
				teamB[1][position-1].team=team;
				teamB[1][position-1].row=row;
				teamB[1][position-1].position=position;
			}
		}
	}
	
	
	/**
	 * Validate the players in both Team A and B. Returns true if all of the following conditions hold:
	 * 
	 * 1. All the positions are filled. That is, there each team must have exactly numRow*numRowPlayers players.
	 * 2. There can be at most MAXEACHTYPE players of each type in each team. For example, if MAXEACHTYPE = 3
	 * then each team can have at most 3 Healers, 3 Tanks, 3 Samurais, 3 BlackMages, and 3 Phoenixes.
	 * 
	 * Returns true if all the conditions above are satisfied, false otherwise.
	 * @return
	 */
	public boolean validatePlayers()
	{
		//INSERT YOUR CODE HERE
		//finish
		int A1 = 0, A2=0, A3=0, A4=0, A5=0, A6=0, B1=0, B2=0, B3=0, B4=0, B5=0, B6=0;
		for(int i=0; i<2; i++) {
			for(int j=0; j<numRowPlayers; j++) {
			if(teamA[i][j]==null||teamB[i][j]==null) {
				return false;
			}
					else{
						//TeamA
						if(teamA[i][j].getType()==Player.PlayerType.Healer) {
							A1++;
						}
						else if(teamA[i][j].getType()==Player.PlayerType.Tank) {
							A2++;
						}
						else if(teamA[i][j].getType()==Player.PlayerType.Samurai) {
							A3++;
						}
						else if(teamA[i][j].getType()==Player.PlayerType.BlackMage) {
							A4++;
						}
						else if(teamA[i][j].getType()==Player.PlayerType.Phoenix) {
							A5++;
						}
						else if(teamA[i][j].getType()==Player.PlayerType.Cherry) {
							A6++;
						}
						//TeamB
								else if(teamB[i][j].getType()==Player.PlayerType.Healer) {
									B1++;
								}
								else if(teamB[i][j].getType()==Player.PlayerType.Tank) {
									B2++;
								}
								else if(teamB[i][j].getType()==Player.PlayerType.Samurai) {
									B3++;
								}
								else if(teamB[i][j].getType()==Player.PlayerType.BlackMage) {
									B4++;
								}
								else if(teamB[i][j].getType()==Player.PlayerType.Phoenix) {
									B5++;
								}
								else if(teamB[i][j].getType()==Player.PlayerType.Cherry) {
									B6++;
								}
					}
			}
		}
		if(A1>MAXEACHTYPE||A2>MAXEACHTYPE||A3>MAXEACHTYPE||A4>MAXEACHTYPE
				||A5>MAXEACHTYPE||A6>MAXEACHTYPE||B1>MAXEACHTYPE||B2>MAXEACHTYPE||B3>MAXEACHTYPE||B4>MAXEACHTYPE
				||B5>MAXEACHTYPE||B6>MAXEACHTYPE) {
			return false;
		}
		else {
			return true;	
		}	
	}
	
	
	/**
	 * Returns the sum of HP of all the players in the given "team"
	 * @param team
	 * @return
	 */
	public static double getSumHP(Player[][] team)
	{
		//INSERT YOUR CODE HERE
		//finish
		double sum=0;
		for(int i=0; i<2; i++) {
			for(int j=0; j<numRowPlayers; j++) {
			sum+=team[i][j].getCurrentHP();
			}
		}
		
		return sum;
	}
	
	/**
	 * Return the team (either teamA or teamB) whose number of alive players is higher than the other. 
	 * 
	 * If the two teams have an equal number of alive players, then the team whose sum of HP of all the
	 * players is higher is returned.
	 * 
	 * If the sums of HP of all the players of both teams are equal, return teamA.
	 * @return
	 */
	public Player[][] getWinningTeam()
	{
		//INSERT YOUR CODE HERE	
		//finish
		int count=0, countB=0, sumA=0, sumB=0;
		for(int i=0; i<2; i++) {
			for(int j=0; j<numRowPlayers; j++) {
				if(teamA[i][j]==null) {
					count++;
				}
			}
		}
		for(int i=0; i<2; i++) {
			for(int j=0; j<numRowPlayers; j++) {
				if(teamB[i][j]==null) {
					countB++;
				}
			}
		}
		if(count==countB) {
			for(int i=0; i<2; i++) {
				for(int j=0; j<numRowPlayers; j++) {
				sumA+=teamA[i][j].getCurrentHP();	
				}
			}
			for(int i=0; i<2; i++) {
				for(int j=0; j<numRowPlayers; j++) {
				sumB+=teamB[i][j].getCurrentHP();	
				}
			}
			if(sumA>sumB) {
				return teamA;
			}
			else {
				return teamB;
			}
		}
		else if(count<countB){
             return teamA;
		}
		else {
			return teamB;
		}
	}
	
	/**
	 * This method simulates the battle between teamA and teamB. The method should have a loop that signifies
	 * a round of the battle. In each round, each player in teamA invokes the method takeAction(). The players'
	 * turns are ordered by its position in the team. Once all the players in teamA have invoked takeAction(),
	 * not it is teamB's turn to do the same. 
	 * 
	 * The battle terminates if one of the following two conditions is met:
	 * 
	 * 1. All the players in a team has been eliminated.
	 * 2. The number of rounds exceeds MAXROUNDS
	 * 
	 * After the battle terminates, report the winning team, which is determined by getWinningTeam().
	 */
	public void startBattle()
	{
		//INSERT YOUR CODE HERE
		//FINISH
		int i, j;
		while(numRounds<MAXROUNDS&&getSumHP(teamA)>0 && getSumHP (teamB)>0) {
                     System.out.println("@ROUNDS "+(numRounds+1));
      //TAKE ACTION
                     //BUFF OFF
                     for(i=0; i<2; i++)
                     {
	                    	 for(j=0;j<numRowPlayers;j++)
	                    	 {
	                    	 teamA[i][j].sleepOff();
	                    	 teamB[i][j].sleepOff();
	                    	 teamA[i][j].abilityOff();
	                    	 }
                     }
                     /////////////////////
					for(i=0; i<2; i++)
					{
						for(j=0; j<numRowPlayers; j++)
						{
							if(teamA[i][j].isAlive())
							{
							teamA[i][j].takeAction(this);	
							}
								
						}
					}
					/////////////////////
					//BUFF OFF
					  for(i=0; i<2; i++)
	                     {
		                    	 for(j=0;j<numRowPlayers;j++)
		                    	 {
		                    	 teamB[i][j].abilityOff();
		                    	 }
	                     }
					  ///////////////////
					for(i=0; i<2; i++)
					{
						for(j=0; j<numRowPlayers; j++)
						{
							if(teamB[i][j].isAlive())
							{
							teamB[i][j].takeAction(this);	
							}
								
						}
					}
		
			displayArea(this, true);
			logAfterEachRound();
			numRounds++;
		}
		System.out.print("@@@ TEAM "+getWinningTeam()[0][0].team+ " WON");
	}
//GET TARGET FOR CURSED && BOUBLE-SLASH
	public Player curse(Player[][] play)
	{
		int i, j, minI=0, minJ=0, go=0;
 		//find first min
 		for(i=0;i<play.length;i++)
 		{
 			for(j=0;j<numRowPlayers;j++)
 			{
 				if(play[i][j].isAlive()==true)
 				{
 					minI=i;
 					minJ=j;
 					go=1;
 					break;
 				}
 			}
 			if(go==1)
 			{
 				break;
 			}
 		}
 		//for taunt
 		for(i=0; i<play.length; i++)
 		{
 			for(j=0; j<numRowPlayers; j++)
 			{
 				if(play[i][j].isTaunting()==true&&play[i][j].isAlive()==true)
 				{
 					return play[i][j];
 				}
 			}
 		}
 		//normal
 	    if(minI==0)
 	    {
 	    		minI=0;
	 	    	for(j=0; j<numRowPlayers; j++)
	 	    	{
	 	    		if(play[minI][j].getCurrentHP()<play[minI][minJ].getCurrentHP())
	 	    		{
	 	    			if(play[minI][j].isAlive()==true)
	 	    			{
	 	    				minJ=j;
	 	    			}
	 	    		}
	 	    		else
	 	    		{
	 	    			continue;
	 	    		}
	 	    	}
 	    }
 	    else
 	    {
 	    	minI=1;
 	    	for(j=0; j<numRowPlayers; j++)
 	    	{
 	    		if(play[minI][j].getCurrentHP()<play[minI][minJ].getCurrentHP())
 	    		{
 	    			if(play[minI][j].isAlive()==true)
 	    			{
 	    				minJ=j;
 	    			}
 	    		}
 	    		else
 	    		{
 	    			continue;
 	    		}
 	    	}
 	    }
 		return play[minI][minJ];	
	}
//GET TARGET ABILITY HEAL
	public Player target(Player[][] play)
	{
		int i, j, minI=0, minJ=0, go=0;
 		//find first min
 		for(i=0;i<play.length;i++)
 		{
 			for(j=0;j<numRowPlayers;j++)
 			{
 				if(play[i][j].isAlive()==true&&play[i][j].isCursed()==false)
 				{
 					minI=i;
 					minJ=j;
 					go=1;
 					break;
 				}
 			}
 			if(go==1)
 			{
 				break;
 			}
 		}
 		//normal
 	    if(minI==0)
 	    {
 	    		minI=0;
	 	    	for(j=0; j<numRowPlayers; j++)
	 	    	{
	 	    		if(play[minI][j].getCurrentHP()<play[minI][minJ].getCurrentHP())
	 	    		{
	 	    			if(play[minI][j].isAlive()==true)
	 	    			{
	 	    				minJ=j;
	 	    			}
	 	    		}
	 	    		else
	 	    		{
	 	    			continue;
	 	    		}
	 	    	}
 	    }
 	    else
 	    {
 	    	minI=1;
 	    	for(j=0; j<numRowPlayers; j++)
 	    	{
 	    		if(play[minI][j].getCurrentHP()<play[minI][minJ].getCurrentHP())
 	    		{
 	    			if(play[minI][j].isAlive()==true)
 	    			{
 	    				minJ=j;
 	    			}
 	    		}
 	    		else
 	    		{
 	    			continue;
 	    		}
 	    	}
 	    }
 		return play[minI][minJ];	
	}
//GET ABILITY TEAM
	public Player[][] ability(Team team)
	{
		if(team==Team.A)
		{
			return teamA;
		}
		else
		{
			return teamB;
		}
	}
//GET TARGET(ATTACK)
 	public Player enermy(Team team)
	{
 		////TeamA
 		if(team==Team.A)
 		{
 		int i, j, minI=0, minJ=0, go=0;
 		//find first min
 		for(i=0;i<teamA.length;i++)
 		{
 			for(j=0;j<numRowPlayers;j++)
 			{
 				if(teamA[i][j].isAlive()==true)
 				{
 					minI=i;
 					minJ=j;
 					go=1;
 					break;
 				}
 			}
 			if(go==1)
 			{
 				break;
 			}
 		}
 		//for taunt
 		for(i=0; i<teamA.length; i++)
 		{
 			for(j=0; j<numRowPlayers; j++)
 			{
 				if(teamA[i][j].isTaunting()==true&&teamA[i][j].isAlive()==true)
 				{
 					return teamA[i][j];
 				}
 			}
 		}
 		//normal
 	    if(minI==0)
 	    {
 	    		minI=0;
	 	    	for(j=0; j<numRowPlayers; j++)
	 	    	{
	 	    		if(teamA[minI][j].getCurrentHP()<teamA[minI][minJ].getCurrentHP())
	 	    		{
	 	    			if(teamA[minI][j].isAlive()==true)
	 	    			{
	 	    				minJ=j;
	 	    			}
	 	    		}
	 	    		else
	 	    		{
	 	    			continue;
	 	    		}
	 	    	}
 	    }
 	    else
 	    {
 	    	minI=1;
 	    	for(j=0; j<numRowPlayers; j++)
 	    	{
 	    		if(teamA[minI][j].getCurrentHP()<teamA[minI][minJ].getCurrentHP())
 	    		{
 	    			if(teamA[minI][j].isAlive()==true)
 	    			{
 	    				minJ=j;
 	    			}
 	    		}
 	    		else
 	    		{
 	    			continue;
 	    		}
 	    	}
 	    }
 		return teamA[minI][minJ];
 		}
 		/////teamB
 		else
 		{
 		int i, j, minI=0, minJ=0, go=0;
 		for(i=0;i<teamB.length;i++)
 		{
 			for(j=0;j<numRowPlayers;j++)
 			{
 				if(teamB[i][j].isAlive()==true)
 				{
 					minI=i;
 					minJ=j;
 					go=1;
 					break;
 				}
 			}
 			if(go==1)
 			{
 				break;
 			}
 		}
 		//for taunt
 		for(i=0; i<teamB.length; i++)
 		{
 			for(j=0; j<numRowPlayers; j++)
 			{
 				if(teamB[i][j].isTaunting()==true&&teamB[i][j].isAlive()==true)
 				{
 					return teamB[i][j];
 				}
 			}
 		}
 	    if(minI==0)
 	    {
 	    		minI=0;
	 	    	for(j=0; j<numRowPlayers; j++)
	 	    	{
	 	    		if(teamB[minI][j].getCurrentHP()<teamB[minI][minJ].getCurrentHP())
	 	    		{
	 	    			if(teamB[minI][j].isAlive()==true)
	 	    			{
	 	    				minJ=j;
	 	    			}
	 	    		}
	 	    		else
	 	    		{
	 	    			continue;
	 	    		}
	 	    	}
 	    }
 	    else
 	    {
 	    	minI=1;
 	    	for(j=0; j<numRowPlayers; j++)
 	    	{
 	    		if(teamB[minI][j].getCurrentHP()<teamB[minI][minJ].getCurrentHP())
 	    		{
 	    			if(teamB[minI][j].isAlive()==true)
 	    			{
 	    				minJ=j;
 	    			}
 	    		}
 	    		else
 	    		{
 	    			continue;
 	    		}
 	    	}
 	    }
 		return teamB[minI][minJ];
 		}
	}
	
	/**
	 * This method displays the current area state, and is already implemented for you.
	 * In startBattle(), you should call this method once before the battle starts, and 
	 * after each round ends. 
	 * 
	 * @param arena
	 * @param verbose
	 */
	public static void displayArea(Arena arena, boolean verbose)
	{
		StringBuilder str = new StringBuilder();
		if(verbose)
		{
			str.append(String.format("%43s   %40s","Team A","")+"\t\t"+String.format("%-38s%-40s","","Team B")+"\n");
			str.append(String.format("%43s","BACK ROW")+String.format("%43s","FRONT ROW")+"  |  "+String.format("%-43s","FRONT ROW")+"\t"+String.format("%-43s","BACK ROW")+"\n");
			for(int i = 0; i < numRowPlayers; i++)
			{
				str.append(String.format("%43s",arena.teamA[1][i])+String.format("%43s",arena.teamA[0][i])+"  |  "+String.format("%-43s",arena.teamB[0][i])+String.format("%-43s",arena.teamB[1][i])+"\n");
			}
		}
	
		str.append("@ Total HP of Team A = "+getSumHP(arena.teamA)+". @ Total HP of Team B = "+getSumHP(arena.teamB)+"\n\n");
		System.out.print(str.toString());
		
		
	}
	
	/**
	 * This method writes a log (as round number, sum of HP of teamA, and sum of HP of teamB) into the log file.
	 * You are not to modify this method, however, this method must be call by startBattle() after each round.
	 * 
	 * The output file will be tested against the auto-grader, so make sure the output look something like:
	 * 
	 * 1	47415.0	49923.0
	 * 2	44977.0	46990.0
	 * 3	42092.0	43525.0
	 * 4	44408.0	43210.0
	 * 
	 * Where the numbers of the first, second, and third columns specify round numbers, sum of HP of teamA, and sum of HP of teamB respectively. 
	 */
	private void logAfterEachRound()
	{
		try {
			Files.write(logFile, Arrays.asList(new String[]{numRounds+"\t"+getSumHP(teamA)+"\t"+getSumHP(teamB)}), StandardOpenOption.APPEND, StandardOpenOption.CREATE);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
