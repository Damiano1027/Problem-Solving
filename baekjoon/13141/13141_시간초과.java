import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int N, M;
    static class Line {
        Fired[] firedList;
        int startVertex, endVertex;
        int currentIndex = 0;
        Line(int startVertex, int endVertex) {
            this.startVertex = startVertex;
            this.endVertex = endVertex;
        }
        Line getNewLine() {
            Line line = new Line(startVertex, endVertex);
            line.firedList = new Fired[firedList.length];
            for (int i = 0; i < firedList.length; i++) {
                line.firedList[i] = firedList[i].copy();
            }
            return line;
        }
        Line getReversedLine() {
            Line line = new Line(endVertex, startVertex);
            line.firedList = new Fired[firedList.length];
            int k = firedList.length - 1;
            for (int i = 0; i < firedList.length; i++) {
                line.firedList[i] = firedList[k--];
            }
            return line;
        }
        boolean currentIndexFired() {
            return firedList[currentIndex].fired;
        }
        void fire() {
            firedList[currentIndex++].fired = true;
        }
        boolean currentIndexExceed() {
            return currentIndex >= firedList.length;
        }
        boolean equalsLine(Line line) {
            if (firedList.length != line.firedList.length) {
                return false;
            }

            int k = firedList.length - 1;
            for (int i = 0; i < firedList.length; i++) {
                if (firedList[i] != line.firedList[k--]) {
                    return false;
                }
            }
            return true;
        }
        boolean isFull() {
            for (Fired fired: firedList) {
                if (!fired.fired) {
                    return false;
                }
            }
            return true;
        }
    }
    static class Fired {
        boolean fired;
        Fired copy() {
            Fired newFired = new Fired();
            newFired.fired = fired;
            return newFired;
        }
    }
    static Map<Integer, List<Line>> staticMap = new HashMap<>();
    static double result = -1;


    public static void main(String[] args) throws Exception {
        tokenizer = new StringTokenizer(reader.readLine());
        N = Integer.parseInt(tokenizer.nextToken());
        M = Integer.parseInt(tokenizer.nextToken());

        for (int i = 1; i <= N; i++) {
            staticMap.put(i, new ArrayList<>());
        }

        for (int i = 0; i < M; i++) {
            tokenizer = new StringTokenizer(reader.readLine());
            int S = Integer.parseInt(tokenizer.nextToken());
            int E = Integer.parseInt(tokenizer.nextToken());
            int L = Integer.parseInt(tokenizer.nextToken());

            Fired[] firedList = new Fired[L];
            for (int j = 0; j < L; j++) {
                firedList[j] = new Fired();
            }

            Line line = new Line(S, E);
            line.firedList = firedList;
            staticMap.get(S).add(line);
        }

        solve();

        writer.write(String.format("%.1f\n", result));
        writer.flush();

        writer.close();
    }

    static void solve() {
        for (int vertex = 1; vertex <= N; vertex++) {
            double time = fire(vertex);
            result = (result == -1) ? time : Math.min(result, time);
        }
    }

    static double fire(int startVertex) {
        Map<Integer, List<Line>> map = getMap();
        List<Line> ongoingLines = new LinkedList<>(map.get(startVertex));
        map.put(startVertex, new ArrayList<>());
        double time = 0.0;

        while (!ongoingLines.isEmpty()) {
            int halfTimeExistCount = 0;
            List<Line> addedOngoingLines = new ArrayList<>();
            int ongoingLinesSize = ongoingLines.size();

            for (int i = 0; i < ongoingLines.size(); i++) {
                Line ongoingLine = ongoingLines.get(i);

                if (ongoingLine.currentIndexFired()) {
                    halfTimeExistCount++;
                } else {
                    ongoingLine.fire();
                }

                if (ongoingLine.currentIndexExceed()) {
                    ongoingLines.remove(i);
                    i--;

                    addedOngoingLines.addAll(map.get(ongoingLine.endVertex));
                    map.put(ongoingLine.endVertex, new ArrayList<>());

                    for (int j = addedOngoingLines.size() - 1; j >= 0; j--) {
                        Line addedOngoingLine = addedOngoingLines.get(j);
                        if (addedOngoingLine.equalsLine(ongoingLine)) {
                            addedOngoingLines.remove(j);
                            break;
                        }
                    }
                } else if (ongoingLine.currentIndexFired()) {
                    ongoingLines.remove(i);
                    i--;
                }
            }

            for (int i = 0; i < ongoingLines.size(); i++) {
                Line line = ongoingLines.get(i);
                if (line.isFull()) {
                    ongoingLines.remove(i);
                    i--;
                }
            }

            ongoingLines.addAll(addedOngoingLines);

            if (halfTimeExistCount > 0) {
                if (ongoingLinesSize % 2 == 0 && ongoingLinesSize / 2 == halfTimeExistCount) {
                    time += 0.5;
                } else {
                    time += 1.0;
                }
            } else {
                time += 1.0;
            }
        }

        return time;
    }

    static Map<Integer, List<Line>> getMap() {
        Map<Integer, List<Line>> copiedMap = new HashMap<>();

        for (int i = 1; i <= N; i++) {
            copiedMap.put(i, new ArrayList<>());
        }

        for (Integer key: staticMap.keySet()) {
            List<Line> oldLines = staticMap.get(key);

            List<Line> copiedLines = new ArrayList<>();
            for (Line oldLine: oldLines) {
                copiedLines.add(oldLine.getNewLine());
            }
            copiedMap.get(key).addAll(copiedLines);

            for (Line copiedLine: copiedLines) {
                Line reversedLine = copiedLine.getReversedLine();
                copiedMap.get(reversedLine.startVertex).add(reversedLine);
            }
        }

        return copiedMap;
    }
}
