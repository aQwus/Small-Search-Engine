class PageIndex{
  MyLinkedList<WordEntry> words;

  PageIndex(){
    words = new MyLinkedList<>();
  }

  void addPositionForWord(String str, Position p){
    int c = 0; // 0 -> position not added; 1 -> position added
    Node<WordEntry> temp;

    try{
      temp = words.head;
      if(temp == null){
        WordEntry newWordEntry = new WordEntry(str);
        newWordEntry.addPosition(p);
        words.Insert(newWordEntry);
        c = 1;
      }
      while(temp != null){
        if(temp.data.arg.equals(str)){
          temp.data.addPosition(p);
          c = 1;
          break;
        }
        temp = temp.next;
      }
      if(c == 0){
        WordEntry newWordEntry = new WordEntry(str);
        newWordEntry.addPosition(p);
        words.Insert(newWordEntry);
        c = 1;
      }
    } catch(NullPointerException e){
      WordEntry newWordEntry = new WordEntry(str);
      newWordEntry.addPosition(p);
      words.Insert(newWordEntry);
    }
  }

  MyLinkedList<WordEntry> getWordEntries(){
    return words;
  }
}
