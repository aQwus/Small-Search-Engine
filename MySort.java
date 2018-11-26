import java.util.*;

class MySort{
  // public ArrayList<SearchResult> sortThisList(ArrayList<SearchResult> l){
	// 	 Collections.sort(l);
	// 	 return l;
	// }

  public ArrayList<SearchResult> sortThisList(MySet<SearchResult> m){
    ArrayList<SearchResult> sr = new ArrayList<SearchResult>(m.setSize());

    Node<SearchResult> temp = m.l.head;

    while(temp != null){
      Node<SearchResult> maxNode = temp;
      Node<SearchResult> temp2 = temp;
      while(temp2 != null){
        if(maxNode.data.compareTo(temp2.data) < 0){
          SearchResult t = temp2.data;
          temp2.data = maxNode.data;
          maxNode.data = t;
        }
        temp2 = temp2.next;
      }
      temp = temp.next;
    }

    temp = m.l.head;
    while(temp != null){
      sr.add(temp.data);
      temp = temp.next;
    }
    return sr;
	}

}
