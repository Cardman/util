package code.network;
import code.gui.initialize.AbstractSocket;
import code.threads.AbstractLock;

public interface NetWindow {

    void gearClient(AbstractSocket _newSocket);

    void loop(Object _readObject, AbstractSocket _socket);

    void quitNetwork(Exiting _exit, AbstractSocket _socket);

    AbstractLock getLock();
}
