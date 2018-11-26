import java.util.*;
import java.io.*;

class AVL {

  AVLNode root;

  AVL(){
    root = null;
  }

  // A utility function to get the height of the tree
  int height(AVLNode N) {
    if (N == null)
      return 0;

    return N.height;
  }

  // A utility function to get maximum of two integers
  int max(int a, int b) {
    return (a > b) ? a : b;
  }

  // A utility function to right rotate subtree rooted with y
  AVLNode rightRotate(AVLNode y) {
    AVLNode x = y.left;
    AVLNode T2 = x.right;

    // Perform rotation
    x.right = y;
    y.left = T2;

    // Update heights
    y.height = max(height(y.left), height(y.right)) + 1;
    x.height = max(height(x.left), height(x.right)) + 1;

    // Return new root
    return x;
  }

  // A utility function to left rotate subtree rooted with x
  AVLNode leftRotate(AVLNode x) {
    AVLNode y = x.right;
    AVLNode T2 = y.left;

    // Perform rotation
    y.left = x;
    x.right = T2;

    // Update heights
    x.height = max(height(x.left), height(x.right)) + 1;
    y.height = max(height(y.left), height(y.right)) + 1;

    // Return new root
    return y;
  }

  // Get Balance factor of node N
  int getBalance(AVLNode N) {
    if (N == null)
      return 0;

    return height(N.left) - height(N.right);
  }

  AVLNode insert(AVLNode node, Position data) {

    /* 1. Perform the normal BST insertion */
    if (node == null)
      return (new AVLNode(data));

    if (data.wordIndex < node.data.wordIndex)
      node.left = insert(node.left, data);
    else if (data.wordIndex > node.data.wordIndex)
      node.right = insert(node.right, data);
    else // Duplicate datas not allowed
      return node;

    /* 2. Update height of this ancestor node */
    node.height = 1 + max(height(node.left),
              height(node.right));

    /* 3. Get the balance factor of this ancestor
      node to check whether this node became
      unbalanced */
    int balance = getBalance(node);

    // If this node becomes unbalanced, then there
    // are 4 cases Left Left Case
    if (balance > 1 && data.wordIndex < node.left.data.wordIndex)
      return rightRotate(node);

    // Right Right Case
    if (balance < -1 && data.wordIndex > node.right.data.wordIndex)
      return leftRotate(node);

    // Left Right Case
    if (balance > 1 && data.wordIndex > node.left.data.wordIndex) {
      node.left = leftRotate(node.left);
      return rightRotate(node);
    }

    // Right Left Case
    if (balance < -1 && data.wordIndex < node.right.data.wordIndex) {
      node.right = rightRotate(node.right);
      return leftRotate(node);
    }

    /* return the (unchanged) node pointer */
    return node;
  }
  //function to search a note with word index
  public Boolean search(AVLNode r, int i){
        Boolean b = false;
        while((r!=null) && !b){
            int rval = r.data.wordIndex;
            if(i < rval){
                r = r.left;
            }
            else if(i > rval){
                r = r.right;
            }
            else{
                b=true;
                break;
            }
            b = search(r, i);
        }
        return b;
    }

  // A utility function to print preorder traversal
  // of the tree.
  // The function also prints height of every node
  void preOrder(AVLNode node) {
    if (node != null) {
      System.out.print(node.data + " ");
      preOrder(node.left);
      preOrder(node.right);
    }
  }

  void printTree(AVLNode root){
    if(root != null){
      System.out.println(root.data.wordIndex);
      if(root.left != null) printTree(root.left);
      if(root.right != null) printTree(root.right);
    } else System.out.println("empty tree");
  }

  int sizeOfTree(AVLNode root){
    int c = 0;
    if(root != null){
      c++;
      c += sizeOfTree(root.left) + sizeOfTree(root.right);
    }
    return c;
  }

}

class AVLNode {
  Position data;
  int height;
  AVLNode left, right;

  AVLNode(Position d) {
    data = d;
    height = 1;
  }
}
