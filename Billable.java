public interface Billable {
    double calculateFee(int hours);

    default String getFormattedReceipt(int hours) {
        double fee = calculateFee(hours);
        return formatCurrency(fee);
    }

    private String formatCurrency(double amount) {
        return String.format("EUR %.2f", amount);
    }

    static void printTaxNotice() {
        System.out.println("Note: All parking rates include 23% Irish VAT.");
    }
}
