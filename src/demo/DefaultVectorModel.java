package demo;

import java.util.Scanner;

public class DefaultVectorModel extends AbstractVectorModel {

    public static void main(String[] args) {

	Scanner in = new Scanner(System.in);

	VectorModel model = new DefaultVectorModel();
	String str1 = "Hello World!";
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

	System.out.println("Press ENTER to manually clear the app, wait some time");
	in.nextLine();
	// model.getListeners().clear(); // even without this model is garbage
	// collected
	model = null;
	System.gc();

	System.out.println("Press ENTER to exit");
	in.nextLine();

    }

    public DefaultVectorModel() {
	super();
    }

    protected void finalize() throws Throwable {
	System.out.println("Finalizing DefaultVectorModel");
	super.finalize();
    }

    @Override
    protected Listener getListener(int i) {
	return (Listener) listeners.elementAt(i);
    }

}
