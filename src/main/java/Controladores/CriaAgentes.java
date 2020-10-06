package Controladores;

import jade.core.*;

public class CriaAgentes extends Agent {

    String[] containerPecas = {
        "-local-host", "127.0.0.1", "-container",
            "-container-name", "Pecas",
            "A:PecaAgenteA();" +
            "B:PecaAgenteB();" +
            "C:PecaAgenteC();" +
            "D:PecaAgenteD();" +
            "E:PecaAgenteE();" +
            "F:PecaAgenteF();" +
            "G:PecaAgenteG();" +
            "H:PecaAgenteH();",
    };

    String[] containerEmbaralhar = {
            "-local-host", "127.0.0.1", "-container",
            "-container-name", "Container-Embaralhar",
            "Embaralhador:AgenteEmbaralhar()"};

    String[] containerResolver = {
            "-local-host", "127.0.0.1", "-container",
            "-container-name", "Container-Resolver",
            "Resolvedor:AgenteResolver()"};


    // ---------------------------------------------------------------------


    public String[] getContainerResolver() {
        return containerResolver;
    }

    public String[] getContainerEmbaralhar() {
        return containerEmbaralhar;
    }

    public String[] getContainerPecas() {
        return containerPecas;
    }
}
