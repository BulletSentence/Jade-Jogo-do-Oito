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

        // Agente Embaralhador

        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        String[] novoContainer = {
                                "-local-host", "127.0.0.1", "-container",
                                "-container-name", "Container-Embaralhar",
                                "Embaralhador:AgenteEmbaralhar()"};
                        jade.Boot.main(novoContainer);

                        if(g.boardControl.isSolving()) return;
                        g.boardControl.randomizeBoard();
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

                        String[] novoContainer = {
                                "-local-host", "127.0.0.1", "-container",
                                "-container-name", "Container-Resolver",
                                "Resolvedor:AgenteResolver()"};
                        jade.Boot.main(novoContainer);

                        if(g.boardControl.isSolving()) return;
                        g.boardControl.solve(g, Resolver.SOLVE_METHOD.A_STAR);
                        g.pack();
                    }
                },
                6000
        );
    }
}
