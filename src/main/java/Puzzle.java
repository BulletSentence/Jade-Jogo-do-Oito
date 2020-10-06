import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

public class Puzzle extends Agent {
    public static void main(String[] args){
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        }

        String[] par = {
                "-gui",
                "-local-host",
                "127.0.0.1",
                "Jogo:Puzzle()"
        };
        jade.Boot.main(par);
    }

    GUI g = new GUI();
    protected void setup() {

        addBehaviour(new  TickerBehaviour(this,500) {
            @Override
            protected void onTick() {
                g.setVisible(true);
            }

        });

        jade.Boot.main(new CriaAgentes().getContainerPecas());

        // Agente Embaralhador
        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        jade.Boot.main(new CriaAgentes().getContainerEmbaralhar());
                        if(g.controleTabela.isResolvendo()) return;
                        g.controleTabela.randomizeBoard();
                        g.drawBoard();
                    }
                },
                8000
        );

        // Resolvedor
        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(10000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        jade.Boot.main(new CriaAgentes().getContainerResolver());
                        if(g.controleTabela.isResolvendo()) return;
                        g.controleTabela.solve(g, Resolver.RESOLVER_POR.ESTRELA);
                        g.pack();
                    }
                },
                6000
        );
    }
}
