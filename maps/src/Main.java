import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        BinTree t = new BinTree();


        int[] keys = {20, 5, 30, 25, 40, 35, 45};
        for (int k: keys)
            t.insert(k);
        t.LNR();
        t.show();

        int k;
        do {
            System.out.print("Command or ?: ");
            String command = in.next();
            switch (command) {
                case "x", "exit", "q", "quit" -> System.exit(0);
                case "?", "h", "help" -> {
                    System.out.println("[i]nsert <int>\n[d]elete <int>\n[r]otate[l]eft <int>\n[r]otate[r]ight <int>\n[r]otate[l]eft[r]ight <int>\n[r]otate[r]ight[l]eft <int>\ne[x]it\n?");
                    continue;
                }
                default -> { // Check commands with node id
                    k = in.nextInt();
                    switch (command) {
                        case "i", "insert" -> t.insert(k);
                        case "d", "delete" -> t.delete(k);
                        case "rl", "rotateleft" -> t.rotLeft(k);
                        case "rr", "rotateright" -> t.rotRight(k);
                        case "rrl", "rotaterightleft" -> t.dRotRL(k);
                        case "rlr", "rotateleftright" -> t.dRotLR(k);
                        default -> {
                            System.out.println("Error.");
                            continue;
                        }
                    }
                }
            }
            t.show();
            t.LNR();
        } while (true);
    }
}

class BinTree {
    static class BinTreeNode {
        int nodeId;
        BinTreeNode parent;
        protected BinTreeNode left = null, right = null;

        protected BinTreeNode(int id) {
            nodeId = id;
        }

        protected BinTreeNode makeNode (int key) { return new BinTreeNode(key); }

        public void insert(int newKey) {
            if (newKey < nodeId) {
                if (left == null) {
                    left = makeNode(newKey);
                    left.parent = this;
                }
                else left.insert(newKey);
            } else if (newKey > nodeId) {
                if (right == null) {
                    right = makeNode(newKey);
                    right.parent = this;
                }
                else right.insert(newKey);
            }
        }

        public void LNR () {
            if (left != null)
                left.LNR();
            process();
            if (right != null)
                right.LNR();
        }

        public void process () {
            System.out.print(nodeId + " ");
        }

        public BinTreeNode search(int key) {
            if (key == nodeId) return this;
            if (key < nodeId && left != null) return left.search(key);
            if (key > nodeId && right != null) return right.search(key);
            return null;
        }

        public void delete (int key, boolean leftSubtree) {
            if (key == nodeId) { // Target found, do deletion
                if (left == null || right == null) // ≤ 1 subtree exists -> shortcut
                    if (leftSubtree) // Target node is left subtree of parent
                        parent.left = left == null ? right : left;
                    else // Node to be removed is right subtree of parent
                        parent.right = left == null ? right : left;
                else // Both subtrees exist
                    nodeId = left.deleteMax(this, true).nodeId;
            } else // Keep searching the target node
                if (key <= nodeId) { // Delete from left subtree, passing parent
                    if (left != null) // Otherwise: Nothing to delete from
                        left.delete(key,  true);
                } else // Delete from right subtree, passing the parent node
                    if (right != null) // Otherwise: Cannot delete anything ...
                        right.delete(key, false);
        }

        private BinTreeNode deleteMax (BinTreeNode parent, boolean leftSubtree) {
            if (right != null)
                return right.deleteMax(this, false);
            if (leftSubtree) {
                parent.left = left;
                left.parent = parent;
            }
            else {
                parent.right = left;
                left.parent = parent;
            }
            return this;
        }

        public int height() {
            return Math.max(left == null ? 0 : left.height() + 1,
                    right == null ? 0 : right.height() + 1);
        }

        public void rotRight () {
            BinTreeNode Z = this;
            BinTreeNode Y = Z.left;
            if (Y != null) {
                BinTreeNode X = Y.left;
                BinTreeNode T3 = Y.right, T4 = Z.right;
                int c = Z.nodeId;
                Z.nodeId = Y.nodeId;
                Y.nodeId = c;
                Y.left = T3;
                Y.right = T4;
                Z.left = X;
                Z.right = Y;
            }
            if (Y != null) {
                Y.parent = Z.parent;
                if (Z.parent != null && Z == Z.parent.left)
                    Z.parent.left = Y;
                else if (Z.parent != null && Z == Z.parent.right)
                    Z.parent.right = Y;
                Z.parent = Y;
            }
        }

