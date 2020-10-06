package Controladores;

import jade.core.*;

public class CriaAgentes extends Agent {

    String[] containerPecas = {
        "-local-host", "127.0.0.1", "-container",
            "-container-name", "Pecas",
            "A:Pecas.AgenteA();" +
            "B:Pecas.AgenteB();" +
            "C:Pecas.AgenteC();" +
            "D:Pecas.AgenteD();" +
            "E:Pecas.AgenteE();" +
            "F:Pecas.AgenteF();" +
            "G:Pecas.AgenteG();" +
            "H:Pecas.AgenteH();",
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
