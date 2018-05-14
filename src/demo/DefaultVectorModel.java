package demo;

import java.util.Vector;

import demo.VectorModel.Listener;

/**
 * this Vector Model cann't clear visitors if they are not active any more
 * 
 * @author etkhrto
 *
 */
public class DefaultVectorModel extends AbstractVectorModel<Listener> {

    public static void main(String[] args) {

	execute(new DefaultVectorModel());

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
	return listeners.elementAt(i);
    }

    @Override
    public Vector<Listener> getListeners() {
	return listeners;
    }

    @Override
    public void addListener(Listener listener) {
	listeners.add(listener);

    }

    @Override
    public void removeListener(Listener listener) {
	listeners.remove(listener);

    }

}
