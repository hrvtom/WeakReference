package demo;

import java.lang.ref.WeakReference;
import java.util.Vector;

import demo.VectorModel.Listener;

public abstract class AbstractWeakVectorModel extends AbstractVectorModel<WeakReference<Listener>> {

    /*
     * (non-Javadoc)
     * 
     * @see demo.VectorModel#getListeners()
     */
    @Override
    public Vector<Listener> getListeners() {
	Vector<Listener> vect = new Vector<>();
	for (WeakReference<Listener> wr : listeners) {
	    vect.addElement(wr.get());
	}
	return vect;
    }

    /*
     * (non-Javadoc)
     * 
     * @see demo.VectorModel#addListener(demo.VectorModel.Listener)
     */
    @Override
    public void addListener(Listener listener) {
	WeakReference<Listener> wr = new WeakReference<>(listener);
	listeners.addElement(wr);
    }

    /*
     * (non-Javadoc)
     * 
     * @see demo.VectorModel#removeListener(demo.VectorModel.Listener)
     */
    @Override
    public void removeListener(Listener listener) {
	WeakReference<Listener> wr = new WeakReference<>(listener);
	System.out.println("Listener Removed " + listeners.removeElement(wr));
    }

    /*
     * (non-Javadoc)
     * 
     * @see demo.AbstractVectorModel#getListener(int)
     */
    @Override
    protected Listener getListener(int i) {
	return listeners.elementAt(i).get();
    }
}
