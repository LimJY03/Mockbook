package PostFeature;

import java.util.Random;

public class PostLinkedList<String> {
	private Node<String> head, tail;
	private int size;
	private int index;

	public PostLinkedList() {
		this.size = 0;
		this.index = 0;
		this.head = this.tail = null;
	}

	public void shuffle() {
		Random random = new Random();

		for (int i = this.size - 1; i > 0; i--) {
			int j = random.nextInt(i + 1);
			swap(i, j);
		}
	}

	private void swap(int i1, int i2) {
		if (i1 == i2) {
			return;
		}

		Node<String> node1 = getNode(i1);
		Node<String> node2 = getNode(i2);

		String temp = node1.username;
		String temp1 = node1.content;
		node1.username = node2.username;
		node1.content = node2.content;
		node2.username = temp;
		node2.content = temp1;
	}

	private Node<String> getNode(int index) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException("out of range for index");
		}
		Node<String> current = head;
		for (int i = 0; i < index; i++) {
			current = current.next;
		}

		return current;
	}

	public String getFirst() {
		if (size == 0 || this.head == null)
			return null;
		return this.head.content;
	}

	public String getLast() {
		if (size == 0)
			return null;
		return this.tail.content;
	}

	public void insertPostList(PostLinkedList<String> s) {
		if (s.head == null)
			return;
		Node<String> current = s.head;
		for (int i = 0; i < s.size; i++) {
			addFirst(current.username, current.content);
			current = current.next;
			size++;
		}
	}

	public void insertThree(PostLinkedList<String> s, int index) {
		if (index < 0 || index >= s.size) {
			return;
		}
		if (s.head == null)
			return;
		Node<String> current = s.head;
		for (int i = 0; i < index - 1; i++) {
			addFirst(current.username, current.content);
			current = current.next;
			size++;
		}
	}

	public void removeThree() {
		removeFirst();
		removeFirst();
		removeFirst();
	}

//	public void printfirst() {
//
//		if (this.head == null) {
//			return;
//		}
//		Node<String> current = head;
//		for (int i = 0; i < this.size && i < 3; i++) {
//                    if(current == null)
//                    {
//                        System.out.println("\nThat's it ! You reached the End of the Feed :D! Please go back");
//                    }
//			System.out.println(current.username);
//			System.out.println("   "+current.content);
//                        System.out.println("--");
//			current = current.next;
//			removeFirst();
//			this.index++;
//		}
//	}
//
//	public int getIndex() {
//		return this.index;
//	}
//
//	public void printthree(int index) {
//		if (index < 0 || index >= size) {
//			System.out.println("\nThat's it ! You reached the End of the Feed :D! Please go back");
//			return;
//		}
//		if (head == null)
//			return;
//		Node<String> current = head;
//		for (int i = 0; i < index - 1; i++) {
//			if (current == null) {
//				System.out.println("\nThat's it ! You reached the End of the Feed :D! Please go back");
//				return;
//			}
//			current = current.next;
//			removeFirst();
//		}
//
//		for (int i = 0; i < 3; i++) {
//			if (current == null) {
//				System.out.println("\nThat's it ! You reached the End of the Feed :D! Please go back");
//				return;
//			}
//			System.out.println(current.username);
//			System.out.println("   " + current.content);
//                        System.out.println("--");
//			current = current.next;
//                        removeFirst();
//
//		}
//	}
//        
        public void printPosts()
        {
            if (this.head == null) {
                    System.out.println("There are no Posts");
                    return;
		}
            
		Node<String> current = head;
		for (int i = 0; i < this.size && i < 3; i++) {
                    if(current == null)
                    {
                        System.out.println("\nThat's it ! You reached the End of the Feed :D! Please go back");
                        break;
                    }
			System.out.println(current.username);
			System.out.println("   "+current.content);
                        System.out.println("--");
			current = current.next;
			removeFirst();
			this.index++;
		}
        }

	public boolean isEmpty() {
		return size == 0;
	}

	public void addFirst(String e, String c) {
		Node<String> newNode = new Node<>(e, c);

		// this check if the post and the user exist in the same time but i don't think
		// this is needed.

		// Node<String> current = this.head;
//    for (int i = 0; i < this.size; i++) {
//        if(current.content.equals(c) && current.username.equals(e))
//        {
//            System.out.println("i was here");
//            return;
//        }
//        current = current.next;
//    }

		newNode.next = this.head;
		this.head = newNode;
		this.size++;

		if (this.tail == null)
			this.tail = this.head;
	}

	public void addLast(String e, String c) {
		Node<String> newNode = new Node<String>(e, c); // Create a new for element e

		if (tail == null) {
			head = tail = newNode; // The new node is the only node in list
		} else {
			tail.next = newNode; // Link the new with the last node
			tail = tail.next; // tail now points to the last node
		}

		size++; // Increase size
	}

	public String removeFirst() {
		if (size == 0) {
			return null;
		}
		if (head == null)
			return null;
		else {
			Node<String> temp = head;
			head = head.next;
			size--;
			if (head == null) {
				tail = null;
			}
			return temp.content;
		}
	}

	public String removeLast() {
		if (size == 0) {
			return null;
		} else if (size == 1) {
			Node<String> temp = head;
			head = tail = null;
			size = 0;
			return temp.content;
		} else {
			Node<String> current = head;

			for (int i = 0; i < size - 2; i++) {
				current = current.next;
			}

			Node<String> temp = tail;
			tail = current;
			tail.next = null;
			size--;
			return temp.content;
		}
	}

	public String remove(int index) {
		if (index < 0 || index >= size) {
			return null;
		} else if (index == 0) {
			return removeFirst();
		} else if (index == size - 1) {
			return removeLast();
		} else {
			Node<String> previous = head;

			for (int i = 1; i < index; i++) {
				previous = previous.next;
			}

			Node<String> current = previous.next;
			previous.next = current.next;
			size--;
			return current.content;
		}
	}

	public void clear() {
		size = 0;
		head = tail = null;
		index = 0;
	}

	public void print() {
		if (head == null)
			return;
		Node<String> current = head;
		for (int i = 0; i < this.size; i++) {
			System.out.println(current.username);
			System.out.println("   " + current.content);
                        System.out.println("--");
			current = current.next;
		}
	}

	private static class Node<String> {
		String content;
		String username;
		Node<String> next;

		public Node(String username, String content) {
			this.username = username;
			this.content = content;
		}
	}
}