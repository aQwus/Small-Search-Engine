class MyLinkedList<X>{
  Node<X> head;

  MyLinkedList(){
    head = null;
  }

  public Boolean IsPresent(X ele){
    Node<X> temp = this.head;
    if(temp == null) {
      return false;
    }
    else{
      int c = 0;
      while(temp != null){
        if(ele.equals(temp.data)){
          c = 1;
          break;
        }
        temp = temp.next;
      }
      if(c == 1) return true;
      else return false;
    }
  }

  public void Insert(X ele){
      if(this.head == null){
        this.head = new Node<X>(ele);
      } else if(!(IsPresent(ele))) {
        Node<X> temp = this.head;
        while(temp.next != null){
          temp = temp.next;
        }
        temp.next = new Node<X>(ele);
      }
  }

  public void Delete(X ele){
    Node<X> temp = this.head;
    if(temp.data.equals(ele)){
      head = temp.next;
      return;
    }
    while(temp.next != null && !(temp.next.data.equals(ele))){
      temp = temp.next;
    }
    if(temp.next != null){
      if(temp.next.data.equals(ele)){
        temp.next = temp.next.next;
      }
    }
  }

  public X Get(X ele){
    Node<X> temp = this.head;
    while(temp != null){
      if(temp.data.equals(ele)) return temp.data;
      temp = temp.next;
    }
    return null;
  }

  public WordEntry getWordEntry(WordEntry ele){
    Node<X> temp = this.head;
    while(temp != null){
      if(((WordEntry)temp.data).arg.equals(ele.arg)) return ((WordEntry)temp.data);
      temp = temp.next;
    }
    return null;
  }

  public Boolean containsPosition(int i){
    Node<X> temp = this.head;
    while(temp != null){
      if(((Position)temp.data).getWordIndex() == i) return true;
      temp = temp.next;
    }
    return false;
  }
}

class Node<X>{
  X data;
  Node<X> next;

  public Node(X d, Node<X> n){
    this.data = d;
    this.next = n;
  }

  public Node(X d){
    this.data = d;
    this.next = null;
  }
}
