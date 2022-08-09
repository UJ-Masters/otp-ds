package za.ac.uj.masters.otp.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import za.ac.uj.masters.otp.entity.Otp;

@Repository
public interface OtpRepository extends CrudRepository<Otp, String> {
}
