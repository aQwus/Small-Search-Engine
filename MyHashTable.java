class MyHashTable{
  int size = 1500;
  MyLinkedList<WordEntry>[] hashArray;

  @SuppressWarnings("unchecked")
  MyHashTable(){
    hashArray = new MyLinkedList[size];
  }

  private int getHashIndex(String str){
    int code = str.hashCode();
    if(code < 0){
      code = (Integer.MAX_VALUE + code) % size;
      return code;
    } else return code % size;
  }

  public void addPositionsForWord(WordEntry w){
    int index = getHashIndex(w.arg);

    if(hashArray[index] == null){
      hashArray[index] = new MyLinkedList<WordEntry>();
      hashArray[index].Insert(w);
    } else {
      if(hashArray[index].getWordEntry(w) == null){
        hashArray[index].Insert(w);
      } else {
        hashArray[index].getWordEntry(w).addPositions(w.getAllPositionsForThisWord());
      }
    }
  }

  public WordEntry getPositionForWord(String str){
    int index = getHashIndex(str);

    if(hashArray[index] == null){
      return null;
    } else {
      WordEntry wstr = new WordEntry(str);
      WordEntry w = hashArray[index].getWordEntry(wstr);
      return w;
    }
  }
}
