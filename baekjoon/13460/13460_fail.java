import java.io.*;
import java.util.*;

public class Main {
    static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int K;
    static Wheel[] wheels = new Wheel[5];

    static class Wheel {
        int[] poles;
        int contactedPoleIndex;
        Wheel(int[] poles, int contactedPoleIndex) {
            this.poles = poles;
            this.contactedPoleIndex = contactedPoleIndex;
        }
        void rotate(int direction) {
            if (direction == 1) {
                int temp = poles[7];
                for (int i = 7; i > 0; i--) {
                    poles[i] = poles[i - 1];
                }
                poles[0] = temp;
            } else {
                int temp = poles[0];
                for (int i = 0; i < 7; i++) {
                    poles[i] = poles[i + 1];
                }
                poles[7] = temp;
            }
        }
        int contactedPole() {
            return poles[contactedPoleIndex];
        }
    }

    public static void main(String[] args) throws Exception {
        for (int i = 0; i < 4; i++) {
            String line = reader.readLine();

            int[] poles = new int[8];
            for (int j = 0; j < 8; j++) {
                poles[j] = Character.getNumericValue(line.charAt(j));
            }
            int contactedPoleIndex;

            if (i == 0 || i == 2) {
                contactedPoleIndex = 2;
            } else {
                contactedPoleIndex = 6;
            }

            wheels[i + 1] = new Wheel(poles, contactedPoleIndex);
        }

        K = Integer.parseInt(reader.readLine());
        for (int i = 0; i < K; i++) {
            tokenizer = new StringTokenizer(reader.readLine());

            int wheelNumber = Integer.parseInt(tokenizer.nextToken());
            int direction = Integer.parseInt(tokenizer.nextToken());

            rotate(wheelNumber, direction);
        }

        writer.write(String.format("%d\n", score()));
        writer.flush();
        writer.close();
    }

    static void rotate(int wheelNumber, int direction) {
        boolean left = isRange(wheelNumber - 1)
                && (wheels[wheelNumber - 1].contactedPole() != wheels[wheelNumber].contactedPole());
        boolean right = isRange(wheelNumber + 1)
                && (wheels[wheelNumber + 1].contactedPole() != wheels[wheelNumber].contactedPole());

        wheels[wheelNumber].rotate(direction);

        if (left) {
            rotateLeftWheel(wheelNumber - 1, direction * -1);
        }
        if (right) {
            rotateRightWheel(wheelNumber + 1, direction * -1);
        }
    }

    static void rotateLeftWheel(int wheelNumber, int direction) {
        boolean left = isRange(wheelNumber - 1)
                && (wheels[wheelNumber - 1].contactedPole() != wheels[wheelNumber].contactedPole());

        wheels[wheelNumber].rotate(direction);

        if (left) {
            rotateLeftWheel(wheelNumber - 1, direction * -1);
        }
    }

    static void rotateRightWheel(int wheelNumber, int direction) {
        boolean right = isRange(wheelNumber + 1)
                && (wheels[wheelNumber + 1].contactedPole() != wheels[wheelNumber].contactedPole());

        wheels[wheelNumber].rotate(direction);

        if (right) {
            rotateRightWheel(wheelNumber + 1, direction * -1);
        }
    }

    static boolean isRange(int wheelNumber) {
        return wheelNumber >= 1 && wheelNumber <= 4;
    }

    static int score() {
        int score = 0;

        score += wheels[1].poles[0] == 0 ? 0 : 1;
        score += wheels[2].poles[0] == 0 ? 0 : 2;
        score += wheels[3].poles[0] == 0 ? 0 : 4;
        score += wheels[4].poles[0] == 0 ? 0 : 8;

        return score;
    }
}
