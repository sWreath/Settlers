/*
*	@file City.java
*	@author Ryan Niday
*	@date 11-1-16
*	@brief a class to perform the activities of a city from catan
*/
package infoClasses;

import java.io.Serializable;

import GameBoard.PointNode;

public class City implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2001076157730740250L;
	PlayerInfo player;
	PointNode location;
	int roll;
	int[] pieceDieVal;
	int[] resource;
	/* @pre a is a valid PointNode, b is a valid PlayerInfo
	*  @post a City object is created with variables initialized to appropriate values
	*  @return none
	*/
	public City(PointNode a, PlayerInfo b){
		player = b;
		pieceDieVal = new int[3];
		resource = new int[3];
		location = a;
		for(int i = 0; i < 3; i++){
			if(location.getAdjacentPiece()[i] == null){
				pieceDieVal[i] = 0;
			}
			else{
				pieceDieVal[i] = location.getAdjacentPiece()[i].getTileID();
			}
		}
		for(int i = 0; i < 3; i++){
			if(location.getAdjacentPiece()[i] == null){
				resource[i] = 0;
			}
			else{
				resource[i] = location.getAdjacentPiece()[i].getTileResource();
			}
		}
	}
	
	/* @pre a is a valid int
	*  @post edits values in player object in comparison to the inputed a value
	*  @return none
	*/
	public void getResources(int a){
		roll = a;
		for(int i = 0; i < 3; i++){
			if(pieceDieVal[i] == roll){
				if(location.getAdjacentPiece()[i] != null && location.getAdjacentPiece()[i].getRobber()){
					System.out.println("Robber prevents from gathering");
				}
				else if(resource[i] == 1){
					player.setWheat((player.getWheat()+1));
				}
				else if(resource[i] == 2){
					player.setWood((player.getWood()+1));
				}
				else if(resource[i] == 3){
					player.setSheep((player.getSheep()+1));
				}
				else if(resource[i] == 4){
					player.setOre((player.getOre()+1));
				}
				else if(resource[i] == 5){
					player.setBrick((player.getBrick()+1));
				}
			}
		}
	}
}
