package demo;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

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
public class WeakVectorModel3 extends AbstractWeakVectorModel {

    private ReferenceQueue<Listener> queue;

    public WeakVectorModel3() {
	super();
	queue = new ReferenceQueue<>();
    }

    public static void main(String[] args) {

	execute(new WeakVectorModel3());

    }

    private void cleanUp() {
	WeakReference<?> wr = (WeakReference<?>) queue.poll();
	while (wr != null) {
	    System.out.println("Gc did it's job, element cleaned");
	    listeners.removeElement(wr);
	    wr = (WeakReference<?>) queue.poll();
	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see demo.WeakVectorModel1#addListener(demo.VectorModel.Listener)
     */
    @Override
    public void addListener(Listener listener) {
	WeakReference<Listener> wr = new WeakReference<>(listener, queue);
	listeners.addElement(wr);
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

}
