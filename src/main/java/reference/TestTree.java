package reference;

public class TestTree {

	public static void main(String[] args) {
		testTree1();
	}

	public static void testTree1() {
		TreeNode<Integer> root = new TreeNode<Integer>(100);
		TreeNode<Integer> n101 = new TreeNode<Integer>(101);
		TreeNode<Integer> n102 = new TreeNode<Integer>(102);
		TreeNode<Integer> n103 = new TreeNode<Integer>(103);
		TreeNode<Integer> n104 = new TreeNode<Integer>(104);

		TreeNode<Integer> n1021 = new TreeNode<Integer>(1021);
		TreeNode<Integer> n1022 = new TreeNode<Integer>(1022);
		TreeNode<Integer> n1023 = new TreeNode<Integer>(1023);

		TreeNode<Integer> n10231 = new TreeNode<Integer>(10231);

		Tree<Integer> tree = new Tree<Integer>(root);
		TreeNode.TreeNodeVisiter<Integer> visiter = (TreeNode<Integer> node) -> {
			int depth = node.getDepth();
			int rootBindData = root.getBindData();
			if (depth == 0 && rootBindData == 0) {
				return true;
			}
			if (rootBindData == 0) {
				depth -= 1;
			}
			StringBuilder sb = new StringBuilder();
			if (depth > 0) {
				for (int i = 0; i < depth; i++) {
					sb.append("\t");
				}
				sb.append("---");
			}
			System.out.println(sb.toString() + node.getBindData());
			return false;
		};
		n1023.appendChildNode(n10231);
		n102.appendChildNode(n1021);
		n102.appendChildNode(n1022);
		n102.appendChildNode(n1023);
		tree.appendChildNode(n101);
		tree.appendChildNode(n102);
		tree.appendChildNode(n103);
		tree.appendChildNode(n104);
		tree.traversal(visiter, Tree.TraversalType.LOOP);
	}

}
