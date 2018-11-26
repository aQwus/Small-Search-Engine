import java.util.*;
import java.io.*;
import java.lang.Math;

class SearchEngine{
  InvertedPageIndex ipi;
  String[] invalidWords = new String[]{ "a", "an", "the", "they", "these", "this", "for", "is", "are", "was", "of", "or", "and", "does", "will", "whose" };
  MySet<PageEntry> pageSet;

  SearchEngine(){
    ipi = new InvertedPageIndex();
    pageSet = new MySet<PageEntry>();
  }

  public Boolean IsValid(String word){
    for(int i=0; i<invalidWords.length;i++){
      if(word.equals(invalidWords[i])) return false;
    }
    return true;
  }

  public String groom(String word){
    word = word.toLowerCase();
    if(word.equals("stacks")) word = "stack";
    else if(word.equals("structures")) word = "structure";
    else if(word.equals("applications")) word = "application";
    return word;
  }

  public PageEntry parsePage(String filename){
    PageEntry page = new PageEntry(filename);
    PageIndex index = page.getPageIndex();
    String input;
    BufferedReader br = null;

    try{
      File f = new File("./newWebpages/"+filename);
      if(f.exists() && !f.isDirectory()){
        br = new BufferedReader(new FileReader("./newWebpages/"+filename));
        int c = 1;

        while((input = br.readLine()) != null){
          input = input.toLowerCase();
          String[] pageWords = input.split("\\s++|\\{|}|\\[|\\]|<|>|=|\\(|\\)|\\.|,|;|'|\"|\\?|#|!|-|:");

          for(int i=0;i<pageWords.length;i++){
            if(!(pageWords[i].equals(""))){
              if(IsValid(pageWords[i])){
                pageWords[i] = groom(pageWords[i]);
                Position pos = new Position(page, c);
                index.addPositionForWord(pageWords[i], pos);
              }
              c++;
            }
          }
        }
      } else {
        System.out.println("file named " + filename + " doesn't exist");
        return null;
      }
    } catch(Exception e){
      e.printStackTrace();
    }
    finally{
      try{
        if(br != null) br.close();
      } catch(Exception e){
        e.printStackTrace();
      }
    }
    return page;
  }

  public float getIDFreqFor(String word){
    float IDFreq = 0.0f;
    int N = pageSet.setSize();
    int c = 0;
    Node<PageEntry> temp = pageSet.l.head;
    while(temp != null){
      if(temp.data.containsWord(word)) c++;
      temp = temp.next;
    }
    IDFreq = (float) Math.log(((float)N)/c);
    return IDFreq;
  }

  public float getIDFreqFor(String str[]){
    float IDFreq = 0.0f;
    int N = pageSet.setSize();
    int c = 0;

    Node<PageEntry> temp = pageSet.l.head;
    while(temp != null){
      int check = 0; //1->page contains all word, 2->vice versa
      int k = 0;
      while(k < str.length){
        if(temp.data.containsWord(str[k])) check = 1;
        else{
          check = 2;
          break;
        }
        k++;
      }
      if(check == 1){
        AVL t = temp.data.getPositionInThisPageOf(str[0]);
        if(temp.data.containsPhrase(t.root,str)) c++;
      }
      temp = temp.next;
    }

    if(c == 0) return 0.0f;
    else IDFreq = (float) Math.log(((float)N)/c);
    return IDFreq;
  }

  public float getRelevanceOfPage(PageEntry p, String word){
    float rel = (p.getTermFrequencyFor(word))*getIDFreqFor(word);
    return rel;
  }

  public float getRelevanceOfPage(PageEntry p, String str[], boolean doTheseWordsRepresentAPhrase){
    float rel = 0f;
    if(str.length > 0){
      int k = 0;
      if(doTheseWordsRepresentAPhrase){
        rel = (p.getTermFrequencyFor(str))*getIDFreqFor(str);
      } else {
        while(k < str.length){
          if(p.containsWord(str[k])){
            rel += getRelevanceOfPage(p, str[k]);
          }
          k++;
        }
      }
    }
    return rel;
  }

