import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;

public class AgenteResolver extends Agent{
        GUI g = new GUI();
        public void setup() {
            System.out.println("Olá, sou o agente " + getLocalName());

            addBehaviour(new OneShotBehaviour() {
                public void action() {
                    if(g.controleTabela.isResolvendo()) return;
                    g.controleTabela.solve(g, Resolver.RESOLVER_POR.ESTRELA);
                    g.pack();
                }
            });
            addBehaviour(new CyclicBehaviour() {
                public void action() {
                }
            });
        }
    }
