import java.util.*;

public class Main {
    // Función heurística para estimar la distancia a la meta
    static double heuristic(double[] state) {
        double x = state[0];
        double y = state[1];
        double goal_x = 10;
        double goal_y = 5;
        return Math.sqrt(Math.pow(x - goal_x, 2) + Math.pow(y - goal_y, 2));
    }

    // Función para generar los sucesores del estado actual
    static List<double[]> successors(double[] state) {
        double x = state[0];
        double y = state[1];
        double delta_h = 0.1;
        List<double[]> successors = new ArrayList<>();
        // Movimiento hacia la izquierda
        double[] new_state_left = {x - delta_h, y};
        // Movimiento hacia la derecha
        double[] new_state_right = {x + delta_h, y};
        successors.add(new_state_left);
        successors.add(new_state_right);
        return successors;
    }

    // Algoritmo de búsqueda heurística
    static List<double[]> search(double[] initial_state) {
        Set<double[]> visited = new HashSet<>();
        List<double[]> queue = new ArrayList<>();
        queue.add(initial_state);
        while (!queue.isEmpty()) {
            double[] state = queue.remove(0);
            if (visited.contains(state)) {
                continue;
            }
            visited.add(state);
            if (state[0] == 10 && state[1] == 5) {
                List<double[]> path = new ArrayList<>();
                path.add(state);
                while (!queue.isEmpty()) {
                    double[] parent = queue.remove(0);
                    if (Arrays.equals(parent, path.get(path.size() - 1))) {
                        path.add(parent);
                    }
                }
                return path;
            }
            for (double[] successor : successors(state)) {
                if (!visited.contains(successor)) {
                    double g = state[1] + 1; // Costo de llegar al sucesor desde el estado actual
                    double h = heuristic(successor); // Estimación de la distancia a la meta
                    double[] nextState = {successor[0], g + h};
                    queue.add(nextState);
                }
            }
            queue.sort(Comparator.comparingDouble(x -> x[1])); // Ordenar la cola de estados por valor f
        }
        return null;
    }

    // Ejemplo de uso
    public static void main(String[] args) {
        double[] initial_state = {0, 5}; // Coordenadas del punto de partida B
        List<double[]> path = search(initial_state);
        if (path != null) {
            System.out.println("Secuencia de movimientos:");
            for (double[] state : path) {
                System.out.println(Arrays.toString(state));
            }
        } else {
            System.out.println("No se encontró una solución.");
        }
    }
}
