package com.example.nextstep;

import org.junit.Before;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StringCalculatorTest {

    private StringCalculator cal;

    @Before
    public void setup() {
        cal = new StringCalculator();
    }
        
    /** 1. 빈 문자열 또는 null 값을 입력할 경우 0을 반환한다 */
    @Test
    public void add_null_또는_빈문자() {
        assertEquals(0, cal.add(null));
        assertEquals(0, cal.add(""));
    }

    /** 2. 숫자 하나를 문자열로 입력할 경우 해당 숫자 반환 */
    @Test
    public void add_숫자하나() throws Exception {
        assertEquals(1, cal.add("1"));
    }

    /** 3. 숫자 두개를 쉼표 구분자로 입력할 경우 두 숫자의 합을 반환 */
    @Test
    public void add_쉼표구분자() throws Exception {
        assertEquals(3, cal.add("1,2"));
    }

    /** 4. 쉼표 이외에 콜론을 사용할 수 있음 */
    @Test
    public void add_쉼표_또는_콜론_구분자() throws Exception {
        assertEquals(6, cal.add("1,2:3"));
    }

    /** 5. //와 \n 문자 사이에 커스텀 구분자를 지정할 수 있음 */
    @Test
    public void add_custom_구분자() throws Exception {
        assertEquals(6, cal.add("//;\n1;2;3"));
    }

    /** 6. 음수를 전달하는 경우 RuntimeException 예외를 throw */
    @Test(expected = RuntimeException.class)
    public void add_negative() throws Exception {
        cal.add("-1,2,3");
    }
}
