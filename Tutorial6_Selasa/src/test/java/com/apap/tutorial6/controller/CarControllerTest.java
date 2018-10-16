package com.apap.tutorial6.controller;

import java.util.Optional;

import com.apap.tutorial6.model.CarModel;
import com.apap.tutorial6.service.CarService;
import com.apap.tutorial6.service.DealerService;

import org.hamcrest.Matchers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * untuk mengetes apakah route “/car/view” mengembalikan hasil yang sesuai dengan ekspektasi.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(CarController.class)
public class CarControllerTest {
	
	// inisiasi MockMvc, CarService, dan DealerService.
	
	@Autowired
	private MockMvc mvc;

	@MockBean
	private CarService carService;

	@MockBean
	private DealerService dealerService;

	@Test
	public void givenCarType_whenViewCar_thenReturnJsonCar() throws Exception {
		
		// inisiasi MockMvc, CarService, dan DealerService.
		CarModel car = new CarModel();
		car.setBrand("MV Agusta");
		car.setType("F4 RC");
		car.setPrice(new Long("1500000000"));
		car.setAmount(13);
		Optional<CarModel> carModel = Optional.of(car);
		Mockito.when(carService.getCarDetailByType(carModel.get().getType())).thenReturn(carModel);
	
		/**
		 * When akan dilakukan pemanggilan route pada controller. 
		 * tes route “/car/view” dengan RequestParam tipe mobil yang telah diinisiasi sebelumnya.
		 */
		mvc.perform(MockMvcRequestBuilders.get("/car/view")
			.param("type", car.getType())
			.contentType(MediaType.APPLICATION_JSON))
		
			//Then akan dilakukan pengecekan terhadap hasil dari pemanggilan route tersebut.
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.type", Matchers.is(car.getType())));
	}

}
