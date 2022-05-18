/*
 https://rosettacode.org/wiki/A*_search_algorithm#Java
 https://stackabuse.com/graphs-in-java-a-star-algorithm/

*/
class Star {
    static class Node implements Comparable<Node> {
        private Node parent;
        private double x, y;

      
        public Node(Node parent, double x, double y) {
            this.parent = parent;
            this.x = x;
            this.y = y;

        }

        public Node getParent() {
            return parent;
        }

        public double getX() {
            return x;
        }

        public double getY() {
            return y;
        }

        public static double calculateEuclideanDistance(Node source, Node target) {
            double distanceX = target.getX() - source.getX();
            double distanceY = target.getY() - source.getY();
            return Math.sqrt(distanceX * distanceX + distanceY * distanceY);
        }

        /**
         * Compare by f value (g + h)
         */
        @Override
        public int compareTo(Node o) {
            // TODO Auto-generated method stub
            return 0;
        }
    }
}