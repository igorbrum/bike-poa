package bikepoa;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Igor Brum
 */
public class Main {
    
    public static void main(String[] args) {
        Grafo grafo = new Grafo();
        
        grafo.setVertices(readFile("files/arestas.txt"));
        
        Vertice i1 = new Vertice();
        Vertice i2 = new Vertice();
        
        i1 = grafo.encontrarVertice("1");
        i2 = grafo.encontrarVertice("8");
        
        List<Vertice> resultado = new ArrayList<Vertice>();
        
        Dijkstra algoritmo = new Dijkstra();
        
        resultado = algoritmo.encontrarMenorCaminhoDijkstra(grafo, i1, i2);
        
        System.out.println("Menor caminho: "+resultado);
    }

    private static List<Vertice> readFile(String fileToRead) {
        Grafo g = new Grafo();
        Vertice v;
        File file = new File(fileToRead);
        String vertices[];
        String linha;
        ArrayList<String[]> s1 = new ArrayList<String[]>();
        
        try {
            BufferedReader r = new BufferedReader(new FileReader(file));
            Map<String, Vertice> mapa = new HashMap<String, Vertice>();
            
            while ((linha = r.readLine()) != null) {
                if (linha.contains(" ")) {
                    s1.add(linha.split("/"));
                    vertices = s1.get(0)[0].split(" ");
                    
                    v = (Vertice) mapa.get(vertices[0]);
                    if (v == null) { v = new Vertice();}
                    
                    List<Vertice> vizinhosAtual = new ArrayList<Vertice>();
                    List<Aresta> arestasAtual = new ArrayList<Aresta>();
                    v.setDescricao(vertices[0]);
                    mapa.put(vertices[0], v);
                    
                    if (linha.contains("/")) {
                        String pesoArestas[] = s1.get(0)[1].split(" ");
                        
                        for (int i = 1; i < vertices.length; i++) {
                            Vertice vit;
                            vit = mapa.get(vertices[i]);
                            if (vit == null) { vit = new Vertice(); }
                            vit.setDescricao(vertices[i]);
                            vizinhosAtual.add(vit);
                            mapa.put(vertices[i], vit);
                            
                            Aresta ait = new Aresta(v, vit);
                            ait.setPeso(Integer.parseInt(pesoArestas[i - 1]));
                            arestasAtual.add(ait);
                        }
                        v.setVizinhos(vizinhosAtual);
                        v.setArestas(arestasAtual);
                    }
                } else {
                    // v = g.encontrarVertice(linha);
                    v = (Vertice) mapa.get(linha);
                    if (v == null) { v = new Vertice(); }
                    v.setDescricao(linha);
                    mapa.put(linha, v);
                }
                
                g.adicionarVertice(v);
                s1.clear();
            }
        } catch (FileNotFoundException ex) {
            System.err.println("Arquivo nao encontrado");
            ex.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return g.getVertices();
    }
}
