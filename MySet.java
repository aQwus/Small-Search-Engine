import java.util.*;

class MySet<X>{

  MyLinkedList<X> l = new MyLinkedList<X>();

  void addElement(X element){
    l.Insert(element);
  }

  public MySet<X> union(MySet<X> otherSet){
    MySet<X> union = new MySet<>();
    Node<X> temp1 = l.head, temp2 = otherSet.l.head;

    while(temp1 != null){
      union.addElement(temp1.data);
      temp1 = temp1.next;
    }

    if(union.l.IsPresent(temp2.data)){
      temp2 = temp2.next;
    } else {
      union.addElement(temp2.data);
      temp2 = temp2.next;
    }
    return union;
  }

  public MySet<X> intersection(MySet<X> otherSet){
    MySet<X> inter = new MySet<X>();
    Node<X> temp = this.l.head;

    while(temp != null){
      if(otherSet.l.IsPresent(temp.data)){
        inter.addElement(temp.data);
      }
      temp = temp.next;
    }
    return inter;
  }

  public void printPageNames(String x){
    String str = "";
    MyLinkedList<String> a = new MyLinkedList<>();
    if(l.head != null){
      a.Insert(((PageEntry)l.head.data).pageName);
      str += ((PageEntry)l.head.data).pageName;
      Node<X> temp = l.head.next;
      while(temp != null){
        if(a.IsPresent(((PageEntry)temp.data).pageName) == false){
          a.Insert(((PageEntry)temp.data).pageName);
          str += ", " + ((PageEntry)temp.data).pageName;
        }
        temp = temp.next;
      }
    }
    if(str.equals("")) System.out.println( "No webpage contains word " + x);
    else System.out.println(str);
  }

  public Boolean searchPage(PageEntry p){
    Node<X> temp = this.l.head;
    while(temp != null){
      PageEntry pe = (PageEntry) temp.data;
      if(pe.equal(p)) return true;
      temp = temp.next;
    }
    return false;
  }

  public int setSize(){
    int c = 0;
    Node<X> temp = this.l.head;
    while(temp != null){
      c++;
      temp = temp.next;
    }
    return c;
  }

  public void printPageSet(){
    String s = "";
    Node<X> temp = this.l.head;
    if(temp != null) {
      s += ((PageEntry)temp.data).getPageName();
      temp = temp.next;

      while(temp != null){
        s += ", " + ((PageEntry)temp.data).getPageName();
        temp = temp.next;
      }
      System.out.println(s);
    } else System.out.println("No webpage found which contains given arguments");
  }

}
