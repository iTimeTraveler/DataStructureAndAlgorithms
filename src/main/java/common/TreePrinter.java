package common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TreePrinter {

	/**
	 * Print a tree
	 */
	public static <T extends Comparable<?>> void print(TreeNode<T> root){
		List<List<String>> lines = new ArrayList<List<String>>();

		List<TreeNode<T>> level = new ArrayList<TreeNode<T>>();
		List<TreeNode<T>> next = new ArrayList<TreeNode<T>>();

		level.add(root);
		int nn = 1;
		int widest = 0;
		while (nn != 0) {
			List<String> line = new ArrayList<String>();

			nn = 0;
			for (TreeNode<T> n : level) {
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

			List<TreeNode<T>> tmp = level;
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
	 * 树形打印输出
	 */
	public static <T extends Comparable<?>> void printTree(TreeNode<T> root){
		int maxLevel = Tree.maxHeight(root);
		printNodeInternal(Collections.singletonList(root), 1, maxLevel);
	}

	private static <T extends Comparable<?>> void printNodeInternal(List<TreeNode<T>> nodes, int level, int maxLevel) {
		if (nodes.isEmpty() || TreePrinter.isAllElementsNull(nodes))
			return;

		int floor = maxLevel - level;
		int edgeLines = (int) Math.pow(2, (Math.max(floor - 1, 0)));
		int firstSpaces = (int) Math.pow(2, (floor)) - 1;
		int betweenSpaces = (int) Math.pow(2, (floor + 1)) - 1;

		TreePrinter.printWhitespaces(firstSpaces);

		List<TreeNode<T>> newNodes = new ArrayList<TreeNode<T>>();
		for (TreeNode<T> node : nodes) {
			if (node != null) {
				System.out.print(node.val);
				newNodes.add(node.left);
				newNodes.add(node.right);
			} else {
				newNodes.add(null);
				newNodes.add(null);
				System.out.print(" ");
			}
			TreePrinter.printWhitespaces(betweenSpaces);
		}
		System.out.println("");

		for (int i = 1; i <= edgeLines; i++) {
			for (int j = 0; j < nodes.size(); j++) {
				TreePrinter.printWhitespaces(firstSpaces - i);
				if (nodes.get(j) == null) {
					TreePrinter.printWhitespaces(edgeLines + edgeLines + i + 1);
					continue;
				}

				if (nodes.get(j).left != null){
					System.out.print("/");
				}else{
					TreePrinter.printWhitespaces(1);
				}
				TreePrinter.printWhitespaces(i + i - 1);

				if(nodes.get(j).right != null){
					System.out.print("\\");
				}else {
					TreePrinter.printWhitespaces(1);
				}
				TreePrinter.printWhitespaces(edgeLines + edgeLines - i);
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

}
