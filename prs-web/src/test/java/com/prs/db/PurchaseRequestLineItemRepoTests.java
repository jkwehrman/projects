package com.prs.db;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.Assertions.assertThat;

import com.prs.business.PurchaseRequestLineItem;
import com.prs.business.User;
import com.prs.db.UserRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
public class PurchaseRequestLineItemRepoTests {


	@Autowired
	private PurchaseRequestLineItemRepository PurchaseRequestLineItemRepo;
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Test
	public void findByPurchaseRequestIDShouldReturnPRLI() {

	this.entityManager.persist(new PurchaseRequestLineItem(13, 2, 5, 95));
	Optional<PurchaseRequestLineItem> u = this.PurchaseRequestLineItemRepo.findByPurchaseRequestID(2);
	assertThat(u.get().getPurchaseRequestID()).isEqualTo(2);

	}
}