class WordEntry{
  String arg;
  MyLinkedList<Position> wordEntry = new MyLinkedList<>();
  AVL tree = new AVL();
  float tf;

  WordEntry(String word){
    this.arg = word;
  }

  void addPosition(Position position){
    wordEntry.Insert(position);
    tree.root = tree.insert(tree.root, position);
  }

  void addPositions(MyLinkedList<Position> positions){
    Node<Position> temp = positions.head;
    while(temp != null){
      wordEntry.Insert(temp.data);
      temp = temp.next;
    }

    temp = positions.head;
    while(temp != null){
      tree.root = tree.insert(tree.root, temp.data);
      temp = temp.next;
    }
  }

  MyLinkedList<Position> getAllPositionsForThisWord(){
    return wordEntry;
  }

  AVL getAVLPositions(){
    return tree;
  }

  float getTermFrequency(String word){
    return tf;
  }
}
