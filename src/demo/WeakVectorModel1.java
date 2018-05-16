package demo;

import java.lang.ref.WeakReference;

public class WeakVectorModel1 extends AbstractWeakVectorModel {

    public static void main(String[] args) {

	execute(new WeakVectorModel1());

    }

    /*
     * (non-Javadoc)
     * 
     * @see demo.AbstractVectorModel#fireElementRemoved(java.lang.Object)
     */
    @Override
    protected void fireElementRemoved(String object) {
	VectorModel.Event<String> e = null;
	int size = listeners.size();
	System.out.println("Listeners before:" + size);
	for (int i = 0; i < size;) {
	    WeakReference<Listener<String>> wr = listeners.get(i);
	    Listener<String> l = wr.get();
	    if (l == null) {
		System.out.println("GC did it's job, removing listener");
		listeners.remove(wr);
		size--;
	    } else {
		if (e == null)
		    e = new VectorModel.Event<String>(this, object);
		l.elementRemoved(e);
		i++;
	    }
	}
	System.out.println("Listeners after:" + listeners.size());
    }

    /*
     * (non-Javadoc)
     * 
     * @see demo.AbstractVectorModel#fireElementAdded(java.lang.Object)
     */
    @Override
    protected void fireElementAdded(String object) {
	VectorModel.Event<String> e = null;
	int size = listeners.size();
	System.out.println("Listeners before:" + size);
	for (int i = 0; i < size;) {
	    WeakReference<Listener<String>> wr = listeners.get(i);
	    Listener<String> l = wr.get();
	    if (l == null) {
		System.out.println("GC did it's job, removing listener");
		listeners.remove(wr);
		size--;
	    } else {
		if (e == null)
		    e = new VectorModel.Event<String>(this, object);
		l.elementAdded(e);
		i++;
	    }
	}
	System.out.println("Listeners after:" + listeners.size());

    }

    protected void finalize() throws Throwable {
	System.out.println("Finalizing WeakVectorModel1");
	super.finalize();
    }

}
