package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class DemoApplicationTests {

	@Test
	void contextLoads() {
	}

	Calculator calcTest = new Calculator();
	@Test
	void itShouldAddNumbers(){
		// given
		int numberOne = 20;
		int numberTwo = 30;

		// when
		int result = calcTest.add(numberOne, numberTwo);

		// then
		int expected = 60;
		assertThat(result).isEqualTo(expected);
	}

	class Calculator{
		int add(int a, int b){
			return a + b;
		}
	}

}
