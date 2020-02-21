//package Assignment3;

import java.util.*;
/**
 * 
 * @author Kunj Desai
 *
 * @param <E>
 */
public class IDLList<E>{
	
	private Node<E> head;
	private Node<E> tail;
	private int size;
	private ArrayList<Node<E>> indices;

	/**
	 * 
	 * Class Node to hold data and pointers
	 *
	 * @param <E>
	 */
	private static class Node<E>
	{
		private E data;
		private Node<E> next;
		private Node<E> prev;
		
		/**
		 * Constructor with one parameter.
		 * @param elem
		 */
		public Node (E elem)
		{
			data = elem;
			next = null;
			prev = null;
		}
		
		/**
		 * Constructor with following parameters.
		 * @param elem
		 * @param next
		 * @param prev
		 */
		public Node(E elem, Node<E> next, Node <E> prev)
		{
			data = elem;
			this.next = next;
			this.prev = prev;
		}
		
	}
	
	/**
	 * Constructor of IDLList class
	 */
	public IDLList()
	{
		size = 0;
		indices = new ArrayList<Node<E>>();
	}
	
	
	/**
	 * add with index method
	 * @param index
	 * @param elem
	 * @return boolean
	 */
	public boolean add(int index, E elem)
	{	
		try
		{
			if(index == 0) {
				add(elem);				
			}
			else if (index ==  size())
			{
				append(elem);
			}
			else
			{
				Node<E> thisElem = indices.get(index);
				Node<E> addElem = new Node<E>(elem, thisElem, thisElem.prev);
				thisElem.prev.next = addElem;
				thisElem.prev = addElem;
				indices.add(index, addElem);
				size++;
				
			}
			return true;
		}
		catch(Exception e)
		{
			throw e;
		}
	}
	
	/**
	 * add to head method
	 * @param elem
	 * @return boolean
	 */
	public boolean add(E elem)
	{
		Node<E> firstElement = new Node<E>(elem);
		
		if(head == null)
		{
			head = firstElement;
			tail = firstElement;	
			indices.add(firstElement);
		}
		else 
		{
			firstElement.next = head;
			head.prev = firstElement;
			head = firstElement;
			indices.add(0,firstElement);			
		}
		
	
		size ++;
		return true;
	}
	
	/**
	 * add element at last
	 * @param elem
	 * @return boolean
	 */
	public boolean append (E elem)
	{
		
		if(head == null)
		{
			add(elem);
		}
		else
		{
			Node<E> appendElement = new Node<E>(elem,null,tail);
			tail.next = appendElement;
			tail = appendElement;
			indices.add(appendElement);
			size++;
		}
		return true;
	}
	
	/**
	 * get element by index
	 * @param index
	 * @return E
	 */
	public E get (int index) 
	{
		try
		{
			return indices.get(index).data;
		}
		catch(Exception e)
		{
			throw e;
		}
	}
	
	/**
	 * get element at head
	 * @return E
	 */
	public E getHead()
	{
		try
		{
			return head.data;
		}
		catch(Exception e)
		{
			throw e;
		}
	}
	
	/**
	 * get element at tail
	 * @return E
	 */
	public E getLast()
	{
		try
		{
			return tail.data;
		}
		catch(Exception e)
		{
			throw e;
		}
	}
	
	/**
	 *
	 * @return size
	 */
	public int size()
	{
		return size;
	}
	
	/**
	 * remove element at head
	 * @return E
	 */
	public E remove ()
	{
		try
		{
			if(head == tail)
			{
				head = null;
				tail = null;
			}
			else
			{
				head = head.next;
				head.prev = null;
			}
			
			size --;
			return indices.remove(0).data;
		}
		catch(Exception e)
		{
			throw e;
		}
	}
	
	/**
	 * remove element at tail
	 * @return E
	 */
	public  E removeLast ()
	{
		if(head == tail)
		{
			head = null;
			tail = null;
		}
		else
		{
			tail = tail.prev;
			tail.next = null;
		}
		size --;
		return indices.remove(size).data;
		
	}

	/**
	 * remove element at given index
	 * @param index
	 * @return E
	 */
	public E removeAt (int index)
	{
		try {
			if(index ==  0 )
			{
				return remove();
			}
			else if( index == size - 1)
			{
				return removeLast();
			}
			else
			{
				Node<E> removeElem = indices.get(index);
				removeElem.prev.next = removeElem.next;
				removeElem.next.prev = removeElem.prev;
				size --;
				return indices.remove(index).data;
			}
		}
		catch(Exception e)
		{
			throw e;
		}
		
	}
	
	/**
	 * remove first occurrence of given element in list
	 * @param elem
	 * @return boolean
	 */
	public boolean remove (E elem)
	{
		try
		{
			E foundElem = null;
			if(head != null)
			{
				for(int i=0; i<size && foundElem == null; i++)
				{
					if(elem.equals(get(i)))
					{
						foundElem = removeAt(i);
						return true;
					}
				}
			}
			return false;
		}
		catch(Exception e)
		{
			throw e;
		}
		
	}
	
	/**
	 * Converting list to String
	 */
	public String toString()
	{
		String value = "Current list is: ";
		Node<E> nodeRef = head;
		while(nodeRef != null)
		{
			value = value + nodeRef.data;
			if(nodeRef.next != null)
				value = value + ", ";
			nodeRef = nodeRef.next;
		}
		return value;
	}
	
	
	/**
	 * Check the size of Array list.
	 */
//	public int indiceSize()
//	{
//		return indices.size();
//	}
	
}
