package bikepoa;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Igor Brum
 */
public class Dijkstra {
    List<Vertice> menorCaminho = new ArrayList<Vertice>();
    Vertice verticeCaminho = new Vertice();
    Vertice atual = new Vertice();
    Vertice vizinho = new Vertice();
    List<Vertice> naoVisitados = new ArrayList<Vertice>();

    // Algoritmo de Dijkstra
    public List<Vertice> encontrarMenorCaminhoDijkstra(Grafo grafo, Vertice v1, Vertice v2) {

        menorCaminho.add(v1);

        for (int i = 0; i < grafo.getVertices().size(); i++) {
            if (grafo.getVertices().get(i).getDescricao().equals(v1.getDescricao())) {
                grafo.getVertices().get(i).setDistancia(0);
            } else {
                grafo.getVertices().get(i).setDistancia(9999);
            }
            this.naoVisitados.add(grafo.getVertices().get(i));
        }

        Collections.sort(naoVisitados);

        while (!this.naoVisitados.isEmpty()) {
            atual = this.naoVisitados.get(0);
            for (int i = 0; i < atual.getArestas().size(); i++) {
                vizinho = atual.getArestas().get(i).getDestino();
                if (!vizinho.verificarVisita()) {
                    if (vizinho.getDistancia() > (atual.getDistancia() + atual.getArestas().get(i).getPeso())) {
                        vizinho.setDistancia(atual.getDistancia()+ atual.getArestas().get(i).getPeso());
                        vizinho.setParent(atual);
                        if (vizinho == v2) {
                            menorCaminho.clear();
                            verticeCaminho = vizinho;
                            menorCaminho.add(vizinho);
                            while (verticeCaminho.getParent() != null) {
                                menorCaminho.add(verticeCaminho.getParent());
                                verticeCaminho = verticeCaminho.getParent();
                            }
                            Collections.sort(menorCaminho);
                        }
                    }
                }
            }
            atual.visitar();
            this.naoVisitados.remove(atual);
            Collections.sort(naoVisitados);
        }
    return menorCaminho;
    }
}
