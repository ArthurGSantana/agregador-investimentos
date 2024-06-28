package tech.arthur.agregadorinvestimentos.service;

import org.springframework.stereotype.Service;
import tech.arthur.agregadorinvestimentos.entity.BillingAddress;
import tech.arthur.agregadorinvestimentos.repository.BillingAddressRepository;

@Service
public class BillingAddressService {
    private final BillingAddressRepository billingAddressRepository;

    public BillingAddressService(BillingAddressRepository billingAddressRepository) {
        this.billingAddressRepository = billingAddressRepository;
    }

    public void createBillingAddress(BillingAddress billingAddress) {
        billingAddressRepository.save(billingAddress);
    }
}
