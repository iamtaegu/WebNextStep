package com.example.nextstep;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringCalculator {
    /* 
    *  리팩토링 한 다음에 private의 메소드가 아니라 
    *  public으로 공개되어 있는 메소드의 주목하면 
    *  add() 메소드의 가속성이 좋음을 알 수 있음 
    */
    int add(String text) {        
        //if ( text == null || text.isEmpty() ) { return 0; }    
        if (isBlank(text)) return 0;    

        // [1] else를 사용하지 말자 
        // [1_1] 복잡도를 줄이기 위해 불필요한 조건 제거 
        // [1_2] 복자도를 줄이기 위해 중복되는 코드 분리 
        //if ( text.contains(",")) { 
        return sum(toInts(this.split(text)));

    }

    // [2] 메소드는 한 가지 책임만 가져야 함 
    // [2_1] 아래 sum의 문자 배열을 숫자로 변화하는 것과 
    // 숫자 배열의 합을 구하는 것을 분리
    private int sum(int[] numbers) {
        int sum = 0;
        for (int number : numbers) {
            sum += number;
        }
        return sum;
    }

    // [5] 요건 변경, 음수 전달하는 경우 RuntimeException
    private int[] toInts(String[] values) {
        int[] numbers = new int[values.length];
        for (int i=0; i < values.length; i++) {
            numbers[i] = toPositive(values[i]);
        }
        return numbers;
    }

    private int toPositive(String value) {
        int number = Integer.parseInt(value);
        if (number < 0) {
            throw new RuntimeException();
        }
        return number;
    }

    private boolean isBlank(String text) {
        if ( text == null || text.isEmpty() ) return true;

        return false;
    }

    // [3] 요건 변경, 구분자 ':' 추가
    // [4] 요견 변경, 커스텀 구분자 추가 
    private String[] split(String text) {
        Matcher m = Pattern.compile("//(.)\n(.*)").matcher(text);

        if (m.find()) {
            String customDelimeter = m.group(1);
            return m.group(2).split(customDelimeter);
        }

        return text.split(",|:");
    }
}
