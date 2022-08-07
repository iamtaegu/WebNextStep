public class Calculator { 
    int add(int i, int j) {
        return i + j;        
    }

    int subtract(int i, int j) {
        return i - j;
    }

    int multiply(int i, int j) {
        return i * j;
    }

    int divide(int i, int j) {
        return i / j; 
    }

    /* 
     * Calculator production 코드
     * main은 Calculator production 테스트 코드 
    */
    /* public static void main(String[] args) {
        Calculator cal = new Calculator();
        System.out.println(cal.add(3,4));
        System.out.println(cal.subtract(5, 4));
        System.out.println(cal.multiply(2, 6));
        System.out.println(cal.divide(8, 4));
    } */
}