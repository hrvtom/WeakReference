package demo;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.Scanner;

/**
 * Another solution is to wait for the garbage collector to finalize and free a
 * listener, and clear the references to it. ThreadedWRVectorModel is
 * implemented this way. You need a ReferenceQueue for the garbage collector to
 * add the reference objects when they have been cleared. When you add a
 * listener, you must register the WeakReference object with the queue When the
 * garbage collector has finalized and freed the listener, it puts that
 * listener's reference object in the queue. Therefore, you only have to wait on
 * the queue for a reference object to be enqueued. The queue.remove() call is
 * blocking, so the thread effectively waits until the garbage collector has
 * freed a listener.
 * 
 * @author etkhrto
 *
 */
public class ThreadedVectorModel extends AbstractVectorModel {

    private ReferenceQueue queue;
    private Thread cleanUpThread;

    public ThreadedVectorModel() {
	super();
	queue = new ReferenceQueue();

	Runnable cleanUp = new Runnable() {

	    /*
	     * (non-Javadoc)
	     * 
	     * @see java.lang.Object#finalize()
	     */
	    @Override
	    protected void finalize() throws Throwable {
		System.out.println("Finalizing cleanUp");
		super.finalize();
	    }

	    @Override
	    public void run() {
		WeakReference wr;
		while (!Thread.interrupted()) {
		    try {
			System.out.println("CleanUp, waiting released listener");
			wr = (WeakReference) queue.remove();//
			System.out.println("CleanUp, removing released listener");
			listeners.removeElement(wr);
			wr = null; // wr holds reference received from
				   // queue.remove() until next wr is
				   // read from queue, and this can take
				   // some time so clean it now
		    } catch (InterruptedException e) {
			System.out.println("CleanUp, interuppted, ending");
			break;
		    }
		}
		System.out.println("CleanUp buy buy");
	    }
	};

	cleanUpThread = new Thread(cleanUp);
	cleanUpThread.start();
    }

    public static void main(String[] args) throws InterruptedException {

	System.out.println("Main hello");
	Scanner in = new Scanner(System.in);
	ThreadedVectorModel model = new ThreadedVectorModel();
	String str1 = "Hello World Threaded!";
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
	model.terminate();
	model = null;
	System.gc();

	System.out.println("Wait, and than pres ENTER to exit");
	in.nextLine();

    }

    public void terminate() {
	if (cleanUpThread != null) {
	    Thread tr = cleanUpThread;
	    // cleanUpThread.interrupt();
	    cleanUpThread = null;
	    tr.interrupt();
	}
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
     * @see java.lang.Object#finalize()
     */
    @Override
    protected void finalize() throws Throwable {
	System.out.println("Finalizing ThreadedVectorModel");
	super.finalize();
    }

    @Override
    protected Listener getListener(int i) {
	return (Listener) ((WeakReference) listeners.elementAt(i)).get();
    }

}
