import java.util.*;

class StockPricePredictor {
    private PriorityQueue<Integer> maxHeap;
    private PriorityQueue<Integer> minHeap;
    private List<Integer> stockPrices;
    private int windowSize;

    public StockPricePredictor(int windowSize) {
        this.windowSize = windowSize;
        this.stockPrices = new ArrayList<>();
        this.maxHeap = new PriorityQueue<>(Collections.reverseOrder());
        this.minHeap = new PriorityQueue<>();
    }

    public void addPrice(int price) {
        stockPrices.add(price);
        maxHeap.offer(price);
        minHeap.offer(price);

        if (stockPrices.size() > windowSize) {
            int removedPrice = stockPrices.remove(0);
            maxHeap.remove(removedPrice);
            minHeap.remove(removedPrice);
        }
    }

    public int getMaxPrice() {
        return maxHeap.peek();
    }

    public int getMinPrice() {
        return minHeap.peek();
    }

    public int predictNextPrice() {
        if (stockPrices.size() < windowSize) {
            System.out.println("Not enough data to make a prediction.");
            return -1;
        }

        int sum = 0;
        for (int i = stockPrices.size() - windowSize; i < stockPrices.size() - 1; i++) {
            sum += stockPrices.get(i + 1) - stockPrices.get(i);
        }

        int avgChange = sum / (windowSize - 1);
        return stockPrices.get(stockPrices.size() - 1) + avgChange;
    }

    public void printCurrentWindow() {
        System.out.println("Current Window: " + stockPrices);
        System.out.println("Max Price in Window: " + getMaxPrice());
        System.out.println("Min Price in Window: " + getMinPrice());
    }

    public static void main(String[] args) {
        StockPricePredictor predictor = new StockPricePredictor(5);

        predictor.addPrice(100);
        predictor.addPrice(102);
        predictor.addPrice(105);
        predictor.addPrice(103);
        predictor.addPrice(107);

        predictor.printCurrentWindow();

        int predictedPrice = predictor.predictNextPrice();
        System.out.println("Predicted Next Price: " + predictedPrice);

        predictor.addPrice(110);
        predictor.printCurrentWindow();
        predictedPrice = predictor.predictNextPrice();
        System.out.println("Predicted Next Price: " + predictedPrice);
    }
}
