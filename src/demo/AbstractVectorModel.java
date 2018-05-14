package demo;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;

public abstract class AbstractVectorModel<E> implements VectorModel {

    protected Vector<E> listeners;
    private List<Object> elements;

    public AbstractVectorModel() {
	super();
	listeners = new Vector<>();
	elements = new ArrayList<>();
    }

    public static void execute(AbstractVectorModel<?> model) {

	System.out.println("Main hello");
	Scanner in = new Scanner(System.in);
	String str1 = "Hello World Threaded!";
	String str2 = "This is just the beggiing";
	model.addElement(str1);
	model.addElement(str2);

	new VectorListFrame(model, "Frame 1").setVisible(true);
	new VectorListFrame(model, "Frame 2").setVisible(true);
	new VectorListFrame(model, "Frame 3").setVisible(true);

	System.out.println("Press ENTER to add more text");
	in.nextLine();

	String str3 = "Adding some more";
	model.addElement(str3);

	System.out.println("Close some windows, than press ENTER to remove some text");
	in.nextLine();
	model.removeElement(str1);

	System.out.println("Press ENTER to perform GC, wait for a moment, maybe GC will do the cluanup");
	in.nextLine();
	System.gc();

	System.out.println("Press ENTER to add some more text");
	in.nextLine();
	model.addElement("Adding some more and more");

	System.out.println("Close some windows, press ENTER to ad some more text");
	in.nextLine();
	model.addElement("This is more than enough");

	System.out.println("Press ENTER to perform GC, wait for a moment, maybe GC will do the cluanup");
	in.nextLine();
	System.gc();

	System.out.println("Press ENTER to remove some text");
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
	in.close();

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

    protected void fireElementRemoved(Object object) {
	VectorModel.Event e = null;
	int size = listeners.size();
	System.out.println("Listeners before:" + size);
	for (int i = 0; i < size; i++) {
	    if (e == null)
		e = new VectorModel.Event(this, object);
	    getListener(i).elementRemoved(e);
	}
	System.out.println("Listeners after:" + listeners.size());
    }

    protected void fireElementAdded(Object object) {
	VectorModel.Event e = null;
	int size = listeners.size();
	System.out.println("Listeners before:" + size);
	for (int i = 0; i < size; i++) {
	    if (e == null)
		e = new VectorModel.Event(this, object);
	    getListener(i).elementAdded(e);
	}
	System.out.println("Listeners after:" + listeners.size());
    }

    protected abstract Listener getListener(int i);

    protected void terminate() {
	// do nothing
    };
}