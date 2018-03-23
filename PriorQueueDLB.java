package project3;
//
//Class for a DLB (De La Brandais) trie
//Can insert, check if a word exists, and find a Priority Queue associated with a word

import java.text.StringCharacterIterator;

public class PriorQueueDLB{
	private PriorityQueueNode root;
	private static char TERMINATOR = '\0';

	//Insert a car with make/model into the priority queue DLB
	
	public boolean insert(String vin, CarPriorityQueue carPQ){
		if(vin == null) return false;

		StringCharacterIterator it = new StringCharacterIterator(vin);

		if(root == null){ //If nothing exists, an insert will be fairly easy and straightforward
			root = new PriorityQueueNode(it.current());

			PriorityQueueNode curr = root;
			it.next();

			while(it.getIndex() < it.getEndIndex()){ //Loop through the word
				PriorityQueueNode newPQNode = new PriorityQueueNode(it.current());
				curr.setChildren(newPQNode);
				curr = curr.getChildren();

				it.next();
			}

			curr.setChildren(new PriorityQueueNode(TERMINATOR)); //Terminate the word in the trie with \0
			if(carPQ != null){ //If a PQ is inserted, travel to the current PQNode's children with \0 and insert the PQ at that PQNode
				curr = curr.getChildren();
				curr.setPriorityQueue(carPQ);
			}
		} else{
			PriorityQueueNode curr = root;

			while(it.getIndex() < it.getEndIndex()){ //Loop through each character in the word
				//At this level in the DLB, search for a PQNode in the horizontal
				//	linked-list that matches the current character; create a new
				//	PQNode if it doesn't exist.
				while(it.current() != curr.getVal()){
					if(curr.getSibling() == null){
						PriorityQueueNode newPQNode = new PriorityQueueNode(it.current());
						curr.setSibling(newPQNode);
						curr = curr.getSibling(); //Traverse to this newly created PQNode
						break;
					} else{
						curr = curr.getSibling();
					}
				}

				if(curr.getChildren() == null){ //Add a new PQNode because the character we're looking for doesn't exist
					PriorityQueueNode newPQNode = new PriorityQueueNode(it.current());
					curr.setChildren(newPQNode);
				}

				it.next();
				curr = curr.getChildren();
			}

			curr.setVal(TERMINATOR); //Terminate the word in the trie with \0
			if(carPQ != null){ //If a PQ is inserted, travel to the current PQNode's children with \0 and insert the PQ at that PQNode
				curr.setPriorityQueue(carPQ);
			}
		}

		return true;
	}

	
	
	public boolean doesExists(String makeModel) {
		if(makeModel == null || root == null) return false;

		StringCharacterIterator it = new StringCharacterIterator(makeModel);
		PriorityQueueNode curr = root;

		while(it.getIndex() < it.getEndIndex()) //Loop through each character in the makeModel
		{ 
			if(curr == null){ //If the PriorityQueueNode we're at is null, then obviously the makeModel does not exist
				return false;
			}

			while(it.current() != curr.getVal()){ //Loop through sibling linked list
				if(curr.getSibling() == null){
					return false; //No sibling PriorityQueueNode matches the current character
				} else{
					curr = curr.getSibling();
				}
			}

			curr = curr.getChildren();
			it.next();
		}

		if(curr == null){ //No PriorityQueueNode exists to terminate the PriorityQueueNode, so it doesn't exist
			return false;
		} else if(curr.getVal() == TERMINATOR){ //Reached the end of the PriorityQueueNode, so it exists
			return true;
		}

		while(curr.getSibling() != null){ //Loop through sibling linked list
			if(curr.getVal() == TERMINATOR){
				return true;
			} else{
				curr = curr.getSibling();
			}
		}

		return false;
	}

	//Return the priority queue based on a given make & model in the trie
	
	public CarPriorityQueue getPriorityQueue(String makeModel){
		if(makeModel == null || root == null) return null;

		StringCharacterIterator it = new StringCharacterIterator(makeModel);
		PriorityQueueNode curr = root;

		while(it.getIndex() < it.getEndIndex()){ //Loop through each character in the word
			if(curr == null){ //If the PQNode we're at is null, then obviously the word does not exist
				return null;
			}

			while(it.current() != curr.getVal()){ //Loop through sibling linked list
				if(curr.getSibling() == null){
					return null; //No sibling PQNode matches the current character
				} else{
					curr = curr.getSibling();
				}
			}

			curr = curr.getChildren();
			it.next();
		}

		if(curr == null){ //No PQNode exists to terminate the word, so no PQ exists
			return null;
		} else if(curr.getVal() == TERMINATOR){ //Reached the end of the word, so a PQ exists
			return curr.getPriorityQueue();
		}

		while(curr.getSibling() != null){ //Loop through sibling linked list
			if(curr.getVal() == TERMINATOR){
				return curr.getPriorityQueue();
			} else{
				curr = curr.getSibling();
			}
		}

		return curr.getPriorityQueue();
	}
}