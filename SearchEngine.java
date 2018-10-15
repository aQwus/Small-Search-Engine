import java.util.*;
import java.io.*;

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
      File f = new File("./webpages/"+filename);
      if(f.exists() && !f.isDirectory()){
        br = new BufferedReader(new FileReader("./webpages/"+filename));
        int c = 1;

        while((input = br.readLine()) != null){
          input = input.toLowerCase();
          String[] pageWords = input.split("\\s++|\\{|}|\\[|\\]|<|>|=|\\(|\\)|\\.|,|;|'|\"|\\?|#|!|-|:");

          for(int i=0;i<pageWords.length;i++){
            if(!(pageWords[i].equals(""))){
              pageWords[i] = groom(pageWords[i]);
              if(IsValid(pageWords[i])){
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
      MySet<PageEntry> pages = ipi.getPagesWhichContainWord(x);
      pages.printPageNames(x);
    }

    else if(command.equals("queryFindPositionsOfWordInAPage")){
      String x = s.next();
      String word = groom(x);
      String y = s.next();
      PageEntry page = parsePage(y);
      String str = "";

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
    }
  }
}
