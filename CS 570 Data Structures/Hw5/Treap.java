package Assignment5;

import java.util.Random;
import java.util.Stack;
/**
 * 
 * @author Kunj Desai
 *
 * @param <E> Generic Type Data
 */
public class Treap <E extends Comparable<E>>{
	
	private Random priorityGenerator;
	private Node<E> root;
	
	/**
	 * private class Node to do create and manipulate node
	 * @author Kunj Desai
	 *
	 * @param <E>
	 */
	private static class Node<E>
	{
		public E data; //key for the search
		public int priority; //random heap priority
		public Node<E> left;
		public Node<E> right;
		
		/**
		 * constructor of Node class.
		 * @param data The key to be stored for sorting.
		 * @param priority Priority to create max-heap
		 */
		public Node(E data, int priority)
		{
			if(data == null)
			{
				throw new NullPointerException("Data is not provided.");
			}
			this.data = data;
			this.priority = priority;
			this.left = null;
			this.right =null;
		}
		
		/**
		 * This will perform right rotation of the node.
		 * @return The root node of result tree
		 */
		Node<E> rotateRight()
		{
			Node<E> rootNode = new Node<E>(data, priority);
			if(this.left != null)
			{
				rootNode.left = this.left.right;
				rootNode.right = this.right;
				this.priority = this.left.priority;
				this.data = this.left.data;			
				this.left = this.left.left;
				this.right = rootNode;
			}
			return this;
			
		}
		
		/**
		 * This will perform left rotation of the node.
		 * @return The root node of the result tree.
		 */
		Node<E> rotateLeft()
		{
			Node<E> rootNode = new Node<E>(data, priority);
			if(this.right != null)
			{
				rootNode.left = this.left;
				rootNode.right = this.right.left;
				this.priority = this.right.priority;
				this.data = this.right.data;				
				this.right = this.right.right;
				this.left = rootNode;
			}
			return this;
		}
	}
	
	/**
	 * Empty constructor of main class Treap.
	 */
	public Treap()
	{
		priorityGenerator = new Random();
		
	}
	
	/**
	 * Constructor with parameter. 
	 * @param seed
	 */
	public Treap(long seed)
	{
		priorityGenerator = new Random(seed);
	}
	
	/**
	 * This function takes just the key and creates priority and perform insertion in tree.
	 * @param key The key which will be added to tree.
	 * @return Boolean value of addition operation.
	 */
	boolean add(E key)
	{
		int priority =  priorityGenerator.nextInt();
		return add(key, priority);
	}
	
	/**
	 * This function takes key and priority and insert and arrange the tree.
	 * @param key The key which will be added to tree. 
	 * @param priority Priority on which max-heap will be arranged.
	 * @return Boolean value of addition operation.
	 */
	boolean add(E key, int priority)
	{
		Node<E> newNode = new Node<E>(key, priority);
		Node<E> tempRoot = root;
		Stack<Node> stack = new Stack<Node>();
		if(root == null)
		{
			root = new Node(key, priority);
			return true;
					
		}
		if(find(key))
		{
			return false;
		}		
		while(tempRoot !=  null)
		{
			stack.push(tempRoot);
			if((key.compareTo(tempRoot.data)) < 0)
			{
				tempRoot = tempRoot.left;
			}
			else
			{
				tempRoot = tempRoot.right;
			}
		}
		
		
		if((key.compareTo((E)stack.peek().data)) < 0)
		{	
			stack.peek().left = newNode;
		}
		else
		{		
			stack.peek().right = newNode;			
		}
		stack.push(newNode);
		reheap(stack);
		return true;
	}
	
	/**
	 * Helper function to arrange tree according to max-heap.
	 * @param stack Stack of nodes to  be arranged.
	 */
	private void reheap(Stack<Node> stack) {
		// TODO Auto-generated method stub
		Node<E> newNode = stack.pop();
		Node<E> parentOfNewNode = stack.pop();
		
		while(parentOfNewNode != null && newNode.priority > parentOfNewNode.priority)
		{
			if((newNode.data.compareTo(parentOfNewNode.data)) > 0)
			{
				parentOfNewNode.rotateLeft();
			}
			else
			{
				parentOfNewNode.rotateRight();
			}
			
			if(!stack.isEmpty())
			{
				parentOfNewNode = stack.pop();
			}
			else
			{
				parentOfNewNode = null;
			}
		}
		
	}

	/**
	 * This function deletes the node with given key.
	 * @param key The key to be deleted from the node.
	 * @return boolean value for the deletion of node.
	 */
	boolean delete(E key)
	{
		Node<E> tempRoot = root;
		Node<E> nodeFound = null ;
		Node<E> lastParent = null;
		Node<E> currentRoot = null;
		if(!find(key))
		{
			return false;
		}
		else
		{
			while(tempRoot != null)
			{
				if(key.compareTo(tempRoot.data) < 0)
				{
					currentRoot = tempRoot;
					tempRoot = tempRoot.left;
				}
				else if((key.compareTo(tempRoot.data)) > 0)
				{
					currentRoot = tempRoot;
					tempRoot = tempRoot.right;
				}
				else
				{
					nodeFound = tempRoot;
					break;
				}
			}
			while((nodeFound.left != null) || (nodeFound.right != null))
			{
				if(nodeFound.right == null )
				{
					lastParent = nodeFound.rotateRight();
					nodeFound = lastParent.right;
				}
				else if(nodeFound.left == null)
				{
					lastParent = nodeFound.rotateLeft();
					nodeFound = lastParent.left;
				}
				else if(nodeFound.left.priority > nodeFound.right.priority)
				{
					lastParent = nodeFound.rotateRight();
					nodeFound = lastParent.right;
				}
				else if (nodeFound.left.priority < nodeFound.right.priority)
				{
					lastParent = nodeFound.rotateLeft();
					nodeFound = lastParent.left;
				}

			}	
			if(lastParent == null)
			{
				lastParent = currentRoot;
			}
			if((lastParent.left != null) &&(lastParent.left.data.compareTo(key)) == 0)
			{
				lastParent.left = null;
			}
			else
			{
				lastParent.right = null;
			}
			
			return true;
			
		}
		
	}
	
