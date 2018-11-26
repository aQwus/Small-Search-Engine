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

  public AVL getPositionInThisPageOf(String str){
    MyLinkedList<WordEntry> words = pageIndex.getWordEntries();

    WordEntry word = new WordEntry(str);
    WordEntry req = words.getWordEntry(word);

    AVL posTree = new AVL();

    MyLinkedList<Position> pos = req.getAllPositionsForThisWord();
    Node<Position> temp = pos.head;

    while(temp != null){
      if(this.equal(temp.data.getPageEntry())) {
        posTree.root = posTree.insert(posTree.root, temp.data);
      }

      temp = temp.next;
    }
    return posTree;
  }

  public Boolean containsPhrase(AVLNode root, String str[]){
    int k = 1, c = 0;
    if(str.length > 0){
      if(root != null){
        while(k < str.length){
          AVL temp = getPositionInThisPageOf(str[k]);
          if(temp.search(temp.root, root.data.getWordIndex() + k)) {
            k++;
            c = 1;
          } else {
            c = 2;
            break;
          }
        }
      if(c == 1) return true;
      else {
        if(containsPhrase(root.left, str) || containsPhrase(root.right, str)) return true;
      }
    }
  }
  return false;
  }

  public int freqOfPhrase(AVLNode root, String str[]){
    int f = 0;
    if(str.length > 0){
      if(root != null){
        if(containsPhrase(root, str)) f++;
        if(containsPhrase(root.left, str)) f++;
        if(containsPhrase(root.right, str)) f++;
      }
    }
    return f;
  }

  float getTermFrequencyFor(String word){
    int n, freqOfWord;
    AVL wordTree = getPositionInThisPageOf(word);
    freqOfWord = wordTree.sizeOfTree(wordTree.root);
    n = noOfWords();

    float tf = ((float)freqOfWord)/n;
    return tf;
  }

  float getTermFrequencyFor(String str[]){
    AVL t = getPositionInThisPageOf(str[0]);
    int m = freqOfPhrase(t.root, str);
    int n = noOfWords();
    int len = str.length;

    float tf = 0f;
    tf = ((float)m)/(n - (len-1)*m);
    return tf;
  }

  public Boolean containsWord(String word){
    MyLinkedList<WordEntry> words = pageIndex.getWordEntries();
    Node<WordEntry> temp = words.head;

    while(temp != null){
      if(temp.data.arg.equals(word)) return true;
      temp = temp.next;
    }
    return false;
  }

  public int noOfWords(){
    int noOfWords = 0;
    MyLinkedList<WordEntry> w = this.pageIndex.getWordEntries();
    Node<WordEntry> temp = w.head;

    while(temp != null){
      AVL t = getPositionInThisPageOf(temp.data.arg);
      noOfWords += t.sizeOfTree(t.root);
      temp = temp.next;
    }
    return noOfWords;
  }

}
