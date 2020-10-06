import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;

public class AgenteResolver extends Agent{
        GUI g = new GUI();
        public void setup() {
            System.out.println("Olá, sou o agente " + getLocalName());

            addBehaviour(new OneShotBehaviour() {
                public void action() {
                    if(g.boardControl.isSolving()) return;
                    g.boardControl.solve(g, Resolver.SOLVE_METHOD.A_STAR);
                    g.pack();
                }
            });
            addBehaviour(new CyclicBehaviour() {
                public void action() {
                }
            });
        }
    }
