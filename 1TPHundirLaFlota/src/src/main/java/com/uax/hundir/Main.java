package com.uax.hundir;

import com.uax.hundir.model.*;
import com.uax.hundir.hash.ShipHashManager;
import com.uax.hundir.graph.PortGraph;
import javax.persistence.*;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Configuración de jugadores
        System.out.println("Configuración de jugadores. Cada jugador puede tener máximo 3 barcos.");
        System.out.print("Ingrese el nombre del Jugador 1: ");
        String player1Name = scanner.nextLine();
        Player player1 = new Player(player1Name);

        System.out.print("Ingrese el nombre del Jugador 2: ");
        String player2Name = scanner.nextLine();
        Player player2 = new Player(player2Name);

        // Configuración de barcos para cada jugador
        for (Player player : new Player[]{player1, player2}) {
            System.out.println("Configurando barcos para " + player.getName());
            int count = 0;
            while (count < 3) {
                System.out.println("Elija el tipo de barco: 1. Battleship 2. Frigate 3. Canoe");
                int choice = scanner.nextInt();
                scanner.nextLine(); // consumir el salto de línea
                System.out.print("Ingrese el nombre del barco: ");
                String shipName = scanner.nextLine();
                Ship ship = null;
                switch (choice) {
                    case 1:
                        ship = new Battleship(shipName);
                        break;
                    case 2:
                        ship = new Frigate(shipName);
                        break;
                    case 3:
                        ship = new Canoe(shipName);
                        break;
                    default:
                        System.out.println("Opción inválida. Intente de nuevo.");
                        continue;
                }
                player.addShip(ship);
                count++;
            }
        }

        // Persistencia inicial utilizando JPA
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hundirPU");
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(player1);
            em.persist(player2);
            em.getTransaction().commit();
            System.out.println("Jugadores y barcos persistidos en la base de datos.");
        } catch(Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
        emf.close();

        // Inicialización del gestor de tablas hash para los barcos (Ejercicio 7)
        ShipHashManager hashManager = new ShipHashManager();
        for (Player player : new Player[]{player1, player2}) {
            for (Ship ship : player.getShips()) {
                hashManager.addShip(ship);
            }
        }
        System.out.println("Tablas hash de barcos configuradas.");

        // Bucle principal de la partida: en cada turno, ambos jugadores atacan
        while (true) {
            player1.attack(player2);
            player2.attack(player1);
            if (player1.allShipsSunk() && player2.allShipsSunk()) {
                System.out.println("¡Empate!");
                break;
            } else if (player1.allShipsSunk()) {
                System.out.println(player2.getName() + " gana la partida!");
                break;
            } else if (player2.allShipsSunk()) {
                System.out.println(player1.getName() + " gana la partida!");
                break;
            }
            System.out.println("Presione Enter para continuar al siguiente turno...");
            scanner.nextLine();
        }

        // Demostración del módulo de grafo de puertos (Ejercicio 8)
        PortGraph graph = new PortGraph();
        // Se agregan algunos puertos y se conectan con aristas (con distancias)
        graph.addPort("Madero");
        graph.addPort("Rodas");
        graph.addPort("Puerto3");
        graph.addEdge("Madero", "Rodas", 10);
        graph.addEdge("Madero", "Puerto3", 5);
        graph.addEdge("Puerto3", "Rodas", 3);
        System.out.println("Recorrido en profundidad (DFS) iniciando en Madero:");
        graph.depthFirstTraversal("Madero");
        System.out.println("Camino más corto desde Madero a Rodas: " + graph.shortestPath("Madero", "Rodas"));
        String portWithMostEdges = graph.portWithMostEdges();
        System.out.println("Puerto con mayor número de aristas: " + portWithMostEdges);
        graph.removePort(portWithMostEdges);
        System.out.println("Después de la eliminación, los puertos en el grafo son: " + graph.getPorts());

        scanner.close();
    }
}
