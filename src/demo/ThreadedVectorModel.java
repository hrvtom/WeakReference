package demo;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

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
public class ThreadedVectorModel extends AbstractWeakVectorModel {

    private ReferenceQueue<Listener<String>> queue;
    private Thread cleanUpThread;

    public ThreadedVectorModel() {
	super();
	queue = new ReferenceQueue<>();

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
		WeakReference<?> wr;
		while (!Thread.interrupted()) {
		    try {
			System.out.println("CleanUp, waiting released listener");
			wr = (WeakReference<?>) queue.remove();//
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
	cleanUp = null;
    }

    public static void main(String[] args) {

	execute(new ThreadedVectorModel());

    }

    /*
     * (non-Javadoc)
     * 
     * @see demo.WeakVectorModel1#addListener(demo.VectorModel.Listener)
     */
    @Override
    public void addListener(Listener<String> listener) {
	WeakReference<Listener<String>> wr = new WeakReference<>(listener, queue);
	listeners.addElement(wr);
    }

    public void terminate() {
	if (cleanUpThread != null) {
	    // Thread tr = cleanUpThread;
	    cleanUpThread.interrupt();
	    cleanUpThread = null;
	    // tr.interrupt();
	}
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

}
