import Controladores.CriaAgentes;
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
                "Jogo:Puzzle();"
        };
        jade.Boot.main(par);
    }

    GUI g = new GUI();
    protected void setup() {
        g.setVisible(false);
        addBehaviour(new  TickerBehaviour(this,11000) {
            @Override
            protected void onTick() {
                g.setVisible(true);
            }
        });

        // -------------------------- CRIA PEÇAS --------------------------------------------
        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        jade.Boot.main(new CriaAgentes().getContainerPecas());
                    }
                },
                10000
        );

        // ------------------- CRIA AGENTE EMBARALHADOR --------------------------------------
        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(10000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        jade.Boot.main(new CriaAgentes().getContainerEmbaralhar());

                        if(g.controleTabela.isResolvendo()) return;
                        g.controleTabela.randomizeBoard();
                        g.drawBoard();
                    }
                },
                10000
        );

        // -------------------- CRIA AGENTE RESOLVEDOR ------------------------------------------
        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(20000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        jade.Boot.main(new CriaAgentes().getContainerResolver());

                        if(g.controleTabela.isResolvendo()) return;
                        g.controleTabela.solve(g, Resolver.RESOLVER_POR.ESTRELA);
                        g.pack();
                    }
                },
                20000
        );
    }
}
