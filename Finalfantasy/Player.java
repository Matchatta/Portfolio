//Matchatta Toyaem ID:6088169 Section:2//
public class Player {

	public enum PlayerType {Healer, Tank, Samurai, BlackMage, Phoenix, Cherry};
	public enum ActionType {Heal,Taunt,DS,Cursed,Rerive,Sleep};
	private ActionType AT;
	private PlayerType type; 	//Type of this player. Can be one of either Healer, Tank, Samurai, BlackMage, or Phoenix
	private double maxHP;		//Max HP of this player
	private double currentHP;	//Current HP of this player 
	private double atk;			//Attack power of this player
	public Arena.Team team;
	public Arena.Row row;
	private Player target=null;
	public int position;
	private int cooldown=0;
	private int numSpecialTurns;
	private boolean Taunt=false;
	private boolean Cursed=false;
	private boolean getSleep=false;
	private boolean revive=false;
	/**
	 * Constructor of class Player, which initializes this player's type, maxHP, atk, numSpecialTurns, 
	 * as specified in the given table. It also reset the internal turn count of this player. 
	 * @param _type
	 */
	public Player(PlayerType _type)
	{	
		//INSERT YOUR CODE HERE
		//finish//
		type=_type;
		if(type==PlayerType.Healer) {
			this.maxHP=4790;
			this.currentHP=this.maxHP;
			this.atk=238;
			this.numSpecialTurns=4;
			this.cooldown=0;
			this.getSleep=false;
			this.Cursed=false;
		}
		else if(type==PlayerType.Tank) {
			this.maxHP=5340;
			this.currentHP=this.maxHP;
			this.atk=255;
			this.numSpecialTurns=4;
			this.cooldown=0;
			this.getSleep=false;
			this.Taunt=false;
			this.Cursed=false;
		}
		else if(type==PlayerType.Samurai) {
			this.maxHP=4005;
			this.currentHP=this.maxHP;
			this.atk=368;
			this.numSpecialTurns=3;
			this.cooldown=0;
			this.getSleep=false;
			this.Cursed=false;
		}
		else if(type==PlayerType.BlackMage) {
			this.maxHP=4175;
			this.currentHP=this.maxHP;
			this.atk=303;
			this.numSpecialTurns=4;
			this.cooldown=0;
			this.getSleep=false;
			this.Cursed=false;
		}
		else if(type==PlayerType.Phoenix) {
			this.maxHP=4175;
			this.currentHP=this.maxHP;
			this.atk=209;
			this.numSpecialTurns=8;
			this.cooldown=0;
			this.getSleep=false;
			this.Cursed=false;
		}
		else if(type==PlayerType.Cherry) {
			this.maxHP=3560;
			this.currentHP=this.maxHP;
			this.atk=198;
			this.numSpecialTurns=4;
			this.cooldown=0;
			this.getSleep=false;
			this.Cursed=false;
		}
	}
	
	/**
	 * Returns the current HP of this player
	 * @return
	 */
	public double getCurrentHP()
	{
		//INSERT YOUR CODE HERE
		//finish//
		return currentHP;
	}
	
	/**
	 * Returns type of this player
	 * @return
	 */
	public Player.PlayerType getType()
	{
		//INSERT YOUR CODE HERE
		//finish//
		return type;
	}
	
	/**
	 * Returns max HP of this player. 
	 * @return
	 */
	public double getMaxHP()
	{
		//INSERT YOUR CODE HERE
		//finish//
		return maxHP;
	}
	
	/**
	 * Returns whether this player is sleeping.
	 * @return
	 */
	public boolean isSleeping()
	{
		//INSERT YOUR CODE HERE
		//FINISH
	if(this.getSleep==true)
	{
		return true;
	}
	else
	{
		return false;	
	}
		
	}
	
	/**
	 * Returns whether this player is being cursed.
	 * @return
	 */
	public boolean isCursed()
	{
		//INSERT YOUR CODE HERE
		//FINISH
	    if(this.Cursed==true)
	    {
	    	return true;
	    }
	    else
	    {
	    	return false;
	    }
		
	}
 
	
	/**
	 * Returns whether this player is alive (i.e. current HP > 0).
	 * @return
	 */
	public boolean isAlive()
	{
		//INSERT YOUR CODE HERE
		//FINISH
		if(this.currentHP<=0) {
			return false;
		}
		
		else {
			return true;
		}
	}
	
