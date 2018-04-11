package demo;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;

public class DefaultVectorModel implements VectorModel {

    private Vector listeners;
    private List elements;

    public static void main(String[] args) {

	Scanner in = new Scanner(System.in);

	VectorModel model = new DefaultVectorModel();
	String str1 = "Hello World!";
	String str2 = "This is just the beggiing";
	model.addElement(str1);
	model.addElement(str2);

	VectorListFrame vlf1 = new VectorListFrame(model, "Frame 1");
	VectorListFrame vlf2 = new VectorListFrame(model, "Frame 2");
	VectorListFrame vlf3 = new VectorListFrame(model, "Frame 3");
	vlf1.setVisible(true);
	vlf2.setVisible(true);
	vlf3.setVisible(true);

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

	System.out.println("Press ENTER to manually clear the app, wait some time");
	in.nextLine();
	// model.getListeners().clear(); // even without this model i garbage
	// collected
	model = null;
	System.gc();

	System.out.println("Press ENTER to exit");
	in.nextLine();

    }

    public DefaultVectorModel() {
	listeners = new Vector<VectorModel.Listener>();
	elements = new ArrayList();
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
	    ((VectorModel.Listener) listeners.elementAt(i)).elementRemoved(e);
	}

    }

    protected void fireElementAdded(Object object) {
	VectorModel.Event e = null;
	int size = listeners.size();
	System.out.println("Listeners:" + size);
	for (int i = 0; i < size; i++) {
	    if (e == null)
		e = new VectorModel.Event(this, object);
	    ((VectorModel.Listener) listeners.elementAt(i)).elementAdded(e);
	}
    }

    protected void finalize() throws Throwable {
	System.out.println("Finalizing DefaultVectorModel");
	super.finalize();
    }

}
