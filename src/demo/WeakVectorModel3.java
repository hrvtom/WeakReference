package demo;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.Scanner;

/**
 * Instead of having a cleanUp thread, you could just implement a cleanUp
 * method. This method would poll the reference queue. Polling the queue is not
 * a blocking operation. If a reference is enqueued, it is returned; but if
 * there are no enqueued references, poll returns immediately with a null
 * reference.
 * 
 * @author etkhrto
 *
 */
public class WeakVectorModel3 extends AbstractVectorModel {

    private ReferenceQueue queue;

    public WeakVectorModel3() {
	super();
	queue = new ReferenceQueue();
    }

    public static void main(String[] args) {

	Scanner in = new Scanner(System.in);

	VectorModel model = new WeakVectorModel3();
	String str1 = "Hello World Weak3!";
	String str2 = "This is just the beggiing";
	model.addElement(str1);
	model.addElement(str2);

	new VectorListFrame(model, "Frame 1").setVisible(true);
	new VectorListFrame(model, "Frame 2").setVisible(true);
	new VectorListFrame(model, "Frame 3").setVisible(true);

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
	model.addElement("Adding some more and more");

	System.out.println("Close some windows, press ENTER to continue");
	in.nextLine();
	System.gc();

	System.out.println("Press ENTER to remove some elements");
	in.nextLine();
	model.removeElement(str2);

	System.out.println("Press ENTER to manually clear the app, wait some time");
	in.nextLine();
	// model.getListeners().clear(); // even without this model is garbage
	// collected
	model = null;
	System.gc();

	System.out.println("Press ENTER to exit");
	in.nextLine();
    }

    private void cleanUp() {
	WeakReference wr = (WeakReference) queue.poll();
	while (wr != null) {
	    System.out.println("Gc did it's job");
	    listeners.removeElement(wr);
	    wr = (WeakReference) queue.poll();
	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#finalize()
     */
    @Override
    protected void finalize() throws Throwable {
	System.out.println("Finalizing WeakVectorModel3");
	super.finalize();
    }

    /*
     * (non-Javadoc)
     * 
     * @see demo.AbstractVectorModel#addListener(demo.VectorModel.Listener)
     */
    @Override
    public void addListener(Listener listener) {
	WeakReference wr = new WeakReference(listener, queue);
	listeners.addElement(wr);
    }

    /*
     * (non-Javadoc)
     * 
     * @see demo.AbstractVectorModel#fireElementRemoved(java.lang.Object)
     */
    @Override
    protected void fireElementRemoved(Object object) {
	cleanUp();
	super.fireElementRemoved(object);
    }

    /*
     * (non-Javadoc)
     * 
     * @see demo.AbstractVectorModel#fireElementAdded(java.lang.Object)
     */
    @Override
    protected void fireElementAdded(Object object) {
	cleanUp();
	super.fireElementAdded(object);
    }

    @Override
    protected Listener getListener(int i) {
	return (Listener) ((WeakReference) listeners.elementAt(i)).get();
    }

}