	/**
	 * Returns whether this player is taunting the other team.
	 * @return
	 */
	public boolean isTaunting()
	{
		//INSERT YOUR CODE HERE
		//FINISH
		if(this.Taunt==true)
		{
			return true;
		}
		else {
			return false;	
		}
	}

	
	
	public void attack(Player target)
	{	
		//INSERT YOUR CODE HERE
		//FINISH
		if(target.isAlive()==true)
		{
			target.currentHP-=this.atk;
		System.out.println("# "+this.team+"["+this.row+"]"+"["+this.position+"] "+"{"+this.type+"}"+" ATTACK "
		+target.team+" ["+target.row+"]"+"["+target.position+"] "+"{"+target.type+"}");
		if(target.currentHP<0) 
		{
			target.currentHP=0;
			target.Cursed=false;
			target.getSleep=false;
			target.Taunt=false;
		}	
		}
	
		
	}
	
	public void useSpecialAbility(Arena arena,Player[][] myTeam, Player[][] theirTeam)
	{	
		//INSERT YOUR CODE HERE
		switch(this.type)
		{
		//BLACKMAGE--------------------------
		case BlackMage:
			  Player targetCurse;
			  targetCurse=arena.curse(theirTeam);
			  if( targetCurse.isAlive()==true)
			  {
				
			   targetCurse.Cursed=true;
			reportTurn(ActionType.Cursed,targetCurse);  
			  }
			   
			break;
			//CHERRY-------------------------
		case Cherry:
			for(int i=0; i<theirTeam.length; i++)
			{
				for(int j=0; j<theirTeam[0].length; j++)
				{
					if(theirTeam[i][j].isAlive()==true)
					{
					theirTeam[i][j].getSleep=true;
					reportTurn(ActionType.Sleep,theirTeam[i][j]);	
					}
				}
			}
			break;
			//HEALER--------------------------
		case Healer:
		if(target==null)
		{
			target=arena.target(myTeam);
			if(target.isAlive()==true)
			{
				
			target.currentHP+=(arena.target(myTeam).maxHP*0.25);
		    checkBlood( target);
		    reportTurn(ActionType.Heal,target);
			}
			
		}
		else
		{
			if(target.isAlive()==true)
			{
			target.currentHP+=(arena.target(myTeam).maxHP*0.25);
		    checkBlood( target);
		    reportTurn(ActionType.Heal,target);	
			}
			
		}
			break;
			//PHOENIX-------------------------
		case Phoenix:
			int i, j=0, go=0;
			for(i=0; i<myTeam.length; i++)
			{
				for(j=0; j<myTeam[0].length; j++)
				{
					if(myTeam[i][j].isAlive()==false)
					{
						go=1;
						break;
					}
				}
				if(go==1)
				{
					break;
				}
			}
			if(myTeam[i][j].team==Arena.Team.A)
			{
				myTeam[i][j].cooldown=0;
				myTeam[i][j].revive=true;
			}
			else
			{
				myTeam[i][j].cooldown=0;
				myTeam[i][j].revive=true;
			}
			myTeam[i][j].currentHP=myTeam[i][j].maxHP*0.3;
			reportTurn(ActionType.Rerive,myTeam[i][j]);
			break;
			//SAMURAI-------------------------
		case Samurai:
		    Player target;
		    target=arena.curse(theirTeam);
		    if(target.isAlive()==true)
		    {
		    	target.currentHP-=(this.atk*2);
			if(target.currentHP<0)
			{
				target.currentHP=0;
			}
			reportTurn(ActionType.DS,target);
		    }
			
			break;
		case Tank:
			//TANK----------------------------
			this.Taunt=true;
			reportTurn(ActionType.Taunt,this);
			break;
		default:
			break;
		
		}
	}
	//CHECK BLOOD FOR HEALER
	public void checkBlood(Player play)
	{
	if(play.currentHP>=play.maxHP)
	{
		play.currentHP=play.maxHP;
	}
	else
	{
		
	}
	}
	//CURSE && SLEEP OFF
	public void sleepOff()
	{
		this.getSleep=false;
		this.Cursed=false;
		target=null;
	}
	//TAUNT OFF
	public void abilityOff()
	{
		this.Taunt=false;
		
	}
	/**
	 * This method is called by Arena when it is this player's turn to take an action. 
	 * By default, the player simply just "attack(target)". However, once this player has 
	 * fought for "numSpecialTurns" rounds, this player must perform "useSpecialAbility(myTeam, theirTeam)"
	 * where each player type performs his own special move. 
	 * @param arena
	 */
	public void takeAction(Arena arena)
	{	
		//INSERT YOUR CODE HERE
		//ATTACK
		//FINISH
		//TEAM A
		if(arena.isMemberOf(this, Arena.Team.A)==true&&this.team==Arena.Team.A&&this.isSleeping()==false)
		{
			if(this.revive==true)
			{
				this.revive=false;
			}
			this.cooldown++;
			if(this.cooldown%this.numSpecialTurns==0)
			{
			useSpecialAbility(arena,arena.ability(Arena.Team.A),arena.ability(Arena.Team.B));
			}
			else
			{
			attack(arena.enermy(Arena.Team.B));	
			}	
		}
		//TEAM B
		else if(arena.isMemberOf(this, Arena.Team.B)==true&&this.team==Arena.Team.B&&this.isSleeping()==false)
		{
			if(this.revive==true)
			{
				this.revive=false;
			}
			this.cooldown++;
			if(this.cooldown%this.numSpecialTurns==0)
			{
			useSpecialAbility(arena,arena.ability(Arena.Team.B),arena.ability(Arena.Team.A));
			}
			else
			{
			attack(arena.enermy(Arena.Team.A));	
			}
		}
			
		
	}
	