	/**
	 * Finds the element in the tree.
	 * @param root Take the root node of the tree.
	 * @param key Takes the key to find in the tree.
	 * @return boolean value for find operation.
	 */
	private boolean find(Node<E> root, E key)
	{
		if(root == null)
		{
			return false;
		}
		else if((key.compareTo(root.data)) < 0)
		{
			return find(root.left, key);
		}
		else if((key.compareTo(root.data)) > 0)
		{
			return find(root.right, key);
		}
		else 
		{
			return true;
		}
	}
	
	/**
	 * Takes only the element/key to perform find.
	 * @param key The key to be found in the tree.
	 * @return boolean value for find operation.
	 */
	public boolean find(E key)
	{
		return find(root, key);
	}
	
	/**
	 * Returns a string value of the tree.
	 * @return String value of the tree.
	 */
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		return getPreOrder(root, 1, sb);
	}
	
	/**
	 * Gets the tree in the pre-order form.
	 * @param node Takes the root as a node.
	 * @param depth Takes the level of the starting node/root
	 * @param sb StringBuilder object to build a string for the tree.
	 * @return
	 */
	private String getPreOrder(Node<E> node, int depth, StringBuilder sb) {
		// TODO Auto-generated method stub
		//Reference taken from in-class exercise
		for(int i=0; i< depth; i++)
		{
			sb.append("  ");
		}		
		if(node == null)
		{
			sb.append("null \n");
		}
		else
		{
			sb.append("(key="+node.data+", priority="+node.priority+") \n");
			getPreOrder(node.left, depth + 1, sb);
			getPreOrder(node.right, depth + 1, sb);
		}
		
		return sb.toString();
		
	}

	/**
	 * Main method()
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("=========================First test case========================= \n");
		
		//Test case given in hw pdf.
		Treap<Integer> testTree = new Treap < Integer >();
		testTree.add (4 ,19);
		testTree.add (2 ,31);
		testTree.add (6 ,70);
		testTree.add (1 ,84);
		testTree.add (3 ,12);
		testTree.add (5 ,83);
		testTree.add (7 ,26);
		System.out.println(testTree.toString());
		System.out.println("Check if treap consists of a node with key '6'? "+ testTree.find(6));
		System.out.println("Check if treap consists of a node with key '16'? "+ testTree.find(16));
		System.out.println("Delete if key with '6' exsists and the boolean result is: "+ testTree.delete(6));
		System.out.println("Delete if key with '16' exsists and the boolean result is: "+ testTree.delete(16));
		System.out.println("Check if treap consists of a node with key '6' after deletion? "+ testTree.find(6));
		
		System.out.println("=========================Second test case========================= \n");
		//Test case given in hw pdf.
		Treap<Character> testTree1 = new Treap<Character>();
		testTree1.add('p',99);
		testTree1.add('g',80);
		testTree1.add('u',75);
		testTree1.add('a',60);
		testTree1.add('j',65);
		testTree1.add('r',40);
		testTree1.add('z',47);
		testTree1.add('w',32);
		testTree1.add('v',21);
		testTree1.add('x',25);
		System.out.println(testTree1.toString());
		testTree1.add('i',93);
		System.out.println("Delete if key with 'z' exsists and the boolean result is: "+ testTree1.delete('z') +"\n");
		System.out.println(testTree1.toString());
		
		System.out.println("=========================Third test case========================= \n");
		//Test case with random number
		Treap<String> testTree2 = new Treap<String>();
		testTree2.add("apple");
		testTree2.add("cherry");
		testTree2.add("banana");
		testTree2.add("mango");
		testTree2.add("orange");
		testTree2.add("grapes");
		System.out.println(testTree2.toString());
		System.out.println("Check if treap consists of a node with key 'banana'? "+ testTree2.find("banana"));
		System.out.println("Add element with the same key 'banana' and the boolean result is: "+ testTree2.add("banana"));
		System.out.println("Delete node with key 'grapes' and the boolean result is: "+ testTree2.delete("grapes") +"\n");
		testTree2.add("kiwi",109);
		System.out.println(testTree2.toString());
		
		System.out.println("=========================Fourth test case========================= \n");
		//Random test case
		Treap<String> testTree3 = new Treap<String>();
		testTree3.add("apple",105);
		testTree3.add("cherry",91);
		testTree3.add("banana",70);
		testTree3.add("mango",100);
		testTree3.add("orange",90);
		testTree3.add("grapes",50);

		System.out.println(testTree3.toString());
		System.out.println("Check if treap consists of a node with key 'banana'? "+ testTree3.find("banana"));
		System.out.println("Add element with the same key 'banana' and the boolean result is: "+ testTree3.add("banana"));
		System.out.println("Delete node with key 'grapes' and the boolean result is: "+ testTree3.delete("banana") +"\n");
		testTree2.add("kiwi",109);
		System.out.println(testTree3.toString());
	}

}
