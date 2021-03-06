package binarytree;


import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import common.BSTreeNode;

/**
 * 二叉树打印输出类
 */
public class BSTPrinter {

	/**
	 * Print a tree like this.
	 * 
	 *                                           2952:0
	 *                     ┌───────────────────────┴───────────────────────┐
	 *                  1249:-1                                         5866:0
	 *         ┌───────────┴───────────┐                       ┌───────────┴───────────┐
	 *      491:-1                  1572:0                  4786:1                  6190:0
	 *   ┌─────┘                                               └─────┐           ┌─────┴─────┐
	 * 339:0                                                      5717:0      6061:0      6271:0
	 * 
	 */
	public static <T extends Comparable<?>> void print(BSTreeNode<T> root){
		List<List<String>> lines = new ArrayList<List<String>>();

		List<BSTreeNode<T>> level = new ArrayList<BSTreeNode<T>>();
		List<BSTreeNode<T>> next = new ArrayList<BSTreeNode<T>>();

		level.add(root);
		int nn = 1;
		int widest = 0;
		while (nn != 0) {
			List<String> line = new ArrayList<String>();

			nn = 0;
			for (BSTreeNode<T> n : level) {
				if (n == null) {
					line.add(null);

					next.add(null);
					next.add(null);
				} else {
					String aa = n.val.toString();
					line.add(aa);
					if (aa.length() > widest) widest = aa.length();

					next.add(n.left);
					next.add(n.right);

					if (n.left != null) nn++;
					if (n.right != null) nn++;
				}
			}

			if (widest % 2 == 1) widest++;

			lines.add(line);

			List<BSTreeNode<T>> tmp = level;
			level = next;
			next = tmp;
			next.clear();
		}

		int perpiece = lines.get(lines.size() - 1).size() * (widest + 4);
		for (int i = 0; i < lines.size(); i++) {
			List<String> line = lines.get(i);
			int hpw = (int) Math.floor(perpiece / 2f) - 1;

			if (i > 0) {
				for (int j = 0; j < line.size(); j++) {

					// split node
					char c = ' ';
					if (j % 2 == 1) {
						if (line.get(j - 1) != null) {
							c = (line.get(j) != null) ? '┴' : '┘';
						} else {
							if (j < line.size() && line.get(j) != null) c = '└';
						}
					}
					System.out.print(c);

					// lines and spaces
					if (line.get(j) == null) {
						for (int k = 0; k < perpiece - 1; k++) {
							System.out.print("  ");
						}
					} else {
						for (int k = 0; k < hpw; k++) {
							System.out.print(j % 2 == 0 ? "  " : "─");
						}
						System.out.print(j % 2 == 0 ? "┌" : "┐");
						for (int k = 0; k < hpw; k++) {
							System.out.print(j % 2 == 0 ? "─" : "  ");
						}
					}
				}
				System.out.println();
			}

			// print line of numbers
			for (int j = 0; j < line.size(); j++) {

				String f = line.get(j);
				if (f == null) f = "";
				int gap1 = (int) Math.ceil(perpiece / 2f - f.length() / 2f);
				int gap2 = (int) Math.floor(perpiece / 2f - f.length() / 2f);

				// a number
				for (int k = 0; k < gap1; k++) {
					System.out.print("  ");
				}
				System.out.print(f);
				for (int k = 0; k < gap2; k++) {
					System.out.print("  ");
				}
			}
			System.out.println();

			perpiece /= 2;
		}
	}


	/**
	 * Print a tree like this.
	 *
	 * │           ┌── 15
	 * │       ┌── 7
	 * │       │   └── 14
	 * │   ┌── 3
	 * │   │   │   ┌── 13
	 * │   │   └── 6
	 * │   │       └── 12
	 * └── 1
	 *     │       ┌── 11
	 *     │   ┌── 5
	 *     │   │   └── 10
	 *     └── 2
	 *         │       ┌── 19
	 *         │   ┌── 9
	 *         │   │   └── 18
	 *         └── 4
	 *             │   ┌── 17
	 *             └── 8
	 *                 └── 16
	 */
	public static <T extends Comparable<?>> void printDirectoryTree(BSTreeNode<T> node){
		printDirectoryTree(node, "", true);
	}

	private static <T extends Comparable<?>> void printDirectoryTree(BSTreeNode<T> node, String prefix, boolean isLeft) {
		if (node == null) {
			System.out.println("Empty tree");
			return;
		}

		if (node.right != null) {
			printDirectoryTree(node.right, prefix + (isLeft ? "│   " : "     "), false);
		}

		System.out.println(prefix + (isLeft ? "└── " : "┌── ") + node.val);

		if (node.left != null) {
			printDirectoryTree(node.left, prefix + (isLeft ? "     " : "│   "), true);
		}
	}



