package ro.msg.learning.shop.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.entities.Customer;
import ro.msg.learning.shop.repositories.CustomerRepository;
import ro.msg.learning.shop.services.utils.EntityNotFoundException;

@RequiredArgsConstructor
@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final CustomerRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        User.UserBuilder builder = null;

        Customer customer = customerRepository.findByUsername(s);

        System.out.println("customer: " + customer.getUsername());

        if(null != customer) {
            builder = User.withUsername(s);
            builder.password(new BCryptPasswordEncoder().encode(customer.getPassword()));
            builder.roles("USER");
            return  builder.build();
        }
        throw new EntityNotFoundException(s, "customer");
    }
}
