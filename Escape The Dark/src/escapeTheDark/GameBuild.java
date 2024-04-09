package escapeTheDark;

import java.util.ArrayList;
import java.util.List;

import gameobjects.Actor;
import gameobjects.Room;
import gameobjects.Thing;
import gameobjects.ThingList;
import globals.Directions;

//Game
public class GameBuild
{
	private ArrayList<Room> map;
	private Actor player;
	Thing crowbar = new Thing("crowbar", "held by the zombie.Can do some serius damage if used as a weapon, but how do you get him to drop it?");
	Thing patient = new Thing("depraved souless zombie","a zombie saying 'apple...apple...give me an apple and I'll give you a prize");
	Thing apple = new Thing("apple","a fresh apple thats very ripe and ready to be eaten");
	Thing idCard = new Thing("ID card","held by the entity. It Can be used to exit the asylum");
	Thing nurse = new Thing("entity", "long hair covers this pale witch looking entity");
	Thing oldKey = new Thing("old key","an old key that says 'office'");
	
	public GameBuild()
	{
		this.map = new ArrayList<Room>();
		String roomPrompt="q: quit\ni: list inventory\nn: go north\ns: go south\ne: go east\nw: go west";
		String exitPrompt="You escaped the asylum.\nYou hear sirens going off and you turn around and see the asylum's name.\n"
				+ "ED GEIN ASYLUM FOR THE CRIMALLY INSANE\n"
				+"...";
		
		ThingList lobbyRoomList = new ThingList();
		//Thing oldKey = new Thing("old key","an old key that says 'office'");
		lobbyRoomList.add(oldKey);
		
		ThingList clinicRoomList = new ThingList();
		//Thing apple = new Thing("apple","a fresh apple thats very ripe and ready to be eaten");
		clinicRoomList.add(apple);
		
		ThingList cafeteriaRoomList = new ThingList();
		//ThingList patientList = new ThingList();
		//Thing crowbar = new Thing("crowbar", "held by the zombie.Can do some serius damage if used as a weapon, but how do you get him to drop it?");
		cafeteriaRoomList.add(crowbar);
		//Room cafe = new Room("Cafe", "A caferteria with empty tables and a figure in a dark corner", -1, -1, -1, 0, cafeteriaRoomList, roomPrompt, 2);
		//Thing patient = new Thing("depraved souless zombie","a zombie saying 'apple...apple...give me an apple and I'll give you a prize");
		patient.setTakable(false);
		cafeteriaRoomList.add(patient);
		
		ThingList officeRoomList = new ThingList();
		//ThingList nurseList = new ThingList();
		//Thing idCard = new Thing("ID card","held by the entity. It Can be used to exit the asylum");
		officeRoomList.add(idCard);
		//Room office = new Room("Office", "A dark office with a figure looking at a omputer screen", -1, 0, -1, -1, clinicRoomList, roomPrompt, 3);
		//Thing nurse = new Thing("entity", "long hair covers this pale witch looking entity");
		nurse.setTakable(false);
		officeRoomList.add(nurse);
		
		ThingList exitList = new ThingList();
		
		ThingList playerlist = new ThingList();
		
		
		Room lobby = new Room("Lobby","a lobby in an insane asylum",1, 3, 2, 4, lobbyRoomList, roomPrompt);
		Room clinic = new Room("Clinic", "A dark clinic with empty beds", -1, 0, -1, -1, clinicRoomList, roomPrompt);
		Room cafe = new Room("Cafe", "A caferteria with empty tables and a figure in a dark corner", -1, -1, -1, 0, cafeteriaRoomList, roomPrompt);
		Room office = new Room("Office", "A dark office with a figure looking at a omputer screen", 0, -1, -1, -1, officeRoomList, roomPrompt);
		Room exit = new Room("Exit", "You made it out of the insane asylum", -1, -1, -1, -1, exitList, exitPrompt);
		map.add(lobby);
		map.add(clinic);
		map.add(cafe);
		map.add(office);
		map.add(exit);
		
		player = new Actor("John Verrückt", "someone who doesnt know how they got where they are right now", playerlist,map.get(0));
	
	}
	
	public ArrayList<Room> getMap() 
	{
        return map;
    }

    private void setMap(ArrayList<Room> gameMap) 
    {
        map = gameMap;
    }

    // player
    public Actor getPlayer() 
    {
        return player;
    }

    private void setPlayer(Actor gamePlayer) 
    {
        player = gamePlayer;
    }

    // take and drop
    private void transferObject(Thing t, ThingList fromlist, ThingList tolist) 
    {
        fromlist.remove(t);
        tolist.add(t);
    }
    
    String takeObject(String obname) 
    {
        String retStr = "";
        Thing t = player.getLocation().getThings().thisObject(obname);
        if(t.isTakable())
        {
        	transferObject(t, player.getLocation().getThings(), player.getThings());
            retStr = obname + " taken!";
        }
        return retStr;
    }
    
