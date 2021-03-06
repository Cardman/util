package cards.gui.containers;
import cards.gui.MainWindow;
import cards.gui.animations.SimulationGameBelote;
import code.gui.CustComponent;

public class ContainerSimuBelote extends ContainerBelote implements ContainerSimu {

    private SimulationGameBelote animationSimulation;
    public ContainerSimuBelote(MainWindow _window) {
        super(_window);
        animationSimulation=new SimulationGameBelote(this);
        getOwner().getThreadFactory().newStartedThread(animationSimulation);
    }

    @Override
    public void withdrawCards() {
        tapisBelote().retirerCartes();
    }

}

