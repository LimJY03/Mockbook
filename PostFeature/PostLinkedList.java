package PostFeature;

import java.util.Random;

public class PostLinkedList<String> {
	private Node<String> head, tail;
	private int size;

	public PostLinkedList() {
		this.size = 0;
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
		if (this.size == 0 || this.head == null)
			return null;
		return this.head.content;
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

	public void printPosts() {
		if (this.head == null) {
			System.out.println("There are no Posts");
			return;
		}

		Node<String> current = head;
		for (int i = 0; i < this.size && i < 3; i++) {
			if (current == null) {
				System.out.println("\nThat's it ! You reached the End of the Feed :D! Please go back");
				break;
			}
			System.out.println(current.username);
			System.out.println("   " + current.content);
			System.out.println("--");
			current = current.next;
			removeFirst();
		}
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public void addFirst(String e, String c) {
		Node<String> newNode = new Node<>(e, c);

		newNode.next = this.head;
		this.head = newNode;
		this.size++;

		if (this.tail == null)
			this.tail = this.head;
	}

	public String removeFirst() {
		if (isEmpty()) {
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

	public void clear() {
		size = 0;
		head = tail = null;
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