        public void rotLeft () {
            BinTreeNode Z = this;
            BinTreeNode Y = Z.right;
            if (Y != null) {
                BinTreeNode X = Y.right;
                BinTreeNode T2 = Y.left;
                BinTreeNode T3 = Z.left;
                int c = Z.nodeId;
                Z.nodeId = Y.nodeId;
                Y.nodeId = c;
                Y.right = X;
                Y.left = Z;
                Z.right = T2;
                Z.left = T3;
            }
            if (Y != null) {
                Y.parent = Z.parent;
                if (Z.parent != null && Z == Z.parent.left)
                    Z.parent.left = Y;
                else if (Z.parent != null && Z == Z.parent.right)
                    Z.parent.right = Y;
                Z.parent = Y;
            }
        }


        protected double draw (double xMin, double xMax, double y, double e1, double e2) {
            double x = (xMin + xMax) / 2;
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.filledEllipse(x, y, e1, e2);
            if (left != null) {
                double x2 = left.draw(xMin, x, y - 1, e1, e2);
                StdDraw.setPenColor(StdDraw.BLACK);
                StdDraw.line(x, y, x2, y-1);
            }
            if (right != null) {
                double x2 = right.draw(x, xMax, y - 1, e1, e2);
                StdDraw.setPenColor(StdDraw.BLACK);
                StdDraw.line(x, y, x2, y-1);
            }
            // Text removed here to avoid lines crossing the text -> label():
            // StdDraw.setPenColor(StdDraw.WHITE);
            // StdDraw.text(x, y, String.valueOf(nodeId));
            return x;
        }

        protected void label (double xMin, double xMax, double y, double e1, double e2) {
            double x = (xMin + xMax) / 2;
            if (left != null)
                left.label(xMin, x, y - 1, e1, e2);

            if (right != null)
                right.label(x, xMax, y - 1, e1, e2);
            StdDraw.setPenColor(StdDraw.WHITE);
            StdDraw.text(x, y, String.valueOf(nodeId));
        }

        public void delete(int key, Object o, boolean b) {
        }
    }

    protected BinTreeNode makeNode (int key) {
        return new BinTreeNode(key);
    }

    protected BinTreeNode root = null;

    public void insert (int newKey) {
        if (root == null)
            root = makeNode(newKey);
        else
            root.insert(newKey);
    }

    public BinTreeNode search (int key) {
        if (root == null)
            return null;
        else
            return root.search(key);
    }

    public void delete (int key) {
        if (root != null) {
            if (root.nodeId == key) {
                if (root.left == null)
                    root = root.right;
                else
                if (root.right == null)
                    root = root.left;
                else
                    root.nodeId = root.left.deleteMax(root, true).nodeId;
            } else
                root.delete(key, null, false);
        }
    }

    public void LNR () {
        if (root != null) root.LNR();
        System.out.println();
    }

    public int height() {
        return root == null ? 0 : root.height();
    }

    public void rotLeft (int k) {
        if (root != null) {
            BinTreeNode Z = search(k);
            if (Z != null) Z.rotLeft();
        }
    }

    public void rotRight (int k) {
        if (root != null) {BinTreeNode Z = search(k);
            if (Z != null) Z.rotRight();
        }
    }

    public void dRotRL (int k) {
        if (root != null) {
            BinTreeNode Z = search(k);
            if (Z != null) {
                BinTreeNode Y = Z.right;
                if (Y != null) {
                    Y.rotRight();
                    Z.rotLeft();
                }
            }
        }
    }

    public void dRotLR (int k) {
        if (root != null) {
            BinTreeNode Z = search(k);
            if (Z != null) {
                BinTreeNode Y = Z.left;
                if (Y != null) {
                    Y.rotLeft();
                    Z.rotRight();
                }
            }
        }
    }

    public void show () {
        final int WIDTH = 500, HEIGHT = 500; // Window size
        StdDraw.setCanvasSize(WIDTH, HEIGHT);
        StdDraw.setTitle(root == null ? "Empty Tree"
                : "R-BT (root = " + root.nodeId + ", h = " + height() + ")");
        double h = height() + 1, w = Math.pow(2, h);
        StdDraw.setYscale(0, h+0.5); // Max. h levels high
        StdDraw.setXscale(0, w+1); // Max. 2^h nodes wide
        if (root != null) { // There is something to draw
            root.draw(0, w, h, w/30, (h+0.5)/30);
            root.label(0, w, h, w/30, (h+0.5)/30);
        }
    }
}