  public void performAction(String actionMessage){
    Scanner s = new Scanner(actionMessage);
    String command = s.next();

    if(command.equals("addPage")){
      String x = s.next();
      PageEntry page = parsePage(x);
      ipi.addPage(page);
      pageSet.addElement(page);
    }

    else if(command.equals("queryFindPagesWhichContainWord")){
      String x = s.next();
      x = groom(x);
      MySet<PageEntry> reqPages = ipi.getPagesWhichContainWord(x);

      float rel = 0.0f;
      MySet<SearchResult> arr = new MySet<SearchResult>();
      Node<PageEntry> temp = reqPages.l.head;
      if(temp == null){
        System.out.println("No webpage contains word " + x);
      }
      while(temp != null){
        rel = getRelevanceOfPage(temp.data, x);
        SearchResult sr = new SearchResult(temp.data, rel);
        arr.addElement(sr);
        temp = temp.next;
      }
      MySort sort = new MySort();
      ArrayList<SearchResult> sortedArr = new ArrayList<SearchResult>(reqPages.setSize());
      sortedArr = sort.sortThisList(arr);

      if(sortedArr.size() > 0) {
        System.out.print(sortedArr.get(0).getPageEntry().getPageName());
        if(sortedArr.size() > 1){
          int k = 1;
          while(k < sortedArr.size()){
            System.out.print(", " + sortedArr.get(k).getPageEntry().getPageName());
            k++;
          }
        }
        System.out.println("");
      }
    }

    else if(command.equals("queryFindPositionsOfWordInAPage")){
      String x = s.next();
      String word = groom(x);
      String y = s.next();
      PageEntry page = parsePage(y);
      String str = "";

      if(page != null && page.containsWord(word)){
        try{
          if(page != null){
            if(pageSet.searchPage(page)){
              WordEntry wordstr = new WordEntry(word);
              WordEntry w = page.getPageIndex().getWordEntries().getWordEntry(wordstr);
              if(w != null){
                MyLinkedList<Position> pos = w.getAllPositionsForThisWord();
                Node<Position> n = pos.head;
                if(n != null){
                  PageEntry pe = (PageEntry) n.data.getPageEntry();
                  if(pe.equal(page)) str += (n.data).getWordIndex();
                  n = pos.head.next;
                  while(n != null){
                    pe = (PageEntry) n.data.getPageEntry();
                    if(pe.equal(page)) str += ", " + (n.data).getWordIndex();
                    n = n.next;
                  }
                }
              }
              if(str.equals("")) System.out.println("Webpage " + y + " does not contain word " + x);
              else System.out.println(str);
            } else {
              System.out.println("No webpage " + y + " found");
            }
          }
        } catch(Exception e){
          System.out.println("Webpage " + y + " does not contain word " + x);
          e.printStackTrace();
        }
      } else {
        System.out.println("Webpage " + y + " does not contain word " + x);
      }
    }

    else if(command.equals("queryFindPagesWhichContainAllWords")){
      String phrase = s.next();
      String t = s.nextLine();
      if(IsValid(t)) phrase += groom(t);
      String[] str = phrase.split("\\s++");

      MySet<PageEntry> reqPages = ipi.getPagesWhichContainAllWords(str);

      float rel = 0.0f;
      MySet<SearchResult> arr = new MySet<SearchResult>();
      Node<PageEntry> temp = reqPages.l.head;
      while(temp != null){
        rel = getRelevanceOfPage(temp.data, str, false);
        SearchResult sr = new SearchResult(temp.data, rel);
        arr.addElement(sr);
        temp = temp.next;
      }
      MySort sort = new MySort();
      ArrayList<SearchResult> sortedArr = new ArrayList<SearchResult>(reqPages.setSize());
      sortedArr = sort.sortThisList(arr);

      if(sortedArr.size() > 0) {
        System.out.print(sortedArr.get(0).getPageEntry().getPageName());
        if(sortedArr.size() > 1){
          int k = 1;
          while(k < sortedArr.size()){
            System.out.print(", " + sortedArr.get(k).getPageEntry().getPageName());
            k++;
          }
        }
        System.out.println("");
      }

    }

    else if(command.equals("queryFindPagesWhichContainAnyOfTheseWords")){
      String phrase = s.next();
      String t = s.nextLine();
      if(IsValid(t)) phrase += groom(t);
      String[] str = phrase.split("\\s++");

      MySet<PageEntry> reqPages = ipi.getPagesWhichContainAnyOfTheseWords(str);

      float rel = 0.0f;
      MySet<SearchResult> arr = new MySet<SearchResult>();
      Node<PageEntry> temp = reqPages.l.head;
      while(temp != null){
        rel = getRelevanceOfPage(temp.data, str, false);
        SearchResult sr = new SearchResult(temp.data, rel);
        arr.addElement(sr);
        temp = temp.next;
      }
      MySort sort = new MySort();
      ArrayList<SearchResult> sortedArr = new ArrayList<SearchResult>(reqPages.setSize());
      sortedArr = sort.sortThisList(arr);

      if(sortedArr.size() > 0) {
        System.out.print(sortedArr.get(0).getPageEntry().getPageName());
        if(sortedArr.size() > 1){
          int k = 1;
          while(k < sortedArr.size()){
            System.out.print(", " + sortedArr.get(k).getPageEntry().getPageName());
            k++;
          }
        }
        System.out.println("");
      }
    }

    else if(command.equals("queryFindPagesWhichContainPhrase")){
      String phrase = s.next();
      String t = s.nextLine();
      if(IsValid(t)) phrase += groom(t);
      String[] str = phrase.split("\\s++");

      MySet<PageEntry> reqPages = ipi.getPagesWhichContainPhrase(str);

      float rel = 0.0f;
      MySet<SearchResult> arr = new MySet<SearchResult>();
      Node<PageEntry> temp = reqPages.l.head;
      while(temp != null){
        rel = getRelevanceOfPage(temp.data, str, true);
        SearchResult sr = new SearchResult(temp.data, rel);
        arr.addElement(sr);
        temp = temp.next;
      }
      MySort sort = new MySort();
      ArrayList<SearchResult> sortedArr = new ArrayList<SearchResult>(reqPages.setSize());
      sortedArr = sort.sortThisList(arr);

      if(sortedArr.size() > 0) {
        System.out.print(sortedArr.get(0).getPageEntry().getPageName());
        if(sortedArr.size() > 1){
          int k = 1;
          while(k < sortedArr.size()){
            System.out.print(", " + sortedArr.get(k).getPageEntry().getPageName());
            k++;
          }
        }
        System.out.println("");
      }
    }

  }

}
