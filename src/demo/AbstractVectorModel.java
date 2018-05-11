package demo;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public abstract class AbstractVectorModel implements VectorModel {

    protected Vector listeners;
    private List<Object> elements;

    public AbstractVectorModel() {
	super();
	listeners = new Vector<>();
	elements = new ArrayList<>();
    }

    public Vector getListeners() {
	return listeners;
    }

    @Override
    public void addElement(Object object) {
	elements.add(object);
	fireElementAdded(object);
    }

    @Override
    public void removeElement(Object object) {
	elements.remove(object);
	fireElementRemoved(object);

    }

    @Override
    public Object elementAt(int index) {
	return elements.get(index);
    }

    @Override
    public int size() {
	return elements.size();
    }

    @Override
    public void addListener(Listener listener) {
	listeners.addElement(listener);

    }

    @Override
    public void removeListener(Listener listener) {
	listeners.removeElement(listener);

    }

    protected void fireElementRemoved(Object object) {
	VectorModel.Event e = null;
	int size = listeners.size();
	System.out.println("Listeners:" + size);
	for (int i = 0; i < size; i++) {
	    if (e == null)
		e = new VectorModel.Event(this, object);
	    ((Listener) getListener(i)).elementRemoved(e);
	}

    }

    protected void fireElementAdded(Object object) {
	VectorModel.Event e = null;
	int size = listeners.size();
	System.out.println("Listeners:" + size);
	for (int i = 0; i < size; i++) {
	    if (e == null)
		e = new VectorModel.Event(this, object);
	    ((Listener) getListener(i)).elementAdded(e);
	}
    }

    protected abstract Listener getListener(int i);

}