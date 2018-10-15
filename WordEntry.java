class WordEntry{
  String arg;
  MyLinkedList<Position> wordEntry = new MyLinkedList<>();
  float tf;

  WordEntry(String word){
    this.arg = word;
  }

  void addPosition(Position position){
    wordEntry.Insert(position);
  }

  void addPositions(MyLinkedList<Position> positions){
    Node<Position> temp = positions.head;
    while(temp != null){
      wordEntry.Insert(temp.data);
      temp = temp.next;
    }
  }

  MyLinkedList<Position> getAllPositionsForThisWord(){
    return wordEntry;
  }

  float getTermFrequency(String word){
    return tf;
  }
}