    private void moveActorTo(Actor p, Room aRoom) 
    {
        p.setLocation(aRoom);
    }

    // move an Actor in direction 'dir'
    private int moveTo(Actor anActor, Directions dir) 
    {
        // return: Constant representing the room number moved to
        // or NOEXIT
        //
        // try to move any Person (typically but not necessarily player)
        // in direction indicated by dir
        Room r = anActor.getLocation();
        int exit;

        switch (dir) 
        {
            case NORTH:
                exit = r.getNorth();
                break;
            case SOUTH:
                exit = r.getSouth();
                break;
            case EAST:
                exit = r.getEast();
                break;
            case WEST:
                exit = r.getWest();
                break;
            default:
                exit = Directions.NOEXIT; // noexit - stay in same room
                break;
        }
        if (exit != Directions.NOEXIT) 
        {
            moveActorTo(anActor, map.get(exit));
        }
        return exit;
    }

   void movePlayerTo(Directions dir) 
   {
        // if roomNumber = NOEXIT, display a special message, otherwise
        // display text (e.g. name and description of room)                
        if (moveTo(player, dir) == Directions.NOEXIT) 
        {
            showStr("No Exit!");
        } 
        else 
        {            
            //look();
        }
    }

    void goNorth() 
    {
        movePlayerTo(Directions.NORTH);
    }

    void goSouth() 
    {
        movePlayerTo(Directions.SOUTH);
    }

    void goWest() 
    {
        movePlayerTo(Directions.WEST);
    }

    void goEast() 
    {
        movePlayerTo(Directions.EAST);
    }

    void look() 
    {
        showStr("You are in the " + getPlayer().getLocation().describe());
    }

    void showStr(String s) 
    {
        System.out.println(s);
    }    

    void showInventory() 
    {
        showStr("You have\n" + getPlayer().getThings().describeThings());
    }
       

    public void showIntro() 
    {
        String s;
        
        s = "You awaken in a dark insane asylum lobby.\n"
                + "You dont know how you got there.\n"
        		+ "You only know your name: " + player.getName()+ "\n";
        showStr(s);
        look();
    }

    //TRYING TO FIGURE OUT HOW TO SAVE OBJECTS SO I CAN MAKE SWITCH CASE
//    final Thing nurse = map.get(3).getThings().get(1);
//	final Thing patient = map.get(2).getThings().get(1); 
//	final Thing crowbar = map.get(2).getThings().get(0);
//	final Thing apple = map.get(1).getThings().get(0);
    public void runCommand(String inputString) 
    {
    	
        String lowString = inputString.trim().toLowerCase();
        Room room = player.getLocation();
        ThingList roomList = player.getLocation().getThings();
        //The following must be obtained through map reference
//        final Thing nurse = map.get(3).getThings().get(1);
//    	final Thing patient = map.get(2).getThings().get(1); 
//    	final Thing crowbar = map.get(2).getThings().get(0);
//    	final Thing apple = map.get(1).getThings().get(0);
		//Thing idCard = map.get(3).getThings().get(0);
		//Thing oldKey = map.get(0).getThings().get(0);
		
        if (!lowString.equals("q")) 
        {
            switch(lowString)
            {
            //cases for directions
            case "q":
            	System.exit(0);
            	break;
            case "i" :
            	showInventory();
            	break;
            case "n" :
            	goNorth();
            	break;
            case "s" :
            	goSouth();
            	break;
            case "e" :
            	goEast();
            	break;
            case "w" :
            	goWest();
            	break;
            //cases for interactions
            case "0" :
            	if (!room.getThings().isEmpty())
            	{
            		if(roomList.get(0).isTakable())
            		{
            			Thing item = roomList.get(0);
            			takeObject(item.getName());
            			showInventory();
            			break;
            		}
            		else
            		{
            			System.out.println("Can not take item 0");
            			break;
            		}
            	}
            case "1" :	
            	if(roomList.get(1).equals(nurse))
            	{
            		if(player.getThings().contains(crowbar))
            		{
            			System.out.println("You killed the entity with the crowbar");
            			takeObject(roomList.get(0).getName()); //take Id
            			showInventory();
            			break;
            		}
            		else
            		{
            			System.out.println("The entity has catured you.");
            			player.setIsAlive(false);
            			break;
            		}
            	}
            	if (roomList.get(0).equals(patient))
            	{
            		if(player.getThings().contains(apple))
            		{
            			System.out.println("You gave him the apple");
            			takeObject(roomList.get(0).getName()); //take Id
            			showInventory();
            			break;
            		}
            	}
            	else
            	{
            		System.out.println("Can not get item 1");
            	}
            default:
            	System.out.println("invalid command");
            	break;
            }

        }
    }
}
