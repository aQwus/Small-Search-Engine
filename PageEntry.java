class PageEntry{
  String pageName;
  PageIndex pageIndex = new PageIndex();

  PageEntry(String pageName){
    this.pageName = pageName;
    this.pageIndex = new PageIndex();
  }

  public PageIndex getPageIndex(){
    return pageIndex;
  }

  public String getPageName(){
    return this.pageName;
  }

  public Boolean equal(Object obj){
    return pageName.equals(((PageEntry)obj).getPageName());
  }
}
