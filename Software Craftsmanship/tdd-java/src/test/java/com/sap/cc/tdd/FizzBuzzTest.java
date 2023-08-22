package com.sap.cc.tdd;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FizzBuzzTest {

	private FizzBuzz fizzBuzz;

	@BeforeEach
	void setUp() {
		fizzBuzz = new FizzBuzz();
	}

	@Test
	void oneShouldGiveOne() {
		assertThat(fizzBuzz.print(1)).isEqualTo("1");
		assertThat(fizzBuzz.print(2)).isEqualTo("2");
	}

	@Test
	void threeShouldGiveFizz(){
		assertThat(fizzBuzz.print(3)).isEqualTo("Fizz");
	}

	@Test
	void fiveShoudlGiveBuzz(){
		assertThat(fizzBuzz.print(5)).isEqualTo("Buzz");
	}

	@Test
	void threeFactorsShouldGiveFizz(){
		assertThat(fizzBuzz.print(6)).isEqualTo("Fizz");
	}

	@Test
	void fiveFactorsShouldGiveBuzz(){
		assertThat(fizzBuzz.print(10)).isEqualTo("Buzz");
	}

	@Test
	void fifteenShouldGiveFizzBuzz(){
		assertThat(fizzBuzz.print(15)).isEqualTo("FizzBuzz");
	}

	@Test
	void threeAndFiveFactorsShouldGiveFizzBuzz(){
		assertThat(fizzBuzz.print(15)).isEqualTo("FizzBuzz");
		assertThat(fizzBuzz.print(30)).isEqualTo("FizzBuzz");
		assertThat(fizzBuzz.print(45)).isEqualTo("FizzBuzz");
	}

}
