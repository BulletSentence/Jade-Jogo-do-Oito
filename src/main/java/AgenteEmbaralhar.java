import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;

public class AgenteEmbaralhar extends Agent{
        GUI g = new GUI();
        public void setup() {
            System.out.println("Olá, sou o agente " + getLocalName());

            addBehaviour(new OneShotBehaviour() {
                public void action() {
                    if(g.controleTabela.isResolvendo()) return;
                    g.controleTabela.randomizeBoard();
                    g.drawBoard();
                }
            });
            addBehaviour(new CyclicBehaviour() {
                public void action() {
                }
            });
        }
    }
