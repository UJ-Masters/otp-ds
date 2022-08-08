package za.ac.uj.otp.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import za.ac.uj.otp.entity.Otp;

@Repository
public interface OtpRepository extends CrudRepository<Otp, String> {
}
