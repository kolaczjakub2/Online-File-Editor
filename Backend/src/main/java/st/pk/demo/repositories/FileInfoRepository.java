package st.pk.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import st.pk.demo.model.FileInfo;

import java.util.UUID;

@Repository
public interface FileInfoRepository extends JpaRepository<FileInfo, UUID> {
}
