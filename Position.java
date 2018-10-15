class Position<PageEntry,Integer>{
  PageEntry p;
  int wordIndex;

  Position(PageEntry p, int wordIndex){
    this.p = p;
    this.wordIndex = wordIndex;
  }

  public PageEntry getPageEntry(){
    return p;
  }

  public int getWordIndex(){
    return wordIndex;
  }
}
