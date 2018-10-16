package com.apap.tutorial6.service;

import static org.junit.Assert.assertThat;

import java.util.Optional;

import com.apap.tutorial6.model.CarModel;
import com.apap.tutorial6.repository.CarDb;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * untuk mengetes apakah fungsi getCarDetailByType() menghasilkan object yang sesuai dengan ekspektasi, 
 * dan apakah manipulasi (jika ada) yang dilakukan di service berjalan dengan semestinya.
 */
@RunWith(SpringRunner.class)
public class CarServiceTest {

	//inisiasi CarService
	@Autowired
	private CarService carService;
	
	/**
	 * a anotasi MockBean pada CarDb yang berguna untuk membuat dummy pada CarDb 
	 * agar hasil dari pemanggilan method JPA dapat dimanipulasi sesuai dengan test yang ingin dilakukan.
	 */
	@MockBean
	private CarDb carDb;

	/**
	 * anotasi TestConfiguration dan Bean yang berguna untuk menginisiasi
	 * carService yang merupakan interface dengan class implementasinya, 
	 * serta membatasi scope penggunaan dari carService tersebut.
	 */
	@TestConfiguration
	static class CarServiceTestContextConfiguration {
		@Bean 
		public CarService carService() {
			return new CarServiceImpl();
		}
	}

	@Test
	public void whenValidType_thenCarShouldBeFound() {
		
		//given: menginisialisasi objek yang akan menjadi sebuah entitas untuk dites
		CarModel car = new CarModel();
		car.setBrand("MV Agusta");
		car.setType("F4 RC");
		car.setPrice(new Long("1500000000"));
		car.setAmount(13);
		Optional<CarModel> carModel = Optional.of(car);
		Mockito.when(carDb.findByType(carModel.get().getType())).thenReturn(carModel);
		
		/**
		 * When akan dilakukan pemanggilan method yang ingin ditest. 
		 * akan mengetes carService, apakah getCarDetailByType() akan berjalan dengan memasukkannya pada objek found.
		 */
		Optional<CarModel> found = carService.getCarDetailByType(car.getType());
		
		/**
		 * Then akan dilakukan pengecekan terhadap objek baru apakah dia null 
		 * atau
		 * sesuai dengan objek awal yang telah diinisiasi yang telah disiapkan sebelumnya.
		 */
		assertThat(found, Matchers.notNullValue());
		assertThat(found.get().getType(), Matchers.equalTo(car.getType()));
	}
}