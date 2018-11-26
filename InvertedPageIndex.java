class InvertedPageIndex{
  MyHashTable mht;

  InvertedPageIndex(){
    mht = new MyHashTable();
  }

  public void addPage(PageEntry p){
    MyLinkedList<WordEntry> w;
    try{
      w  = p.getPageIndex().getWordEntries();
      if(w != null){
        Node<WordEntry> temp = w.head;
        while(temp != null){
          mht.addPositionsForWord(temp.data);
          temp = temp.next;
        }
      }
    } catch(Exception e){
      e.printStackTrace();
    }
  }

  public MySet<PageEntry> getPagesWhichContainWord(String str){
    MySet<PageEntry> pageEntry = new MySet<>();
    WordEntry w = mht.getPositionForWord(str);

    try{
      if(w != null){
        MyLinkedList<Position> pos = w.getAllPositionsForThisWord();
        Node<Position> temp = pos.head;

        while(temp != null){
          Position posi = temp.data;
          PageEntry pe = (PageEntry) posi.getPageEntry();
          pageEntry.addElement(pe);
          temp = temp.next;
        }
      }
      return pageEntry;
    } catch(Exception e){
      e.printStackTrace();
    }
    return null;
  }

  public MySet<PageEntry> getPagesWhichContainAllWords(String str[]){
    if(str.length > 0){
      MySet<PageEntry> aPages = getPagesWhichContainWord(str[0]);
      for(int i=1; i<str.length;i++){
        aPages = aPages.intersection(getPagesWhichContainWord(str[i]));
      }
      return aPages;
    }
    return null;
  }

  public MySet<PageEntry> getPagesWhichContainAnyOfTheseWords(String str[]){
    if(str.length > 0){
      MySet<PageEntry> aPages = getPagesWhichContainWord(str[0]);
      for(int i=1; i<str.length;i++){
        aPages = aPages.union(getPagesWhichContainWord(str[i]));
      }
      return aPages;
    }
    return null;
  }

  public MySet<PageEntry> getPagesWhichContainPhrase(String str[]){
    MySet<PageEntry> aPages = getPagesWhichContainAllWords(str);
    Node<PageEntry> temp = aPages.l.head;

    while(temp != null){
      AVL posTree = (temp.data).getPositionInThisPageOf(str[0]);
      if((temp.data).containsPhrase(posTree.root, str)) temp = temp.next;
      else{
        Node<PageEntry> temp2 = temp.next;
        aPages.l.Delete(temp.data);
        temp = temp2;
      }
    }
    return aPages;
  }

}
