package demo;

import java.util.EventListener;
import java.util.EventObject;
import java.util.Vector;

/**
 * Define a simple "application" data model. You can add, remove, and access
 * objects. When you add or remove an object, all registered
 * VectorModel.Listeners will be notified with a VectorModel.Event.
 */

public interface VectorModel<T> {
    public static class Event<T> extends EventObject {
	private static final long serialVersionUID = -1;
	private T element;

	public Event(VectorModel<T> model, T element) {
	    super(model);
	    this.element = element;
	}

	public T getElement() {
	    return element;
	}
    }

    public interface Listener<T> extends EventListener {
	public void elementAdded(VectorModel.Event<T> e);

	public void elementRemoved(VectorModel.Event<T> e);
    }

    public Vector<Listener<T>> getListeners();

    public void addElement(T object);

    public void removeElement(T object);

    public T elementAt(int index);

    public int size();

    public void addListener(VectorModel.Listener<T> listener);

    public void removeListener(VectorModel.Listener<T> listener);

}
