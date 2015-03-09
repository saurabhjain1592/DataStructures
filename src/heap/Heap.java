package heap;

import java.lang.reflect.Array;


public class Heap<T extends Comparable<T>> {
	private T a[];
	private int last=0;
	private Class<? extends T[]> typeParameterClass;

	@SuppressWarnings("unchecked")
	public Heap(int s, Class<? extends T[]> typeParameterClass){
    	this.typeParameterClass = typeParameterClass;
    	a = (T[]) Array.newInstance(typeParameterClass.getComponentType() , s+1);
    	last = 0;
	}
    
	@SuppressWarnings("unchecked")
	public Heap(T a[]){
    	typeParameterClass = (Class<T[]>)a.getClass();
    	this.a = (T[])Array.newInstance(typeParameterClass.getComponentType(), a.length+1);
    	for(int i=0; i<a.length; i++){
        	this.a[i+1]=a[i];
    	}
    	this.last = this.a.length-1;
    	for(int i=(this.a.length)/2; i>0; i--){
        	heapifyUp(i);
        	heapifyDown(i);
    	}
	}

	public boolean insert(T t){
    	if(last+1<a.length){
        	last++;
        	a[last]=t;
        	heapifyUp(last);
        	return true;
    	}
    	return false;
	}
    
	public T getMin(){
    	if(last>0){
        	return this.a[1];
    	}else{
        	return null;
    	}
	}
    
	public T extractMin(){
    	if(last>0){
        	T ret = a[1];
        	T temp = a[1];
        	a[1] = a[last];
        	a[last] = temp;
        	last--;
        	heapifyDown(1);
        	return ret;
    	}else{
        	return null;
    	}
	}
    
	public boolean delete(T t){
    	int i = search(t,1);
    	if(i!=-1){
        	T temp = a[i];
        	a[i] = a[last];
        	a[last] = temp;
        	last--;
        	heapifyUp(i);
        	heapifyDown(i);
        	return true;
    	}else{
        	return false;
    	}
	}
    
	private int search(T t, int i){
    	if(i>last){
        	return -1;
    	}
    	if(a[i].equals(t)){
        	return i;
    	}else{
        	int x=-1,y=-1;
        	if(2*i<=last && t.compareTo(a[2*i])>=0){
            	x = search(t,2*i);
        	}
        	if(2*i+1<=last && t.compareTo(a[2*i+1])>=0){
            	y = search(t,2*i+1);
        	}
        	return x!=-1?x:((y!=-1)?y:-1);
    	}
	}
    
	private void heapifyDown(int i){
    	while(i<last){
        	if(2*i<=last){
            	if(2*i+1<=last){
                	T min = a[2*i+1].compareTo(a[2*i])<0?a[2*i+1]:a[2*i];
                	if(min.compareTo(a[i])<0){
                    	int tI = (min.equals(a[2*i+1]))?(2*i+1):(2*i);
                    	T temp = a[tI];
                    	a[tI] = a[i];
                    	a[i] = temp;
                    	i = tI;
                	}else{
                    	break;
                	}
            	}else{
                	if(a[2*i].compareTo(a[i])<0){
                    	T temp = a[2*i];
                    	a[2*i] = a[i];
                    	a[i] = temp;
                    	i = 2*i;
                	}else{
                    	break;
                	}
            	}
        	}else{
            	break;
        	}
    	}
	}
    
	private void heapifyUp(int i){
    	while(i!=1 && a[i].compareTo(a[i/2])<0){
        	T temp = a[i/2];
        	a[i/2] = a[i];
        	a[i] = temp;
        	i = i/2;
    	}
	}
    
	public static void main(String[] args){
    	Integer a[] = {5,2,1,3,6,8,9,4,7};
    	Heap<Integer> h = new Heap<Integer>(a.length, a.getClass());
    	for(int i=0; i<a.length; i++){
        	h.insert(a[i]);
    	}
    	System.out.println(h.getMin());
    	System.out.println(h.extractMin());
    	System.out.println(h.insert(5));
    	System.out.println(h.getMin());
    	System.out.println(h.delete(9));
    	System.out.println(h.delete(12));
    	System.out.println(h.delete(h.getMin()));
    	System.out.println(h.insert(0));
    	Integer c = h.extractMin();
    	while(c!=null){
        	System.out.println(c);
        	c = h.extractMin();
    	}
    	System.out.println();
    	System.out.println();
   	 
    	Integer a1[] = {5,2,1,3,6,8,9,4,7};
    	Heap<Integer> h1 = new Heap<Integer>(a1);
    	System.out.println(h1.getMin());
    	System.out.println(h1.extractMin());
    	System.out.println(h1.insert(5));
    	System.out.println(h1.getMin());
    	System.out.println(h1.delete(9));
    	System.out.println(h1.delete(12));
    	System.out.println(h1.delete(h1.getMin()));
    	System.out.println(h1.insert(0));
    	Integer c1 = h1.extractMin();
    	while(c1!=null){
        	System.out.println(c1);
        	c1 = h1.extractMin();
    	}
	}
}



