class SearchResult implements Comparable<SearchResult>{
  PageEntry p;
  float r;

  SearchResult(PageEntry p, float r){
    this.p = p;
    this.r = r;
  }

  public PageEntry getPageEntry(){
    return p;
  }

  public float getRelevance(){
    return r;
  }

  public int compareTo(SearchResult otherObject){
    if(r == otherObject.getRelevance()) return 0;

    if(r > otherObject.getRelevance()) return 1;
    else return (-1);
  }
}
