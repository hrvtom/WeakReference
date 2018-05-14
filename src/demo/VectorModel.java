package demo;

import java.util.EventListener;
import java.util.EventObject;
import java.util.Vector;

/**
 * Define a simple "application" data model. You can add, remove, and access
 * objects. When you add or remove an object, all registered
 * VectorModel.Listeners will be notified with a VectorModel.Event.
 */

public interface VectorModel {
    public static class Event extends EventObject {
	private static final long serialVersionUID = -1;
	private Object element;

	public Event(VectorModel model, Object element) {
	    super(model);
	    this.element = element;
	}

	public Object getElement() {
	    return element;
	}
    }

    public interface Listener extends EventListener {
	public void elementAdded(VectorModel.Event e);

	public void elementRemoved(VectorModel.Event e);
    }

    public Vector<Listener> getListeners();

    public void addElement(Object object);

    public void removeElement(Object object);

    public Object elementAt(int index);

    public int size();

    public void addListener(VectorModel.Listener listener);

    public void removeListener(VectorModel.Listener listener);

}
