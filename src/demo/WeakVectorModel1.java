package demo;

/**
 * By using WeakReference objects to hold the references to your data model's listeners,
 * you avoid the problem of lapsed listeners. DefaultVectorModel holds direct,
 * strong references to the listeners, which prevents them from being garbage-collected.
 * The new WeakRefVectorModel implementation holds only indirect, weak references
 * to the listeners. When the garbage collector determines that a listener is only
 * weakly reachable, it finalizes the listener, frees its memory, and clears
 * the weak reference to it.
 * Whenever an event is fired from WeakRefVectorModel, you have to test the referents,
 * the actual VectorModel.Listener objects, for null before you call their elementAdded
 * or elementRemoved methods.
 */
import java.lang.ref.WeakReference;
import java.util.Scanner;

public class WeakVectorModel1 extends AbstractVectorModel {

    public static void main(String[] args) {

	Scanner in = new Scanner(System.in);

	VectorModel model = new WeakVectorModel1();
	String str1 = "Hello World Weak1!";
	String str2 = "This is just the beggiing";
	model.addElement(str1);
	model.addElement(str2);

	VectorListFrame vlf1 = new VectorListFrame(model, "Frame 1");
	VectorListFrame vlf2 = new VectorListFrame(model, "Frame 2");
	VectorListFrame vlf3 = new VectorListFrame(model, "Frame 3");
	vlf1.setVisible(true);
	vlf2.setVisible(true);
	vlf3.setVisible(true);

	System.out.println("Press ENTER to continue");
	in.nextLine();

	String str3 = "Adding some more";
	model.addElement(str3);

	System.out.println("Close some windows, press ENTER to continue");
	in.nextLine();
	model.removeElement(str1);

	System.out.println("Press ENTER to perform GC, wait for some time");
	in.nextLine();
	System.gc();

	System.out.println("Press ENTER to add some more");
	in.nextLine();
	String str4 = "Adding some more and more";
	model.addElement(str4);

	System.out.println("Press ENTER to manually clear the app, wait some time");
	in.nextLine();
	// model.getListeners().clear(); // even without this model i garbage
	// collected
	model = null;
	System.gc();

	System.out.println("Press ENTER to exit");
	in.nextLine();
    }

    /*
     * (non-Javadoc)
     * 
     * @see demo.AbstractVectorModel#fireElementRemoved(java.lang.Object)
     */
    @Override
    protected void fireElementRemoved(Object object) {
	VectorModel.Event e = null;
	int size = listeners.size();
	System.out.println("Listeners:" + size);
	for (int i = 0; i < size;) {
	    WeakReference wr = (WeakReference) getListeners().elementAt(i);
	    Listener l = (Listener) wr.get();
	    if (l == null) {
		System.out.println("GC did it's job");
		getListeners().removeElement(wr);
		size--;
	    } else {
		if (e == null)
		    e = new VectorModel.Event(this, object);
		l.elementRemoved(e);
		i++;
	    }
	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see demo.AbstractVectorModel#fireElementAdded(java.lang.Object)
     */
    @Override
    protected void fireElementAdded(Object object) {
	VectorModel.Event e = null;
	int size = listeners.size();
	System.out.println("Listeners:" + size);
	for (int i = 0; i < size;) {
	    WeakReference wr = (WeakReference) getListeners().elementAt(i);
	    Listener l = (Listener) wr.get();
	    if (l == null) {
		System.out.println("GC did it's job");
		getListeners().removeElement(wr);
		size--;
	    } else {
		if (e == null)
		    e = new VectorModel.Event(this, object);
		l.elementAdded(e);
		i++;
	    }
	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see demo.AbstractVectorModel#addListener(demo.VectorModel.Listener)
     */
    @Override
    public void addListener(Listener listener) {
	WeakReference wr = new WeakReference(listener);
	listeners.addElement(wr);
    }

    protected void finalize() throws Throwable {
	System.out.println("Finalizing WeakVectorModel1");
	super.finalize();
    }

}
