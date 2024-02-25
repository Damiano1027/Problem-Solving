import java.io.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static int G, P;
    static int[] uf;
    static boolean resultDetected = false;
    static int result = 0;

    public static void main(String[] args) throws Exception {
        G = Integer.parseInt(reader.readLine());
        P = Integer.parseInt(reader.readLine());

        uf = new int[G + 1];
        for (int i = 0; i <= G; i++) {
            uf[i] = i;
        }

        for (int i = 0; i < P; i++) {
            int g = Integer.parseInt(reader.readLine());

            if (resultDetected) {
                continue;
            }

            int root = find(g);
            if (root == 0) {
                resultDetected = true;
                continue;
            }
            result++;
            union(root - 1, root);
        }

        writer.write(result + "\n");
        writer.flush();

        writer.close();
    }

    static void union(int a, int b) {
        int aRoot = find(a);
        int bRoot = find(b);

        uf[bRoot] = aRoot;
    }

    static int find(int number) {
        if (uf[number] == number) {
            return number;
        }

        return uf[number] = find(uf[number]);
    }
}
