package com.apap.tutorial6.repository;

import static org.junit.Assert.assertThat;

import java.util.Optional;

import com.apap.tutorial6.model.CarModel;
import com.apap.tutorial6.model.DealerModel;

import org.hamcrest.Matchers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Untuk mengetes apakah fungsi findByType() menghasilkan object yang sesuai
 * dengan ekspektasi.
 *
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class CarDbTest {
	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private CarDb carDb;

	@Test
	public void whenFindByType_thenReturnCar() {
		
		//Pada Given, akan diinisiasi objek yang akan menjadi sebuah entitas pada database untuk dites
		DealerModel dealerModel = new DealerModel();
		dealerModel.setNama("Rendezvous");
		dealerModel.setAlamat("Elan Plateau");
		dealerModel.setNoTelp("0217382917");
		entityManager.persist(dealerModel);
		entityManager.flush();

		/**When akan dilakukan pemanggilan method yang ingin ditest. Pada code diatas kita
		akan mengetes carDb, apakah method findByType() akan berjalan dengan memasukkannya pada
		objek found.**/
		CarModel carModel = new CarModel();
		carModel.setBrand("MV Agusta");
		carModel.setType("F4 RC");
		carModel.setPrice(new Long("1500000000"));
		carModel.setAmount(13);
		carModel.setDealer(dealerModel);
		entityManager.persist(carModel);
		entityManager.flush();

		
		//Then akan dilakukan pengecekan terhadap objek bar
		Optional<CarModel> found = carDb.findByType(carModel.getType());
		
		//apakah dia null
		assertThat(found.get(), Matchers.notNullValue());
		
		//esuai dengan objek awal yang telah diinisiasi yang telah disiapkan sebelumnya.
		assertThat(found.get(), Matchers.equalTo(carModel));

	}
}


