package demo;

import java.util.Vector;

import demo.VectorModel.Listener;

/**
 * this Vector Model cann't clear visitors if they are not active any more
 * 
 * @author etkhrto
 *
 */
public class DefaultVectorModel extends AbstractVectorModel<Listener<String>, String> {

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
    protected Listener<String> getListener(int i) {
	return listeners.elementAt(i);
    }

    @Override
    public Vector<Listener<String>> getListeners() {
	return listeners;
    }

    @Override
    public void addListener(Listener<String> listener) {
	listeners.add(listener);

    }

    @Override
    public void removeListener(Listener<String> listener) {
	listeners.remove(listener);

    }

}
