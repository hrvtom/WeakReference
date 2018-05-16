package demo;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;

import demo.VectorModel.Event;

/**
 * Displays a VectorModel in a small frame, using a JList. Uses a private
 * anonymous inner class to implement VecotrModel.Listener. This inner class
 * adds or removes elements from the JList's data model.
 *
 * Note: As the code's out-commented lines show, in the case of VectorListFrame
 * it would be quite easy to remove the object from the VectorModel's listener
 * list. when the frame is closed. Alas, in the real world code, it's not always
 * this easy
 */
public class VectorListFrame extends JFrame {
    private static final long serialVersionUID = -1;

    // number of non-finalized VectorListFrames
    public static int nFrames = 0;

    // Commenting out discussed bellow..
    // private VectorModel vectorModel;

    protected DefaultListModel<String> listModel;
    protected VectorModel.Listener<String> modelListener = new VectorModel.Listener<String>() {

	@Override
	public void elementAdded(Event<String> e) {
	    System.out.println(VectorListFrame.this.getTitle() + " element added");
	    listModel.addElement(e.getElement());
	}

	@Override
	public void elementRemoved(Event<String> e) {
	    System.out.println(VectorListFrame.this.getTitle() + " element removed");
	    listModel.removeElement(e.getElement());
	}
    };

    public VectorListFrame(VectorModel<String> vectorModel, String name) {
	super(name);
	setSize(200, 200);
	setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	synchronized (VectorListFrame.class) {
	    nFrames++;
	}
	listModel = new DefaultListModel<>();
	int size = vectorModel.size();
	for (int i = 0; i < size; i++) {
	    listModel.addElement(vectorModel.elementAt(i));
	}
	getContentPane().add(new JScrollPane(new JList(listModel)));
	vectorModel.addListener(modelListener);
	// Commenting out discussed bellow
	// this.vectorModel = vectorModel;
    }

    // Commenting out discussed bellow
    public void dispose() {
	System.out.println("Disposing " + getTitle());
	super.dispose();
	// vectorModel.removeListener(modelListener);
    }

    protected void finalize() throws Throwable {
	System.out.println("Finalizing " + getTitle());
	super.finalize();
	synchronized (VectorListFrame.class) {
	    nFrames--;
	}
    }
}
