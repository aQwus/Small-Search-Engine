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
}