	/**
	 * 树形打印输出
	 * 
	 *         2
	 *        / \
	 *       /   \
	 *      /     \
	 *     /       \
	 *     7       5
	 *    / \     / \
	 *   /   \   /   \
	 *   2   6   3   6
	 *  / \ / \ / \ / \
	 *  5 8 4 5 8 4 5 8
	 *
	 */
	public static <T extends Comparable<?>> void printTree(BSTreeNode<T> root){
		int maxLevel = BinaryTree.maxHeight(root);
		printNodeInternal(Collections.singletonList(root), 1, maxLevel);
	}

	private static <T extends Comparable<?>> void printNodeInternal(List<BSTreeNode<T>> nodes, int level, int maxLevel) {
		if (nodes.isEmpty() || BSTPrinter.isAllElementsNull(nodes))
			return;

		int floor = maxLevel - level;
		int edgeLines = (int) Math.pow(2, (Math.max(floor - 1, 0)));
		int firstSpaces = (int) Math.pow(2, (floor)) - 1;
		int betweenSpaces = (int) Math.pow(2, (floor + 1)) - 1;

		BSTPrinter.printWhitespaces(firstSpaces);

		List<BSTreeNode<T>> newNodes = new ArrayList<BSTreeNode<T>>();
		for (BSTreeNode<T> node : nodes) {
			if (node != null) {
				System.out.print(node.val + (node.haveColor() ? (node.isRed() ? "△" : "▉") : ""));
				newNodes.add(node.left);
				newNodes.add(node.right);
			} else {
				newNodes.add(null);
				newNodes.add(null);
				System.out.print(" ");
			}
			BSTPrinter.printWhitespaces(betweenSpaces);
		}
		System.out.println("");

		for (int i = 1; i <= edgeLines; i++) {
			for (int j = 0; j < nodes.size(); j++) {
				BSTPrinter.printWhitespaces(firstSpaces - i);
				if (nodes.get(j) == null) {
					BSTPrinter.printWhitespaces(edgeLines + edgeLines + i + 1);
					continue;
				}

				if (nodes.get(j).left != null){
					System.out.print("/");
				}else{
					BSTPrinter.printWhitespaces(1);
				}
				BSTPrinter.printWhitespaces(i + i - 1);

				if(nodes.get(j).right != null){
					System.out.print("\\");
				}else {
					BSTPrinter.printWhitespaces(1);
				}
				BSTPrinter.printWhitespaces(edgeLines + edgeLines - i);
			}
			System.out.println("");
		}
		printNodeInternal(newNodes, level + 1, maxLevel);
	}

	private static void printWhitespaces(int count) {
		for (int i = 0; i < count; i++)
			System.out.print(" ");
	}

	private static <T> boolean isAllElementsNull(List<T> list) {
		for (Object object : list) {
			if (object != null)
				return false;
		}
		return true;
	}


	/**
	 * it used print the given node and its children nodes,
	 * every layer output in one line
	 * @param root
	 *
	 * d(B)
	 * b(B d left) g(R d right)
	 * a(R b left) e(B g left) h(B g right)
	 * f(R e right)
	 */
	public static <T extends Comparable<?>> void printRBTreeDetails(BSTreeNode<T> root){
		LinkedList<BSTreeNode<T>> queue =new LinkedList<BSTreeNode<T>>();
		LinkedList<BSTreeNode<T>> queue2 =new LinkedList<BSTreeNode<T>>();
		if(root == null){
			return ;
		}
		queue.add(root);
		boolean firstQueue = true;

		while(!queue.isEmpty() || !queue2.isEmpty()){
			LinkedList<BSTreeNode<T>> q = firstQueue ? queue : queue2;
			BSTreeNode<T> n = q.poll();

			if(n != null){
				String pos = n.parent == null ? "" : ( n == n.parent.left
						? " left" : " right");
				String pstr = n.parent == null ? "" : n.parent.val+"";
				String cstr = n.isRed()? "R△" : "B▉";
				cstr = n.parent == null ? cstr : cstr + " ";
				System.out.print(n.val + "("+ (cstr) + pstr + (pos) + ")\t");
				if(n.left != null){
					(firstQueue ? queue2 : queue).add(n.left);
				}
				if(n.right != null){
					(firstQueue ? queue2 : queue).add(n.right);
				}
			}else{
				System.out.println();
				firstQueue = !firstQueue;
			}
		}
		System.out.println();
	}
}