	/**
	 * This method overrides the default Object's toString() and is already implemented for you. 
	 */
	@Override
	public String toString()
	{
		return "["+this.type.toString()+" HP:"+this.currentHP+"/"+this.maxHP+" ATK:"+this.atk+"]["
				+((this.isCursed())?"C":"")
				+((this.isTaunting())?"T":"")
				+((this.isSleeping())?"S":"")
				+"]";
	}
	//REPORT THAT TURN
public void reportTurn(ActionType _AT,Player theirTeam)
{
AT=_AT;
switch(AT)
{
case Cursed:System.out.println("# "+this.team+"["+this.row+"]"+"["+this.position+"] "+"{"+this.type+"}"+" Curse "
		+theirTeam.team+" ["+theirTeam.row+"]"+"["+theirTeam.position+"] "+"{"+theirTeam.type+"}");
	break;
case DS:System.out.println("#"+this.team+"["+this.row+"]"+"["+this.position+"] "+"{"+this.type+"}"+" Double-Slashes "
		+theirTeam.team+" ["+theirTeam.row+"]"+"["+theirTeam.position+"] "+"{"+theirTeam.type+"}");
	break;
case Heal:System.out.println("#"+this.team+"["+this.row+"]"+"["+this.position+"] "+"{"+this.type+"}"+" Heals "
		+theirTeam.team+" ["+theirTeam.row+"]"+"["+theirTeam.position+"] "+"{"+theirTeam.type+"}");
	break;
case Rerive:System.out.println("#"+this.team+"["+this.row+"]"+"["+this.position+"] "+"{"+this.type+"}"+" Revive "
		+theirTeam.team+" ["+theirTeam.row+"]"+"["+theirTeam.position+"] "+"{"+theirTeam.type+"}");
	break;
case Sleep:System.out.println("#"+this.team+"["+this.row+"]"+"["+this.position+"] "+"{"+this.type+"}"+" Feeds a Fortune Cookie to "
		+theirTeam.team+" ["+theirTeam.row+"]"+"["+theirTeam.position+"] "+"{"+theirTeam.type+"}");
	break;

case Taunt:System.out.println("#"+this.team+"["+this.row+"]"+"["+this.position+"] "+"{"+this.type+"}"+" is Taunting ");
	break;
default:
	break;

}
}
	
}
