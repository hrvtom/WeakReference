package demo;

import java.lang.ref.WeakReference;
import java.util.Vector;

import demo.VectorModel.Listener;

public abstract class AbstractWeakVectorModel extends AbstractVectorModel<WeakReference<Listener<String>>, String> {

    /*
     * (non-Javadoc)
     * 
     * @see demo.VectorModel#getListeners()
     */
    @Override
    public Vector<Listener<String>> getListeners() {
	Vector<Listener<String>> vect = new Vector<>();
	for (WeakReference<Listener<String>> wr : listeners) {
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
    public void addListener(Listener<String> listener) {
	WeakReference<Listener<String>> wr = new WeakReference<>(listener);
	listeners.addElement(wr);
    }

    /*
     * (non-Javadoc)
     * 
     * @see demo.VectorModel#removeListener(demo.VectorModel.Listener)
     */
    @Override
    public void removeListener(Listener<String> listener) {
	WeakReference<Listener<String>> wr = new WeakReference<>(listener);
	System.out.println("Listener Removed " + listeners.removeElement(wr));
    }

    /*
     * (non-Javadoc)
     * 
     * @see demo.AbstractVectorModel#getListener(int)
     */
    @Override
    protected Listener<String> getListener(int i) {
	return listeners.elementAt(i).get();
    }
}
